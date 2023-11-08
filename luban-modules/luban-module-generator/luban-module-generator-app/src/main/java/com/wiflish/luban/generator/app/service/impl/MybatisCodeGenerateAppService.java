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
package com.wiflish.luban.generator.app.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.wiflish.luban.core.infra.po.BasePO;
import com.wiflish.luban.generator.api.dto.GeneratorDTO;
import com.wiflish.luban.generator.api.service.CodeGenerateAppService;

import java.util.HashMap;

public class MybatisCodeGenerateAppService implements CodeGenerateAppService {
    private static final String defaultPathTemplate = "/tmp/.code_gen/%s";

    @Override
    public void generate(GeneratorDTO generatorDTO) {
        String projectRootPath = generatorDTO.getOutputBasePath();
        if (StrUtil.isEmpty(projectRootPath)) {
            projectRootPath = String.format(defaultPathTemplate, RandomUtil.randomNumbers(20));
        }

        String codePath = projectRootPath + "/src/main/java";
        String resourcePath = projectRootPath + "/src/main/resources/mapper";

        // 设置controller, mapperXml生成路径
        HashMap<OutputFile, String> configMap = MapUtil.newHashMap();
        configMap.put(OutputFile.xml, resourcePath);
        configMap.put(OutputFile.controller, "/tmp"); //不要controller.
        configMap.put(OutputFile.service, "/tmp");
        configMap.put(OutputFile.serviceImpl, "/tmp");

        FastAutoGenerator.create(generatorDTO.getDbUrl(), generatorDTO.getDbUsername(), generatorDTO.getDbPassword())
                .injectionConfig(builder -> {
//                    builder.customFile()
                })
                .globalConfig(builder -> {
                    builder.author(generatorDTO.getAuthor()) // 设置作者
                            .outputDir(codePath); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent(generatorDTO.getParentPackage()) // 设置父包名
                            .entity("po")
                            .mapper("dao")
                            .pathInfo(configMap);
                })
                .strategyConfig(builder -> {
                    builder.mapperBuilder()
                            .enableFileOverride()
                            .enableBaseColumnList()
                            .enableBaseResultMap()
                            .formatMapperFileName("%sDao")
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
                    builder.addInclude(generatorDTO.getIncludeTableNames()) // 设置需要生成的表名
                            .addTablePrefix(generatorDTO.getTablePrefixNames()); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
