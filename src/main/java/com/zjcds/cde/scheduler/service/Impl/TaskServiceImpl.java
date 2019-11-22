package com.zjcds.cde.scheduler.service.Impl;

import com.zjcds.cde.scheduler.base.BeanPropertyCopyUtils;
import com.zjcds.cde.scheduler.base.PageResult;
import com.zjcds.cde.scheduler.base.Paging;
import com.zjcds.cde.scheduler.dao.jpa.QuartzDao;
import com.zjcds.cde.scheduler.dao.jpa.TaskDao;
import com.zjcds.cde.scheduler.domain.dto.TaskForm;
import com.zjcds.cde.scheduler.domain.entity.Quartz;
import com.zjcds.cde.scheduler.domain.entity.Task;
import com.zjcds.cde.scheduler.service.TaskService;
import com.zjcds.cde.scheduler.utils.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * @author Ma on 20191122
 */
@Service
public class TaskServiceImpl implements TaskService {
   @Autowired
    private TaskDao taskDao;
    @Autowired
    private QuartzDao quartzDao;

    private static Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);
    @Override
    @Transactional
    public void addTask(TaskForm.AddTask addTask,Integer uId){
        Task task= BeanPropertyCopyUtils.copy(addTask,Task.class);
        Quartz quartz=quartzDao.findByQuartzId(task.getQuartzId());
        task.setStartTime(quartz.getStartTime());
        task.setEndTime(quartz.getEndTime());
        task.setUserId(uId);
        task.setStatus(Constant.IMPLEMENT);
        task.setQuartzDesc(quartz.getQuartzDescription());
        taskDao.save(task);
    }

    @Override
    @Transactional
    public void deleteTask(Integer taskId){
        Task task = taskDao.findByQuartzId(taskId);
        task.setStatus(Constant.INVALID);
        taskDao.save(task);

    }

    @Override
    public PageResult<Task> getList(Paging paging, List<String> queryString, List<String> orderBys, Integer uId) {
        queryString.add("createUser~Eq~"+uId);
        queryString.add("status~lt~"+Constant.INVALID);
        PageResult<Task> task = taskDao.findAll(paging,queryString,orderBys);
        return task;
    }


}
