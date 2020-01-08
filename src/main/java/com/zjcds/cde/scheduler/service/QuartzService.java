package com.zjcds.cde.scheduler.service;

import com.zjcds.cde.scheduler.base.PageResult;
import com.zjcds.cde.scheduler.base.Paging;
import com.zjcds.cde.scheduler.domain.dto.QuartzForm;
import com.zjcds.cde.scheduler.domain.entity.Quartz;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
/**
 * @author Ma on 20191122
 */
public interface QuartzService
{
    Quartz addQuartz(QuartzForm.AddQuartz addQuartz,Integer uId);
    void deleteQuartz(Integer quartzId,Integer uId);
    void updateQuartz(QuartzForm.UpdateQuartz updateQuartz,Integer uId) throws ParseException;
    PageResult<Quartz> getList(Paging paging, List<String> queryString, List<String> orderBys,Integer uId);
    Date getNextValidTime(Date date, Integer quartzId) throws ParseException;
    List<Quartz> getQuartzByDelFlag(Integer flag);
}
