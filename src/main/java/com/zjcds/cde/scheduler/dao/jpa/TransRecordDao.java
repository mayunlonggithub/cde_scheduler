package com.zjcds.cde.scheduler.dao.jpa;

import com.zjcds.cde.scheduler.base.CustomRepostory;
import com.zjcds.cde.scheduler.domain.entity.TransRecord;

import java.util.List;

/**
 * @author J on 20191024
 */
public interface TransRecordDao extends CustomRepostory<TransRecord,Integer> {

    public TransRecord findByRecordIdAndCreateUserAndDelFlag(Integer recordId,Integer createUser,Integer delFlag);

    public List<TransRecord> findByCreateUserAndDelFlag(Integer createUser,Integer delFlag);

    public List<TransRecord> findByCreateUserAndRecordStatusAndDelFlag(Integer createUser,Integer recordStatus,Integer delFlag);

    public List<TransRecord> findByRecordTransAndDelFlag(Integer recordTrans,Integer delFlag);
}
