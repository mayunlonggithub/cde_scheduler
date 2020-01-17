package com.zjcds.cde.scheduler.controller;

import com.zjcds.cde.scheduler.base.*;
import com.zjcds.cde.scheduler.domain.dto.TransForm;
import com.zjcds.cde.scheduler.domain.entity.Trans;
import com.zjcds.cde.scheduler.domain.entity.User;
import com.zjcds.cde.scheduler.service.TransService;
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author J on 20191121
 */
@RestController

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
    public ResponseResult<TransForm.Trans> getList(Paging paging, @RequestParam(required = false,name = "queryString") List<String> queryString, @RequestParam(required = false, name = "orderBy") List<String> orderBys, HttpServletRequest request){
        Integer userId= WebSecurityUtils.currentUserId();
        if (CollectionUtils.isEmpty((Collection) queryString)) {
            queryString = new ArrayList();
        }
        if (CollectionUtils.isEmpty((Collection) orderBys)) {
            orderBys = new ArrayList();
            ((List) orderBys).add("transQuartzDesc");
        }
        PageResult<Trans> trans = transService.getList(paging,queryString, orderBys, userId);
        PageResult<TransForm.Trans>  owner = PageUtils.copyPageResult(trans,TransForm.Trans.class);
        return new ResponseResult(true,"请求成功",owner);
    }


//    @PostMapping("/insert/{uId}")
//    @ApiOperation(value = "插入转换信息", produces = "application/json;charset=utf-8")
//
//    public ResponseResult<Void> insert(@RequestBody TransForm.AddTrans addTrans, @PathVariable(required = true ,name = "uId") Integer uId){
//        transService.insert(addTrans,uId);
//        return new ResponseResult(true,"请求成功");
//    }


    @PutMapping("/update/{transId}")
    @ApiOperation(value = "修改转换信息", produces = "application/json;charset=utf-8")

    public ResponseResult<Void> update(@RequestBody TransForm.UpdateTrans updateTrans, @PathVariable(required = true ,name = "transId") Integer transId,HttpServletRequest request) throws ParseException {
        Integer userId=WebSecurityUtils.currentUserId();
        transService.update(updateTrans,transId,userId);
        return new ResponseResult(true,"请求成功");
    }

    @DeleteMapping("/delete/{transId}")
    @ApiOperation(value = "删除转换信息", produces = "application/json;charset=utf-8")

    public ResponseResult<Void> delete(@PathVariable(required = true ,name ="transId") Integer transId,HttpServletRequest request){ ;
        Assert.notNull(transId,"要删除的转换id不能为空");
        Integer userId=WebSecurityUtils.currentUserId();
        transService.delete(transId,userId);
        return new ResponseResult(true,"请求成功");
    }

    @GetMapping("/getTrans/{transId}")
    @ApiOperation(value = "获取转换信息", produces = "application/json;charset=utf-8")

    public ResponseResult<TransForm.Trans> getTrans(@PathVariable(required = true ,name = "transId") Integer transId,HttpServletRequest request){
        Integer userId=WebSecurityUtils.currentUserId();
        Trans trans = transService.getTrans(transId,userId);
        TransForm.Trans owner = BeanPropertyCopyUtils.copy(trans,TransForm.Trans.class);
        return new ResponseResult(false,"请求成功",owner);
    }

    @PutMapping("/start/{transId}")
    @ApiOperation(value = "开始转换", produces = "application/json;charset=utf-8")

    public ResponseResult<Void> start(@RequestBody TransForm.TransParam transParam, @PathVariable(required = true ,name = "transId") Integer transId, HttpServletRequest request) throws KettleException, ParseException {
        Integer userId=WebSecurityUtils.currentUserId();
        transService.start(transId,userId,transParam.getParam(),1,0);
        return new ResponseResult(true,"请求成功");
    }
}
