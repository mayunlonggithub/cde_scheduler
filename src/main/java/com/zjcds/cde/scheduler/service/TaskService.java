package com.zjcds.cde.scheduler.service;

import com.zjcds.cde.scheduler.domain.dto.TaskForm;
import com.zjcds.cde.scheduler.domain.entity.Task;
import com.zjcds.common.base.domain.page.Paging;
import com.zjcds.common.jpa.PageResult;
import org.quartz.*;

import java.util.List;

public interface TaskService {
    void addTask(TaskForm.AddTask addTask, Integer uId);
    void deleteTask(Integer taskId);
    PageResult<Task> getList(Paging paging, List<String> queryString, List<String> orderBys, Integer uId);
    JobDataMap getJobDataMap(Task task);
    JobDetail getTaskDetail(JobKey jobKey, String description, JobDataMap map);
    Trigger getTrigger(Task task);
    void restartAllJobs() throws SchedulerException;
    void runTask(Task task);
    void shutdown(Integer taskId);
}
