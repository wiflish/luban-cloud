/*-
 * ************
 * luban-cloud
 * ------------
 * Copyright (C) 2023 - 2023 the original author or authors.
 * ------------
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ************
 */
package com.wiflish.luban.initializr.generator;

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
