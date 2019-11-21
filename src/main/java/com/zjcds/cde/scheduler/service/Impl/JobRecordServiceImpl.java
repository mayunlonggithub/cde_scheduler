package com.zjcds.cde.scheduler.service.Impl;

import com.zjcds.cde.scheduler.dao.jpa.JobRecordDao;
import com.zjcds.cde.scheduler.domain.entity.JobRecord;
import com.zjcds.cde.scheduler.service.JobRecordService;
import com.zjcds.cde.scheduler.utils.Constant;
import com.zjcds.common.base.domain.page.Paging;
import com.zjcds.common.jpa.PageResult;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author J on 20191112
 */
@Service
public class JobRecordServiceImpl implements JobRecordService {

    @Autowired
    private JobRecordDao jobRecordDao;


    /**
     * @Title getList
     * @Description 获取带分页的列表
     * @param uId 用户ID
     * @param jobId 作业ID
     * @return
     * @return BootTablePage
     */
    @Override
    public PageResult<JobRecord> getList(Paging paging, List<String> queryString, List<String> orderBys, Integer uId, Integer jobId){

        queryString.add("createUser~Eq~"+uId);
        if (jobId != null){
            queryString.add("recordJob~Eq~"+jobId);
        }
        PageResult<JobRecord> jobRecordList = jobRecordDao.findAll(paging, queryString, orderBys);

        return jobRecordList;
    }

    /**
     * @Title getLogContent
     * @Description 转换日志内容
     * @param recordId 转换记录ID
     * @return
     * @throws IOException
     * @return String
     */
    @Override
    public String getLogContent(Integer recordId) throws IOException{
        JobRecord kJobRecord = jobRecordDao.findOne(recordId);
        String logFilePath = kJobRecord.getLogFilePath();
        return FileUtils.readFileToString(new File(logFilePath), Constant.DEFAULT_ENCODING);
    }
}
