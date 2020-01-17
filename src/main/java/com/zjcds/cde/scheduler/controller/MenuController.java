package com.zjcds.cde.scheduler.controller;

import com.zjcds.cde.scheduler.common.BaseResponse;
import com.zjcds.cde.scheduler.domain.dto.TMenuForm;
import com.zjcds.cde.scheduler.service.TMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author huangyj on 20190831
 */
@RestController
@RequestMapping("/tMenu")
@Api(value = "菜单信息模块", tags = {"B-菜单信息模块"})
public class MenuController {

    @Autowired
    private TMenuService tMenuService;

    @GetMapping
    @ApiOperation(value = "查询所有菜单树", produces = "application/json;charset=utf-8")
    public BaseResponse<List<TMenuForm.TMenuTree>> queryAllTMenu(){
        List<TMenuForm.TMenuTree> owner = tMenuService.queryAllTMenu();
        return new BaseResponse(owner);
    }

    @PostMapping("/addTMenu")
    @ApiOperation(value = "新增菜单信息", produces = "application/json;charset=utf-8")
    public BaseResponse<TMenuForm.TMenu> addTMenu(@RequestBody TMenuForm.AddTMenu addTMenu){
        TMenuForm.TMenu owner = tMenuService.addTMenu(addTMenu);
        return new BaseResponse(owner);

    }

    @PutMapping("/updateTMenu")
    @ApiOperation(value = "修改菜单信息", produces = "application/json;charset=utf-8")
    public BaseResponse<TMenuForm.TMenu> updateTMenu(Integer id, @RequestBody TMenuForm.UpdateTMenu updateTMenu){
        TMenuForm.TMenu owner = tMenuService.updateTMenu(id,updateTMenu);
        return new BaseResponse(owner);
    }

    @DeleteMapping("/deleteTMenu/{id}")
    @ApiOperation(value = "删除菜单信息", produces = "application/json;charset=utf-8")
    public BaseResponse<Void> deleteTMenu(@PathVariable("id") Integer id){
        Assert.notNull(id,"要删除的菜单id不能为空");
        tMenuService.deleteTMenu(id);
        return new BaseResponse();
    }

    @GetMapping("/{roleId}")
    @ApiOperation(value = "根据id查询角色", produces = "application/json;charset=utf-8")
    public BaseResponse<List<TMenuForm.TMenu>> findByUserId(@PathVariable("roleId") Integer roleId){
        List<TMenuForm.TMenu> owner = tMenuService.findByRoleId(roleId);
        return new BaseResponse(owner);
    }

    @GetMapping("/findMenu/{menuId}")
    @ApiOperation(value = "根据菜单id查询详情", produces = "application/json;charset=utf-8")
    public BaseResponse<TMenuForm.TMenu> findById(@PathVariable("menuId") Integer menuId){
        TMenuForm.TMenu owner = tMenuService.findById(menuId);
        return new BaseResponse(owner);
    }

    @GetMapping("/validMenu")
    @ApiOperation(value = "查询有效菜单树", produces = "application/json;charset=utf-8")
    public BaseResponse<List<TMenuForm.TMenuTree>> findValidTMenu(){
        List<TMenuForm.TMenuTree> owner = tMenuService.findValidTMenu();
        return new BaseResponse(owner);
    }
}
