package com.zjcds.cde.scheduler.domain.entity;

import com.zjcds.cde.scheduler.base.CreateModifyTime;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author huangyj on 20190831
 */
@Entity
@Table(name = "T_MENU")
public class Menu extends CreateModifyTime {
    private Integer id;
    private String menuName;
    private String menuDes;
    private String menuIcon;
    private String menuUrl;
    private String routeName;
    private Integer menuParent;
    private Integer menuOrder;
    private Integer ifValid;

    private List<Menu> menus = new ArrayList<>();



    @Id
    @Column(name = "ID")
    @TableGenerator(name = "idGenerator", table = "t_id_generator", pkColumnName = "id_key", pkColumnValue = "tMenu", valueColumnName = "id_value")
    @GeneratedValue(generator = "idGenerator", strategy = GenerationType.TABLE)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @Basic
    @Column(name = "MENU_NAME")
    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    @Basic
    @Column(name = "MENU_DES")
    public String getMenuDes() {
        return menuDes;
    }

    public void setMenuDes(String menuDes) {
        this.menuDes = menuDes;
    }

    @Basic
    @Column(name = "MENU_ICON")
    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    @Basic
    @Column(name = "MENU_URL")
    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    @Basic
    @Column(name = "ROUTE_NAME")
    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    @Basic
    @Column(name = "MENU_PARENT")
    public Integer getMenuParent() {
        return menuParent;
    }

    public void setMenuParent(Integer menuParent) {
        this.menuParent = menuParent;
    }

    @Basic
    @Column(name = "MENU_ORDER")
    public Integer getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(Integer menuOrder) {
        this.menuOrder = menuOrder;
    }

    @Basic
    @Column(name = "IF_VALID")
    public Integer getIfValid() {
        return ifValid;
    }

    public void setIfValid(Integer ifValid) {
        this.ifValid = ifValid;
    }

//    @JoinTable(name = "r_role_menu",
//            joinColumns = {@JoinColumn(name="role_id")},
//            inverseJoinColumns = {@JoinColumn(name = "menu_id")}
//    )
//    @ManyToMany(fetch = FetchType.LAZY)
    @Transient
    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }
}
