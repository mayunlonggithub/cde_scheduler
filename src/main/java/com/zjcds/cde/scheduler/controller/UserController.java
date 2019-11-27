package com.zjcds.cde.scheduler.controller;

import com.zjcds.cde.scheduler.base.*;
import com.zjcds.cde.scheduler.domain.dto.UserForm;
import com.zjcds.cde.scheduler.domain.entity.User;
import com.zjcds.cde.scheduler.service.UserService;
import com.zjcds.cde.scheduler.utils.Constant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author J on 20191105
 */
@RestController

@Api(description = "用户信息")
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ApiOperation(value = "用户登录", produces = "application/json;charset=utf-8")

    public ResponseResult<UserForm.User> login(@RequestBody UserForm.UserLogin userLogin, RedirectAttributes attr, HttpServletRequest request){
        if (null != userLogin && StringUtils.isNotBlank(userLogin.getAccount()) &&
                StringUtils.isNotBlank(userLogin.getPassword())){
            User user = userService.login(userLogin);
            UserForm.User owner = BeanPropertyCopyUtils.copy(user,UserForm.User.class);
            if (null != user){
                request.getSession().setAttribute(Constant.SESSION_ID, user);
                return new ResponseResult<>(true,"登录成功",owner);
            }
            return new ResponseResult<>(false,"登录失败,账号或密码不正确");
        }
        return new ResponseResult<>(false,"登录失败,账号或密码不能为空");
    }

    @PostMapping("/logout")
    @ApiOperation(value = "用户注销", produces = "application/json;charset=utf-8")

    public ResponseResult<Void> logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute(Constant.SESSION_ID);
        return new ResponseResult<>(true,"注销成功");
    }


    @GetMapping("/isAdmin")
    @ApiOperation(value = "是否管理员", produces = "application/json;charset=utf-8")

    public ResponseResult<Void> isAdmin(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(user,"未登录或登录已失效，请重新登录");
        boolean isAdmin = userService.isAdmin(user.getId());
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
    public ResponseResult<UserForm.User> getList(Paging paging, @RequestParam(required = false,name = "queryString") List<String> queryString, @RequestParam(required = false, name = "orderBy") List<String> orderBys, HttpServletRequest request){
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        if (CollectionUtils.isEmpty((Collection) queryString)) {
            queryString = new ArrayList();
        }
        if (CollectionUtils.isEmpty((Collection) orderBys)) {
            orderBys = new ArrayList();
            ((List) orderBys).add("createTimeDesc");
        }
        PageResult<User> user = userService.getList(paging,queryString,orderBys,kUser.getId());
        PageResult<UserForm.User> owner = PageUtils.copyPageResult(user,UserForm.User.class);
        return new ResponseResult(true,"请求成功",owner);
    }

    @DeleteMapping("delete/{id}")
    @ApiOperation(value = "删除用户", produces = "application/json;charset=utf-8")

    public ResponseResult<Void> delete(HttpServletRequest request,@PathVariable(required = true ,name = "id") Integer id){
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        Assert.notNull(id,"要删除的用户id不能为空");
        userService.delete(id,kUser.getId());
        return new ResponseResult(true,"请求成功");
    }

    @PostMapping("addTUser")
    @ApiOperation(value = "插入一个用户", produces = "application/json;charset=utf-8")

    public ResponseResult<Void> addTUser(@RequestBody UserForm.AddUser addTUser, HttpServletRequest request){
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        userService.addUser(addTUser,kUser.getId());
        return new ResponseResult(true,"请求成功");
    }


    @PutMapping("/updateUser/{id}")
    @ApiOperation(value = "修改用户信息", produces = "application/json;charset=utf-8")

    public ResponseResult<Void> updateUser(@PathVariable(required = true ,name = "id") Integer id,@RequestBody UserForm.UpdateUser updateUser, HttpServletRequest request){
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        userService.updateUser(updateUser,id,kUser.getId());
        return new ResponseResult(true,"请求成功");
    }

    @PostMapping("/isAccountExist/{account}")
    @ApiOperation(value = "判断账号是否存在", produces = "application/json;charset=utf-8")

    public ResponseResult<Void> isAccountExist(@PathVariable(required = true ,name = "account") String account, HttpServletRequest request){
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        boolean isAccountExist = userService.isAccountExist(account);
        if(isAccountExist){
            return new ResponseResult(true,"请求成功");
        }else {
            return new ResponseResult(false,"账号已存在");
        }
    }

    @PostMapping("/isAccountExist/{id}")
    @ApiOperation(value = "判断账号是否存在", produces = "application/json;charset=utf-8")

    public ResponseResult<Void> isAccountExist(@PathVariable(required = true ,name = "id") Integer id, HttpServletRequest request){
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        User user = userService.getUser(id);
        UserForm.User owner = BeanPropertyCopyUtils.copy(user,UserForm.User.class);
        return new ResponseResult(true,"请求成功",owner);
    }

}
