package com.zjcds.cde.scheduler.service.Impl;

import com.zjcds.cde.scheduler.base.BeanPropertyCopyUtils;
import com.zjcds.cde.scheduler.base.PageResult;
import com.zjcds.cde.scheduler.base.Paging;
import com.zjcds.cde.scheduler.dao.jpa.JobDao;
import com.zjcds.cde.scheduler.dao.jpa.JobMonitorDao;
import com.zjcds.cde.scheduler.dao.jpa.JobRecordDao;
import com.zjcds.cde.scheduler.dao.jpa.RepositoryDao;
import com.zjcds.cde.scheduler.domain.dto.JobForm;
import com.zjcds.cde.scheduler.domain.dto.TaskForm;
import com.zjcds.cde.scheduler.domain.entity.Job;
import com.zjcds.cde.scheduler.domain.entity.JobMonitor;
import com.zjcds.cde.scheduler.domain.entity.JobRecord;
import com.zjcds.cde.scheduler.domain.entity.Repository;
import com.zjcds.cde.scheduler.service.JobMonitorService;
import com.zjcds.cde.scheduler.service.JobService;
import com.zjcds.cde.scheduler.service.TaskService;
import com.zjcds.cde.scheduler.utils.Constant;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
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

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private TaskService taskService;

    @Value("${cde.log.file.path}")
    private String cdeLogFilePath;
    @Value("${cde.file.repository}")
    private String cdeFileRepository;

    private org.pentaho.di.job.Job job;

    /**
     * @param uId   用户ID
     * @return BootTablePage
     * @Title getList
     * @Description 获取列表
     */
    @Override
    public PageResult<Job> getList(Paging paging, List<String> queryString, List<String> orderBys, Integer uId) {
        Assert.notNull(uId,"未登录,请重新登录");
        queryString.add("createUser~Eq~"+uId);
        queryString.add("delFlag~Eq~1");
        PageResult<Job> jobList = jobDao.findAll(paging, queryString, orderBys);
        return jobList;
    }

    @Override
    public List<Job> getList(Integer uId) {
        Assert.notNull(uId,"未登录,请重新登录");
        List<Job> jobList = jobDao.findByCreateUserAndDelFlag(uId,1);
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
    public void delete(Integer jobId,Integer uId) {
        Assert.notNull(uId,"未登录,请重新登录");
        Assert.notNull(jobId,"要删除的作业ID不能为空");
        Job job = jobDao.findByJobIdAndDelFlag(jobId,uId);
        Assert.notNull(job,"要删除的任务不存在或已删除");
        job.setDelFlag(0);
        jobDao.save(job);
        //移除策略
        taskService.deleteTask(job.getJobQuartz());
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
        Assert.notNull(uId,"未登录,请重新登录");
        Assert.notNull(repositoryId,"资源库ID不能为空");
        Assert.hasText(jobPath,"作业路径不能为空");
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
        Assert.notNull(uId,"未登录,请重新登录");
        //补充添加作业信息
        //作业基础信息
        Job job = BeanPropertyCopyUtils.copy(addJob,Job.class);
        boolean status = check(job.getJobRepositoryId(),job.getJobPath(),uId);
        Assert.isTrue(status,"该作业已存在");
        //作业是否被删除
        job.setDelFlag(1);
        //作业是否启动
        job.setJobStatus(2);
        job.setCreateUser(uId);
        job.setModifyUser(uId);
        jobDao.save(job);
    }

    /**
     * @param jobId 作业ID
     * @return Job
     * @Title getJob
     * @Description 获取作业信息
     */
    @Override
    public Job getJob(Integer jobId,Integer uId) {
        Assert.notNull(uId,"未登录,请重新登录");
        Assert.notNull(jobId,"要查询的jobId不能为空");
        return jobDao.findByJobId(jobId);
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
    public void update(JobForm.UpdateJob updateJob, Integer jobId,Integer uId) {
        Assert.notNull(uId,"未登录,请重新登录");
        Assert.notNull(jobId,"要更新的jobId不能为空");
        Job j = jobDao.findByJobIdAndDelFlag(jobId,1);
        Assert.notNull(j,"要修改的作业不存在或已删除");
        Job job = BeanPropertyCopyUtils.copy(updateJob,Job.class);
        job.setModifyUser(uId);
        job.setDelFlag(j.getDelFlag());
        job.setCreateUser(j.getCreateUser());
        job.setCreateTime(j.getCreateTime());
        job = jobDao.save(job);
        if(job.getJobQuartz()!=null){
            TaskForm.AddTask addTask = new TaskForm.AddTask();
            addTask.setJobId(job.getJobId());
            addTask.setQuartzId(job.getJobQuartz());
            addTask.setTaskName(job.getJobName());
            addTask.setTaskGroup("job");
            addTask.setTaskDescription(job.getJobDescription());
            if(updateJob.getJobQuartz()!=j.getJobQuartz()){
                //移除策略
                taskService.deleteTask(updateJob.getJobQuartz());
                //新增策略
                taskService.addTask(addTask,uId);
            }
        }
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
        Assert.notNull(uId,"未登录,请重新登录");
        Assert.notNull(jobId,"要启动的作业id不能为空");
        Job job = jobDao.findByJobId(jobId);
        Repository repository = repositoryDao.findByRepositoryId(job.getJobRepositoryId());
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
        Assert.notNull(userId,"未登录,请重新登录");
        KettleDatabaseRepository kettleDatabaseRepository  = init(repository);
        RepositoryDirectoryInterface directory = kettleDatabaseRepository.loadRepositoryDirectoryTree()
                .findDirectory(jobPath);
        JobMeta jobMeta = kettleDatabaseRepository.loadJob(jobName,directory,new ProgressNullMonitorListener(),null);
        if(param!=null&&param.size()>0){
            for (String key : param.keySet()){
                jobMeta.setParameterValue(key,param.get(key));
            }
        }
        job = new org.pentaho.di.job.Job(kettleDatabaseRepository, jobMeta);
        job.setDaemon(true);
        job.setLogLevel(LogLevel.BASIC);
        if (StringUtils.isNotEmpty(logLevel)) {
            job.setLogLevel(Constant.logger(logLevel));
        }
        String exception = null;
        Integer recordStatus = 2;
//            Date jobStartDate = null;
        Date jobStopDate = null;
        String logText = null;
        Integer runStatus = 2;
        try {
//                jobStartDate = new Date();
            //更改监控状态为执行中
            jobMonitorService.updateRunStatusJob(Integer.parseInt(jobId),Integer.parseInt(userId),1);
            job.run();
            job.waitUntilFinished();
            jobStopDate = new Date();
        } catch (Exception e) {
            exception = e.getMessage();
            recordStatus = 3;
            runStatus=3;
        } finally {
            if (job.isFinished()) {
                if (job.getErrors() > 0) {
                    recordStatus = 3;
                    runStatus=3;
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
                    writeToDBAndFile(jobRecord, logText, executeTime, nexExecuteTime,runStatus,userId);
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
    public void writeToDBAndFile(JobRecord jobRecord, String logText, Date lastExecuteTime, Date nextExecuteTime,Integer runStatus,String uId)
            throws IOException {
        // 将日志信息写入文件
        FileUtils.writeStringToFile(new File(jobRecord.getLogFilePath()), logText, Constant.DEFAULT_ENCODING, false);
        // 写入转换运行记录到数据库
        jobRecordDao.save(jobRecord);
        JobMonitor template = new JobMonitor();
        template.setCreateUser(jobRecord.getCreateUser());
        template.setMonitorJob(jobRecord.getRecordJob());
//        JobMonitor templateOne = sqlManager.templateOne(template);
        JobMonitor templateOne = jobMonitorDao.findByMonitorJobAndCreateUser(jobRecord.getRecordJob(),Integer.parseInt(uId));
        templateOne.setLastExecuteTime(lastExecuteTime);
        templateOne.setRunStatus(runStatus);
        //在监控表中增加下一次执行时间
        templateOne.setNextExecuteTime(nextExecuteTime);
        if (jobRecord.getRecordStatus() == 2) {// 证明成功
            //成功次数加1
            templateOne.setMonitorSuccess(templateOne.getMonitorSuccess() + 1);
            jobMonitorDao.save(templateOne);
        } else if (jobRecord.getRecordStatus() == 3) {// 证明失败
            //失败次数加1
            templateOne.setMonitorFail(templateOne.getMonitorFail() + 1);
            jobMonitorDao.save(templateOne);
        }
    }

    /**
     * 查询所有作业名称Map
     * @return
     */
    @Override
    public Map<Integer,String> jobNameMap(){
        List<Job> jobList = jobDao.findByDelFlag(1);
        Map<Integer,String> jobNameMap = new HashMap<>();
        if(jobList.size()>0&&jobList!=null){
            for (Job j:jobList){
                Map<Integer,String> map = new HashMap<>();
                map.put(j.getJobId(),j.getJobName());
                jobNameMap.putAll(map);
            }
        }
        return jobNameMap;
    }
}
