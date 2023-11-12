package com.wiflish.luban.starter.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wiflish
 * @since 2023-09-21
 */
@Data
@ConfigurationProperties(prefix = "luban.security")
public class SecurityProperties {
    private Boolean disable = false;
}
