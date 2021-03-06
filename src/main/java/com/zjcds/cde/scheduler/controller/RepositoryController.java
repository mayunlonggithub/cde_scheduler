package com.zjcds.cde.scheduler.controller;

import com.zjcds.cde.scheduler.base.*;
import com.zjcds.cde.scheduler.domain.dto.RepositoryForm;
import com.zjcds.cde.scheduler.domain.dto.RepositoryTreeForm;
import com.zjcds.cde.scheduler.domain.dto.RepositoryTypeForm;
import com.zjcds.cde.scheduler.domain.entity.Repository;
import com.zjcds.cde.scheduler.domain.entity.RepositoryType;
import com.zjcds.cde.scheduler.domain.entity.User;
import com.zjcds.cde.scheduler.service.RepositoryService;
import com.zjcds.cde.scheduler.utils.Constant;
import com.zjcds.cde.scheduler.utils.WebSecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.pentaho.di.core.exception.KettleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author J on 20191113
 */
@RestController

@Api(description = "资源库管理")
@RequestMapping("/repository")
public class RepositoryController {

    @Autowired
    private RepositoryService repositoryService;


    @GetMapping("/getList")
    @ApiOperation(value = "获取列表信息", produces = "application/json;charset=utf-8")
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
    public ResponseResult<RepositoryForm.Repository> getList(Paging paging, @RequestParam(required = false,name = "queryString") List<String> queryString, @RequestParam(required = false, name = "orderBy") List<String> orderBys, HttpServletRequest request){
        Integer userId= WebSecurityUtils.currentUserId();
        if (CollectionUtils.isEmpty((Collection) queryString)) {
            queryString = new ArrayList();
        }
        if (CollectionUtils.isEmpty((Collection) orderBys)) {
            orderBys = new ArrayList();
            ((List) orderBys).add("createTimeDesc");
        }
        PageResult<Repository> repository = repositoryService.getList(paging,queryString, orderBys, userId);
        PageResult<RepositoryForm.Repository>  owner = PageUtils.copyPageResult(repository,RepositoryForm.Repository.class);
        return new ResponseResult(true,"请求成功",owner);
    }

    @GetMapping("/getTreeList/{repositoryId}")
    @ApiOperation(value = "获取资源库树信息", produces = "application/json;charset=utf-8")

    public ResponseResult<List<RepositoryTreeForm>> getTreeList(@PathVariable(required = true ,name = "repositoryId") Integer repositoryId,HttpServletRequest request) throws KettleException{
        Integer userId=WebSecurityUtils.currentUserId();
        List<RepositoryTreeForm> repositoryTreeFormList = repositoryService.getTreeList(repositoryId,userId);
        return new ResponseResult<>(true,"请求成功",repositoryTreeFormList);
    }


    @PostMapping("/insert")
    @ApiOperation(value = "插入资源库信息", produces = "application/json;charset=utf-8")

    public ResponseResult<Void> insert(@RequestBody RepositoryForm.AddRepository addRepository, HttpServletRequest request)throws KettleException{
        Integer userId=WebSecurityUtils.currentUserId();
        repositoryService.insert(addRepository,userId);
        return new ResponseResult(true,"请求成功");
    }


    @PutMapping("/update/{repositoryId}")
    @ApiOperation(value = "修改资源库信息", produces = "application/json;charset=utf-8")

    public ResponseResult<Void> update(@RequestBody RepositoryForm.UpdateRepository updateRepository,@PathVariable(required = true ,name = "repositoryId") Integer repositoryId, HttpServletRequest request){
        Integer userId=WebSecurityUtils.currentUserId();
        repositoryService.update(updateRepository,repositoryId,userId);
        return new ResponseResult(true,"请求成功");
    }

    @DeleteMapping("/delete/{repositoryId}")
    @ApiOperation(value = "删除资源库信息", produces = "application/json;charset=utf-8")

    public ResponseResult<Void> delete(@PathVariable(required = true ,name = "repositoryId") Integer repositoryId,HttpServletRequest request){
        Integer userId=WebSecurityUtils.currentUserId();
        repositoryService.delete(repositoryId,userId);
        return new ResponseResult(true,"请求成功");
    }

    @PostMapping("/check")
    @ApiOperation(value = "检查资源库连接", produces = "application/json;charset=utf-8")

    public ResponseResult<Void> check(@RequestBody RepositoryForm.AddRepository addRepository,HttpServletRequest request)throws KettleException{
        Integer userId=WebSecurityUtils.currentUserId();
        boolean check = repositoryService.check(addRepository,userId);
        if(check){
            return new ResponseResult(true,"资源库连接成功");
        }else {
            return new ResponseResult(false,"资源库连接失败,请检查连接信息");
        }
    }

    @GetMapping("/getType")
    @ApiOperation(value = "获取资源库类别列表", produces = "application/json;charset=utf-8")

    public ResponseResult<RepositoryTypeForm.RepositoryType> getType(HttpServletRequest request){
        Integer userId=WebSecurityUtils.currentUserId();
        List<RepositoryType> repositoryTypes = repositoryService.getRepositoryTypeList();
        List<RepositoryTypeForm.RepositoryType> owner = BeanPropertyCopyUtils.copy(repositoryTypes,RepositoryTypeForm.RepositoryType.class);
        return new ResponseResult(true,"请求成功",owner);

    }

    @GetMapping("/getAccess")
    @ApiOperation(value = "获取资源库访问类型", produces = "application/json;charset=utf-8")

    public ResponseResult<List<String>> getAccess(HttpServletRequest request){
        Integer userId=WebSecurityUtils.currentUserId();
        List<String> access = repositoryService.getAccess();
        return new ResponseResult(true,"请求成功",access);

    }


    @GetMapping("/getRepository/{repositoryId}")
    @ApiOperation(value = "获取资源库对象", produces = "application/json;charset=utf-8")

    public ResponseResult<List<String>> getRepository(@PathVariable(required = true ,name = "repositoryId") Integer repositoryId,HttpServletRequest request){
        Integer userId=WebSecurityUtils.currentUserId();
        Repository repository = repositoryService.getRepository(repositoryId,userId);
        RepositoryForm.Repository owner = BeanPropertyCopyUtils.copy(repository,RepositoryForm.Repository.class);
        return new ResponseResult(false,"请求成功",owner);

    }

    @PostMapping("/saveTreeList/{repositoryId}")
    @ApiOperation(value = "获取资源库的所有作业和转换任务进行保存", produces = "application/json;charset=utf-8")

    public ResponseResult<Void> saveTreeList(@PathVariable(required = true ,name = "repositoryId") Integer repositoryId,HttpServletRequest request)throws KettleException{
        Integer userId=WebSecurityUtils.currentUserId();
        repositoryService.saveTreeList(repositoryId,userId);
        return new ResponseResult(true,"请求成功");
    }
}
