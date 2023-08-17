package com.wiflish.luban.core.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wiflish
 * @since 2023-08-16
 */
@SpringBootApplication
@MapperScan("com.wiflish.luban.core.mybatis")
public class MybatisApplication {
    public static void main(String[] args) {
        SpringApplication.run(MybatisApplication.class, args);
    }
}
