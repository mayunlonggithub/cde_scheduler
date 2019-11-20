package com.zjcds.cde.scheduler.controller;

import com.zjcds.cde.scheduler.domain.dto.UserForm;
import com.zjcds.cde.scheduler.domain.entity.User;
import com.zjcds.cde.scheduler.service.UserService;
import com.zjcds.common.base.domain.page.Paging;
import com.zjcds.common.dozer.BeanPropertyCopyUtils;
import com.zjcds.common.jpa.PageResult;
import com.zjcds.common.jpa.utils.PageUtils;
import com.zjcds.common.jsonview.annotations.JsonViewException;
import com.zjcds.common.jsonview.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author J on 20191105
 */
@RestController
@JsonViewException
@Api(description = "用户信息")
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ApiOperation(value = "用户登录", produces = "application/json;charset=utf-8")
    @JsonViewException
    public ResponseResult<UserForm.User> login(@RequestBody UserForm.UserLogin userLogin){
        User user = userService.login(userLogin);
        UserForm.User owner = BeanPropertyCopyUtils.copy(user,UserForm.User.class);
        return new ResponseResult(true,"请求成功",owner);
    }


    @GetMapping("/isAdmin/{uId}")
    @ApiOperation(value = "是否管理员", produces = "application/json;charset=utf-8")
    @JsonViewException
    public ResponseResult<Void> isAdmin(@PathVariable(required = true ,name = "uId") Integer uId){
        boolean isAdmin = userService.isAdmin(uId);
        return new ResponseResult(true,"请求成功",isAdmin);
    }

    @GetMapping("/getList")
    @ApiOperation(value = "获取用户分页列表", produces = "application/json;charset=utf-8")
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
            defaultValue = "field~Eq~1234",
            dataType = "String",
            paramType = "query",
            allowMultiple = true
    ), @ApiImplicitParam(
            name = "orderBy",
            value = "排序",
            defaultValue = "field1Desc",
            dataType = "String",
            paramType = "query",
            allowMultiple = true
    )})
    public ResponseResult<Void> getList(Paging paging, @RequestParam(required = false,name = "queryString") List<String> queryString, @RequestParam(required = false, name = "orderBy") List<String> orderBys){
        PageResult<User> user = userService.getList(paging,queryString,orderBys);
        PageResult<UserForm.User> owner = PageUtils.copyPageResult(user,UserForm.User.class);
        return new ResponseResult(true,"请求成功",owner);
    }

    @DeleteMapping("delete/{uId}")
    @ApiOperation(value = "删除用户", produces = "application/json;charset=utf-8")
    public ResponseResult<Void> delete(@PathVariable("uId") Integer uId){
        Assert.notNull(uId,"要删除的用户id不能为空");
        userService.delete(uId);
        return new ResponseResult(true,"请求成功");
    }

    @PostMapping("addTUser/{uId}")
    @ApiOperation(value = "插入一个用户", produces = "application/json;charset=utf-8")
    public ResponseResult<Void> addTUser(@RequestBody UserForm.AddUser addTUser,@PathVariable(required = true ,name = "uId") Integer uId){
        userService.addUser(addTUser,uId);
        return new ResponseResult(true,"请求成功");
    }


    @PutMapping("/updateUser/{uId}")
    @ApiOperation(value = "修改用户信息", produces = "application/json;charset=utf-8")
    public ResponseResult<Void> updateUser(@PathVariable(required = true ,name = "uId") Integer uId,@RequestBody UserForm.UpdateUser updateUser){
        userService.updateUser(updateUser,uId);
        return new ResponseResult(true,"请求成功");
    }

}
