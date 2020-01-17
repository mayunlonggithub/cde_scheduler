package com.zjcds.cde.scheduler.domain.dto;


import com.zjcds.cde.scheduler.base.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;
import java.util.List;

/**
 * @author huangyj on 20190831
 */
@Getter
@Setter
public class TMenuForm {

    @Getter
    @Setter
    @ApiModel(value = "tMenu",description = "菜单信息")
    public static class TMenu extends BaseBean {
        @ApiModelProperty(value = "id")
        private Integer id;
        @ApiModelProperty(value = "菜单名称")
        private String menuName;
        @ApiModelProperty(value = "菜单描述")
        private String menuDes;
        @ApiModelProperty(value = "菜单图标")
        private String menuIcon;
        @ApiModelProperty(value = "菜单链接")
        private String menuUrl;
        @ApiModelProperty(value = "路由的名称")
        private String routeName;
        @ApiModelProperty(value = "父级菜单，顶级菜单为0")
        private Integer menuParent;
        @ApiModelProperty(value = "父级菜单名称")
        private String menuParentName;
        @ApiModelProperty(value = "父级中的顺序")
        private Integer menuOrder;
        @ApiModelProperty(value = "是否有效1有效，0失效")
        private Integer ifValid;
        @ApiModelProperty(value = "创建时间")
        private Date createTime;
        @ApiModelProperty(value = "修改时间")
        private Date modifyTime;
    }

    @Getter
    @Setter
    @ApiModel(value = "tMenu.add",description = "新增菜单信息")
    public static class AddTMenu extends BaseBean {
        @NotBlank
        @ApiModelProperty(value = "菜单名称")
        private String menuName;
        @ApiModelProperty(value = "菜单描述")
        private String menuDes;
        @ApiModelProperty(value = "菜单图标")
        private String menuIcon;
        @ApiModelProperty(value = "菜单链接")
        private String menuUrl;
        @ApiModelProperty(value = "路由的名称")
        private String routeName;
        @ApiModelProperty(value = "父级菜单，顶级菜单为0")
        private Integer menuParent;
        @ApiModelProperty(value = "父级中的顺序")
        private Integer menuOrder;
        @ApiModelProperty(value = "是否有效1有效，0失效")
        private Integer ifValid;
    }

    @Getter
    @Setter
    @ApiModel(value = "tMenu.update",description = "修改菜单信息")
    public static class UpdateTMenu extends TMenuForm.AddTMenu{
        @ApiModelProperty(value = "id")
        private Integer id;
    }

    @Getter
    @Setter
    @ApiModel(value = "tMenuTree",description = "菜单信息")
    public static class TMenuTree extends BaseBean {
        @ApiModelProperty(value = "id")
        private Integer id;
        @ApiModelProperty(value = "菜单名称")
        private String menuName;
        @ApiModelProperty(value = "菜单描述")
        private String menuDes;
        @ApiModelProperty(value = "菜单图标")
        private String menuIcon;
        @ApiModelProperty(value = "菜单链接")
        private String menuUrl;
        @ApiModelProperty(value = "路由的名称")
        private String routeName;
        @ApiModelProperty(value = "父级菜单，顶级菜单为0")
        private Integer menuParent;
        @ApiModelProperty(value = "父级中的顺序")
        private Integer menuOrder;
        @ApiModelProperty(value = "是否有效1有效，0失效")
        private Integer ifValid;
        @ApiModelProperty(value = "创建时间")
        private Date createTime;
        @ApiModelProperty(value = "修改时间")
        private Date modifyTime;
        @ApiModelProperty(value = "子菜单")
        private List<TMenuTree> tMenuTreeList;
    }
}
