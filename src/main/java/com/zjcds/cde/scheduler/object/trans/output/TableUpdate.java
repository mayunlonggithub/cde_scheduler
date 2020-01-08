package com.zjcds.cde.scheduler.object.trans.output;

import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.insertupdate.InsertUpdateMeta;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author J on 2020/1/6
 */
public class TableUpdate {


    /**
     * 创建步骤：（输出：插入更新）
     * @param databaseMeta 数据库连接
     * @param tableName 表名称
     * @param keyLookups 关键字表字段
     * @param conditions 关键字比较符
     * @param keyStreams 关键字流字段1
     * @param keyStreams2 关键字流字段2
     * @param tableColumn 表字段
     * @param streamColumn 流字段
     * @param isUpdate 是否更新
     * @param stepName 步骤名称
     * @return
     */
    public static StepMeta createTableUpdate(DatabaseMeta databaseMeta, String tableName,List<String> keyLookups,List<String> conditions,List<String> keyStreams,
            List<String> keyStreams2, List<String> tableColumn, List<String> streamColumn,List<Boolean> isUpdate, String stepName) {
        // 新建一个表更新步骤
        InsertUpdateMeta insertUpdateMeta = new InsertUpdateMeta();
        // 设置步骤的数据库连接
        insertUpdateMeta.setDatabaseMeta(databaseMeta);
        // 设置步骤中的表
        insertUpdateMeta.setTableName(tableName);
        // 设置用来查询的关键字
        String[] keyLookup=keyLookups.toArray(new String[keyLookups.size()]);
        insertUpdateMeta.setKeyLookup(keyLookup);
        String[] condition=conditions.toArray(new String[conditions.size()]);
        insertUpdateMeta.setKeyCondition(condition);
        String[] keyStream=keyStreams.toArray(new String[keyStreams.size()]);
        insertUpdateMeta.setKeyStream(keyStream);
        String[] keyStream2=keyStreams2.toArray(new String[keyStreams2.size()]);
        insertUpdateMeta.setKeyStream2(keyStream2);// 一定要加上
        //映射字段判断
        Assert.notNull(tableColumn,"表字段不能为null");
        Assert.notNull(streamColumn,"流字段不能为null");
        Assert.isTrue(tableColumn.size()==streamColumn.size(),"表字段数量与流字段数量不一致");
        //表字段
        String[] tblCol=tableColumn.toArray(new String[tableColumn.size()]);
        insertUpdateMeta.setUpdateLookup(tblCol);
        //流字段
        String[] streamCol=streamColumn.toArray(new String[streamColumn.size()]);
        insertUpdateMeta.setUpdateStream(streamCol);
        // 设置是否更新
        Boolean[] updateOrNot = isUpdate.toArray(new Boolean[isUpdate.size()]);
        insertUpdateMeta.setUpdate(updateOrNot);
        // 设置步骤名称
        StepMeta tableOutput = new StepMeta(stepName, insertUpdateMeta);
        tableOutput.setLocation(100,50);
        return tableOutput;

    }
}
