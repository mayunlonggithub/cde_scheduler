package com.zjcds.cde.scheduler.object.trans.input;

import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.tableinput.TableInputMeta;

/**
 * @author J on 20191209
 * 表输入
 */
public class TableInput {

    /**
     * 创建步骤：（输入：表输入）
     * @param databaseMeta
     * @param sql
     * @param stepName
     * @return
     */
     public static StepMeta createTableInput(DatabaseMeta databaseMeta,String sql,String stepName) {
        // 新建一个表输入步骤(TableInputMeta)
        TableInputMeta tableInputMeta = new TableInputMeta();
        // 设置步骤1的数据库连接
        tableInputMeta.setDatabaseMeta(databaseMeta);
        // 设置步骤1中的sql
        tableInputMeta.setSQL(sql);
        // 设置步骤名称
         StepMeta tableInput = new StepMeta(stepName, tableInputMeta);
         tableInput.setLocation(50,50);
        return tableInput;
     }
}
