package com.wiflish.luban.initializr.configuration;

import cn.hutool.core.collection.ListUtil;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.generator.project.contributor.ProjectContributor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;

import java.nio.file.Path;
import java.util.List;

/**
 * 去掉默认的内容，全部用自己写的.
 *
 * @author wiflish
 * @since 2022-09-22
 */
@ProjectGenerationConfiguration
public class ReplaceDefaultContributorConfiguration {
    @Bean
    public ReplaceDefaultContributorBeanPostProcessor replaceProjectContributorBeanPostProcessor() {
        return new ReplaceDefaultContributorBeanPostProcessor();
    }

    /**
     * 采用自己的结构，不需要生成: webFolder和帮助文档.
     */
    private static class ReplaceDefaultContributorBeanPostProcessor implements BeanPostProcessor {
        private final List<String> defaultList = ListUtil.of("mavenWrapperContributor", "webFoldersContributor", "helpDocumentProjectContributor",
                "mainSourceCodeProjectContributor", "mavenBuildProjectContributor", "applicationPropertiesContributor", "testSourceCodeProjectContributor");
        private final ProjectContributor none = new NoneProjectContributor();

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            if (!(bean instanceof ProjectContributor)) {
                return bean;
            }
            if (!defaultList.contains(beanName)) {
                return bean;
            }
            return none;
        }
    }

    /**
     * 生成空内容.
     *
     * @author wiflish
     * @since 2022-09-26
     */
    private static class NoneProjectContributor implements ProjectContributor {
        @Override
        public void contribute(Path projectRoot) {
        }
    }
}
