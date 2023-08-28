package com.wiflish.luban.initializr.generator.project;

import com.wiflish.luban.initializr.generator.constants.ArchitectureEnum;
import io.spring.initializr.generator.buildsystem.BuildItemResolver;
import io.spring.initializr.generator.project.*;
import io.spring.initializr.metadata.InitializrMetadata;
import io.spring.initializr.metadata.InitializrMetadataProvider;
import io.spring.initializr.metadata.support.MetadataBuildItemResolver;
import io.spring.initializr.web.project.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.reflect.Constructor;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wiflish
 * @since 2023-08-25 14:37:39
 */
public class LubanProjectGenerationInvoker extends ProjectGenerationInvoker<LubanProjectRequest> {
    private final ApplicationContext parentApplicationContext;

    private final ApplicationEventPublisher eventPublisher;

    private final ProjectRequestToDescriptionConverter<LubanProjectRequest> requestConverter;
    private final ProjectAssetGenerator<Path> projectAssetGenerator = new DefaultProjectAssetGenerator();
    private final transient Map<Path, List<Path>> temporaryFiles = new ConcurrentHashMap<>();

    public LubanProjectGenerationInvoker(ApplicationContext parentApplicationContext, ProjectRequestToDescriptionConverter<LubanProjectRequest> requestConverter) {
        this(parentApplicationContext, parentApplicationContext, requestConverter);
    }

    protected LubanProjectGenerationInvoker(ApplicationContext parentApplicationContext, ApplicationEventPublisher eventPublisher, ProjectRequestToDescriptionConverter<LubanProjectRequest> requestConverter) {
        super(parentApplicationContext, eventPublisher, requestConverter);
        this.parentApplicationContext = parentApplicationContext;
        this.eventPublisher = eventPublisher;
        this.requestConverter = requestConverter;
    }

    @Override
    public ProjectGenerationResult invokeProjectStructureGeneration(LubanProjectRequest request) {
        InitializrMetadata metadata = this.parentApplicationContext.getBean(InitializrMetadataProvider.class).get();
        try {
            ProjectDescription description = this.requestConverter.convert(request, metadata);

            String architecture = request.getArchitecture();
            ArchitectureEnum architectureEnum = ArchitectureEnum.getArchitecture(architecture);

            ProjectGenerator projectGenerator;
            if (Objects.requireNonNull(architectureEnum) == ArchitectureEnum.DDD) {
                projectGenerator = new LubanProjectGenerator((
                        projectGenerationContext) -> customizeProjectGenerationContext(projectGenerationContext, metadata));
            } else {
                projectGenerator = new ProjectGenerator((
                        projectGenerationContext) -> customizeProjectGenerationContext(projectGenerationContext, metadata));
            }

            ProjectGenerationResult result = projectGenerator.generate(description, generateProject(description, request));
            addTempFile(result.getRootDirectory(), result.getRootDirectory());

            return result;
        } catch (ProjectGenerationException ex) {
            publishProjectFailedEvent(request, metadata, ex);
            throw ex;
        }
    }

    private ProjectAssetGenerator<ProjectGenerationResult> generateProject(ProjectDescription description, LubanProjectRequest request) {
        return (context) -> {
            Path projectDir = getProjectAssetGenerator(description).generate(context);
            publishProjectGeneratedEvent(request, context);

            try {
                Constructor<ProjectGenerationResult> constructor = ProjectGenerationResult.class.getDeclaredConstructor(ProjectDescription.class, Path.class);
                constructor.setAccessible(true);

                return constructor.newInstance(context.getBean(ProjectDescription.class), projectDir);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Override
    protected ProjectAssetGenerator<Path> getProjectAssetGenerator(ProjectDescription description) {
        return this.projectAssetGenerator;
    }

    private void customizeProjectGenerationContext(AnnotationConfigApplicationContext context, InitializrMetadata metadata) {
        context.setParent(this.parentApplicationContext);
        context.registerBean(InitializrMetadata.class, () -> metadata);
        context.registerBean(BuildItemResolver.class, () -> new MetadataBuildItemResolver(metadata, context.getBean(ProjectDescription.class).getPlatformVersion()));
        context.registerBean(MetadataProjectDescriptionCustomizer.class, () -> new MetadataProjectDescriptionCustomizer(metadata));
    }

    private void addTempFile(Path group, Path file) {
        this.temporaryFiles.compute(group, (path, paths) -> {
            List<Path> newPaths = (paths != null) ? paths : new ArrayList<>();
            newPaths.add(file);
            return newPaths;
        });
    }

    private void publishProjectGeneratedEvent(LubanProjectRequest request, ProjectGenerationContext context) {
        InitializrMetadata metadata = context.getBean(InitializrMetadata.class);
        ProjectGeneratedEvent event = new ProjectGeneratedEvent(request, metadata);
        this.eventPublisher.publishEvent(event);
    }

    private void publishProjectFailedEvent(LubanProjectRequest request, InitializrMetadata metadata, Exception cause) {
        ProjectFailedEvent event = new ProjectFailedEvent(request, metadata, cause);
        this.eventPublisher.publishEvent(event);
    }
}
