package codegen;

import internal.entity.DbColumn;
import parser.Context;
import parser.entity.JavaModelGeneratorConfiguration;
import parser.entity.SqlMapGeneratorConfiguration;
import parser.entity.TableConfiguration;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;

public class GenerateDao {
    public void generate(Context context) throws Exception {
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

            generateDao(context, tableName, domainObjectName, columns);
        }
    }

    private void generateDao(Context context, String tableName, String domainObjectName, List<DbColumn> columns) throws Exception {
        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = context.getSqlMapGeneratorConfiguration();
        StringBuilder result = new StringBuilder();

        result.append("package " + sqlMapGeneratorConfiguration.getTargetPackage() + ";");
        enter(result);
        enter(result);

        StringBuilder importSb = new StringBuilder();
        StringBuilder contentSb = new StringBuilder();

        Object idType = null;
        for(DbColumn dbColumn : columns) {
            if(importSb.indexOf(dbColumn.getTypeClassName())==-1){
                importSb.append("import "+dbColumn.getTypeClassName()+";");
                enter(importSb);
            }
            if(dbColumn.getJdbcName().equals("id")){
                idType = dbColumn.getJavaJdbcType();
            }
        }
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = context.getJavaModelGeneratorConfiguration();
        importSb.append("import "+javaModelGeneratorConfiguration.getTargetPackage()+"."+domainObjectName+";");
        enter(importSb);

        contentSb.append("  "+domainObjectName+ " selectByPrimaryKey(Long id);");
        enter(contentSb);
        enter(contentSb);

        contentSb.append("  int deleteByPrimaryKey("+idType+" id);");
        enter(contentSb);
        enter(contentSb);

        contentSb.append("  int updateByPrimaryKey("+domainObjectName+" record);");
        enter(contentSb);
        enter(contentSb);

        contentSb.append("  int insertSelective("+domainObjectName+" record);");

        result.append(importSb);
        enter(result);

        result.append("public interface "+domainObjectName+"Dao {");
        enter(result);

        result.append(contentSb);
        enter(result);

        result.append("}");
        enter(result);

        SqlMapGeneratorConfiguration target = context.getSqlMapGeneratorConfiguration();
        String targetProject = target.getTargetProject();
        String targetPackage  = target.getTargetPackage();
        String targetPackagePath = targetPackage.replace(".","/");

        File father = new File(targetProject+"\\"+targetPackagePath);
        if(!father.exists()){
            father.mkdirs();
        }
        File file = new File(father+"\\"+domainObjectName+"Dao.java");
        if(file.exists()){
//            throw new RuntimeException(domainObjectName+".java" +" is already exist!");
        }
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(result.toString());
        fileWriter.flush();
        fileWriter.close();
    }

    /**
     * 换行
     */
    private void enter(StringBuilder sb) {
        sb.append(System.getProperty("line.separator"));
    }
}
