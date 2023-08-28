package com.wiflish.luban.initializr.generator.project;

import com.wiflish.luban.initializr.generator.controller.LubanProjectGenerationController;
import io.spring.initializr.metadata.InitializrMetadataProvider;
import io.spring.initializr.web.project.ProjectGenerationInvoker;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author wiflish
 * @since 2023-08-25 17:46:18
 */
@Configuration
public class LubanProjectGenerationConfiguration {
    @Bean
    public LubanProjectGenerationController projectGenerationController(InitializrMetadataProvider metadataProvider,
                                                                             ApplicationContext applicationContext) {
        ProjectGenerationInvoker<LubanProjectRequest> projectGenerationInvoker = new LubanProjectGenerationInvoker(
                applicationContext, new LubanProjectRequestToDescriptionConverter());
        return new LubanProjectGenerationController(metadataProvider, projectGenerationInvoker);
    }
}