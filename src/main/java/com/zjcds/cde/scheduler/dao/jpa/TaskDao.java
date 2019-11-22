package com.zjcds.cde.scheduler.dao.jpa;

import com.zjcds.cde.scheduler.domain.entity.Task;
import com.zjcds.common.jpa.CustomRepostory;

import java.util.List;

public interface TaskDao extends CustomRepostory<Task,Integer>{
         List<Task> findByStatus(Integer status);
}
