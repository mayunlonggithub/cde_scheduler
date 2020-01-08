package com.zjcds.cde.scheduler.object.trans.output;

import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.tableoutput.TableOutputMeta;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author J on 20191209
 * 表输出
 */
public class TableOutput {

    /**
     * 创建步骤：（输出：表输出）
     * @param databaseMeta  数据库连接
     * @param tableName 表名称
     * @param tableColumn   表字段
     * @param streamColumn  流字段
     * @param stepName  步骤名称
     * @return
     */
    public static StepMeta createTableOutput(DatabaseMeta databaseMeta, String tableName, List<String> tableColumn,List<String> streamColumn,String stepName) {
        // 新建一个表输出步骤(TableInputMeta)
        TableOutputMeta tableOutputMeta = new TableOutputMeta();
        // 设置步骤的数据库连接
        tableOutputMeta.setDatabaseMeta(databaseMeta);
        // 设置步骤1中的sql
        tableOutputMeta.setTableName(tableName);
        //指定数据库字段
        tableOutputMeta.setSpecifyFields(true);
        //裁剪表
        tableOutputMeta.setTruncateTable(true);
        //映射字段判断
        Assert.notNull(tableColumn,"表字段不能为null");
        Assert.notNull(streamColumn,"流字段不能为null");
        Assert.isTrue(tableColumn.size()==streamColumn.size(),"表字段数量与流字段数量不一致");
        //表字段
        String[] tblCol=tableColumn.toArray(new String[tableColumn.size()]);
        tableOutputMeta.setFieldDatabase(tblCol);
        //流字段
        String[] streamCol=streamColumn.toArray(new String[streamColumn.size()]);
        tableOutputMeta.setFieldStream(streamCol);
        // 设置步骤名称
        StepMeta tableOutput = new StepMeta(stepName, tableOutputMeta);
        tableOutput.setLocation(100,50);
        return tableOutput;

    }
}
