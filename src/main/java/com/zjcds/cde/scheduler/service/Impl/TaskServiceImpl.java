package com.zjcds.cde.scheduler.service.Impl;

import com.zjcds.cde.scheduler.Task.DynamicTask;
import com.zjcds.cde.scheduler.dao.jpa.QuartzDao;
import com.zjcds.cde.scheduler.dao.jpa.TaskDao;
import com.zjcds.cde.scheduler.domain.dto.TaskForm;
import com.zjcds.cde.scheduler.domain.entity.Quartz;
import com.zjcds.cde.scheduler.domain.entity.Task;
import com.zjcds.cde.scheduler.service.TaskService;
import com.zjcds.cde.scheduler.utils.Constant;
import com.zjcds.common.base.domain.page.Paging;
import com.zjcds.common.dozer.BeanPropertyCopyUtils;
import com.zjcds.common.jpa.PageResult;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;
    @Autowired
    private TaskDao taskDao;
    @Autowired
    private QuartzDao quartzDao;

    private static Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);
    @Override
    @Transactional
    public void addTask(TaskForm.AddTask addTask,Integer uId){
        Task task= BeanPropertyCopyUtils.copy(addTask,Task.class);
        Quartz quartz=quartzDao.findOne(task.getQuartzId());
        task.setStartTime(quartz.getStartTime());
        task.setEndTime(quartz.getEndTime());
        task.setUserId(uId);
        task.setStatus(Constant.IMPLEMENT);
        task.setQuartzDesc(quartz.getQuartzDescription());
        taskDao.save(task);
        runTask(task);
    }

    @Override
    @Transactional
    public void deleteTask(Integer taskId){
        shutdown(taskId);
        Task task = taskDao.findOne(taskId);
        task.setStatus(Constant.INVALID);
        taskDao.save(task);

    }

    @Override
    public PageResult<Task> getList(Paging  paging, List<String> queryString, List<String> orderBys, Integer uId) {
        queryString.add("createUser~Eq~"+uId);
        queryString.add("status~lt~"+Constant.INVALID);
        PageResult<Task> task = taskDao.findAll(paging,queryString,orderBys);
        return task;
    }

    //获取JobDataMap.(Job参数对象)
    public JobDataMap getJobDataMap(Task task) {
        JobDataMap map = new JobDataMap();
        map.put("taskId",task.getTaskId());
        map.put("jobId",task.getJobId());
        map.put("quartzId",task.getQuartzId());
        map.put("quartzDesc",task.getQuartzDesc());
        map.put("taskName",task.getTaskName());
        map.put("taskGroup",task.getTaskGroup());
        map.put("startTime",task.getStartTime());
        map.put("endTime",task.getEndTime());
        map.put("status",task.getStatus());
        map.put("userId",task.getUserId());
        map.put("param",task.getParam());
        map.put("createUser",task.getCreateUser());
        map.put("modifyUser",task.getModifyUser());
        return map;
    }

    //获取JobDetail,JobDetail是任务的定义,而Job是任务的执行逻辑,JobDetail里会引用一个Job Class来定义
    public JobDetail getTaskDetail(JobKey jobKey, String description, JobDataMap map) {
        return JobBuilder.newJob(DynamicTask.class)
                .withIdentity(jobKey)
                .withDescription(description)
                .setJobData(map)
                .storeDurably()
                .build();
    }

    //获取Trigger (Job的触发器,执行规则)
    public Trigger getTrigger(Task task) {
        return TriggerBuilder.newTrigger()
                .withIdentity(task.getTaskName(),task.getTaskGroup())
                .withSchedule(CronScheduleBuilder.cronSchedule(quartzDao.findOne(task.getQuartzId()).getQuartzCron()))
                .build();
    }

    //获取JobKey,包含Name和Group
    public JobKey getTaskKey(Task task) {
        return JobKey.jobKey(task.getTaskName(), task.getTaskGroup());
    }


    @Override
    public void restartAllJobs() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        Set<JobKey> set = scheduler.getJobKeys(GroupMatcher.anyGroup());
        for (JobKey jobKey : set) {
            scheduler.deleteJob(jobKey);
        }
        List<Task> list = taskDao.findByStatus(Constant.IMPLEMENT);
        for (Task task : list) {
            logger.info("Job register name : {} , cron : {}", task.getTaskName(),task.getTaskGroup());
            JobDataMap map = getJobDataMap(task);
            JobKey jobKey = getTaskKey(task);
            JobDetail jobDetail = getTaskDetail(jobKey, task.getTaskDescription(), map);
            scheduler.scheduleJob(jobDetail, getTrigger(task));
        }
    }

    @Override
    public void runTask(Task task) {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobDataMap map = getJobDataMap(task);
        JobKey jobKey = getTaskKey(task);
        JobDetail jobDetail = getTaskDetail(jobKey,task.getTaskDescription(), map);
        try {
            scheduler.scheduleJob(jobDetail, getTrigger(task));
        } catch (SchedulerException e) {
            logger.error(e.toString());
        }
    }
    @Override
    public void shutdown(Integer taskId) {
        Task task = taskDao.findOne(taskId);
        TriggerKey triggerKey = new TriggerKey(task.getTaskGroup() + task.getTaskName());
        JobKey jobKey = getTaskKey(task);
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        try {
            scheduler.unscheduleJob(triggerKey);
            scheduler.deleteJob(jobKey);
        }catch (SchedulerException e) {
            logger.error(e.toString());
        }
    }
}
