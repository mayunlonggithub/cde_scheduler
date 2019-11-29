package com.zjcds.cde.scheduler.dao.jpa.view;

import com.zjcds.cde.scheduler.base.CustomRepostory;
import com.zjcds.cde.scheduler.domain.entity.JobMonitor;
import com.zjcds.cde.scheduler.domain.entity.view.JobMonitorView;

import java.util.List;

/**
 * @author J on 20191125
 */
public interface JobMonitorViewDao extends CustomRepostory<JobMonitorView,Integer> {

    public List<JobMonitorView> findByCreateUserAndMonitorStatus(Integer createUser, Integer monitorStatus);

    public List<JobMonitorView> findByCreateUser(Integer createUser);
}
