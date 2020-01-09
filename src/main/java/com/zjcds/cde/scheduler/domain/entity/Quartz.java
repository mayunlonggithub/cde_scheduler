package com.zjcds.cde.scheduler.domain.entity;

import com.zjcds.cde.scheduler.base.CreateModifyTime;

import javax.persistence.*;
import java.util.Date;
/**
 * @author Ma on 20191122
 */
@Entity
@Table(name = "t_quartz")
public class Quartz extends CreateModifyTime {
    //调度策略ID
    private Integer quartzId;
    //策略描述
    private String quartzDescription;
    //策略Corn表达式
    private String quartzCron;
    //策略添加者
    private Integer createUser;
    //策略编辑者
    private Integer modifyUser;
    //是否删除
    private Integer delFlag;
    //策略开始时间
    private Date startTime;
    //策略结束时间
    private Date endTime;
    //策略生成方式选择
    private Integer quartzFlag;
    //单元选择
    private String unitType;
    //秒间隔
    private Integer secInterval;
    //分间隔
    private Integer minInterval;
    //时间隔
    private Integer hourInterval;
    //执行秒
    private Integer execSec;
    //执行分
    private Integer execMin;
    //执行时
    private Integer execHour;
    //执行天
    private Integer execDay;
    //执行周
    private Integer execWeek;
    //执行月
    private Integer execMonth;
    //执行年份
    private Integer execYear;
    //说明
    private String quartzName;
    //是否有关联任务
    private Integer assTaskFlag;
    //是否有效
    private Integer ifValid;


    @Id
    @Column(name = "quartz_id")
    @TableGenerator(name = "idGenerator", table = "t_id_generator", pkColumnName = "id_key", pkColumnValue = "t_quartz", valueColumnName = "id_value")
    @GeneratedValue(generator = "idGenerator", strategy = GenerationType.TABLE)
    public Integer getQuartzId() {
        return quartzId;
    }
    public void setQuartzId(Integer quartzId) {
        this.quartzId = quartzId;
    }

    @Basic
    @Column(name = "quartz_description")
    public String getQuartzDescription() {
        return quartzDescription;
    }

    public void setQuartzDescription(String quartzDescription) {
        this.quartzDescription = quartzDescription;
    }

    @Basic
    @Column(name = "quartz_cron")
    public String getQuartzCron() {
        return quartzCron;
    }

    public void setQuartzCron(String quartzCron) {
        this.quartzCron = quartzCron;
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
    @Column(name = "del_flag")
    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
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
    @Column(name = "quartz_flag")
    public Integer getQuartzFlag() {
        return quartzFlag;
    }

    public void setQuartzFlag(Integer quartzFlag) {
        this.quartzFlag = quartzFlag;
    }

    @Basic
    @Column(name = "unit_type")
    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    @Basic
    @Column(name = "sec_interval")
    public int getSecInterval() {
        return secInterval;
    }

    public void setSecInterval(int secInterval) {
        this.secInterval = secInterval;
    }

    @Basic
    @Column(name = "min_interval")
    public int getMinInterval() {
        return minInterval;
    }

    public void setMinInterval(int minInterval) {
        this.minInterval = minInterval;
    }

    @Basic
    @Column(name = "hour_interval")
    public Integer getHourInterval() {
        return hourInterval;
    }

    public void setHourInterval(Integer hourInterval) {
        this.hourInterval = hourInterval;
    }

    @Basic
    @Column(name = "exec_sec")
    public Integer getExecSec() {
        return execSec;
    }

    public void setExecSec(Integer execSec) {
        this.execSec = execSec;
    }

    @Basic
    @Column(name = "exec_min")
    public Integer getExecMin() {
        return execMin;
    }

    public void setExecMin(Integer execMin) {
        this.execMin = execMin;
    }

    @Basic
    @Column(name = "exec_hour")
    public Integer getExecHour() {
        return execHour;
    }

    public void setExecHour(Integer execHour) {
        this.execHour = execHour;
    }

    @Basic
    @Column(name = "exec_day")
    public Integer getExecDay() {
        return execDay;
    }

    public void setExecDay(Integer execDay) {
        this.execDay = execDay;
    }

    @Basic
    @Column(name = "exec_week")
    public Integer getExecWeek() {
        return execWeek;
    }

    public void setExecWeek(Integer execWeek) {
        this.execWeek = execWeek;
    }

    @Basic
    @Column(name = "exec_month")
    public Integer getExecMonth() {
        return execMonth;
    }

    public void setExecMonth(Integer execMonth) {
        this.execMonth = execMonth;
    }

    @Basic
    @Column(name = "exec_year")
    public Integer getExecYear() {
        return execYear;
    }
    public void setExecYear(Integer execYear) {
        this.execYear = execYear;
    }

    @Basic
    @Column(name = "quartz_Name")
    public String getQuartzName() {
        return quartzName;
    }

    public void setQuartzName(String quartzName) {
        this.quartzName = quartzName;
    }

    @Basic
    @Column(name = "asstask_flag")
    public Integer getAssTaskFlag() {
        return assTaskFlag;
    }

    public void setAssTaskFlag(Integer assTaskFlag) {
        this.assTaskFlag = assTaskFlag;
    }

    @Basic
    @Column(name = "if_valid")
    public Integer getIfValid() {
        return ifValid;
    }

    public void setIfValid(Integer ifValid) {
        this.ifValid = ifValid;
    }
}
