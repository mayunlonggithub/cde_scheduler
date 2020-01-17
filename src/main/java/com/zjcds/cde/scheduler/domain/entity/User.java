package com.zjcds.cde.scheduler.domain.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.zjcds.cde.scheduler.base.CreateModifyTime;
import com.zjcds.cde.scheduler.domain.enums.UserStatus;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * @author huangyj on 20190831
 */
@Entity
@Table(name = "t_user")
public class User extends CreateModifyTime implements UserDetails {
    private Integer id;
    private String account;
    private String name;
    @JSONField(serialize = false)
    private String password;
    private UserStatus status;
    private Integer deptPid;
    private Integer deptId;
    private String phone;
    private String email;
    private List<Role> roles;
    private Department department;
    private List<Menu> menus;
    private Integer updateFlag;
//    private Set<Role> roles = new HashSet<>();

    @Id
    @Column(name = "ID")
    @TableGenerator(name = "idGenerator", table = "t_id_generator", pkColumnName = "id_key", pkColumnValue = "tUser", valueColumnName = "id_value")
    @GeneratedValue(generator = "idGenerator", strategy = GenerationType.TABLE)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "ACCOUNT")
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Basic
    @Column(name = "nickname")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "PASSWORD")

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "STATUS")
    @Convert(converter = UserStatus.UserStatusConverter.class)
    public UserStatus getStatus() {
        //默认激活状态
        if (status == null) {
            status = UserStatus.ACTIVE;
        }
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    @Basic
    @Column(name = "dept_pid")
    public Integer getDeptPid() {
        return deptPid;
    }

    public void setDeptPid(Integer deptPid) {
        this.deptPid = deptPid;
    }

    @Basic
    @Column(name = "dept_id")
    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    @Basic
    @Column(name = "PHONE")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "update_flag")
    public Integer getUpdateFlag() {
        return updateFlag;
    }

    public void setUpdateFlag(Integer updateFlag) {
        this.updateFlag = updateFlag;
    }


//    @ManyToMany
//    @JoinTable(name = "r_user_role",
//            joinColumns = {@JoinColumn(name = "user_id")},
//            inverseJoinColumns = {@JoinColumn(name = "role_id")})
//    public Set<Role> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(Set<Role> roles) {
//        this.roles = roles;
//    }

    @Transient
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }



    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new HashSet<>();
        boolean hasDefault = false;
        if (roles != null) {
            for (Role role : roles) {
                Assert.hasText(role.getName(), "角色不能为空！");
                if(StringUtils.startsWith(role.getName(),"ROLE_")) {
                    authorities.add(new SimpleGrantedAuthority(role.getName()));
                }else {
                    authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));
                }
                if(StringUtils.equals(role.getName(),"DEFAULT") || StringUtils.equals(role.getName(),"ROLE_DEFAULT")) {
                    hasDefault = true;
                }
            }
            //注入default权限
            if(!hasDefault){
                authorities.add(new SimpleGrantedAuthority("ROLE_DEFAULT"));
            }
        }
        return authorities;
    }

    @Override
    @Transient
    public String getUsername() {
        return account;
    }

    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isEnabled() {
        if (status == null || status != UserStatus.ACTIVE)
            return false;
        else
            return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        return account.equals(user.account);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + account.hashCode();
        return result;
    }

    @Transient
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Transient
    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }
}
