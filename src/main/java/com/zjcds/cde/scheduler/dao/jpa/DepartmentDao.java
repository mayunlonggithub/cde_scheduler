package com.zjcds.cde.scheduler.dao.jpa;

import com.zjcds.cde.scheduler.base.CustomRepostory;
import com.zjcds.cde.scheduler.domain.entity.Department;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author huangyj on 20190831
 */
public interface DepartmentDao extends CustomRepostory<Department,Integer> {

    /**
     * 根据oid获取部门信息
     * @param oid
     * @return
     */
    @Query("select t from Department t where t.oid=:oid")
    public Department findByOid(@Param("oid") Integer oid);

    @Query("select t from Department t where t.districtFlag=1")
    public List<Department> findByDistrictFlag();

    /**
     * 根据子部门列表
     * @param id
     * @return
     */
    @Query("select d from Department d where d.pid = :id")
    public List<Department> querySubList(@Param("id") Integer id);

}
