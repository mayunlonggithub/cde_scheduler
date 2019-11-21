package com.zjcds.cde.scheduler.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author J on 20191121
 */
@Getter
@Setter
public class TransMonitorForm {

    @Getter
    @Setter
    @ApiModel(value = "transMonitor",description ="转换监控信息" )
    public static class TransMonitor{
        @ApiModelProperty(value = "监控转换ID")
        private Integer monitorId;
        @ApiModelProperty(value = "监控的转换的ID")
        private Integer monitorTrans;
        @ApiModelProperty(value = "成功次数")
        private Integer monitorSuccess;
        @ApiModelProperty(value = "失败次数")
        private Integer monitorFail;
        @ApiModelProperty(value = "添加者")
        private Integer createUser;
        @ApiModelProperty(value = "监控状态")
        private Integer monitorStatus;
        @ApiModelProperty(value = "运行状态")
        private String runStatus;
        @ApiModelProperty(value = "最后执行时间")
        private Date lastExecuteTime;
        @ApiModelProperty(value = "下次执行时间")
        private Date nextExecuteTime;
    }
}