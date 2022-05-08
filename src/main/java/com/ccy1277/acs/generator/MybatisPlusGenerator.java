package com.ccy1277.acs.generator;

import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * MybatisPlus代码生成器
 * created by ccy on 2022/5/8
 */

public class MybatisPlusGenerator {
    public static void main(String[] args) {
        // 获得程序当前路径
        String projectPath = System.getProperty("user.dir");
        String[] tableNames = scanner("表名\n(支持两种模式：" +
                "1：多张表请用逗号隔开 2：模糊匹配请首先输入*):").split(",");
        // 代码生成器
        AutoGenerator autoGenerator = new AutoGenerator();
        autoGenerator.setGlobalConfig(initGlobalConfig(projectPath));
        autoGenerator.setDataSource(setDataSourceConfig());
        autoGenerator.setPackageInfo(initPackageConfig());
        autoGenerator.setCfg(initInjectionConfig(projectPath));
        autoGenerator.setTemplate(initTemplateConfig());
        autoGenerator.setStrategy(initStrategyConfig(tableNames));

        autoGenerator.setTemplateEngine(new VelocityTemplateEngine());

        autoGenerator.execute();
    }

    /**
     * 读取控制台输入
     */
    private static String scanner(String tip){
        Scanner input = new Scanner(System.in);
        System.out.println(("请输入" + tip));

        if (input.hasNext()) {
            String next = input.next();
            if (StrUtil.isNotEmpty(next)) {
                return next;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    /**
     * 初始化全局配置
     */
    private static GlobalConfig initGlobalConfig(String path){
        GlobalConfig globalConfig = new GlobalConfig();
        // 生成文件的输出目录
        globalConfig.setOutputDir(path + "/src/main/java");
        // 开发人员
        globalConfig.setAuthor("ccy1277");
        // 是否打开输出目录
        globalConfig.setOpen(false);
        // 开启 swagger2 模式
        globalConfig.setSwagger2(true);
        // 开启 BaseResultMap
        globalConfig.setBaseResultMap(true);
        // 是否覆盖已有文件
        globalConfig.setFileOverride(true);
        // 时间类型
        globalConfig.setDateType(DateType.ONLY_DATE);
        // %s为占位符 实体命名方式
        globalConfig.setEntityName("%s");
        globalConfig.setMapperName("%sMapper");
        globalConfig.setXmlName("%sMapper");
        globalConfig.setServiceName("%sService");
        globalConfig.setServiceImplName("%sServiceImpl");
        globalConfig.setControllerName("%sController");

        return globalConfig;
    }

    /**
     * 包名配置
     */
    private static PackageConfig initPackageConfig(){
        Props props = new Props("generator.properties");

        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent(props.getStr("package.base"));
        packageConfig.setEntity("model");
        return packageConfig;
    }

    /**
     * 数据源配置
     */
    private static DataSourceConfig setDataSourceConfig(){
        // 读取generator.properties
        Props props = new Props("generator.properties");
        DataSourceConfig dataSourceConfig = new DataSourceConfig();

        dataSourceConfig.setUrl(props.getStr("url"));
        dataSourceConfig.setDriverName(props.getStr("driverName"));
        dataSourceConfig.setUsername(props.getStr("username"));
        dataSourceConfig.setPassword(props.getStr("password"));

        return dataSourceConfig;
    }

    /**
     * 模板设置
     */
    private static TemplateConfig initTemplateConfig(){
        return new TemplateConfig().setXml(null);
    }

    /**
     * 策略配置
     */
    private static StrategyConfig initStrategyConfig(String[] tableNames){
        StrategyConfig strategyConfig = new StrategyConfig();
        // 数据库表映射到实体的命名策略 默认下划线转驼峰命名:NamingStrategy.underline_to_camel
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        // 【实体】是否为 lombok 模型（默认 false）
        strategyConfig.setEntityLombokModel(true);
        // 生成 @RestController 控制器
        strategyConfig.setRestControllerStyle(true);
        // 需要包含的表名
        if(tableNames.length == 1 && tableNames[0].charAt(0) == '*'){
            String prefix = tableNames[0].substring(1);
            strategyConfig.setLikeTable(new LikeTable(prefix));
        }else{
            strategyConfig.setInclude(tableNames);

        }

        return strategyConfig;
    }

    /**
     * 自定义配置
     */
    private static InjectionConfig initInjectionConfig(String projectPath){
        // 自定义配置
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                // 注入自定义 Map 对象(注意需要 setMap 放进去) 可以在模板中使用
            }
        };
        // Velocity模板引擎
        String templatePath = "/templates/mapper.xml.vm";
        // 自定义输出配置
        List<FileOutConfig> fileOutConfigList = new ArrayList<>();
        // 自定义输出配置优先级最高
        fileOutConfigList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                System.out.println(tableInfo.getEntityName());
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mappers/" +
                        tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        injectionConfig.setFileOutConfigList(fileOutConfigList);
        return injectionConfig;
    }
}
