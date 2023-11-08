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
package com.wiflish.luban.generator.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.wiflish.luban.core.infra.po.BasePO;
import com.wiflish.luban.generator.CodeGenerator;
import com.wiflish.luban.generator.dto.GeneratorDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Objects;

@Slf4j
public class MybatisCodeGenerator implements CodeGenerator {

    @Override
    public void generate(GeneratorDTO generatorDTO) {
        String projectRootPath = generatorDTO.getOutputBasePath();
        if (StrUtil.isEmpty(projectRootPath)) {
            projectRootPath = Objects.requireNonNull(MybatisCodeGenerator.class.getClassLoader().getResource("")).getPath();
            projectRootPath = projectRootPath.substring(0, projectRootPath.indexOf("/target"));
        }

        String codePath = projectRootPath + "/src/main/java";
        String mapperPath = projectRootPath + buildMapperPath(generatorDTO);

        // 设置controller, mapperXml生成路径
        HashMap<OutputFile, String> configMap = MapUtil.newHashMap();
        configMap.put(OutputFile.xml, mapperPath);

        FastAutoGenerator.create(generatorDTO.getDbUrl(), generatorDTO.getDbUsername(), generatorDTO.getDbPassword())
                .injectionConfig(builder -> {
//                    builder.customFile()
//                    builder.customFile()

//                    CustomerFile customerFile = new CustomerFile();
                })
                .globalConfig(builder -> {
                    builder.author(generatorDTO.getAuthor()) // 设置作者
                            .commentDate("yyyy-MM-dd")
                            .enableSpringdoc()
                            .outputDir(codePath); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent(buildBaseParentPackage(generatorDTO)) // 设置父包名
                            .entity("po")
                            .mapper("dao")
                            .service("app.service")
                            .controller("app.web.controller")
                            .moduleName(generatorDTO.getBoundedContext())
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
                            .addTableFills()
                            .logicDeleteColumnName("deleted")
                            .logicDeletePropertyName("deleted")
                            .versionColumnName("version")
                            .versionPropertyName("version")
                            .formatFileName("%sPO")
                            .superClass(BasePO.class)
                            .disableSerialVersionUID()
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

    private static String buildBaseDaoPackage(GeneratorDTO generatorDTO) {
        String baseParentPackage = buildBaseParentPackage(generatorDTO);
        return baseParentPackage + ".infra";
    }

    private static String buildBaseDomainPackage(GeneratorDTO generatorDTO) {
        String baseParentPackage = buildBaseParentPackage(generatorDTO);
        return baseParentPackage + ".domain.entity";
    }

    private static String buildBaseServicePackage(GeneratorDTO generatorDTO) {
        String baseParentPackage = buildBaseParentPackage(generatorDTO);
        return baseParentPackage + ".app.service";
    }

    private static String buildBaseControllerPackage(GeneratorDTO generatorDTO) {
        String baseParentPackage = buildBaseParentPackage(generatorDTO);
        return baseParentPackage + ".app.web.controller";
    }

    private static String buildBaseParentPackage(GeneratorDTO generatorDTO) {
        String baseParentPackage = generatorDTO.getBaseParentPackage();
        String boundedContext = generatorDTO.getBoundedContext();
        if (StrUtil.isNotEmpty(boundedContext)) {
            baseParentPackage = baseParentPackage + "." + boundedContext;
        }
        return baseParentPackage;
    }

    private static String buildMapperPath(GeneratorDTO generatorDTO) {
        String mapperResourcesDir = generatorDTO.getMapperResourcesDir();
        StringBuilder resourcePath = new StringBuilder();
        resourcePath.append("/src/main/resources");
        if (StrUtil.isEmpty(mapperResourcesDir)) {
            resourcePath.append("/mapper");
        } else {
            if (mapperResourcesDir.indexOf("/") != 0) {
                mapperResourcesDir = "/" + mapperResourcesDir;
            }
            resourcePath.append(mapperResourcesDir);
        }
        return resourcePath.toString();
    }
}
