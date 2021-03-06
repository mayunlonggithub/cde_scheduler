package com.zjcds.cde.scheduler.domain.entity.view;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;
/**
 * @author J on 20191125
 */
@Entity
@Table(name = "v_job_record")
public class JobRecordView {
    private Integer recordId;
    private Integer recordJob;
    private Date planStartTime;
    private Date startTime;
    private Date stopTime;
    private Integer recordStatus;
    private String logFilePath;
    private Integer createUser;
    private String recordJobName;
    private String duration;
    private Integer repositoryId;
    private Integer manualExe;
    private String  jobPath;

    @Id
    @Column(name = "record_id")
    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    @Basic
    @Column(name = "record_job")
    public Integer getRecordJob() {
        return recordJob;
    }

    public void setRecordJob(Integer recordJob) {
        this.recordJob = recordJob;
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
    @Column(name = "job_name")
    public String getRecordJobName() {
        return recordJobName;
    }

    public void setRecordJobName(String recordJobName) {
        this.recordJobName = recordJobName;
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
    @Column(name = "job_repository_id")
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
    @Column(name = "plan_start_time")
    public Date getPlanStartTime() {
        return planStartTime;
    }

    public void setPlanStartTime(Date planStartTime) {
        this.planStartTime = planStartTime;
    }

    @Basic
    @Column(name = "job_path")
    public String getJobPath() {
        return jobPath;
    }

    public void setJobPath(String jobPath) {
        this.jobPath = jobPath;
    }
}
