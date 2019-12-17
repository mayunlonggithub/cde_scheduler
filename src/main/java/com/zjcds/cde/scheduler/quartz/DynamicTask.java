package com.zjcds.cde.scheduler.quartz;

import com.alibaba.fastjson.JSON;
import com.zjcds.cde.scheduler.dao.jpa.TaskDao;
import com.zjcds.cde.scheduler.domain.entity.Task;
import com.zjcds.cde.scheduler.service.JobService;
import com.zjcds.cde.scheduler.service.TransService;
import com.zjcds.cde.scheduler.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@DisallowConcurrentExecution
@Component
@Slf4j
public class DynamicTask implements Job {
    @Autowired
    private TaskDao taskDao;
    @Autowired
    private JobService jobService;
    @Autowired
    private TransService transService;
    private Logger logger = LoggerFactory.getLogger(DynamicTask.class);

    /**
     * 核心方法,Quartz Job真正的执行逻辑.
     *
     * @param executorContext executorContext JobExecutionContext中封装有Quartz运行所需要的所有信息
     * @throws JobExecutionException execute()方法只允许抛出JobExecutionException异常
     */
    @Override
    public void execute(JobExecutionContext executorContext) throws JobExecutionException {
        long ExeStartTime = System.currentTimeMillis();
        //JobDetail中的JobDataMap是共用的,从getMergedJobDataMap获取的JobDataMap是全新的对象
        JobDataMap map = executorContext.getMergedJobDataMap();
        Integer taskId = map.getInt("taskId");
        Integer jobId = map.getInt("jobId");
        Integer uId = map.getInt("userId");
        String groupName = map.getString("taskGroup");
        String param = map.getString("param");
        Map<String, String> paramMap = (Map) JSON.parse(param);
        logger.info("Running Job ID : {} ", map.getInt("jobId"));
        logger.info("Running Quartz ID: {}" , map.getInt("quartzId"));
        logger.info("Running Quartz Description : {}" , map.getString("quartzDesc"));
        logger.info("Running Task Name : {} ", groupName);
        logger.info("Running Task Group: {} ", map.getString("taskGroup"));
        logger.info("Running User ID: {} ", map.getInt("userId"));
        logger.info("Running Parameter: {} ", param);
        try {
            Task task = taskDao.findByTaskId(taskId);
            Trigger trigger = executorContext.getTrigger();
            Scheduler scheduler = executorContext.getScheduler();
            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
            if (triggerState.equals(Trigger.TriggerState.COMPLETE)) {
                task.setStatus(Constant.COMPLETION);
                taskDao.save(task);
                log.info(">>>>>>>>>>>>>Trigger has been completed>>>>>>>>>>>>>");
            }
            if("job".equals(groupName)) {
                jobService.start(jobId, uId, paramMap);
            }else if("trans".equals(groupName)) {
                transService.start(jobId, uId, paramMap);
            }
        } catch(Exception e){
                e.printStackTrace();
                logger.error("调起任务有错误", e);
            }
        long ExeEndTime = System.currentTimeMillis();
        logger.info(">>>>>>>>>>>>> Running Job has been completed , cost time :  " + (ExeEndTime -ExeStartTime) + "ms\n");
    }
        }


