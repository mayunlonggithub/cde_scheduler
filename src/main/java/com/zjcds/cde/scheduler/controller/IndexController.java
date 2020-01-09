package com.zjcds.cde.scheduler.controller;

import com.zjcds.cde.scheduler.base.BeanPropertyCopyUtils;
import com.zjcds.cde.scheduler.base.ResponseResult;
import com.zjcds.cde.scheduler.domain.dto.JobMonitorForm;
import com.zjcds.cde.scheduler.domain.dto.TransMonitorForm;
import com.zjcds.cde.scheduler.domain.entity.JobMonitor;
import com.zjcds.cde.scheduler.domain.entity.TransMonitor;
import com.zjcds.cde.scheduler.domain.entity.User;
import com.zjcds.cde.scheduler.domain.entity.view.JobMonitorView;
import com.zjcds.cde.scheduler.domain.entity.view.TransMonitorView;
import com.zjcds.cde.scheduler.service.*;
import com.zjcds.cde.scheduler.utils.Constant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author J on 20191121
 */
@RestController

@Api(description = "首页")
@RequestMapping("/index")
public class IndexController {

    @Autowired
    @Lazy
    private TransMonitorService transMonitorService;
    @Autowired
    @Lazy
    private JobMonitorService jobMonitorService;
    @Autowired
    @Lazy
    private JobRecordService jobRecordService;
    @Autowired
    private TransRecordService transRecordService;

    @GetMapping("/allRuning")
    @ApiOperation(value = "总监控任务数", produces = "application/json;charset=utf-8")

    public ResponseResult<Integer> allRuning(HttpServletRequest request){
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        Integer allMonitorTrans = transMonitorService.getAllMonitorTrans(kUser.getId());
        Integer allMonitorJob = jobMonitorService.getAllMonitorJob(kUser.getId());
        Integer allRuning = allMonitorTrans + allMonitorJob;
        return new ResponseResult(true,"请求成功",allRuning);
    }

    @GetMapping("/allMonitorJob")
    @ApiOperation(value = "监控作业数", produces = "application/json;charset=utf-8")

    public ResponseResult<Integer> allMonitorJob(HttpServletRequest request){
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        Integer allMonitorJob = jobMonitorService.getAllMonitorJob(kUser.getId());
        return new ResponseResult(true,"请求成功",allMonitorJob);
    }

    @GetMapping("/allMonitorTrans")
    @ApiOperation(value = "监控转换数", produces = "application/json;charset=utf-8")
    public ResponseResult<Integer> allMonitorTrans(HttpServletRequest request){
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        Integer allMonitorTrans = transMonitorService.getAllMonitorTrans(kUser.getId());
        return new ResponseResult(true,"请求成功",allMonitorTrans);
    }

    @GetMapping("/getTrans")
    @ApiOperation(value = "转换监控记录Top5", produces = "application/json;charset=utf-8")

    public ResponseResult<TransMonitorForm.TransMonitorStatis> getTrans(HttpServletRequest request){
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        List<TransMonitorForm.TransMonitorStatis> owner = transRecordService.getListToday(kUser.getId());
        if(owner.size()>5){
            owner = owner.subList(0,5);
        }
        return new ResponseResult(true,"请求成功",owner);
    }

    @GetMapping("/getJob")
    @ApiOperation(value = "作业监控记录Top5", produces = "application/json;charset=utf-8")
    public ResponseResult<JobMonitorForm.JobMonitorStatis> getJob(HttpServletRequest request){
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        List<JobMonitorForm.JobMonitorStatis> owner = jobRecordService.getListToday(kUser.getId());
        if(owner.size()>5){
            owner = owner.subList(0,5);
        }
        return new ResponseResult(true,"请求成功",owner);
    }

    @GetMapping("/getKettleLine")
    @ApiOperation(value = "转换监控记录", produces = "application/json;charset=utf-8")

    public ResponseResult<Map<String, Object>> getKettleLine(HttpServletRequest request){
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Map<String,Object> resultMap = new HashMap<String, Object>();
        List<String> dateList = new ArrayList<String>();
        for (int i = -6; i <= 0; i++){
            Calendar instance = Calendar.getInstance();
            instance.add(Calendar.DATE, i);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateFormat = simpleDateFormat.format(instance.getTime());
            dateList.add(dateFormat);
        }
        resultMap.put("legend", dateList);
        //总数
        Map<String, Object> transLine = transMonitorService.getTransLine(kUser.getId(),dateList);
        resultMap.put("trans", transLine);
        //成功
        Map<String, Object> transSuccess = transMonitorService.getTransSuccess(kUser.getId(),dateList);
        resultMap.put("transSuccess", transSuccess);
        //失败
        Map<String, Object> transFail = transMonitorService.getTransFail(kUser.getId(),dateList);
        resultMap.put("transFail", transFail);
        //总数
        Map<String, Object> jobLine = jobMonitorService.getJobLine(kUser.getId(),dateList);
        resultMap.put("job", jobLine);
        //成功
        Map<String, Object> jobSuccess = jobMonitorService.getJobSuccess(kUser.getId(),dateList);
        resultMap.put("jobSuccess", jobSuccess);
        //失败
        Map<String, Object> jobFail = jobMonitorService.getJobFail(kUser.getId(),dateList);
        resultMap.put("jobFail", jobFail);
        return new ResponseResult<>(true,"请求成功",resultMap);
    }
}
