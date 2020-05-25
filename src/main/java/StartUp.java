import codegen.DbWriter;
import internal.DbReader;
import parser.Configuration;
import parser.ConfigurationParser;

import java.io.File;

public class StartUp {
    public static void main(String[] args) throws Exception {
        //读取XML文件
        File configFile = new File(StartUp.class.getResource("/generator.xml").toURI());

        //解析XML文件
        ConfigurationParser cp = new ConfigurationParser();
        Configuration configuration = cp.parseConfiguration(configFile);

        //读取数据库属性
        DbReader dbReader = new DbReader(configuration);
        dbReader.read();

        //生成相应文档
        DbWriter dbWriter = new DbWriter(configuration);
        dbWriter.write();

        System.out.println();

    }
}
