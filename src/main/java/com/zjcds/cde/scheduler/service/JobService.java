package com.zjcds.cde.scheduler.service;

import com.zjcds.cde.scheduler.domain.dto.JobForm;
import com.zjcds.cde.scheduler.domain.entity.Job;
import com.zjcds.cde.scheduler.domain.entity.Repository;
import com.zjcds.common.base.domain.page.Paging;
import com.zjcds.common.jpa.PageResult;
import org.pentaho.di.core.exception.KettleException;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author J on 20191112
 */
public interface JobService {

    /**
     * @param uId   用户ID
     * @return BootTablePage
     * @Title getList
     * @Description 获取列表
     */
    public PageResult<Job> getList(Paging paging, List<String> queryString, List<String> orderBys, Integer uId);

    /**
     * @param jobId 作业ID
     * @return void
     * @Title delete
     * @Description 删除作业
     */
    public void delete(Integer jobId);

    /**
     * @param repositoryId 资源库ID
     * @param jobPath      作业路径
     * @param uId          用户ID
     * @return boolean
     * @Title check
     * @Description 检查当前作业是否可以插入到数据库
     */
    public boolean check(Integer repositoryId, String jobPath, Integer uId);


    /**
     * @param addJob          作业信息
     * @param uId           用户ID
     * @return void
     * @throws SQLException
     * @Title insert
     * @Description 插入作业到数据库
     */
    public void insert(JobForm.AddJob addJob, Integer uId);

    /**
     * @param jobId 作业ID
     * @return Job
     * @Title getJob
     * @Description 获取作业信息
     */
    public Job getJob(Integer jobId);

    /**
     * @param updateJob          作业对象
     * @param jobId           用户ID
     * @return void
     * @Title update
     * @Description 更新作业信息
     */
    public void update(JobForm.UpdateJob updateJob, Integer jobId);

    /**
     * @param jobId 作业ID
     * @return void
     * @Title start
     * @Description 启动作业
     */
    public void start(Integer jobId,Integer uId,Map<String,String> param)throws KettleException;

    /**
     * 手动执行
     * @param repository 资源库连接信息
     * @param jobId 作业id
     * @param jobName 作业名称
     * @param jobPath 作业路径
     * @param userId 用户id
     * @param logLevel 日志等级
     * @param logFilePath 日志路径
     * @param executeTime 执行时间
     * @param nexExecuteTime 下次执行时间
     * @param param 参数map
     * @throws KettleException
     */
    public void manualRunRepositoryJob(Repository repository, String jobId, String jobName, String jobPath, String userId, String logLevel, String logFilePath, Date executeTime, Date nexExecuteTime, Map<String,String> param) throws KettleException;

}