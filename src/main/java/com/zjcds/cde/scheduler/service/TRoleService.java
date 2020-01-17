package com.zjcds.cde.scheduler.service;

import com.zjcds.cde.scheduler.base.PageResult;
import com.zjcds.cde.scheduler.base.Paging;
import com.zjcds.cde.scheduler.domain.dto.TRoleForm;

import java.util.List;

/**
 * @author huangyj on 20190831
 */
public interface TRoleService extends RoleService {

    public PageResult<TRoleForm.TRole> queryAllTRole(Paging paging, List<String> queryString, List<String> orderBys);

    public TRoleForm.TRole addTRole(TRoleForm.AddTRole addTRole);

    public TRoleForm.TRole updateTRole(Integer id, TRoleForm.UpdateTRole updateTRole);

    public void deleteTRole(Integer id);

    public List<TRoleForm.AddRRoleMenu> addRRoleMenu(Integer roleId, List<TRoleForm.AddRRoleMenu> addRRoleMenus);

    public List<TRoleForm.TRole> findByUserId(Integer userId);

}
