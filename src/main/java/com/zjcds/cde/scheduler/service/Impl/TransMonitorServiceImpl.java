package com.zjcds.cde.scheduler.service.Impl;

import com.zjcds.cde.scheduler.dao.jpa.TransMonitorDao;
import com.zjcds.cde.scheduler.domain.entity.TransMonitor;
import com.zjcds.cde.scheduler.service.TransMonitorService;
import com.zjcds.common.base.domain.page.Paging;
import com.zjcds.common.jpa.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransMonitorServiceImpl implements TransMonitorService {

    @Autowired
    private TransMonitorDao transMonitorDao;

    /**
     *
     * @param paging
     * @param queryString
     * @param orderBys
     * @param uId
     * @return
     * @Description 获取分页列表
     */
    public PageResult<TransMonitor> getList(Paging  paging, List<String> queryString, List<String> orderBys, Integer uId) {
        queryString.add("createUser~Eq~"+uId);
        PageResult<TransMonitor> transMonitorPageResult = transMonitorDao.findAll(paging,queryString,orderBys);
        return transMonitorPageResult;
    }


    /**
     * @param uId 用户ID
     * @return Integer
     * @Title getAllMonitorTrans
     * @Description 获取全部被监控的转换
     */
//    public Integer getAllMonitorTrans(Integer uId) {
//        TransMonitor template = new TransMonitor();
//        template.setCreateUser(uId);
//        template.setMonitorStatus(1);
//        List<TransMonitor> kTransMonitorList = kTransMonitorDao.template(template);
//        return kTransMonitorList.size();
//    }


}
