package com.zjcds.cde.scheduler.dao.jpa;

import com.zjcds.cde.scheduler.base.CustomRepostory;
import com.zjcds.cde.scheduler.domain.entity.Task;

import java.util.List;

/**
 * @author Ma on 20191122
 */
public interface TaskDao extends CustomRepostory<Task,Integer> {
    List<Task> findByStatus(Integer status);

    Task findByJobIdAndTaskGroupAndStatusInAndDelFlag(Integer jobId, String taskGroup,Integer[] status,Integer delFlag);

    Task findByTaskId(Integer TaskId);

    List<Task> findByQuartzIdAndStatusIn(Integer quartzId, Integer[] status);



}

