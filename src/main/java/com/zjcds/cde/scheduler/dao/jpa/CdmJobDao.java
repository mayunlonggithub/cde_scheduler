package com.zjcds.cde.scheduler.dao.jpa;

import com.zjcds.cde.scheduler.base.CustomRepostory;
import com.zjcds.cde.scheduler.domain.entity.CdmJob;

/**
 * @author J on 20191118
 */
public interface CdmJobDao extends CustomRepostory<CdmJob,Integer> {

    public CdmJob findByJobName(String jobName);
}
