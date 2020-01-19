package com.zjcds.cde.scheduler.service.Impl;

import com.zjcds.cde.scheduler.base.BeanPropertyCopyUtils;
import com.zjcds.cde.scheduler.base.PageResult;
import com.zjcds.cde.scheduler.base.PageUtils;
import com.zjcds.cde.scheduler.base.Paging;
import com.zjcds.cde.scheduler.common.BaseResponse;
import com.zjcds.cde.scheduler.common.ErrorEnum;
import com.zjcds.cde.scheduler.dao.jpa.UserDao;
import com.zjcds.cde.scheduler.dao.jpa.UserRoleDao;
import com.zjcds.cde.scheduler.domain.dto.TUserForm;
import com.zjcds.cde.scheduler.domain.entity.User;
import com.zjcds.cde.scheduler.domain.entity.UserRole;
import com.zjcds.cde.scheduler.service.TDepartmentService;
import com.zjcds.cde.scheduler.service.TUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;

/**
 * @author huangyj on 20190831
 */
@Service
public class TUserServiceImpl extends UserServiceImpl implements TUserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private TDepartmentService tDepartmentService;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * 分页查询用户
     * @param paging
     * @param queryString
     * @param orderBys
     * @return
     */
    @Override
    public PageResult<TUserForm.TUser> queryAllTUser(Paging paging, List<String> queryString, List<String> orderBys){
        PageResult<User> tUser = userDao.findAll(paging,queryString,orderBys);
        PageResult<TUserForm.TUser> owner = PageUtils.copyPageResult(tUser, TUserForm.TUser.class);
        List<TUserForm.TUser> ownerList = owner.getContent();
        Map<Integer,String> map = tDepartmentService.TDepartmentMap();
        for (TUserForm.TUser u : ownerList){
            if(u.getDeptId()!=null){
                u.getDeptId().setValue( map.get(Integer.valueOf(u.getDeptId().getKey())));
            }
            if(u.getDeptPid()!=null){
                u.getDeptPid().setValue(map.get(Integer.valueOf(u.getDeptPid().getKey())));}
        }
        return owner;
    }

    /**
     * 新增用户
     * @param addTUser
     * @return
     */
    @Override
    @Transactional
    public TUserForm.TUser addTUser(TUserForm.AddTUser addTUser){
        User user = userDao.findByAccount(addTUser.getAccount());
        Assert.isNull(user,"该用户已存在");
        user = BeanPropertyCopyUtils.copy(addTUser, User.class);
        user.setPassword(passwordEncoder.encode("123456"));
        if(addTUser.getTDepartment().length==1){
        user.setDeptPid(addTUser.getTDepartment()[0]);}
        else {
            user.setDeptPid(addTUser.getTDepartment()[0]);
            user.setDeptId(addTUser.getTDepartment()[1]);
        }

        user = userDao.save(user);
        TUserForm.TUser owner = BeanPropertyCopyUtils.copy(user, TUserForm.TUser.class);
        Map<Integer,String> map = tDepartmentService.TDepartmentMap();
        if(owner.getDeptId()!=null){
            owner.getDeptId().setValue( map.get(Integer.valueOf(owner.getDeptId().getKey())));

        }
        if(owner.getDeptPid()!=null){
            owner.getDeptPid().setValue(map.get(Integer.valueOf(owner.getDeptPid().getKey())));}
        return owner;
    }

    /**
     * 修改用户
     * @param id
     * @param updateTUser
     * @return
     */
    @Override
    @Transactional
    public TUserForm.TUser updateTUser(Integer id, TUserForm.UpdateTUser updateTUser){
        Assert.notNull(id,"要修改的id不能为空");
        Optional<User> tUserOptional = userDao.findById(id);
        User user = new User();
        if(tUserOptional.isPresent()){
            user = tUserOptional.get();
        }
        Assert.notNull(user,"该数据不存在或已删除");
        String password = user.getPassword();
        user = BeanPropertyCopyUtils.copy(updateTUser, User.class);
        user.setPassword(password);
        if(updateTUser.getTDepartment().length==1){
            user.setDeptPid(updateTUser.getTDepartment()[0]);}
        else {
            user.setDeptPid(updateTUser.getTDepartment()[0]);
            user.setDeptId(updateTUser.getTDepartment()[1]);
        }
        user = userDao.save(user);
        TUserForm.TUser owner = BeanPropertyCopyUtils.copy(user, TUserForm.TUser.class);
        Map<Integer,String> map = tDepartmentService.TDepartmentMap();
        if(owner.getDeptId()!=null){
            owner.getDeptId().setValue( map.get(Integer.valueOf(owner.getDeptId().getKey())));

        }
        if(owner.getDeptPid()!=null){
            owner.getDeptPid().setValue(map.get(Integer.valueOf(owner.getDeptPid().getKey())));}
        return owner;
    }

    /**
     * 删除用户
     * @param id
     */
    @Override
    @Transactional
    public void deleteTUser(Integer id){
        Assert.notNull(id,"要修改的id不能为空");
        User user = userDao.getOne(id);
        Assert.notNull(user,"该数据不存在或已删除");
        userDao.deleteById(id);
        userRoleDao.deleteByUserId(id);
    }

    /**
     * 新增用户角色关系
     * @param userId
     * @param addRUserRole
     * @return
     */
    @Override
    @Transactional
    public List<TUserForm.AddRUserRole> addRUserRole(Integer userId, List<TUserForm.AddRUserRole> addRUserRole){
        List<UserRole> userRoles = BeanPropertyCopyUtils.copy(addRUserRole, UserRole.class);
        // 删除该用户角色关系
         List<Integer> roleIdList=userRoleDao.findByUserId(userId);
         if(roleIdList.size()!=0){
        userRoleDao.deleteByUserId(userId);}
        // 保存角色关系
        userRoles = userRoleDao.saveAll(userRoles);
        List<TUserForm.AddRUserRole> owner = BeanPropertyCopyUtils.copy(userRoles, TUserForm.AddRUserRole.class);
        return owner;
    }

    /**
     * 重置密码
     * @param userId
     */
    @Override
    public void resetPassword(Integer userId){
        Assert.notNull(userId,"用户id不能为空");
        Optional<User> userOptional = userDao.findById(userId);
        User user = new User();
        if (userOptional.isPresent()){
            user=userOptional.get();
        }
        Assert.notNull(user,"用户不存在或已删除");
        user.setPassword(passwordEncoder.encode("123456"));
        userDao.save(user);
    }

    /**
     * 修改密码
     * @param updatePassword
     */
    public BaseResponse updatePassword(TUserForm.UpdatePassword updatePassword){
        Assert.notNull(updatePassword.getUserId(),"用户id不能为空");
        Assert.hasText(updatePassword.getOldPassword(),"旧密码不能为空");
        Assert.hasText(updatePassword.getNewPassword(),"新密码不能为空");
        Optional<User> userOptional = userDao.findById(updatePassword.getUserId());
        User user = new User();
        if (userOptional.isPresent()){
            user=userOptional.get();
        }
        if(!passwordEncoder.matches(updatePassword.getOldPassword(),user.getPassword())){
            return new BaseResponse(ErrorEnum.ERROR1008);
        }
        user.setPassword(passwordEncoder.encode(updatePassword.getNewPassword()));
        userDao.save(user);
        return new BaseResponse();
    }
}
