package com.zjcds.cde.scheduler.object.db;

import com.zjcds.cde.scheduler.domain.dto.exchange.MetaDatasourceForm;
import com.zjcds.cde.scheduler.domain.dto.exchange.RepositoryTreeForm;
import com.zjcds.cde.scheduler.domain.enums.DbAccessTypeCodeEnum;
import com.zjcds.cde.scheduler.domain.enums.DbTypeCodeEnum;
import com.zjcds.cde.scheduler.utils.Constant;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.RepositoryDirectory;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.RepositoryElementMetaInterface;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryMeta;

import java.util.*;

/**
 * @author J on 2019/12/16
 */
public class Repository {

    public static Map<Integer, KettleDatabaseRepository> KettleDatabaseRepositoryCatch
            = new HashMap<Integer, KettleDatabaseRepository>();



    /**
     * @Title connectionRepository
     * @Description 连接资源库对象
     * @param detail 资源库连接信息
     * @throws KettleException
     * @return void
     */
    public static KettleDatabaseRepository connectionRepository(MetaDatasourceForm.Detail detail) throws KettleException {
        if (null != detail){
            String name = detail.getDsId();
            String type = DbTypeCodeEnum.valueOf(detail.getDsType().toString()).getValue();
            String access = DbAccessTypeCodeEnum.valueOf(detail.getConnectType()).getValue();
//            String type = "oracle";
//            String access = "Navicat";
            String host = detail.getConnectIp();
            String db = detail.getSidName()+"?useSSL=false";
            String port = detail.getConnectPort();
            String user = detail.getConnectUser();
            String pass = detail.getConnectPwd();
            DatabaseMeta databaseMeta = new DatabaseMeta(name, type, access, host, db, port, user, pass);
            databaseMeta.addExtraOption(databaseMeta.getPluginId(), "characterEncoding", "UTF-8");
            databaseMeta.addExtraOption(databaseMeta.getPluginId(), "useUnicode", "true");
            //资源库元对象
            KettleDatabaseRepositoryMeta repositoryInfo = new KettleDatabaseRepositoryMeta();
            repositoryInfo.setConnection(databaseMeta);
            //资源库
            KettleDatabaseRepository repository = new KettleDatabaseRepository();
            repository.init(repositoryInfo);
            repository.connect("admin", "admin");
            //添加缓存
//            if (null != detail.getRepositoryId()){
//                KettleDatabaseRepositoryCatch.put(detail.getRepositoryId(), repository);
//            }
            return repository;
        }
        return null;
    }


    /**
     * @Title disConnectionRepository
     * @Description 断开资源库并删除缓存对象
     * @param repository
     * @param ID
     * @return void
     */
    public static void disConnectionRepository(KettleDatabaseRepository repository, Integer ID){
        repository.disconnect();
        repository.clearSharedObjectCache();
        KettleDatabaseRepositoryCatch.remove(ID);
    }

    /**
     * @Title disConnectionAllRepository
     * @Description 断开全部资源库
     * @return void
     */
    public static void disConnectionAllRepository(){
        KettleDatabaseRepositoryCatch.forEach((ID, repository) -> {
            repository.disconnect();
            repository.clearSharedObjectCache();
        });
        KettleDatabaseRepositoryCatch.clear();
    }


    /**
     * @Title getAllDirectoryTreeList
     * @Description 递归调用获取全部的树形菜单
     * @param kettleDatabaseRepository 资源库
     * @param path 当前路径
     * @param allRepositoryTreeList 所有的树形菜单
     * @return
     * @throws KettleException
     * @return List<RepositoryTree> 所有的树形菜单
     */
    public static List<RepositoryTreeForm> getAllDirectoryTreeList(KettleDatabaseRepository kettleDatabaseRepository,
                                                                   String path, List<RepositoryTreeForm> allRepositoryTreeList) throws KettleException {
        //获取Job和Transformation和Directory的信息
        List<RepositoryTreeForm> repositoryTreeList = getJobAndTrans(kettleDatabaseRepository, path);
        if (repositoryTreeList.size() != 0){
            for (RepositoryTreeForm repositoryTree : repositoryTreeList){
                //如果有子Directory或者Job和Transformation。那么递归遍历
                if (!repositoryTree.isLasted()){
                    getAllDirectoryTreeList(kettleDatabaseRepository, repositoryTree.getPath(), allRepositoryTreeList);
                    allRepositoryTreeList.add(repositoryTree);
                }else{
                    allRepositoryTreeList.add(repositoryTree);
                }
            }
        }
        return allRepositoryTreeList;
    }

    /**
     * @Title getJobAndTrans
     * @Description 获取Job和Transformation和Directory的信息
     * @param repository
     * @param path
     * @return RepositoryTree的集合
     * @throws KettleException
     * @return List<RepositoryTree>
     */
    public static List<RepositoryTreeForm> getJobAndTrans(KettleDatabaseRepository repository,
                                                          String path) throws KettleException {
        //获取当前的路径信息
        RepositoryDirectoryInterface rDirectory = repository.loadRepositoryDirectoryTree().findDirectory(path);
        //获取Directory信息
        List<RepositoryTreeForm> repositoryTreeList = getDirectory(repository, rDirectory);
        //获取Job和Transformation的信息
        List<RepositoryElementMetaInterface> li = repository.getJobAndTransformationObjects(rDirectory.getObjectId(), false);
        if (null != li) {
            for (RepositoryElementMetaInterface repel : li) {
                if (Constant.TYPE_JOB.equals(repel.getObjectType().toString())) {
                    RepositoryTreeForm repositoryTree = new RepositoryTreeForm();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(Constant.TYPE_JOB).append(rDirectory.getObjectId().toString()).append("@").append(repel.getObjectId().toString());
                    repositoryTree.setId(stringBuilder.toString());
                    repositoryTree.setParent(rDirectory.getObjectId().toString());
                    repositoryTree.setText(repel.getName());
                    repositoryTree.setType(Constant.TYPE_JOB);
                    repositoryTree.setLasted(true);
                    repositoryTreeList.add(repositoryTree);
                }else if (Constant.TYPE_TRANS.equals(repel.getObjectType().toString())){
                    RepositoryTreeForm repositoryTree = new RepositoryTreeForm();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(Constant.TYPE_TRANS).append(rDirectory.getObjectId().toString()).append("@").append(repel.getObjectId().toString());
                    repositoryTree.setId(stringBuilder.toString());
                    repositoryTree.setParent(rDirectory.getObjectId().toString());
                    repositoryTree.setText(repel.getName());
                    repositoryTree.setType(Constant.TYPE_TRANS);
                    repositoryTree.setLasted(true);
                    repositoryTreeList.add(repositoryTree);
                }
            }
        }
        return repositoryTreeList;
    }

    /**
     * @Title getDirectory
     * @Description 获取Directory信息
     * @param repository
     * @param rDirectory
     * @return
     * @throws KettleException
     * @return List<RepositoryTree>
     */
    public static List<RepositoryTreeForm> getDirectory(KettleDatabaseRepository repository,
                                                        RepositoryDirectoryInterface rDirectory) throws KettleException {
        List<RepositoryTreeForm> repositoryTreeList = new ArrayList<RepositoryTreeForm>();
        if (null != repository && null != rDirectory){
            RepositoryDirectoryInterface tree = repository.loadRepositoryDirectoryTree().findDirectory(rDirectory.getObjectId());
            if (rDirectory.getNrSubdirectories() > 0){
                for (int i = 0; i < rDirectory.getNrSubdirectories(); i++) {
                    RepositoryDirectory subTree = tree.getSubdirectory(i);
                    RepositoryTreeForm repositoryTree = new RepositoryTreeForm();
                    repositoryTree.setId(subTree.getObjectId().toString());
                    repositoryTree.setParent(rDirectory.getObjectId().toString());
                    repositoryTree.setText(subTree.getName());
                    repositoryTree.setPath(subTree.getPath());
                    //判断是否还有子Directory或者Job和Transformation
                    List<RepositoryElementMetaInterface> RepositoryElementMetaInterfaceList =
                            repository.getJobAndTransformationObjects(subTree.getObjectId(), false);
                    if (subTree.getNrSubdirectories() > 0 || RepositoryElementMetaInterfaceList.size() > 0){
                        repositoryTree.setLasted(false);
                    }else{
                        repositoryTree.setLasted(true);
                    }
                    repositoryTreeList.add(repositoryTree);
                }
            }
        }
        return repositoryTreeList;
    }
}
