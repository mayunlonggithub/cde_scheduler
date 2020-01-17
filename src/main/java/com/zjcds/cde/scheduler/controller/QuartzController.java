package com.zjcds.cde.scheduler.controller;

import com.zjcds.cde.scheduler.base.PageResult;
import com.zjcds.cde.scheduler.base.PageUtils;
import com.zjcds.cde.scheduler.base.Paging;
import com.zjcds.cde.scheduler.base.ResponseResult;
import com.zjcds.cde.scheduler.domain.dto.QuartzForm;
import com.zjcds.cde.scheduler.domain.entity.Quartz;
import com.zjcds.cde.scheduler.domain.entity.User;
import com.zjcds.cde.scheduler.service.QuartzService;
import com.zjcds.cde.scheduler.utils.Constant;
import com.zjcds.cde.scheduler.utils.WebSecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
/**
 * @author Ma on 20191122
 */
@RestController

@Api(description = "调度策略信息")
@RequestMapping("/quartz")
public class QuartzController {
    @Autowired
    private QuartzService quartzService;

    @PostMapping("/addQuartz")
    @ApiOperation(value = "添加策略", produces = "application/json;charset=utf-8")
    public ResponseResult<Void> addQuartz(@RequestBody QuartzForm.AddQuartz addQuartz, HttpServletRequest request){
        Integer userId=WebSecurityUtils.currentUserId();
        if (addQuartz.getQuartzCron()!=null) {
            if (!CronExpression.isValidExpression(addQuartz.getQuartzCron())) {
                return new ResponseResult(false, "Cron表达式不正确");
            }
        }
        quartzService.addQuartz(addQuartz,userId);
        return new ResponseResult(true,"请求成功");
    }

    @DeleteMapping("/deleteQuartz/{quartzId}")
    @ApiOperation(value = "删除策略", produces = "application/json;charset=utf-8")
    public ResponseResult<Void> delete(@PathVariable("quartzId") Integer quartzId,HttpServletRequest request){
        Integer userId=WebSecurityUtils.currentUserId();
        Assert.notNull(quartzId,"要删除的策略id不能为空");
        quartzService.deleteQuartz(quartzId,userId);
        return new ResponseResult(true,"请求成功");
    }

    @PutMapping("/updateQuartz")
    @ApiOperation(value = "手动修改策略", produces = "application/json;charset=utf-8")
    public  ResponseResult<Void> updateQuartz(@RequestBody QuartzForm.UpdateQuartz updateQuartz,HttpServletRequest request) throws ParseException {
        Integer userId=WebSecurityUtils.currentUserId();
        if (updateQuartz.getQuartzCron() != null) {
            if (!CronExpression.isValidExpression(updateQuartz.getQuartzCron())) {
                return new ResponseResult(false, "Cron表达式不正确");
            }
        }
        quartzService.updateQuartz(updateQuartz,userId);
        return new ResponseResult(true, "请求成功");
    }

    @GetMapping("/getList")
    @ApiOperation(value = "获取调度策略分页列表", produces = "application/json;charset=utf-8")
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
    public ResponseResult<Void> getList(Paging paging, @RequestParam(required = false,name = "queryString") List<String> queryString, @RequestParam(required = false, name = "orderBy") List<String> orderBys,HttpServletRequest request){
        if (CollectionUtils.isEmpty((Collection) queryString)) {
            queryString = new ArrayList();
        }
        if (CollectionUtils.isEmpty((Collection) orderBys)) {
            orderBys = new ArrayList();
            ((List) orderBys).add("createTimeDesc");
        }
//        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
//        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        Integer userId=WebSecurityUtils.currentUserId();
        PageResult<Quartz> quartz = quartzService.getList(paging,queryString,orderBys,userId);
        PageResult<QuartzForm.Quartz> owner = PageUtils.copyPageResult(quartz, QuartzForm.Quartz.class);
        return new ResponseResult(true,"请求成功",owner);
    }
}
