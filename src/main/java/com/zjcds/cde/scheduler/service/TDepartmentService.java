package com.zjcds.cde.scheduler.service;

import com.zjcds.cde.scheduler.domain.dto.TDepartmentForm;
import com.zjcds.cde.scheduler.domain.entity.Department;

import java.util.List;
import java.util.Map;

/**
 * @author huangyj on 20190831
 */
public interface TDepartmentService {

    public List<TDepartmentForm.TDepartmentTree> queryAllTDepartment();

    public Map<Integer,String> TDepartmentMap();

    public List<Department> findByDistrictFlag();
}
