package com.zjcds.cde.scheduler.domain.entity;

import com.zjcds.common.jpa.domain.CreateModifyTime;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author J on 20191024
 * 转换信息
 */
@Entity
@Table(name = "t_trans")
public class Trans extends CreateModifyTime {
    //转换ID
    private Integer transId;
    //类别ID
//    private Integer categoryId;
    //转换名称
    private String transName;
    //转换描述
    private String transDescription;
    //转换类型
    private Integer transType;
    //转换保存路径
    private String transPath;
    //转换的资源库ID
    private Integer transRepositoryId;
    //定时策略（外键ID）
    private Integer transQuartz;
    //转换执行记录（外键ID）
    private Integer transRecord;
    //日志级别
    private String transLogLevel;
    //状态
    private Integer transStatus;
    //添加者
    private Integer createUser;
    //编辑者
    private Integer modifyUser;
    //是否删除
    private Integer delFlag;

    @Id
    @Column(name = "trans_id")
    @TableGenerator(name = "idGenerator", table = "t_id_generator", pkColumnName = "id_key", pkColumnValue = "trans", valueColumnName = "id_value")
    @GeneratedValue(generator = "idGenerator", strategy = GenerationType.TABLE)
    public Integer getTransId() {
        return transId;
    }

    public void setTransId(Integer transId) {
        this.transId = transId;
    }

//    @Basic
//    @Column(name = "category_id")
//    public Integer getCategoryId() {
//        return categoryId;
//    }
//
//    public void setCategoryId(Integer categoryId) {
//        this.categoryId = categoryId;
//    }

    @Basic
    @Column(name = "trans_name")
    public String getTransName() {
        return transName;
    }

    public void setTransName(String transName) {
        this.transName = transName;
    }

    @Basic
    @Column(name = "trans_description")
    public String getTransDescription() {
        return transDescription;
    }

    public void setTransDescription(String transDescription) {
        this.transDescription = transDescription;
    }

    @Basic
    @Column(name = "trans_type")
    public Integer getTransType() {
        return transType;
    }

    public void setTransType(Integer transType) {
        this.transType = transType;
    }

    @Basic
    @Column(name = "trans_path")
    public String getTransPath() {
        return transPath;
    }

    public void setTransPath(String transPath) {
        this.transPath = transPath;
    }

    @Basic
    @Column(name = "trans_repository_id")
    public Integer getTransRepositoryId() {
        return transRepositoryId;
    }

    public void setTransRepositoryId(Integer transRepositoryId) {
        this.transRepositoryId = transRepositoryId;
    }

    @Basic
    @Column(name = "trans_quartz")
    public Integer getTransQuartz() {
        return transQuartz;
    }

    public void setTransQuartz(Integer transQuartz) {
        this.transQuartz = transQuartz;
    }

    @Basic
    @Column(name = "trans_record")
    public Integer getTransRecord() {
        return transRecord;
    }

    public void setTransRecord(Integer transRecord) {
        this.transRecord = transRecord;
    }

    @Basic
    @Column(name = "trans_log_level")
    public String getTransLogLevel() {
        return transLogLevel;
    }

    public void setTransLogLevel(String transLogLevel) {
        this.transLogLevel = transLogLevel;
    }

    @Basic
    @Column(name = "trans_status")
    public Integer getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(Integer transStatus) {
        this.transStatus = transStatus;
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
