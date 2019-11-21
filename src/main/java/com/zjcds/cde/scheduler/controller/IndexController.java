package com.zjcds.cde.scheduler.controller;

import com.zjcds.cde.scheduler.domain.entity.User;
import com.zjcds.cde.scheduler.service.JobMonitorService;
import com.zjcds.cde.scheduler.service.TransMonitorService;
import com.zjcds.cde.scheduler.utils.Constant;
import com.zjcds.common.jsonview.annotations.JsonViewException;
import com.zjcds.common.jsonview.utils.ResponseResult;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author J on 20191121
 */
@RestController
@JsonViewException
@Api(description = "首页")
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private TransMonitorService transMonitorService;
    @Autowired
    private JobMonitorService jobMonitorService;

    public ResponseResult<Integer> allRuning(HttpServletRequest request){
        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
        Integer allMonitorTrans = transMonitorService.getAllMonitorTrans(kUser.getId());
        Integer allMonitorJob = jobMonitorService.getAllMonitorJob(kUser.getId());
        Integer allRuning = allMonitorTrans + allMonitorJob;
        return new ResponseResult(true,"请求成功",allRuning);
    }

}
