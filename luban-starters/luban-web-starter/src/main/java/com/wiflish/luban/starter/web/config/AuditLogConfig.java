package com.wiflish.luban.starter.web.config;

import com.wiflish.luban.starter.web.audit.AuditLogAspect;
import com.wiflish.luban.starter.web.audit.AuditLogService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wiflish
 * @since 2023-11-22
 */
@Configuration
@ConditionalOnBean(AuditLogService.class)
public class AuditLogConfig {
    @Bean
    public AuditLogAspect auditLogAspect(AuditLogService auditLogService) {
        return new AuditLogAspect(auditLogService);
    }
}
