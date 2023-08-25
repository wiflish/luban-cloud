
package com.wiflish.luban.initializr.generator.project;

import com.wiflish.luban.initializr.generator.annotation.LubanProjectGenerationConfiguration;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.ProjectGenerationContext;
import io.spring.initializr.generator.project.ProjectGenerator;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Luban project generator
 *
 * @author wiflish
 * @since 2023-08-25 15:56:13
 */
public class LubanProjectGenerator extends ProjectGenerator {
    public LubanProjectGenerator(Consumer<ProjectGenerationContext> contextConsumer, Supplier<? extends ProjectGenerationContext> contextFactory) {
        super(contextConsumer, contextFactory);
    }

    public LubanProjectGenerator(Consumer<ProjectGenerationContext> contextConsumer) {
        super(contextConsumer);
    }

    @Override
    protected List<String> getCandidateProjectGenerationConfigurations(ProjectDescription description) {
        return SpringFactoriesLoader.loadFactoryNames(LubanProjectGenerationConfiguration.class, getClass().getClassLoader());
    }
}
