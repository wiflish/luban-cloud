package com.wiflish.luban.generator;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动类
 *
 * @author wiflish
 * @since 2023-10-08
 */
@SpringBootApplication
@ComponentScan(value = {"com.wiflish.luban.generator", "com.wiflish.luban"})
@MapperScan("com.wiflish.luban.generator.**.infra.mapper")
public class GeneratorApplication {
    public static void main(String[] args) {
        SpringApplication.run(GeneratorApplication.class, args);
    }
}