package com.zjcds.cde.scheduler.service.Impl;

import com.zjcds.cde.scheduler.dao.jpa.UserDao;
import com.zjcds.cde.scheduler.domain.entity.User;
import com.zjcds.cde.scheduler.service.UserService;
import com.zjcds.cde.scheduler.utils.MD5Utils;
import com.zjcds.common.base.domain.page.Paging;
import com.zjcds.common.jpa.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * @Title login
     * @Description 登陆
     * @param kUser 用户信息对象
     * @return
     * @return KUser
     */
    @Override
    public User login(User kUser){
        User user = userDao.findByUAccountAndDelFlag(kUser.getAccount(),1);
        if (null != user){
            if (user.getPassword().equals(MD5Utils.Encrypt(kUser.getPassword(), true))){
                return user;
            }
            return null;
        }
        return null;
    }

    /**
     * @Title isAdmin
     * @Description 用户是否为管理员
     * @param uId 用户ID
     * @return
     * @return boolean
     */
    @Override
    public boolean isAdmin(Integer uId){
        User kUser = userDao.findOne(uId);
        if ("admin".equals(kUser.getAccount())){
            return true;
        }else {
            return false;
        }
    }

    /**
     * @Title getList
     * @Description 获取用户分页列表
     * @return
     */
    @Override
    public PageResult<User> getList(Paging  paging, List<String> queryString, List<String> orderBys){
        queryString.add("delFlag~eq~1");
        PageResult<User> user = userDao.findAll(paging,queryString,orderBys);
        return user;
    }

    /**
     * @Title delete
     * @Description 删除用户
     * @param uId 用户ID
     * @return void
     */
    @Override
    @Transactional
    public void delete(Integer uId){
        User kUser = userDao.findOne(uId);
        kUser.setDelFlag(0);
        userDao.save(kUser);
    }

    /**
     * @Title insert
     * @Description 插入一个用户
     * @param kUser
     * @return void
     */
    @Override
    @Transactional
    public void insert(User kUser, Integer uId){
        kUser.setPassword(MD5Utils.Encrypt(kUser.getPassword(), true));
        kUser.setCreateUser(uId);
        kUser.setModifyUser(uId);
        kUser.setDelFlag(1);
        userDao.save(kUser);
    }

    /**
     * @Title IsAccountExist
     * @Description 判断账号是否存在
     * @param uAccount
     * @return void
     */
    @Override
    public boolean IsAccountExist(String uAccount) {
        User user = userDao.findByUAccountAndDelFlag(uAccount,1);
        if (null == user) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * @Title getUser
     * @Description 获取 用户
     * @param uId 用户ID
     * @return
     * @return KUser
     */
    @Override
    public User getUser(Integer uId){
        return userDao.findOne(uId);
    }

    /**
     * @Title update
     * @Description 更新用户
     * @param kUser 用户对象
     * @param uId 用户ID
     * @return void
     */
    @Override
    @Transactional
    public void update(User kUser, Integer uId){
        //只有不为null的字段才参与更新
        userDao.save(kUser);
    }

}
