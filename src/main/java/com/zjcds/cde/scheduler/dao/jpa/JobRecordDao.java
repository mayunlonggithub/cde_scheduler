package com.zjcds.cde.scheduler.dao.jpa;

import com.zjcds.cde.scheduler.domain.entity.JobRecord;
import com.zjcds.common.jpa.CustomRepostory;

import java.util.List;

/**
 * @author J on 20191024
 */
public interface JobRecordDao extends CustomRepostory<JobRecord,Integer> {

    public JobRecord findByRecordIdAndCreateUser(Integer RecordId,Integer CreateUser);
}
