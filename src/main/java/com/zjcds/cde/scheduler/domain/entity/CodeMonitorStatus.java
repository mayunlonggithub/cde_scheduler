package com.zjcds.cde.scheduler.domain.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

/**
 * @author Ma
 * @version 1.0
 * @date 2019/12/19 18:03
 */
@Entity
@Table(name = "code_monitor_status")
public class CodeMonitorStatus {
    private int monitorStatusId;
    private String monitorStatus;

    @Id
    @Column(name = "monitor_status_id")
    public int getMonitorStatusId() {
        return monitorStatusId;
    }

    public void setMonitorStatusId(int monitorStatusId) {
        this.monitorStatusId = monitorStatusId;
    }

    @Basic
    @Column(name = "monitor_status")
    public String getMonitorStatus() {
        return monitorStatus;
    }

    public void setMonitorStatus(String monitorStatus) {
        this.monitorStatus = monitorStatus;
    }


}
