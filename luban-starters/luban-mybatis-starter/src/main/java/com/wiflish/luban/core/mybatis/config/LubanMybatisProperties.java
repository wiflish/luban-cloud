package com.wiflish.luban.core.mybatis.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wiflish
 * @since 2023-08-22
 */
@Getter
@Setter
@ConfigurationProperties("luban.mybatis")
public class LubanMybatisProperties {
    private String createIdField;
    private String updateIdField;
}
