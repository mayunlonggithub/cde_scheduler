package com.zjcds.cde.scheduler.controller;

import com.zjcds.cde.scheduler.base.PageResult;
import com.zjcds.cde.scheduler.base.Paging;
import com.zjcds.cde.scheduler.common.BaseResponse;
import com.zjcds.cde.scheduler.domain.dto.TRoleForm;
import com.zjcds.cde.scheduler.service.TRoleService;
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
@RequestMapping("/tRole")
@Api(value = "角色信息模块", tags = {"B-角色信息模块"})
public class RoleController {

    @Autowired
    private TRoleService tRoleService;

    @GetMapping
    @ApiOperation(value = "查询所有角色信息", produces = "application/json;charset=utf-8")
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
    public BaseResponse<PageResult<TRoleForm.TRole>> queryAllTRole(Paging paging, @RequestParam(required = false,name = "queryString") List<String> queryString, @RequestParam(required = false, name = "orderBy") List<String> orderBys){
        if (CollectionUtils.isEmpty((Collection) orderBys)) {
            orderBys = new ArrayList();
            ((List) orderBys).add("modifyTimeDesc");
        }
        PageResult<TRoleForm.TRole> owner = tRoleService.queryAllTRole(paging,queryString,orderBys);
        return new BaseResponse<>(owner);
    }

    @PostMapping
    @ApiOperation(value = "新增角色信息", produces = "application/json;charset=utf-8")
    public BaseResponse<TRoleForm.TRole> addTRole(@RequestBody TRoleForm.AddTRole addTRole){
        TRoleForm.TRole owner = tRoleService.addTRole(addTRole);
        return new BaseResponse(owner);

    }

    @PutMapping
    @ApiOperation(value = "修改角色信息", produces = "application/json;charset=utf-8")
    public BaseResponse<TRoleForm.TRole> updateTRole(Integer id, @RequestBody TRoleForm.UpdateTRole updateTRole){
        TRoleForm.TRole owner = tRoleService.updateTRole(id,updateTRole);
        return new BaseResponse(owner);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除角色信息", produces = "application/json;charset=utf-8")
    public BaseResponse<Void> deleteTRole(@PathVariable("id") Integer id){
        Assert.notNull(id,"要删除的角色id不能为空");
        tRoleService.deleteTRole(id);
        return new BaseResponse();
    }

    @PostMapping("/{roleId}")
    @ApiOperation(value = "新增角色菜单绑定信息", produces = "application/json;charset=utf-8")
    public BaseResponse<List<TRoleForm.AddRRoleMenu>> addRRoleMenu(@PathVariable(required = true ,name = "roleId") Integer roleId, @RequestBody List<TRoleForm.AddRRoleMenu> addRRoleMenus){
        List<TRoleForm.AddRRoleMenu> owner = tRoleService.addRRoleMenu(roleId,addRRoleMenus);
        return new BaseResponse(owner);
    }

    @GetMapping("/{userId}")
    @ApiOperation(value = "根据用户id查询角色", produces = "application/json;charset=utf-8")
    public BaseResponse<List<TRoleForm.TRole>> findByUserId(@PathVariable("userId") Integer userId){
        List<TRoleForm.TRole> owner = tRoleService.findByUserId(userId);
        return new BaseResponse(owner);
    }
}
