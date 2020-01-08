package com.zjcds.cde.scheduler.domain.dto.exchange;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TableInputForm {

    @Getter
    @Setter
    @ApiModel(value = "tableInputParam",description = "表输入参数")
    public static class TableInputParam{
        @ApiModelProperty(value = "数据源id")
        private String dsId;
        @ApiModelProperty(value = "数据源版本号")
        private String version;
        @ApiModelProperty(value = "查询语句")
        private String sql;
        @ApiModelProperty(value = "步骤名称")
        private String stepName;
    }
}
