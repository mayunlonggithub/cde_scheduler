package com.zjcds.cde.scheduler.domain.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author J on 20191118
 */
@Entity
@Table(name = "t_cdm_job")
public class CdmJob {
    private Integer id;
    private String jobName;
    private String jobPath;
    private String param;
    private String manualExecute;

    @Id
    @Column(name = "id")
    @TableGenerator(name = "idGenerator", table = "t_id_generator", pkColumnName = "id_key", pkColumnValue = "cdmJob", valueColumnName = "id_value")
    @GeneratedValue(generator = "idGenerator", strategy = GenerationType.TABLE)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    @Column(name = "job_path")
    public String getJobPath() {
        return jobPath;
    }

    public void setJobPath(String jobPath) {
        this.jobPath = jobPath;
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
    @Column(name = "manual_execute")
    public String getManualExecute() {
        return manualExecute;
    }

    public void setManualExecute(String manualExecute) {
        this.manualExecute = manualExecute;
    }


}
