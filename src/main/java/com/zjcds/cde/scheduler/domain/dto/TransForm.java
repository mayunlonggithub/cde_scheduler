package com.zjcds.cde.scheduler.domain.dto;

import com.zjcds.common.base.domain.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Map;

/**
 * @author J on 20191120
 */
@Getter
@Setter
public class TransForm {

    @Getter
    @Setter
    @ApiModel(value = "transParam",description = "转换执行参数")
    public static class TransParam{
        @ApiModelProperty(value = "参数map")
        private Map<String,String> param;
    }

    @Getter
    @Setter
    @ApiModel(value = "trans",description = "转换信息")
    public static class Trans extends BaseBean {
        @ApiModelProperty(value = "转换ID")
        private Integer transId;
//        @ApiModelProperty(value = "类别ID")
//        private Integer categoryId;
        @ApiModelProperty(value = "转换名称")
        private String transName;
        @ApiModelProperty(value = "任务描述")
        private String transDescription;
        @ApiModelProperty(value = "转换类型")
        private Integer transType;
        @ApiModelProperty(value = "转换保存路径")
        private String transPath;
        @ApiModelProperty(value = "转换的资源库ID")
        private Integer transRepositoryId;
        @ApiModelProperty(value = "定时策略（外键ID）")
        private Integer transQuartz;
        @ApiModelProperty(value = "转换执行记录（外键ID）")
        private Integer transRecord;
        @ApiModelProperty(value = "日志级别")
        private String transLogLevel;
        @ApiModelProperty(value = "状态")
        private Integer transStatus;
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
    @ApiModel(value = "trans.add",description = "新增转换信息")
    public static class AddTrans extends BaseBean{
//        @ApiModelProperty(value = "类别ID")
//        private Integer categoryId;
        @ApiModelProperty(value = "转换名称")
        private String transName;
        @ApiModelProperty(value = "任务描述")
        private String transDescription;
        @ApiModelProperty(value = "转换类型")
        private Integer transType;
        @ApiModelProperty(value = "转换保存路径")
        private String transPath;
        @ApiModelProperty(value = "转换的资源库ID")
        private Integer transRepositoryId;
        @ApiModelProperty(value = "定时策略（外键ID）")
        private Integer transQuartz;
        @ApiModelProperty(value = "日志级别")
        private String transLogLevel;


    }

    @Getter
    @Setter
    @ApiModel(value = "trans.update",description = "修改转换信息")
    public static class UpdateTrans extends TransForm.AddTrans{
        @ApiModelProperty(value = "转换ID")
        private Integer transId;
    }
}
