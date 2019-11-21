package com.zjcds.cde.scheduler.service.Impl;

import com.zjcds.cde.scheduler.dao.jpa.*;
import com.zjcds.cde.scheduler.dao.jpa.view.RepositoryJobViewDao;
import com.zjcds.cde.scheduler.domain.dto.JobForm;
import com.zjcds.cde.scheduler.domain.entity.*;
import com.zjcds.cde.scheduler.domain.entity.view.RepositoryJobView;
import com.zjcds.cde.scheduler.quartz.DBConnectionModel;
import com.zjcds.cde.scheduler.service.JobMonitorService;
import com.zjcds.cde.scheduler.service.JobService;
import com.zjcds.cde.scheduler.utils.CommonUtils;
import com.zjcds.cde.scheduler.utils.Constant;
import com.zjcds.common.base.domain.page.Paging;
import com.zjcds.common.dozer.BeanPropertyCopyUtils;
import com.zjcds.common.jpa.PageResult;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.beetl.sql.core.DSTransactionManager;
import org.beetl.sql.core.db.KeyHolder;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.ProgressNullMonitorListener;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.logging.KettleLogStore;
import org.pentaho.di.core.logging.LogLevel;
import org.pentaho.di.core.logging.LoggingBuffer;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import static org.eclipse.jetty.util.component.LifeCycle.stop;

/**
 * @author J on 20191112
 */
@Service
public class JobServiceImpl implements JobService {


    @Autowired
    private JobDao jobDao;

    @Autowired
    private JobMonitorService jobMonitorService;

    @Autowired
    private RepositoryDao repositoryDao;

    @Autowired
    private JobMonitorDao jobMonitorDao;

    @Autowired
    private JobRecordDao jobRecordDao;

    @Value("${cde.log.file.path}")
    private String cdeLogFilePath;

    @Value("${cde.file.repository}")
    private String cdeFileRepository;

    @Value("${com.zjcds.dataSources.druids.dataSource.driverClassName}")
    private String jdbcDriver;

    @Value("${com.zjcds.dataSources.druids.dataSource.url}")
    private String jdbcUrl;

    @Value("${com.zjcds.dataSources.druids.dataSource.username}")
    private String jdbcUsername;

    @Value("${com.zjcds.dataSources.druids.dataSource.password}")
    private String jdbcPassword;



    private org.pentaho.di.job.Job job;

    /**
     * @param uId   用户ID
     * @return BootTablePage
     * @Title getList
     * @Description 获取列表
     */
    @Override
    public PageResult<Job> getList(Paging paging, List<String> queryString, List<String> orderBys, Integer uId) {
        queryString.add("createUser~Eq~"+uId);
        queryString.add("delFlag~Eq~1");
        PageResult<Job> jobList = jobDao.findAll(paging, queryString, orderBys);
        
        return jobList;
    }

    /**
     * @param jobId 作业ID
     * @return void
     * @Title delete
     * @Description 删除作业
     */
    @Override
    @Transactional
    public void delete(Integer jobId) {
        Job job = jobDao.findOne(jobId);
        job.setDelFlag(0);
        jobDao.save(job);
    }

    /**
     * @param repositoryId 资源库ID
     * @param jobPath      作业路径
     * @param uId          用户ID
     * @return boolean
     * @Title check
     * @Description 检查当前作业是否可以插入到数据库
     */
    @Override
    public boolean check(Integer repositoryId, String jobPath, Integer uId) {
        List<Job> jobList = jobDao.findByCreateUserAndDelFlagAndJobRepositoryIdAndJobPath(uId,1,repositoryId,jobPath);
        if (null != jobList && jobList.size() > 0) {
            return false;
        } else {
            return true;
        }
    }


    /**
     * @param addJob          作业信息
     * @param uId           用户ID
     * @return void
     * @throws SQLException
     * @Title insert
     * @Description 插入作业到数据库
     */
    @Override
    @Transactional
    public void insert(JobForm.AddJob addJob, Integer uId) {
        //补充添加作业信息
        //作业基础信息
        Job job = BeanPropertyCopyUtils.copy(addJob,Job.class);
        //作业是否被删除
        job.setDelFlag(1);
        //作业是否启动
        job.setJobStatus(2);
        jobDao.save(job);
    }

    /**
     * @param jobId 作业ID
     * @return Job
     * @Title getJob
     * @Description 获取作业信息
     */
    @Override
    public Job getJob(Integer jobId) {
        Assert.notNull(jobId,"要查询的jobId不能为空");
        return jobDao.findOne(jobId);
    }

    /**
     * @param updateJob     作业对象
     * @param jobId           用户ID
     * @return void
     * @Title update
     * @Description 更新作业信息
     */
    @Override
    @Transactional
    public void update(JobForm.UpdateJob updateJob, Integer jobId) {
        Assert.notNull(jobId,"要更新的jobId不能为空");
        Job job = BeanPropertyCopyUtils.copy(updateJob,Job.class);
        jobDao.save(job);
    }


    /**
     * @param jobId 作业ID
     * @return void
     * @Title start
     * @Description 启动作业
     */
    @Override
    @Transactional
    public void start(Integer jobId,Integer uId,Map<String,String> param)throws KettleException {
        Assert.notNull(jobId,"要启动的作业id不能为空");
        Job job = jobDao.findOne(jobId);
        Repository repository = repositoryDao.findOne(job.getJobRepositoryId());
        String logFilePath = cdeLogFilePath;
        Date executeTime = new Date();
        Date nexExecuteTime = null;
        //添加监控
        jobMonitorService.addMonitor(uId,jobId,nexExecuteTime);
        manualRunRepositoryJob(repository,jobId.toString(),job.getJobName(),job.getJobPath(),uId.toString(),job.getJobLogLevel(),logFilePath,executeTime,nexExecuteTime,param);
    }


    /**
     * 手动执行
     * @param repository 资源库连接信息
     * @param jobId 作业id
     * @param jobName 作业名称
     * @param jobPath 作业路径
     * @param userId 用户id
     * @param logLevel 日志等级
     * @param logFilePath 日志路径
     * @param executeTime 执行时间
     * @param nexExecuteTime 下次执行时间
     * @param param 参数map
     * @throws KettleException
     */
    @Override
    @Transactional
    public void manualRunRepositoryJob(Repository repository, String jobId, String jobName, String jobPath, String userId, String logLevel, String logFilePath, Date executeTime, Date nexExecuteTime, Map<String,String> param) throws KettleException {
        KettleDatabaseRepository kettleDatabaseRepository  = init(repository);
        RepositoryDirectoryInterface directory = kettleDatabaseRepository.loadRepositoryDirectoryTree()
                .findDirectory(jobPath);
        JobMeta jobMeta = kettleDatabaseRepository.loadJob(jobName,directory,new ProgressNullMonitorListener(),null);
        if(param.size()>0){
            for (String key : param.keySet()){
                jobMeta.setParameterValue(key,param.get(key));
            }
        }
        job = new org.pentaho.di.job.Job(kettleDatabaseRepository, jobMeta);
        job.setDaemon(true);
        job.setLogLevel(LogLevel.DEBUG);
        if (StringUtils.isNotEmpty(logLevel)) {
            job.setLogLevel(Constant.logger(logLevel));
        }
        String exception = null;
        Integer recordStatus = 1;
//            Date jobStartDate = null;
        Date jobStopDate = null;
        String logText = null;
        try {
//                jobStartDate = new Date();
            job.run();
            job.waitUntilFinished();
            jobStopDate = new Date();
        } catch (Exception e) {
            exception = e.getMessage();
            recordStatus = 2;
        } finally {
            if (job.isFinished()) {
                if (job.getErrors() > 0) {
                    recordStatus = 2;
                    if(null == job.getResult().getLogText() || "".equals(job.getResult().getLogText())){
                        logText = exception;
                    }
                }
                // 写入作业执行结果
                StringBuilder allLogFilePath = new StringBuilder();
                allLogFilePath.append(logFilePath).append("/").append(userId).append("/")
                        .append(StringUtils.remove(jobPath, "/")).append("@").append(jobName).append("-log")
                        .append("/").append(new Date().getTime()).append(".").append("txt");
                String logChannelId = job.getLogChannelId();
                LoggingBuffer appender = KettleLogStore.getAppender();
                logText = appender.getBuffer(logChannelId, true).toString();
                try {
                    JobRecord jobRecord = new JobRecord();
                    jobRecord.setRecordJob(Integer.parseInt(jobId));
                    jobRecord.setCreateUser(Integer.parseInt(userId));
                    jobRecord.setLogFilePath(allLogFilePath.toString());
                    jobRecord.setRecordStatus(recordStatus);
                    jobRecord.setStartTime(executeTime);
                    jobRecord.setStopTime(jobStopDate);
                    writeToDBAndFile(jobRecord, logText, executeTime, nexExecuteTime);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 资源库初始化并连接
     * @param repository
     * @return
     * @throws KettleException
     */
    public KettleDatabaseRepository init(Repository repository)throws KettleException{
        KettleEnvironment.init();
        DatabaseMeta databaseMeta = new DatabaseMeta(null, repository.getRepositoryType(), repository.getDatabaseAccess(),
                repository.getDatabaseHost(), repository.getDatabaseName(), repository.getDatabasePort(), repository.getDatabaseUsername(), repository.getDatabasePassword());
        databaseMeta.addExtraOption(databaseMeta.getPluginId(), "characterEncoding", "UTF-8");
        databaseMeta.addExtraOption(databaseMeta.getPluginId(), "useUnicode", "true");
        //资源库元对象
        KettleDatabaseRepositoryMeta repositoryInfo = new KettleDatabaseRepositoryMeta();
        repositoryInfo.setConnection(databaseMeta);
        //资源库
        KettleDatabaseRepository kettleDatabaseRepository = new KettleDatabaseRepository();
        kettleDatabaseRepository.init(repositoryInfo);
        kettleDatabaseRepository.connect("admin", "admin");
        //判断是否连接成功
        if (kettleDatabaseRepository.isConnected()) {
            System.out.println( "connected" );
        }else{
            System.out.println("error");
        }
        return kettleDatabaseRepository;
    }


    /**
     * @param jobRecord         作业记录信息
     * @param logText            日志信息
     * @return void
     * @throws IOException
     * @throws SQLException
     * @Title writeToDBAndFile
     * @Description 保存作业运行日志信息到文件和数据库
     */
    public void writeToDBAndFile(JobRecord jobRecord, String logText, Date lastExecuteTime, Date nextExecuteTime)
            throws IOException {
        // 将日志信息写入文件
        FileUtils.writeStringToFile(new File(jobRecord.getLogFilePath()), logText, Constant.DEFAULT_ENCODING, false);
        // 写入转换运行记录到数据库
        jobRecordDao.save(jobRecord);
        JobMonitor template = new JobMonitor();
        template.setCreateUser(jobRecord.getCreateUser());
        template.setMonitorJob(jobRecord.getRecordJob());
//        JobMonitor templateOne = sqlManager.templateOne(template);
        JobMonitor templateOne = jobMonitorDao.findByMonitorJob(jobRecord.getRecordJob());
        templateOne.setLastExecuteTime(lastExecuteTime);
        //在监控表中增加下一次执行时间
        templateOne.setNextExecuteTime(nextExecuteTime);
        if (jobRecord.getRecordStatus() == 1) {// 证明成功
            //成功次数加1
            templateOne.setMonitorSuccess(templateOne.getMonitorSuccess() + 1);
            jobMonitorDao.save(templateOne);
        } else if (jobRecord.getRecordStatus() == 2) {// 证明失败
            //失败次数加1
            templateOne.setMonitorFail(templateOne.getMonitorFail() + 1);
            jobMonitorDao.save(templateOne);
        }
    }
}