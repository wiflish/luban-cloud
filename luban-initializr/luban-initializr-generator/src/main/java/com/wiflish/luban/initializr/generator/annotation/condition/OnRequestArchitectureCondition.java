package com.wiflish.luban.initializr.generator.annotation.condition;

import cn.hutool.core.util.StrUtil;
import com.wiflish.luban.initializr.generator.constants.ArchitectureEnum;
import com.wiflish.luban.initializr.generator.project.LubanProjectDescription;
import io.spring.initializr.generator.condition.ProjectGenerationCondition;
import io.spring.initializr.generator.project.ProjectDescription;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Objects;

/**
 * @author wiflish
 * @since 2023-08-28 11:12:18
 */
public class OnRequestArchitectureCondition extends ProjectGenerationCondition {
    @Override
    protected boolean matches(ProjectDescription description, ConditionContext context,
                              AnnotatedTypeMetadata metadata) {

        ArchitectureEnum architectureEnum = (ArchitectureEnum) Objects.requireNonNull(metadata.getAnnotationAttributes(
                ConditionalOnRequestArchitecture.class.getName())).get("value");
        if (description instanceof LubanProjectDescription aDescription) {
            return StrUtil.equals(aDescription.getArchitecture(), architectureEnum.getId());
        }
        return false;
    }
}
