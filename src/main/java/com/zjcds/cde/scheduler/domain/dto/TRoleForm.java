package com.zjcds.cde.scheduler.domain.dto;

import com.zjcds.cde.scheduler.base.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author huangyj on 20190831
 */
@Getter
@Setter
public class TRoleForm {

    @Getter
    @Setter
    @ApiModel(value = "tRole",description = "角色信息")
    public static class TRole extends BaseBean {
        @NotNull
        @ApiModelProperty(value = "id")
        private Integer id;
        @ApiModelProperty(value = "名称")
        private String name;
        @ApiModelProperty(value = "描述")
        private String description;
        @ApiModelProperty(value = "创建时间")
        private Date createTime;
        @ApiModelProperty(value = "修改时间")
        private Date modifyTime;
    }

    @Getter
    @Setter
    @ApiModel(value = "tRole.add",description = "新增角色信息")
    public static class AddTRole extends BaseBean {
        @ApiModelProperty(value = "名称")
        private String name;
        @ApiModelProperty(value = "描述")
        private String description;

    }

    @Getter
    @Setter
    @ApiModel(value = "tRole.update",description = "修改角色信息")
    public static class UpdateTRole extends TRoleForm.AddTRole {
        @NotNull
        @ApiModelProperty(value = "id")
        private Integer id;
    }

    @Getter
    @Setter
    @ApiModel(value = "addRRoleMenu",description = "修改角色信息")
    public static class AddRRoleMenu extends BaseBean {
        @NotNull
        @ApiModelProperty(value = "角色id")
        private Integer roleId;
        @NotNull
        @ApiModelProperty(value = "菜单id")
        private Integer menuId;
    }
}
