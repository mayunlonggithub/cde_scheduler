package com.zjcds.cde.scheduler.dao.jpa;

import com.zjcds.cde.scheduler.base.CustomRepostory;
import com.zjcds.cde.scheduler.domain.entity.JobRecord;

/**
 * @author J on 20191024
 */
public interface JobRecordDao extends CustomRepostory<JobRecord,Integer> {

    public JobRecord findByRecordIdAndCreateUser(Integer RecordId,Integer CreateUser);
}
