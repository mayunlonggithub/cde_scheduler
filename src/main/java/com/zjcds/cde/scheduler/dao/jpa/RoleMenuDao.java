package com.zjcds.cde.scheduler.dao.jpa;

import com.zjcds.cde.scheduler.base.CustomRepostory;
import com.zjcds.cde.scheduler.domain.entity.RoleMenu;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author huangyj on 20190831
 */
public interface RoleMenuDao extends CustomRepostory<RoleMenu,Integer> {

    @Query("select t from RoleMenu t where t.menuId=:menuId")
    public RoleMenu findByMenuId(@Param("menuId") String menuId);

    @Query("select t.menuId from RoleMenu t where t.roleId in (:roleId)")
    public List<Integer> findByRoleId(@Param("roleId") List<Integer> roleId);

    @Query("select t from RoleMenu t where t.menuId=:menuId and t.roleId=:roleId")
    public RoleMenu findByMenuIdAndRoleId(@Param("menuId") String menuId, @Param("roleId") String roleId);

    @Modifying
    @Query("delete from RoleMenu t where t.roleId=:roleId")
    public void deleteByRoleId(@Param("roleId") Integer roleId);

    @Query("select t.menuId from RoleMenu t where t.roleId =:roleId")
    public List<Integer> findByRoleId(@Param("roleId") Integer roleId);
}
