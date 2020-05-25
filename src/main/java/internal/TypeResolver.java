package internal;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TypeResolver {
    private static TypeResolver typeResolver = new TypeResolver();

    public static TypeResolver getInstance() {
        return typeResolver;
    }

    public Map<Integer, JdbcTypeInformation> initType() {
        Map<Integer, JdbcTypeInformation> typeMap = new HashMap<>();

        typeMap.put(Types.ARRAY, new JdbcTypeInformation("ARRAY", //$NON-NLS-1$
                qualifiedJavaType(Object.class.getName()),Object.class.getName()));
        typeMap.put(Types.BIGINT, new JdbcTypeInformation("BIGINT", //$NON-NLS-1$
                qualifiedJavaType(Long.class.getName()),Long.class.getName()));
        typeMap.put(Types.BINARY, new JdbcTypeInformation("BINARY", //$NON-NLS-1$
                qualifiedJavaType("byte[]"),"byte[]".getClass().getName())); //$NON-NLS-1$
        typeMap.put(Types.BIT, new JdbcTypeInformation("BIT", //$NON-NLS-1$
                qualifiedJavaType(Boolean.class.getName()),Boolean.class.getName()));
        typeMap.put(Types.BLOB, new JdbcTypeInformation("BLOB", //$NON-NLS-1$
                qualifiedJavaType("byte[]"),"byte[]".getClass().getName())); //$NON-NLS-1$
        typeMap.put(Types.BOOLEAN, new JdbcTypeInformation("BOOLEAN", //$NON-NLS-1$
                qualifiedJavaType(Boolean.class.getName()),Boolean.class.getName()));
        typeMap.put(Types.CHAR, new JdbcTypeInformation("CHAR", //$NON-NLS-1$
                qualifiedJavaType(String.class.getName()),String.class.getName()));
        typeMap.put(Types.CLOB, new JdbcTypeInformation("CLOB", //$NON-NLS-1$
                qualifiedJavaType(String.class.getName()),String.class.getName()));
        typeMap.put(Types.DATALINK, new JdbcTypeInformation("DATALINK", //$NON-NLS-1$
                qualifiedJavaType(Object.class.getName()),Object.class.getName()));
        typeMap.put(Types.DATE, new JdbcTypeInformation("DATE", //$NON-NLS-1$
                qualifiedJavaType(Date.class.getName()),Date.class.getName()));
        typeMap.put(Types.DECIMAL, new JdbcTypeInformation("DECIMAL", //$NON-NLS-1$
                qualifiedJavaType(BigDecimal.class.getName()),BigDecimal.class.getName()));
        typeMap.put(Types.DISTINCT, new JdbcTypeInformation("DISTINCT", //$NON-NLS-1$
                qualifiedJavaType(Object.class.getName()),Object.class.getName()));
        typeMap.put(Types.DOUBLE, new JdbcTypeInformation("DOUBLE", //$NON-NLS-1$
                qualifiedJavaType(Double.class.getName()),Double.class.getName()));
        typeMap.put(Types.FLOAT, new JdbcTypeInformation("FLOAT", //$NON-NLS-1$
                qualifiedJavaType(Double.class.getName()),Double.class.getName()));
        typeMap.put(Types.INTEGER, new JdbcTypeInformation("INTEGER", //$NON-NLS-1$
                qualifiedJavaType(Integer.class.getName()),Integer.class.getName()));
        typeMap.put(Types.JAVA_OBJECT, new JdbcTypeInformation("JAVA_OBJECT", //$NON-NLS-1$
                qualifiedJavaType(Object.class.getName()),Object.class.getName()));
        typeMap.put(Types.LONGNVARCHAR, new JdbcTypeInformation("LONGNVARCHAR", //$NON-NLS-1$
                qualifiedJavaType(String.class.getName()),String.class.getName()));
        typeMap.put(Types.LONGVARBINARY, new JdbcTypeInformation(
                "LONGVARBINARY", //$NON-NLS-1$
                qualifiedJavaType("byte[]"),"byte[]".getClass().getName())); //$NON-NLS-1$
        typeMap.put(Types.LONGVARCHAR, new JdbcTypeInformation("LONGVARCHAR", //$NON-NLS-1$
                qualifiedJavaType(String.class.getName()),String.class.getName()));
        typeMap.put(Types.NCHAR, new JdbcTypeInformation("NCHAR", //$NON-NLS-1$
                qualifiedJavaType(String.class.getName()),String.class.getName()));
        typeMap.put(Types.NCLOB, new JdbcTypeInformation("NCLOB", //$NON-NLS-1$
                qualifiedJavaType(String.class.getName()),String.class.getName()));
        typeMap.put(Types.NVARCHAR, new JdbcTypeInformation("NVARCHAR", //$NON-NLS-1$
                qualifiedJavaType(String.class.getName()),String.class.getName()));
        typeMap.put(Types.NULL, new JdbcTypeInformation("NULL", //$NON-NLS-1$
                qualifiedJavaType(Object.class.getName()),Object.class.getName()));
        typeMap.put(Types.NUMERIC, new JdbcTypeInformation("NUMERIC", //$NON-NLS-1$
                qualifiedJavaType(BigDecimal.class.getName()),BigDecimal.class.getName()));
        typeMap.put(Types.OTHER, new JdbcTypeInformation("OTHER", //$NON-NLS-1$
                qualifiedJavaType(Object.class.getName()),Object.class.getName()));
        typeMap.put(Types.REAL, new JdbcTypeInformation("REAL", //$NON-NLS-1$
                qualifiedJavaType(Float.class.getName()),Float.class.getName()));
        typeMap.put(Types.REF, new JdbcTypeInformation("REF", //$NON-NLS-1$
                qualifiedJavaType(Object.class.getName()),Object.class.getName()));
        typeMap.put(Types.SMALLINT, new JdbcTypeInformation("SMALLINT", //$NON-NLS-1$
                qualifiedJavaType(Short.class.getName()),Short.class.getName()));
        typeMap.put(Types.STRUCT, new JdbcTypeInformation("STRUCT", //$NON-NLS-1$
                qualifiedJavaType(Object.class.getName()),Object.class.getName()));
        typeMap.put(Types.TIME, new JdbcTypeInformation("TIME", //$NON-NLS-1$
                qualifiedJavaType(Date.class.getName()),Date.class.getName()));
        typeMap.put(Types.TIMESTAMP, new JdbcTypeInformation("TIMESTAMP", //$NON-NLS-1$
                qualifiedJavaType(Date.class.getName()),Date.class.getName()));
        typeMap.put(Types.TINYINT, new JdbcTypeInformation("TINYINT", //$NON-NLS-1$
                qualifiedJavaType(Byte.class.getName()),Byte.class.getName()));
        typeMap.put(Types.VARBINARY, new JdbcTypeInformation("VARBINARY", //$NON-NLS-1$
                qualifiedJavaType("byte[]"),"byte[]".getClass().getName())); //$NON-NLS-1$
        typeMap.put(Types.VARCHAR, new JdbcTypeInformation("VARCHAR", //$NON-NLS-1$
                qualifiedJavaType(String.class.getName()),String.class.getName()));
        // JDK 1.8 types
        typeMap.put(Types.TIME_WITH_TIMEZONE, new JdbcTypeInformation("TIME_WITH_TIMEZONE", //$NON-NLS-1$
                qualifiedJavaType("java.time.OffsetTime"),String.class.getName())); //$NON-NLS-1$
        typeMap.put(Types.TIMESTAMP_WITH_TIMEZONE, new JdbcTypeInformation("TIMESTAMP_WITH_TIMEZONE",
                qualifiedJavaType("java.time.OffsetDateTime"),String.class.getName())); //$NON-NLS-1$

        return typeMap;
    }

    private String qualifiedJavaType(String javaType) {
        int index = javaType.lastIndexOf('.');
        return javaType.substring(index + 1);
    }

    class JdbcTypeInformation {
        private String jdbcType;
        private String xmlType;
        private String typeClassName;

        public JdbcTypeInformation(String xmlType, String jdbcType, String typeClassName) {
            this.jdbcType = jdbcType;
            this.xmlType = xmlType;
            this.typeClassName = typeClassName;
        }

        public String getJdbcType() {
            return jdbcType;
        }

        public void setJdbcType(String jdbcType) {
            this.jdbcType = jdbcType;
        }

        public String getXmlType() {
            return xmlType;
        }

        public void setXmlType(String xmlType) {
            this.xmlType = xmlType;
        }

        public String getTypeClassName() {
            return typeClassName;
        }

        public void setTypeClassName(String typeClassName) {
            this.typeClassName = typeClassName;
        }
    }
}
