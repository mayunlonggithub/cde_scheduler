package com.zjcds.cde.scheduler.controller.exchange;

import com.zjcds.cde.scheduler.base.ResponseResult;
import com.zjcds.cde.scheduler.domain.dto.exchange.TransTabInTabUpdateForm;
import com.zjcds.cde.scheduler.service.exchange.TransTabInTabUpdateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author J on 2020/1/2
 */
@RestController
@Api(description = "创建转换:表输入-插入更新")
@RequestMapping("/transTabInTabUpdate")
public class TransTabInTabUpdateController {

    @Autowired
    private TransTabInTabUpdateService transTabInTabUpdateService;

    @PostMapping("/addTrans")
    @ApiOperation(value = "创建转换:表输入-插入更新", produces = "application/json;charset=utf-8")
    public ResponseResult<Void> addTrans(@RequestBody TransTabInTabUpdateForm.TransTabInTabUpdate transTabInTabUpdate)throws Exception {
        transTabInTabUpdateService.addTrans(transTabInTabUpdate);
        return new ResponseResult(true,"转换创建成功");
    }
}
