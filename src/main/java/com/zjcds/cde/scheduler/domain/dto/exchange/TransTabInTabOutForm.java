package com.zjcds.cde.scheduler.domain.dto.exchange;

import com.zjcds.cde.scheduler.domain.dto.QuartzForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransTabInTabOutForm {

    @Getter
    @Setter
    @ApiModel(value = "transTabInTabOut",description = "")
    public static class TransTabInTabOut{
        @ApiModelProperty(value = "转换名称")
        private String transName;
        @ApiModelProperty(value = "路径名称")
        private String dirName;
        @ApiModelProperty(value = "表输入")
        private TableInputForm.TableInputParam tblIn;
        @ApiModelProperty(value = "表输出")
        private TableOutputForm.TableOutputParam tblOut;
        @ApiModelProperty(value = "策略")
        private QuartzForm.AddQuartz addQuartz;
    }
}
