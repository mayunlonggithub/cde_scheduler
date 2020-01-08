package com.zjcds.cde.scheduler.object.db;

import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryMeta;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Initialize {

    private static Initialize instance;

    @Value("${cde.repositoryType}")
    private String repositoryType;
    @Value("${cde.databaseAccess}")
    private String databaseAccess;
    @Value("${cde.databaseHost}")
    private String databaseHost;
    @Value("${cde.databaseName}")
    private String databaseName;
    @Value("${cde.databasePort}")
    private String databasePort;
    @Value("${cde.databaseUsername}")
    private String databaseUsername;
    @Value("${cde.databasePassword}")
    private String databasePassword;


//    /**
//     * 单例模式，获得工具类的一个对象
//     *
//     * @return
//     */
//    public static Initialize getInstance() {
//        if (instance == null) {
//            instance = new Initialize();
//        }
//        return instance;
//    }

    /**
     * 资源库初始化并连接
     * @return
     * @throws KettleException
     */

    public KettleDatabaseRepository RepositoryCon()throws KettleException{
        KettleEnvironment.init();
        DatabaseMeta databaseMeta = new DatabaseMeta(null, repositoryType, databaseAccess, databaseHost, databaseName, databasePort, databaseUsername, databasePassword);
        databaseMeta.addExtraOption(databaseMeta.getPluginId(), "characterEncoding", "UTF-8");
        databaseMeta.addExtraOption(databaseMeta.getPluginId(), "useUnicode", "true");
        //资源库元对象
        KettleDatabaseRepositoryMeta repositoryInfo = new KettleDatabaseRepositoryMeta();
        repositoryInfo.setConnection(databaseMeta);
        //资源库
        KettleDatabaseRepository kettleDatabaseRepository = new KettleDatabaseRepository();
        kettleDatabaseRepository.init(repositoryInfo);
        kettleDatabaseRepository.connect("admin", "admin");
        //判断是否连接成功
        if (kettleDatabaseRepository.isConnected()) {
            System.out.println( "connected" );
        }else{
            System.out.println("error");
        }
        return kettleDatabaseRepository;
    }
}
