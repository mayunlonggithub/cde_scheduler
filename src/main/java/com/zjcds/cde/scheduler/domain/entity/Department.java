package com.zjcds.cde.scheduler.domain.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * @author huangyj on 20190831
 */
@Entity
@Table(name = "T_DEPARTMENT")
public class Department {
    private Integer oid;
    private Integer pid;
    private String name;
    private String address;
    private String leader;
    private String tel;
    private String districtld;
    private String orgcode;
    private Integer ordnum;
    private String abbr;
    private Integer auditGrade;
    private Integer districtFlag;

    @Id
    @Column(name = "OID")
    @TableGenerator(name = "idGenerator", table = "t_id_generator", pkColumnName = "id_key", pkColumnValue = "tDepartment", valueColumnName = "id_value")
    @GeneratedValue(generator = "idGenerator", strategy = GenerationType.TABLE)
    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    @Basic
    @Column(name = "PID")
    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    @Basic
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "ADDRESS")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "LEADER")
    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    @Basic
    @Column(name = "TEL")
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Basic
    @Column(name = "DISTRICTLD")
    public String getDistrictld() {
        return districtld;
    }

    public void setDistrictld(String districtld) {
        this.districtld = districtld;
    }

    @Basic
    @Column(name = "ORGCODE")
    public String getOrgcode() {
        return orgcode;
    }

    public void setOrgcode(String orgcode) {
        this.orgcode = orgcode;
    }

    @Basic
    @Column(name = "ORDNUM")
    public Integer getOrdnum() {
        return ordnum;
    }

    public void setOrdnum(Integer ordnum) {
        this.ordnum = ordnum;
    }

    @Basic
    @Column(name = "ABBR")
    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    @Basic
    @Column(name = "AUDIT_GRADE")
    public Integer getAuditGrade() {
        return auditGrade;
    }

    public void setAuditGrade(Integer auditGrade) {
        this.auditGrade = auditGrade;
    }

    @Basic
    @Column(name = "DISTRICT_FLAG")
    public Integer getDistrictFlag() {
        return districtFlag;
    }

    public void setDistrictFlag(Integer districtFlag) {
        this.districtFlag = districtFlag;
    }


}
