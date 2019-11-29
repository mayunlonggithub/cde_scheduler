package com.zjcds.cde.scheduler.dao.jpa.view;

import com.zjcds.cde.scheduler.base.CustomRepostory;
import com.zjcds.cde.scheduler.domain.entity.view.JobRecordGroupView;

import java.util.List;

/**
 * @author J on 20191129
 */
public interface JobRecordGroupViewDao extends CustomRepostory<JobRecordGroupView,Integer> {

    public List<JobRecordGroupView> findByCreateUser(Integer createUser);
}
