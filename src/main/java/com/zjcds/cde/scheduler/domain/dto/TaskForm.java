package com.zjcds.cde.scheduler.domain.dto;

import com.zjcds.cde.scheduler.base.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author J on 20191024
 * 转换执行监控
 */
@Getter
@Setter
public class TaskForm {
    @Getter
    @Setter
    @ApiModel(value = "task",description = "任务信息")
    public static class Task extends BaseBean {
        @ApiModelProperty(value = "任务ID")
        private int taskId;
        @ApiModelProperty(value = "作业ID")
        private Integer jobId;
        @ApiModelProperty(value = "调度策略ID")
        private Integer quartzId;
        @ApiModelProperty(value = "任务名称")
        private String taskName;
        @ApiModelProperty(value = "任务组别")
        private String taskGroup;
        @ApiModelProperty(value = "任务描述")
        private String taskDescription;
        @ApiModelProperty(value = "任务开始时间")
        private Date startTime;
        @ApiModelProperty(value = "任务结束时间")
        private Date endTime;
        @ApiModelProperty(value = "任务状态")
        private Integer status;
        @ApiModelProperty(value = "用户ID")
        private Integer userId;
        @ApiModelProperty(value = "任务创建者")
        private Integer createUser;
        @ApiModelProperty(value = "任务修改者")
        private Integer modifyUser;

    }
    @Getter
    @Setter
    @ApiModel(value = "task.add",description = "添加任务")
    public static class AddTask extends BaseBean {
        @ApiModelProperty(value = "作业ID")
        private Integer jobId;
        @ApiModelProperty(value = "调度策略ID")
        private Integer quartzId;
        @ApiModelProperty(value = "任务名称")
        private String taskName;
        @ApiModelProperty(value = "任务组别")
        private String taskGroup;
        @ApiModelProperty(value = "任务描述")
        private String taskDescription;

    }
}
