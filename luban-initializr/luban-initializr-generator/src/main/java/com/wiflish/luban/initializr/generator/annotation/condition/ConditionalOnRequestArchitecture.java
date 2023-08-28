package com.wiflish.luban.initializr.generator.annotation.condition;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 *
 * @author wiflish
 * @since 2023-08-28 11:10:34
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
@Documented
@Conditional(OnRequestArchitectureCondition.class)
public @interface ConditionalOnRequestArchitecture {

	/**
	 * Architecture ID.
	 */
	String value();
}
