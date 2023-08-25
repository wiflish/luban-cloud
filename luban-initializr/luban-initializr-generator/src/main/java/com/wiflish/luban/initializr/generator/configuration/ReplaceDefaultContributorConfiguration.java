///*-
// * ************
// * luban-cloud
// * ------------
// * Copyright (C) 2023 - 2023 the original author or authors.
// * ------------
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// * ************
// */
//package com.wiflish.luban.initializr.generator.configuration;
//
//import cn.hutool.core.collection.ListUtil;
//import com.wiflish.luban.initializr.generator.annotation.LubanProjectGenerationConfiguration;
//import io.spring.initializr.generator.project.contributor.ProjectContributor;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.config.BeanPostProcessor;
//import org.springframework.context.annotation.Bean;
//
//import java.nio.file.Path;
//import java.util.List;
//
///**
// * 去掉默认的内容，全部用自己写的.
// *
// * @author wiflish
// * @since 2022-09-22
// */
//@LubanProjectGenerationConfiguration
//public class ReplaceDefaultContributorConfiguration {
//    @Bean
//    public ReplaceDefaultContributorBeanPostProcessor replaceProjectContributorBeanPostProcessor() {
//        return new ReplaceDefaultContributorBeanPostProcessor();
//    }
//
//    /**
//     * 采用自己的结构，不需要生成: webFolder和帮助文档.
//     */
//    private static class ReplaceDefaultContributorBeanPostProcessor implements BeanPostProcessor {
//        private final List<String> defaultList = ListUtil.of("mavenWrapperContributor", "webFoldersContributor", "helpDocumentProjectContributor",
//                "mainSourceCodeProjectContributor", "mavenBuildProjectContributor", "applicationPropertiesContributor", "testSourceCodeProjectContributor");
//        private final ProjectContributor none = new NoneProjectContributor();
//
//        @Override
//        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//            if (!(bean instanceof ProjectContributor)) {
//                return bean;
//            }
//            if (!defaultList.contains(beanName)) {
//                return bean;
//            }
//            return none;
//        }
//    }
//
//    /**
//     * 生成空内容.
//     *
//     * @author wiflish
//     * @since 2022-09-26
//     */
//    private static class NoneProjectContributor implements ProjectContributor {
//        @Override
//        public void contribute(Path projectRoot) {
//        }
//    }
//}
