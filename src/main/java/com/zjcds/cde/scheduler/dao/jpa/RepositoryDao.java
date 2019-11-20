package com.zjcds.cde.scheduler.dao.jpa;

import com.zjcds.cde.scheduler.domain.entity.Repository;
import com.zjcds.common.jpa.CustomRepostory;

import java.util.List;

/**
 * @author J on 20191024
 */
public interface RepositoryDao extends CustomRepostory<Repository,Integer> {

    public List<Repository> findByCreateUserAndDelFlag(Integer createUser,Integer delFlag);

    public Repository findByRepositoryId(Integer repositoryId);
}
