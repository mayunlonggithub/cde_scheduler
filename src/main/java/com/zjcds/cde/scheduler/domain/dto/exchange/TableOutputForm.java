package com.zjcds.cde.scheduler.domain.dto.exchange;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TableOutputForm {

    @Getter
    @Setter
    @ApiModel(value = "tableOutputParam",description = "表输出参数")
    public static class TableOutputParam{
        @ApiModelProperty(value = "数据源id")
        private String dsId;
        @ApiModelProperty(value = "数据源版本号")
        private String version;
        @ApiModelProperty(value = "目标表名称")
        private String tableName;
        @ApiModelProperty(value = "表字段")
        private List<String> tableColumn;
        @ApiModelProperty(value = "流字段")
        private List<String> streamColumn;
        @ApiModelProperty(value = "步骤名称")
        private String stepName;
    }
}
