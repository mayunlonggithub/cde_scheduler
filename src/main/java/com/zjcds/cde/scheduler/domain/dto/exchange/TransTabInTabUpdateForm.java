package com.zjcds.cde.scheduler.domain.dto.exchange;

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
        @ApiModelProperty(value = "")
        private String transName;
        private String dirName;
        private TableInputForm.TableInputParam tblIn;
        private TableUpdateForm.TableUpdateParam tblUpdate;
    }
}
