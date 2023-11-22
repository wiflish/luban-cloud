package com.wiflish.luban.starter.web.audit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 审计日志注解，放到Controller方法上。
 *
 * @author wiflish
 * @since 2023-11-22
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuditLog {
    /**
     * 模块名
     */
    String module() default "";

    /**
     * 功能名
     */
    String funcName() default "";

    /**
     * 审计类型
     */
    AuditTypeEnum type() default AuditTypeEnum.OTHER;
}
