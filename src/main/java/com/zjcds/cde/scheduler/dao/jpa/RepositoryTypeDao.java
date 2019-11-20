package com.zjcds.cde.scheduler.dao.jpa;

import com.zjcds.cde.scheduler.domain.entity.RepositoryType;
import com.zjcds.common.jpa.CustomRepostory;

import java.util.List;

/**
 * @author J on 20191112
 */
public interface RepositoryTypeDao extends CustomRepostory<RepositoryType,Integer> {

    public List<RepositoryType> findByIsShow(Integer isShow);
}
