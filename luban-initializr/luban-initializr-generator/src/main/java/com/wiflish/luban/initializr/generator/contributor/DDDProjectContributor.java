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
package com.wiflish.luban.initializr.generator.contributor;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.wiflish.luban.initializr.generator.constants.ArchitectureEnum;
import com.wiflish.luban.initializr.generator.project.LubanProjectDescription;
import com.wiflish.luban.initializr.generator.project.filter.DependencyFileFilter;
import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.io.template.TemplateRenderer;
import io.spring.initializr.generator.project.contributor.ProjectContributor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author wiflish
 * @since 2022-09-22
 */
@Slf4j
public class DDDProjectContributor implements ProjectContributor {
    private static final String fileSeparator = File.separator;
    private static final String rootResource = "classpath:templates/ddd";
    private static final String javaSourceDir = "src/main/java";
    private static final String javaTestSourceDir = "src/test/java";

    private static final String filePathOrNameReplace = "{";

    private final LubanProjectDescription description;
    private final PathMatchingResourcePatternResolver resolver;
    private final TemplateRenderer templateRenderer;

    private final List<DependencyFileFilter> dependencyFileFilters;

    private final Map<String, Object> paramMap;

    public DDDProjectContributor(LubanProjectDescription description, TemplateRenderer templateRenderer,
                                 List<DependencyFileFilter> dependencyFileFilters) {
        this.templateRenderer = templateRenderer;
        this.description = description;
        this.resolver = new PathMatchingResourcePatternResolver();
        this.paramMap = MapUtil.newHashMap();
        this.dependencyFileFilters = dependencyFileFilters;

        initParamMap(description);
    }

    private void initParamMap(LubanProjectDescription description) {
        this.paramMap.put("name", description.getName());
        this.paramMap.put("applicationName", description.getApplicationName());
        this.paramMap.put("groupId", description.getGroupId());
        this.paramMap.put("artifactId", description.getArtifactId());
        this.paramMap.put("version", description.getVersion());
        this.paramMap.put("packageName", description.getPackageName());
        this.paramMap.put("currentDate", LocalDateTimeUtil.format(LocalDateTime.now(), "yyyy-MM-dd"));
        this.paramMap.put("description", description.getDescription());
        this.paramMap.put("author", Optional.ofNullable(description.getAuthor()).orElse("wiflish"));
        this.paramMap.put("port", Optional.ofNullable(description.getPort()).orElse("9001"));

        //FIXME 需要作为参数传进来.
//        this.paramMap.put("logRootDir", "/data/logs/java");
//        this.paramMap.put("dbName", "demo");
//        this.paramMap.put("dbUsername", "root");
//        this.paramMap.put("dbPassword", "root");


        Map<String, Dependency> requestedDependencies = description.getRequestedDependencies();
        if (MapUtil.isEmpty(requestedDependencies)) {
            return;
        }
        for (String id : requestedDependencies.keySet()) {
            this.paramMap.put(id, true);
        }
    }

    @Override
    public void contribute(Path projectRoot) throws IOException {
        //pom and README.md
        Resource[] rootFiles = this.resolver.getResources(rootResource + "/*.*");
        for (Resource resource : rootFiles) {
            generateFile(resource, projectRoot);
        }

        //process sub modules.
        Resource[] subModelResources = this.resolver.getResources(rootResource + "/*");

        for (Resource subModule : subModelResources) {
            if (!subModule.getFile().isDirectory()) {
                continue;
            }
            //子模块下所有文件.
            try {
                Resource[] resources = this.resolver.getResources(rootResource + "/" + subModule.getFilename() + "/**");
                for (Resource resource : resources) {
                    generateFile(resource, projectRoot);
                }
            } catch (IOException e) {
                log.warn("代码生成过程处理失败. subModel: {}", subModule.getFilename(), e);
            }
        }
    }

    private void generateFile(Resource resource, Path projectRoot) throws IOException {
        if (!resource.isReadable()) {
            return;
        }
        String ddd = ArchitectureEnum.DDD.getId();
        String path = resource.getURI().getPath();
        int dddModelIdx = path.indexOf(ddd);
        if (dddModelIdx < 0) {
            return;
        }
        String dddModelPathStr = path.substring(dddModelIdx);
        int mustacheIdx = dddModelPathStr.lastIndexOf(".mustache");
        if (mustacheIdx != -1) {
            //mustache模板文件.
            String template = dddModelPathStr.substring(0, mustacheIdx);
            String targetPathStr = dddModelPathStr.substring(0, mustacheIdx).substring(ddd.length() + 1);
            targetPathStr = targetPathStr.replace(ddd, description.getArtifactId());
            Path targetPath = projectRoot.resolve(targetPathStr);

            if (targetPathStr.lastIndexOf(".java") != -1) {
                generateJavaFile(template, targetPath);
            } else {
                generateFile(template, targetPath);
            }
        } else {
            //不是模板文件，直接复制.
            String targetPathStr = dddModelPathStr.substring(ddd.length() + 1);
            targetPathStr = targetPathStr.replace(ddd, description.getArtifactId());
            Path targetPath = projectRoot.resolve(targetPathStr);

            Files.createDirectories(targetPath.getParent());
            Files.copy(resource.getInputStream(), targetPath);
        }
    }

    private void generateJavaFile(String template, Path targetPath) throws IOException {
        String parent = targetPath.getParent().toString();
        int idx = parent.indexOf(javaSourceDir);
        if (idx == -1) {
            idx = parent.indexOf(javaTestSourceDir);
        }

        String javaRoot = parent.substring(0, idx + javaSourceDir.length());
        String javaFile = targetPath.toString().substring(idx + javaSourceDir.length());

        if (javaFile.contains(filePathOrNameReplace)) {
            javaFile = StrUtil.format(javaFile, paramMap);
        }

        String targetPathStr = javaRoot + fileSeparator + convertPackage2Path() + javaFile;
        Path path = Paths.get(targetPathStr);

        generateFile(template, path);
    }

    private void generateFile(String template, Path outputPath) throws IOException {
        //处理文件过滤.
        Map<String, Dependency> requestedDependencies = description.getRequestedDependencies();
        if (!CollectionUtil.isEmpty(this.dependencyFileFilters)) {
            for (DependencyFileFilter dependencyFileFilter : this.dependencyFileFilters) {
                boolean filtered = dependencyFileFilter.filtered(outputPath.getFileName(), requestedDependencies);
                if (filtered) {
                    return;
                }
            }
        }

        String fileContent = templateRenderer.render(template, this.paramMap);

        Files.createDirectories(outputPath.getParent());
        Files.writeString(outputPath, fileContent);
    }

    private String convertPackage2Path() {
        String packageName = description.getPackageName();

        return packageName.replaceAll("\\.", fileSeparator);
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}
