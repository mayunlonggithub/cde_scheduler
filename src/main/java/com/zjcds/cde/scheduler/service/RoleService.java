package com.zjcds.cde.scheduler.service;

import com.zjcds.cde.scheduler.domain.entity.Role;

import java.util.List;

/**
 * Created date: 2019-08-21
 *
 * @author wut
 */
public interface RoleService {

    public Role queryById(Integer id);

    /**
     * 根据name查询角色
     * @param name
     * @return
     */
    public List<Role> queryRolesByNameLike(String name);

    /**
     * 根据name查询角色
     * @param name
     * @return
     */
    public Long countRolesByNameLike(String name);

    public List<Role> queryByDescLike(String desc);



}
