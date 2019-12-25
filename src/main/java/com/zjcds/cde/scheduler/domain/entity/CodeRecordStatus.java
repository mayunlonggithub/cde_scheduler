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
@Table(name = "code_record_status")
public class CodeRecordStatus {
    private int recordStatusId;
    private String recordStatus;

    @Id
    @Column(name = "record_status_id")
    public int getRecordStatusId() {
        return recordStatusId;
    }

    public void setRecordStatusId(int recordStatusId) {
        this.recordStatusId = recordStatusId;
    }

    @Basic
    @Column(name = "record_status")
    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }

}
