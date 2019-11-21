package com.zjcds.cde.scheduler.controller;

import com.zjcds.cde.scheduler.domain.dto.RepositoryForm;
import com.zjcds.cde.scheduler.domain.dto.RepositoryTreeForm;
import com.zjcds.cde.scheduler.domain.dto.RepositoryTypeForm;
import com.zjcds.cde.scheduler.domain.dto.UserForm;
import com.zjcds.cde.scheduler.domain.entity.Repository;
import com.zjcds.cde.scheduler.domain.entity.RepositoryType;
import com.zjcds.cde.scheduler.domain.entity.User;
import com.zjcds.cde.scheduler.service.RepositoryService;
import com.zjcds.cde.scheduler.utils.Constant;
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
@JsonViewException
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
    public ResponseResult<RepositoryForm.Repository> getList(Paging paging, @RequestParam(required = false,name = "queryString") List<String> queryString, @RequestParam(required = false, name = "orderBy") List<String> orderBys, HttpServletRequest request){
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        if (CollectionUtils.isEmpty((Collection) queryString)) {
            queryString = new ArrayList();
        }
        if (CollectionUtils.isEmpty((Collection) orderBys)) {
            orderBys = new ArrayList();
        }
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        PageResult<Repository> repository = repositoryService.getList(paging,queryString, orderBys, kUser.getId());
        PageResult<RepositoryForm.Repository>  owner = PageUtils.copyPageResult(repository,RepositoryForm.Repository.class);
        return new ResponseResult(true,"请求成功",owner);
    }

    @GetMapping("/getTreeList/{repositoryId}")
    @ApiOperation(value = "获取资源库树信息", produces = "application/json;charset=utf-8")
    @JsonViewException
    public ResponseResult<List<RepositoryTreeForm>> getTreeList(@PathVariable(required = true ,name = "repositoryId") Integer repositoryId) throws KettleException{
        List<RepositoryTreeForm> repositoryTreeFormList = repositoryService.getTreeList(repositoryId);
        return new ResponseResult<>(true,"请求成功",repositoryTreeFormList);
    }


    @PostMapping("/insert")
    @ApiOperation(value = "插入资源库信息", produces = "application/json;charset=utf-8")
    @JsonViewException
    public ResponseResult<Void> insert(@RequestBody RepositoryForm.AddRepository addRepository, HttpServletRequest request)throws KettleException{
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        repositoryService.insert(addRepository,kUser.getId());
        return new ResponseResult(true,"请求成功");
    }


    @PutMapping("/update")
    @ApiOperation(value = "修改资源库信息", produces = "application/json;charset=utf-8")
    @JsonViewException
    public ResponseResult<Void> update(@RequestBody RepositoryForm.UpdateRepository updateRepository, HttpServletRequest request){
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        repositoryService.update(updateRepository,kUser.getId());
        return new ResponseResult(true,"请求成功");
    }

    @DeleteMapping("/delete}")
    @ApiOperation(value = "删除资源库信息", produces = "application/json;charset=utf-8")
    @JsonViewException
    public ResponseResult<Void> delete(HttpServletRequest request){
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        repositoryService.delete(kUser.getId());
        return new ResponseResult(true,"请求成功");
    }

    @PostMapping("/check")
    @ApiOperation(value = "检查资源库连接", produces = "application/json;charset=utf-8")
    @JsonViewException
    public ResponseResult<Void> check(@RequestBody RepositoryForm.AddRepository addRepository)throws KettleException{
        boolean check = repositoryService.check(addRepository);
        if(check){
            return new ResponseResult(true,"资源库连接成功");
        }else {
            return new ResponseResult(false,"资源库连接失败,请检查连接信息");
        }
    }

    @GetMapping("/getType")
    @ApiOperation(value = "获取资源库类别列表", produces = "application/json;charset=utf-8")
    @JsonViewException
    public ResponseResult<RepositoryTypeForm.RepositoryType> getType(){
        List<RepositoryType> repositoryTypes = repositoryService.getRepositoryTypeList();
        List<RepositoryTypeForm.RepositoryType> owner = BeanPropertyCopyUtils.copy(repositoryTypes,RepositoryTypeForm.RepositoryType.class);
        return new ResponseResult(true,"请求成功",owner);

    }

    @GetMapping("/getAccess")
    @ApiOperation(value = "获取资源库访问类型", produces = "application/json;charset=utf-8")
    @JsonViewException
    public ResponseResult<List<String>> getAccess(){
        List<String> access = repositoryService.getAccess();
        return new ResponseResult(true,"请求成功",access);

    }


    @GetMapping("/getRepository/{repositoryId}")
    @ApiOperation(value = "获取资源库对象", produces = "application/json;charset=utf-8")
    @JsonViewException
    public ResponseResult<List<String>> getRepository(@PathVariable(required = true ,name = "repositoryId") Integer repositoryId){
        Repository repository = repositoryService.getRepository(repositoryId);
        RepositoryForm.Repository owner = BeanPropertyCopyUtils.copy(repository,RepositoryForm.Repository.class);
        return new ResponseResult(false,"请求成功",owner);

    }
}