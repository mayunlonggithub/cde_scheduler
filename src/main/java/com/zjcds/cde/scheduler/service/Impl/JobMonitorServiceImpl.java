package com.zjcds.cde.scheduler.service.Impl;

import com.zjcds.cde.scheduler.dao.jpa.JobMonitorDao;
import com.zjcds.cde.scheduler.domain.entity.JobMonitor;
import com.zjcds.cde.scheduler.service.JobMonitorService;
import com.zjcds.cde.scheduler.utils.CommonUtils;
import com.zjcds.cde.scheduler.utils.Constant;
import com.zjcds.common.base.domain.page.Paging;
import com.zjcds.common.jpa.PageResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author J on 20191111
 */
@Service
public class JobMonitorServiceImpl implements JobMonitorService {

    @Autowired
    private JobMonitorDao jobMonitorDao;


    /**
     *
     * @param paging
     * @param queryString
     * @param orderBys
     * @param monitorStatus
     * @param categoryId
     * @param jobName
     * @param uId
     * @return
     * @Description 获取作业监控分页列表
     */
    public PageResult<JobMonitor> getList(Paging paging, List<String> queryString, List<String> orderBys, Integer monitorStatus, Integer categoryId, String jobName, Integer uId) {
        queryString.add("createUser~Eq~"+uId);
        queryString.add("monitorStatus~Eq~"+monitorStatus);
        if(StringUtils.isNotEmpty(jobName)){
            queryString.add("jobName~Eq~"+jobName);
        }
        if (categoryId != null) {
            queryString.add("categoryId~Eq~"+categoryId);
        }
        PageResult<JobMonitor> jobMonitorPageResult = jobMonitorDao.findAll(paging, queryString, orderBys);

        return jobMonitorPageResult;
    }

//    /**
//     * @param uId   用户ID
//     * @return BootTablePage
//     * @Title getList
//     * @Description 获取作业监控不分页列表
//     */
//    public PageResult<JobMonitor> getList(Integer uId) {
//
//        List<JobMonitor> jobMonitorList = jobMonitorDao.findByCreateUserAndMonitorStatus(uId,1);
////        Collections.sort(jobMonitorList);
//        List<JobMonitor> newKJobMonitorList = new ArrayList<JobMonitor>();
//        if (jobMonitorList.size() >= 5) {
//            newKJobMonitorList = jobMonitorList.subList(0, 5);
//        } else {
//            newKJobMonitorList = jobMonitorList;
//        }
//        BootTablePage bootTablePage = new BootTablePage();
//        bootTablePage.setRows(newKJobMonitorList);
//        bootTablePage.setTotal(5);
//        return bootTablePage;
//    }

    /**
     * @param uId 用户ID
     * @return Integer
     * @Title getAllMonitorJob
     * @Description 获取所有的监控作业
     */
    public Integer getAllMonitorJob(Integer uId) {
        List<JobMonitor> jobMonitorList = jobMonitorDao.findByCreateUserAndMonitorStatus(uId,1);
        return jobMonitorList.size();
    }

    /**
     * @param uId 用户ID
     * @return Integer
     * @Title getAllSuccess
     * @Description 获取执行成功的数
     */
    public Integer getAllSuccess(Integer uId) {
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
    public Integer getAllFail(Integer uId) {
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
    public Map<String, Object> getJobLine(Integer uId) {

        List<JobMonitor> jobMonitorList = jobMonitorDao.findByCreateUser(uId);
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        List<Integer> resultList = new ArrayList<Integer>();
        for (int i = 0; i < 7; i++) {
            resultList.add(i, 0);
        }
        if (jobMonitorList != null && !jobMonitorList.isEmpty()) {
            for (JobMonitor jobMonitor : jobMonitorList) {
                String runStatus = jobMonitor.getRunStatus();
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
        resultMap.put("name", "作业");
        resultMap.put("data", resultList);
        return resultMap;
    }

    /**
     * @param userId 用户ID
     * @param jobId  转换ID
     * @return void
     * @Title addMonitor
     * @Description 添加监控
     */
    @Override
    @Transactional
    public void addMonitor(Integer userId, Integer jobId, Date nextExecuteTime) {
        JobMonitor templateOne = jobMonitorDao.findByMonitorJob(jobId);
        if (null != templateOne) {
            templateOne.setMonitorStatus(1);
            StringBuilder runStatusBuilder = new StringBuilder();
            runStatusBuilder.append(templateOne.getRunStatus())
                    .append(",").append(new Date().getTime()).append(Constant.RUNSTATUS_SEPARATE);
            templateOne.setRunStatus(runStatusBuilder.toString());
            templateOne.setNextExecuteTime(nextExecuteTime);
            jobMonitorDao.save(templateOne);
        } else {
            JobMonitor jobMonitor = new JobMonitor();
            jobMonitor.setMonitorJob(jobId);
            jobMonitor.setCreateUser(userId);
            jobMonitor.setMonitorSuccess(0);
            jobMonitor.setMonitorFail(0);
            StringBuilder runStatusBuilder = new StringBuilder();
            runStatusBuilder.append(new Date().getTime()).append(Constant.RUNSTATUS_SEPARATE);
            jobMonitor.setRunStatus(runStatusBuilder.toString());
            jobMonitor.setMonitorStatus(1);
            jobMonitor.setNextExecuteTime(nextExecuteTime);
            jobMonitorDao.save(jobMonitor);
        }
    }

}
