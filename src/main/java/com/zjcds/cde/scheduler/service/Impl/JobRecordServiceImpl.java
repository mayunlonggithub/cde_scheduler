package com.zjcds.cde.scheduler.service.Impl;

import com.zjcds.cde.scheduler.base.PageResult;
import com.zjcds.cde.scheduler.base.Paging;
import com.zjcds.cde.scheduler.common.ErrorEnum;
import com.zjcds.cde.scheduler.dao.jpa.JobMonitorDao;
import com.zjcds.cde.scheduler.dao.jpa.JobRecordDao;
import com.zjcds.cde.scheduler.dao.jpa.view.JobMonitorViewDao;
import com.zjcds.cde.scheduler.dao.jpa.view.JobRecordViewDao;
import com.zjcds.cde.scheduler.domain.dto.JobMonitorForm;
import com.zjcds.cde.scheduler.domain.entity.JobMonitor;
import com.zjcds.cde.scheduler.domain.entity.JobRecord;
import com.zjcds.cde.scheduler.domain.entity.view.JobMonitorView;
import com.zjcds.cde.scheduler.domain.entity.view.JobRecordView;
import com.zjcds.cde.scheduler.service.JobRecordService;
import com.zjcds.cde.scheduler.service.JobService;
import com.zjcds.cde.scheduler.utils.Constant;
import com.zjcds.cde.scheduler.utils.DateUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author J on 20191112
 */
@Service
public class JobRecordServiceImpl implements JobRecordService {

    @Autowired
    private JobRecordDao jobRecordDao;
    @Autowired
    private JobRecordViewDao jobRecordViewDao;

    @Autowired
    private JobService jobService;
    @Autowired
    private JobMonitorViewDao jobMonitorViewDao;

    @Value("${cde.file.download}")
    private String cdeFileDownload;

    /**
     * @Title getList
     * @Description 获取带分页的列表
     * @param uId 用户ID
     * @return
     * @return BootTablePage
     */
    @Override
    public PageResult<JobRecordView> getList(Paging paging, List<String> queryString, List<String> orderBys, Integer uId){
        Assert.notNull(uId,"未登录,请重新登录");
        queryString.add("createUser~Eq~"+uId);
        PageResult<JobRecordView> jobRecordPageResult = jobRecordViewDao.findAll(paging, queryString, orderBys);
//        List<JobRecord> jobRecordList = jobRecordPageResult.getContent();
//        jobName(jobRecordList);
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
     * @Description 作业日志内容
     * @param recordId 转换记录ID
     * @return
     * @throws IOException
     * @return String
     */
    @Override
    public String getLogContent(Integer recordId, Integer uId) throws IOException {
        Assert.notNull(uId,"未登录,请重新登录");
        JobRecord kJobRecord = jobRecordDao.findByRecordIdAndCreateUser(recordId,uId);
        Assert.notNull(kJobRecord,"日志不存在或已删除");
        String logFilePath = kJobRecord.getLogFilePath();

        File file = new File(logFilePath);
        FileReader reader = new FileReader(file);
        BufferedReader br = new BufferedReader(reader);
        String content = "";
        while (br.ready()) {
            content += br.readLine()+"</br>";
        }
        return content;
    }

    /**
     * 日志文件下载
     * @param recordId
     * @param uId
     * @param
     */

    @Override
    public  void getLogDownload(Integer recordId,Integer uId, HttpServletResponse response) throws Exception {
        Assert.notNull(uId,"未登录,请重新登录");
        JobRecord kJobRecord = jobRecordDao.findByRecordIdAndCreateUser(recordId,uId);
        Assert.notNull(kJobRecord,"日志不存在或已删除");
        String logFilePath = kJobRecord.getLogFilePath();
        String fileName = logFilePath.split("/")[logFilePath.split("/").length-1];
        File file = new File(logFilePath);
        InputStream inputStream = new FileInputStream(file);
        // 配置文件下载
        response.setContentType("application/force-download");
        // 下载文件能正常显示中文
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        byte[] buffer = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        bis = new BufferedInputStream(inputStream);
        os = response.getOutputStream();
        int i = bis.read(buffer);
        while (i != -1) {
            os.write(buffer, 0, i);
            i = bis.read(buffer);
        }
        System.out.println("Download the file successfully!");

    }

    /**
     * 当天作业运行统计
     * @param uId
     * @return
     */
    @Override
    public List<JobMonitorForm.JobMonitorStatis> getListToday(Integer uId){
        Date startTime = DateUtils.getStartTime(new Date());
        Date endTime = DateUtils.getEndTime(new Date());
        //取当天数据
        List<JobRecordView> jobRecords = jobRecordViewDao.findByStartTime(uId,startTime,endTime);

        //取成功数据
        Map<String,Long> mapSuccess = jobRecords.stream().filter(e->e.getRecordStatus()==2).collect(Collectors.groupingBy(JobRecordView::getRecordJobName,Collectors.counting()));
        //取失败数
        Map<String,Long> mapFail = jobRecords.stream().filter(e->e.getRecordStatus()==3).collect(Collectors.groupingBy(JobRecordView::getRecordJobName,Collectors.counting()));

        List<JobMonitorView> jobMonitorList = jobMonitorViewDao.findByCreateUser(uId);
        Map<String,Long> jobName = jobMonitorList.stream().collect(Collectors.groupingBy(JobMonitorView::getMonitorJobName,Collectors.counting()));
        List<JobMonitorForm.JobMonitorStatis> statis = new ArrayList<>();
        for (String s:jobName.keySet()){
            JobMonitorForm.JobMonitorStatis j = new JobMonitorForm.JobMonitorStatis();
            j.setMonitorJobName(s);
//            Integer sumSuccess = jobRecords.stream().filter(e->e.getRecordJobName().equals(s)&&e.getRecordJob()==1);

            Integer sumFail = 0;

//            j.setMonitorSuccess(sumSuccess);
            j.setMonitorFail(sumFail);
            statis.add(j);
        };
        return statis;
    }
}
