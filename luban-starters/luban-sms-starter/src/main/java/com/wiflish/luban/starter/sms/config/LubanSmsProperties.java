package com.wiflish.luban.starter.sms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wiflish
 * @since 2024-01-05
 */
@Data
@ConfigurationProperties(prefix = "luban.sms")
public class LubanSmsProperties {
    private Aliyun aliyun;

    @Data
    public static class Aliyun {
        private String accessKey;
        private String secretKey;
    }
}
