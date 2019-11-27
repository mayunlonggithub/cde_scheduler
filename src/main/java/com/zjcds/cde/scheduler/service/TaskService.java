package com.zjcds.cde.scheduler.service;

import com.zjcds.cde.scheduler.base.PageResult;
import com.zjcds.cde.scheduler.base.Paging;
import com.zjcds.cde.scheduler.domain.dto.TaskForm;
import com.zjcds.cde.scheduler.domain.entity.Quartz;
import com.zjcds.cde.scheduler.domain.entity.Task;
import org.quartz.SchedulerException;

import java.util.List;
/**
 * @author Ma on 20191122
 */
public interface TaskService {
    void addTask(TaskForm.AddTask addTask, Integer uId);

    void deleteTask(Integer taskId);

    PageResult<Task> getList(Paging paging, List<String> queryString, List<String> orderBys, Integer uId);

    void restartAllTasks() throws SchedulerException;

    void runTask(Integer taskId);

    void shutDown(Integer taskId);

    void startAllTasks();

    void shutDownAllTasks();
}