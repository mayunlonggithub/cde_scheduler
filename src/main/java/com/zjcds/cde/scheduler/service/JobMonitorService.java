package com.zjcds.cde.scheduler.service;

import com.zjcds.cde.scheduler.domain.entity.JobMonitor;
import com.zjcds.common.base.domain.page.Paging;
import com.zjcds.common.jpa.PageResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author J on 20191111
 */
public interface JobMonitorService {


    /**
     *
     * @param paging
     * @param queryString
     * @param orderBys
     * @param uId
     * @return
     * @Description 获取作业监控分页列表
     */
    public PageResult<JobMonitor> getList(Paging paging, List<String> queryString, List<String> orderBys, Integer uId);

    /**
     * @param uId 用户ID
     * @return Integer
     * @Title getAllMonitorJob
     * @Description 获取所有的监控作业
     */
    public Integer getAllMonitorJob(Integer uId);

    /**
     * @param uId 用户ID
     * @return Integer
     * @Title getAllSuccess
     * @Description 获取执行成功的数
     */
    public Integer getAllSuccess(Integer uId);

    /**
     * @param uId 用户ID
     * @return Integer
     * @Title getAllFail
     * @Description 获取执行失败的数
     */
    public Integer getAllFail(Integer uId);

    /**
     * @param userId 用户ID
     * @param jobId  转换ID
     * @return void
     * @Title addMonitor
     * @Description 添加监控
     */
    public void addMonitor(Integer userId, Integer jobId, Date nextExecuteTime);
}
