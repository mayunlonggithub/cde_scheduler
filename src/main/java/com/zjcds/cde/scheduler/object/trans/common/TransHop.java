package com.zjcds.cde.scheduler.object.trans.common;

import org.pentaho.di.trans.TransHopMeta;
import org.pentaho.di.trans.step.StepMeta;

public class TransHop {

    /**
     * 创建节点连接
     *
     * @param step1
     * @param step2
     * @return
     */
    public static TransHopMeta createHop(StepMeta step1, StepMeta step2) {
        // 设置起始步骤和目标步骤，把两个步骤关联起来
        TransHopMeta transHopMeta = new TransHopMeta(step1, step2);
        return transHopMeta;
    }
}
