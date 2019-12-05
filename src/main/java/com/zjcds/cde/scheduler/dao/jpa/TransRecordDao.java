package com.zjcds.cde.scheduler.dao.jpa;

import com.zjcds.cde.scheduler.base.CustomRepostory;
import com.zjcds.cde.scheduler.domain.entity.TransRecord;

import java.util.List;

/**
 * @author J on 20191024
 */
public interface TransRecordDao extends CustomRepostory<TransRecord,Integer> {

    public TransRecord findByRecordIdAndCreateUser(Integer recordId,Integer createUser);

    public List<TransRecord> findByCreateUser(Integer createUser);

    public List<TransRecord> findByCreateUserAndRecordStatus(Integer createUser,Integer recordStatus);
}
