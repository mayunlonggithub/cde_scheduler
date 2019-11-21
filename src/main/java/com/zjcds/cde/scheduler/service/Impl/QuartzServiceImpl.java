package com.zjcds.cde.scheduler.service.Impl;

import com.zjcds.cde.scheduler.dao.jpa.QuartzDao;
import com.zjcds.cde.scheduler.domain.dto.QuartzForm;
import com.zjcds.cde.scheduler.domain.entity.Quartz;
import com.zjcds.cde.scheduler.service.QuartzService;
import com.zjcds.cde.scheduler.utils.CronUtils;
import com.zjcds.common.base.domain.page.Paging;
import com.zjcds.common.dozer.BeanPropertyCopyUtils;
import com.zjcds.common.jpa.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class QuartzServiceImpl implements QuartzService {
    @Autowired
    private QuartzDao quartzDao;

    @Override
    @Transactional
    public void addQuartz(QuartzForm.AddQuartz addQuartz){
        if(addQuartz.getQuartzCron()==null) {
            List<String> cron = CronUtils.createQuartzCronressionAndDescription(addQuartz);
            addQuartz.setQuartzCron(cron.get(0));
            addQuartz.setQuartzDescription(cron.get(1));
        }
        Quartz quartz=BeanPropertyCopyUtils.copy(addQuartz,Quartz.class);
        quartz.setDelFlag(1);
        quartzDao.save(quartz);
    }

    @Override
    @Transactional
    public void deleteQuartz(Integer quartzId){
        Quartz quartz = quartzDao.findOne(quartzId);
        quartz.setDelFlag(0);
        quartzDao.save(quartz);
    }

    @Override
    @Transactional
    public void updateQuartz(QuartzForm.UpdateQuartz updateQuartz) {
        if (updateQuartz.getQuartzCron() == null) {
            List<String> cron = CronUtils.createQuartzCronressionAndDescription(updateQuartz);
            updateQuartz.setQuartzCron(cron.get(0));
            updateQuartz.setQuartzDescription(cron.get(1));
        }
        Quartz quartz = BeanPropertyCopyUtils.copy(updateQuartz, Quartz.class);
        quartzDao.save(quartz);
    }

    @Override
    @Transactional
    public PageResult<Quartz> getList(Paging paging, List<String> queryString, List<String> orderBys){
        queryString.add("delFlag~eq~1");
        PageResult<Quartz> quartz = quartzDao.findAll(paging,queryString,orderBys);
        return quartz;
    }

}