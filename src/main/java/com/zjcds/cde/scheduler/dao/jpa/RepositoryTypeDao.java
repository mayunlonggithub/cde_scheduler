package com.zjcds.cde.scheduler.dao.jpa;

import com.zjcds.cde.scheduler.base.CustomRepostory;
import com.zjcds.cde.scheduler.domain.entity.RepositoryType;

import java.util.List;

/**
 * @author J on 20191112
 */
public interface RepositoryTypeDao extends CustomRepostory<RepositoryType,Integer> {

    public List<RepositoryType> findByIsShow(Integer isShow);
}
