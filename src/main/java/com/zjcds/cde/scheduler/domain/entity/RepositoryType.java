package com.zjcds.cde.scheduler.domain.entity;

import javax.persistence.*;

/**
 * @author J on 20191024
 * 资源库类型
 */
@Entity
@Table(name = "t_repository_type")
public class RepositoryType {
    //资源库类型ID
    private Integer repositoryTypeId;
    //资源库类型代码
    private String repositoryTypeCode;
    //资源库类型描述
    private String repositoryTypeDes;
    //是否显示
    private Integer isShow;

    @Id
    @Column(name = "repository_type_id")
    @TableGenerator(name = "idGenerator", table = "t_id_generator", pkColumnName = "id_key", pkColumnValue = "repositoryType", valueColumnName = "id_value")
    @GeneratedValue(generator = "idGenerator", strategy = GenerationType.TABLE)
    public Integer getRepositoryTypeId() {
        return repositoryTypeId;
    }

    public void setRepositoryTypeId(Integer repositoryTypeId) {
        this.repositoryTypeId = repositoryTypeId;
    }

    @Basic
    @Column(name = "repository_type_code")
    public String getRepositoryTypeCode() {
        return repositoryTypeCode;
    }

    public void setRepositoryTypeCode(String repositoryTypeCode) {
        this.repositoryTypeCode = repositoryTypeCode;
    }

    @Basic
    @Column(name = "repository_type_des")
    public String getRepositoryTypeDes() {
        return repositoryTypeDes;
    }

    public void setRepositoryTypeDes(String repositoryTypeDes) {
        this.repositoryTypeDes = repositoryTypeDes;
    }

    @Basic
    @Column(name = "is_show")
    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }
}
