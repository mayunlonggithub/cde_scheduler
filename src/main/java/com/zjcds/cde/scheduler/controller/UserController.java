package com.zjcds.cde.scheduler.controller;

import com.zjcds.cde.scheduler.base.PageResult;
import com.zjcds.cde.scheduler.base.Paging;
import com.zjcds.cde.scheduler.common.BaseResponse;
import com.zjcds.cde.scheduler.domain.dto.TUserForm;
import com.zjcds.cde.scheduler.service.TUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author huangyj on 20190831
 */
@RestController
@RequestMapping("/tUser")
@Api(value = "用户信息模块", tags = {"B-用户信息模块"})
public class UserController {

    @Autowired
    private TUserService tUserService;

    @GetMapping
    @ApiOperation(value = "查询所有用户信息", produces = "application/json;charset=utf-8")
    @ApiImplicitParams({@ApiImplicitParam(
            name = "pageIndex",
            value = "分页页码",
            defaultValue = "1",
            dataType = "int",
            paramType = "query"
    ), @ApiImplicitParam(
            name = "limit",
            value = "返回行数",
            defaultValue = "2147483647",
            dataType = "int",
            paramType = "query"
    ), @ApiImplicitParam(
            name = "queryString",
            value = "查询条件",
            defaultValue = "",
            dataType = "String",
            paramType = "query",
            allowMultiple = true
    ), @ApiImplicitParam(
            name = "orderBy",
            value = "排序",
            defaultValue = "",
            dataType = "String",
            paramType = "query",
            allowMultiple = true
    )})
    public BaseResponse<PageResult<TUserForm.TUser>> queryAllTUser(Paging paging, @RequestParam(required = false,name = "queryString") List<String> queryString, @RequestParam(required = false, name = "orderBy") List<String> orderBys){
        if (CollectionUtils.isEmpty((Collection) orderBys)) {
            orderBys = new ArrayList();
            ((List) orderBys).add("modifyTimeDesc");
        }
        PageResult<TUserForm.TUser> owner = tUserService.queryAllTUser(paging,queryString,orderBys);
        return new BaseResponse<>(owner);
    }

    @PostMapping
    @ApiOperation(value = "新增用户信息", produces = "application/json;charset=utf-8")
    public BaseResponse<TUserForm.TUser> addTUser(@RequestBody TUserForm.AddTUser addTUser){
        TUserForm.TUser owner = tUserService.addTUser(addTUser);
        return new BaseResponse(owner);
    }

    @PutMapping
    @ApiOperation(value = "修改用户信息", produces = "application/json;charset=utf-8")
    public BaseResponse<TUserForm.TUser> updateTUser(Integer id, @RequestBody TUserForm.UpdateTUser updateTUser){
        TUserForm.TUser owner = tUserService.updateTUser(id,updateTUser);
        return new BaseResponse(owner);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除用户信息", produces = "application/json;charset=utf-8")
    public BaseResponse<Void> deleteTUser(@PathVariable("id") Integer id){
        Assert.notNull(id,"要删除的用户id不能为空");
        tUserService.deleteTUser(id);
        return new BaseResponse();
    }

    @PostMapping("/{userId}")
    @ApiOperation(value = "新增用户角色绑定信息", produces = "application/json;charset=utf-8")
    public BaseResponse<List<TUserForm.AddRUserRole>> addRUserRole(@PathVariable(required = true ,name = "userId") Integer userId, @RequestBody List<TUserForm.AddRUserRole> addRUserRole){
        List<TUserForm.AddRUserRole> owner = tUserService.addRUserRole(userId,addRUserRole);
        return new BaseResponse(owner);
    }

    @PostMapping("/resetPassword/{userId}")
    @ApiOperation(value = "重置密码", produces = "application/json;charset=utf-8")
    public BaseResponse<Void> resetPassword(@PathVariable(required = true ,name = "userId") Integer userId){
        tUserService.resetPassword(userId);
        return new BaseResponse("重置成功");
    }

    @PostMapping("/updatePassword")
    @ApiOperation(value = "修改密码", produces = "application/json;charset=utf-8")
    public BaseResponse<Void> updatePassword(@RequestBody TUserForm.UpdatePassword updatePassword){
        BaseResponse owner = tUserService.updatePassword(updatePassword);
        return owner;
    }

}
