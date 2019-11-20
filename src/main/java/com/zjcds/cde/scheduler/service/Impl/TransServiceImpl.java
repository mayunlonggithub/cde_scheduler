package com.zjcds.cde.scheduler.service.Impl;

import com.zjcds.cde.scheduler.dao.jpa.*;
import com.zjcds.cde.scheduler.domain.dto.JobForm;
import com.zjcds.cde.scheduler.domain.dto.TransForm;
import com.zjcds.cde.scheduler.domain.entity.*;
import com.zjcds.cde.scheduler.quartz.DBConnectionModel;
import com.zjcds.cde.scheduler.service.TransMonitorService;
import com.zjcds.cde.scheduler.service.TransService;
import com.zjcds.cde.scheduler.utils.CommonUtils;
import com.zjcds.cde.scheduler.utils.Constant;
import com.zjcds.common.base.domain.page.Paging;
import com.zjcds.common.dozer.BeanPropertyCopyUtils;
import com.zjcds.common.jpa.PageResult;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.beetl.sql.core.DSTransactionManager;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.ProgressNullMonitorListener;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.logging.KettleLogStore;
import org.pentaho.di.core.logging.LogLevel;
import org.pentaho.di.core.logging.LoggingBuffer;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryMeta;
import org.pentaho.di.trans.TransMeta;
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

/**
 * @author J on 20191111
 */
@Service
public class TransServiceImpl implements TransService {
    private org.pentaho.di.trans.Trans trans;

    @Autowired
    private TransDao transDao;
    @Autowired
    private QuartzDao quartzDao;
    @Autowired
    private RepositoryDao repositoryDao;
    @Autowired
    private TransMonitorDao transMonitorDao;
    @Autowired
    private TransRecordDao transRecordDao;
    @Autowired
    private TransMonitorService transMonitorService;

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



    /**
     * @param uId   用户ID
     * @return BootTablePage
     * @Title getList
     * @Description 获取列表
     */
    @Override
    public PageResult<Trans> getList(Paging paging, List<String> queryString, List<String> orderBys, Integer uId) {
        queryString.add("createUser~Eq~"+uId);
        queryString.add("delFlag~Eq~1");
        PageResult<Trans> transPageResult = transDao.findAll(paging,queryString,orderBys);
        return transPageResult;
    }

    /**
     * @param transId 转换ID
     * @return void
     * @Title delete
     * @Description 删除转换
     */
    @Override
    @Transactional
    public void delete(Integer transId) {
        Trans trans = transDao.findOne(transId);
        trans.setDelFlag(0);
        transDao.save(trans);
    }

    /**
     * @param repositoryId 资源库ID
     * @param kTransPath   转换路径信息
     * @param uId          用户ID
     * @return boolean
     * @Title check
     * @Description 检查转换是否添加过
     */
    @Override
    public boolean check(Integer repositoryId, String kTransPath, Integer uId) {
        List<Trans> kTransList = transDao.findByCreateUserAndDelFlagAndTransRepositoryIdAndTransPath(uId,1,repositoryId,kTransPath);
        if (null != kTransList && kTransList.size() > 0) {
            return false;
        } else {
            return true;
        }
    }


    /**
     * @param addTrans        转换对象
     * @param uId           用户ID
     * @return void
     * @throws SQLException
     * @Title insert
     * @Description 添加转换到数据库
     */
    @Override
    @Transactional
    public void insert(TransForm.AddTrans addTrans, Integer uId) {
        //补充添加作业信息
        //作业基础信息
        Trans trans = BeanPropertyCopyUtils.copy(addTrans,Trans.class);
        //作业是否被删除
        trans.setDelFlag(1);
        //作业是否启动
        trans.setTransStatus(2);
        transDao.save(trans);
    }

    /**
     * @param transId 转换ID
     * @return KTrans
     * @Title getTrans
     * @Description 获取转换对象
     */
    @Override
    public Trans getTrans(Integer transId) {
        Assert.notNull(transId,"要查询的transId不能为空");
        return transDao.findOne(transId);
    }

    /**
     * @param updateTrans        转换对象
     * @param transId           转换ID
     * @return void
     * @Title update
     * @Description 更新转换信息
     */
    @Override
    @Transactional
    public void update(TransForm.UpdateTrans updateTrans, Integer transId) {
        Assert.notNull(transId,"要更新的transId不能为空");
        Trans trans = BeanPropertyCopyUtils.copy(updateTrans,Trans.class);
        transDao.save(trans);
    }


    /**
     * @param transId 转换ID
     * @return void
     * @Title start
     * @Description 启动转换
     */
    @Override
    @Transactional
    public void start(Integer transId,Integer uId,Map<String,String> param)throws KettleException {
        Assert.notNull(transId,"要启动的作业id不能为空");
        Trans trans = transDao.findOne(transId);
        Repository repository = repositoryDao.findOne(trans.getTransRepositoryId());
        String logFilePath = cdeLogFilePath;
        Date executeTime = new Date();
        Date nexExecuteTime = null;
        //添加监控
        transMonitorService.addMonitor(uId,transId,nexExecuteTime);
        manualRunRepositoryTrans(repository,transId.toString(),trans.getTransName(),trans.getTransPath(),uId.toString(),trans.getTransLogLevel(),logFilePath,executeTime,nexExecuteTime,param);
    }

    /**
     * 手动执行
     * @param repository 资源库连接信息
     * @param transId 转换id
     * @param transName 转换名称
     * @param transPath 转换路径
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
    public void manualRunRepositoryTrans(Repository repository, String transId, String transName, String transPath, String userId, String logLevel, String logFilePath, Date executeTime, Date nexExecuteTime, Map<String,String> param) throws KettleException {
        KettleDatabaseRepository kettleDatabaseRepository  = init(repository);
        RepositoryDirectoryInterface directory = kettleDatabaseRepository.loadRepositoryDirectoryTree()
                .findDirectory(transPath);
        TransMeta transMeta = kettleDatabaseRepository.loadTransformation(transName,directory,new ProgressNullMonitorListener(),true,null);
        if(param.size()>0){
            for (String key : param.keySet()){
                transMeta.setParameterValue(key,param.get(key));
            }
        }
        trans = new org.pentaho.di.trans.Trans(transMeta);
        trans.setLogLevel(LogLevel.DEBUG);
        if (StringUtils.isNotEmpty(logLevel)) {
            trans.setLogLevel(Constant.logger(logLevel));
        }
        String exception = null;
        Integer recordStatus = 1;
//            Date jobStartDate = null;
        Date transStopDate = null;
        String logText = null;
        try {
            trans.execute(null);
            trans.waitUntilFinished();
            transStopDate = new Date();
        } catch (Exception e) {
            exception = e.getMessage();
            recordStatus = 2;
        } finally {
            if (trans.isFinished()) {
                if (trans.getErrors() > 0) {
                    recordStatus = 2;
                    if(null == trans.getResult().getLogText() || "".equals(trans.getResult().getLogText())){
                        logText = exception;
                    }
                }
                // 写入作业执行结果
                StringBuilder allLogFilePath = new StringBuilder();
                allLogFilePath.append(logFilePath).append("/").append(userId).append("/")
                        .append(StringUtils.remove(transPath, "/")).append("@").append(transName).append("-log")
                        .append("/").append(new Date().getTime()).append(".").append("txt");
                String logChannelId = trans.getLogChannelId();
                LoggingBuffer appender = KettleLogStore.getAppender();
                logText = appender.getBuffer(logChannelId, true).toString();
                try {
                    TransRecord transRecord = new TransRecord();
                    transRecord.setRecordTrans(Integer.parseInt(transId));
                    transRecord.setCreateUser(Integer.parseInt(userId));
                    transRecord.setLogFilePath(allLogFilePath.toString());
                    transRecord.setRecordStatus(recordStatus);
                    transRecord.setStartTime(executeTime);
                    transRecord.setStopTime(transStopDate);
                    writeToDBAndFile(transRecord, logText, executeTime, nexExecuteTime);
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
     * @param transRecord        转换记录信息
     * @param logText            日志信息
     * @return void
     * @throws IOException
     * @throws SQLException
     * @Title writeToDBAndFile
     * @Description 保存转换运行日志信息到文件和数据库
     */
    public void writeToDBAndFile(TransRecord transRecord, String logText, Date lastExecuteTime, Date nextExecuteTime)
            throws IOException {
        // 将日志信息写入文件
        FileUtils.writeStringToFile(new File(transRecord.getLogFilePath()), logText, Constant.DEFAULT_ENCODING, false);
        // 写入转换运行记录到数据库
        transRecordDao.save(transRecord);
        JobMonitor template = new JobMonitor();
        template.setCreateUser(transRecord.getCreateUser());
        template.setMonitorJob(transRecord.getRecordTrans());
//        JobMonitor templateOne = sqlManager.templateOne(template);
        TransMonitor templateOne = transMonitorDao.findByMonitorTrans(transRecord.getRecordTrans());
        templateOne.setLastExecuteTime(lastExecuteTime);
        //在监控表中增加下一次执行时间
        templateOne.setNextExecuteTime(nextExecuteTime);
        if (transRecord.getRecordStatus() == 1) {// 证明成功
            //成功次数加1
            templateOne.setMonitorSuccess(templateOne.getMonitorSuccess() + 1);
            transMonitorDao.save(templateOne);
        } else if (transRecord.getRecordStatus() == 2) {// 证明失败
            //失败次数加1
            templateOne.setMonitorFail(templateOne.getMonitorFail() + 1);
            transMonitorDao.save(templateOne);
        }
    }

}
