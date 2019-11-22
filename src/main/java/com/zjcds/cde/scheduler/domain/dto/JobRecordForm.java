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
public class JobRecordForm {

    @Getter
    @Setter
    @ApiModel(value = "jobRecord",description = "作业执行日志")
    public static class JobRecord extends BaseBean {
        @ApiModelProperty(value = "作业记录ID")
        private Integer recordId;
        @ApiModelProperty(value = "作业ID")
        private Integer recordJob;
        @ApiModelProperty(value = "作业名称")
        private String recordJobName;
        @ApiModelProperty(value = "启动时间")
        private Date startTime;
        @ApiModelProperty(value = "停止时间")
        private Date stopTime;
        @ApiModelProperty(value = "任务执行结果")
        private Integer recordStatus;
        @ApiModelProperty(value = "作业日志记录文件保存位置")
        private String logFilePath;
        @ApiModelProperty(value = "添加者")
        private Integer createUser;
    }
}
