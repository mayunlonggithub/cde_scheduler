package com.zjcds.cde.scheduler.service.Impl;

import com.zjcds.cde.scheduler.base.PageResult;
import com.zjcds.cde.scheduler.base.Paging;
import com.zjcds.cde.scheduler.dao.jpa.TransRecordDao;
import com.zjcds.cde.scheduler.domain.entity.TransRecord;
import com.zjcds.cde.scheduler.service.TransRecordService;
import com.zjcds.cde.scheduler.service.TransService;
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
 * jackson 相关配置
 *
 * @author J on 20191107.
 */
@Service
public class TransRecordServiceImpl implements TransRecordService {

    @Autowired
    private TransRecordDao transRecordDao;

    @Autowired
    private TransService transService;


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
        Assert.notNull(uId,"未登录,请重新登录");
        queryString.add("createUser~Eq~"+uId);
        if (transId != null){
            queryString.add("recordTrans~Eq~"+transId);
        }
        PageResult<TransRecord> transRecordPageResult = transRecordDao.findAll(paging, queryString, orderBys);
        List<TransRecord> transRecordList = transRecordPageResult.getContent();
        transName(transRecordList);
        return transRecordPageResult;
    }

    /**
     * 加载作业名称
     * @param transRecordList
     */
    public void transName(List<TransRecord> transRecordList){
        Map<Integer,String> map = transService.transNameMap();
        if(transRecordList.size()>0&&transRecordList!=null){
            for (TransRecord t:transRecordList){
                t.setRecordTransName(map.get(t.getRecordTrans()));
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
    public String getLogContent(Integer recordId,Integer uId) throws IOException{
        Assert.notNull(uId,"未登录,请重新登录");
        TransRecord transRecord = transRecordDao.findByRecordIdAndCreateUser(recordId,uId);
        Assert.notNull(transRecord,"日志不存在或已删除");
        String logFilePath = transRecord.getLogFilePath();
        return FileUtils.readFileToString(new File(logFilePath), Constant.DEFAULT_ENCODING);
    }

}
