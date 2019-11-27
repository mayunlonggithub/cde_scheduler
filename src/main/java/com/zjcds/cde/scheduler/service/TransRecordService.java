package com.zjcds.cde.scheduler.service;

import com.zjcds.cde.scheduler.base.PageResult;
import com.zjcds.cde.scheduler.base.Paging;
import com.zjcds.cde.scheduler.domain.entity.TransRecord;
import com.zjcds.cde.scheduler.domain.entity.view.TransRecordView;

import javax.servlet.http.HttpServletResponse;
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
     * @return
     * @return BootTablePage
     */
    public PageResult<TransRecordView> getList(Paging paging, List<String> queryString, List<String> orderBys, Integer uId);

    /**
     * @Title getLogContent
     * @Description 转换日志内容
     * @param recordId 转换记录ID
     * @return
     * @throws IOException
     * @return String
     */
    public String getLogContent(Integer recordId, Integer uId) throws IOException;

    /**
     * 日志文件下载
     * @param recordId
     * @param uId
     * @param
     */
    public  void getLogDownload(Integer recordId,Integer uId, HttpServletResponse response) throws Exception;
}
