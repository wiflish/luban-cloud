package com.wiflish.luban.initializr.generator.annotation;

import org.springframework.context.annotation.Configuration;

import java.lang.annotation.*;

/**
 *
 * @author wiflish
 * @since 2023-08-25 16:10:42
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration
public @interface LubanProjectGenerationConfiguration {
}
