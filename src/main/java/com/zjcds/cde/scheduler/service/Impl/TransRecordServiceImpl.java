package com.zjcds.cde.scheduler.service.Impl;

import com.zjcds.cde.scheduler.base.PageResult;
import com.zjcds.cde.scheduler.base.Paging;
import com.zjcds.cde.scheduler.dao.jpa.TransRecordDao;
import com.zjcds.cde.scheduler.dao.jpa.view.TransMonitorViewDao;
import com.zjcds.cde.scheduler.dao.jpa.view.TransRecordGroupViewDao;
import com.zjcds.cde.scheduler.dao.jpa.view.TransRecordViewDao;
import com.zjcds.cde.scheduler.domain.dto.JobMonitorForm;
import com.zjcds.cde.scheduler.domain.dto.TransMonitorForm;
import com.zjcds.cde.scheduler.domain.entity.JobRecord;
import com.zjcds.cde.scheduler.domain.entity.TransRecord;
import com.zjcds.cde.scheduler.domain.entity.view.*;
import com.zjcds.cde.scheduler.service.TransRecordService;
import com.zjcds.cde.scheduler.service.TransService;
import com.zjcds.cde.scheduler.utils.Constant;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private TransRecordViewDao transRecordViewDao;

    @Autowired
    private TransService transService;
    @Autowired
    private TransRecordGroupViewDao transRecordGroupViewDao;
    @Autowired
    private TransMonitorViewDao transMonitorViewDao;


    /**
     * @Title getList
     * @Description 获取列表
     * @param uId 用户ID
     * @return
     * @return BootTablePage
     */
    @Override
    public PageResult<TransRecordView> getList(Paging paging, List<String> queryString, List<String> orderBys, Integer uId){
        Assert.notNull(uId,"未登录,请重新登录");
        queryString.add("createUser~Eq~"+uId);
        PageResult<TransRecordView> transRecordPageResult = transRecordViewDao.findAll(paging, queryString, orderBys);
//        List<TransRecord> transRecordList = transRecordPageResult.getContent();
//        transName(transRecordList);
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
        TransRecord transRecord = transRecordDao.findByRecordIdAndCreateUser(recordId,uId);
        Assert.notNull(transRecord,"日志不存在或已删除");
        String logFilePath = transRecord.getLogFilePath();
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
     * 当天转换运行统计
     * @param uId
     * @return
     */
    @Override
    public List<TransMonitorForm.TransMonitorStatis> getListToday(Integer uId){
        //取当天数据
        List<TransRecordGroupView> transRecordGroupViewList = transRecordGroupViewDao.findByCreateUser(uId);
        //取成功数据
        List<TransRecordGroupView> success = transRecordGroupViewList.stream().filter(e->e.getRecordStatus()==2).collect(Collectors.toList());
        //取失败数
        List<TransRecordGroupView> fail = transRecordGroupViewList.stream().filter(e->e.getRecordStatus()==3).collect(Collectors.toList());

        List<TransMonitorView> transMonitorList = transMonitorViewDao.findByCreateUser(uId);
        List<TransMonitorForm.TransMonitorStatis> statis = new ArrayList<>();

        for (TransMonitorView s:transMonitorList){
            TransMonitorForm.TransMonitorStatis t = new TransMonitorForm.TransMonitorStatis();
            //相同job的成功数据
            TransRecordGroupView transSuccess = success.stream().filter(e->e.getRecordTrans().equals(s.getMonitorTrans())).findAny().orElse(new TransRecordGroupView());
            //相同job的失败数据
            TransRecordGroupView transFail = fail.stream().filter(e->e.getRecordTrans().equals(s.getMonitorTrans())).findAny().orElse(new TransRecordGroupView());

            t.setMonitorTransName(s.getMonitorTransName());
            t.setRepositoryName(s.getRepositoryName());
            if(transSuccess.getRecordTrans()!=null){
                t.setMonitorSuccess(transSuccess.getNum());
            }else {
                t.setMonitorSuccess(0);
            }
            if(transFail.getRecordTrans()!=null){
                t.setMonitorFail(transFail.getNum());
            }else {
                t.setMonitorFail(0);
            }

            statis.add(t);
        };
        return statis;
    }

}
