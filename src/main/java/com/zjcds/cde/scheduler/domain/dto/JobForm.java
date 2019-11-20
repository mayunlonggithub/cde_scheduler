package com.zjcds.cde.scheduler.domain.dto;

import com.zjcds.common.base.domain.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Map;

/**
 * @author J on 20191119
 */
@Getter
@Setter
public class JobForm {

    @Getter
    @Setter
    @ApiModel(value = "jobParam",description = "作业执行参数")
    public static class JobParam{
        @ApiModelProperty(value = "参数map")
        private Map<String,String> param;
    }

    @Getter
    @Setter
    @ApiModel(value = "job",description = "作业信息")
    public static class Job extends BaseBean {
        @ApiModelProperty(value = "作业ID")
        private Integer jobId;
        @ApiModelProperty(value = "类别ID")
        private Integer categoryId;
        @ApiModelProperty(value = "作业名称")
        private String jobName;
        @ApiModelProperty(value = "任务描述")
        private String jobDescription;
        @ApiModelProperty(value = "作业类型")
        private Integer jobType;
        @ApiModelProperty(value = "作业保存路径")
        private String jobPath;
        @ApiModelProperty(value = "作业的资源库ID")
        private Integer jobRepositoryId;
        @ApiModelProperty(value = "定时策略（外键ID）")
        private Integer jobQuartz;
        @ApiModelProperty(value = "作业执行记录（外键ID）")
        private Integer jobRecord;
        @ApiModelProperty(value = "日志级别")
        private String jobLogLevel;
        @ApiModelProperty(value = "状态")
        private Integer jobStatus;
        @ApiModelProperty(value = "添加者")
        private Integer createUser;
        @ApiModelProperty(value = "编辑者")
        private Integer modifyUser;
        @ApiModelProperty(value = "是否删除")
        private Integer delFlag;
        @ApiModelProperty(value = "创建时间")
        private Date createTime;
        @ApiModelProperty(value = "修改时间")
        private Date modifyTime;

    }

    @Getter
    @Setter
    @ApiModel(value = "job.add",description = "新增作业信息")
    public static class AddJob extends BaseBean{
        @ApiModelProperty(value = "类别ID")
        private Integer categoryId;
        @ApiModelProperty(value = "作业名称")
        private String jobName;
        @ApiModelProperty(value = "任务描述")
        private String jobDescription;
        @ApiModelProperty(value = "作业类型")
        private Integer jobType;
        @ApiModelProperty(value = "作业保存路径")
        private String jobPath;
        @ApiModelProperty(value = "作业的资源库ID")
        private Integer jobRepositoryId;
        @ApiModelProperty(value = "定时策略（外键ID）")
        private Integer jobQuartz;
        @ApiModelProperty(value = "日志级别")
        private String jobLogLevel;


    }

    @Getter
    @Setter
    @ApiModel(value = "job.update",description = "修改作业信息")
    public static class UpdateJob extends JobForm.AddJob{
        @ApiModelProperty(value = "作业ID")
        private Integer jobId;
    }
}
