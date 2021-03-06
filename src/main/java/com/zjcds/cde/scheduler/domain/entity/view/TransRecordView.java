package com.zjcds.cde.scheduler.domain.entity.view;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;
/**
 * @author J on 20191125
 */
@Entity
@Table(name = "v_trans_record")
public class TransRecordView {
    private Integer recordId;
    private Integer recordTrans;
    private Date startTime;
    private Date stopTime;
    private Integer recordStatus;
    private String logFilePath;
    private Integer createUser;
    private String recordTransName;
    private String duration;
    private Integer repositoryId;
    private Integer manualExe;
    private Date planStartTime;
    private String transPath;

    @Id
    @Column(name = "record_id")
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

    @Basic
    @Column(name = "trans_name")
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
    @Column(name = "trans_repository_id")
    public Integer getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(Integer repositoryId) {
        this.repositoryId = repositoryId;
    }
    @Basic
    @Column(name = "manual_execution")
    public Integer getManualExe() {
        return manualExe;
    }

    public void setManualExe(Integer manualExe) {
        this.manualExe = manualExe;
    }
    @Basic
    @Column(name="plan_start_time")
    public Date getPlanStartTime() {
        return planStartTime;
    }

    public void setPlanStartTime(Date planStartTime) {
        this.planStartTime = planStartTime;
    }

    @Basic
    @Column(name = "trans_path")
    public String getTransPath() {
        return transPath;
    }

    public void setTransPath(String transPath) {
        this.transPath = transPath;
    }
}
