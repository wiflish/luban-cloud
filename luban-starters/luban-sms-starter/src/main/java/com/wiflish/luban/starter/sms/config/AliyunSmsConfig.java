package com.wiflish.luban.starter.sms.config;

import com.wiflish.luban.starter.sms.aliyun.service.AliyunSmsService;
import com.wiflish.luban.starter.sms.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wiflish
 * @since 2024-01-05
 */
@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(LubanSmsProperties.class)
@ConditionalOnProperty(prefix = "luban.sms.aliyun", name="access-key")
public class AliyunSmsConfig {
    @Bean
    public SmsService getAliyunSmsService(LubanSmsProperties lubanSmsProperties) {
        return new AliyunSmsService(lubanSmsProperties.getAliyun());
    }
}
