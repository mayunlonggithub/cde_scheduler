package com.zjcds.cde.scheduler.dao.jpa.view;

import com.zjcds.cde.scheduler.base.CustomRepostory;
import com.zjcds.cde.scheduler.domain.entity.JobRecord;
import com.zjcds.cde.scheduler.domain.entity.view.JobRecordView;
import org.beetl.sql.core.annotatoin.Param;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @author J on 20191125
 */
public interface JobRecordViewDao extends CustomRepostory<JobRecordView,Integer> {

    @Query("select t from JobRecordView t where t.createUser=?1 and t.startTime>=?2 and t.startTime<=?3")
    public List<JobRecordView> findByStartTime(@Param("uId") Integer uId,@Param("startTime") Date startTime,@Param("endTime") Date endTime);
}
