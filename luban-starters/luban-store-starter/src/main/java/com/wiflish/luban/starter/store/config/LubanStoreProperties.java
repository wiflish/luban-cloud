package com.wiflish.luban.starter.store.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wiflish
 * @since 2023-11-30
 */
@Data
@ConfigurationProperties(prefix = "luban.store")
public class LubanStoreProperties {
    /**
     * token有效时间，默认一小时.
     */
    private int expireSeconds = 3600;
    private Qiniu qiniu;

    @Data
    public static class Qiniu {
        private String accessKey;
        private String secretKey;
        private String bucket;
        private String domain;
    }
}
