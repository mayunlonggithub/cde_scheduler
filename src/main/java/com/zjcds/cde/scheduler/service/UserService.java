package com.zjcds.cde.scheduler.service;

import com.zjcds.cde.scheduler.domain.entity.User;

import java.util.List;
import java.util.Set;

/**
 * 用户Service
 * Created date:2019-08-21
 *
 * @author shihuajie
 */
public interface UserService {

    /**
     * 通过用户账号查询用户实体
     * 未查到返回null
     *
     * @param accunt
     * @return
     */
    public User queryUserByAccount(String accunt);

    /**
     * 根据用户id查询用户
     * @param id
     * @return
     */
    public User queryUserWithRole(Integer id);

    /**
     * 根据name查询角色
     * @param name
     * @return
     */
    public List<User> queryUsersByNameLike(String name);

    /**
     * 根据name查询角色
     * @param name
     * @return
     */
    public Long countUsersByNameLike(String name);

    public List<User> queryActiveUserByAccountOrName(String account, String name);

    public Long countActiveUserByAccountOrName(String account, String name);

    public List<User> queryByIdIn(Set<Integer> userIdSet);

}
