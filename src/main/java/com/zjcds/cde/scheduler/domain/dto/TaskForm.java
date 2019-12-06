package com.zjcds.cde.scheduler.domain.dto;

import com.zjcds.cde.scheduler.base.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
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
    @Getter
    @Setter
    @ApiModel(value = "task.view",description = "任务视图")
    public static class TaskView extends BaseBean {
        @ApiModelProperty(value = "任务ID")
        private Integer taskId;
        @ApiModelProperty(value = "作业ID")
        private int jobId;
        @ApiModelProperty(value = "分类ID")
        private Integer categoryId;
        @ApiModelProperty(value = "作业名称")
        private String jobName;
        @ApiModelProperty(value = "作业描述")
        private String jobDescription;
        @ApiModelProperty(value = "作业类型")
        private Integer jobType;
        @ApiModelProperty(value = "作业路径")
        private String jobPath;
        @ApiModelProperty(value = "作业资源库ID")
        private Integer jobRepositoryId;
        @ApiModelProperty(value = "策略ID")
        private Integer jobQuartz;
        @ApiModelProperty(value = "作业ID")
        private Integer jobRecord;
        @ApiModelProperty(value = "作业记录ID")
        private String jobLogLevel;
        @ApiModelProperty(value = "作业状态")
        private Integer jobStatus;
        @ApiModelProperty(value = "创建时间")
        private Timestamp createTime;
        @ApiModelProperty(value = "创建者")
        private Integer createUser;
        @ApiModelProperty(value = "修改时间")
        private Timestamp modifyTime;
        @ApiModelProperty(value = "修改者")
        private Integer modifyUser;
        @ApiModelProperty(value = "是否删除")
        private Integer delFlag;
        @ApiModelProperty(value = "分内组别")
        private String groups;
        @ApiModelProperty(value = "任务状态")
        private Integer stat;
    }
}
