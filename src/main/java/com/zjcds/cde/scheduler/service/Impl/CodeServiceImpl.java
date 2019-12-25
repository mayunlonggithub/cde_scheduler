package com.zjcds.cde.scheduler.service.Impl;

import com.zjcds.cde.scheduler.dao.jpa.CodeMonitorStatusDao;
import com.zjcds.cde.scheduler.dao.jpa.CodeRecordStatusDao;
import com.zjcds.cde.scheduler.domain.entity.CodeMonitorStatus;
import com.zjcds.cde.scheduler.domain.entity.CodeRecordStatus;
import com.zjcds.cde.scheduler.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author J on 20191122
 */
@Service
public class CodeServiceImpl implements CodeService {
    @Autowired
    private CodeMonitorStatusDao codeMonitorStatusDao;
    @Autowired
    private CodeRecordStatusDao codeRecordStatusDao;
    @Override
    public List<CodeMonitorStatus> findAllMonitorStatus() {
        return codeMonitorStatusDao.findAll();
    }
    @Override
    public List<CodeRecordStatus> findAllRecordStatus(){
        return codeRecordStatusDao.findAll();
    }

}
