package com.zjcds.cde.scheduler.controller;

import com.zjcds.cde.scheduler.domain.enums.BaseValue;
import com.zjcds.common.jsonview.annotations.JsonViewException;
import com.zjcds.common.jsonview.utils.ResponseResult;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@JsonViewException
@Api(description = "字典表管理")
@RequestMapping("/cdeJob")
public class CodeController {


    public ResponseResult<BaseValue> code(){
        BaseValue baseValue = new BaseValue();
        baseValue.setKey("1");
        baseValue.setValue("1");
        return new ResponseResult(baseValue) ;
    }

}
