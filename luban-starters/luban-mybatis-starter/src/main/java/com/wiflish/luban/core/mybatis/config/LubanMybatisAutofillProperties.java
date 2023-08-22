package com.wiflish.luban.core.mybatis.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.wiflish.luban.core.mybatis.config.LubanMybatisAutofillProperties.PREFIX;

/**
 * @author wiflish
 * @since 2023-08-22
 */
@Getter
@Setter
@ConfigurationProperties(PREFIX)
public class LubanMybatisAutofillProperties {
    /**
     * 前缀
     */
    public static final String PREFIX = "luban.mybatis.autofill";

    /**
     * 是否开启自动填入createId和updateId.
     */
    private Boolean enable = Boolean.TRUE;

    private String createIdField = "createId";
    private String updateIdField = "updateId";
}
