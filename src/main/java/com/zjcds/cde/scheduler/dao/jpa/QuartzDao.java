package com.zjcds.cde.scheduler.dao.jpa;
import com.zjcds.cde.scheduler.domain.entity.Quartz;
import com.zjcds.cde.scheduler.domain.entity.User;
import com.zjcds.common.jpa.CustomRepostory;

import java.util.List;

/**
 * @author Ma on 20191122
 */
public interface QuartzDao extends CustomRepostory<Quartz,Integer> {
     List<Quartz> findByDelFlag(Integer delflag);
}

