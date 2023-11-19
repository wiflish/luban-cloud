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

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.config.builder.CustomFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.wiflish.luban.core.infra.po.BasePO;
import com.wiflish.luban.generator.CodeGenerator;
import com.wiflish.luban.generator.config.LubanGeneratorConfig;
import com.wiflish.luban.generator.dto.GeneratorDTO;
import com.wiflish.luban.generator.enums.LubanGeneratorEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
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

        String domainRootPath = projectRootPath.replace("-app", "-domain");
        String apiRootPath = projectRootPath.replace("-app", "-api");

        // 设置controller, mapperXml生成路径
        HashMap<OutputFile, String> configMap = MapUtil.newHashMap();
        configMap.put(OutputFile.xml, mapperPath);

        FastAutoGenerator.create(generatorDTO.getDbUrl(), generatorDTO.getDbUsername(), generatorDTO.getDbPassword())
                .injectionConfig(builder -> {

                    CustomFile.Builder controllerBuilder = new CustomFile.Builder();
                    controllerBuilder.enableFileOverride()
                            .templatePath("templates/ftl/Controller.java.ftl")
                            .packageName("app.controller")
                            .formatNameFunction(tableInfo -> StrUtil.upperFirst(StrUtil.toCamelCase(tableInfo.getName())))
                            .fileName("Controller.java");
                    CustomFile.Builder entityBuilder = new CustomFile.Builder();
                    entityBuilder.enableFileOverride()
                            .templatePath("templates/ftl/Entity.java.ftl")
                            .packageName("domain.entity")
                            .formatNameFunction(tableInfo -> StrUtil.upperFirst(StrUtil.toCamelCase(tableInfo.getName())))
                            .fileName(".java")
                            .filePath(buildDomainCodePath(domainRootPath, generatorDTO));
                    CustomFile.Builder queryBuilder = new CustomFile.Builder();
                    queryBuilder.enableFileOverride()
                            .templatePath("templates/ftl/Query.java.ftl")
                            .packageName("api.dto.query")
                            .formatNameFunction(tableInfo -> StrUtil.upperFirst(StrUtil.toCamelCase(tableInfo.getName())) + "Query")
                            .fileName(".java")
                            .filePath(buildApiCodePath(apiRootPath, generatorDTO));
                    CustomFile.Builder dtoBuilder = new CustomFile.Builder();
                    dtoBuilder.enableFileOverride()
                            .templatePath("templates/ftl/DTO.java.ftl")
                            .packageName("api.dto")
                            .formatNameFunction(tableInfo -> StrUtil.upperFirst(StrUtil.toCamelCase(tableInfo.getName())) + "DTO")
                            .fileName(".java")
                            .filePath(buildApiCodePath(apiRootPath, generatorDTO));
                    CustomFile.Builder cmdBuilder = new CustomFile.Builder();
                    cmdBuilder.enableFileOverride()
                            .templatePath("templates/ftl/Cmd.java.ftl")
                            .packageName("api.dto.cmd")
                            .formatNameFunction(tableInfo -> StrUtil.upperFirst(StrUtil.toCamelCase(tableInfo.getName())) + "EditCmd")
                            .fileName(".java")
                            .filePath(buildApiCodePath(apiRootPath, generatorDTO));

                    List<CustomFile> customFiles = CollectionUtil.newArrayList();
                    customFiles.add(buildCustomFile(LubanGeneratorEnum.CONTROLLER, null, generatorDTO));
                    customFiles.add(buildCustomFile(LubanGeneratorEnum.APP_SERVICE, apiRootPath, generatorDTO));
//                    customFiles.add(buildCustomFile(LubanGeneratorEnum.APP_SERVICE_IMPL, null, generatorDTO));
                    customFiles.add(buildCustomFile(LubanGeneratorEnum.DTO, apiRootPath, generatorDTO));
                    customFiles.add(buildCustomFile(LubanGeneratorEnum.Cmd, apiRootPath, generatorDTO));
                    customFiles.add(buildCustomFile(LubanGeneratorEnum.Query, apiRootPath, generatorDTO));
                    customFiles.add(buildCustomFile(LubanGeneratorEnum.CONTROLLER, null, generatorDTO));
//                    customFiles.add(buildCustomFile(LubanGeneratorEnum.Entity, domainRootPath, generatorDTO));

                    builder.customFile(customFiles)
                            .beforeOutputFile((tableInfo, map) -> {
                                String name = tableInfo.getName();

                                LubanGeneratorConfig generatorConfig = new LubanGeneratorConfig();

                                generatorConfig.setEntityName(StrUtil.upperFirst(StrUtil.toCamelCase(name)));
                                generatorConfig.setEntityNameCamel(StrUtil.toCamelCase(name));
                                generatorConfig.setMapping(buildMapping(name));

                                map.put(LUBAN_CONFIG, generatorConfig);

                            });
//                    CustomerFile customerFile = new CustomerFile();
                })
                .globalConfig(builder -> {
                    builder.author(generatorDTO.getAuthor()) // 设置作者
                            .commentDate("yyyy-MM-dd")
                            .enableSpringdoc()
                            .disableOpenDir()
                            .outputDir(codePath); // 指定输出目录
                })
                .templateConfig(builder -> {
                    builder.disable(TemplateType.CONTROLLER)
                            .disable(TemplateType.SERVICE)
                            .disable(TemplateType.SERVICE_IMPL);
                })
                .packageConfig(builder -> {
                    builder.parent(generatorDTO.getBaseParentPackage()) // 设置父包名
                            .entity("infra.po")
                            .mapper("infra.mapper")
                            .moduleName(generatorDTO.getBoundedContext())
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
                            .addTableFills()
                            .logicDeleteColumnName("deleted")
                            .versionColumnName("version")
                            .formatFileName("%sPO")
                            .superClass(BasePO.class)
                            .addSuperEntityColumns("id", "create_id", "create_time", "update_id", "update_time", "version", "deleted", "feature_bit", "feature_json")
                            .build();
                    builder.addInclude(generatorDTO.getIncludeTableNames()) // 设置需要生成的表名
                            .addTablePrefix(generatorDTO.getTablePrefixNames()); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }

    private CustomFile buildCustomFile(LubanGeneratorEnum lubanGeneratorEnum, String rootPath, GeneratorDTO generatorDTO) {
        CustomFile.Builder builder = new CustomFile.Builder();
        builder.enableFileOverride()
                .templatePath(lubanGeneratorEnum.getTemplatePath())
                .packageName(lubanGeneratorEnum.getPackageName())
                .formatNameFunction(tableInfo -> StrUtil.upperFirst(StrUtil.toCamelCase(tableInfo.getName())))
                .fileName(lubanGeneratorEnum.getType());
        if (lubanGeneratorEnum.getFilePathFunction() != null) {
            builder.filePath(lubanGeneratorEnum.getFilePathFunction().apply(rootPath, generatorDTO));
        }

        return builder.build();
    }

    private String buildApiCodePath(String apiRootPath, GeneratorDTO generatorDTO) {
        StringBuilder resourcePath = new StringBuilder();
        resourcePath.append(apiRootPath);
        resourcePath.append("/src/main/java");
        resourcePath.append("/").append(generatorDTO.getBaseParentPackage().replace(".", "/"));
        if (StrUtil.isNotEmpty(generatorDTO.getBoundedContext())) {
            resourcePath.append("/").append(generatorDTO.getBoundedContext());
        }
        return resourcePath.toString();
    }

    private String buildDomainCodePath(String domainRootPath, GeneratorDTO generatorDTO) {
        StringBuilder resourcePath = new StringBuilder();
        resourcePath.append(domainRootPath);
        resourcePath.append("/src/main/java");
        resourcePath.append("/").append(generatorDTO.getBaseParentPackage().replace(".", "/"));
        if (StrUtil.isNotEmpty(generatorDTO.getBoundedContext())) {
            resourcePath.append("/").append(generatorDTO.getBoundedContext());
        }
        return resourcePath.toString();
    }

    private String buildMapping(String name) {
        String result = StrUtil.replace(name, "_", "/");
        if (!result.endsWith("s")) {
            result += "s";
        }
        result = "/" + result;
        return result.toLowerCase();
    }

    private static String buildMapperPath(GeneratorDTO generatorDTO) {
        StringBuilder resourcePath = new StringBuilder();
        resourcePath.append("/src/main/resources/mapper");

        String boundedContext = generatorDTO.getBoundedContext();
        if (StrUtil.isNotEmpty(boundedContext)) {
            resourcePath.append("/").append(boundedContext);
        }

        return resourcePath.toString();
    }
}
