package codegen;

import internal.entity.DbColumn;
import parser.Configuration;
import parser.Context;
import parser.entity.JavaModelGeneratorConfiguration;
import parser.entity.TableConfiguration;

import java.util.List;
import java.util.Map;

/**
 * java文件生成类（数据源从数据库中读取而来）
 */
public class DbWriter {
    private  Configuration configuration;

    public DbWriter(Configuration configuration) {
        this.configuration = configuration;
    }

    public void write() throws Exception{
        Context context = configuration.getContexts().get(0);
        Map<String, List<DbColumn>> tableColumnMaps = context.getTableColumnMaps();
        for(Map.Entry<String, List<DbColumn>> entry : tableColumnMaps.entrySet()) {
            String tableName = entry.getKey();
            List<DbColumn> columns = entry.getValue();
            generateTable(context,tableName,columns);
        }
    }

    private void generateTable(Context context, String tableName, List<DbColumn> columns) {
        StringBuilder result = new StringBuilder();//总的，待生成的文件字符流
        enter(result);
        result.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >");
        enter(result);
        result.append("<mapper namespace=\"com.sunt.wzbdapi.dao.SmsInfoMapper\" >");
        enter(result);

        String baseResultMap = generateBaseResultMap(context,columns);
        String selectByPrimaryKey = generateSelectByPrimaryKey(context,columns,tableName);
        System.out.println(selectByPrimaryKey);

    }

    private String generateSelectByPrimaryKey(Context context, List<DbColumn> columns, String tableName) {
        String xmlJdbcType = "";
        String typeClassName = "";
        for(int i=0;i<columns.size();i++) {
            DbColumn column = columns.get(i);
            if (column.getName().equals("id")) {
                xmlJdbcType = column.getXmlJdbcType();
                typeClassName = column.getTypeClassName();
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("<select id=\"selectByPrimaryKey\" resultMap=\"BaseResultMap\" parameterType=\""+typeClassName+"\" >");
        enter(sb);
        sb.append("  select *");
        enter(sb);
        sb.append("  from "+tableName);
        enter(sb);
        sb.append("  where id = #{id,jdbcType="+ xmlJdbcType+"}");
        enter(sb);
        sb.append("</select>");
        enter(sb);
        return sb.toString();
    }

    private String generateBaseResultMap(Context context, List<DbColumn> columns) {
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = context.getJavaModelGeneratorConfiguration();

        String baseResultMapType = javaModelGeneratorConfiguration.getTargetPackage();
        StringBuilder sb = new StringBuilder();
        sb.append("  <resultMap id=\"BaseResultMap\" type=\"" + baseResultMapType+"\">");
        enter(sb);

        for(int i=0;i<columns.size();i++){
            DbColumn column = columns.get(i);
            if(column.getName().equals("id")){
                sb.append("    <id column=\"id\" property=\"id\" jdbcType=\""+column.getXmlJdbcType()+"\" />");
            }else{
                sb.append("    <result column=\""+column.getName()+"\" property=\"" + column.getJdbcName() + "\" jdbcType=\"" + column.getXmlJdbcType() + "\" />" );
            }
            enter(sb);
        }
        sb.append("  </resultMap>");
        return sb.toString();
    }

    /**
     * 换行
     */
    private void enter(StringBuilder sb){
        sb.append(System.getProperty("line.separator"));
    }


}
