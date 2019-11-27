package com.zjcds.cde.scheduler.service;

import com.zjcds.cde.scheduler.base.PageResult;
import com.zjcds.cde.scheduler.base.Paging;
import com.zjcds.cde.scheduler.domain.dto.RepositoryForm;
import com.zjcds.cde.scheduler.domain.dto.RepositoryTreeForm;
import com.zjcds.cde.scheduler.domain.entity.Repository;
import com.zjcds.cde.scheduler.domain.entity.RepositoryType;
import org.pentaho.di.core.exception.KettleException;

import java.util.List;

/**
 * @author J on 20191112
 */
public interface RepositoryService {

    /**
     * 获取数据库资源库的树形菜单
     * @param repositoryId
     * @return
     * @throws KettleException
     */
    public List<RepositoryTreeForm> getTreeList(Integer repositoryId, Integer uId) throws KettleException;

    /**
     * 判断是否可以连接上资源库
     * @param addRepository
     * @return
     * @throws KettleException
     */
    public boolean check(RepositoryForm.AddRepository addRepository,Integer uId) throws KettleException;


    /**
     * @Title getList
     * @Description 获取列表带分页
     * @param uId 用户ID
     * @return
     * @return BootTablePage
     */
    public PageResult<Repository> getList(Paging paging, List<String> queryString, List<String> orderBys, Integer uId);

    /**
     * @Title getRepositoryTypeList
     * @Description 获取资源库类别列表
     * @return
     * @return List<KRepositoryType>
     */
    public List<RepositoryType> getRepositoryTypeList();

    /**
     * @Title getKRepository
     * @Description 获取资源库对象
     * @param repositoryId 资源库ID
     * @return
     * @return KRepository
     */
    public Repository getRepository(Integer repositoryId,Integer uId);

    /**
     * @Title getAccess
     * @Description 获取资源库访问类型
     * @return
     * @return String[]
     */
    public List<String> getAccess();

    /**
     * @Title insert
     * @Description 插入资源库
     * @param addRepository 资源库对象
     * @param uId 用户ID
     * @return void
     */
    public void insert(RepositoryForm.AddRepository addRepository, Integer uId) throws KettleException;

    /**
     * @Title update
     * @Description 更新资源库
     * @param updateRepository 资源库对象
     * @param uId 用户ID
     * @return void
     */
    public void update(RepositoryForm.UpdateRepository updateRepository,Integer repositoryId, Integer uId);

    /**
     * @Title delete
     * @Description 删除资源库
     * @param repositoryId 资源库ID
     * @return void
     */
    public void delete(Integer repositoryId,Integer uId);

    /**
     * 获取资源库的所有作业和转换任务进行保存
     * @param repositoryId
     * @throws KettleException
     */
    public void saveTreeList(Integer repositoryId,Integer uId) throws KettleException;
}
