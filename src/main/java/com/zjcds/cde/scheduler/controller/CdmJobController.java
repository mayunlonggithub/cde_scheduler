package com.zjcds.cde.scheduler.controller;

import com.zjcds.cde.scheduler.base.BeanPropertyCopyUtils;
import com.zjcds.cde.scheduler.base.ResponseResult;
import com.zjcds.cde.scheduler.domain.dto.CdmJobForm;
import com.zjcds.cde.scheduler.domain.entity.CdmJob;
import com.zjcds.cde.scheduler.service.CdmJobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.pentaho.di.core.exception.KettleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;

/**
 * @author J on 20191113
 */
@RestController

@Api(description = "管控任务管理")
@RequestMapping("/cdeJob")
public class CdmJobController {

    @Autowired
    @Lazy
    private CdmJobService cdmJobService;

    @PostMapping("/cdmJobExecute")
    @ApiOperation(value = "执行任务", produces = "application/json;charset=utf-8")

    public ResponseResult<Void> cdmJobExecute(@RequestBody CdmJobForm.CdmJobParam cdmJobParam, HttpServletRequest request) throws KettleException, ParseException {
//        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
//        Assert.notNull(kUser,"未登录或登录已失效，请重新登录");
        cdmJobService.cdmJobExecute(cdmJobParam,2);
        return new ResponseResult(true,"请求成功");
    }

    @GetMapping("/cdmJobName")
    @ApiOperation(value = "查询可执行任务", produces = "application/json;charset=utf-8")

    public ResponseResult<List<CdmJobForm.CdmJobName>> cdmJobName() {
        List<CdmJob> cdmJob = cdmJobService.cdmJobName();
        List<CdmJobForm.CdmJobName> owner = BeanPropertyCopyUtils.copy(cdmJob,CdmJobForm.CdmJobName.class);
        return new ResponseResult(true,"请求成功",owner);
    }


//    @PostMapping("/cdmJobStop")
//    @ApiOperation(value = "停止任务", produces = "application/json;charset=utf-8")
//    public ResponseResult<Void> cdmJobStop(@RequestBody CdmJobForm.CdmJobParam cdmJobParam, HttpServletRequest request) throws KettleException {
//        User kUser = (User) request.getSession().getAttribute(Constant.SESSION_ID);
//        cdmJobService.cdmJobStop(cdmJobParam,1);
//        return new ResponseResult(true,"请求成功");
//    }
}
