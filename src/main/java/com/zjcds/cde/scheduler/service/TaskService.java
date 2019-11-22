package com.zjcds.cde.scheduler.service;

import com.zjcds.cde.scheduler.base.PageResult;
import com.zjcds.cde.scheduler.base.Paging;
import com.zjcds.cde.scheduler.domain.dto.TaskForm;
import com.zjcds.cde.scheduler.domain.entity.Task;

import java.util.List;
/**
 * @author Ma on 20191122
 */
public interface TaskService {
    void addTask(TaskForm.AddTask addTask, Integer uId);
    void deleteTask(Integer taskId);
    PageResult<Task> getList(Paging paging, List<String> queryString, List<String> orderBys, Integer uId);
}
