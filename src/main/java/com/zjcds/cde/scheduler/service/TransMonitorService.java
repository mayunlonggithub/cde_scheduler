package com.zjcds.cde.scheduler.service;

import com.zjcds.cde.scheduler.domain.entity.TransMonitor;
import com.zjcds.common.base.domain.page.Paging;
import com.zjcds.common.jpa.PageResult;

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
}
