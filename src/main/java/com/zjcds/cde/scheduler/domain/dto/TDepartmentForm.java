package com.zjcds.cde.scheduler.domain.dto;

import com.zjcds.cde.scheduler.base.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author huangyj on 20190831
 */
@Getter
@Setter
public class TDepartmentForm {

    @Getter
    @Setter
    @ApiModel(value = "tDepartment",description = "部门信息")
    public static class TDepartment extends BaseBean {
        @ApiModelProperty(value = "主键id")
        private Integer oid;
        @ApiModelProperty(value = "父id")
        private Integer pid;
        @ApiModelProperty(value = "部门名称")
        private String name;
        @ApiModelProperty(value = "部门位置")
        private String address;
        @ApiModelProperty(value = "负责人")
        private String leader;
        @ApiModelProperty(value = "电话")
        private String tel;
        @ApiModelProperty(value = "行政区划")
        private String districtld;
        @ApiModelProperty(value = "组织机构编码")
        private String orgcode;
        @ApiModelProperty(value = "排序")
        private Integer ordnum;
        @ApiModelProperty(value = "部门简称")
        private String abbr;
        @ApiModelProperty(value = "审查级别")
        private Integer auditGrade;
        @ApiModelProperty(value = "省市标志")
        private Integer districtFlag;

    }

    @Getter
    @Setter
    @ApiModel(value = "tDepartment.add",description = "新增部门信息")
    public static class AddTDepartment extends BaseBean {
        @ApiModelProperty(value = "父id")
        private Integer pid;
        @ApiModelProperty(value = "部门名称")
        private String name;
        @ApiModelProperty(value = "部门位置")
        private String address;
        @ApiModelProperty(value = "负责人")
        private String leader;
        @ApiModelProperty(value = "电话")
        private String tel;
        @ApiModelProperty(value = "行政区划")
        private String districtld;
        @ApiModelProperty(value = "组织机构编码")
        private String orgcode;
        @ApiModelProperty(value = "排序")
        private Integer ordnum;
        @ApiModelProperty(value = "部门简称")
        private String abbr;
        @ApiModelProperty(value = "审查级别")
        private Integer auditGrade;
        @ApiModelProperty(value = "省市标志")
        private Integer districtFlag;
    }

    @Getter
    @Setter
    @ApiModel(value = "tDepartment.update",description = "修改部门信息")
    public static class UpdateTDepartment extends TDepartmentForm.AddTDepartment{
        @ApiModelProperty(value = "主键id")
        private Integer oid;

    }

    @Getter
    @Setter
    @ApiModel(value = "tDepartmentTree",description = "部门信息")
    public static class TDepartmentTree extends BaseBean {
        @ApiModelProperty(value = "主键id")
        private Integer oid;
        @ApiModelProperty(value = "父id")
        private Integer pid;
        @ApiModelProperty(value = "部门名称")
        private String name;
        @ApiModelProperty(value = "部门位置")
        private String address;
        @ApiModelProperty(value = "负责人")
        private String leader;
        @ApiModelProperty(value = "电话")
        private String tel;
        @ApiModelProperty(value = "行政区划")
        private String districtld;
        @ApiModelProperty(value = "组织机构编码")
        private String orgcode;
        @ApiModelProperty(value = "排序")
        private Integer ordnum;
        @ApiModelProperty(value = "部门简称")
        private String abbr;
        @ApiModelProperty(value = "审查级别")
        private Integer auditGrade;
        @ApiModelProperty(value = "省市标志")
        private Integer districtFlag;

        private List<TDepartmentTree> tDepartmentTrees;
    }
}
