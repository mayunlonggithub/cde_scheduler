package com.zjcds.cde.scheduler.dao.jpa;

import com.zjcds.cde.scheduler.base.CustomRepostory;
import com.zjcds.cde.scheduler.domain.entity.Menu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author huangyj on 20190831
 */
public interface MenuDao extends CustomRepostory<Menu,Integer> {

    @Query("select t from Menu t where t.id=:id")
    public Optional<Menu> findById(@Param("id") Integer id);

    @Query("select t from Menu t where t.id in (:id)")
    public List<Menu> findByIdList(@Param("id") List<Integer> id);

    @Query("select max (t.menuOrder) from Menu t where t.menuParent=:menuParent")
    public Integer findByMenuOrderMax(@Param("menuParent") Integer menuParent);

    @Query("select m from Menu m ")
    public Set<Menu> queryAllMenu();

    @Query("select count(t) from Menu t where t.menuParent=:id")
    public Integer countByMenuParent(@Param("id") Integer id);

    /**
     * 查询有效菜单
     * @return
     */
    public List<Menu> findByIfValid(Integer ifValid);

    @Query("select t.menuName from Menu t where t.id=:menuParent")
    public String findByMenuName(@Param("menuParent") Integer menuParent);

}
