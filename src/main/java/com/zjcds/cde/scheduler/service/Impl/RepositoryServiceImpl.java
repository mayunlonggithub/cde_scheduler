package com.zjcds.cde.scheduler.service.Impl;

import com.zjcds.cde.scheduler.base.BeanPropertyCopyUtils;
import com.zjcds.cde.scheduler.base.PageResult;
import com.zjcds.cde.scheduler.base.Paging;
import com.zjcds.cde.scheduler.dao.jpa.*;
import com.zjcds.cde.scheduler.domain.dto.RepositoryForm;
import com.zjcds.cde.scheduler.domain.dto.RepositoryTreeForm;
import com.zjcds.cde.scheduler.domain.entity.*;
import com.zjcds.cde.scheduler.service.InitializeService;
import com.zjcds.cde.scheduler.service.RepositoryService;
import com.zjcds.cde.scheduler.utils.RepositoryUtil;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author J on 20191112
 */
@Service
public class RepositoryServiceImpl implements RepositoryService {

    @Autowired
    private RepositoryDao repositoryDao;

    @Autowired
    private RepositoryTypeDao repositoryTypeDao;

    @Autowired
    private JobDao jobDao;

    @Autowired
    private TransDao transDao;

    @Autowired
    private CdmJobDao cdmJobDao;
    @Autowired
    private InitializeService initializeService;

    /**
     * @Title getRepositoryTreeList
     * @Description 获取数据库资源库的树形菜单
     * @param repositoryId
     * @return
     * @throws KettleException
     * @return List<RepositoryTree>
     */
    @Override
    public List<RepositoryTreeForm> getTreeList(Integer repositoryId,Integer uId) throws KettleException{
        Assert.notNull(uId,"未登录,请重新登录");
        KettleDatabaseRepository kettleDatabaseRepository = null;
        List<RepositoryTreeForm> allRepositoryTreeList = new ArrayList<>();
        if (RepositoryUtil.KettleDatabaseRepositoryCatch.containsKey(repositoryId)){
            kettleDatabaseRepository = RepositoryUtil.KettleDatabaseRepositoryCatch.get(repositoryId);
        }else {
            Repository kRepository = repositoryDao.findByRepositoryId(repositoryId);
            kettleDatabaseRepository = RepositoryUtil.connectionRepository(kRepository);
        }
        if (null != kettleDatabaseRepository){
            List<RepositoryTreeForm> repositoryTreeList = new ArrayList<>();
            allRepositoryTreeList = RepositoryUtil.getAllDirectoryTreeList(kettleDatabaseRepository, "/", repositoryTreeList);
        }
        return allRepositoryTreeList;
    }

    /**
     * @Title ckeck
     * @Description 判断是否可以连接上资源库
     * @param addRepository
     * @return
     * @throws KettleException
     * @return boolean
     */
    @Override
    public boolean check(RepositoryForm.AddRepository addRepository,Integer uId) throws KettleException{
        Assert.notNull(uId,"未登录,请重新登录");
        Repository repository = BeanPropertyCopyUtils.copy(addRepository,Repository.class);
        KettleDatabaseRepository kettleDatabaseRepository = RepositoryUtil.connectionRepository(repository);
        if (kettleDatabaseRepository != null){
            if (kettleDatabaseRepository.isConnected()){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }


    /**
     * @Title getList
     * @Description 获取列表带分页
     * @param uId 用户ID
     * @return
     * @return BootTablePage
     */
    @Override
    public PageResult<Repository> getList(Paging paging, List<String> queryString, List<String> orderBys, Integer uId){
        Assert.notNull(uId,"未登录,请重新登录");
        queryString.add("createUser~Eq~"+uId);
        queryString.add("delFlag~Eq~1");
        PageResult<Repository> repositoryList = repositoryDao.findAll(paging,queryString,orderBys);
        return repositoryList;
    }

    /**
     * @Title getRepositoryTypeList
     * @Description 获取资源库类别列表
     * @return
     * @return List<KRepositoryType>
     */
    @Override
    public List<RepositoryType> getRepositoryTypeList(){
        return repositoryTypeDao.findByIsShow(1);
    }

    /**
     * @Title getKRepository
     * @Description 获取资源库对象
     * @param repositoryId 资源库ID
     * @return
     * @return KRepository
     */
    @Override
    public Repository getRepository(Integer repositoryId,Integer uId){
        Assert.notNull(uId,"未登录,请重新登录");
        Assert.notNull(repositoryId,"要查询的资源库id不能为空");
        //如果根据主键没有获取到对象，返回null
        return repositoryDao.findByRepositoryId(repositoryId);
    }

    /**
     * @Title getAccess
     * @Description 获取资源库访问类型
     * @return
     * @return String[]
     */
    @Override
    public List<String> getAccess(){
        List<String> access = Arrays.asList(RepositoryUtil.getDataBaseAccess());
        return access;
    }

    /**
     * @Title insert
     * @Description 插入资源库
     * @param addRepository 资源库对象
     * @param uId 用户ID
     * @return void
     */
    @Override
    @Transactional
    public void insert(RepositoryForm.AddRepository addRepository, Integer uId) throws KettleException{
        Assert.notNull(uId,"未登录,请重新登录！");
        Repository repository = repositoryDao.findByDatabaseHostAndDatabaseNameAndDelFlagAndCreateUser(addRepository.getDatabaseHost(),addRepository.getDatabaseName(),1,uId);
        Assert.isNull(repository,"该资源库信息已存在，请重新输入");
        repository = BeanPropertyCopyUtils.copy(addRepository,Repository.class);
        repository.setCreateUser(uId);
        repository.setModifyUser(uId);
        repository.setDelFlag(1);
        repository = repositoryDao.save(repository);
//        saveTreeList(repository.getRepositoryId());
    }

    /**
     * @Title update
     * @Description 更新资源库
     * @param updateRepository 资源库对象
     * @param uId 用户ID
     * @return void
     */
    @Override
    @Transactional
    public void update(RepositoryForm.UpdateRepository updateRepository,Integer repositoryId, Integer uId){
        Assert.notNull(uId,"未登录,请重新登录");
        Assert.notNull(repositoryId,"要修改的资源库ID不能为空");
        Repository repository = repositoryDao.findByRepositoryIdAndDelFlag(repositoryId,1);
        Assert.notNull(repository,"要修改的资源库不存在或已删除");
        Repository rep = BeanPropertyCopyUtils.copy(updateRepository,Repository.class);
        rep.setModifyUser(uId);
        rep.setDelFlag(repository.getDelFlag());
        rep.setCreateUser(repository.getCreateUser());
        rep.setCreateTime(repository.getCreateTime());
        //只有不为null的字段才参与更新
        repositoryDao.save(rep);
    }

    /**
     * @Title delete
     * @Description 删除资源库
     * @param repositoryId 资源库ID
     * @return void
     */
    @Override
    @Transactional
    public void delete(Integer repositoryId,Integer uId){
        Assert.notNull(uId,"未登录,请重新登录");
        Assert.notNull(repositoryId,"要删除的资源库ID不能为空！");
        Repository repository = repositoryDao.findByRepositoryIdAndDelFlag(repositoryId,1);
        Assert.notNull(repository,"要修改的资源库不存在或已删除");
        repository.setDelFlag(0);
        repositoryDao.save(repository);
    }

    /**
     * 获取资源库的所有作业和转换任务进行保存
     * @param repositoryId
     * @throws KettleException
     */
    @Override
//    @Transactional
    public void saveTreeList(Integer repositoryId,Integer uId) throws KettleException{
        Repository kRepository = repositoryDao.findByRepositoryId(repositoryId);
        kRepository.setModifyTime(new Date());
        repositoryDao.save(kRepository);
        KettleDatabaseRepository kettleDatabaseRepository  = initializeService.init(kRepository);
        if(uId!=2){    //判断非管控用户
            List<RepositoryTreeForm> allRepositoryTreeList = new ArrayList<>();
            if (RepositoryUtil.KettleDatabaseRepositoryCatch.containsKey(repositoryId)){
                kettleDatabaseRepository = RepositoryUtil.KettleDatabaseRepositoryCatch.get(repositoryId);
            }else {

                kettleDatabaseRepository = RepositoryUtil.connectionRepository(kRepository);
            }
            if (null != kettleDatabaseRepository){
                List<RepositoryTreeForm> repositoryTreeList = new ArrayList<>();
                allRepositoryTreeList = RepositoryUtil.getAllDirectoryTreeList(kettleDatabaseRepository, "/", repositoryTreeList);
            }
            for (RepositoryTreeForm r : allRepositoryTreeList){
                if(r.getId().contains("@")){
                    String[] str = r.getId().split("@");
                    r.setRId(Integer.parseInt(str[1]));
                    r.setId(str[1]);
                }else {
                    r.setRId(Integer.parseInt(r.getId()));
                }
                r.setRepositoryId(repositoryId);
            }
            //资源库所有信息，作业，转换，路径
            List<RepositoryTree> repositoryTrees = BeanPropertyCopyUtils.copy(allRepositoryTreeList,RepositoryTree.class);
            //过滤出路径
            List<RepositoryTree> path = repositoryTrees.stream().filter(e->e.getType() == null).collect(Collectors.toList());
            //过滤出作业
            List<RepositoryTree> repositoryJob = repositoryTrees.stream().filter(e->e.getType() !=null && e.getType().equals("job")).collect(Collectors.toList());
            //取出作业路径信息
            repositoryJob = stream(path,repositoryJob);
            //过滤出转换
            List<RepositoryTree> repositoryTrans = repositoryTrees.stream().filter(e->e.getType() !=null && e.getType().equals("transformation")).collect(Collectors.toList());
            //取出转换路径信息
            repositoryTrans = stream(path,repositoryTrans);
//        repositoryTreeDao.deleteByRepositoryId(repositoryId);
//        repositoryTreeDao.save(repositoryTrees);
            //保存作业信息
            List<Job> jobList = addJob(repositoryJob,repositoryId,uId);
            jobDao.saveAll(jobList);
            //保存转换信息
            List<Trans> transList = addTrans(repositoryTrans,repositoryId,uId);
            transDao.saveAll(transList);
        }else {  //管控用户的任务从初始化表获取任务信息
            List<CdmJob> cdmJobList = cdmJobDao.findAll();
            List<RepositoryTree> RepositoryTree = new ArrayList<>();
            if(cdmJobList.size()>0){
                for (CdmJob c :cdmJobList){
                    RepositoryTree repositoryTree = new RepositoryTree();
                    repositoryTree.setText(c.getJobName());
                    repositoryTree.setRepositoryId(repositoryId);
                    if(c.getJobPath()==null){
                        repositoryTree.setPath("/");
                    }else {
                        repositoryTree.setPath(c.getJobPath());
                    }
                    RepositoryTree.add(repositoryTree);
                }
            }
            List<Job> jobList = addJob(RepositoryTree,repositoryId,uId);
            jobDao.saveAll(jobList);
        }


    }

    //整合新增的作业信息
    private List<Job> addJob(List<RepositoryTree> repositoryJob,Integer repositoryId,Integer uId){
        List<Job> jobs = jobDao.findByJobRepositoryId(repositoryId);
        List<Job> jobList = new ArrayList<>();
        for (RepositoryTree j:repositoryJob){
            Job jobList1 = jobs.stream().filter(e->e.getJobName().equals(j.getText())&&e.getJobPath().equals(j.getPath())&&e.getJobRepositoryId()==j.getRepositoryId()&&e.getDelFlag()==1).findFirst().orElse(new Job());
            //判断作业是否已经存在
            if(jobList1.getJobId()==null){
                Job job = new Job();
                job.setJobName(j.getText());
                job.setJobType(1);
                job.setJobPath(j.getPath());
                job.setJobRepositoryId(j.getRepositoryId());
                job.setJobLogLevel("basic");
                job.setDelFlag(1);
                job.setCreateUser(uId);
                job.setModifyUser(uId);
                jobList.add(job);
            }
        }
        return jobList;
    }

    //整合新增的转换信息
    private List<Trans> addTrans(List<RepositoryTree> repositoryTrans,Integer repositoryId,Integer uId){
        List<Trans> trans = transDao.findByTransRepositoryId(repositoryId);
        List<Trans> transList = new ArrayList<>();
        for (RepositoryTree t:repositoryTrans){
            Trans trans1 = trans.stream().filter(e->e.getTransName().equals(t.getText())&&e.getTransPath().equals(t.getPath())&&e.getTransRepositoryId()==t.getRepositoryId()&&e.getDelFlag()==1).findFirst().orElse(new Trans());
            //判断转换是否已经存在
            if(trans1.getTransId()==null){
                Trans tran = new Trans();
                tran.setTransName(t.getText());
                tran.setTransType(1);
                tran.setTransPath(t.getPath());
                tran.setTransRepositoryId(t.getRepositoryId());
                tran.setTransLogLevel("basic");
                tran.setDelFlag(1);
                tran.setCreateUser(uId);
                tran.setModifyUser(uId);
                transList.add(tran);
            }
        }
        return transList;
    }

    //作业转换加载路径信息
    private List<RepositoryTree> stream(List<RepositoryTree> path,List<RepositoryTree> repositoryTrees){
        for(RepositoryTree t : repositoryTrees){
            RepositoryTree pathTrans = path.stream().filter(e->e.getId().toString().equals(t.getParent())).findFirst().orElse(new RepositoryTree());
            if(pathTrans!=null){
                //根目录为null
                if(pathTrans.getPath()==null){
                    t.setPath("/");
                }else {
                    t.setPath(pathTrans.getPath());
                }
            }
        }
        return repositoryTrees;
    }

}
