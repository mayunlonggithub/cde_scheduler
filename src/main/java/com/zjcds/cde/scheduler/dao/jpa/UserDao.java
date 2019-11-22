package com.zjcds.cde.scheduler.dao.jpa;

import com.zjcds.cde.scheduler.base.CustomRepostory;
import com.zjcds.cde.scheduler.domain.entity.User;

/**
 * @author J on 20191024
 */
public interface UserDao extends CustomRepostory<User,Integer> {

    public User findByAccountAndDelFlag(String account,Integer delflag);

}
