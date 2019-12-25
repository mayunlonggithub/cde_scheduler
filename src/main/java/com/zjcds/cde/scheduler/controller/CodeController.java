package com.zjcds.cde.scheduler.controller;

import com.zjcds.cde.scheduler.dao.jpa.QuartzDao;
import com.zjcds.cde.scheduler.domain.entity.CodeMonitorStatus;
import com.zjcds.cde.scheduler.domain.entity.CodeRecordStatus;
import com.zjcds.cde.scheduler.domain.entity.Quartz;
import com.zjcds.cde.scheduler.base.ResponseResult;
import com.zjcds.cde.scheduler.domain.enums.BaseValue;
import com.zjcds.cde.scheduler.service.CodeService;
import com.zjcds.cde.scheduler.service.QuartzService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Ma on 20191122
 */
@RestController

@Api(description = "字典表管理")
@RequestMapping("/cdeJob")
public class CodeController {

    @Autowired
    private QuartzService quartzService;
    @Autowired
    private CodeService codeService;

    @GetMapping("/getMonitorStatus")
    @ApiOperation(value = "监控状态信息", produces = "application/json;charset=utf-8")
    public ResponseResult<List<BaseValue>> getMonitorStatus() {
        List<BaseValue> bList = new ArrayList<>();
       for(CodeMonitorStatus monitorStatus:codeService.findAllMonitorStatus()){
            BaseValue baseValue=new BaseValue();
            baseValue.setKey(String.valueOf(monitorStatus.getMonitorStatusId()));
            baseValue.setValue(monitorStatus.getMonitorStatus());
            bList.add(baseValue);
       }
        return new ResponseResult(true, "请求成功", bList);
    }




    @GetMapping("/getRunStatus")
    @ApiOperation(value = "运行状态信息", produces = "application/json;charset=utf-8")
    public ResponseResult<List<BaseValue>> getRunStatus() {
        List<BaseValue> bList = new ArrayList<>();
        BaseValue baseValue = new BaseValue();
        baseValue.setKey("0");
        baseValue.setValue("未执行");
        BaseValue baseValue1 = new BaseValue();
        baseValue1.setKey("1");
        baseValue1.setValue("执行中");
        BaseValue baseValue2 = new BaseValue();
        baseValue2.setKey("2");
        baseValue2.setValue("成功");
        BaseValue baseValue3 = new BaseValue();
        baseValue3.setKey("3");
        baseValue3.setValue("失败");
        bList.add(baseValue);
        bList.add(baseValue1);
        bList.add(baseValue2);
        bList.add(baseValue3);
        return new ResponseResult(true, "请求成功", bList);
    }

    @GetMapping("/getRecordStatus")
    @ApiOperation(value = "任务执行状态信息", produces = "application/json;charset=utf-8")
    public ResponseResult<List<BaseValue>> getRecordStatus() {
        List<BaseValue> bList = new ArrayList<>();
        for(CodeRecordStatus recordStatus:codeService.findAllRecordStatus()){
            BaseValue baseValue=new BaseValue();
            baseValue.setKey(String.valueOf(recordStatus.getRecordStatusId()));
            baseValue.setValue(recordStatus.getRecordStatus());
            bList.add(baseValue);
        }
        return new ResponseResult(true, "请求成功", bList);
    }


    @GetMapping("/getQuartzList")
    public ResponseResult<List<BaseValue>> code() {
        List<Quartz> qList = quartzService.getQuartzByDelFlag(1);
        BaseValue baseValue = new BaseValue();
        List<BaseValue> bList = new ArrayList<>();
        for (Quartz quartz : qList) {
            baseValue.setKey(String.valueOf(quartz.getQuartzId()));
            baseValue.setValue(quartz.getQuartzDescription());
            bList.add(baseValue);
        }
        return new ResponseResult(true, "请求成功", bList);
    }


    @GetMapping("/getTaskStatus")
    @ApiOperation(value = "运行状态信息", produces = "application/json;charset=utf-8")
    public ResponseResult<List<BaseValue>> getTaskStatus() {
        List<BaseValue> tList = new ArrayList<>();
        BaseValue baseValue = new BaseValue();
        baseValue.setKey("1");
        baseValue.setValue("执行");
        BaseValue baseValue1 = new BaseValue();
        baseValue1.setKey("2");
        baseValue1.setValue("停止");
        BaseValue baseValue2 = new BaseValue();
        baseValue2.setKey("3");
        baseValue2.setValue("完成");
        BaseValue baseValue3 = new BaseValue();
        baseValue3.setKey("4");
        baseValue3.setValue("失效");
        tList.add(baseValue);
        tList.add(baseValue1);
        tList.add(baseValue2);
        tList.add(baseValue3);
        return new ResponseResult(true, "请求成功", tList);
    }
}
