package com.zjcds.cde.scheduler.service;

import com.zjcds.cde.scheduler.domain.entity.JobRecord;
import com.zjcds.common.base.domain.page.Paging;
import com.zjcds.common.jpa.PageResult;

import java.io.IOException;
import java.util.List;

/**
 * @author J on 20191112
 */
public interface JobRecordService {

    /**
     * @Title getList
     * @Description 获取带分页的列表
     * @param uId 用户ID
     * @param jobId 作业ID
     * @return
     * @return BootTablePage
     */
    public PageResult<JobRecord> getList(Paging paging, List<String> queryString, List<String> orderBys, Integer uId, Integer jobId);

    /**
     * @Title getLogContent
     * @Description 转换日志内容
     * @param recordId 转换记录ID
     * @return
     * @throws IOException
     * @return String
     */
    public String getLogContent(Integer recordId) throws IOException;
}
