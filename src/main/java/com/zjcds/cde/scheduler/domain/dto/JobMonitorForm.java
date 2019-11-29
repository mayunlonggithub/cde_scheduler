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
public class JobMonitorForm {

    @Getter
    @Setter
    @ApiModel(value = "jobMonitor",description = "作业监控信息")
    public static class JobMonitor extends BaseBean {
        @ApiModelProperty(value = "监控作业ID")
        private Integer monitorId;
        @ApiModelProperty(value = "监控的作业ID")
        private Integer monitorJob;
        @ApiModelProperty(value = "监控的作业名称")
        private String monitorJobName;
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
    }

    @Getter
    @Setter
    @ApiModel(value = "jobMonitorStatis",description = "作业监控记录")
    public static class JobMonitorStatis extends BaseBean {
        @ApiModelProperty(value = "资源库名称")
        private String repositoryName;
        @ApiModelProperty(value = "监控的作业名称")
        private String monitorJobName;
        @ApiModelProperty(value = "成功次数")
        private Integer monitorSuccess;
        @ApiModelProperty(value = "失败次数")
        private Integer monitorFail;
    }

}
