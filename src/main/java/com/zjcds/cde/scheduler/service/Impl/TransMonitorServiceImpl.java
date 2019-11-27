package com.zjcds.cde.scheduler.service.Impl;

import com.zjcds.cde.scheduler.base.PageResult;
import com.zjcds.cde.scheduler.base.Paging;
import com.zjcds.cde.scheduler.dao.jpa.TransMonitorDao;
import com.zjcds.cde.scheduler.dao.jpa.view.TransMonitorViewDao;
import com.zjcds.cde.scheduler.domain.entity.TransMonitor;
import com.zjcds.cde.scheduler.domain.entity.view.TransMonitorView;
import com.zjcds.cde.scheduler.service.TransMonitorService;
import com.zjcds.cde.scheduler.service.TransService;
import com.zjcds.cde.scheduler.utils.CommonUtils;
import com.zjcds.cde.scheduler.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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
    @Autowired
    private TransMonitorViewDao transMonitorViewDao;
    @Autowired
    private TransService transService;

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
    public PageResult<TransMonitorView> getList(Paging paging, List<String> queryString, List<String> orderBys, Integer uId) {
        Assert.notNull(uId,"未登录,请重新登录");
        queryString.add("createUser~Eq~"+uId);
        PageResult<TransMonitorView> transMonitorPageResult = transMonitorViewDao.findAll(paging,queryString,orderBys);
//        List<TransMonitor> transMonitorList = transMonitorPageResult.getContent();
//        transName(transMonitorList);
        return transMonitorPageResult;
    }

    @Override
    public List<TransMonitorView> getList(Integer uId) {
        Assert.notNull(uId,"未登录,请重新登录");
        List<TransMonitorView> transMonitorList = transMonitorViewDao.findByCreateUserAndMonitorStatus(uId,1);
//        transName(transMonitorList);
        return transMonitorList;
    }

    /**
     * 加载作业名称
     * @param transMonitorList
     */
    public void transName(List<TransMonitor> transMonitorList){
        Map<Integer,String> map = transService.transNameMap();
        if(transMonitorList.size()>0&&transMonitorList!=null){
            for (TransMonitor t:transMonitorList){
                t.setMonitorTransName(map.get(t.getMonitorTrans()));
            }
        }
    }

    /**
     * @param uId 用户ID
     * @return Integer
     * @Title getAllMonitorTrans
     * @Description 获取全部被监控的转换
     */
    @Override
    public Integer getAllMonitorTrans(Integer uId) {
        Assert.notNull(uId,"未登录,请重新登录");
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
        Assert.notNull(uId,"未登录,请重新登录");
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
        Assert.notNull(uId,"未登录,请重新登录");
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
        Assert.notNull(uId,"未登录,请重新登录");
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
        Assert.notNull(userId,"未登录,请重新登录");
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

    /**
     * 更新转换状态
     * @param transId
     * @param uId
     */
    @Async
    @Override
    @Transactional
    public void updateRunStatusTrans(Integer transId,Integer uId,Integer runStatus) {
        Assert.notNull(transId, "转换id不能为空");
        Assert.notNull(uId, "用户id不能为空");
        TransMonitor transMonitor = transMonitorDao.findByMonitorTransAndCreateUser(transId, uId);
        transMonitor.setRunStatus(runStatus.toString());
        transMonitorDao.save(transMonitor);
    }

}
