package com.zjcds.cde.scheduler.service.exchange;

import com.zjcds.cde.scheduler.domain.dto.exchange.TransTabInTabUpdateForm;

/**
 * @author J on 2020/1/2
 */
public interface TransTabInTabUpdateService {

    public void addTrans(TransTabInTabUpdateForm.TransTabInTabUpdate transTabInTabUpdate) throws Exception;
}
