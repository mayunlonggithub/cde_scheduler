package com.zjcds.cde.scheduler.service.Impl;

import com.zjcds.cde.scheduler.dao.jpa.DepartmentDao;
import com.zjcds.cde.scheduler.dao.jpa.MenuDao;
import com.zjcds.cde.scheduler.dao.jpa.RoleDao;
import com.zjcds.cde.scheduler.dao.jpa.RoleMenuDao;
import com.zjcds.cde.scheduler.dao.jpa.UserDao;
import com.zjcds.cde.scheduler.dao.jpa.UserRoleDao;
import com.zjcds.cde.scheduler.domain.entity.Department;
import com.zjcds.cde.scheduler.domain.entity.Menu;
import com.zjcds.cde.scheduler.domain.entity.Role;
import com.zjcds.cde.scheduler.domain.entity.User;
import com.zjcds.cde.scheduler.service.UserService;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created date:2019-08-21
 *
 * @author shihuajie
 */
@Service
public class UserServiceImpl implements UserService {

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;
    
    @Autowired
    private MenuDao menuDao;
    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired

    private RoleMenuDao roleMenuDao;


    @Override
    public User queryUserByAccount(String accunt) {
        User user = userDao.findByAccount(accunt);
         //根据用户查询部门信息
        Department sysDept = departmentDao.findByOid(user.getDeptId());
        user.setDepartment(sysDept);
        // 根据用户查询角色

        List<Integer> roleId = userRoleDao.findByUserId(user.getId());
        if(roleId!=null&&roleId.size()>0){
            List<Role> roleList = roleDao.findByIdList(roleId);
            user.setRoles(roleList);
        }
//            user.setRoles(new ArrayList<Role>());
        // 根据角色查询菜单信息
        List<Integer> menuId = roleMenuDao.findByRoleId(roleId);
        if(menuId!=null&&menuId.size()>0){
            List<Menu> menuList = menuDao.findByIdList(menuId);
            menuList = menuList.stream().filter(e->e.getIfValid()==1).collect(Collectors.toList());
            user.setMenus(tMenuTree(menuList));
        }
        return user;
    }

    @Override
    public User queryUserWithRole(Integer id) {
        return userDao.findById(id).get();
    }

    @Override
    public List<User> queryUsersByNameLike(String name) {
        return userDao.findByNameLike(name);
    }

    @Override
    public Long countUsersByNameLike(String name) {
        return userDao.countByNameLike(name);
    }

    @Override
    public List<User> queryActiveUserByAccountOrName(String account, String name){
        return userDao.findActiveUserByAccountOrName(account, name);
    }

    @Override
    public Long countActiveUserByAccountOrName(String account, String name){
        return userDao.countActiveUserByAccountOrName(account, name);
    }

    @Override
    public List<User> queryByIdIn(Set<Integer> userIdSet) {
        return userDao.findByIdIn(new ArrayList<>(userIdSet));
    }


    /**
     * 构建菜单树
     * @param menus
     * @return
     */
    public List<Menu> tMenuTree(List<Menu> menus){
        // 复制一份
        List<Menu> tMenuList = menus;
        // 取出父节点
        menus = menus.stream().sorted(Comparator.comparing(Menu::getMenuOrder)).filter(e->e.getMenuParent()==0).collect(Collectors.toList());
        // 创建父资源
        List<Menu> menuTrees = menus;
        // 循环父节点加载子节点
        if(menuTrees !=null&& menuTrees.size()>=0){
            for (Menu tree : menuTrees){
                List<Menu> menuList = tMenuList.stream().sorted(Comparator.comparing(Menu::getMenuOrder)).filter(e->e.getMenuParent().equals(tree.getId())).collect(Collectors.toList());
                if(menuList!=null&&menuList.size()>0){
                    List<Menu> trees = menuList;
                    // 循环子节点
                    trees = tMenuTrees(trees,tMenuList);
                    tree.setMenus(trees);

                }
            }
        }
        return menuTrees;
    }

    /**
     * 循环加载子菜单
     * @param menus
     * @return
     */
    public List<Menu> tMenuTrees(List<Menu> menus, List<Menu> tMenuList){
        List<Menu> menuTrees = menus;
        for (Menu t : menuTrees){
            List<Menu> menuList = tMenuList.stream().sorted(Comparator.comparing(Menu::getMenuOrder)).filter(e->e.getMenuParent().equals(t.getId())).collect(Collectors.toList());
            if(menuList!=null&&menuList.size()>0){
                menuList = tMenuTrees(menuList,tMenuList);
                t.setMenus(menuList);
            }
        }
        return menuTrees;
    }
}
