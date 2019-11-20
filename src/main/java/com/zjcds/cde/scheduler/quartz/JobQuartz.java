//package com.zjcds.cde.scheduler.quartz;
//
//import com.zjcds.cde.scheduler.dao.jpa.JobMonitorDao;
//import com.zjcds.cde.scheduler.dao.jpa.JobRecordDao;
//import com.zjcds.cde.scheduler.domain.entity.JobMonitor;
//import com.zjcds.cde.scheduler.domain.entity.JobRecord;
//import com.zjcds.cde.scheduler.domain.entity.Repository;
//import com.zjcds.cde.scheduler.utils.Constant;
//import com.zjcds.cde.scheduler.utils.RepositoryUtil;
//import org.apache.commons.io.FileUtils;
//import org.apache.commons.lang.StringUtils;
//import org.beetl.sql.core.*;
//import org.beetl.sql.core.db.DBStyle;
//import org.beetl.sql.core.db.MySqlStyle;
//import org.beetl.sql.ext.DebugInterceptor;
//import org.pentaho.di.core.KettleEnvironment;
//import org.pentaho.di.core.ProgressNullMonitorListener;
//import org.pentaho.di.core.database.DatabaseMeta;
//import org.pentaho.di.core.exception.KettleException;
//import org.pentaho.di.core.exception.KettleMissingPluginsException;
//import org.pentaho.di.core.exception.KettleXMLException;
//import org.pentaho.di.core.logging.KettleLogStore;
//import org.pentaho.di.core.logging.LogLevel;
//import org.pentaho.di.core.logging.LoggingBuffer;
//import org.pentaho.di.job.Job;
//import org.pentaho.di.job.JobMeta;
//import org.pentaho.di.repository.RepositoryDirectoryInterface;
//import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
//import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryMeta;
//import org.quartz.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.io.File;
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.Date;
//
///**
// * @author J
// */
//@Service
//public class JobQuartz implements InterruptableJob {
////    private org.pentaho.di.job.Job job;
//
//    @Autowired
//    private JobRecordDao jobRecordDao;
//    @Autowired
//    private JobMonitorDao jobMonitorDao;
//
////    public void execute(JobExecutionContext context) throws JobExecutionException {
////
////        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
////        Object KRepositoryObject = jobDataMap.get(Constant.REPOSITORYOBJECT);
////        Object DbConnectionObject = jobDataMap.get(Constant.DBCONNECTIONOBJECT);
////        String jobName_str = context.getJobDetail().getKey().getName();
////        String[] names = jobName_str.split("@");
////        String jobId = String.valueOf(jobDataMap.get(Constant.JOBID));
////        String jobPath = String.valueOf(jobDataMap.get(Constant.JOBPATH));
////        String jobName = String.valueOf(jobDataMap.get(Constant.JOBNAME));
////        String userId = String.valueOf(jobDataMap.get(Constant.USERID));
////        String logLevel = String.valueOf(jobDataMap.get(Constant.LOGLEVEL));
////        String logFilePath = String.valueOf(jobDataMap.get(Constant.LOGFILEPATH));
////        Date lastExecuteTime = context.getFireTime();
////        Date nexExecuteTime = context.getNextFireTime();
////
////        if (null != DbConnectionObject && DbConnectionObject instanceof DBConnectionModel) {// 首先判断数据库连接对象是否正确
////            // 判断作业类型
////            if (null != KRepositoryObject && KRepositoryObject instanceof Repository) {// 证明该作业是从资源库中获取到的
////                try {
////                    runRepositoryJob(KRepositoryObject, DbConnectionObject, jobId, jobPath, jobName, userId, logLevel,
////                            logFilePath, lastExecuteTime, nexExecuteTime);
////                } catch (KettleException e) {
////                    e.printStackTrace();
////                }
////            } else {
////                try {
////                    runFileJob(DbConnectionObject, jobId, jobPath, jobName, userId, logLevel, logFilePath, lastExecuteTime, nexExecuteTime);
////                } catch (KettleXMLException | KettleMissingPluginsException e) {
////                    e.printStackTrace();
////                }
////            }
////        }
////    }
//
////    /**
////     * @param KRepositoryObject 数据库连接对象
////     * @param KRepositoryObject 资源库对象
////     * @param jobId             作业ID
////     * @param jobPath           作业在资源库中的路径信息
////     * @param jobName           作业名称
////     * @param userId            作业归属者ID
////     * @param logLevel          作业的日志等级
////     * @param logFilePath       作业日志保存的根路径
////     * @return void
////     * @throws KettleException
////     * @Title runRepositoryJob
////     * @Description 运行资源库中的作业
////     */
////    public void runRepositoryJob(Object KRepositoryObject, Object DbConnectionObject, String jobId,
////                                 String jobPath, String jobName, String userId, String logLevel, String logFilePath, Date executeTime, Date nexExecuteTime) throws KettleException {
////        Repository kRepository = (Repository) KRepositoryObject;
////        Integer repositoryId = kRepository.getRepositoryId();
////        KettleDatabaseRepository kettleDatabaseRepository = null;
////        if (RepositoryUtil.KettleDatabaseRepositoryCatch.containsKey(repositoryId)) {
////            kettleDatabaseRepository = RepositoryUtil.KettleDatabaseRepositoryCatch.get(repositoryId);
////        } else {
////            kettleDatabaseRepository = RepositoryUtil.connectionRepository(kRepository);
////        }
////        if (null != kettleDatabaseRepository) {
////            RepositoryDirectoryInterface directory = kettleDatabaseRepository.loadRepositoryDirectoryTree()
////                    .findDirectory(jobPath);
////            JobMeta jobMeta = kettleDatabaseRepository.loadJob(jobName, directory, new ProgressNullMonitorListener(),
////                    null);
////            job = new org.pentaho.di.job.Job(kettleDatabaseRepository, jobMeta);
////            job.setDaemon(true);
////            job.setLogLevel(LogLevel.DEBUG);
////            if (StringUtils.isNotEmpty(logLevel)) {
////                job.setLogLevel(Constant.logger(logLevel));
////            }
////            String exception = null;
////            Integer recordStatus = 1;
//////            Date jobStartDate = null;
////            Date jobStopDate = null;
////            String logText = null;
////            try {
//////                jobStartDate = new Date();
////                job.run();
////                job.waitUntilFinished();
////                jobStopDate = new Date();
////            } catch (Exception e) {
////                exception = e.getMessage();
////                recordStatus = 2;
////            } finally {
////                if (job.isFinished()) {
////                    if (job.getErrors() > 0) {
////                        recordStatus = 2;
////                        if(null == job.getResult().getLogText() || "".equals(job.getResult().getLogText())){
////                            logText = exception;
////                        }
////                    }
////                    // 写入作业执行结果
////                    StringBuilder allLogFilePath = new StringBuilder();
////                    allLogFilePath.append(logFilePath).append("/").append(userId).append("/")
////                            .append(StringUtils.remove(jobPath, "/")).append("@").append(jobName).append("-log")
////                            .append("/").append(new Date().getTime()).append(".").append("txt");
////                    String logChannelId = job.getLogChannelId();
////                    LoggingBuffer appender = KettleLogStore.getAppender();
////                    logText = appender.getBuffer(logChannelId, true).toString();
////                    try {
////                        JobRecord jobRecord = new JobRecord();
////                        jobRecord.setRecordJob(Integer.parseInt(jobId));
////                        jobRecord.setCreateUser(Integer.parseInt(userId));
////                        jobRecord.setLogFilePath(allLogFilePath.toString());
////                        jobRecord.setRecordStatus(recordStatus);
////                        jobRecord.setStartTime(executeTime);
////                        jobRecord.setStopTime(jobStopDate);
////                        writeToDBAndFile(DbConnectionObject, jobRecord, logText, executeTime, nexExecuteTime);
////                    } catch (IOException | SQLException e) {
////                        e.printStackTrace();
////                    }
////                }
////            }
////        }
////    }
//
////    public void runFileJob(Object DbConnectionObject, String jobId, String jobPath, String jobName,
////                           String userId, String logLevel, String logFilePath, Date lastExecuteTime, Date nexExecuteTime) throws KettleXMLException, KettleMissingPluginsException {
////        JobMeta jobMeta = new JobMeta(jobPath, null);
////        job = new org.pentaho.di.job.Job(null, jobMeta);
////        job.setDaemon(true);
////        job.setLogLevel(LogLevel.DEBUG);
////        if (StringUtils.isNotEmpty(logLevel)) {
////            job.setLogLevel(Constant.logger(logLevel));
////        }
////        String exception = null;
////        Integer recordStatus = 1;
////        Date jobStartDate = null;
////        Date jobStopDate = null;
////        String logText = null;
////        try {
////            jobStartDate = new Date();
////            job.run();
////            job.waitUntilFinished();
////            jobStopDate = new Date();
////        } catch (Exception e) {
////            exception = e.getMessage();
////            recordStatus = 2;
////        } finally {
////            if (null != job && job.isFinished()) {
////                if (job.getErrors() > 0
////                        && (null == job.getResult().getLogText() || "".equals(job.getResult().getLogText()))) {
////                    logText = exception;
////                }
////                // 写入作业执行结果
////                StringBuilder allLogFilePath = new StringBuilder();
////                allLogFilePath.append(logFilePath).append("/").append(userId).append("/")
////                        .append(StringUtils.remove(jobPath, "/")).append("@").append(jobName).append("-log").append("/")
////                        .append(new Date().getTime()).append(".").append("txt");
////                String logChannelId = job.getLogChannelId();
////                LoggingBuffer appender = KettleLogStore.getAppender();
////                logText = appender.getBuffer(logChannelId, true).toString();
////                try {
////                    JobRecord kJobRecord = new JobRecord();
////                    kJobRecord.setRecordJob(Integer.parseInt(jobId));
////                    kJobRecord.setCreateUser(Integer.parseInt(userId));
////                    kJobRecord.setLogFilePath(allLogFilePath.toString());
////                    kJobRecord.setRecordStatus(recordStatus);
////                    kJobRecord.setStartTime(jobStartDate);
////                    kJobRecord.setStopTime(jobStopDate);
////                    writeToDBAndFile(DbConnectionObject, kJobRecord, logText, lastExecuteTime, nexExecuteTime);
////                } catch (IOException | SQLException e) {
////                    e.printStackTrace();
////                }
////            }
////        }
////    }
//
////    /**
////     * @param DbConnectionObject 数据库连接对象
////     * @param kJobRecord         作业记录信息
////     * @param logText            日志信息
////     * @return void
////     * @throws IOException
////     * @throws SQLException
////     * @Title writeToDBAndFile
////     * @Description 保存作业运行日志信息到文件和数据库
////     */
////    public void writeToDBAndFile(Object DbConnectionObject, JobRecord jobRecord, String logText, Date lastExecuteTime, Date nextExecuteTime)
////            throws IOException, SQLException {
////        // 将日志信息写入文件
////        FileUtils.writeStringToFile(new File(jobRecord.getLogFilePath()), logText, Constant.DEFAULT_ENCODING, false);
////        // 写入转换运行记录到数据库
////        DSTransactionManager.start();
////        jobRecordDao.save(jobRecord);
////        JobMonitor template = new JobMonitor();
////        template.setCreateUser(jobRecord.getCreateUser());
////        template.setMonitorJob(jobRecord.getRecordJob());
//////        JobMonitor templateOne = sqlManager.templateOne(template);
////        JobMonitor templateOne = jobMonitorDao.findByCreateUserAndMonitorJob(jobRecord.getCreateUser(),jobRecord.getRecordJob());
////        templateOne.setLastExecuteTime(lastExecuteTime);
////        //在监控表中增加下一次执行时间
////        templateOne.setNextExecuteTime(nextExecuteTime);
////        if (jobRecord.getRecordStatus() == 1) {// 证明成功
////            //成功次数加1
////            templateOne.setMonitorSuccess(templateOne.getMonitorSuccess() + 1);
////            jobMonitorDao.save(templateOne);
////        } else if (jobRecord.getRecordStatus() == 2) {// 证明失败
////            //失败次数加1
////            templateOne.setMonitorFail(templateOne.getMonitorFail() + 1);
////            jobMonitorDao.save(templateOne);
////        }
////        DSTransactionManager.commit();
////    }
//
////    @Override
////    public void interrupt() throws UnableToInterruptJobException {
////        //stop the running job
////        this.job.stopAll();
////    }
//
//
//}
