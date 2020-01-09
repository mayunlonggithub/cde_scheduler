package com.zjcds.cde.scheduler.domain.dto;

import com.zjcds.cde.scheduler.base.BaseBean;
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
        @ApiModelProperty(value = "监控的转换的名称")
        private String monitorTransName;
        @ApiModelProperty(value = "成功次数")
        private Integer monitorSuccess;
        @ApiModelProperty(value = "失败次数")
        private Integer monitorFail;
        @ApiModelProperty(value = "添加者")
        private Integer createUser;
        @ApiModelProperty(value = "监控状态")
        private Integer monitorStatus;
        @ApiModelProperty(value = "运行状态")
        private Integer runStatus;
        @ApiModelProperty(value = "最后执行时间")
        private Date lastExecuteTime;
        @ApiModelProperty(value = "下次执行时间")
        private Date nextExecuteTime;
        @ApiModelProperty(value = "资源库ID")
        private Integer repositoryId;
        @ApiModelProperty(value = "转换路径")
        private String transPath;
    }

    @Getter
    @Setter
    @ApiModel(value = "transMonitorStatis",description = "转换监控记录")
    public static class TransMonitorStatis extends BaseBean {
        @ApiModelProperty(value = "资源库名称")
        private String repositoryName;
        @ApiModelProperty(value = "监控的转换名称")
        private String monitorTransName;
        @ApiModelProperty(value = "成功次数")
        private Integer monitorSuccess;
        @ApiModelProperty(value = "失败次数")
        private Integer monitorFail;
    }
}
