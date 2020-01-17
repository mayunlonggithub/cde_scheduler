package com.zjcds.cde.scheduler.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author huangyj on 20190831
 */
@Entity
@Table(name = "R_ROLE_MENU")
@IdClass(RoleMenuPK.class)
public class RoleMenu {
    private Integer roleId;
    private Integer menuId;

    @Id
    @Column(name = "ROLE_ID")
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Id
    @Column(name = "MENU_ID")
    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

}
