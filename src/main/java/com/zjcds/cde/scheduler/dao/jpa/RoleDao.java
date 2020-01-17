package com.zjcds.cde.scheduler.dao.jpa;

import com.zjcds.cde.scheduler.base.CustomRepostory;
import com.zjcds.cde.scheduler.domain.entity.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author huangyj on 20190831
 */
public interface RoleDao extends CustomRepostory<Role,Integer> {

//    @EntityGraph(attributePaths = "menus")
    @Query("select t from Role t where t.id=:id")
    public Optional<Role> findById(@Param("id") Integer id);

    @Query("select t from Role t where t.id in (:id)")
    public List<Role> findByIdList(@Param("id") List<Integer> id);

//    @EntityGraph(attributePaths = "menus")
//    @Query("select r from Role r where r.id = :id")
//    public Optional<Role> findById(@Param("id") Integer id);

    @Query("select r from Role r where r.name = :name")
    public Role findByName(@Param("name") String name);

//    @Query("select m from Role r left join r.menus m where r.id = :roleId and m.hide = false ")
//    public List<Menu> queryMenuFor(@Param("roleId") Integer roleId);

    public List<Role> findByNameLike(String name);

    public Long countByNameLike(String name);

    @Query("select t from Role t where t.description like %:desc%")
    public List<Role> findByDescLike(@Param("desc") String desc);
}
