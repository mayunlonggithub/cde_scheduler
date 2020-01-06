package com.zjcds.cde.scheduler.service.Impl;

import com.zjcds.cde.scheduler.base.BeanPropertyCopyUtils;
import com.zjcds.cde.scheduler.base.PageResult;
import com.zjcds.cde.scheduler.base.Paging;
import com.zjcds.cde.scheduler.dao.jpa.QuartzDao;
import com.zjcds.cde.scheduler.dao.jpa.TaskDao;
import com.zjcds.cde.scheduler.dao.jpa.view.JobTransViewDao;
import com.zjcds.cde.scheduler.domain.dto.TaskForm;
import com.zjcds.cde.scheduler.domain.entity.Quartz;
import com.zjcds.cde.scheduler.domain.entity.Task;
import com.zjcds.cde.scheduler.domain.entity.view.JobTransView;
import com.zjcds.cde.scheduler.quartz.DynamicTask;
import com.zjcds.cde.scheduler.service.JobMonitorService;
import com.zjcds.cde.scheduler.service.JobService;
import com.zjcds.cde.scheduler.service.TaskService;
import com.zjcds.cde.scheduler.service.TransMonitorService;
import com.zjcds.cde.scheduler.service.TransService;
import com.zjcds.cde.scheduler.utils.Constant;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Ma on 20191124
 */
@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskDao taskDao;
    @Autowired
    private QuartzDao quartzDao;
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;
    @Autowired
    private JobService jobService;
    @Autowired
    private TransService transService;
    @Autowired
    private JobTransViewDao jobTransViewDao;
    @Autowired
    private JobMonitorService jobMonitorService;
    @Autowired
    private TransMonitorService transMonitorService;

    private static Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Override
    @Transactional
    public void addTask(TaskForm.AddTask addTask, Integer uId, Integer jobId) throws ParseException {
        Task task = BeanPropertyCopyUtils.copy(addTask, Task.class);
        Quartz quartz = quartzDao.findByQuartzId(task.getQuartzId());
        quartz.setAssTaskFlag(1);
        quartzDao.save(quartz);
        task.setStartTime(quartz.getStartTime());
        task.setEndTime(quartz.getEndTime());
        task.setUserId(uId);
//        Job job= jobDao.findByJobId(jobId);
        Date date = new Date();
        if (date.after(quartz.getEndTime())) {
            task.setStatus(Constant.COMPLETION);
            jobMonitorService.addMonitor(uId,jobId,null,0,1);
        } else {
            task.setStatus(Constant.VALID);
        }
        task.setDelFlag(1);
        task.setQuartzDesc(quartz.getQuartzDescription());
        taskDao.save(task);
        runTask(task.getTaskId());

    }

    @Override
    @Transactional
    public void deleteTask(Integer taskId,Integer uId) {
        Task task = taskDao.findByTaskId(taskId);
        Integer quartzId = task.getQuartzId();
        Integer jobId=task.getJobId();
        Quartz quartz = quartzDao.findByQuartzId(quartzId);
        shutDown(taskId);
        task.setDelFlag(0);
        Integer[] staArray = {Constant.VALID, Constant.COMPLETION};
        List<Task> list = taskDao.findByQuartzIdAndStatusIn(quartzId, staArray);
        if (list.size() == 0) {
            quartz.setAssTaskFlag(0);
        }
        taskDao.save(task);
        if ("trans".equals(task.getTaskGroup())) {
            transService.updateTransQuartz(task.getJobId(), null);
            transMonitorService.addMonitor(uId,jobId,null,0,1);
        } else if ("job".equals(task.getTaskGroup())) {
            jobService.updateJobQuartz(task.getJobId(), null);
            jobMonitorService.addMonitor(uId,jobId,null,0,1);
        }


    }

    @Override
    @Transactional
    public void deleteTask(Integer jobId, String taskGroup,Integer uId) {
        Integer[] status = {Constant.VALID, Constant.COMPLETION};
        Task task = taskDao.findByJobIdAndTaskGroupAndStatusInAndDelFlag(jobId, taskGroup, status, 1);
        Integer quartzId = task.getQuartzId();
        Integer taskId = task.getTaskId();
        shutDown(taskId);
        task.setDelFlag(0);
        taskDao.save(task);
        Quartz quartz = quartzDao.findByQuartzId(quartzId);
        List<Task> list = taskDao.findByQuartzIdAndStatusIn(quartzId, status);
        if (list.size() == 0) {
            quartz.setAssTaskFlag(0);
        }
        jobMonitorService.addMonitor(uId,jobId,null,0,1);
    }

    @Override
    public PageResult<JobTransView> getList(Paging paging, List<String> queryString, List<String> orderBys, Integer uId, Integer quartzId) {
        queryString.add("createUser~Eq~" + uId);
        queryString.add("delFlag~eq~1");
        queryString.add("jobQuartz~Eq~" + quartzId);
        PageResult<JobTransView> jobTransView = jobTransViewDao.findAll(paging, queryString, orderBys);
        return jobTransView;
    }

    //获取JobDataMap.(Job参数对象)
    public JobDataMap getJobDataMap(Task task) {
        JobDataMap map = new JobDataMap();
        map.put("taskId", task.getTaskId());
        map.put("jobId", task.getJobId());
        map.put("quartzId", task.getQuartzId());
        map.put("quartzDesc", task.getQuartzDesc());
        map.put("taskName", task.getTaskName());
        map.put("taskGroup", task.getTaskGroup());
        map.put("status", task.getStatus());
        map.put("userId", task.getUserId());
        map.put("param", task.getParam());
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
                .withIdentity(task.getTaskName(), task.getTaskGroup())
                .withSchedule(CronScheduleBuilder.cronSchedule(quartzDao.findByQuartzId(task.getQuartzId()).getQuartzCron()))
                .startAt(task.getStartTime())
                .endAt(task.getEndTime())
                .build();
    }

    //获取JobKey,包含Name和Group
    public JobKey getTaskKey(Task task) {
        return JobKey.jobKey(task.getTaskName(), task.getTaskGroup());
    }

    @Override
    public void restartAllTasks() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        Set<JobKey> set = scheduler.getJobKeys(GroupMatcher.anyGroup());
        for (JobKey jobKey : set) {
            scheduler.unscheduleJob(TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup()));
            scheduler.deleteJob(jobKey);
        }
        List<Task> list = taskDao.findByStatus(Constant.VALID);
        for (Task task : list) {
            logger.info("Job register name : {} , cron : {}", task.getTaskName(), task.getTaskGroup());
            JobDataMap map = getJobDataMap(task);
            JobKey jobKey = getTaskKey(task);
            JobDetail jobDetail = getTaskDetail(jobKey, task.getTaskDesc(), map);
            scheduler.scheduleJob(jobDetail, getTrigger(task));
        }
    }

    @Override
    public void runTask(Integer taskId) {
        Task task = taskDao.findByTaskId(taskId);
        task.setStatus(Constant.VALID);
        taskDao.save(task);
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobDataMap map = getJobDataMap(task);
        JobKey jobKey = getTaskKey(task);

        JobDetail jobDetail = getTaskDetail(jobKey, task.getTaskDesc(), map);
        try {
            scheduler.scheduleJob(jobDetail, getTrigger(task));
        } catch (SchedulerException e) {
            logger.error(e.toString());
        }
    }

    @Override
    public void shutDown(Integer taskId) {
        Task task = taskDao.findByTaskId(taskId);
        task.setStatus(Constant.INVALID);
        taskDao.save(task);
        JobKey jobKey = getTaskKey(task);
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        try {
            scheduler.unscheduleJob(TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup()));
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            logger.error(e.toString());
        }
    }
//
//    @Override
//    public void startAllTasks() {
//        List<Task> list = taskDao.findByStatus(Constant.VALID);
//        Scheduler scheduler = schedulerFactoryBean.getScheduler();
//        for (Task task : list) {
//            JobDataMap map = getJobDataMap(task);
//            JobKey jobKey = getTaskKey(task);
//            JobDetail jobDetail = getTaskDetail(jobKey, task.getTaskDesc(), map);
//            task.setStatus(Constant.VALID);
//            taskDao.save(task);
//            try {
//            scheduler.scheduleJob(jobDetail, getTrigger(task));
//            }catch (SchedulerException e) {
//                logger.error(e.toString());
//            }
//            logger.info("Job register name : {} , cron : {}", task.getTaskName(),task.getTaskGroup());
//        }
//    }
//
//    @Override
//    public void shutDownAllTasks( ){
//        List<Task> list = taskDao.findByStatus(Constant.VALID);
//        Scheduler scheduler = schedulerFactoryBean.getScheduler();
//        for (Task task : list) {
//            TriggerKey triggerKey = new TriggerKey(task.getTaskGroup() + task.getTaskName());
//            JobKey jobKey = getTaskKey(task);
//            task.setStatus(Constant.INVALID);
//            taskDao.save(task);
//            try {
//                scheduler.unscheduleJob(triggerKey);
//                scheduler.deleteJob(jobKey);
//            }catch (SchedulerException e) {
//            logger.error(e.toString());
//        }
//            logger.info("Job Pause name : {} , cron : {}", task.getTaskName(),task.getTaskGroup());
//        }
//    }
}
