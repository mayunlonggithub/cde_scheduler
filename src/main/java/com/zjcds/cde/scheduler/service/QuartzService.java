package com.zjcds.cde.scheduler.service;

import com.zjcds.cde.scheduler.domain.dto.QuartzForm;
import com.zjcds.cde.scheduler.domain.entity.Quartz;
import com.zjcds.common.base.domain.page.Paging;
import com.zjcds.common.jpa.PageResult;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
/**
 * @author Ma on 20191122
 */
public interface QuartzService
{
    void addQuartz(QuartzForm.AddQuartz addQuartz);
    void deleteQuartz(Integer quartzId);
    void updateQuartz(QuartzForm.UpdateQuartz updateQuartz);
    PageResult<Quartz> getList(Paging paging, List<String> queryString, List<String> orderBys);
    Date getNextValidTime(Date date, Integer quartzId) throws ParseException;
}
