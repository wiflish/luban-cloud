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
package com.wiflish.luban.starter.store.config;

import com.wiflish.luban.starter.store.service.StoreService;
import com.wiflish.luban.starter.store.service.impl.QiniuServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 七牛云配置
 *
 * @author wiflish
 * @since 2023-11-30
 */
@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(LubanStoreProperties.class)
@ConditionalOnProperty(prefix = "luban.store.qiniu", name="access-key")
public class QiniuConfig {
    private final LubanStoreProperties lubanStoreProperties;

    @Bean
    public StoreService getQiniuStoreService(LubanStoreProperties lubanStoreProperties) {
        return new QiniuServiceImpl(lubanStoreProperties);
    }
}
