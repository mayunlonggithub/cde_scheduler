package com.zjcds.cde.scheduler.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * @author huangyj on 20190831
 */
@Entity
@Table(name = "R_USER_ROLE")
@IdClass(UserRolePK.class)
public class UserRole {
    private Integer userId;
    private Integer roleId;

    @Id
    @Column(name = "USER_ID")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Id
    @Column(name = "ROLE_ID")
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

}
