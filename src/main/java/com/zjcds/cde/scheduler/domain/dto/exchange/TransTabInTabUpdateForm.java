package com.zjcds.cde.scheduler.domain.dto.exchange;

import com.zjcds.cde.scheduler.domain.dto.QuartzForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author J on 2020/1/6
 */
@Getter
@Setter
public class TransTabInTabUpdateForm {

    @Getter
    @Setter
    @ApiModel(value = "transTabInTabUpdate",description = "")
    public static class TransTabInTabUpdate{
        @ApiModelProperty(value = "转换名称")
        private String transName;
        @ApiModelProperty(value = "路径名称")
        private String dirName;
        @ApiModelProperty(value = "表输入")
        private TableInputForm.TableInputParam tblIn;
        @ApiModelProperty(value = "插入更新")
        private TableUpdateForm.TableUpdateParam tblUpdate;
        @ApiModelProperty(value = "策略")
        private QuartzForm.AddQuartz addQuartz;
    }
}
