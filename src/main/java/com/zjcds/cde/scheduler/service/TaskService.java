package com.zjcds.cde.scheduler.service;

import com.zjcds.cde.scheduler.base.PageResult;
import com.zjcds.cde.scheduler.base.Paging;
import com.zjcds.cde.scheduler.base.ResponseResult;
import com.zjcds.cde.scheduler.domain.dto.TaskForm;
import com.zjcds.cde.scheduler.domain.entity.Quartz;
import com.zjcds.cde.scheduler.domain.entity.Task;
import com.zjcds.cde.scheduler.domain.entity.view.JobTransView;
import org.quartz.SchedulerException;

import java.text.ParseException;
import java.util.List;
/**
 * @author Ma on 20191122
 */
public interface TaskService {
    void addTask(TaskForm.AddTask addTask, Integer uId,Integer jobId) throws ParseException;

    void deleteTask(Integer taskId,Integer uId);

    void deleteTask(Integer jobId, String taskGroup,Integer uId);

    PageResult<JobTransView> getList(Paging paging, List<String> queryString, List<String> orderBys, Integer uId, Integer quartzId);

    void restartAllTasks() throws SchedulerException;

    void runTask(Integer taskId);

    void shutDown(Integer taskId);

//    void startAllTasks();
//
//    void shutDownAllTasks();
}
