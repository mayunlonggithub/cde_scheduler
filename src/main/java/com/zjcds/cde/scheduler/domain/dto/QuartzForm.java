package com.zjcds.cde.scheduler.domain.dto;

import com.zjcds.cde.scheduler.base.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
/**
 * @author Ma on 20191122
 */
@Data
public class QuartzForm {
        @Data
        @ApiModel(value = "scheduling category", description = "调度策略")
        public static class Quartz extends BaseBean {
                @ApiModelProperty(value = "调度策略ID")
                private int quartzId;
                @ApiModelProperty(value = "策略描述")
                private String quartzDescription;
                @ApiModelProperty(value = "策略Corn表达式")
                private String quartzCron;
                @ApiModelProperty(value = "策略添加者")
                private Integer createUser;
                @ApiModelProperty(value = "策略编辑者")
                private Integer modifyUser;
                @ApiModelProperty(value = "是否删除")
                private Integer delFlag;
                @ApiModelProperty(value = "策略开始时间")
                private Date startTime;
                @ApiModelProperty(value = "策略结束时间")
                private Date endTime;
                @ApiModelProperty(value = "策略生成选择")
                private Integer quartzFlag;
                @ApiModelProperty(value = "单元选择")
                private String unitType;
                @ApiModelProperty(value = "秒间隔")
                private Integer secInterval;
                @ApiModelProperty(value = "分间隔")
                private Integer minInterval;
                @ApiModelProperty(value = "时间隔")
                private Integer hourInterval;
                @ApiModelProperty(value = "执行秒")
                private Integer execSec;
                @ApiModelProperty(value = "执行分")
                private Integer execMin;
                @ApiModelProperty(value = "执行时")
                private Integer execHour;
                @ApiModelProperty(value = "执行天")
                private Integer execDay;
                @ApiModelProperty(value = "执行周")
                private Integer execWeek;
                @ApiModelProperty(value = "执行月份")
                private Integer execMonth;
                @ApiModelProperty(value = "执行年份")
                private Integer execYear;
                @ApiModelProperty(value = "策略名称")
                private String quartzName;
                @ApiModelProperty(value = "是否有关联任务")
                private Integer assTaskFlag;
                @ApiModelProperty(value = "是否有效")
                private Integer ifValid;
        }

        @Getter
        @Setter
        @ApiModel(value = "quartz.add", description = "增加策略")
        public static class AddQuartz extends BaseBean {
                @ApiModelProperty(value = "单元选择")
                private String unitType;
                @ApiModelProperty(value = "策略生成选择")
                private Integer quartzFlag;
                @ApiModelProperty(value = "策略开始时间")
                private Date startTime;
                @ApiModelProperty(value = "策略结束时间")
                private Date endTime;
                @ApiModelProperty(value = "秒间隔")
                private Integer secInterval;
                @ApiModelProperty(value = "分间隔")
                private Integer minInterval;
                @ApiModelProperty(value = "时间隔")
                private Integer hourInterval;
                @ApiModelProperty(value = "执行秒")
                private Integer execSec;
                @ApiModelProperty(value = "执行分")
                private Integer execMin;
                @ApiModelProperty(value = "执行时")
                private Integer execHour;
                @ApiModelProperty(value = "执行天")
                private Integer execDay;
                @ApiModelProperty(value = "执行周")
                private Integer execWeek;
                @ApiModelProperty(value = "执行月份")
                private Integer execMonth;
                @ApiModelProperty(value = "执行月份")
                private Integer execYear;
                @ApiModelProperty(value = "策略描述")
                private String quartzDescription;
                @ApiModelProperty(value = "策略Corn表达式")
                private String quartzCron;
                @ApiModelProperty(value = "策略名称")
                private String quartzName;
        }
        @Getter
        @Setter
        @ApiModel(value = "quartz.update", description = "修改策略")
        public static class UpdateQuartz extends AddQuartz{
                @ApiModelProperty(value = "调度策略ID")
                private int quartzId;
        }
        }



