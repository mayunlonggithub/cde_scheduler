package com.zjcds.cde.scheduler.controller;

import com.zjcds.cde.scheduler.base.PageResult;
import com.zjcds.cde.scheduler.base.PageUtils;
import com.zjcds.cde.scheduler.base.Paging;
import com.zjcds.cde.scheduler.base.ResponseResult;
import com.zjcds.cde.scheduler.domain.dto.TransForm;
import com.zjcds.cde.scheduler.domain.dto.TransMonitorForm;
import com.zjcds.cde.scheduler.domain.dto.TransRecordForm;
import com.zjcds.cde.scheduler.domain.entity.TransMonitor;
import com.zjcds.cde.scheduler.domain.entity.TransRecord;
import com.zjcds.cde.scheduler.domain.entity.User;
import com.zjcds.cde.scheduler.domain.entity.view.TransMonitorView;
import com.zjcds.cde.scheduler.domain.entity.view.TransRecordView;
import com.zjcds.cde.scheduler.service.TransMonitorService;
import com.zjcds.cde.scheduler.service.TransRecordService;
import com.zjcds.cde.scheduler.utils.Constant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author J on 20191121
 */
@RestController

@Api(description = "转换监控管理")
@RequestMapping("/TransMonitor")
public class TransMonitorController {

    @Autowired
    private TransMonitorService transMonitorService;
    @Autowired
    private TransRecordService transRecordService;

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
    public ResponseResult<TransMonitorForm.TransMonitor> getList(Paging paging, @RequestParam(required = false,name = "queryString") List<String> queryString, @RequestParam(required = false, name = "orderBy") List<String> orderBys, HttpServletRequest request){
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        if (CollectionUtils.isEmpty((Collection) queryString)) {
            queryString = new ArrayList();
        }
        if (CollectionUtils.isEmpty((Collection) orderBys)) {
            orderBys = new ArrayList();
        }
        PageResult<TransMonitorView> trans = transMonitorService.getList(paging,queryString, orderBys, kUser.getId());
        PageResult<TransMonitorForm.TransMonitor>  owner = PageUtils.copyPageResult(trans,TransMonitorForm.TransMonitor.class);
        return new ResponseResult(true,"请求成功",owner);
    }

    @GetMapping("/getAllMonitorTrans}")
    @ApiOperation(value = "获取所有的监控转换数", produces = "application/json;charset=utf-8")

    public ResponseResult<Integer> getAllMonitorTrans(HttpServletRequest request){
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        Integer allMonitorTrans= transMonitorService.getAllMonitorTrans(kUser.getId());
        return new ResponseResult<>(true,"请求成功",allMonitorTrans);
    }

    @GetMapping("/getAllSuccess}")
    @ApiOperation(value = "获取执行成功的数", produces = "application/json;charset=utf-8")

    public ResponseResult<Integer> getAllSuccess(HttpServletRequest request){
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        Integer allSuccess= transMonitorService.getAllSuccess(kUser.getId());
        return new ResponseResult<>(true,"请求成功",allSuccess);
    }

    @GetMapping("/getAllFail}")
    @ApiOperation(value = "获取执行失败的数", produces = "application/json;charset=utf-8")

    public ResponseResult<Integer> getAllFail(HttpServletRequest request){
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        Integer allFail= transMonitorService.getAllFail(kUser.getId());
        return new ResponseResult<>(true,"请求成功",allFail);
    }

    @GetMapping("/getRecordList")
    @ApiOperation(value = "转换执行日志记录", produces = "application/json;charset=utf-8")
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
    public ResponseResult<TransRecordForm.TransRecord> getRecordList(Paging paging, @RequestParam(required = false,name = "queryString") List<String> queryString, @RequestParam(required = false, name = "orderBy") List<String> orderBys,  HttpServletRequest request){
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        if (CollectionUtils.isEmpty((Collection) queryString)) {
            queryString = new ArrayList();
        }
        if (CollectionUtils.isEmpty((Collection) orderBys)) {
            orderBys = new ArrayList();
            ((List) orderBys).add("startTimeDesc");
        }
        PageResult<TransRecordView> trans = transRecordService.getList(paging,queryString, orderBys, kUser.getId());
        PageResult<TransRecordForm.TransRecord>  owner = PageUtils.copyPageResult(trans,TransRecordForm.TransRecord.class);
        return new ResponseResult(true,"请求成功",owner);
    }

    @GetMapping("/getLogContent/{recordId}")
    @ApiOperation(value = "日志详情", produces = "application/json;charset=utf-8")

    public ResponseResult<String> getAllFail(@PathVariable(required = true ,name = "recordId") Integer recordId,HttpServletRequest request)throws IOException {
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        String logContent= transRecordService.getLogContent(recordId,kUser.getId());
        return new ResponseResult(true,"请求成功",logContent);
    }

    @GetMapping("/getLogDownload/{recordId}")
    @ApiOperation(value = "日志下载", produces = "application/json;charset=utf-8")
    public ResponseResult<Void> getLogDownload(@PathVariable(required = true ,name = "recordId") Integer recordId, HttpServletRequest request, HttpServletResponse response ) throws Exception {
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        transRecordService.getLogDownload(recordId,kUser.getId(),response);
        return new ResponseResult(true,"请求成功");
    }
}
