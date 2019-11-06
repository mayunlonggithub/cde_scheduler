package com.zjcds.cde.scheduler.dao.jpa;

import com.zjcds.cde.scheduler.domain.entity.User;
import com.zjcds.common.jpa.CustomRepostory;
import org.springframework.data.jpa.repository.Query;

/**
 * @author J on 20191024
 */
public interface UserDao extends CustomRepostory<User,Integer> {

    public User findByAccountAndDelFlag(String account,Integer delflag);
}
