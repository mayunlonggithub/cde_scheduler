package com.zjcds.cde.scheduler.object.db;

import com.zjcds.cde.scheduler.domain.dto.exchange.MetaDatasourceForm;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.ObjectId;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.pentaho.di.trans.TransMeta;

import java.util.Arrays;

public class Database {

    public static final String[] DBTYPECODE = { "oracle", "mysql", "db2", };
    public static final String[] DBACCESSTYPECODE = { "Native(JDBC)", "ODBC", "OCI", "JNDI", };


    public static DatabaseMeta databaseMeta(MetaDatasourceForm.Detail detail) throws KettleException {
        String name = detail.getDsId();
        String type = Arrays.asList(DBTYPECODE).get(detail.getDsType()-1);
        String access = Arrays.asList(DBACCESSTYPECODE).get(Integer.parseInt(detail.getConnectType())-1);
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
