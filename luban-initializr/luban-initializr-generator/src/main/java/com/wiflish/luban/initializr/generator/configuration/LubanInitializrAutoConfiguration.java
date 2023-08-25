///*
// * Copyright 2012-2023 the original author or authors.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      https://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package com.wiflish.luban.initializr.generator.configuration;
//
//import com.wiflish.luban.initializr.generator.controller.LubanProjectGenerationController;
//import com.wiflish.luban.initializr.generator.project.LubanProjectGenerationInvoker;
//import io.spring.initializr.generator.io.IndentingWriterFactory;
//import io.spring.initializr.generator.io.SimpleIndentStrategy;
//import io.spring.initializr.generator.io.template.MustacheTemplateRenderer;
//import io.spring.initializr.generator.io.template.TemplateRenderer;
//import io.spring.initializr.generator.project.MutableProjectDescription;
//import io.spring.initializr.generator.project.ProjectDescription;
//import io.spring.initializr.generator.project.ProjectDirectoryFactory;
//import io.spring.initializr.metadata.*;
//import io.spring.initializr.web.autoconfigure.InitializrWebConfig;
//import io.spring.initializr.web.controller.CommandLineMetadataController;
//import io.spring.initializr.web.controller.ProjectGenerationController;
//import io.spring.initializr.web.controller.ProjectMetadataController;
//import io.spring.initializr.web.controller.SpringCliDistributionController;
//import io.spring.initializr.web.project.*;
//import io.spring.initializr.web.support.DefaultDependencyMetadataProvider;
//import io.spring.initializr.web.support.DefaultInitializrMetadataProvider;
//import io.spring.initializr.web.support.InitializrMetadataUpdateStrategy;
//import org.springframework.beans.factory.ObjectProvider;
//import org.springframework.boot.autoconfigure.AutoConfiguration;
//import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
//import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
//import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.boot.context.properties.bind.Binder;
//import org.springframework.cache.Cache;
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.support.NoOpCache;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.core.env.Environment;
//
//import javax.cache.configuration.MutableConfiguration;
//import javax.cache.expiry.CreatedExpiryPolicy;
//import javax.cache.expiry.Duration;
//import java.nio.file.Files;
//import java.util.function.Supplier;
//import java.util.stream.StreamSupport;
//
///**
// * @author wiflish
// * @since 2023-08-25 17:06:04
// */
//@AutoConfiguration(after = {JacksonAutoConfiguration.class, RestTemplateAutoConfiguration.class})
//@EnableConfigurationProperties(InitializrProperties.class)
//public class LubanInitializrAutoConfiguration {
//
//    @Bean
//    public ProjectDescription projectDescription() {
//        return new MutableProjectDescription();
//    }
//
//    @Bean
//    @ConditionalOnMissingBean
//    public ProjectDirectoryFactory projectDirectoryFactory() {
//        return (description) -> Files.createTempDirectory("project-");
//    }
//    @Bean
//    @ConditionalOnMissingBean
//    public IndentingWriterFactory indentingWriterFactory() {
//        return IndentingWriterFactory.create(new SimpleIndentStrategy("\t"),
//                (builder) -> builder.indentingStrategy("yaml", new SimpleIndentStrategy("  ")));
//    }
//
//    @Bean
//    @ConditionalOnMissingBean(TemplateRenderer.class)
//    public MustacheTemplateRenderer templateRenderer(Environment environment,
//                                                     ObjectProvider<CacheManager> cacheManager) {
//        return new MustacheTemplateRenderer("classpath:/templates",
//                determineCache(environment, cacheManager.getIfAvailable()));
//    }
//
//    private Cache determineCache(Environment environment, CacheManager cacheManager) {
//        if (cacheManager != null) {
//            Binder binder = Binder.get(environment);
//            boolean cache = binder.bind("spring.mustache.cache", Boolean.class).orElse(true);
//            if (cache) {
//                return cacheManager.getCache("initializr.templates");
//            }
//        }
//        return new NoOpCache("templates");
//    }
//
//    @Bean
//    @ConditionalOnMissingBean(InitializrMetadataProvider.class)
//    public InitializrMetadataProvider initializrMetadataProvider(InitializrProperties properties,
//                                                                 ObjectProvider<InitializrMetadataUpdateStrategy> initializrMetadataUpdateStrategy) {
//        InitializrMetadata metadata = InitializrMetadataBuilder.fromInitializrProperties(properties).build();
//        return new DefaultInitializrMetadataProvider(metadata,
//                initializrMetadataUpdateStrategy.getIfAvailable(() -> (current) -> current));
//    }
//
//    @Bean
//    @ConditionalOnMissingBean
//    public DependencyMetadataProvider dependencyMetadataProvider() {
//        return new DefaultDependencyMetadataProvider();
//    }
//
//    /**
//     * Initializr web configuration.
//     */
//    @Configuration
//    @ConditionalOnWebApplication
//    static class InitializrWebConfiguration {
//
//        @Bean
//        InitializrWebConfig initializrWebConfig() {
//            return new InitializrWebConfig();
//        }
//
//        @Bean
//        @ConditionalOnMissingBean
//        ProjectGenerationController<ProjectRequest> projectGenerationController(
//                InitializrMetadataProvider metadataProvider,
//                ObjectProvider<ProjectRequestPlatformVersionTransformer> platformVersionTransformer,
//                ApplicationContext applicationContext) {
//            ProjectGenerationInvoker<ProjectRequest> projectGenerationInvoker = new LubanProjectGenerationInvoker(
//                    applicationContext, new DefaultProjectRequestToDescriptionConverter(platformVersionTransformer
//                    .getIfAvailable(DefaultProjectRequestPlatformVersionTransformer::new)));
//            return new LubanProjectGenerationController(metadataProvider, projectGenerationInvoker);
//        }
//
//        @Bean
//        @ConditionalOnMissingBean
//        ProjectMetadataController projectMetadataController(InitializrMetadataProvider metadataProvider,
//                                                            DependencyMetadataProvider dependencyMetadataProvider) {
//            return new ProjectMetadataController(metadataProvider, dependencyMetadataProvider);
//        }
//
//        @Bean
//        @ConditionalOnMissingBean
//        CommandLineMetadataController commandLineMetadataController(InitializrMetadataProvider metadataProvider,
//                                                                    TemplateRenderer templateRenderer) {
//            return new CommandLineMetadataController(metadataProvider, templateRenderer);
//        }
//
//        @Bean
//        @ConditionalOnMissingBean
//        SpringCliDistributionController cliDistributionController(InitializrMetadataProvider metadataProvider) {
//            return new SpringCliDistributionController(metadataProvider);
//        }
//    }
//
//    /**
//     * Initializr cache configuration.
//     */
//    @Configuration
//    @ConditionalOnClass(javax.cache.CacheManager.class)
//    static class InitializrCacheConfiguration {
//
//        @Bean
//        JCacheManagerCustomizer initializrCacheManagerCustomizer() {
//            return new InitializrJCacheManagerCustomizer();
//        }
//
//    }
//
//    @Order(0)
//    private static class InitializrJCacheManagerCustomizer implements JCacheManagerCustomizer {
//
//        @Override
//        public void customize(javax.cache.CacheManager cacheManager) {
//            createMissingCache(cacheManager, "initializr.metadata",
//                    () -> config().setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.TEN_MINUTES)));
//            createMissingCache(cacheManager, "initializr.dependency-metadata", this::config);
//            createMissingCache(cacheManager, "initializr.project-resources", this::config);
//            createMissingCache(cacheManager, "initializr.templates", this::config);
//        }
//
//        private void createMissingCache(javax.cache.CacheManager cacheManager, String cacheName,
//                                        Supplier<MutableConfiguration<Object, Object>> config) {
//            boolean cacheExist = StreamSupport.stream(cacheManager.getCacheNames().spliterator(), true)
//                    .anyMatch((name) -> name.equals(cacheName));
//            if (!cacheExist) {
//                cacheManager.createCache(cacheName, config.get());
//            }
//        }
//
//        private MutableConfiguration<Object, Object> config() {
//            return new MutableConfiguration<>().setStoreByValue(false)
//                    .setManagementEnabled(true)
//                    .setStatisticsEnabled(true);
//        }
//
//    }
//
//}
