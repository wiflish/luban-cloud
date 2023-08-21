package com.wiflish.luban.initializr.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * <p>项目脚手架application, 基于spring-initializr.</p>
 *
 * <p>需要把InitializrApplication放到一个独立的package下面，这样就不会扫描到*Contributor类。</p>
 *
 * <p>原因： *Contributor类是在请求生成项目的过程中注入到SpringContext中的。</p>
 *
 * @author wiflish
 * @since 2022-09-13
 */
@SpringBootApplication
@EnableCaching
@EnableAsync
public class InitializrApplication {
    public static void main(String[] args) {
        SpringApplication.run(InitializrApplication.class, args);
    }
}
