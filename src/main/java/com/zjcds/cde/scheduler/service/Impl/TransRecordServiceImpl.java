package com.zjcds.cde.scheduler.service.Impl;

import com.zjcds.cde.scheduler.dao.jpa.TransRecordDao;
import com.zjcds.cde.scheduler.domain.entity.TransRecord;
import com.zjcds.cde.scheduler.service.TransRecordService;
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
 * jackson 相关配置
 *
 * @author J on 20191107.
 */
@Service
public class TransRecordServiceImpl implements TransRecordService {

    @Autowired
    private TransRecordDao transRecordDao;

    /**
     * @Title getList
     * @Description 获取列表
     * @param uId 用户ID
     * @param transId 转换ID
     * @return
     * @return BootTablePage
     */
    @Override
    public PageResult<TransRecord> getList(Paging paging, List<String> queryString, List<String> orderBys, Integer uId, Integer transId){
        queryString.add("createUser~Eq~"+uId);
        if (transId != null){
            queryString.add("transId~Eq~"+transId);
        }
        PageResult<TransRecord> transRecordList = transRecordDao.findAll(paging, queryString, orderBys);
        return transRecordList;
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
        TransRecord kTransRecord = transRecordDao.findOne(recordId);
        String logFilePath = kTransRecord.getLogFilePath();
        return FileUtils.readFileToString(new File(logFilePath), Constant.DEFAULT_ENCODING);
    }

}
