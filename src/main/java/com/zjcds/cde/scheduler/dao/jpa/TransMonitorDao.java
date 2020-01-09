package com.zjcds.cde.scheduler.dao.jpa;

import com.zjcds.cde.scheduler.base.CustomRepostory;
import com.zjcds.cde.scheduler.domain.entity.TransMonitor;

import java.util.List;

/**
 * @author J on 20191024
 */
public interface TransMonitorDao extends CustomRepostory<TransMonitor,Integer> {

    public List<TransMonitor> findByCreateUserAndMonitorStatusAndDelFlag(Integer createUser,Integer monitorStatus,Integer delFlag);

    public TransMonitor findByCreateUserAndMonitorTransAndDelFlag(Integer createUser,Integer monitorTrans,Integer delFlag);

    public TransMonitor findByMonitorTransAndCreateUserAndDelFlag(Integer monitorTrans,Integer createUser,Integer delFlag);

}
