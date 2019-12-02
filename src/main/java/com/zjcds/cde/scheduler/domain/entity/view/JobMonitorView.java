package com.zjcds.cde.scheduler.domain.entity.view;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

/**
 * @author J on 20191125
 */
@Entity
@Table(name = "v_job_monitor")
public class JobMonitorView {
    private Integer monitorId;
    private Integer monitorJob;
    private Integer monitorSuccess;
    private Integer monitorFail;
    private Integer createUser;
    private Integer monitorStatus;
    private String runStatus;
    private Date lastExecuteTime;
    private Date nextExecuteTime;
    private String monitorJobName;
    private Integer repositoryId;
    private String repositoryName;

    @Id
    @Column(name = "monitor_id")
    public Integer getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(Integer monitorId) {
        this.monitorId = monitorId;
    }

    @Basic
    @Column(name = "monitor_job")
    public Integer getMonitorJob() {
        return monitorJob;
    }

    public void setMonitorJob(Integer monitorJob) {
        this.monitorJob = monitorJob;
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
    @Column(name = "job_name")
    public String getMonitorJobName() {
        return monitorJobName;
    }

    public void setMonitorJobName(String monitorJobName) {
        this.monitorJobName = monitorJobName;
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
}
