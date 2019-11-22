package com.zjcds.cde.scheduler.controller;

import com.zjcds.cde.scheduler.dao.jpa.QuartzDao;
import com.zjcds.cde.scheduler.domain.entity.Quartz;
import com.zjcds.cde.scheduler.domain.enums.BaseValue;
import com.zjcds.common.jsonview.annotations.JsonViewException;
import com.zjcds.common.jsonview.utils.ResponseResult;
import io.swagger.annotations.Api;
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
@JsonViewException
@Api(description = "字典表管理")
@RequestMapping("/cdeJob")
public class CodeController {
    @Autowired
    private QuartzDao quartzDao;

    @GetMapping("/getList")
    public ResponseResult<List<BaseValue>> code() {
        List<Quartz> qList = quartzDao.findByDelFlag(1);
        BaseValue baseValue = new BaseValue();
        List<BaseValue> bList = new ArrayList<>();
        for (Quartz quartz : qList) {
            baseValue.setKey(String.valueOf(quartz.getQuartzId()));
            baseValue.setValue(quartz.getQuartzDescription());
            bList.add(baseValue);
        }
        return new ResponseResult(true, "请求成功", bList);
    }
}
