package com.zjcds.cde.scheduler.domain.entity;

import com.zjcds.common.jpa.domain.CreateModifyTime;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author J on 20191024
 * 用户信息
 */
@Entity
@Table(name = "t_user")
public class User extends CreateModifyTime {
    //用户ID
    private Integer id;
    //用户昵称
    private String nickname;
    //用户邮箱
    private String email;
    //用户电话
    private String phone;
    //用户账号
    private String account;
    //用户密码
    private String password;
    //添加者
    private Integer createUser;
    //编辑者
    private Integer modifyUser;
    //是否删除
    private Integer delFlag;

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "nickname")
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "account")
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "create_user")
    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    @Basic
    @Column(name = "modify_user")
    public Integer getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(Integer modifyUser) {
        this.modifyUser = modifyUser;
    }

    @Basic
    @Column(name = "del_flag")
    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

}
