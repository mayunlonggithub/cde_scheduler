package com.zjcds.cde.scheduler.domain.entity.view;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "v_trans_record_group", schema = "cde_scheduler", catalog = "")
public class TransRecordGroupView {
    private int recordId;
    private Integer recordTrans;
    private Timestamp startTime;
    private Timestamp stopTime;
    private Integer recordStatus;
    private String logFilePath;
    private Integer createUser;
    private String transName;
    private long num;

    @Basic
    @Column(name = "record_id")
    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
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
    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "stop_time")
    public Timestamp getStopTime() {
        return stopTime;
    }

    public void setStopTime(Timestamp stopTime) {
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
    public String getTransName() {
        return transName;
    }

    public void setTransName(String transName) {
        this.transName = transName;
    }

    @Basic
    @Column(name = "num")
    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransRecordGroupView that = (TransRecordGroupView) o;
        return recordId == that.recordId &&
                num == that.num &&
                Objects.equals(recordTrans, that.recordTrans) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(stopTime, that.stopTime) &&
                Objects.equals(recordStatus, that.recordStatus) &&
                Objects.equals(logFilePath, that.logFilePath) &&
                Objects.equals(createUser, that.createUser) &&
                Objects.equals(transName, that.transName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recordId, recordTrans, startTime, stopTime, recordStatus, logFilePath, createUser, transName, num);
    }
}
