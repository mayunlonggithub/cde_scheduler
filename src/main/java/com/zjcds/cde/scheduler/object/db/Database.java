package com.zjcds.cde.scheduler.object.db;

import com.zjcds.cde.scheduler.domain.dto.exchange.MetaDatasourceForm;
import com.zjcds.cde.scheduler.domain.enums.DbAccessTypeCodeEnum;
import com.zjcds.cde.scheduler.domain.enums.DbTypeCodeEnum;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.ObjectId;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.pentaho.di.trans.TransMeta;

public class Database {


    public static DatabaseMeta databaseMeta(MetaDatasourceForm.Detail detail) throws KettleException {
        String name = detail.getDsId();
        String type = DbTypeCodeEnum.getKey(detail.getDsType().toString());
        String access = DbAccessTypeCodeEnum.getKey(detail.getConnectType());
//        String type = "oracle";
//        String access = "Navicat";
        String host = detail.getConnectIp();
        String db = detail.getSidName()+"?useSSL=false";
        String port = detail.getConnectPort();
        String user = detail.getConnectUser();
        String pass = detail.getConnectPwd();
        DatabaseMeta databaseMeta = new DatabaseMeta(name, type, access, host, db, port, user, pass);
        return databaseMeta;
    }

    /**
     * 保存转换到资源库
     *
     * @param kettleDatabaseRepository
     * @param transMeta
     * @throws Exception
     */
    public static void saveTrans(KettleDatabaseRepository kettleDatabaseRepository, TransMeta transMeta,String dirName)
            throws Exception {
        //查找目录
        RepositoryDirectoryInterface dir = kettleDatabaseRepository.loadRepositoryDirectoryTree().findDirectory("/"+dirName);
        if(dir==null){
            dir = kettleDatabaseRepository.getUserHomeDirectory();
        }
        dir = kettleDatabaseRepository.createRepositoryDirectory(dir, dirName.trim());
        dir.getPath();
        transMeta.setRepositoryDirectory(dir);
        ObjectId existingId = kettleDatabaseRepository.getTransformationID( transMeta.getName(), transMeta.getRepositoryDirectory() );
        transMeta.setObjectId(existingId);
        kettleDatabaseRepository.save(transMeta, null,null,true);

    }


}
