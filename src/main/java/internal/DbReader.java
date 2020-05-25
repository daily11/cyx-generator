package internal;

import internal.entity.DbColumn;
import parser.Configuration;
import parser.entity.JDBCConnectionConfiguration;
import parser.entity.TableConfiguration;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库属性读取类
 */
public class DbReader {
    private Configuration configuration;
    /**
     * Integer内容为jdbcType，数据库读取出来的属性对应的类型，Datetime与timestamp类型的值都一样
     */
    private Map<Integer, TypeResolver.JdbcTypeInformation> typeMap;
    /**
     * <表名，该表所有列<每个列的属性描述>>
     */
    private Map<String, List<DbColumn>> tableColumnMaps = new HashMap<>();

    public DbReader(Configuration configuration) {
        this.configuration = configuration;
        initType();
    }

    private void initType() {
        typeMap = TypeResolver.getInstance().initType();
    }

    public void read() throws Exception {
        JDBCConnectionConfiguration jdbcConnectionConfiguration = configuration.getContexts().get(0).getJdbcConnectionConfiguration();
        List<TableConfiguration> tableConfigurationList = configuration.getContexts().get(0).getTableConfigurations();

        String url = jdbcConnectionConfiguration.getConnectionURL();
        String username = jdbcConnectionConfiguration.getUserId();
        String password = jdbcConnectionConfiguration.getPassword();
        Class.forName(jdbcConnectionConfiguration.getDriverClass());//项目pom中需要引入jdbc依赖
        Connection conn = DriverManager.getConnection(url, username, password);

        for (TableConfiguration tableConfiguration : tableConfigurationList) {
            System.out.println("------------------------------------------------------------");
            String tableName = tableConfiguration.getTableName();
            List<DbColumn> dbColumnList = new ArrayList<>();
            tableColumnMaps.put(tableName, dbColumnList);
            configuration.getContexts().get(0).setTableColumnMaps(tableColumnMaps);
            DatabaseMetaData databaseMetaData = conn.getMetaData();
            //获取所有表
//        ResultSet tableSet = databaseMetaData.getTables(null, "%", "%", new String[]{"TABLE"});
            //获取所有列
            ResultSet resultSet = databaseMetaData.getColumns(null, "%", tableName, "%");

            List<Map<String, Object>> tableResult = new ArrayList<>();
            while (resultSet.next()) {
                int dataType = resultSet.getInt("DATA_TYPE");
//                System.out.println("dataType--->" + dataType);

                Map<String, Object> columnMap = new HashMap<String, Object>();
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                //获取列数
                int colNum = resultSetMetaData.getColumnCount();
                //遍历列，获取列信息
                for (int i = 1; i <= colNum; i++) {
                    // 列名
                    String name = resultSetMetaData.getColumnLabel(i);
                    // 列属性
                    Object value = resultSet.getObject(i);
                    // 加入属性
                    columnMap.put(name, value);
                }
                tableResult.add(columnMap);
                printColumn(columnMap, dbColumnList, dataType);
            }
        }
    }

    private void printColumn(Map<String, Object> columnMap, List<DbColumn> dbColumnList, int dataType) {
        Object columnName = null;
        Object columnType = null;
        Object columnRemark = null;
        Object columnSize = null;
        Object columnIsNullable = null;
        for (Map.Entry entry : columnMap.entrySet()) {
            String key = (String) entry.getKey();
            if (key.equals("COLUMN_NAME")) {
                columnName = entry.getValue();
            }
            if (key.equals("TYPE_NAME")) {
                columnType = entry.getValue();
            }
            if (key.equals("REMARKS")) {
                columnRemark = entry.getValue();
            }
            if (key.equals("COLUMN_SIZE")) {
                columnSize = entry.getValue();
            }
            if (key.equals("IS_NULLABLE")) {
                columnIsNullable = entry.getValue();
            }
        }
//        System.out.println("列名:" + columnName + " , 类型:" + columnType + " , 备注:" + columnRemark
//                + " , 列大小:" + columnSize + " , 是否允许空:" + columnIsNullable);

        DbColumn dbColumn = new DbColumn();
        dbColumn.setIsNullable((String) columnIsNullable);
        dbColumn.setName((String) columnName);
        dbColumn.setJdbcName(getJdbcName((String) columnName));
        dbColumn.setRemark((String) columnRemark);
        dbColumn.setSize((Integer) columnSize);
        TypeResolver.JdbcTypeInformation jdbcTypeInformation = typeMap.get(dataType);
        dbColumn.setJavaJdbcType(jdbcTypeInformation.getJdbcType());
        dbColumn.setXmlJdbcType(jdbcTypeInformation.getXmlType());
        dbColumn.setTypeClassName(jdbcTypeInformation.getTypeClassName());

        dbColumnList.add(dbColumn);
    }

    private String getJdbcName(String columnName) {
        StringBuilder sb = new StringBuilder();
        String[] arr = columnName.split("_");
        sb.append(arr[0]);
        if (arr.length > 0) {
            for (int i = 1; i < arr.length; i++) {
                String s = arr[i];
                s = s.substring(0, 1).toUpperCase() + s.substring(1);
                sb.append(s);
            }
        }

        return sb.toString();
    }
}
