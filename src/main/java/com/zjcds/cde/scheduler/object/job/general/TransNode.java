package com.zjcds.cde.scheduler.object.job.general;

import org.pentaho.di.job.entry.JobEntryCopy;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;

/**
 * @author J on 20191209
 * 转换节点
 */
public class TransNode {

    public static JobEntryCopy createTransNode(KettleDatabaseRepository kettleDatabaseRepository)throws Exception{

        JobEntryCopy jobEntryCopy = new JobEntryCopy();
        return jobEntryCopy;
    }
}
