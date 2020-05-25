package internal.entity;

/**
 * 数据库表中列的描述类
 */
public class DbColumn {
    //列名
    private String name;
    //java对象属性名
    private String jdbcName;
    //java列类型
    private String javaJdbcType;
    //xml列类型
    private String xmlJdbcType;
    //typeClassName
    private String typeClassName;
    //列大小
    private Integer size;
    //是否允许空
    private String isNullable;
    //备注
    private String remark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJdbcName() {
        return jdbcName;
    }

    public void setJdbcName(String jdbcName) {
        this.jdbcName = jdbcName;
    }

    public String getJavaJdbcType() {
        return javaJdbcType;
    }

    public void setJavaJdbcType(String javaJdbcType) {
        this.javaJdbcType = javaJdbcType;
    }

    public String getXmlJdbcType() {
        return xmlJdbcType;
    }

    public void setXmlJdbcType(String xmlJdbcType) {
        this.xmlJdbcType = xmlJdbcType;
    }

    public String getTypeClassName() {
        return typeClassName;
    }

    public void setTypeClassName(String typeClassName) {
        this.typeClassName = typeClassName;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getIsNullable() {
        return isNullable;
    }

    public void setIsNullable(String isNullable) {
        this.isNullable = isNullable;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
