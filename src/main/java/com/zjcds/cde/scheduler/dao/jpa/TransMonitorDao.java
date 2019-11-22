package com.zjcds.cde.scheduler.dao.jpa;

import com.zjcds.cde.scheduler.base.CustomRepostory;
import com.zjcds.cde.scheduler.domain.entity.TransMonitor;

import java.util.List;

/**
 * @author J on 20191024
 */
public interface TransMonitorDao extends CustomRepostory<TransMonitor,Integer> {

    public List<TransMonitor> findByCreateUserAndMonitorStatus(Integer createUser,Integer monitorStatus);

    public List<TransMonitor> findByCreateUser(Integer createUser);

    public TransMonitor findByCreateUserAndMonitorTrans(Integer createUser,Integer monitorTrans);

    public TransMonitor findByMonitorTrans(Integer monitorTrans);

    public TransMonitor findByMonitorTransAndCreateUser(Integer monitorTrans,Integer createUser);

}
