package com.zjcds.cde.scheduler.controller.exchange;

import com.zjcds.cde.scheduler.base.ResponseResult;
import com.zjcds.cde.scheduler.domain.dto.exchange.TransTabInTabOutForm;
import com.zjcds.cde.scheduler.service.exchange.TransTabInTabOutService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Api(description = "创建转换:表输入-表输出")
@RequestMapping("/transTabInTabOut")
public class TransTabInTabOutController {

    @Autowired
    private TransTabInTabOutService transTabInTabOutService;

    @PostMapping("/addTrans")
    @ApiOperation(value = "创建转换:表输入-表输出", produces = "application/json;charset=utf-8")
    public ResponseResult<Void> addTrans(@RequestBody TransTabInTabOutForm.TransTabInTabOut transTabInTabOut)throws Exception {
        transTabInTabOutService.addTrans(transTabInTabOut);
        return new ResponseResult(true,"转换创建成功");
    }
}
