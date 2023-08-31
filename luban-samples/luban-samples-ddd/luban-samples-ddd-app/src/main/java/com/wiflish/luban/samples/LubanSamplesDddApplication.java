package com.wiflish.luban.samples;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动类
 *
 * @author wiflish
 * @since 2023-08-28
 */
@SpringBootApplication
@ComponentScan(value = {"com.wiflish.luban.samples.ddd", "com.wiflish.luban"})
@MapperScan("com.wiflish.luban.samples.infra.mapper")
public class LubanSamplesDddApplication {
    public static void main(String[] args) {
        SpringApplication.run(LubanSamplesDddApplication.class, args);
    }
}