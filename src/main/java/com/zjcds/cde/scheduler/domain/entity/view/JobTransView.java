package com.zjcds.cde.scheduler.domain.entity.view;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Ma
 * @version 1.0
 * @date 2019/12/5 16:09
 */
@Entity
@Table(name = "v_job_trans")
@IdClass(JobTransViewPK.class)
public class JobTransView {
    private Integer taskId;
    private int jobId;
    private Integer categoryId;
    private String jobName;
    private String jobDescription;
    private Integer jobType;
    private String jobPath;
    private Integer jobRepositoryId;
    private Integer jobQuartz;
    private Integer jobRecord;
    private String jobLogLevel;
    private Integer jobStatus;
    private Date createTime;
    private Integer createUser;
    private Date modifyTime;
    private Integer modifyUser;
    private Integer delFlag;
    private String groups;
    private Integer stat;


    @Id
    @Column(name = "task_id")
    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    @Basic
    @Column(name = "job_id")
    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    @Basic
    @Column(name = "category_id")
    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Basic
    @Column(name = "job_name")
    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    @Basic
    @Column(name = "job_description")
    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    @Basic
    @Column(name = "job_type")
    public Integer getJobType() {
        return jobType;
    }

    public void setJobType(Integer jobType) {
        this.jobType = jobType;
    }

    @Basic
    @Column(name = "job_path")
    public String getJobPath() {
        return jobPath;
    }

    public void setJobPath(String jobPath) {
        this.jobPath = jobPath;
    }

    @Basic
    @Column(name = "job_repository_id")
    public Integer getJobRepositoryId() {
        return jobRepositoryId;
    }

    public void setJobRepositoryId(Integer jobRepositoryId) {
        this.jobRepositoryId = jobRepositoryId;
    }

    @Basic
    @Column(name = "job_quartz")
    public Integer getJobQuartz() {
        return jobQuartz;
    }

    public void setJobQuartz(Integer jobQuartz) {
        this.jobQuartz = jobQuartz;
    }

    @Basic
    @Column(name = "job_record")
    public Integer getJobRecord() {
        return jobRecord;
    }

    public void setJobRecord(Integer jobRecord) {
        this.jobRecord = jobRecord;
    }

    @Basic
    @Column(name = "job_log_level")
    public String getJobLogLevel() {
        return jobLogLevel;
    }

    public void setJobLogLevel(String jobLogLevel) {
        this.jobLogLevel = jobLogLevel;
    }

    @Basic
    @Column(name = "job_status")
    public Integer getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(Integer jobStatus) {
        this.jobStatus = jobStatus;
    }

    @Basic
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
    @Column(name = "modify_time")
    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
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

    @Basic
    @Column(name = "groups")
    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    @Basic
    @Column(name = "stat")
    public Integer getStat() {
        return stat;
    }

    public void setStat(Integer stat) {
        this.stat = stat;
    }

}
