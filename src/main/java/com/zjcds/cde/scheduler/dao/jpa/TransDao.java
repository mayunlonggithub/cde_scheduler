package com.zjcds.cde.scheduler.dao.jpa;

import com.zjcds.cde.scheduler.base.CustomRepostory;
import com.zjcds.cde.scheduler.domain.entity.Trans;

import java.util.List;

/**
 * @author J on 20191024
 */
public interface TransDao extends CustomRepostory<Trans,Integer> {

    public List<Trans> findByCreateUserAndDelFlag(Integer createUser,Integer delFlag);

    public List<Trans> findByCreateUserAndDelFlagAndTransRepositoryIdAndTransPath(Integer createUser,Integer delFlag,Integer TransRepositoryId,String TransPath);

    public List<Trans> findByTransRepositoryId(Integer transRepositoryId);

    public List<Trans> findByTransRepositoryIdAndDelFlag(Integer transRepositoryId,Integer delFlag);

    public List<Trans> findByDelFlag(Integer delFlag);

    public Trans findByTransIdAndDelFlag(Integer transId,Integer delFlag);

    public Trans findByTransId(Integer transId);
}
