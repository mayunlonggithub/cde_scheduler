package com.zjcds.cde.scheduler.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @author J on 20191121
 */
@Getter
@Setter
public class CdmJobForm {

    @Getter
    @Setter
    @ApiModel(value = "cdmJobParam",description = "作业执行参数")
    public static class CdmJobParam{
        @ApiModelProperty(value = "作业名称")
        private String jobName;
        @ApiModelProperty(value = "参数map")
        private Map<String,String> param;
    }

    @Getter
    @Setter
    @ApiModel(value = "cdmJobName",description = "作业执行参数")
    public static class CdmJobName{
        @ApiModelProperty(value = "id")
        private Integer id;
        @ApiModelProperty(value = "作业名称")
        private String jobName;
        @ApiModelProperty(value = "是否有参数")
        private String param;
        @ApiModelProperty(value = "作业路径")
        private String jobPath;
        @ApiModelProperty(value = "是否手动执行")
        private String manualExecute;
    }
}
