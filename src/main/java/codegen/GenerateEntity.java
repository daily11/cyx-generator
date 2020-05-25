package codegen;

import internal.entity.DbColumn;
import org.springframework.util.StringUtils;
import parser.Context;
import parser.entity.JavaClientGeneratorConfiguration;
import parser.entity.JavaModelGeneratorConfiguration;
import parser.entity.TableConfiguration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GenerateEntity {
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

            generateEntity(context, tableName, domainObjectName, columns);
        }
    }

    private void generateEntity(Context context, String tableName, String domainObjectName, List<DbColumn> columns) throws Exception {
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = context.getJavaModelGeneratorConfiguration();
        StringBuilder result = new StringBuilder();

        result.append("package "+javaModelGeneratorConfiguration.getTargetPackage()+";");
        enter(result);
        enter(result);

        StringBuilder importSb = new StringBuilder();
        StringBuilder fieldSb = new StringBuilder();
        StringBuilder getterSetterSb = new StringBuilder();

        importSb.append("import java.io.Serializable;");
        enter(importSb);
        for(DbColumn dbColumn : columns) {
            if(!StringUtils.isEmpty(dbColumn.getRemark())){
                fieldSb.append("  /**");
                enter(fieldSb);
                fieldSb.append("  *  "+dbColumn.getRemark());
                enter(fieldSb);
                fieldSb.append("  */");
                enter(fieldSb);
            }
            fieldSb.append("  private "+dbColumn.getJavaJdbcType()+" "+dbColumn.getJdbcName()+";");
            enter(fieldSb);
            getterSetterSb.append("  public void set"+getFieldName(dbColumn.getJdbcName())+"("+dbColumn.getJavaJdbcType()+" "+dbColumn.getJdbcName()+") {this."+dbColumn.getJdbcName()+" = "+dbColumn.getJdbcName()+"; }");
            enter(getterSetterSb);
            getterSetterSb.append("  public "+dbColumn.getJavaJdbcType()+" get"+getFieldName(dbColumn.getJdbcName())+"() { return "+dbColumn.getJdbcName()+";}");
            enter(getterSetterSb);
            enter(getterSetterSb);
            if(importSb.indexOf(dbColumn.getTypeClassName())==-1){
                importSb.append("import "+dbColumn.getTypeClassName()+";");
                enter(importSb);
            }
        }

        result.append(importSb);
        enter(result);

        result.append("public class " + domainObjectName + " implements Serializable {");
        enter(result);

        result.append(fieldSb);
        enter(result);

        result.append(getterSetterSb);
        enter(result);

        result.append("}");
//        System.out.println(result);

        JavaModelGeneratorConfiguration target = context.getJavaModelGeneratorConfiguration();
        String targetProject = target.getTargetProject();
        String targetPackage  = target.getTargetPackage();
        String targetPackagePath = targetPackage.replace(".","/");

        File father = new File(targetProject+"\\"+targetPackagePath);
        if(!father.exists()){
            father.mkdirs();
        }
        File file = new File(father+"\\"+domainObjectName+".java");
        if(file.exists()){
//            throw new RuntimeException(domainObjectName+".java" +" is already exist!");
        }
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(result.toString());
        fileWriter.flush();
        fileWriter.close();
    }

    private String getFieldName(String fieldName) {
        return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    /**
     * 换行
     */
    private void enter(StringBuilder sb) {
        sb.append(System.getProperty("line.separator"));
    }

}
