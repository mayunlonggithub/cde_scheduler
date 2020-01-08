package com.zjcds.cde.scheduler.domain.dto.exchange;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author J on 2020/1/6
 */
@Getter
@Setter
public class TableUpdateForm {

    @Getter
    @Setter
    @ApiModel(value = "tableUpdateParam",description = "表更新参数")
    public static class TableUpdateParam{
        @ApiModelProperty(value = "数据源id")
        private String dsId;
        @ApiModelProperty(value = "数据源版本号")
        private String version;
        @ApiModelProperty(value = "目标表名称")
        private String tableName;
        @ApiModelProperty(value = "关键字表字段")
        private List<String> keyLookups;
        @ApiModelProperty(value = "关键字比较符")
        private List<String> conditions;
        @ApiModelProperty(value = "关键字流字段1")
        private List<String> keyStreams;
        @ApiModelProperty(value = "关键字流字段2")
        private List<String> keyStreams2;
        @ApiModelProperty(value = "表字段")
        private List<String> tableColumn;
        @ApiModelProperty(value = "流字段")
        private List<String> streamColumn;
        @ApiModelProperty(value = "是否更新")
        private List<Boolean> isUpdate;
        @ApiModelProperty(value = "步骤名称")
        private String stepName;
    }
}
