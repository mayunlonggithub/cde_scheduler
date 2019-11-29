package com.zjcds.cde.scheduler.dao.jpa;

import com.zjcds.cde.scheduler.base.CustomRepostory;
import com.zjcds.cde.scheduler.domain.entity.Job;

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

    public Job findByJobId(Integer jobId);

    public Job findByJobNameAndDelFlagAndCreateUser(String jobName,Integer delFlag,Integer createUser);
}
