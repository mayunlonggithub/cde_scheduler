package com.zjcds.cde.scheduler.domain.dto.exchange;

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
        @ApiModelProperty(value = "")
        private String transName;
        private String dirName;
        private TableInputForm.TableInputParam tblIn;
        private TableOutputForm.TableOutputParam tblOut;
    }
}
