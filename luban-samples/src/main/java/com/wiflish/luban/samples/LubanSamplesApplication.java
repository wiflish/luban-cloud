package com.wiflish.luban.samples;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wiflish
 * @since 2023-08-16
 */
@SpringBootApplication
@MapperScan("com.wiflish.luban.samples")
public class LubanSamplesApplication {
    public static void main(String[] args) {
        SpringApplication.run(LubanSamplesApplication.class, args);
    }
}
