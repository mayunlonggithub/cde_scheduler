package com.zjcds.cde.scheduler.service.Impl;

import com.zjcds.cde.scheduler.domain.entity.Repository;
import com.zjcds.cde.scheduler.service.InitializeService;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryMeta;
import org.springframework.stereotype.Service;

@Service
public class InitializeServiceImpl implements InitializeService {

    /**
     * 资源库初始化并连接
     * @param repository
     * @return
     * @throws KettleException
     */
    @Override
    public KettleDatabaseRepository init(Repository repository)throws KettleException{
        KettleEnvironment.init();
        DatabaseMeta databaseMeta = new DatabaseMeta(null, repository.getRepositoryType(), repository.getDatabaseAccess(),
                repository.getDatabaseHost(), repository.getDatabaseName(), repository.getDatabasePort(), repository.getDatabaseUsername(), repository.getDatabasePassword());
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
