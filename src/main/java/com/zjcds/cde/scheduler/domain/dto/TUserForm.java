package com.zjcds.cde.scheduler.domain.dto;

import com.zjcds.cde.scheduler.base.BaseBean;
import com.zjcds.cde.scheduler.domain.enums.BaseValue;
import com.zjcds.cde.scheduler.domain.enums.UserStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

/**
 * @author huangyj on 20190831
 */
@Getter
@Setter
public class TUserForm {

    @Getter
    @Setter
    @ApiModel(value = "tUser",description = "用户信息")
    public static class TUser extends BaseBean {
        @ApiModelProperty(value = "id")
        private Integer id;
        @ApiModelProperty(value = "账户")
        private String account;
        @ApiModelProperty(value = "名称")
        private String name;
        @ApiModelProperty(value = "密码")
        private String password;
        @ApiModelProperty(value = "状态")
        private String status;
        @ApiModelProperty(value = "所属部门")
        private BaseValue deptId;
        @ApiModelProperty(value = "所属父部门")
        private BaseValue deptPid;
        @ApiModelProperty(value = "手机")
        private String phone;
        @ApiModelProperty(value = "邮箱")
        private String email;
        @ApiModelProperty(value = "创建时间")
        private Date createTime;
        @ApiModelProperty(value = "修改时间")
        private Date modifyTime;
        @ApiModelProperty(value = "部门信息")
        private TDepartmentForm.TDepartment tDepartment;
        @ApiModelProperty(value = "角色信息")
        private List<TRoleForm.TRole> tRole;
        @ApiModelProperty(value = "菜单信息")
        private List<TMenuForm.TMenuTree> tMenu;
        @ApiModelProperty(value = "token")
        private String token;
    }

    @Getter
    @Setter
    @ApiModel(value = "tUser.add",description = "新增用户信息")
    public static class AddTUser extends BaseBean {
        @NotBlank
        @ApiModelProperty(value = "账户")
        private String account;
        @ApiModelProperty(value = "名称")
        private String name;
        @ApiModelProperty(value = "状态")
        private UserStatus status;
        @ApiModelProperty(value = "所属部门")
        private Integer[] tDepartment ;
        @ApiModelProperty(value = "手机")
        private String phone;
        @ApiModelProperty(value = "邮箱")
        private String email;
    }

    @Getter
    @Setter
    @ApiModel(value = "tUser.update",description = "修改用户信息")
    public static class UpdateTUser extends TUserForm.AddTUser {
        @ApiModelProperty(value = "id")
        private Integer id;
    }

    @Getter
    @Setter
    @ApiModel(value = "addRUserRole",description = "新增用户角色关联")
    public static class AddRUserRole extends BaseBean {
        @ApiModelProperty(value = "用户id")
        private Integer userId;
        @ApiModelProperty(value = "角色id")
        private Integer roleId;
    }

    @Getter
    @Setter
    @ApiModel(value = "login",description = "用户登录")
    public static class Login extends BaseBean {
        @ApiModelProperty(value = "用户名")
        private String username;
        @ApiModelProperty(value = "密码")
        private String password;
    }

    @Getter
    @Setter
    @ApiModel(value = "logout",description = "用户登出")
    public static class Logout extends BaseBean {
        @ApiModelProperty(value = "用户id")
        private Integer userId;
    }

    @Getter
    @Setter
    @ApiModel(value = "updatePassword",description = "修改密码")
    public static class UpdatePassword extends BaseBean {
        @ApiModelProperty(value = "用户id")
        private Integer userId;
        @ApiModelProperty(value = "旧密码")
        private String oldPassword;
        @ApiModelProperty(value = "新密码")
        private String newPassword;
    }
}
