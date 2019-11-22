package com.zjcds.cde.scheduler.controller;
import com.zjcds.cde.scheduler.domain.dto.TaskForm;
import com.zjcds.cde.scheduler.domain.entity.Task;
import com.zjcds.cde.scheduler.domain.entity.User;
import com.zjcds.cde.scheduler.service.TaskService;
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
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@JsonViewException
@Api(description = "任务调度信息")
@RequestMapping("/Task")
public class TaskController {
    @Autowired
    private TaskService taskService;
    private static Logger logger = LoggerFactory.getLogger(TaskController.class);
    //初始化启动所有的Job
    @PostConstruct
    public void initialize() {
        try {
            taskService.restartAllJobs();
            logger.info("INIT SUCCESS");
        } catch (SchedulerException e) {
            logger.info("INIT EXCEPTION : " + e.getMessage());
            e.printStackTrace();
        }
    }
    @PostMapping("/addTask")
    @ApiOperation(value = "添加任务", produces = "application/json;charset=utf-8")
    public ResponseResult<Void> addTask(@RequestBody TaskForm.AddTask addTask, HttpServletRequest request){
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        taskService.addTask(addTask,kUser.getId());
        return new ResponseResult(true,"请求成功");
    }

    @DeleteMapping("/deleteTask/{taskId}")
    @ApiOperation(value = "删除策略", produces = "application/json;charset=utf-8")
    public ResponseResult<Void> delete(@PathVariable("taskId") Integer taskId){
        Assert.notNull(taskId,"要删除的策略id不能为空");
        taskService.deleteTask(taskId);
        return new ResponseResult(true,"请求成功");
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
    public ResponseResult<Void> getList(Paging paging, @RequestParam(required = false,name = "queryString") List<String> queryString, @RequestParam(required = false, name = "orderBy") List<String> orderBys,HttpServletRequest request){
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        PageResult<Task> task= taskService.getList(paging,queryString,orderBys,kUser.getId());
        PageResult<TaskForm.Task> owner = PageUtils.copyPageResult(task, TaskForm.Task.class);
        return new ResponseResult(true,"请求成功",owner);
    }

}
