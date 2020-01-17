package com.zjcds.cde.scheduler.service.Impl;

import com.zjcds.cde.scheduler.base.BeanPropertyCopyUtils;
import com.zjcds.cde.scheduler.dao.jpa.DepartmentDao;
import com.zjcds.cde.scheduler.domain.dto.TDepartmentForm;
import com.zjcds.cde.scheduler.domain.entity.Department;
import com.zjcds.cde.scheduler.service.TDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author huangyj on 20190831
 */
@Service
public class TDepartmentServiceImpl implements TDepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    @Override
    public List<TDepartmentForm.TDepartmentTree> queryAllTDepartment(){
        List<Department> department = departmentDao.findAll();
        List<TDepartmentForm.TDepartment> tDepartmentList = BeanPropertyCopyUtils.copy(department, TDepartmentForm.TDepartment.class);
        List<TDepartmentForm.TDepartmentTree> owner = tDepartmentTree(tDepartmentList);
        return owner;
    }

    /**
     * 构建菜单树
     * @param tDepartments
     * @return
     */
    public List<TDepartmentForm.TDepartmentTree> tDepartmentTree(List<TDepartmentForm.TDepartment> tDepartments){
        // 复制一份
        List<TDepartmentForm.TDepartment> tDepartmentList = tDepartments;
        // 取出父节点
        tDepartments = tDepartments.stream().filter(e->e.getPid()==0).collect(Collectors.toList());
        // 创建父资源
        List<TDepartmentForm.TDepartmentTree> tDepartmentTrees = BeanPropertyCopyUtils.copy(tDepartments, TDepartmentForm.TDepartmentTree.class);
        // 循环父节点加载子节点
        if(tDepartmentTrees!=null&&tDepartmentTrees.size()>=0){
            for (TDepartmentForm.TDepartmentTree tree : tDepartmentTrees){
                List<TDepartmentForm.TDepartment> menuList = tDepartmentList.stream().filter(e->e.getPid().equals(tree.getOid())).collect(Collectors.toList());
                if(menuList!=null&&menuList.size()>0){
                    List<TDepartmentForm.TDepartmentTree> trees = BeanPropertyCopyUtils.copy(menuList, TDepartmentForm.TDepartmentTree.class);
                    // 循环子节点
                    trees = tDepartmentTrees(trees);
                    tree.setTDepartmentTrees(trees);

                }
            }
        }
        return tDepartmentTrees;
    }

    /**
     * 循环加载子菜单
     * @param tDepartments
     * @return
     */
    public List<TDepartmentForm.TDepartmentTree> tDepartmentTrees(List<TDepartmentForm.TDepartmentTree> tDepartments){
        List<TDepartmentForm.TDepartmentTree> tDepartmentTrees = tDepartments;
        for (TDepartmentForm.TDepartmentTree t : tDepartmentTrees){
            List<TDepartmentForm.TDepartmentTree> menuList = tDepartments.stream().filter(e->e.getPid().equals(t.getOid())).collect(Collectors.toList());
            if(menuList!=null&&menuList.size()>0){
                menuList = tDepartmentTrees(menuList);
                t.setTDepartmentTrees(menuList);
            }
        }
        return tDepartmentTrees;
    }

    @Override
    public Map<Integer,String> TDepartmentMap(){
        List<Department> departments = departmentDao.findAll();
        Map<Integer,String> map = new HashMap<>();
        for (Department t : departments){
            map.put(t.getOid(),t.getName());
        }
        return map;
    }

    /**
     * 查询所有省级部门
     * @return
     */
    @Override
    public List<Department> findByDistrictFlag(){
        List<Department> departments = departmentDao.findByDistrictFlag();
        return departments;
    }
}
