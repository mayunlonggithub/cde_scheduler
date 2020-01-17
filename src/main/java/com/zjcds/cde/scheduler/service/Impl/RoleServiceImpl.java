package com.zjcds.cde.scheduler.service.Impl;

import com.zjcds.cde.scheduler.dao.jpa.RoleDao;
import com.zjcds.cde.scheduler.domain.entity.Role;
import com.zjcds.cde.scheduler.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created date: 2019-08-21
 *
 * @author wut
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public Role queryById(Integer id) {
        return roleDao.findById(id).get();
    }

    @Override
    public List<Role> queryRolesByNameLike(String name) {
        return roleDao.findByNameLike(name);
    }

    @Override
    public Long countRolesByNameLike(String name) {
        return roleDao.countByNameLike(name);
    }

    @Override
    public List<Role> queryByDescLike(String desc){
        return roleDao.findByDescLike(desc);
    }

}
