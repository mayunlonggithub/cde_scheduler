package com.zjcds.cde.scheduler.service.Impl;

import com.zjcds.cde.scheduler.base.PageResult;
import com.zjcds.cde.scheduler.base.Paging;
import com.zjcds.cde.scheduler.common.ErrorEnum;
import com.zjcds.cde.scheduler.dao.jpa.JobMonitorDao;
import com.zjcds.cde.scheduler.dao.jpa.JobRecordDao;
import com.zjcds.cde.scheduler.dao.jpa.view.JobMonitorViewDao;
import com.zjcds.cde.scheduler.dao.jpa.view.JobRecordGroupViewDao;
import com.zjcds.cde.scheduler.dao.jpa.view.JobRecordViewDao;
import com.zjcds.cde.scheduler.domain.dto.JobMonitorForm;
import com.zjcds.cde.scheduler.domain.entity.JobMonitor;
import com.zjcds.cde.scheduler.domain.entity.JobRecord;
import com.zjcds.cde.scheduler.domain.entity.view.JobMonitorView;
import com.zjcds.cde.scheduler.domain.entity.view.JobRecordGroupView;
import com.zjcds.cde.scheduler.domain.entity.view.JobRecordView;
import com.zjcds.cde.scheduler.service.JobRecordService;
import com.zjcds.cde.scheduler.service.JobService;
import com.zjcds.cde.scheduler.utils.Constant;
import com.zjcds.cde.scheduler.utils.DateUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
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
    @Lazy
    private JobService jobService;
    @Autowired
    private JobMonitorViewDao jobMonitorViewDao;

    @Autowired
    private JobRecordGroupViewDao jobRecordGroupViewDao;

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
        JobRecord kJobRecord = jobRecordDao.findByRecordIdAndCreateUserAndDelFlag(recordId,uId,1);
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
        JobRecord kJobRecord = jobRecordDao.findByRecordIdAndCreateUserAndDelFlag(recordId,uId,1);
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
        //取当天数据
        List<JobRecordGroupView> jobRecordGroupViewList = jobRecordGroupViewDao.findByCreateUser(uId);
        //取成功数据
        List<JobRecordGroupView> success = jobRecordGroupViewList.stream().filter(e->e.getRecordStatus()==2).collect(Collectors.toList());
        //取失败数
        List<JobRecordGroupView> fail = jobRecordGroupViewList.stream().filter(e->e.getRecordStatus()==3).collect(Collectors.toList());

        List<JobMonitorView> jobMonitorList = jobMonitorViewDao.findByCreateUser(uId);
        List<JobMonitorForm.JobMonitorStatis> statis = new ArrayList<>();

        for (JobMonitorView s:jobMonitorList){
            JobMonitorForm.JobMonitorStatis j = new JobMonitorForm.JobMonitorStatis();
            //相同job的成功数据
            JobRecordGroupView jobSuccess = success.stream().filter(e->e.getRecordJob().equals(s.getMonitorJob())).findAny().orElse(new JobRecordGroupView());
            //相同job的失败数据
            JobRecordGroupView jobFail = fail.stream().filter(e->e.getRecordJob().equals(s.getMonitorJob())).findAny().orElse(new JobRecordGroupView());

            j.setMonitorJobName(s.getMonitorJobName());
            j.setRepositoryName(s.getRepositoryName());
            if(jobSuccess.getRecordJob()!=null){
                j.setMonitorSuccess(jobSuccess.getNum());
            }else {
                j.setMonitorSuccess(0);
            }
            if(jobFail.getRecordJob()!=null){
                j.setMonitorFail(jobFail.getNum());
            }else {
                j.setMonitorFail(0);
            }
            statis.add(j);
        };
        return statis;
    }
}
