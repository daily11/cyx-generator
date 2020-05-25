package parser.entity;

import parser.Context;

public class TableConfiguration {
    private String tableName;
    private String domainObjectName;
    private Context context;

    public TableConfiguration(Context context) {
        this.context = context;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDomainObjectName() {
        return domainObjectName;
    }

    public void setDomainObjectName(String domainObjectName) {
        this.domainObjectName = domainObjectName;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
