package com.zjcds.cde.scheduler.dao.jpa;

import com.zjcds.cde.scheduler.base.CustomRepostory;
import com.zjcds.cde.scheduler.domain.entity.JobRecord;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @author J on 20191024
 */
public interface JobRecordDao extends CustomRepostory<JobRecord,Integer> {

    public JobRecord findByRecordIdAndCreateUser(Integer RecordId,Integer CreateUser);

    public List<JobRecord> findByCreateUser(Integer createUser);


}
