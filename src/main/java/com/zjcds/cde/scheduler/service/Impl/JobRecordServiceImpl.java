package com.zjcds.cde.scheduler.service.Impl;

import com.zjcds.cde.scheduler.base.PageResult;
import com.zjcds.cde.scheduler.base.Paging;
import com.zjcds.cde.scheduler.dao.jpa.JobRecordDao;
import com.zjcds.cde.scheduler.domain.entity.JobRecord;
import com.zjcds.cde.scheduler.service.JobRecordService;
import com.zjcds.cde.scheduler.service.JobService;
import com.zjcds.cde.scheduler.utils.Constant;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author J on 20191112
 */
@Service
public class JobRecordServiceImpl implements JobRecordService {

    @Autowired
    private JobRecordDao jobRecordDao;

    @Autowired
    private JobService jobService;

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
        Assert.notNull(uId,"未登录,请重新登录");
        queryString.add("createUser~Eq~"+uId);
        if (jobId != null){
            queryString.add("recordJob~Eq~"+jobId);
        }
        PageResult<JobRecord> jobRecordPageResult = jobRecordDao.findAll(paging, queryString, orderBys);
        List<JobRecord> jobRecordList = jobRecordPageResult.getContent();
        jobName(jobRecordList);
        return jobRecordPageResult;
    }

    /**
     * 加载转换名称
     * @param jobRecordList
     */
    public void jobName(List<JobRecord> jobRecordList){
        Map<Integer,String> map = jobService.jobNameMap();
        if(jobRecordList.size()>0&&jobRecordList!=null){
            for (JobRecord j:jobRecordList){
                j.setRecordJobName(map.get(j.getRecordJob()));
            }
        }
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
    public String getLogContent(Integer recordId, Integer uId) throws IOException{
        Assert.notNull(uId,"未登录,请重新登录");
        JobRecord kJobRecord = jobRecordDao.findByRecordIdAndCreateUser(recordId,uId);
        Assert.notNull(kJobRecord,"日志不存在或已删除");
        String logFilePath = kJobRecord.getLogFilePath();
        return FileUtils.readFileToString(new File(logFilePath), Constant.DEFAULT_ENCODING);
    }
}
