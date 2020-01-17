package com.zjcds.cde.scheduler.service.Impl;

import com.zjcds.cde.scheduler.base.BeanPropertyCopyUtils;
import com.zjcds.cde.scheduler.base.PageResult;
import com.zjcds.cde.scheduler.base.PageUtils;
import com.zjcds.cde.scheduler.base.Paging;
import com.zjcds.cde.scheduler.dao.jpa.RoleDao;
import com.zjcds.cde.scheduler.dao.jpa.RoleMenuDao;
import com.zjcds.cde.scheduler.dao.jpa.UserRoleDao;
import com.zjcds.cde.scheduler.domain.dto.TRoleForm;
import com.zjcds.cde.scheduler.domain.entity.Role;
import com.zjcds.cde.scheduler.domain.entity.RoleMenu;
import com.zjcds.cde.scheduler.service.TRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author huangyj on 20190831
 */
@Service
public class TRoleServiceImpl extends RoleServiceImpl implements TRoleService {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private RoleMenuDao roleMenuDao;
    @Autowired
    private UserRoleDao userRoleDao;

    /**
     * 分页查询角色
     * @param paging
     * @param queryString
     * @param orderBys
     * @return
     */
    @Override
    public PageResult<TRoleForm.TRole> queryAllTRole(Paging paging, List<String> queryString, List<String> orderBys){
        PageResult<Role> tRole = roleDao.findAll(paging,queryString,orderBys);
        PageResult<TRoleForm.TRole> owner = PageUtils.copyPageResult(tRole, TRoleForm.TRole.class);
        return owner;
    }

    /**
     * 新增角色
     * @param addTRole
     * @return
     */
    @Override
    @Transactional
    public TRoleForm.TRole addTRole(TRoleForm.AddTRole addTRole){
        Role role = BeanPropertyCopyUtils.copy(addTRole, Role.class);
        role = roleDao.save(role);
        TRoleForm.TRole owner = BeanPropertyCopyUtils.copy(role, TRoleForm.TRole.class);
        return owner;
    }

    /**
     * 修改角色
     * @param id
     * @param updateTRole
     * @return
     */
    @Override
    @Transactional
    public TRoleForm.TRole updateTRole(Integer id, TRoleForm.UpdateTRole updateTRole){
        Assert.notNull(id,"要修改的id不能为空");
        Optional<Role> tRoleOptional = roleDao.findById(id);
        Role role = new Role();
        if(tRoleOptional.isPresent()){
            role = tRoleOptional.get();
        }
        Assert.notNull(role,"该数据不存在或已删除");
        role = BeanPropertyCopyUtils.copy(updateTRole, Role.class);
        role = roleDao.save(role);
        TRoleForm.TRole owner = BeanPropertyCopyUtils.copy(role, TRoleForm.TRole.class);
        return owner;
    }

    /**
     * 删除角色
     * @param id
     */
    @Override
    @Transactional
    public void deleteTRole(Integer id){
        Assert.notNull(id,"要修改的id不能为空");
        Optional<Role> tRoleOptional = roleDao.findById(id);
        Role role = new Role();
        if(tRoleOptional.isPresent()){
            role = tRoleOptional.get();
        }
        Assert.notNull(role,"该数据不存在或已删除");
        roleDao.deleteById(id);
    }

    @Override
    @Transactional
    public List<TRoleForm.AddRRoleMenu> addRRoleMenu(Integer roleId, List<TRoleForm.AddRRoleMenu> addRRoleMenus){
        List<RoleMenu> roleMenus = BeanPropertyCopyUtils.copy(addRRoleMenus, RoleMenu.class);
        // 删除该用户角色关系
        roleMenuDao.deleteByRoleId(roleId);
        // 保存角色关系
        roleMenus = roleMenuDao.saveAll(roleMenus);
        List<TRoleForm.AddRRoleMenu> owner = BeanPropertyCopyUtils.copy(roleMenus, TRoleForm.AddRRoleMenu.class);
        return owner;
    }

    /**
     * 根据用户查询角色
     */
    public List<TRoleForm.TRole> findByUserId(Integer userId){
        Assert.notNull(userId,"要查询的用户id不能为空");
        List<Integer> roleId = userRoleDao.findByUserId(userId);
        List<Role> roles = new ArrayList<>();
        if(roleId!=null&&roleId.size()>0){
            roles = roleDao.findByIdList(roleId);
        }
        List<TRoleForm.TRole> owner = BeanPropertyCopyUtils.copy(roles, TRoleForm.TRole.class);
        return owner;
    }

}
