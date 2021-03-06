package com.zjcds.cde.scheduler.controller;

import com.zjcds.cde.scheduler.base.PageResult;
import com.zjcds.cde.scheduler.base.PageUtils;
import com.zjcds.cde.scheduler.base.Paging;
import com.zjcds.cde.scheduler.base.ResponseResult;
import com.zjcds.cde.scheduler.domain.dto.TaskForm;
import com.zjcds.cde.scheduler.domain.entity.view.JobTransView;
import com.zjcds.cde.scheduler.service.TaskService;
import com.zjcds.cde.scheduler.utils.WebSecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
/**
 * @author Ma on 20191125
 */
@RestController
@Api(description = "任务调度信息")
@RequestMapping("/Task")
public class TaskController {
    @Autowired
    private TaskService taskService;
    //初始化启动所有的Job
    private static Logger logger = LoggerFactory.getLogger(TaskController.class);
    @PostConstruct
    public void initialize() {
        try {
            taskService.restartAllTasks();
            logger.info("INIT SUCCESS");
        } catch (SchedulerException e) {
            logger.info("INIT EXCEPTION : " + e.getMessage());
            e.printStackTrace();
        }
    }


    @DeleteMapping("/deleteTask/{taskId}")
    @ApiOperation(value = "删除", produces = "application/json;charset=utf-8")
    public ResponseResult<Void> delete(@PathVariable("taskId") Integer taskId,HttpServletRequest request){
        Assert.notNull(taskId,"要删除的策略id不能为空");
        Integer userId= WebSecurityUtils.currentUserId();
        taskService.deleteTask(taskId,userId);
        return new ResponseResult(true,"请求成功");
    }

    @GetMapping("/getList/{quartzId}")
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
    public ResponseResult<TaskForm.TaskView> getList(Paging paging, @RequestParam(required = false,name = "queryString") List<String> queryString, @RequestParam(required = false, name = "orderBy") List<String> orderBys, HttpServletRequest request,@PathVariable("quartzId") Integer  quartzId){
        Integer userId=WebSecurityUtils.currentUserId();
        if (CollectionUtils.isEmpty((Collection) queryString)) {
            queryString = new ArrayList();
        }
        if (CollectionUtils.isEmpty((Collection) orderBys)) {
            orderBys = new ArrayList();
            ((List) orderBys).add("createTimeDesc");
        }
        PageResult<JobTransView> task= taskService.getList(paging,queryString,orderBys,userId,quartzId);
        PageResult<TaskForm.TaskView> owner = PageUtils.copyPageResult(task, TaskForm.TaskView.class);
        return new ResponseResult(true,"请求成功",owner);
    }

    /**
     * 根据服务id启动task
     *
     * @return
     */
    @GetMapping("/runTask/{taskId}")
    @ApiOperation(value = "开始执行", produces = "application/json;charset=utf-8")
    public ResponseResult<Void> runTask(@PathVariable("taskId") Integer taskId) {
        taskService.runTask(taskId);
        return new ResponseResult(true,"请求成功");
    }

    /**
     * 根据ID关闭某个Job
     *
     * @param id
     * @return
     * @throws SchedulerException
     */
    @GetMapping("/shutDownTask/{taskId}")
    @ApiOperation(value = "停止执行", produces = "application/json;charset=utf-8")
    public ResponseResult<Void> shutDownTask(@PathVariable("taskId") Integer taskId) {
        taskService.shutDown(taskId);
        return new ResponseResult(true,"请求成功");
    }
//    /**
//     * 执行全部任务
//     * @throws SchedulerException
//     */
//    @GetMapping("/startAllTasks")
//    @ApiOperation(value = "全部执行", produces = "application/json;charset=utf-8")
//    public ResponseResult<Void> startAllTasks( ) {
//        taskService.startAllTasks();
//        return new ResponseResult(true,"请求成功");
//    }
//
//    /**
//     *停止全部任务
//     *
//     */
//    @GetMapping("/shutDownAllTasks")
//    @ApiOperation(value = "全部停止", produces = "application/json;charset=utf-8")
//    public ResponseResult<Void> shutDownAllTasks( ) {
//        taskService.shutDownAllTasks();
//        return new ResponseResult(true,"请求成功");
//    }
}
