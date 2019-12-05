package com.zjcds.cde.scheduler.dao.jpa;

import com.zjcds.cde.scheduler.base.CustomRepostory;
import com.zjcds.cde.scheduler.domain.entity.Task;

import java.util.List;

/**
 * @author Ma on 20191122
 */
public interface TaskDao extends CustomRepostory<Task,Integer> {
         List<Task> findByStatus(Integer status);
         Task findByJobIdAndTaskGroupAndStatus(Integer jobId,String taskGroup,Integer status);
         public Task findByTaskId(Integer TaskId);
}
