package com.zjcds.cde.scheduler.dao.jpa.view;

import com.zjcds.cde.scheduler.base.CustomRepostory;
import com.zjcds.cde.scheduler.domain.entity.view.TransRecordGroupView;

import java.util.List;

/**
 * @author J on 20191129
 */
public interface TransRecordGroupViewDao extends CustomRepostory<TransRecordGroupView,Integer> {

    public List<TransRecordGroupView> findByCreateUser(Integer createUser);
}
