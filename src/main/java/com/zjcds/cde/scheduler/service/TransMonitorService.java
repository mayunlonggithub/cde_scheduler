package com.zjcds.cde.scheduler.service;

import com.zjcds.cde.scheduler.base.PageResult;
import com.zjcds.cde.scheduler.base.Paging;
import com.zjcds.cde.scheduler.domain.entity.TransMonitor;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * jackson 相关配置
 *
 * @author J on 20191107.
 */
public interface TransMonitorService {

    /**
     *
     * @param paging
     * @param queryString
     * @param orderBys
     * @param uId
     * @return
     * @Description 获取分页列表
     */
    public PageResult<TransMonitor> getList(Paging paging, List<String> queryString, List<String> orderBys, Integer uId);

    public List<TransMonitor> getList(Integer uId);

    /**
     * @param uId 用户ID
     * @return Integer
     * @Title getAllMonitorTrans
     * @Description 获取全部被监控的转换
     */
    public Integer getAllMonitorTrans(Integer uId);

    /**
     * @param uId 用户ID
     * @return Integer
     * @Title getAllSuccess
     * @Description 获取所有转换执行成功的次数的和
     */
    public Integer getAllSuccess(Integer uId);

    /**
     * @param uId 用户ID
     * @return Integer
     * @Title getAllFail
     * @Description 获取所有转换执行失败的次数的和
     */
    public Integer getAllFail(Integer uId);

    /**
     * @param uId 用户ID
     * @return Map<String   ,   Object>
     * @Title getTransLine
     * @Description 获取7天内转换的折线图
     */
    public Map<String, Object> getTransLine(Integer uId);

    /**
     * @param userId  用户ID
     * @param transId 转换ID
     * @return void
     * @Title addMonitor
     * @Description 添加监控
     */
    public void addMonitor(Integer userId, Integer transId, Date nextExecuteTime);

    /**
     * 更新转换状态
     * @param transId
     * @param uId
     */
    public void updateRunStatusTrans(Integer transId,Integer uId,Integer runStatus);
}
