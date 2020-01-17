package com.zjcds.cde.scheduler.service;

import com.zjcds.cde.scheduler.domain.dto.TMenuForm;

import java.util.List;

/**
 * @author huangyj on 20190831
 */
public interface TMenuService {

    public List<TMenuForm.TMenuTree> queryAllTMenu();

    public TMenuForm.TMenu addTMenu(TMenuForm.AddTMenu addTMenu);

    public TMenuForm.TMenu updateTMenu(Integer id, TMenuForm.UpdateTMenu updateTMenu);

    public void deleteTMenu(Integer id);

    public List<TMenuForm.TMenuTree> tMenuTree(List<TMenuForm.TMenu> tMenus);

    public List<TMenuForm.TMenu> findByRoleId(Integer roleId);

    public TMenuForm.TMenu findById(Integer menuId);

    public List<TMenuForm.TMenuTree> findValidTMenu();

}
