/**
 * create by HeYif.
 * Copyright (c) 2019 All Rights Reserved.
 */
package com.zjcds.cde.scheduler.domain.dto.exchange;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 数据源信息form
 * @author heyif
 * @since v1.0 2019-10-10T14:51
 */
public class MetaDatasourceForm {

    @Setter
    @Getter
    @ApiModel(value = "mateDatasourceDetail", description = "数据源详情")
    public static class Detail{
        @ApiModelProperty(value = "数据源ID", required = true)
        private String dsId;
        @ApiModelProperty(value = "数据源名称", readOnly = true)
        private String dsName;
        @ApiModelProperty(value = "数据源归属", readOnly = true)
        private String dsBelong;
        @ApiModelProperty(value = "数据源类型", readOnly = true)
        private Integer dsType;
        @ApiModelProperty(value = "访问方式", readOnly = true)
        private String connectType;
        @ApiModelProperty(value = "访问IP", readOnly = true)
        private String connectIp;
        @ApiModelProperty(value = "数据库实例名称", readOnly = true)
        private String sidName;
        @ApiModelProperty(value = "连接端口号", readOnly = true)
        private String connectPort;
        @ApiModelProperty(value = "连接用户名", readOnly = true)
        private String connectUser;
        @ApiModelProperty(value = "连接密码", readOnly = true)
        private String connectPwd;
        @ApiModelProperty(value = "用户类型", readOnly = true)
        private String userType;
        @ApiModelProperty(value = "数据源描述", readOnly = true)
        private Integer dsDetail;
        @ApiModelProperty(value = "版本", readOnly = true)
        private String version;
        @ApiModelProperty(value = "状态", readOnly = true)
        private String stat;
    }



}
