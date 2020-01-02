package com.zjcds.cde.scheduler.domain.entity.view;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;
/**
 * @author J on 20191125
 */
@Entity
@Table(name = "v_trans_monitor")
public class TransMonitorView {
    private Integer monitorId;
    private Integer monitorTrans;
    private Integer monitorSuccess;
    private Integer monitorFail;
    private Integer createUser;
    private Integer monitorStatus;
    private String runStatus;
    private Date lastExecuteTime;
    private Date nextExecuteTime;
    private String monitorTransName;
    private Integer repositoryId;
    private String repositoryName;
    private String transPath;

    @Id
    @Column(name = "monitor_id")
    public Integer getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(Integer monitorId) {
        this.monitorId = monitorId;
    }

    @Basic
    @Column(name = "monitor_trans")
    public Integer getMonitorTrans() {
        return monitorTrans;
    }

    public void setMonitorTrans(Integer monitorTrans) {
        this.monitorTrans = monitorTrans;
    }

    @Basic
    @Column(name = "monitor_success")
    public Integer getMonitorSuccess() {
        return monitorSuccess;
    }

    public void setMonitorSuccess(Integer monitorSuccess) {
        this.monitorSuccess = monitorSuccess;
    }

    @Basic
    @Column(name = "monitor_fail")
    public Integer getMonitorFail() {
        return monitorFail;
    }

    public void setMonitorFail(Integer monitorFail) {
        this.monitorFail = monitorFail;
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
    @Column(name = "monitor_status")
    public Integer getMonitorStatus() {
        return monitorStatus;
    }

    public void setMonitorStatus(Integer monitorStatus) {
        this.monitorStatus = monitorStatus;
    }

    @Basic
    @Column(name = "run_status")
    public String getRunStatus() {
        return runStatus;
    }

    public void setRunStatus(String runStatus) {
        this.runStatus = runStatus;
    }

    @Basic
    @Column(name = "last_execute_time")
    public Date getLastExecuteTime() {
        return lastExecuteTime;
    }

    public void setLastExecuteTime(Date lastExecuteTime) {
        this.lastExecuteTime = lastExecuteTime;
    }

    @Basic
    @Column(name = "next_execute_time")
    public Date getNextExecuteTime() {
        return nextExecuteTime;
    }

    public void setNextExecuteTime(Date nextExecuteTime) {
        this.nextExecuteTime = nextExecuteTime;
    }

    @Basic
    @Column(name = "trans_name")
    public String getMonitorTransName() {
        return monitorTransName;
    }

    public void setMonitorTransName(String monitorTransName) {
        this.monitorTransName = monitorTransName;
    }

    @Basic
    @Column(name = "repository_id")
    public Integer getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(Integer repositoryId) {
        this.repositoryId = repositoryId;
    }

    @Basic
    @Column(name = "repository_name")
    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
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
