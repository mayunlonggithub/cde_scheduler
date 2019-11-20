package com.zjcds.cde.scheduler.service.Impl;

import com.zjcds.cde.scheduler.dao.jpa.TransMonitorDao;
import com.zjcds.cde.scheduler.domain.entity.TransMonitor;
import com.zjcds.cde.scheduler.service.TransMonitorService;
import com.zjcds.cde.scheduler.utils.CommonUtils;
import com.zjcds.cde.scheduler.utils.Constant;
import com.zjcds.common.base.domain.page.Paging;
import com.zjcds.common.jpa.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * jackson 相关配置
 *
 * @author J on 20191107.
 */
@Service
public class TransMonitorServiceImpl implements TransMonitorService {

    @Autowired
    private TransMonitorDao transMonitorDao;

    /**
     *
     * @param paging
     * @param queryString
     * @param orderBys
     * @param uId
     * @return
     * @Description 获取分页列表
     */
    @Override
    public PageResult<TransMonitor> getList(Paging  paging, List<String> queryString, List<String> orderBys, Integer uId) {
        queryString.add("createUser~Eq~"+uId);
        PageResult<TransMonitor> transMonitorPageResult = transMonitorDao.findAll(paging,queryString,orderBys);
        return transMonitorPageResult;
    }


    /**
     * @param uId 用户ID
     * @return Integer
     * @Title getAllMonitorTrans
     * @Description 获取全部被监控的转换
     */
    @Override
    public Integer getAllMonitorTrans(Integer uId) {
        List<TransMonitor> transMonitorList = transMonitorDao.findByCreateUserAndMonitorStatus(uId,1);
        return transMonitorList.size();
    }

    /**
     * @param uId 用户ID
     * @return Integer
     * @Title getAllSuccess
     * @Description 获取所有转换执行成功的次数的和
     */
    @Override
    public Integer getAllSuccess(Integer uId) {
        List<TransMonitor> transMonitorList = transMonitorDao.findByCreateUserAndMonitorStatus(uId,1);
        Integer allSuccess = 0;
        for (TransMonitor transMonitor : transMonitorList) {
            allSuccess += transMonitor.getMonitorSuccess();
        }
        return allSuccess;
    }

    /**
     * @param uId 用户ID
     * @return Integer
     * @Title getAllFail
     * @Description 获取所有转换执行失败的次数的和
     */
    @Override
    public Integer getAllFail(Integer uId) {
        List<TransMonitor> transMonitorList = transMonitorDao.findByCreateUserAndMonitorStatus(uId,1);
        Integer allSuccess = 0;
        for (TransMonitor transMonitor : transMonitorList) {
            allSuccess += transMonitor.getMonitorFail();
        }
        return allSuccess;
    }


    /**
     * @param uId 用户ID
     * @return Map<String   ,   Object>
     * @Title getTransLine
     * @Description 获取7天内转换的折线图
     */
    @Override
    public Map<String, Object> getTransLine(Integer uId) {

        List<TransMonitor> transMonitorList = transMonitorDao.findByCreateUser(uId);
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        List<Integer> resultList = new ArrayList<Integer>();
        for (int i = 0; i < 7; i++) {
            resultList.add(i, 0);
        }
        if (transMonitorList != null && !transMonitorList.isEmpty()) {
            for (TransMonitor transMonitor : transMonitorList) {
                String runStatus = transMonitor.getRunStatus();
                if (runStatus != null && runStatus.contains(",")) {
                    String[] startList = runStatus.split(",");
                    for (String startOnce : startList) {
                        String[] startAndStopTime = startOnce.split(Constant.RUNSTATUS_SEPARATE);
                        if (startAndStopTime.length != 2)
                            continue;
                        //得到一次任务的起始时间和结束时间的毫秒值
                        resultList = CommonUtils.getEveryDayData(Long.parseLong(startAndStopTime[0]), Long.parseLong(startAndStopTime[1]), resultList);
                    }
                }
            }
        }
        resultMap.put("name", "转换");
        resultMap.put("data", resultList);
        return resultMap;
    }

    /**
     * @param userId  用户ID
     * @param transId 转换ID
     * @return void
     * @Title addMonitor
     * @Description 添加监控
     */
    @Override
    @Transactional
    public void addMonitor(Integer userId, Integer transId, Date nextExecuteTime) {

        TransMonitor templateOne = transMonitorDao.findByCreateUserAndMonitorTrans(userId,transId);
        if (null != templateOne) {
            templateOne.setMonitorStatus(1);
            StringBuilder runStatusBuilder = new StringBuilder();
            runStatusBuilder.append(templateOne.getRunStatus())
                    .append(",").append(new Date().getTime()).append(Constant.RUNSTATUS_SEPARATE);
            templateOne.setRunStatus(runStatusBuilder.toString());
            templateOne.setNextExecuteTime(nextExecuteTime);
            transMonitorDao.save(templateOne);
        } else {
            TransMonitor kTransMonitor = new TransMonitor();
            kTransMonitor.setMonitorTrans(transId);
            kTransMonitor.setCreateUser(userId);
            kTransMonitor.setMonitorSuccess(0);
            kTransMonitor.setMonitorFail(0);
            StringBuilder runStatusBuilder = new StringBuilder();
            runStatusBuilder.append(new Date().getTime()).append(Constant.RUNSTATUS_SEPARATE);
            kTransMonitor.setRunStatus(runStatusBuilder.toString());
            kTransMonitor.setMonitorStatus(1);
            kTransMonitor.setNextExecuteTime(nextExecuteTime);
            transMonitorDao.save(kTransMonitor);
        }
    }


}
