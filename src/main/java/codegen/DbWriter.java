package codegen;

import internal.entity.DbColumn;
import org.springframework.transaction.annotation.Transactional;
import parser.Configuration;
import parser.Context;
import parser.entity.JavaModelGeneratorConfiguration;
import parser.entity.SqlMapGeneratorConfiguration;
import parser.entity.TableConfiguration;

import java.beans.Transient;
import java.util.List;
import java.util.Map;

/**
 * java文件生成类（数据源从数据库中读取而来）
 */
public class DbWriter {
    private Configuration configuration;
    private GenerateXml generateXml = new GenerateXml();
    private GenerateEntity generateEntity = new GenerateEntity();
    private GenerateDao generateDao = new GenerateDao();

    public DbWriter(Configuration configuration) {
        this.configuration = configuration;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void write() throws Exception {
        Context context = configuration.getContexts().get(0);
        //生产XML文件
        generateXml.generate(context);
        //生产entity文件
        generateEntity.generate(context);
        //生产dao文件
        generateDao.generate(context);
    }



}
