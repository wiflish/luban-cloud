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

import com.wiflish.luban.initializr.generator.annotation.LubanProjectGenerationConfiguration;
import com.wiflish.luban.initializr.generator.annotation.condition.ConditionalOnRequestArchitecture;
import com.wiflish.luban.initializr.generator.constants.ArchitectureEnum;
import com.wiflish.luban.initializr.generator.project.LubanProjectDescription;
import com.wiflish.luban.initializr.generator.project.filter.DependencyFileFilter;
import io.spring.initializr.generator.io.template.TemplateRenderer;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * @author wiflish
 * @since 2022-09-22
 */
@LubanProjectGenerationConfiguration
public class DDDProjectContributorConfiguration {
    private final LubanProjectDescription projectDescription;
    private final TemplateRenderer templateRenderer;
    @Resource
    private List<DependencyFileFilter> dependencyFileFilters;

    public DDDProjectContributorConfiguration(LubanProjectDescription projectDescription, TemplateRenderer templateRenderer) {
        this.projectDescription = projectDescription;
        this.templateRenderer = templateRenderer;
    }

    @Bean
    @ConditionalOnRequestArchitecture(ArchitectureEnum.DDD)
    public DDDProjectContributor dddContributor() {
        return new DDDProjectContributor(projectDescription, templateRenderer, dependencyFileFilters);
    }

}
