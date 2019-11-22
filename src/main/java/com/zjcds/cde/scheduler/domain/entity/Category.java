package com.zjcds.cde.scheduler.domain.entity;

import com.zjcds.cde.scheduler.base.CreateModifyTime;

import javax.persistence.*;

/**
 * @author J on 20191024
 * 交换类别表
 */
@Entity
@Table(name = "t_category")
public class Category extends CreateModifyTime {
    //分类ID
    private Integer categoryId;
    //分类名称
    private String categoryName;
    //添加者
    private Integer createUser;
    //编辑者
    private Integer modifyUser;
    //是否删除
    private Integer delFlag;


    @Id
    @Column(name = "category_id")
    @TableGenerator(name = "idGenerator", table = "t_id_generator", pkColumnName = "id_key", pkColumnValue = "category", valueColumnName = "id_value")
    @GeneratedValue(generator = "idGenerator", strategy = GenerationType.TABLE)
    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Basic
    @Column(name = "category_name")
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Basic
    @Column(name = "create_user")
    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    @Basic
    @Column(name = "modify_user")
    public Integer getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(Integer modifyUser) {
        this.modifyUser = modifyUser;
    }

    @Basic
    @Column(name = "del_flag")
    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

}
