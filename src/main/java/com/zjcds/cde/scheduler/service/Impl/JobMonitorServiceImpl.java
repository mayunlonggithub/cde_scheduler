package com.zjcds.cde.scheduler.service.Impl;

import com.zjcds.cde.scheduler.base.PageResult;
import com.zjcds.cde.scheduler.base.Paging;
import com.zjcds.cde.scheduler.dao.jpa.JobMonitorDao;
import com.zjcds.cde.scheduler.dao.jpa.JobRecordDao;
import com.zjcds.cde.scheduler.dao.jpa.view.JobMonitorViewDao;
import com.zjcds.cde.scheduler.domain.entity.JobMonitor;
import com.zjcds.cde.scheduler.domain.entity.JobRecord;
import com.zjcds.cde.scheduler.domain.entity.view.JobMonitorView;
import com.zjcds.cde.scheduler.service.JobMonitorService;
import com.zjcds.cde.scheduler.service.JobService;
import com.zjcds.cde.scheduler.utils.CommonUtils;
import com.zjcds.cde.scheduler.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import static java.sql.Date.valueOf;

/**
 * @author J on 20191111
 */
@Service
public class JobMonitorServiceImpl implements JobMonitorService {

    @Autowired
    private JobMonitorDao jobMonitorDao;
    @Autowired
    private JobMonitorViewDao jobMonitorViewDao;

    @Autowired
    private JobService jobService;

    @Autowired
    private JobRecordDao jobRecordDao;

    /**
     *
     * @param paging
     * @param queryString
     * @param orderBys
     * @param uId
     * @return
     * @Description 获取作业监控分页列表
     */
    @Override
    public PageResult<JobMonitorView> getList(Paging paging, List<String> queryString, List<String> orderBys, Integer uId) {
        Assert.notNull(uId,"未登录,请重新登录");
        queryString.add("createUser~Eq~"+uId);
        PageResult<JobMonitorView> jobMonitorPageResult = jobMonitorViewDao.findAll(paging, queryString, orderBys);
//        List<JobMonitor> jobMonitorList = jobMonitorPageResult.getContent();
//        jobName(jobMonitorList);
        return jobMonitorPageResult;
    }


    /**
     * 加载转换名称
     * @param jobMonitorList
     */
    public void jobName(List<JobMonitor> jobMonitorList){
        Map<Integer,String> map = jobService.jobNameMap();
        if(jobMonitorList.size()>0&&jobMonitorList!=null){
            for (JobMonitor j:jobMonitorList){
                j.setMonitorJobName(map.get(j.getMonitorJob()));
            }
        }
    }

    /**
     * @param uId 用户ID
     * @return Integer
     * @Title getAllMonitorJob
     * @Description 获取所有的监控作业
     */
    @Override
    public Integer getAllMonitorJob(Integer uId) {
        Assert.notNull(uId,"未登录,请重新登录");
        List<JobMonitor> jobMonitorList = jobMonitorDao.findByCreateUserAndMonitorStatus(uId,1);
        return jobMonitorList.size();
    }

    /**
     * @param uId 用户ID
     * @return Integer
     * @Title getAllSuccess
     * @Description 获取执行成功的数
     */
    @Override
    public Integer getAllSuccess(Integer uId) {
        Assert.notNull(uId,"未登录,请重新登录");
        List<JobMonitor> jobMonitorList = jobMonitorDao.findByCreateUserAndMonitorStatus(uId,1);
        Integer allSuccess = 0;
        for (JobMonitor jobMonitor : jobMonitorList) {
            allSuccess += jobMonitor.getMonitorSuccess();
        }
        return allSuccess;
    }

    /**
     * @param uId 用户ID
     * @return Integer
     * @Title getAllFail
     * @Description 获取执行失败的数
     */
    @Override
    public Integer getAllFail(Integer uId) {
        Assert.notNull(uId,"未登录,请重新登录");
        List<JobMonitor> jobMonitorList = jobMonitorDao.findByCreateUserAndMonitorStatus(uId,1);
        Integer allSuccess = 0;
        for (JobMonitor jobMonitor : jobMonitorList) {
            allSuccess += jobMonitor.getMonitorFail();
        }
        return allSuccess;
    }

    /**
     * @param uId 用户ID
     * @return Map<String   ,   Object>
     * @Title getTransLine
     * @Description 获取7天内作业的折线图
     */
    @Override
    public Map<String, Object> getJobLine(Integer uId,List<String> dateList) {
        Assert.notNull(uId,"未登录,请重新登录");
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        List<Integer> resultList = new ArrayList<Integer>();
        //获取当前用户所有执行记录
        List<JobRecord> jobRecordList = jobRecordDao.findByCreateUser(uId);
        //截取时间日期到日
        jobRecordList.stream().forEach(e ->e.setStartTime(valueOf(e.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())));
        //group by 根据时间日期
        Map<Date,Long> job = jobRecordList.stream().collect(Collectors.groupingBy(JobRecord :: getStartTime,Collectors.counting()));
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
            if(job.get(s)==null){
                sum=0;
            }else {
                sum=job.get(s).intValue();
            }
            resultList.add(i,sum);
            i += 1;
        }
        resultMap.put("name", "作业");
        resultMap.put("data", resultList);
        return resultMap;
    }

    /**
     * @param uId 用户ID
     * @param jobId  转换ID
     * @return void
     * @Title addMonitor
     * @Description 添加监控
     */
    @Override
    @Transactional
    public void addMonitor(Integer uId, Integer jobId, Date nextExecuteTime) {
        Assert.notNull(uId,"未登录,请重新登录");
        JobMonitor templateOne = jobMonitorDao.findByMonitorJobAndCreateUser(jobId,uId);
        if (null != templateOne) {
            templateOne.setMonitorStatus(1);
            templateOne.setRunStatus(0);
            templateOne.setLastExecuteTime(templateOne.getNextExecuteTime());
            templateOne.setNextExecuteTime(nextExecuteTime);
            jobMonitorDao.save(templateOne);
        } else {
            JobMonitor jobMonitor = new JobMonitor();
            jobMonitor.setMonitorJob(jobId);
            jobMonitor.setCreateUser(uId);
            jobMonitor.setMonitorSuccess(0);
            jobMonitor.setMonitorFail(0);
            jobMonitor.setMonitorStatus(1);
            jobMonitor.setRunStatus(0);
            jobMonitor.setNextExecuteTime(nextExecuteTime);
            jobMonitorDao.save(jobMonitor);
        }
    }

    /**
     * 更新作业状态
     * @param jobId
     * @param uId
     */
    @Async
    @Override
    @Transactional
    public void updateRunStatusJob(Integer jobId,Integer uId,Integer runStatus) {
        Assert.notNull(jobId, "作业id不能为空");
        Assert.notNull(uId, "用户id不能为空");
        JobMonitor jobMonitor = jobMonitorDao.findByMonitorJobAndCreateUser(jobId, uId);
        jobMonitor.setRunStatus(runStatus);
        jobMonitorDao.save(jobMonitor);
    }

}
