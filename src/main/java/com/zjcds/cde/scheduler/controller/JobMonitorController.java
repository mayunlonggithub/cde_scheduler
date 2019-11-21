package com.zjcds.cde.scheduler.controller;

import com.zjcds.cde.scheduler.domain.dto.JobForm;
import com.zjcds.cde.scheduler.domain.dto.JobMonitorForm;
import com.zjcds.cde.scheduler.domain.dto.JobRecordForm;
import com.zjcds.cde.scheduler.domain.entity.JobMonitor;
import com.zjcds.cde.scheduler.domain.entity.JobRecord;
import com.zjcds.cde.scheduler.domain.entity.User;
import com.zjcds.cde.scheduler.service.JobMonitorService;
import com.zjcds.cde.scheduler.service.JobRecordService;
import com.zjcds.cde.scheduler.utils.Constant;
import com.zjcds.common.base.domain.page.Paging;
import com.zjcds.common.jpa.PageResult;
import com.zjcds.common.jpa.utils.PageUtils;
import com.zjcds.common.jsonview.annotations.JsonViewException;
import com.zjcds.common.jsonview.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@JsonViewException
@Api(description = "作业监控管理")
@RequestMapping("/jobMonitor")
public class JobMonitorController {

    @Autowired
    private JobMonitorService jobMonitorService;
    @Autowired
    private JobRecordService jobRecordService;

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
    public ResponseResult<JobForm.Job> getList(Paging paging, @RequestParam(required = false,name = "queryString") List<String> queryString, @RequestParam(required = false, name = "orderBy") List<String> orderBys, HttpServletRequest request){
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        if (CollectionUtils.isEmpty((Collection) queryString)) {
            queryString = new ArrayList();
        }
        if (CollectionUtils.isEmpty((Collection) orderBys)) {
            orderBys = new ArrayList();
        }
        PageResult<JobMonitor> job = jobMonitorService.getList(paging,queryString, orderBys, kUser.getId());
        PageResult<JobMonitorForm.JobMonitor>  owner = PageUtils.copyPageResult(job,JobMonitorForm.JobMonitor.class);
        return new ResponseResult(true,"请求成功",owner);
    }

    @GetMapping("/getAllMonitorJob}")
    @ApiOperation(value = "获取所有的监控作业数", produces = "application/json;charset=utf-8")
    @JsonViewException
    public ResponseResult<Integer> getAllMonitorJob(HttpServletRequest request){
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        Integer allMonitorJob= jobMonitorService.getAllMonitorJob(kUser.getId());
        return new ResponseResult<>(true,"请求成功",allMonitorJob);
    }

    @GetMapping("/getAllSuccess}")
    @ApiOperation(value = "获取执行成功的数", produces = "application/json;charset=utf-8")
    @JsonViewException
    public ResponseResult<Integer> getAllSuccess(HttpServletRequest request){
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        Integer allSuccess= jobMonitorService.getAllSuccess(kUser.getId());
        return new ResponseResult<>(true,"请求成功",allSuccess);
    }

    @GetMapping("/getAllFail}")
    @ApiOperation(value = "获取执行失败的数", produces = "application/json;charset=utf-8")
    @JsonViewException
    public ResponseResult<Integer> getAllFail(HttpServletRequest request){
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        Integer allFail= jobMonitorService.getAllFail(kUser.getId());
        return new ResponseResult<>(true,"请求成功",allFail);
    }

    @GetMapping("/getRecordList/{jobId}")
    @ApiOperation(value = "作业执行日志记录", produces = "application/json;charset=utf-8")
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
    public ResponseResult<JobForm.Job> getRecordList(Paging paging, @RequestParam(required = false,name = "queryString") List<String> queryString, @RequestParam(required = false, name = "orderBy") List<String> orderBys, @PathVariable(required = true ,name = "jobId") Integer jobId, HttpServletRequest request){
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        if (CollectionUtils.isEmpty((Collection) queryString)) {
            queryString = new ArrayList();
        }
        if (CollectionUtils.isEmpty((Collection) orderBys)) {
            orderBys = new ArrayList();
        }
        PageResult<JobRecord> job = jobRecordService.getList(paging,queryString, orderBys, kUser.getId(),jobId);
        PageResult<JobRecordForm.JobRecord>  owner = PageUtils.copyPageResult(job,JobRecordForm.JobRecord.class);
        return new ResponseResult(true,"请求成功",owner);
    }

    @GetMapping("/getLogContent/{recordId}")
    @ApiOperation(value = "日志详情", produces = "application/json;charset=utf-8")
    @JsonViewException
    public ResponseResult<String> getAllFail(@PathVariable(required = true ,name = "recordId") Integer recordId,HttpServletRequest request)throws IOException {
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        String logContent= jobRecordService.getLogContent(recordId,kUser.getId());
        return new ResponseResult(true,"请求成功",logContent);
    }

}
