package com.zjcds.cde.scheduler.domain.entity.view;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author J on 20191128
 */
@Entity
@Table(name = "v_trans_record_group")
public class TransRecordGroupView {
    private Integer recordId;
    private Integer recordTrans;
    private Timestamp startTime;
    private Timestamp stopTime;
    private Integer recordStatus;
    private String logFilePath;
    private Integer createUser;
    private String transName;
    private Integer num;
    //转换的资源库ID
    private Integer transRepositoryId;
    private String repositoryName;

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
    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
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
    @Column(name = "repository_name")
    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }
}
