package com.zjcds.cde.scheduler.service;

import com.zjcds.cde.scheduler.base.PageResult;
import com.zjcds.cde.scheduler.base.Paging;
import com.zjcds.cde.scheduler.domain.entity.JobMonitor;
import com.zjcds.cde.scheduler.domain.entity.view.JobMonitorView;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
    public PageResult<JobMonitorView> getList(Paging paging, List<String> queryString, List<String> orderBys, Integer uId);


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
    public void addMonitor(Integer userId, Integer jobId, Date nextExecuteTime,Integer manualExe,Integer completionFlag);

    /**
     * @param uId 用户ID
     * @return Map<String   ,   Object>
     * @Title getTransLine
     * @Description 获取7天内作业的折线图
     */
    public Map<String, Object> getJobLine(Integer uId,List<String> dateList);

    /**
     * 更新作业状态
     * @param jobId
     * @param uId
     */
    public void updateRunStatusJob(Integer jobId,Integer uId,Integer runStatus);

    /**
     * @param uId 用户ID
     * @return Map<String   ,   Object>
     * @Title getTransLine
     * @Description 获取7天内作业的成功柱状图
     */
    public Map<String, Object> getJobSuccess(Integer uId,List<String> dateList);

    /**
     * @param uId 用户ID
     * @return Map<String   ,   Object>
     * @Title getTransLine
     * @Description 获取7天内作业的失败柱状图
     */
    public Map<String, Object> getJobFail(Integer uId,List<String> dateList);
}
