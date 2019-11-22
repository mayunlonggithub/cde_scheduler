package com.zjcds.cde.scheduler.dao.jpa;

import com.zjcds.cde.scheduler.base.CustomRepostory;
import com.zjcds.cde.scheduler.domain.entity.TransRecord;

/**
 * @author J on 20191024
 */
public interface TransRecordDao extends CustomRepostory<TransRecord,Integer> {

    public TransRecord findByRecordIdAndCreateUser(Integer recordId,Integer createUser);
}
