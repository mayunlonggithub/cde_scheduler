package com.zjcds.cde.scheduler.utils;

import com.zjcds.cde.scheduler.domain.dto.QuartzForm;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class CronUtils {
    /**
     * 方法摘要：构建Cron表达式
     *
     * @param quartz
     * @return String
     */
    static StringBuffer quartzCron;
    static StringBuffer quartzDescription;

    public static List<String> createQuartzCronressionAndDescription(QuartzForm.AddQuartz addQuartz) {
          List<String> Cron=new ArrayList<>();
          quartzCron=new StringBuffer("");
          quartzDescription=new StringBuffer("");
        //秒
        quartzCron.append(addQuartz.getExecSec()).append(" ");
        //分
        quartzCron.append(addQuartz.getExecMin()).append(" ");
        //小时
        quartzCron.append(addQuartz.getExecHour()).append(" ");
        if(addQuartz.getQuartzFlag()==Constant.Single){
               createSingleQuartzCronression(addQuartz);
        }else {
            switch (addQuartz.getUnitType()) {
                case "day":
                    createDayQuartzCronression(addQuartz);
                    break;
                case "week":
                    createWeekQuartzCronression(addQuartz);
                    break;
                case "month":
                    createMonthQuartzCronression(addQuartz);
                    break;
                case "year":
                    createYearQuartzCronression(addQuartz);
                    break;
                case "interval":
                    createIntervalQuartzCronression(addQuartz);
                    break;
            }
        }
        Cron.add(quartzCron.toString());
        Cron.add(quartzDescription.toString());
        return  Cron;
    }

    public static void createDayQuartzCronression(QuartzForm.AddQuartz addQuartz) {
        //每天
        quartzCron.append("* ").append(" ");//日
        quartzCron.append("* ").append(" ");//月
        quartzCron.append("?");//周
        quartzDescription.append("每天");
        quartzDescription.append(addQuartz.getExecHour()).append("时");
        quartzDescription.append(addQuartz.getExecMin()).append("分");
        quartzDescription.append(addQuartz.getExecSec()).append("秒");
        quartzDescription.append("执行");
    }

    public static void createWeekQuartzCronression(QuartzForm.AddQuartz addQuartz) {
        //按每周
        //一个月中第几天
        quartzCron.append("? ").append(" ");
        //月份
        quartzCron.append("* ").append(" ");
        //周
        Integer week = addQuartz.getExecWeek() + 1;
        if (week > 7) {
            week = 1;
        }
        quartzCron.append(week);
        String[] day={"","周一","周二","周三","周四","周五","周六","周天"};
        quartzDescription.append("每周的").append(day[addQuartz.getExecWeek()]);
        quartzDescription.append(addQuartz.getExecHour()).append("时");
        quartzDescription.append(addQuartz.getExecMin()).append("分");
        quartzDescription.append(addQuartz.getExecSec()).append("秒");
        quartzDescription.append("执行");
    }

    public static void createMonthQuartzCronression(QuartzForm.AddQuartz addQuartz) {

        //按每月
        //一个月中的哪几天
        Integer day = addQuartz.getExecDay();
        quartzCron.append(day).append(" ");
        //月份
        quartzCron.append(" * ").append(" ");
        //周
        quartzCron.append("?");
        String Day = addQuartz.getExecDay() + "号";
        quartzDescription.append("每月的").append(Day);
        quartzDescription.append(addQuartz.getExecHour()).append("时");
        quartzDescription.append(addQuartz.getExecMin()).append("分");
        quartzDescription.append(addQuartz.getExecSec()).append("秒");
        quartzDescription.append("执行");
    }

    public static void createYearQuartzCronression(QuartzForm.AddQuartz addQuartz) {
        //按每年
        //一年中的哪一个月
        Integer month = addQuartz.getExecMonth();
        //月中的哪一天
        Integer day = addQuartz.getExecDay();
        //天
        quartzCron.append(day).append(" ");
        //月份
        quartzCron.append(month).append(" ");
        //周
        quartzCron.append("?").append(" ");
        //年
        quartzCron.append("*");
        String Month=addQuartz.getExecMonth()+"月";
        String Day = addQuartz.getExecDay() + "号";
        quartzDescription.append("每年的").append(Month).append(Day);
        quartzDescription.append(addQuartz.getExecHour()).append("时");
        quartzDescription.append(addQuartz.getExecMin()).append("分");
        quartzDescription.append(addQuartz.getExecSec()).append("秒");
        quartzDescription.append("执行");
    }

    public static void createIntervalQuartzCronression(QuartzForm.AddQuartz addQuartz) {

        quartzCron = new StringBuffer("");
        Integer secInterval = addQuartz.getSecInterval();
        Integer minInterval = addQuartz.getMinInterval();
        Integer hourInterval = addQuartz.getHourInterval();
        java.sql.Timestamp time = new java.sql.Timestamp(new java.util.Date().getTime());
        Integer hour=time.getHours();//时
        Integer min=time.getMinutes();//分
        Integer sec=time.getSeconds();//秒
        if(secInterval!=null) {
            quartzCron.append("*").append("/").append(secInterval).append(" ");
            quartzCron.append("*").append(" ");
            quartzCron.append("*").append(" ");
            quartzDescription.append("每隔").append(addQuartz.getSecInterval()).append("秒").append("执行");

        }else if(minInterval!=null){
            quartzCron.append(sec).append(" ");
            quartzCron.append("*").append("/").append(minInterval).append(" ");
            quartzCron.append("*").append(" ");
            quartzDescription.append("每隔").append(addQuartz.getMinInterval()).append("分钟").append("执行");
        }else {
            quartzCron.append(sec).append(" ");
            quartzCron.append(min).append(" ");
            quartzCron.append("*").append("/").append(hourInterval).append(" ");
            quartzDescription.append("每隔").append(addQuartz.getHourInterval()).append("小时").append("执行");
        }
        //天
        quartzCron.append("*").append(" ");
        //月份
        quartzCron.append("*").append(" ");
        //周
        quartzCron.append("?").append(" ");
    }

    public static void createSingleQuartzCronression(QuartzForm.AddQuartz addQuartz) {
        //一年中的哪一个月
        Integer month = addQuartz.getExecMonth();
        //月中的哪一天
        Integer day = addQuartz.getExecDay();
        //年份
        Integer year=addQuartz.getExecYear();
        //天
        quartzCron.append(day).append(" ");
        //月份
        quartzCron.append(month).append(" ");
        //周
        quartzCron.append("?").append(" ");
        //年
        quartzCron.append(year);
        String Month=addQuartz.getExecMonth()+"月";
        String Day = addQuartz.getExecDay() + "号";
        quartzDescription.append(addQuartz.getExecYear()+"年").append(Month).append(Day);
        quartzDescription.append(addQuartz.getExecHour()).append("时");
        quartzDescription.append(addQuartz.getExecMin()).append("分");
        quartzDescription.append(addQuartz.getExecSec()).append("秒");
        quartzDescription.append("执行一次");
    }
    }
