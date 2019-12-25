package com.zjcds.cde.scheduler.service.Impl;

import com.zjcds.cde.scheduler.base.PageResult;
import com.zjcds.cde.scheduler.base.Paging;
import com.zjcds.cde.scheduler.dao.jpa.TransMonitorDao;
import com.zjcds.cde.scheduler.dao.jpa.TransRecordDao;
import com.zjcds.cde.scheduler.dao.jpa.view.TransMonitorViewDao;
import com.zjcds.cde.scheduler.domain.entity.TransMonitor;
import com.zjcds.cde.scheduler.domain.entity.TransRecord;
import com.zjcds.cde.scheduler.domain.entity.view.TransMonitorView;
import com.zjcds.cde.scheduler.service.TransMonitorService;
import com.zjcds.cde.scheduler.service.TransService;
import com.zjcds.cde.scheduler.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @Autowired
    private TransRecordDao transRecordDao;

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
    public Map<String, Object> getTransLine(Integer uId,List<String> dateList) {
        Assert.notNull(uId,"未登录,请重新登录");
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        List<Integer> resultList = new ArrayList<Integer>();
        //获取当前用户所有执行记录
        List<TransRecord> transRecordList = transRecordDao.findByCreateUser(uId);
        //截取时间日期到日
        transRecordList.stream().forEach(e ->e.setStartTime(DateUtils.getYmd(e.getStartTime())));
        //group by 根据时间日期
        Map<Date,Long> trans = transRecordList.stream().collect(Collectors.groupingBy(TransRecord :: getStartTime,Collectors.counting()));
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<Date> dates = new ArrayList<>();
        dateList.forEach(e->{
            try {
                dates.add(format.parse(e));
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        });
        Integer i=0;
        //根据时间循环取出执行数量
        for(Date s : dates){
            Integer sum ;
            if(trans.get(s)==null){
                sum=0;
            }else {
                sum=trans.get(s).intValue();
            }
            resultList.add(i,sum);
            i += 1;
        }
        resultMap.put("name", "转换");
        resultMap.put("data", resultList);
        return resultMap;
    }

    /**
     * @param uId 用户ID
     * @return Map<String   ,   Object>
     * @Title getTransLine
     * @Description 获取7天内转换的成功柱状图
     */
    @Override
    public Map<String, Object> getTransSuccess(Integer uId,List<String> dateList) {
        Assert.notNull(uId,"未登录,请重新登录");
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        List<Integer> resultList = new ArrayList<Integer>();
        //获取当前用户所有执行记录
        List<TransRecord> transRecordList = transRecordDao.findByCreateUserAndRecordStatus(uId,2);
        //截取时间日期到日

        transRecordList.stream().forEach(e ->e.setStartTime(DateUtils.getYmd(e.getStartTime())));
        //group by 根据时间日期
        Map<Date,Long> trans = transRecordList.stream().collect(Collectors.groupingBy(TransRecord :: getStartTime,Collectors.counting()));
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<Date> dates = new ArrayList<>();
        dateList.forEach(e->{
            try {
                dates.add(format.parse(e));
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        });
        Integer i=0;
        //根据时间循环取出执行数量
        for(Date s : dates){
            Integer sum ;
            if(trans.get(s)==null){
                sum=0;
            }else {
                sum=trans.get(s).intValue();
            }
            resultList.add(i,sum);
            i += 1;
        }
        resultMap.put("name", "转换成功");
        resultMap.put("data", resultList);
        return resultMap;
    }

    /**
     * @param uId 用户ID
     * @return Map<String   ,   Object>
     * @Title getTransLine
     * @Description 获取7天内转换的失败柱状图
     */
    @Override
    public Map<String, Object> getTransFail(Integer uId,List<String> dateList) {
        Assert.notNull(uId,"未登录,请重新登录");
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        List<Integer> resultList = new ArrayList<Integer>();
        //获取当前用户所有执行记录
        List<TransRecord> transRecordList = transRecordDao.findByCreateUserAndRecordStatus(uId,3);
        //截取时间日期到日
        transRecordList.stream().forEach(e ->e.setStartTime(DateUtils.getYmd(e.getStartTime())));
        //group by 根据时间日期
        Map<Date,Long> trans = transRecordList.stream().collect(Collectors.groupingBy(TransRecord :: getStartTime,Collectors.counting()));
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<Date> dates = new ArrayList<>();
        dateList.forEach(e->{
            try {
                dates.add(format.parse(e));
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        });
        Integer i=0;
        //根据时间循环取出执行数量
        for(Date s : dates){
            Integer sum ;
            if(trans.get(s)==null){
                sum=0;
            }else {
                sum=trans.get(s).intValue();
            }
            resultList.add(i,sum);
            i += 1;
        }
        resultMap.put("name", "转换失败");
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
            templateOne.setRunStatus(0);
            templateOne.setNextExecuteTime(nextExecuteTime);
            transMonitorDao.save(templateOne);
        } else {
            TransMonitor kTransMonitor = new TransMonitor();
            kTransMonitor.setMonitorTrans(transId);
            kTransMonitor.setCreateUser(userId);
            kTransMonitor.setMonitorSuccess(0);
            kTransMonitor.setMonitorFail(0);
            kTransMonitor.setRunStatus(0);
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
        transMonitor.setRunStatus(runStatus);
        transMonitorDao.save(transMonitor);
    }

}
