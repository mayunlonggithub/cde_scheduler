package com.zjcds.cde.scheduler.service.Impl;

import com.zjcds.cde.scheduler.dao.jpa.CdmJobDao;
import com.zjcds.cde.scheduler.dao.jpa.JobDao;
import com.zjcds.cde.scheduler.dao.jpa.RepositoryDao;
import com.zjcds.cde.scheduler.domain.dto.CdmJobForm;
import com.zjcds.cde.scheduler.domain.entity.CdmJob;
import com.zjcds.cde.scheduler.domain.entity.Job;
import com.zjcds.cde.scheduler.domain.entity.Repository;
import com.zjcds.cde.scheduler.quartz.DBConnectionModel;
import com.zjcds.cde.scheduler.service.CdmJobService;
import com.zjcds.cde.scheduler.service.JobMonitorService;
import com.zjcds.cde.scheduler.service.JobService;
import com.zjcds.cde.scheduler.service.QuartzService;
import org.pentaho.di.core.exception.KettleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @author J on 20191118
 */
@Service
public class CdmJobServiceImpl implements CdmJobService {

    @Autowired
    private JobService jobService;
    @Autowired
    private CdmJobDao cdmJobDao;
    @Autowired
    private RepositoryDao repositoryDao;
    @Autowired
    JobMonitorService jobMonitorService;
    @Autowired
    private JobDao jobDao;
    @Autowired
    private QuartzService quartzService;
    @Value("${cde.log.file.path}")
    private String cdeLogFilePath1;


    @Override
    @Transactional
    public void cdmJobExecute(CdmJobForm.CdmJobParam cdmJobParam,Integer uId) throws KettleException, ParseException {
        Repository repository = repositoryDao.findByCreateUserAndDelFlag(uId,1).get(0);
//        DBConnectionModel dBConnectionModel = new DBConnectionModel(driverClassName,url,username,password);
        Job job = jobDao.findByJobNameAndDelFlagAndCreateUser(cdmJobParam.getJobName(),1,uId);
        String logLevel = job.getJobLogLevel();
        String logFilePath = cdeLogFilePath1;
        Date executeTime = new Date();
        Date nexExecuteTime = quartzService.getNextValidTime(executeTime,job.getJobQuartz());
        //添加监控
        jobMonitorService.addMonitor(uId,job.getJobId(),nexExecuteTime);
        jobService.manualRunRepositoryJob(repository,job.getJobId().toString(),job.getJobName(),job.getJobPath(),uId.toString(),logLevel,logFilePath,executeTime,nexExecuteTime,cdmJobParam.getParam(),null);
    }






//    @Override
//    @Transactional
//    public void cdmJobStop(CdmJobForm.CdmJobParam cdmJobParam,Integer uId) throws KettleException {
//        Repository repository = repositoryDao.findOne(1);
//        DBConnectionModel dBConnectionModel = new DBConnectionModel(driverClassName,url,username,password);
//        CdmJob cdmJob = cdmJobDao.findByJobName(cdmJobParam.getJobName());
//        String logLevel = "5";
//        String logFilePath = cdeLogFilePath1;
//        Date executeTime = null;
//        Date nexExecuteTime = null;
//        manualStopRepositoryJob(repository,dBConnectionModel,cdmJob.getId().toString(),cdmJob.getJobName(),cdmJob.getJobPath(),uId.toString(),logLevel,logFilePath,executeTime,nexExecuteTime,cdmJobParam.getParam());
//    }


//    /**
//     * 手动停止
//     * @param
//     * @throws KettleException
//     */
//    public void manualStopRepositoryJob(Repository repository, Object DbConnectionObject, String jobId, String jobName, String jobPath, String userId, String logLevel, String logFilePath, Date executeTime, Date nexExecuteTime, Map<String,String> param) throws KettleException {
//        KettleDatabaseRepository kettleDatabaseRepository  = init(repository);
//        RepositoryDirectoryInterface directory = kettleDatabaseRepository.loadRepositoryDirectoryTree()
//                .findDirectory(jobPath);
//        JobMeta jobMeta = kettleDatabaseRepository.loadJob(jobName,directory,new ProgressNullMonitorListener(),null);
//        if(param.size()>0){
//            for (String key : param.keySet()){
//                jobMeta.setParameterValue(key,param.get(key));
//            }
//        }
//        job = new Job(kettleDatabaseRepository, jobMeta);
//        job.setDaemon(true);
//        job.setLogLevel(LogLevel.DEBUG);
//        if (StringUtils.isNotEmpty(logLevel)) {
//            job.setLogLevel(Constant.logger(logLevel));
//        }
//        String exception = null;
//        Integer recordStatus = 1;
////            Date jobStartDate = null;
//        Date jobStopDate = null;
//        String logText = null;
//        try {
////                jobStartDate = new Date();
//            job.stopAll();
//            job.waitUntilFinished();
//            jobStopDate = new Date();
//        } catch (Exception e) {
//            exception = e.getMessage();
//            recordStatus = 2;
//        } finally {
//            if (job.isFinished()) {
//                if (job.getErrors() > 0) {
//                    recordStatus = 2;
//                    if(null == job.getResult().getLogText() || "".equals(job.getResult().getLogText())){
//                        logText = exception;
//                    }
//                }
//                // 写入作业执行结果
//                StringBuilder allLogFilePath = new StringBuilder();
//                allLogFilePath.append(logFilePath).append("/").append(userId).append("/")
//                        .append(StringUtils.remove(jobPath, "/")).append("@").append(jobName).append("-log")
//                        .append("/").append(new Date().getTime()).append(".").append("txt");
//                String logChannelId = job.getLogChannelId();
//                LoggingBuffer appender = KettleLogStore.getAppender();
//                logText = appender.getBuffer(logChannelId, true).toString();
////                try {
////                    JobRecord jobRecord = new JobRecord();
////                    jobRecord.setRecordJob(Integer.parseInt(jobId));
////                    jobRecord.setCreateUser(Integer.parseInt(userId));
////                    jobRecord.setLogFilePath(allLogFilePath.toString());
////                    jobRecord.setRecordStatus(recordStatus);
////                    jobRecord.setStartTime(executeTime);
////                    jobRecord.setStopTime(jobStopDate);
////                    jobQuartz.writeToDBAndFile(DbConnectionObject, jobRecord, logText, executeTime, nexExecuteTime);
////                } catch (IOException | SQLException e) {
////                    e.printStackTrace();
////                }
//            }
//        }
//    }



    @Override
    public List<CdmJob> cdmJobName(){
        List<CdmJob> cdmJobList = cdmJobDao.findAll();
        return cdmJobList;
    }
}
