package com.zjcds.cde.scheduler.dao.jpa;

import com.zjcds.cde.scheduler.base.CustomRepostory;
import com.zjcds.cde.scheduler.domain.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author huangyj on 20190831
 */
public interface UserDao extends CustomRepostory<User,Integer> {

//    @EntityGraph(attributePaths = {"roles"})
    @Query("select t from User t where t.id=:id")
    public Optional<User> findById(@Param("id") Integer id);

//    @EntityGraph(attributePaths = "roles")
    public User findByAccount(String account);


//    /**
//     * 根据账号名查询用户
//     *
//     * @param account
//     * @return
//     */
//    @EntityGraph(attributePaths = "roles")
//    public User findByAccount(String account);

//    @EntityGraph(attributePaths = {"roles"})
//    @Query("select u from User u where u.id = :id")
//    public Optional<User> findById(@Param("id") Integer id);

//    @Query("select distinct m from User u join u.roles r join r.menus m where u.id = :id")
//    public Set<Menu> findMenusForUser(@Param("id") Integer id);

    /**
     * 根据depId获取用户列表
     * @param depId
     * @return
     */
    @Query("select u from User u where u.deptId = :depId")
    public List<User> findUsersByDepId(@Param("depId") Integer depId);

    public List<User> findByNameLike(String name);

    public Long countByNameLike(String name);

    @Query("select u from User u where u.account like  :account or u.name like  :name ")
    public List<User> findActiveUserByAccountOrName(@Param("account") String account, @Param("name") String name);

    @Query("select count(u) from User u where u.account like  :account or u.name like  :name ")
    public Long countActiveUserByAccountOrName(@Param("account") String account, @Param("name") String name);

    public List<User> findByIdIn(List<Integer> userIdList);
}
