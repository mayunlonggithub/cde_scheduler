package com.zjcds.cde.scheduler.service.exchange;

import com.zjcds.cde.scheduler.domain.dto.exchange.TransTabInTabOutForm;

public interface TransTabInTabOutService {

    public void addTrans(TransTabInTabOutForm.TransTabInTabOut transTabInTabOut) throws Exception;
}
