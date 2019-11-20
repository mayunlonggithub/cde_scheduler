package com.zjcds.cde.scheduler.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author J on 20191111
 */
public interface JobMonitorService {

    /**
     * @param userId 用户ID
     * @param jobId  转换ID
     * @return void
     * @Title addMonitor
     * @Description 添加监控
     */
    public void addMonitor(Integer userId, Integer jobId, Date nextExecuteTime);
}
