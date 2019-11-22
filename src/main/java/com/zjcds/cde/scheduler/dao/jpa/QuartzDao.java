package com.zjcds.cde.scheduler.dao.jpa;

import com.zjcds.cde.scheduler.base.CustomRepostory;
import com.zjcds.cde.scheduler.domain.entity.Quartz;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

/**
 * @author J on 20191024
 */
public interface QuartzDao extends CustomRepostory<Quartz,Integer> {
       List<Quartz> findByDelFlag(Integer delflag);
       @Modifying
       void  deleteByQuartzId(Integer id);

       public Quartz findByQuartzId(Integer quartzId);
}

