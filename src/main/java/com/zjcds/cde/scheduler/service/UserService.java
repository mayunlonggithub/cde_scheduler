package com.zjcds.cde.scheduler.service;

import com.zjcds.cde.scheduler.domain.dto.UserForm;
import com.zjcds.cde.scheduler.domain.entity.User;
import com.zjcds.common.base.domain.page.Paging;
import com.zjcds.common.jpa.PageResult;

import java.util.List;

/**
 * jackson 相关配置
 *
 * @author J on 20191107.
 */
public interface UserService {

    /**
     * @Title login
     * @Description 登陆
     * @param userLogin 用户信息对象
     * @return
     * @return KUser
     */
    public User login(UserForm.UserLogin userLogin);

    /**
     * @Title isAdmin
     * @Description 用户是否为管理员
     * @param uId 用户ID
     * @return
     * @return boolean
     */
    public boolean isAdmin(Integer uId);

    /**
     * @Title getList
     * @Description 获取用户分页列表
     * @return
     */
    public PageResult<User> getList(Paging paging, List<String> queryString, List<String> orderBys);

    /**
     * @Title delete
     * @Description 删除用户
     * @param uId 用户ID
     * @return void
     */
    public void delete(Integer uId);

    /**
     * @Title addUser
     * @Description 插入一个用户
     * @param addTUser
     * @return void
     */
    public void addUser(UserForm.AddUser addTUser, Integer uId);

    /**
     * @Title IsAccountExist
     * @Description 判断账号是否存在
     * @param uAccount
     * @return void
     */
    public boolean IsAccountExist(String uAccount);

    /**
     * @Title getUser
     * @Description 获取 用户
     * @param uId 用户ID
     * @return
     * @return KUser
     */
    public User getUser(Integer uId);

    /**
     * @Title update
     * @Description 更新用户
     * @param updateUser 用户对象
     * @param uId 用户ID
     * @return void
     */
    public void updateUser(UserForm.UpdateUser updateUser, Integer uId);
}
