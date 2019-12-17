package com.zjcds.cde.scheduler.dao.jpa;

import com.zjcds.cde.scheduler.base.CustomRepostory;
import com.zjcds.cde.scheduler.domain.entity.Task;

import java.util.List;

/**
 * @author Ma on 20191122
 */
public interface TaskDao extends CustomRepostory<Task,Integer> {
    List<Task> findByStatus(Integer status);

    Task findByJobIdAndTaskGroupAndStatusIn(Integer jobId, String taskGroup,Integer[] status);

    Task findByTaskId(Integer TaskId);

    List<Task> findByQuartzIdAndStatusIn(Integer quartzId, Integer[] status);
}

