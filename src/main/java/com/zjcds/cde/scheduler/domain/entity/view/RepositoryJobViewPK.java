package com.zjcds.cde.scheduler.domain.entity.view;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author J on 20191119
 */
public class RepositoryJobViewPK implements Serializable {

    private Integer rId;
    private Integer repositoryId;

    @Id
    @Column(name = "r_id")
    public Integer getrId() {
        return rId;
    }

    public void setrId(Integer rId) {
        this.rId = rId;
    }

    @Id
    @Column(name = "repository_id")
    public Integer getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(Integer repositoryId) {
        this.repositoryId = repositoryId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RepositoryJobViewPK that = (RepositoryJobViewPK) o;
        return rId.equals(that.rId) &&
                repositoryId.equals(that.repositoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rId, repositoryId);
    }

}
