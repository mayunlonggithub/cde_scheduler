package com.zjcds.cde.scheduler.service.Impl;

import com.zjcds.cde.scheduler.base.BeanPropertyCopyUtils;
import com.zjcds.cde.scheduler.base.PageResult;
import com.zjcds.cde.scheduler.base.Paging;
import com.zjcds.cde.scheduler.dao.jpa.*;
import com.zjcds.cde.scheduler.domain.dto.TaskForm;
import com.zjcds.cde.scheduler.domain.dto.TransForm;
import com.zjcds.cde.scheduler.domain.entity.*;
import com.zjcds.cde.scheduler.service.TransMonitorService;
import com.zjcds.cde.scheduler.service.TransService;
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
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryMeta;
import org.pentaho.di.trans.TransMeta;
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
//    @Autowired
//    private TaskService taskService;

    @Value("${cde.log.file.path}")
    private String cdeLogFilePath;
    @Value("${cde.file.repository}")
    private String cdeFileRepository;




    /**
     * @param uId   用户ID
     * @return BootTablePage
     * @Title getList
     * @Description 获取列表
     */
    @Override
    public PageResult<Trans> getList(Paging paging, List<String> queryString, List<String> orderBys, Integer uId) {
        Assert.notNull(uId,"未登录,请重新登录");
        queryString.add("createUser~Eq~"+uId);
        queryString.add("delFlag~Eq~1");
        PageResult<Trans> transPageResult = transDao.findAll(paging,queryString,orderBys);
        return transPageResult;
    }

    @Override
    public List<Trans> getList(Integer uId) {
        Assert.notNull(uId,"未登录,请重新登录");
        List<Trans> transList = transDao.findByCreateUserAndDelFlag(uId,1);
        return transList;
    }

    /**
     * @param transId 转换ID
     * @return void
     * @Title delete
     * @Description 删除转换
     */
    @Override
    @Transactional
    public void delete(Integer transId,Integer uId) {
        Assert.notNull(uId,"未登录,请重新登录");
        Assert.notNull(transId,"要删除的转换id不能为空");
        Trans trans = transDao.findByTransIdAndDelFlag(transId,uId);
        Assert.notNull(trans,"要删除的转换不存在或已删除");
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
        Assert.notNull(uId,"未登录,请重新登录");
        Assert.notNull(repositoryId,"资源库id不能为空");
        Assert.hasText(kTransPath,"转换路径信息不能为空");
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
        Assert.notNull(uId,"未登录,请重新登录");
        //补充添加作业信息
        //作业基础信息
        Trans trans = BeanPropertyCopyUtils.copy(addTrans,Trans.class);
        boolean status = check(trans.getTransRepositoryId(),trans.getTransPath(),uId);
        Assert.isTrue(status,"该转换已存在");
        //作业是否被删除
        trans.setDelFlag(1);
        //作业是否启动
        trans.setTransStatus(2);
        trans.setCreateUser(uId);
        trans.setModifyUser(uId);
        transDao.save(trans);
    }

    /**
     * @param transId 转换ID
     * @return KTrans
     * @Title getTrans
     * @Description 获取转换对象
     */
    @Override
    public Trans getTrans(Integer transId,Integer uId) {
        Assert.notNull(uId,"未登录,请重新登录");
        Assert.notNull(transId,"要查询的transId不能为空");
        return transDao.findByTransId(transId);
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
    public void update(TransForm.UpdateTrans updateTrans, Integer transId,Integer uId) {
        Assert.notNull(uId,"未登录,请重新登录");
        Assert.notNull(transId,"要更新的jobId不能为空");
        Trans t = transDao.findByTransIdAndDelFlag(transId,1);
        Assert.notNull(t,"要修改的作业不存在或已删除");
        Trans trans = BeanPropertyCopyUtils.copy(updateTrans,Trans.class);
        trans.setModifyUser(uId);
        trans.setDelFlag(t.getDelFlag());
        trans.setCreateUser(t.getCreateUser());
        trans.setCreateTime(t.getCreateTime());
        transDao.save(trans);
        if(trans.getTransQuartz()!=null){
            TaskForm.AddTask addTask = new TaskForm.AddTask();
            addTask.setJobId(trans.getTransId());
            addTask.setQuartzId(trans.getTransQuartz());
            addTask.setTaskName(trans.getTransName());
            addTask.setTaskGroup("trans");
            addTask.setTaskDescription(trans.getTransDescription());
//            taskService.addTask(addTask,uId);
        }
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
        Assert.notNull(uId,"未登录,请重新登录");
        Assert.notNull(transId,"要启动的作业id不能为空");
        Trans trans = transDao.findByTransId(transId);
        Repository repository = repositoryDao.findByRepositoryId(trans.getTransRepositoryId());
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
        Assert.notNull(userId,"未登录,请重新登录");
        KettleDatabaseRepository kettleDatabaseRepository  = init(repository);
        RepositoryDirectoryInterface directory = kettleDatabaseRepository.loadRepositoryDirectoryTree()
                .findDirectory(transPath);
        TransMeta transMeta = kettleDatabaseRepository.loadTransformation(transName,directory,new ProgressNullMonitorListener(),true,null);
        if(param!=null&&param.size()>0){
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
        Integer recordStatus = 2;
//            Date jobStartDate = null;
        Date transStopDate = null;
        String logText = null;
        try {
            trans.execute(null);
            trans.waitUntilFinished();
            transStopDate = new Date();
        } catch (Exception e) {
            exception = e.getMessage();
            recordStatus = 3;
        } finally {
            if (trans.isFinished()) {
                if (trans.getErrors() > 0) {
                    recordStatus = 3;
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
        if (transRecord.getRecordStatus() == 2) {// 证明成功
            //成功次数加1
            templateOne.setMonitorSuccess(templateOne.getMonitorSuccess() + 1);
            transMonitorDao.save(templateOne);
        } else if (transRecord.getRecordStatus() == 3) {// 证明失败
            //失败次数加1
            templateOne.setMonitorFail(templateOne.getMonitorFail() + 1);
            transMonitorDao.save(templateOne);
        }
    }


    /**
     * 查询所有转换名称Map
     * @return
     */
    @Override
    public Map<Integer,String> transNameMap(){
        List<Trans> transList = transDao.findByDelFlag(1);
        Map<Integer,String> transNameMap = new HashMap<>();
        if(transList.size()>0&&transList!=null){
            for (Trans t:transList){
                Map<Integer,String> map = new HashMap<>();
                map.put(t.getTransId(),t.getTransName());
                transNameMap.putAll(map);
            }
        }
        return transNameMap;
    }
}
