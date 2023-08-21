package com.wiflish.luban.initializr.configuration;

import com.wiflish.luban.initializr.app.filter.DependencyFileFilter;
import com.wiflish.luban.initializr.contributor.DDDProjectContributor;
import io.spring.initializr.generator.condition.ConditionalOnRequestedDependency;
import io.spring.initializr.generator.io.template.TemplateRenderer;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * @author wiflish
 * @since 2022-09-22
 */
@ProjectGenerationConfiguration
public class DDDProjectContributorConfiguration {
    private final ProjectDescription projectDescription;
    private final TemplateRenderer templateRenderer;
    @Resource
    private List<DependencyFileFilter> dependencyFileFilters;

    public DDDProjectContributorConfiguration(ProjectDescription projectDescription, TemplateRenderer templateRenderer) {
        this.projectDescription = projectDescription;
        this.templateRenderer = templateRenderer;
    }

    @Bean
    @ConditionalOnRequestedDependency("luban-ddd")
    public DDDProjectContributor dddContributor() {
        return new DDDProjectContributor(projectDescription, templateRenderer, dependencyFileFilters);
    }

}
