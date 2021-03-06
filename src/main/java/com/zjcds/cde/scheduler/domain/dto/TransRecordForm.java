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
public class TransRecordForm {

    @Getter
    @Setter
    @ApiModel(value = "transRecord",description = "转换执行记录")
    public static class TransRecord{
        @ApiModelProperty(value = "转换记录ID")
        private Integer recordId;
        @ApiModelProperty(value = "转换ID")
        private Integer recordTrans;
        @ApiModelProperty(value = "转换名称")
        private String recordTransName;
        @ApiModelProperty(value = "计划开始时间")
        private Date planStartTime;
        @ApiModelProperty(value = "启动时间")
        private Date startTime;
        @ApiModelProperty(value = "停止时间")
        private Date stopTime;
        @ApiModelProperty(value = "执行时长")
        private String duration;
        @ApiModelProperty(value = "任务执行结果")
        private Integer recordStatus;
        @ApiModelProperty(value = "转换日志记录文件保存位置")
        private String logFilePath;
        @ApiModelProperty(value = "添加者")
        private Integer createUser;
        @ApiModelProperty(value = "资源库ID")
        private Integer repositoryId;
        @ApiModelProperty(value = "是否手动执行")
        private  Integer manualExe;
        @ApiModelProperty(value = "转换路径")
        private String transPath;
    }
}
