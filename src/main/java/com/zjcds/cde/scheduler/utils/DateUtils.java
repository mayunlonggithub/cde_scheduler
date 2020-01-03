package com.zjcds.cde.scheduler.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    /**
     * 获取今天开始时间
     */
    public static Date getStartTime(Date date) {
        Calendar todayStart = Calendar.getInstance();
        todayStart.setTime(date);
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }

    /**
     * 获取今天结束时间
     * */
    public static Date getEndTime(Date date) {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.setTime(date);
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }

    /**
     * 获取时间差
     * @param startTime
     * @param endTime
     * @return
     */
    public static String getDuration(Date startTime, Date endTime){
        String duration=null;
        try {
            long diff = endTime.getTime() - startTime.getTime();//这样得到的差值是微秒级别
            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
            long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
            long second = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60)-minutes*(1000*60))/(1000);
            if(days>0){
                duration = ""+days+"天"+hours+"小时"+minutes+"分"+second+"秒";
            }
            if(days<=0){
                duration = ""+hours+"小时"+minutes+"分"+second+"秒";
            }
            if(days<=0&&hours<=0){
                duration = ""+minutes+"分"+second+"秒";
            }
            if(days<=0&&hours<=0&&minutes<=0){
                duration = ""+second+"秒";
            }
            return duration;
        }catch (Exception e) {
            return duration;
        }

    }


    public static Date getYmd(Date date) {
        Calendar Ymd = Calendar.getInstance();
        Ymd.setTime(date);
        Ymd.set(Calendar.HOUR_OF_DAY, 0);
        Ymd.set(Calendar.MINUTE, 0);
        Ymd.set(Calendar.SECOND, 0);
        Ymd.set(Calendar.MILLISECOND, 0);
        return Ymd.getTime();
    }
}
