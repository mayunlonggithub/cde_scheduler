package com.zjcds.cde.scheduler.controller;

import com.zjcds.cde.scheduler.base.*;
import com.zjcds.cde.scheduler.domain.dto.JobForm;
import com.zjcds.cde.scheduler.domain.entity.Job;
import com.zjcds.cde.scheduler.domain.entity.User;
import com.zjcds.cde.scheduler.service.JobService;
import com.zjcds.cde.scheduler.utils.Constant;
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
 * @author J on 20191120
 */
@RestController

@Api(description = "作业管理")
@RequestMapping("/job")
public class JobController {

    @Autowired
    private JobService jobService;

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
    public ResponseResult<JobForm.Job> getList(Paging paging, @RequestParam(required = false,name = "queryString") List<String> queryString, @RequestParam(required = false, name = "orderBy") List<String> orderBys, HttpServletRequest request){
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        if (CollectionUtils.isEmpty((Collection) queryString)) {
            queryString = new ArrayList();
        }
        if (CollectionUtils.isEmpty((Collection) orderBys)) {
            orderBys = new ArrayList();
            ((List) orderBys).add("createTimeDesc");
        }
        PageResult<Job> job = jobService.getList(paging,queryString, orderBys, kUser.getId());
        PageResult<JobForm.Job>  owner = PageUtils.copyPageResult(job,JobForm.Job.class);
        return new ResponseResult(true,"请求成功",owner);
    }


//    @PostMapping("/insert/{uId}")
//    @ApiOperation(value = "插入作业信息", produces = "application/json;charset=utf-8")
//
//    public ResponseResult<Void> insert(@RequestBody JobForm.AddJob addJob, @PathVariable(required = true ,name = "uId") Integer uId){
//        jobService.insert(addJob,uId);
//        return new ResponseResult(true,"请求成功");
//    }


    @PutMapping("/update/{jobId}")
    @ApiOperation(value = "修改作业信息", produces = "application/json;charset=utf-8")

    public ResponseResult<Void> update(@RequestBody JobForm.UpdateJob updateJob, @PathVariable(required = true ,name = "jobId") Integer jobId,HttpServletRequest request){
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        jobService.update(updateJob,jobId,kUser.getId());
        return new ResponseResult(true,"请求成功");
    }

    @DeleteMapping("/delete/{jobId}")
    @ApiOperation(value = "删除作业信息", produces = "application/json;charset=utf-8")

    public ResponseResult<Void> delete(@PathVariable(required = true ,name ="jobId") Integer jobId,HttpServletRequest request){
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        Assert.notNull(jobId,"要删除的作业id不能为空");
        jobService.delete(jobId,kUser.getId());
        return new ResponseResult(true,"请求成功");
    }

    @GetMapping("/getJob/{jobId}")
    @ApiOperation(value = "获取作业信息", produces = "application/json;charset=utf-8")

    public ResponseResult<JobForm.Job> getJob(@PathVariable(required = true ,name = "jobId") Integer jobId,HttpServletRequest request){
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        Job job = jobService.getJob(jobId,kUser.getId());
        JobForm.Job owner = BeanPropertyCopyUtils.copy(job,JobForm.Job.class);
        return new ResponseResult(false,"请求成功",owner);
    }

    @PutMapping("/start/{jobId}")
    @ApiOperation(value = "开始作业", produces = "application/json;charset=utf-8")

    public ResponseResult<Void> start(@RequestBody JobForm.JobParam jobParam, @PathVariable(required = true ,name = "jobId") Integer jobId, HttpServletRequest request) throws KettleException, ParseException {
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        jobService.start(jobId,kUser.getId(),jobParam.getParam());
        return new ResponseResult(true,"请求成功");
    }
}
