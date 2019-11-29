package com.zjcds.cde.scheduler.service.Impl;

import com.zjcds.cde.scheduler.base.BeanPropertyCopyUtils;
import com.zjcds.cde.scheduler.base.PageResult;
import com.zjcds.cde.scheduler.base.Paging;
import com.zjcds.cde.scheduler.dao.jpa.UserDao;
import com.zjcds.cde.scheduler.domain.dto.UserForm;
import com.zjcds.cde.scheduler.domain.entity.User;
import com.zjcds.cde.scheduler.service.UserService;
import com.zjcds.cde.scheduler.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

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
        Assert.notNull(uId,"未登录,请重新登录");
        Optional<User> userOptional = userDao.findById(uId);
        User user = new User();
        if(userOptional.isPresent()){
            user=userOptional.get();
        }
        Assert.notNull(user,"管理员不存在或已删除");
        if ("admin".equals(user.getAccount())){
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
    public PageResult<User> getList(Paging paging, List<String> queryString, List<String> orderBys, Integer uId){
        //未删除
        queryString.add("delFlag~Eq~1");
        //根据用户查询
        Assert.notNull(uId,"未登录,请重新登录");
        queryString.add("createUser~Eq~"+uId);
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
    public void delete(Integer id,Integer uId){
        Assert.notNull(id,"要删除的用户id不能为空");
        Assert.notNull(uId,"未登录,请重新登录");
        Optional<User> userOptional = userDao.findById(id);
        User user = new User();
        if(userOptional.isPresent()){
            user=userOptional.get();
        }
        Assert.notNull(user,"该用户不存在或已删除");
        user.setDelFlag(0);
        user.setModifyUser(uId);
        userDao.save(user);
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
        Assert.notNull(uId,"未登录,请重新登录");
        User user = userDao.findByAccountAndDelFlag(addTUser.getAccount(),1);
        Assert.isNull(user,"该账号已存在");
        user = BeanPropertyCopyUtils.copy(addTUser,User.class);
        user.setPassword(MD5Utils.Encrypt(user.getPassword(), true));
        user.setCreateUser(uId);
        user.setModifyUser(uId);
        user.setDelFlag(1);
        user.setUpdateFlag(1);
        userDao.save(user);
    }

    /**
     * @Title IsAccountExist
     * @Description 判断账号是否存在
     * @param account
     * @return void
     */
    @Override
    public boolean isAccountExist(String account) {
        Assert.notNull(account,"账号不能为空");
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
        Assert.notNull(uId,"未登录,请重新登录");
        Optional<User> userOptional = userDao.findById(uId);
        User user = new User();
        if(userOptional.isPresent()){
            user=userOptional.get();
        }
        return user;
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
    public void updateUser(UserForm.UpdateUser updateUser, Integer id,Integer uId){
        Assert.notNull(id,"要修改的用户id不能为空");
        Assert.notNull(uId,"未登录,请重新登录");
        Optional<User> userOptional = userDao.findById(id);
        User u = new User();
        if(userOptional.isPresent()){
            u=userOptional.get();
        }
        Assert.notNull(u,"该用户不存在或已删除");
        User user = BeanPropertyCopyUtils.copy(updateUser,User.class);
        Assert.isTrue(user.getAccount().equals("admin"),"管理员admin用户不能修改");
        Assert.isTrue(user.getAccount().equals("cdm"),"数据治理cdm用户不能修改");
        user.setPassword(u.getPassword());
        user.setCreateUser(u.getCreateUser());
        user.setModifyUser(uId);
        user.setAccount(u.getAccount());
        user.setDelFlag(u.getDelFlag());
        user.setUpdateFlag(u.getUpdateFlag());
        //只有不为null的字段才参与更新
        userDao.save(user);
    }

}
