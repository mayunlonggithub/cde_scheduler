package com.zjcds.cde.scheduler.dao.jpa;

import com.zjcds.cde.scheduler.domain.entity.Trans;
import com.zjcds.common.jpa.CustomRepostory;

import java.util.List;

/**
 * @author J on 20191024
 */
public interface TransDao extends CustomRepostory<Trans,Integer> {

    public List<Trans> findByCreateUserAndDelFlag(Integer createUser,Integer delFlag);

    public List<Trans> findByCreateUserAndDelFlagAndTransRepositoryIdAndTransPath(Integer createUser,Integer delFlag,Integer TransRepositoryId,String TransPath);

    public List<Trans> findByTransRepositoryId(Integer transRepositoryId);
}
