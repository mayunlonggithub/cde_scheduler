package com.zjcds.cde.scheduler.object.job.general;

import org.pentaho.di.job.entries.special.JobEntrySpecial;
import org.pentaho.di.job.entry.JobEntryCopy;

/**
 * @author J on 20191209
 * 作业开始
 */
public class JobStart {

    /**
     * 创建开始节点
     * @return
     */
    public static JobEntryCopy createJobStart(){
        JobEntrySpecial jobEntrySpecial = new JobEntrySpecial();
        jobEntrySpecial.setName("");
        jobEntrySpecial.setStart(true);
        JobEntryCopy jobEntryCopy = new JobEntryCopy(jobEntrySpecial);
        jobEntryCopy.setDrawn();
        jobEntryCopy.setLocation(10,10);
        return jobEntryCopy;
    }
}
