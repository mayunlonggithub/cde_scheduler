package com.zjcds.cde.scheduler.controller;

import com.zjcds.cde.scheduler.common.BaseResponse;
import com.zjcds.cde.scheduler.domain.dto.TDepartmentForm;
import com.zjcds.cde.scheduler.service.TDepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author huangyj on 20190831
 */
@RestController
@RequestMapping("/tDepartment")
@Api(value = "部门信息模块", tags = {"B-部门信息模块"})
public class DepartmentController {

    @Autowired
    private TDepartmentService tDepartmentService;

    @GetMapping
    @ApiOperation(value = "获取部门树", produces = "application/json;charset=utf-8")
    public BaseResponse<TDepartmentForm.TDepartmentTree> addTRole(){
        List<TDepartmentForm.TDepartmentTree> owner = tDepartmentService.queryAllTDepartment();
        return new BaseResponse(owner);
    }
}
