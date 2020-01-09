package com.zjcds.cde.scheduler.dao.jpa;

import com.zjcds.cde.scheduler.base.CustomRepostory;
import com.zjcds.cde.scheduler.domain.entity.JobMonitor;

import java.util.List;

/**
 * @author J on 20191024
 */
public interface JobMonitorDao extends CustomRepostory<JobMonitor,Integer> {

    public List<JobMonitor> findByCreateUserAndMonitorStatusAndDelFlag(Integer createUser,Integer monitorStatus,Integer delFlag);

    public JobMonitor findByMonitorJobAndDelFlag(Integer monitorJob,Integer delFlag);

    public JobMonitor findByMonitorJobAndCreateUserAndDelFlag(Integer monitorJob,Integer createUser,Integer delFlag);
}
