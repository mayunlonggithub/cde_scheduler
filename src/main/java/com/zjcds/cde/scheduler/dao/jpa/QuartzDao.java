package com.zjcds.cde.scheduler.dao.jpa;
import com.zjcds.cde.scheduler.domain.entity.Quartz;
import com.zjcds.common.jpa.CustomRepostory;

/**
 * @author J on 20191024
 */
public interface QuartzDao extends CustomRepostory<Quartz,Integer> {
       void  deleteByQuartzId(Integer id);
}

