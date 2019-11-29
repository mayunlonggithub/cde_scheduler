package com.zjcds.cde.scheduler.service;

import com.zjcds.cde.scheduler.base.PageResult;
import com.zjcds.cde.scheduler.base.Paging;
import com.zjcds.cde.scheduler.domain.dto.JobMonitorForm;
import com.zjcds.cde.scheduler.domain.entity.JobRecord;
import com.zjcds.cde.scheduler.domain.entity.view.JobRecordView;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
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
     * @return
     * @return BootTablePage
     */
    public PageResult<JobRecordView> getList(Paging paging, List<String> queryString, List<String> orderBys, Integer uId);

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
    public void getLogDownload(Integer recordId,Integer uId,HttpServletResponse response) throws Exception;

    /**
     * 当天作业运行统计
     * @param uId
     * @return
     */
    public List<JobMonitorForm.JobMonitorStatis> getListToday(Integer uId);
}
