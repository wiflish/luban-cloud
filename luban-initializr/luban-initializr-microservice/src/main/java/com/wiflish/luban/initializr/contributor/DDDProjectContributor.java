package com.wiflish.luban.initializr.contributor;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.wiflish.luban.initializr.app.filter.DependencyFileFilter;
import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.io.template.TemplateRenderer;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.contributor.ProjectContributor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author wiflish
 * @since 2022-09-22
 */
@Slf4j
public class DDDProjectContributor implements ProjectContributor {
    private static final String fileSeparator = File.separator;
    private static final String dddModel = "ddd";
    private static final String rootResource = "classpath:templates/ddd";
    private static final List<String> subModels = ListUtil.of("ddd-api", "ddd-app", "ddd-dist", "ddd-domain", "ddd-infra");
    private static final String javaSourceDir = "src/main/java";
    private static final String javaTestSourceDir = "src/test/java";

    private static final String filePathOrNameReplace = "{";

    private final ProjectDescription description;
    private final PathMatchingResourcePatternResolver resolver;
    private final TemplateRenderer templateRenderer;

    private final List<DependencyFileFilter> dependencyFileFilters;

    private Map<String, Object> paramMap;

    public DDDProjectContributor(ProjectDescription description, TemplateRenderer templateRenderer,
                                 List<DependencyFileFilter> dependencyFileFilters) {
        this.templateRenderer = templateRenderer;
        this.description = description;
        this.resolver = new PathMatchingResourcePatternResolver();
        this.paramMap = MapUtil.newHashMap();
        this.dependencyFileFilters = dependencyFileFilters;

        initParamMap(description);
    }

    private void initParamMap(ProjectDescription description) {
        this.paramMap.put("name", description.getName());
        this.paramMap.put("applicationName", description.getApplicationName());
        this.paramMap.put("groupId", description.getGroupId());
        this.paramMap.put("artifactId", description.getArtifactId());
        this.paramMap.put("version", description.getVersion());
        this.paramMap.put("packageName", description.getPackageName());
        this.paramMap.put("currentDate", LocalDateTimeUtil.format(LocalDateTime.now(), "yyyy-MM-dd"));
        this.paramMap.put("description", description.getDescription());

        //FIXME 需要作为参数传进来.
        this.paramMap.put("logRootDir", "/data/logs/java");
        this.paramMap.put("port", "8089");
        this.paramMap.put("dbName", "demo");
        this.paramMap.put("dbUsername", "root");
        this.paramMap.put("dbPassword", "root");


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

        //处理子模块.
        subModels.stream().forEach(sourceModelName -> {
            //子模块下所有文件.
            try {
                Resource[] resources = this.resolver.getResources(rootResource + fileSeparator + sourceModelName + "/**");
                for (Resource resource : resources) {
                    generateFile(resource, projectRoot);
                }
            } catch (IOException e) {
                log.warn("代码生成过程处理失败. subModel: {}", sourceModelName, e);
            }
        });
    }

    private void generateFile(Resource resource, Path projectRoot) throws IOException {
        if (!resource.isReadable()) {
            return;
        }
        String path = resource.getURI().getPath();
        int dddModelIdx = path.indexOf(dddModel);
        if (dddModelIdx < 0) {
            return;
        }
        String dddModelPathStr = path.substring(dddModelIdx);
        Path dddModelTargetRootPath = projectRoot.resolve(description.getArtifactId());

        int mustacheIdx = dddModelPathStr.lastIndexOf(".mustache");
        if (mustacheIdx != -1) {
            //mustache模板文件.
            String template = dddModelPathStr.substring(0, mustacheIdx);
            String targetPathStr = dddModelPathStr.substring(0, mustacheIdx).substring(dddModel.length() + 1);
            targetPathStr = targetPathStr.replace(dddModel, description.getArtifactId());
            Path targetPath = dddModelTargetRootPath.resolve(targetPathStr);

            if (targetPathStr.indexOf(".java") != -1) {
                generateJavaFile(template, targetPath);
            } else {
                generateFile(template, targetPath);
            }
        } else {
            //不是模板文件，直接复制.
            String targetPathStr = dddModelPathStr.substring(dddModel.length() + 1);
            targetPathStr = targetPathStr.replace(dddModel, description.getArtifactId());
            Path targetPath = dddModelTargetRootPath.resolve(targetPathStr);

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

        if (javaFile.indexOf(filePathOrNameReplace) != -1) {
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
        Files.write(outputPath, fileContent.getBytes(StandardCharsets.UTF_8));
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
