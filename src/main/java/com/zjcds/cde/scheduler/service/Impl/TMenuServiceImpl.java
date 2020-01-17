package com.zjcds.cde.scheduler.service.Impl;

import com.zjcds.cde.scheduler.base.BeanPropertyCopyUtils;
import com.zjcds.cde.scheduler.dao.jpa.MenuDao;
import com.zjcds.cde.scheduler.dao.jpa.RoleMenuDao;
import com.zjcds.cde.scheduler.domain.dto.TMenuForm;
import com.zjcds.cde.scheduler.domain.entity.Menu;
import com.zjcds.cde.scheduler.service.TMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author huangyj on 20190831
 */
@Service
public class TMenuServiceImpl implements TMenuService {

    @Autowired
    private MenuDao menuDao;
    @Autowired
    private RoleMenuDao roleMenuDao;

    /**
     * 获取菜单树
     * @return
     */
    @Override
    public List<TMenuForm.TMenuTree> queryAllTMenu(){
        List<Menu> menus = menuDao.findAll();
        List<TMenuForm.TMenu> tMenus = BeanPropertyCopyUtils.copy(menus, TMenuForm.TMenu.class);
        List<TMenuForm.TMenuTree> owner =  tMenuTree(tMenus);
        return owner;
    }

    /**
     * 新增菜单
     * @param addTMenu
     * @return
     */
    @Override
    @Transactional
    public TMenuForm.TMenu addTMenu(TMenuForm.AddTMenu addTMenu){
        Assert.notNull(addTMenu.getMenuParent(),"父id不能为空");
        Menu menu = BeanPropertyCopyUtils.copy(addTMenu, Menu.class);
        if(addTMenu.getMenuOrder()==null||addTMenu.getMenuOrder()==0){
            // 取当前节点最大顺序
            Integer menuOrder = menuDao.findByMenuOrderMax(menu.getMenuParent());
            if(menuOrder==null){
                menuOrder=0;
            }
            menu.setMenuOrder(menuOrder+1);
        }
        menu = menuDao.save(menu);
        TMenuForm.TMenu owner = BeanPropertyCopyUtils.copy(menu, TMenuForm.TMenu.class);
        return owner;
    }

    /**
     * 修改菜单
     * @param id
     * @param updateTMenu
     * @return
     */
    @Override
    @Transactional
    public TMenuForm.TMenu updateTMenu(Integer id, TMenuForm.UpdateTMenu updateTMenu){
        Assert.notNull(id,"要修改的id不能为空");
        Optional<Menu> tMenuOptional = menuDao.findById(id);
        Menu menu = new Menu();
        if(tMenuOptional.isPresent()){
            menu = tMenuOptional.get();
        }
        Assert.notNull(menu,"该数据不存在或已删除");
        menu = BeanPropertyCopyUtils.copy(updateTMenu, Menu.class);
        menu = menuDao.save(menu);
        TMenuForm.TMenu owner = BeanPropertyCopyUtils.copy(menu, TMenuForm.TMenu.class);
        return owner;
    }

    /**
     * 删除菜单
     * @param id
     */
    @Override
    @Transactional
    public void deleteTMenu(Integer id){
        Assert.notNull(id,"要修改的id不能为空");
        Optional<Menu> tMenuOptional = menuDao.findById(id);
        Menu menu = new Menu();
        if(tMenuOptional.isPresent()){
            menu = tMenuOptional.get();
        }
        Assert.notNull(menu,"该数据不存在或已删除");
        menuDao.deleteById(id);
    }

    /**
     * 构建菜单树
     * @param tMenus
     * @return
     */
    @Override
    public List<TMenuForm.TMenuTree> tMenuTree(List<TMenuForm.TMenu> tMenus){
        // 复制一份
        List<TMenuForm.TMenu> tMenuList = tMenus;
        List<TMenuForm.TMenuTree> treeList = BeanPropertyCopyUtils.copy(tMenuList, TMenuForm.TMenuTree.class);
        // 取出父节点
        tMenus = tMenus.stream().sorted(Comparator.comparing(TMenuForm.TMenu::getMenuOrder)).filter(e->e.getMenuParent()==0).collect(Collectors.toList());
        // 创建父资源
        List<TMenuForm.TMenuTree> tMenuTrees = BeanPropertyCopyUtils.copy(tMenus, TMenuForm.TMenuTree.class);
        // 循环父节点加载子节点
        if(tMenuTrees!=null&&tMenuTrees.size()>=0){
            for (TMenuForm.TMenuTree tree : tMenuTrees){
                //查询出父节点下一层子节点数据
                List<TMenuForm.TMenu> menuList = tMenuList.stream().sorted(Comparator.comparing(TMenuForm.TMenu::getMenuOrder)).filter(e->e.getMenuParent().equals(tree.getId())).collect(Collectors.toList());
                if(menuList!=null&&menuList.size()>0){
                    List<TMenuForm.TMenuTree> trees = BeanPropertyCopyUtils.copy(menuList, TMenuForm.TMenuTree.class);
                    // 循环子节点
                    trees = tMenuTrees(trees,treeList);
                    tree.setTMenuTreeList(trees);
                }
            }
        }
        return tMenuTrees;
    }

    /**
     * 循环加载子菜单
     * @param tMenus
     * @return
     */
    public List<TMenuForm.TMenuTree> tMenuTrees(List<TMenuForm.TMenuTree> tMenus, List<TMenuForm.TMenuTree> treeList){
        List<TMenuForm.TMenuTree> tMenuTrees = tMenus;
        for (TMenuForm.TMenuTree t : tMenuTrees){
            List<TMenuForm.TMenuTree> menuLists = treeList.stream().sorted(Comparator.comparing(TMenuForm.TMenuTree::getMenuOrder)).filter(e->e.getMenuParent().equals(t.getId())).collect(Collectors.toList());
            if(menuLists!=null&&menuLists.size()>0){
                menuLists = tMenuTrees(menuLists,treeList);
                t.setTMenuTreeList(menuLists);
            }
        }
        return tMenuTrees;
    }

    /**
     * 根据角色查询菜单信息
     * @param roleId
     * @return
     */
    public List<TMenuForm.TMenu> findByRoleId(Integer roleId){
        Assert.notNull(roleId,"要查询的角色id不能为空");
        List<Integer> menuId = roleMenuDao.findByRoleId(roleId);
        List<Menu> menuList = new ArrayList<>();
        List<TMenuForm.TMenu> owner = new ArrayList<>();
        if(menuId!=null&&menuId.size()>0){
            List<Menu> menus = menuDao.findByIdList(menuId);
            for (Menu t : menus){
                Integer tMenu = menuDao.countByMenuParent(t.getId());
                if(tMenu==0){
                    menuList.add(t);
                }
            }
            owner = BeanPropertyCopyUtils.copy(menuList, TMenuForm.TMenu.class);
        }
//        List<TMenuForm.TMenuTree> owner =  tMenuTree(menuList);
        return owner;
    }

    /**
     * 根据菜单id查询详情
     */
    @Override
    public TMenuForm.TMenu findById(Integer menuId){
        Optional<Menu> tMenu = menuDao.findById(menuId);
        TMenuForm.TMenu owner = new TMenuForm.TMenu();
        if(tMenu.isPresent()){
            owner = BeanPropertyCopyUtils.copy(tMenu.get(), TMenuForm.TMenu.class);
            String menuName = menuDao.findByMenuName(tMenu.get().getMenuParent());
            owner.setMenuParentName(menuName);
        }

        return owner;
    }

    /**
     * 查询有效菜单
     * @return
     */
    @Override
    public List<TMenuForm.TMenuTree> findValidTMenu(){
        List<Menu> menus = menuDao.findByIfValid(1);
        List<TMenuForm.TMenu> tMenus = BeanPropertyCopyUtils.copy(menus, TMenuForm.TMenu.class);
        List<TMenuForm.TMenuTree> owner =  tMenuTree(tMenus);
        return owner;
    }
}
