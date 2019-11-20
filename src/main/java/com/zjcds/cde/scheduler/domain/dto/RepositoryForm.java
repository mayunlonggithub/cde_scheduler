package com.zjcds.cde.scheduler.domain.dto;

import com.zjcds.common.base.domain.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author J on 20191119
 */
@Getter
@Setter
public class RepositoryForm {

    @Getter
    @Setter
    @ApiModel(value = "repository",description = "资源库信息")
    public static class Repository extends BaseBean {
        @ApiModelProperty(value = "ID")
        private Integer repositoryId;
        @ApiModelProperty(value = "资源库名称")
        private String repositoryName;
        @ApiModelProperty(value = "登录用户名")
        private String repositoryUsername;
        @ApiModelProperty(value = "登录密码")
        private String repositoryPassword;
        @ApiModelProperty(value = "资源库数据库类型")
        private String repositoryType;
        @ApiModelProperty(value = "资源库数据库访问模式")
        private String databaseAccess;
        @ApiModelProperty(value = "资源库数据库主机名或者IP地址")
        private String databaseHost;
        @ApiModelProperty(value = "资源库数据库端口号")
        private String databasePort;
        @ApiModelProperty(value = "资源库数据库名称")
        private String databaseName;
        @ApiModelProperty(value = "数据库登录账号")
        private String databaseUsername;
        @ApiModelProperty(value = "数据库登录密码")
        private String databasePassword;
        @ApiModelProperty(value = "添加者")
        private Integer createUser;
        @ApiModelProperty(value = "编辑者")
        private Integer modifyUser;
        @ApiModelProperty(value = "是否删除")
        private Integer delFlag;
        @ApiModelProperty(value = "创建时间")
        private Date createTime;
        @ApiModelProperty(value = "修改时间")
        private Date modifyTime;
    }

    @Getter
    @Setter
    @ApiModel(value = "repository.add",description = "新增资源库信息")
    public static class AddRepository  extends BaseBean {
        @ApiModelProperty(value = "资源库名称")
        private String repositoryName;
        @ApiModelProperty(value = "登录用户名")
        private String repositoryUsername;
        @ApiModelProperty(value = "登录密码")
        private String repositoryPassword;
        @ApiModelProperty(value = "资源库数据库类型")
        private String repositoryType;
        @ApiModelProperty(value = "资源库数据库访问模式")
        private String databaseAccess;
        @ApiModelProperty(value = "资源库数据库主机名或者IP地址")
        private String databaseHost;
        @ApiModelProperty(value = "资源库数据库端口号")
        private String databasePort;
        @ApiModelProperty(value = "资源库数据库名称")
        private String databaseName;
        @ApiModelProperty(value = "数据库登录账号")
        private String databaseUsername;
        @ApiModelProperty(value = "数据库登录密码")
        private String databasePassword;
    }

    @Getter
    @Setter
    @ApiModel(value = "repository.update",description = "修改资源库信息")
    public static class UpdateRepository extends RepositoryForm.AddRepository{
        @ApiModelProperty(value = "ID")
        private Integer repositoryId;
    }
}
