package com.zjcds.cde.scheduler.domain.entity.view;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author J on 20191119
 */
public class RepositoryTransViewPK implements Serializable {

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
        RepositoryTransViewPK that = (RepositoryTransViewPK) o;
        return rId == that.rId &&
                repositoryId == that.repositoryId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rId, repositoryId);
    }
}
