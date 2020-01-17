package com.zjcds.cde.scheduler.dao.jpa;

import com.zjcds.cde.scheduler.base.CustomRepostory;
import com.zjcds.cde.scheduler.domain.entity.UserRole;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author huangyj on 20190831
 */
public interface UserRoleDao extends CustomRepostory<UserRole,Integer> {

    @Query("select t.roleId from UserRole t where t.userId=:userId")
    public List<Integer> findByUserId(@Param("userId") Integer userId);

    @Query("select t from UserRole t where t.roleId=:roleId")
    public UserRole findByRoleId(@Param("roleId") Integer roleId);

    @Query("select t from UserRole t where t.userId=:userId and t.roleId=:roleId")
    public UserRole findByUserIdAndRoleId(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

    @Modifying
    @Query("delete from UserRole t where t.userId=:userId")
    public void deleteByUserId(@Param("userId") Integer userId);
}
