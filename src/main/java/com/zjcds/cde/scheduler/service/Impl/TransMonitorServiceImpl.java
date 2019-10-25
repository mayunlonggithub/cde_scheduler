package com.zjcds.cde.scheduler.service.Impl;

import com.zjcds.cde.scheduler.dao.jpa.TransMonitorDao;
import com.zjcds.cde.scheduler.service.TransMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransMonitorServiceImpl implements TransMonitorService {

    @Autowired
    private TransMonitorDao transMonitorDao;

    /**
     * @param start 起始行数
     * @param size  每页数据条数
     * @param uId   用户ID
     * @return BootTablePage
     * @Title getList
     * @Description 获取分页列表
     */
    public BootTablePage getList(Integer start, Integer size, Integer monitorStatus, Integer categoryId, String transName, Integer uId) {
        KTransMonitor template = new KTransMonitor();
        template.setAddUser(uId);
        template.setMonitorStatus(monitorStatus);
        if (StringUtils.isNotEmpty(transName)) {
            template.setTransName(transName);
        }
        List<KTransMonitor> kTransMonitorList = kTransMonitorDao.pageQuery(template, start, size, categoryId);
        Long allCount = kTransMonitorDao.allCount(template, categoryId);
        BootTablePage bootTablePage = new BootTablePage();
        bootTablePage.setRows(kTransMonitorList);
        bootTablePage.setTotal(allCount);
        return bootTablePage;
    }

}
