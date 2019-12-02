package com.zjcds.cde.scheduler.dao.jpa.view;

import com.zjcds.cde.scheduler.base.CustomRepostory;
import com.zjcds.cde.scheduler.domain.entity.TransMonitor;
import com.zjcds.cde.scheduler.domain.entity.view.TransMonitorView;

import java.util.List;

/**
 * @author J on 20191125
 */
public interface TransMonitorViewDao extends CustomRepostory<TransMonitorView,Integer> {

    public List<TransMonitorView> findByCreateUserAndMonitorStatus(Integer createUser, Integer monitorStatus);

    public List<TransMonitorView> findByCreateUser(Integer createUser);
}
