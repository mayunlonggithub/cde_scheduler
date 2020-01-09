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

    public JobRecord findByRecordIdAndCreateUserAndDelFlag(Integer RecordId,Integer CreateUser,Integer delFlag);

    public List<JobRecord> findByCreateUserAndDelFlag(Integer createUser,Integer delFlag);

    public List<JobRecord> findByCreateUserAndRecordStatusAndDelFlag(Integer createUser,Integer recordStatus,Integer delFlag);

    public List<JobRecord>  findByRecordJobAndDelFlag(Integer recordJob,Integer delFlag);
}
