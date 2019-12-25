package com.zjcds.cde.scheduler.service.Impl;

import com.zjcds.cde.scheduler.base.BeanPropertyCopyUtils;
import com.zjcds.cde.scheduler.base.PageResult;
import com.zjcds.cde.scheduler.base.Paging;
import com.zjcds.cde.scheduler.dao.jpa.QuartzDao;
import com.zjcds.cde.scheduler.dao.jpa.TaskDao;
import com.zjcds.cde.scheduler.domain.dto.QuartzForm;
import com.zjcds.cde.scheduler.domain.dto.TaskForm;
import com.zjcds.cde.scheduler.domain.entity.Quartz;
import com.zjcds.cde.scheduler.domain.entity.Task;
import com.zjcds.cde.scheduler.service.JobService;
import com.zjcds.cde.scheduler.service.QuartzService;
import com.zjcds.cde.scheduler.service.TaskService;
import com.zjcds.cde.scheduler.service.TransService;
import com.zjcds.cde.scheduler.utils.Constant;
import com.zjcds.cde.scheduler.utils.CronUtils;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @author Ma on 20191122
 */
@Service
@Transactional
public class QuartzServiceImpl implements QuartzService {
    @Autowired
    private QuartzDao quartzDao;
    @Autowired
    private TaskDao  taskDao;
    @Autowired
    private TransService transService;
    @Autowired
    private JobService jobService;
    @Autowired
    private TaskService taskService;
    @Override
    @Transactional
    public void addQuartz(QuartzForm.AddQuartz addQuartz) {
        if (addQuartz.getQuartzCron() == null) {
            List<String> cron = CronUtils.createQuartzCronressionAndDescription(addQuartz);
            addQuartz.setQuartzCron(cron.get(0));
            addQuartz.setQuartzDescription(cron.get(1));
        }
        Quartz quartz = BeanPropertyCopyUtils.copy(addQuartz, Quartz.class);
        quartz.setDelFlag(1);
        quartzDao.save(quartz);
    }

    @Override
    @Transactional
    public void deleteQuartz(Integer quartzId) {
        Quartz quartz = quartzDao.findByQuartzId(quartzId);
        quartz.setDelFlag(0);
        quartz.setAssTaskFlag(0);
        quartzDao.save(quartz);
        Integer[] staArray={Constant.COMPLETION,Constant.VALID};
        List<Task>  taskList=taskDao.findByQuartzIdAndStatusIn(quartz.getQuartzId(),staArray);
        if(taskList!=null) {
            for (Task task : taskList) {
                if ("trans".equals(task.getTaskGroup())) {
                    transService.updateTransQuartz(task.getJobId(), null);
                    taskService.shutDown(task.getTaskId());
                } else if ("job".equals(task.getTaskGroup())) {
                    jobService.updateJobQuartz(task.getJobId(), null);
                    taskService.shutDown(task.getTaskId());
                }
            }
        }
    }

    @Override
    @Transactional
    public void updateQuartz(QuartzForm.UpdateQuartz updateQuartz) {
        if (updateQuartz.getQuartzCron() == null) {
            List<String> cron = CronUtils.createQuartzCronressionAndDescription(updateQuartz);
            updateQuartz.setQuartzCron(cron.get(0));
            updateQuartz.setQuartzDescription(cron.get(1));
        }
        Quartz quartz = BeanPropertyCopyUtils.copy(updateQuartz, Quartz.class);
        quartz.setDelFlag(1);
        quartz.setAssTaskFlag(1);
        quartzDao.save(quartz);
        Integer[] staArray={Constant.COMPLETION,Constant.VALID};
        List<Task>  taskList=taskDao.findByQuartzIdAndStatusIn(quartz.getQuartzId(),staArray);
        if(taskList!=null) {
            for (Task task : taskList) {
                task.setQuartzDesc(quartz.getQuartzDescription());
                task.setStartTime(quartz.getStartTime());
                task.setEndTime(quartz.getEndTime());
                taskService.shutDown(task.getTaskId());
                taskService.runTask(task.getTaskId());
                }
        }
    }

    @Override
    public PageResult<Quartz> getList(Paging paging, List<String> queryString, List<String> orderBys) {
        queryString.add("delFlag~eq~1");
        PageResult<Quartz> quartz = quartzDao.findAll(paging, queryString, orderBys);
        return quartz;
    }

    @Override
    //根据当前时间和Cron表达式获取下次执行时间
    public Date getNextValidTime(Date date, Integer quartzId) throws ParseException {
        String cron = quartzDao.findByQuartzId(quartzId).getQuartzCron();
        CronExpression cronExpression = new CronExpression(cron);
        return cronExpression.getNextValidTimeAfter(date);
    }
    @Override
    public List<Quartz> getQuartzByDelFlag(Integer flag){
        return quartzDao.findByDelFlag(flag);
    }
}
