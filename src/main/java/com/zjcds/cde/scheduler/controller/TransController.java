package com.zjcds.cde.scheduler.controller;

import com.zjcds.cde.scheduler.domain.dto.TransForm;
import com.zjcds.cde.scheduler.domain.entity.Trans;
import com.zjcds.cde.scheduler.domain.entity.User;
import com.zjcds.cde.scheduler.service.TransService;
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
 * @author J on 20191121
 */
@RestController
@JsonViewException
@Api(description = "转换管理")
@RequestMapping("/trans")
public class TransController {

    @Autowired
    private TransService transService;

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
    public ResponseResult<TransForm.Trans> getList(Paging paging, @RequestParam(required = false,name = "queryString") List<String> queryString, @RequestParam(required = false, name = "orderBy") List<String> orderBys, HttpServletRequest request){
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        if (CollectionUtils.isEmpty((Collection) queryString)) {
            queryString = new ArrayList();
        }
        if (CollectionUtils.isEmpty((Collection) orderBys)) {
            orderBys = new ArrayList();
        }
        PageResult<Trans> trans = transService.getList(paging,queryString, orderBys, kUser.getId());
        PageResult<TransForm.Trans>  owner = PageUtils.copyPageResult(trans,TransForm.Trans.class);
        return new ResponseResult(true,"请求成功",owner);
    }


//    @PostMapping("/insert/{uId}")
//    @ApiOperation(value = "插入转换信息", produces = "application/json;charset=utf-8")
//    @JsonViewException
//    public ResponseResult<Void> insert(@RequestBody TransForm.AddTrans addTrans, @PathVariable(required = true ,name = "uId") Integer uId){
//        transService.insert(addTrans,uId);
//        return new ResponseResult(true,"请求成功");
//    }


    @PutMapping("/update/{transId}")
    @ApiOperation(value = "修改转换信息", produces = "application/json;charset=utf-8")
    @JsonViewException
    public ResponseResult<Void> update(@RequestBody TransForm.UpdateTrans updateTrans, @PathVariable(required = true ,name = "transId") Integer transId,HttpServletRequest request){
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        transService.update(updateTrans,transId,kUser.getId());
        return new ResponseResult(true,"请求成功");
    }

    @DeleteMapping("/delete/{transId}")
    @ApiOperation(value = "删除转换信息", produces = "application/json;charset=utf-8")
    @JsonViewException
    public ResponseResult<Void> delete(@PathVariable(required = true ,name ="transId") Integer transId,HttpServletRequest request){
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        Assert.notNull(transId,"要删除的转换id不能为空");
        transService.delete(transId,kUser.getId());
        return new ResponseResult(true,"请求成功");
    }

    @GetMapping("/getTrans/{transId}")
    @ApiOperation(value = "获取转换信息", produces = "application/json;charset=utf-8")
    @JsonViewException
    public ResponseResult<TransForm.Trans> getTrans(@PathVariable(required = true ,name = "transId") Integer transId,HttpServletRequest request){
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        Trans trans = transService.getTrans(transId,kUser.getId());
        TransForm.Trans owner = BeanPropertyCopyUtils.copy(trans,TransForm.Trans.class);
        return new ResponseResult(false,"请求成功",owner);
    }

    @PutMapping("/start/{transId}")
    @ApiOperation(value = "开始转换", produces = "application/json;charset=utf-8")
    @JsonViewException
    public ResponseResult<Void> start(@RequestBody TransForm.TransParam transParam, @PathVariable(required = true ,name = "transId") Integer transId, HttpServletRequest request)throws KettleException {
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        transService.start(transId,kUser.getId(),transParam.getParam());
        return new ResponseResult(true,"请求成功");
    }
}
