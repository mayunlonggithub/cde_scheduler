package com.zjcds.cde.scheduler.controller;

import com.zjcds.cde.scheduler.domain.dto.CdmJobForm;
import com.zjcds.cde.scheduler.domain.dto.JobForm;
import com.zjcds.cde.scheduler.domain.dto.RepositoryForm;
import com.zjcds.cde.scheduler.domain.entity.Job;
import com.zjcds.cde.scheduler.domain.entity.Repository;
import com.zjcds.cde.scheduler.domain.entity.User;
import com.zjcds.cde.scheduler.service.JobService;
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
import org.pentaho.di.core.exception.KettleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author J on 20191120
 */
@RestController
@JsonViewException
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
        PageResult<Job> job = jobService.getList(paging,queryString, orderBys, kUser.getId());
        PageResult<JobForm.Job>  owner = PageUtils.copyPageResult(job,JobForm.Job.class);
        return new ResponseResult(true,"请求成功",owner);
    }


//    @PostMapping("/insert/{uId}")
//    @ApiOperation(value = "插入作业信息", produces = "application/json;charset=utf-8")
//    @JsonViewException
//    public ResponseResult<Void> insert(@RequestBody JobForm.AddJob addJob, @PathVariable(required = true ,name = "uId") Integer uId){
//        jobService.insert(addJob,uId);
//        return new ResponseResult(true,"请求成功");
//    }


    @PutMapping("/update/{jobId}")
    @ApiOperation(value = "修改作业信息", produces = "application/json;charset=utf-8")
    @JsonViewException
    public ResponseResult<Void> update(@RequestBody JobForm.UpdateJob updateJob, @PathVariable(required = true ,name = "jobId") Integer jobId){
        jobService.update(updateJob,jobId);
        return new ResponseResult(true,"请求成功");
    }

    @DeleteMapping("/delete/{jobId}")
    @ApiOperation(value = "删除作业信息", produces = "application/json;charset=utf-8")
    @JsonViewException
    public ResponseResult<Void> delete(@PathVariable(required = true ,name ="jobId") Integer jobId){
        Assert.notNull(jobId,"要删除的作业id不能为空");
        jobService.delete(jobId);
        return new ResponseResult(true,"请求成功");
    }

    @GetMapping("/getJob/{jobId}")
    @ApiOperation(value = "获取作业信息", produces = "application/json;charset=utf-8")
    @JsonViewException
    public ResponseResult<JobForm.Job> getJob(@PathVariable(required = true ,name = "jobId") Integer jobId){
        Job job = jobService.getJob(jobId);
        JobForm.Job owner = BeanPropertyCopyUtils.copy(job,JobForm.Job.class);
        return new ResponseResult(false,"请求成功",owner);
    }

    @PutMapping("/start/{jobId}")
    @ApiOperation(value = "开始作业", produces = "application/json;charset=utf-8")
    @JsonViewException
    public ResponseResult<Void> start(@RequestBody JobForm.JobParam jobParam, @PathVariable(required = true ,name = "jobId") Integer jobId, HttpServletRequest request)throws KettleException {
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        jobService.start(jobId,1,jobParam.getParam());
        return new ResponseResult(true,"请求成功");
    }
    
}
