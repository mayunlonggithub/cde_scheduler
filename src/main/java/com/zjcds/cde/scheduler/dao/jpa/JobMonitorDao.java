package com.zjcds.cde.scheduler.dao.jpa;

import com.zjcds.cde.scheduler.domain.entity.JobMonitor;
import com.zjcds.common.jpa.CustomRepostory;

import java.util.List;

/**
 * @author J on 20191024
 */
public interface JobMonitorDao extends CustomRepostory<JobMonitor,Integer> {

    public List<JobMonitor> findByCreateUserAndMonitorStatus(Integer createUser,Integer monitorStatus);

    public List<JobMonitor> findByCreateUser(Integer createUser);

    public JobMonitor findByMonitorJob(Integer monitorJob);
}
