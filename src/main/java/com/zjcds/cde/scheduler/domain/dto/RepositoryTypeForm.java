package com.zjcds.cde.scheduler.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author J on 20191119
 */
@Getter
@Setter
public class RepositoryTypeForm {

    @Getter
    @Setter
    @ApiModel(value = "repositoryType",description = "资源库类型代码")
    public static class RepositoryType{
        @ApiModelProperty(value = "资源库类型ID")
        private Integer repositoryTypeId;
        @ApiModelProperty(value = "资源库类型代码")
        private String repositoryTypeCode;
        @ApiModelProperty(value = "资源库类型描述")
        private String repositoryTypeDes;

    }
}
