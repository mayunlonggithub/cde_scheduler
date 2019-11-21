package com.zjcds.cde.scheduler.dao.jpa;

import com.zjcds.cde.scheduler.domain.entity.Job;
import com.zjcds.common.jpa.CustomRepostory;

import java.util.List;

/**
 * @author J on 20191024
 */
public interface JobDao extends CustomRepostory<Job,Integer> {

    public List<Job> findByCreateUserAndDelFlag(Integer createUser,Integer delFlag);

    public List<Job> findByCreateUserAndDelFlagAndJobRepositoryIdAndJobPath(Integer createUser,Integer delFlag,Integer jobRepositoryId,String jobPath);

    public List<Job> findByJobRepositoryId(Integer repositoryId);

    public Job findByJobIdAndDelFlag(Integer jobId,Integer delFlag);

    public List<Job> findByDelFlag(Integer delFlag);
}
