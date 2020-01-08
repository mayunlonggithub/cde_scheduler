package com.zjcds.cde.scheduler.object.trans.common;

import org.pentaho.di.trans.TransMeta;

public class NewTrans {

    /**
     * 创建转换
     * @param transName
     * @return
     */
    public static TransMeta createTrans(String transName) {
        TransMeta transMeta = new TransMeta();
        // 设置转化的名称
        transMeta.setName(transName);
        return transMeta;
    }
}
