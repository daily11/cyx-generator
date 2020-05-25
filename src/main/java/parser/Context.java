package parser;

import internal.entity.DbColumn;
import parser.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Context {
    private JDBCConnectionConfiguration jdbcConnectionConfiguration;
    private JavaModelGeneratorConfiguration javaModelGeneratorConfiguration;
    private SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration;
    private JavaClientGeneratorConfiguration javaClientGeneratorConfiguration;
    private ArrayList<TableConfiguration> tableConfigurations;
    /**
     * <表名，该表所有列<每个列的属性描述>>
     */
    private Map<String,List<DbColumn>> tableColumnMaps;

    public Map<String, List<DbColumn>> getTableColumnMaps() {
        return tableColumnMaps;
    }

    public void setTableColumnMaps(Map<String, List<DbColumn>> tableColumnMaps) {
        this.tableColumnMaps = tableColumnMaps;
    }

    public ArrayList<TableConfiguration> getTableConfigurations() {
        return tableConfigurations;
    }

    public void setTableConfigurations(ArrayList<TableConfiguration> tableConfigurations) {
        this.tableConfigurations = tableConfigurations;
    }

    public JavaClientGeneratorConfiguration getJavaClientGeneratorConfiguration() {
        return javaClientGeneratorConfiguration;
    }

    public void setJavaClientGeneratorConfiguration(JavaClientGeneratorConfiguration javaClientGeneratorConfiguration) {
        this.javaClientGeneratorConfiguration = javaClientGeneratorConfiguration;
    }

    public JDBCConnectionConfiguration getJdbcConnectionConfiguration() {
        return jdbcConnectionConfiguration;
    }

    public void setJdbcConnectionConfiguration(JDBCConnectionConfiguration jdbcConnectionConfiguration) {
        this.jdbcConnectionConfiguration = jdbcConnectionConfiguration;
    }

    public JavaModelGeneratorConfiguration getJavaModelGeneratorConfiguration() {
        return javaModelGeneratorConfiguration;
    }

    public void setJavaModelGeneratorConfiguration(JavaModelGeneratorConfiguration javaModelGeneratorConfiguration) {
        this.javaModelGeneratorConfiguration = javaModelGeneratorConfiguration;
    }

    public SqlMapGeneratorConfiguration getSqlMapGeneratorConfiguration() {
        return sqlMapGeneratorConfiguration;
    }

    public void setSqlMapGeneratorConfiguration(SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration) {
        this.sqlMapGeneratorConfiguration = sqlMapGeneratorConfiguration;
    }

    public void addTableConfiguration(TableConfiguration tc) {
        if (tableConfigurations == null) {
            tableConfigurations = new ArrayList<>();
        }
        this.tableConfigurations.add(tc);
    }

}
