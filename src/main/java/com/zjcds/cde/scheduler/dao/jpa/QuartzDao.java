package com.zjcds.cde.scheduler.dao.jpa;

import com.zjcds.cde.scheduler.base.CustomRepostory;
import com.zjcds.cde.scheduler.domain.entity.Quartz;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author J on 20191024
 */
public interface QuartzDao extends CustomRepostory<Quartz,Integer> {
       List<Quartz> findByDelFlagAndCreateUser(Integer delFlag,Integer uId);
       @Modifying
       void  deleteByQuartzId(Integer id);

       public Quartz findByQuartzId(Integer quartzId);

       List<Quartz> findByDelFlag(Integer delFlag);


}

