package com.zjcds.cde.scheduler.service;

import com.zjcds.cde.scheduler.base.PageResult;
import com.zjcds.cde.scheduler.base.Paging;
import com.zjcds.cde.scheduler.common.BaseResponse;
import com.zjcds.cde.scheduler.domain.dto.TUserForm;

import java.util.List;

/**
 * @author huangyj on 20190831
 */
public interface TUserService extends UserService {

    public PageResult<TUserForm.TUser> queryAllTUser(Paging paging, List<String> queryString, List<String> orderBys);

    public TUserForm.TUser addTUser(TUserForm.AddTUser addTUser);

    public TUserForm.TUser updateTUser(Integer id, TUserForm.UpdateTUser updateTUser);

    public void deleteTUser(Integer id);

    public List<TUserForm.AddRUserRole> addRUserRole(Integer userId, List<TUserForm.AddRUserRole> addRUserRole);

    public void resetPassword(Integer userId);

    public BaseResponse updatePassword(TUserForm.UpdatePassword updatePassword);

}
