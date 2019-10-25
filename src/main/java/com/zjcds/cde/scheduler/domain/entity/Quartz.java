package com.zjcds.cde.scheduler.domain.entity;

import com.zjcds.common.jpa.domain.CreateModifyTime;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author J on 20191024
 * 定时策略信息
 */
@Entity
@Table(name = "t_quartz")
public class Quartz extends CreateModifyTime {
    //任务ID
    private Integer quartzId;
    //任务描述
    private String quartzDescription;
    //定时策略
    private String quartzCron;
    //添加者
    private Integer createUser;
    //编辑者
    private Integer modifyUser;
    //是否删除
    private Integer delFlag;

    @Id
    @Column(name = "quartz_id")
    public Integer getQuartzId() {
        return quartzId;
    }

    public void setQuartzId(Integer quartzId) {
        this.quartzId = quartzId;
    }

    @Basic
    @Column(name = "quartz_description")
    public String getQuartzDescription() {
        return quartzDescription;
    }

    public void setQuartzDescription(String quartzDescription) {
        this.quartzDescription = quartzDescription;
    }

    @Basic
    @Column(name = "quartz_cron")
    public String getQuartzCron() {
        return quartzCron;
    }

    public void setQuartzCron(String quartzCron) {
        this.quartzCron = quartzCron;
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
