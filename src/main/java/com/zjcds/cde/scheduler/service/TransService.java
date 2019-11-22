package com.zjcds.cde.scheduler.service;

import com.zjcds.cde.scheduler.base.PageResult;
import com.zjcds.cde.scheduler.base.Paging;
import com.zjcds.cde.scheduler.domain.dto.TransForm;
import com.zjcds.cde.scheduler.domain.entity.Repository;
import com.zjcds.cde.scheduler.domain.entity.Trans;
import org.pentaho.di.core.exception.KettleException;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author J on 20191111
 */
public interface TransService {

    /**
     * @param uId   用户ID
     * @return BootTablePage
     * @Title getList
     * @Description 获取列表
     */
    public PageResult<Trans> getList(Paging paging, List<String> queryString, List<String> orderBys, Integer uId);


    public List<Trans> getList(Integer uId);


    /**
     * @param transId 转换ID
     * @return void
     * @Title delete
     * @Description 删除转换
     */
    public void delete(Integer transId, Integer uId);

    /**
     * @param repositoryId 资源库ID
     * @param transPath      转换路径
     * @param uId          用户ID
     * @return boolean
     * @Title check
     * @Description 检查当前转换是否可以插入到数据库
     */
    public boolean check(Integer repositoryId, String transPath, Integer uId);


    /**
     * @param addTrans          转换信息
     * @param uId           用户ID
     * @return void
     * @throws SQLException
     * @Title insert
     * @Description 插入转换到数据库
     */
    public void insert(TransForm.AddTrans addTrans, Integer uId);

    /**
     * @param transId 转换ID
     * @return Trans
     * @Title getTrans
     * @Description 获取转换信息
     */
    public Trans getTrans(Integer transId, Integer uId);

    /**
     * @param updateTrans          转换对象
     * @param transId           用户ID
     * @return void
     * @Title update
     * @Description 更新转换信息
     */
    public void update(TransForm.UpdateTrans updateTrans, Integer transId, Integer uId);


    /**
     * @param transId 转换ID
     * @return void
     * @Title start
     * @Description 启动转换
     */
    public void start(Integer transId,Integer uId,Map<String,String> param)throws KettleException;


    /**
     * 手动执行
     * @param repository 资源库连接信息
     * @param transId 转换id
     * @param transName 转换名称
     * @param transPath 转换路径
     * @param userId 用户id
     * @param logLevel 日志等级
     * @param logFilePath 日志路径
     * @param executeTime 执行时间
     * @param nexExecuteTime 下次执行时间
     * @param param 参数map
     * @throws KettleException
     */
    public void manualRunRepositoryTrans(Repository repository, String transId, String transName, String transPath, String userId, String logLevel, String logFilePath, Date executeTime, Date nexExecuteTime, Map<String,String> param) throws KettleException;


    /**
     * 查询所有转换名称Map
     * @return
     */
    public Map<Integer,String> transNameMap();

}
