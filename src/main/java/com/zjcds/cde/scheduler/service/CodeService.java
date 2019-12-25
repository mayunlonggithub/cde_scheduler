package com.zjcds.cde.scheduler.service;

import com.zjcds.cde.scheduler.domain.entity.CodeMonitorStatus;
import com.zjcds.cde.scheduler.domain.entity.CodeRecordStatus;

import java.util.List;

/**
 * @author J on 20191122
 */
public interface CodeService {
    List<CodeMonitorStatus>  findAllMonitorStatus();
    List<CodeRecordStatus>  findAllRecordStatus();

}
