package com.zjcds.cde.scheduler.service.Impl;

import com.zjcds.cde.scheduler.dao.jpa.UserDao;
import com.zjcds.cde.scheduler.domain.dto.UserForm;
import com.zjcds.cde.scheduler.domain.entity.User;
import com.zjcds.cde.scheduler.service.UserService;
import com.zjcds.cde.scheduler.utils.MD5Utils;
import com.zjcds.common.base.domain.page.Paging;
import com.zjcds.common.dozer.BeanPropertyCopyUtils;
import com.zjcds.common.jpa.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * jackson 相关配置
 *
 * @author J on 20191107.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * @Title login
     * @Description 登陆
     * @param userLogin 用户信息对象
     * @return
     * @return KUser
     */
    @Override
    public User login(UserForm.UserLogin userLogin){
        User user = userDao.findByAccountAndDelFlag(userLogin.getAccount(),1);
        if (null != user){
            if (user.getPassword().equals(MD5Utils.Encrypt(userLogin.getPassword(), true))){
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
     * @Title addUser
     * @Description 插入一个用户
     * @param addTUser
     * @return void
     */
    @Override
    @Transactional
    public void addUser(UserForm.AddUser addTUser, Integer uId){
        User user = BeanPropertyCopyUtils.copy(addTUser,User.class);
        user.setPassword(MD5Utils.Encrypt(user.getPassword(), true));
        user.setCreateUser(uId);
        user.setModifyUser(uId);
        user.setDelFlag(1);
        userDao.save(user);
    }

    /**
     * @Title IsAccountExist
     * @Description 判断账号是否存在
     * @param account
     * @return void
     */
    @Override
    public boolean IsAccountExist(String account) {
        User user = userDao.findByAccountAndDelFlag(account,1);
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
     * @Title updateUser
     * @Description 更新用户
     * @param updateUser 用户对象
     * @param uId 用户ID
     * @return void
     */
    @Override
    @Transactional
    public void updateUser(UserForm.UpdateUser updateUser, Integer uId){
        User user = BeanPropertyCopyUtils.copy(updateUser,User.class);
        user.setPassword(MD5Utils.Encrypt(user.getPassword(), true));
        //只有不为null的字段才参与更新
        userDao.save(user);
    }

}
