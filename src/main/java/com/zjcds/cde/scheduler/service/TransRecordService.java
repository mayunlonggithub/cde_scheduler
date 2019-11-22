package com.zjcds.cde.scheduler.service;

import com.zjcds.cde.scheduler.base.PageResult;
import com.zjcds.cde.scheduler.base.Paging;
import com.zjcds.cde.scheduler.domain.entity.TransRecord;

import java.io.IOException;
import java.util.List;

/**
 * jackson 相关配置
 *
 * @author J on 20191107.
 */
public interface TransRecordService {

    /**
     * @Title getList
     * @Description 获取列表
     * @param uId 用户ID
     * @param transId 转换ID
     * @return
     * @return BootTablePage
     */
    public PageResult<TransRecord> getList(Paging paging, List<String> queryString, List<String> orderBys, Integer uId, Integer transId);

    /**
     * @Title getLogContent
     * @Description 转换日志内容
     * @param recordId 转换记录ID
     * @return
     * @throws IOException
     * @return String
     */
    public String getLogContent(Integer recordId, Integer uId) throws IOException;
}
