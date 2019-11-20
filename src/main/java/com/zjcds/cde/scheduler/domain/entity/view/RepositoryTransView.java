package com.zjcds.cde.scheduler.domain.entity.view;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author J 20191119
 */
@Entity
@Table(name = "v_repository_trans")
@IdClass(RepositoryTransViewPK.class)
public class RepositoryTransView {
    private Integer rId;
    private String parent;
    private Integer repositoryId;
    private String text;
    private String type;
    private String path;

    @Id
    @Column(name = "r_id")
    public Integer getrId() {
        return rId;
    }

    public void setrId(Integer rId) {
        this.rId = rId;
    }

    @Basic
    @Column(name = "parent")
    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    @Id
    @Column(name = "repository_id")
    public Integer getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(Integer repositoryId) {
        this.repositoryId = repositoryId;
    }

    @Basic
    @Column(name = "text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Basic
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "path")
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
