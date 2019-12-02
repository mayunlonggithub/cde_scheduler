package com.zjcds.cde.scheduler.service;

import com.zjcds.cde.scheduler.domain.entity.Repository;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;

public interface InitializeService {

    /**
     * 资源库初始化并连接
     * @param repository
     * @return
     * @throws KettleException
     */
    public KettleDatabaseRepository init(Repository repository)throws KettleException;
}
