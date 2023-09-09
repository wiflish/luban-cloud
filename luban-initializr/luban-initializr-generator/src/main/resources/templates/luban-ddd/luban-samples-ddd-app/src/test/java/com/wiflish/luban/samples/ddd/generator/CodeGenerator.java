/*-
 * ************
 * luban-cloud
 * ------------
 * Copyright (C) 2023 - 2023 the original author or authors.
 * ------------
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ************
 */
package com.wiflish.luban.samples.ddd.generator;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.setting.Setting;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.wiflish.luban.core.infra.po.BasePO;

import java.util.HashMap;
import java.util.List;

public class CodeGenerator {
    private static String url = "";
    private static String username = "";
    private static String password = "";

    private static String author = "";
    private static List<String> includeTableNames = ListUtil.empty();
    private static List<String> tablePrefixNames = ListUtil.empty();
    private static String parentPackage = "";

    private static void loadProperties() {
        Setting setting = new Setting("generator.properties");

        author = setting.getStr("generator.author");
        parentPackage = setting.getStr("generator.parent.package");

        url = setting.getStr("generator.db.url");
        username = setting.getStr("generator.db.username");
        password = setting.getStr("generator.db.password");
        tablePrefixNames = ListUtil.of(setting.getStrings("generator.db.include.table_prefix_names"));
        includeTableNames = ListUtil.of(setting.getStrings("generator.db.include.table_names"));
    }

    public static void generate() {
        loadProperties();

        String projectRootPath = CodeGenerator.class.getClassLoader().getResource("").getPath();
        try {
            projectRootPath = projectRootPath.substring(0, projectRootPath.indexOf("/target/test-classes/"));
        } catch (Exception e) {
            projectRootPath = projectRootPath.substring(0, projectRootPath.indexOf("/target/classes/"));
        }

        String codePath = projectRootPath + "/src/main/java";
        String resourcePath = projectRootPath + "/src/main/resources/mapper";

        // 设置controller, mapperXml生成路径
        HashMap<OutputFile, String> configMap = MapUtil.newHashMap();
        configMap.put(OutputFile.xml, resourcePath);
        configMap.put(OutputFile.controller, "/tmp"); //不要controller.
        configMap.put(OutputFile.service, "/tmp");
        configMap.put(OutputFile.serviceImpl, "/tmp");

        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author(author) // 设置作者
                            .disableOpenDir()
                            .outputDir(codePath); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent(parentPackage) // 设置父包名
                            .entity("po")
                            .mapper("mapper")
                            .pathInfo(configMap);
                })
                .strategyConfig(builder -> {
                    builder.mapperBuilder()
                            .enableFileOverride()
                            .enableBaseColumnList()
                            .enableBaseResultMap()
                            .build();
                    builder.entityBuilder()
                            .enableFileOverride()
                            .enableLombok()
                            .logicDeleteColumnName("deleted")
                            .logicDeletePropertyName("deleted")
                            .versionColumnName("version")
                            .versionPropertyName("version")
                            .formatFileName("%sPO")
                            .superClass(BasePO.class)
                            .addSuperEntityColumns("id", "create_id", "create_time", "update_id", "update_time", "version", "deleted", "feature_bit", "feature_json")
                            .build();

                    builder.serviceBuilder()
                            .enableFileOverride()
                            .build();
                    builder.controllerBuilder()
                            .enableFileOverride()
                            .enableHyphenStyle()
                            .enableRestStyle()
                            .build();
                    builder.addInclude(includeTableNames) // 设置需要生成的表名
                            .addTablePrefix(tablePrefixNames); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }

    public static void main(String[] args) {
        CodeGenerator.generate();
    }
}
