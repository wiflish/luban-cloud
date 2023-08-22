package com.wiflish.luban.core.mybatis.config;

import com.wiflish.luban.core.mybatis.objecthandler.AuditMetaObjectHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import static com.wiflish.luban.core.mybatis.config.LubanMybatisAutofillProperties.PREFIX;

/**
 * @author wiflish
 * @since 2023-08-22
 */
@AutoConfiguration
@EnableConfigurationProperties(LubanMybatisAutofillProperties.class)
public class MybatisPlusConfiguration {
    @Autowired
    private LubanMybatisAutofillProperties lubanMybatisAutofillProperties;

    @Bean
    @ConditionalOnProperty(value = PREFIX + ".enable", havingValue = "true", matchIfMissing = true)
    public AuditMetaObjectHandler getAuditMetaObjectHandler() {
        return new AuditMetaObjectHandler(lubanMybatisAutofillProperties);
    }
}
