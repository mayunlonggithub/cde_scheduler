package com.zjcds.cde.scheduler.dao.jpa;

import com.zjcds.cde.scheduler.base.CustomRepostory;
import com.zjcds.cde.scheduler.domain.entity.JobMonitor;

import java.util.List;

/**
 * @author J on 20191024
 */
public interface JobMonitorDao extends CustomRepostory<JobMonitor,Integer> {

    public List<JobMonitor> findByCreateUserAndMonitorStatus(Integer createUser,Integer monitorStatus);

    public List<JobMonitor> findByCreateUser(Integer createUser);

    public JobMonitor findByMonitorJob(Integer monitorJob);

    public JobMonitor findByMonitorJobAndCreateUser(Integer monitorJob,Integer createUser);
}
