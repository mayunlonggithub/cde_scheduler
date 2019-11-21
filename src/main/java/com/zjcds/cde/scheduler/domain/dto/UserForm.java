package com.zjcds.cde.scheduler.domain.dto;

import com.zjcds.common.base.domain.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author J on 20191107
 */
@Getter
@Setter
public class UserForm {

    @Getter
    @Setter
    @ApiModel(value = "userLogin",description = "用户登录")
    public static class UserLogin extends BaseBean {
        @ApiModelProperty(value = "用户账号")
        private String account;
        @ApiModelProperty(value = "用户密码")
        private String password;

    }

    @Getter
    @Setter
    @ApiModel(value = "user",description = "用户信息")
    public static class User extends BaseBean {
        @ApiModelProperty(value = "用户ID")
        private Integer id;
        @ApiModelProperty(value = "用户昵称")
        private String nickname;
        @ApiModelProperty(value = "用户邮箱")
        private String email;
        @ApiModelProperty(value = "用户电话")
        private String phone;
        @ApiModelProperty(value = "用户账号")
        private String account;
        @ApiModelProperty(value = "用户密码")
        private String password;
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
    @ApiModel(value = "user.add",description = "新增用户信息")
    public static class AddUser extends BaseBean {
        @ApiModelProperty(value = "用户昵称")
        private String nickname;
        @ApiModelProperty(value = "用户邮箱")
        private String email;
        @ApiModelProperty(value = "用户电话")
        private String phone;
        @ApiModelProperty(value = "用户账号")
        private String account;
        @ApiModelProperty(value = "用户密码")
        private String password;
    }

    @Getter
    @Setter
    @ApiModel(value = "user.update",description = "修改用户信息")
    public static class UpdateUser extends UserForm.AddUser {
        @ApiModelProperty(value = "用户ID")
        private Integer id;

    }
}
