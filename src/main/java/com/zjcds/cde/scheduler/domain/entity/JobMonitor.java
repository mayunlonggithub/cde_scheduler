package com.zjcds.cde.scheduler.domain.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author J on 20191024
 * 作业执行监控
 */
@Entity
@Table(name = "t_job_monitor")
public class JobMonitor {
    //监控作业ID
    private Integer monitorId;
    //监控的作业ID
    private Integer monitorJob;
    //成功次数
    private Integer monitorSuccess;
    //失败次数
    private Integer monitorFail;
    //添加者
    private Integer createUser;
    //监控状态
    private Integer monitorStatus;
    //运行状态
    private String runStatus;
    //最后执行时间
    private Date lastExecuteTime;
    //下次执行时间
    private Date nextExecuteTime;

    @Id
    @Column(name = "monitor_id")
    @TableGenerator(name = "idGenerator", table = "t_id_generator", pkColumnName = "id_key", pkColumnValue = "jobMonitor", valueColumnName = "id_value")
    @GeneratedValue(generator = "idGenerator", strategy = GenerationType.TABLE)
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


}
