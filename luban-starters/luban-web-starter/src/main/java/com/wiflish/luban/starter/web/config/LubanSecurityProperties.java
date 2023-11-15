package com.wiflish.luban.starter.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author wiflish
 * @since 2023-09-21
 */
@Data
@ConfigurationProperties(prefix = "luban.security")
public class LubanSecurityProperties {
    private Boolean disable = false;

    private List<String> permitUrls;
}
