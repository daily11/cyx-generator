package codegen;

import internal.entity.DbColumn;
import parser.Context;
import parser.entity.JavaClientGeneratorConfiguration;
import parser.entity.JavaModelGeneratorConfiguration;
import parser.entity.SqlMapGeneratorConfiguration;
import parser.entity.TableConfiguration;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;

public class GenerateXml {

    public void generate(Context context) throws Exception{
        Map<String, List<DbColumn>> tableColumnMaps = context.getTableColumnMaps();
        for (Map.Entry<String, List<DbColumn>> entry : tableColumnMaps.entrySet()) {
            String tableName = entry.getKey();
            List<DbColumn> columns = entry.getValue();

            String domainObjectName = "";
            List<TableConfiguration> tableConfigurationList = context.getTableConfigurations();
            for (TableConfiguration tableConfiguration : tableConfigurationList) {
                if (tableConfiguration.getTableName().equals(tableName)) {
                    domainObjectName = tableConfiguration.getDomainObjectName();
                }
            }

            generateTable(context, tableName, domainObjectName, columns);
        }
    }

    private void generateTable(Context context, String tableName, String domainObjectName, List<DbColumn> columns) throws Exception{
        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = context.getSqlMapGeneratorConfiguration();

        StringBuilder result = new StringBuilder();//总的，待生成的文件字符流
        result.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >");
        enter(result);
        result.append("<mapper namespace=\"" + sqlMapGeneratorConfiguration.getTargetPackage() + "." + domainObjectName + "Dao\""+">");
        enter(result);
        enter(result);

        String baseResultMap = generateBaseResultMap(context, columns, domainObjectName);
        String selectByPrimaryKey = generateSelectByPrimaryKey(context, columns, tableName);
        String insertSelective = generateInsertSelective(context, columns, tableName, domainObjectName);
        String updateByPrimaryKeySelective = generateUpdateByPrimaryKeySelective(context, columns, tableName, domainObjectName);
        String deleteByPrimaryKey = generateDeleteByPrimaryKey(context, columns, tableName);

        result.append(baseResultMap);
        enter(result);
        result.append(selectByPrimaryKey);
        enter(result);
        result.append(insertSelective);
        enter(result);
        result.append(updateByPrimaryKeySelective);
        enter(result);
        result.append(deleteByPrimaryKey);
        enter(result);
        result.append("</mapper>");

//        System.out.println(result.toString());

        JavaClientGeneratorConfiguration target = context.getJavaClientGeneratorConfiguration();
        String targetProject = target.getTargetProject();
        String targetPackage  = target.getTargetPackage();
        String targetPackagePath = targetPackage.replace(".","/");
        File father = new File(targetProject+"\\"+targetPackagePath);
        if(!father.exists()){
            father.mkdirs();
        }
        File file = new File(father+"\\"+domainObjectName+"Mapping.xml");
        if(file.exists()){
//            throw new RuntimeException(domainObjectName+".xml" +" is already exist!");
        }
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(result.toString());
        fileWriter.flush();
        fileWriter.close();
    }

    private String generateDeleteByPrimaryKey(Context context, List<DbColumn> columns, String tableName) {
        String xmlJdbcType = "";
        String typeClassName = "";
        for (int i = 0; i < columns.size(); i++) {
            DbColumn column = columns.get(i);
            if (column.getName().equals("id")) {
                xmlJdbcType = column.getXmlJdbcType();
                typeClassName = column.getTypeClassName();
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("  <delete id=\"deleteByPrimaryKey\" parameterType=\"" + typeClassName + "\">");
        enter(sb);
        sb.append("    DELETE FROM " + tableName);
        enter(sb);
        sb.append("    WHERE id = #{id, jdbcType=" + xmlJdbcType + "}");
        enter(sb);
        sb.append("  </delete>");
        enter(sb);

        return sb.toString();
    }

    private String generateUpdateByPrimaryKeySelective(Context context, List<DbColumn> columns, String tableName, String domainObjectName) {
        String xmlJdbcType = "";
        String typeClassName = "";
        for (int i = 0; i < columns.size(); i++) {
            DbColumn column = columns.get(i);
            if (column.getName().equals("id")) {
                xmlJdbcType = column.getXmlJdbcType();
                typeClassName = column.getTypeClassName();
            }
        }
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = context.getJavaModelGeneratorConfiguration();
        String targetPackage = javaModelGeneratorConfiguration.getTargetPackage();
        StringBuilder sb = new StringBuilder();
        sb.append("  <update id=\"updateByPrimaryKeySelective\" parameterType=\"" + targetPackage + "." + domainObjectName + "\">");
        enter(sb);
        sb.append("    UPDATE " + tableName);
        enter(sb);
        sb.append("    <set>");
        enter(sb);
        for (DbColumn dbColumn : columns) {
            sb.append("      <if test=\"" + dbColumn.getJdbcName() + " != null\">");
            enter(sb);
            sb.append("        " + dbColumn.getName() + " = #{" + dbColumn.getJdbcName() + ", jdbcType=" + dbColumn.getXmlJdbcType() + "},");
            enter(sb);
            sb.append("      </if>");
            enter(sb);
        }
        sb.append("    </set>");
        enter(sb);
        sb.append("    WHERE id = #{id, jdbcType=" + xmlJdbcType + "}");
        enter(sb);
        sb.append("  </update>");
        enter(sb);

        return sb.toString();
    }

    private String generateInsertSelective(Context context, List<DbColumn> columns, String tableName, String domainObjectName) {
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = context.getJavaModelGeneratorConfiguration();
        String targetPackage = javaModelGeneratorConfiguration.getTargetPackage();
        StringBuilder sb = new StringBuilder();
        sb.append("  <insert id=\"insertSelective\" parameterType=\"" + targetPackage + "." + domainObjectName + "\">");
        enter(sb);
        sb.append("    insert into " + tableName);
        enter(sb);
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        sb1.append("    <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
        sb2.append("    <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
        enter(sb1);
        enter(sb2);
        for (DbColumn dbColumn : columns) {
            sb1.append("      <if test=\"" + dbColumn.getJdbcName() + " != null\">");
            enter(sb1);
            sb1.append("        " + dbColumn.getName() + ",");
            enter(sb1);
            sb1.append("      </if>");
            enter(sb1);

            sb2.append("      <if test=\"" + dbColumn.getJdbcName() + " != null\">");
            enter(sb2);
            sb2.append("        #{" + dbColumn.getJdbcName() + ", jdbcType=" + dbColumn.getXmlJdbcType() + "},");
            enter(sb2);
            sb2.append("      </if>");
            enter(sb2);
        }
        sb1.append("    </trim>");
        enter(sb1);
        sb2.append("    </trim>");
        sb.append(sb1);
        sb.append(sb2);
        enter(sb);
        sb.append("  </insert>");
        enter(sb);

        return sb.toString();
    }

    private String generateSelectByPrimaryKey(Context context, List<DbColumn> columns, String tableName) {
        String xmlJdbcType = "";
        String typeClassName = "";
        for (int i = 0; i < columns.size(); i++) {
            DbColumn column = columns.get(i);
            if (column.getName().equals("id")) {
                xmlJdbcType = column.getXmlJdbcType();
                typeClassName = column.getTypeClassName();
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("  <select id=\"selectByPrimaryKey\" resultMap=\"BaseResultMap\" parameterType=\"" + typeClassName + "\" >");
        enter(sb);
        sb.append("    SELECT *");
        enter(sb);
        sb.append("    FROM " + tableName);
        enter(sb);
        sb.append("    WHERE id = #{id, jdbcType=" + xmlJdbcType + "}");
        enter(sb);
        sb.append("  </select>");
        enter(sb);
        return sb.toString();
    }

    private String generateBaseResultMap(Context context, List<DbColumn> columns, String domainObjectName) {
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = context.getJavaModelGeneratorConfiguration();

        String baseResultMapType = javaModelGeneratorConfiguration.getTargetPackage();
        StringBuilder sb = new StringBuilder();
        sb.append("  <resultMap id=\"BaseResultMap\" type=\"" + baseResultMapType + "." + domainObjectName + "\">");
        enter(sb);

        for (int i = 0; i < columns.size(); i++) {
            DbColumn column = columns.get(i);
            if (column.getName().equals("id")) {
                sb.append("    <id column=\"id\" property=\"id\" jdbcType=\"" + column.getXmlJdbcType() + "\" />");
            } else {
                sb.append("    <result column=\"" + column.getName() + "\" property=\"" + column.getJdbcName() + "\" jdbcType=\"" + column.getXmlJdbcType() + "\" />");
            }
            enter(sb);
        }
        sb.append("  </resultMap>");
        enter(sb);
        return sb.toString();
    }

    /**
     * 换行
     */
    private void enter(StringBuilder sb) {
        sb.append(System.getProperty("line.separator"));
    }

}
