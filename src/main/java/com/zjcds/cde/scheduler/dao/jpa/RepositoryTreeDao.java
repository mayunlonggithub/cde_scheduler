package com.zjcds.cde.scheduler.dao.jpa;

import com.zjcds.cde.scheduler.base.CustomRepostory;
import com.zjcds.cde.scheduler.domain.entity.RepositoryTree;
import org.springframework.data.jpa.repository.Modifying;

/**
 * @author J on 20191118
 */
public interface RepositoryTreeDao extends CustomRepostory<RepositoryTree,Integer> {

    @Modifying
    public void deleteByRepositoryId(Integer repositoryId);
}
