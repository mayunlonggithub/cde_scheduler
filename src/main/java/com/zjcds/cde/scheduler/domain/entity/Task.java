package com.zjcds.cde.scheduler.domain.entity;

import javax.persistence.*;

import java.util.Date;

/**
 * @author Ma on 20191122
 * 任务调度
 */
@Entity
@Table(name = "t_task", schema = "cde_scheduler", catalog = "")
public class Task {
    //任务ID
    private Integer taskId;
    //作业ID
    private Integer jobId;
    //调度策略ID
    private Integer quartzId;
    //任务名称
    private String taskName;
    //任务分组
    private String taskGroup;
    //任务描述
    private String taskDesc;
    //任务开始时间
    private Date startTime;
    //任务结束时间
    private Date endTime;
    //任务状态
    private Integer status;
    //用户ID
    private Integer userId;
    //参数
    private String param;
    //创建者
    private Integer createUser;
    //编辑者
    private Integer modifyUser;
    //调度策略描述
    private String quartzDesc;
    //是否删除
    private Integer delFlag;

    @Id
    @Column(name = "task_id")
    @TableGenerator(name = "idGenerator", table = "t_id_generator", pkColumnName = "id_key", pkColumnValue = "t_task", valueColumnName = "id_value")
    @GeneratedValue(generator = "idGenerator", strategy = GenerationType.TABLE)
    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    @Basic
    @Column(name = "job_id")
    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    @Basic
    @Column(name = "quartz_id")
    public Integer getQuartzId() {
        return quartzId;
    }

    public void setQuartzId(Integer quartzId) {
        this.quartzId = quartzId;
    }

    @Basic
    @Column(name = "task_name")
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Basic
    @Column(name = "task_group")
    public String getTaskGroup() {
        return taskGroup;
    }

    public void setTaskGroup(String taskGroup) {
        this.taskGroup = taskGroup;
    }

    @Basic
    @Column(name = "task_desc")
    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
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
    @Column(name = "end_time")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "param")
    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
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
    @Column(name = "quartz_desc")
    public String getQuartzDesc() {
        return quartzDesc;
    }

    public void setQuartzDesc(String quartzDesc) {
        this.quartzDesc = quartzDesc;
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
