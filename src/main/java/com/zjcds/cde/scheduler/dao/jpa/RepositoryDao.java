package com.zjcds.cde.scheduler.dao.jpa;

import com.zjcds.cde.scheduler.base.CustomRepostory;
import com.zjcds.cde.scheduler.domain.entity.Repository;

import java.util.List;

/**
 * @author J on 20191024
 */
public interface RepositoryDao extends CustomRepostory<Repository,Integer> {

    public List<Repository> findByCreateUserAndDelFlag(Integer createUser,Integer delFlag);

    public Repository findByRepositoryId(Integer repositoryId);

    public Repository findByDatabaseHostAndDatabaseNameAndDelFlagAndCreateUser(String databaseHost,String databaseName,Integer delFlag,Integer createUser);

    public Repository findByRepositoryIdAndDelFlag(Integer repositoryId,Integer delFlag);

}
