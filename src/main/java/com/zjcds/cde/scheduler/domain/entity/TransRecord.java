package com.zjcds.cde.scheduler.domain.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author J on 20191024
 * 转换执行记录
 */
@Entity
@Table(name = "t_trans_record")
public class TransRecord {
    //转换记录ID
    private Integer recordId;
    //转换ID
    private Integer recordTrans;
    //启动时间
    private Date startTime;
    //停止时间
    private Date stopTime;
    //任务执行结果
    private Integer recordStatus;
    //转换日志记录文件保存位置
    private String logFilePath;
    //添加者
    private Integer createUser;
    //转换名称
    private String recordTransName;
    //时间差
    private String duration;
    //是否手动执行
    private Integer manualExecute;
    //计划开始时间
    private Date  planStartTime;
    //是否删除
    private Integer delFlag;
    @Id
    @Column(name = "record_id")
    @TableGenerator(name = "idGenerator", table = "t_id_generator", pkColumnName = "id_key", pkColumnValue = "transRecord", valueColumnName = "id_value")
    @GeneratedValue(generator = "idGenerator", strategy = GenerationType.TABLE)
    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    @Basic
    @Column(name = "record_trans")
    public Integer getRecordTrans() {
        return recordTrans;
    }

    public void setRecordTrans(Integer recordTrans) {
        this.recordTrans = recordTrans;
    }

    @Basic
    @Column(name = "start_time")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "stop_time")
    public Date getStopTime() {
        return stopTime;
    }

    public void setStopTime(Date stopTime) {
        this.stopTime = stopTime;
    }

    @Basic
    @Column(name = "record_status")
    public Integer getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(Integer recordStatus) {
        this.recordStatus = recordStatus;
    }

    @Basic
    @Column(name = "log_file_path")
    public String getLogFilePath() {
        return logFilePath;
    }

    public void setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    @Basic
    @Column(name = "create_user")
    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    @Transient
    public String getRecordTransName() {
        return recordTransName;
    }

    public void setRecordTransName(String recordTransName) {
        this.recordTransName = recordTransName;
    }

    @Basic
    @Column(name = "duration")
    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Basic
    @Column(name="manual_execution")
    public Integer getManualExecute() {
        return manualExecute;
    }

    public void setManualExecute(Integer manualExecute) {
        this.manualExecute = manualExecute;
    }
    @Basic
    @Column(name = "plan_start_time")
    public Date getPlanStartTime() {
        return planStartTime;
    }
    public void setPlanStartTime(Date planStartTime) {
        this.planStartTime = planStartTime;
    }

    @Basic
    @Column(name = "del_Flag")
    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}
