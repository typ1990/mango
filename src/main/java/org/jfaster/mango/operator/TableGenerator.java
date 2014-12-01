package org.jfaster.mango.operator;

import org.jfaster.mango.partition.TablePartition;

/**
 * @author ash
 */
public class TableGenerator {

    private String table; // 原始表名称
    private String shardParameterName;
    private String shardPropertyPath; // 为""的时候表示没有属性
    private TablePartition tablePartition; // 分表

    public TableGenerator(String table, String shardParameterName,
                          String shardPropertyPath, TablePartition tablePartition) {
        this.table = table;
        this.shardParameterName = shardParameterName;
        this.shardPropertyPath = shardPropertyPath;
        this.tablePartition = tablePartition;
    }

    public String getTable(InvocationContext context) {
        String realTable;
        if (!needTablePartition()) {
            realTable = table;
        } else {
            Object shardParam = context.getPropertyValue(shardParameterName, shardPropertyPath);
            realTable = tablePartition.getPartitionedTable(table, shardParam);
        }
        return realTable;
    }

    boolean needTablePartition() {
        return tablePartition != null && shardParameterName != null && shardPropertyPath != null;
    }

}