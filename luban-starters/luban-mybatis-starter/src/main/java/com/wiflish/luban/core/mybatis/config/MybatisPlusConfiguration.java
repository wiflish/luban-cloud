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
package com.wiflish.luban.core.mybatis.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.wiflish.luban.core.mybatis.objecthandler.AuditMetaObjectHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import static com.wiflish.luban.core.mybatis.config.LubanMybatisAutofillProperties.PREFIX;

/**
 * @author wiflish
 * @since 2023-08-22
 */
@AutoConfiguration
@EnableConfigurationProperties(LubanMybatisAutofillProperties.class)
public class MybatisPlusConfiguration {
    @Autowired
    private LubanMybatisAutofillProperties lubanMybatisAutofillProperties;

    @Bean
    @ConditionalOnProperty(value = PREFIX + ".enable", havingValue = "true", matchIfMissing = true)
    public AuditMetaObjectHandler getAuditMetaObjectHandler() {
        return new AuditMetaObjectHandler(lubanMybatisAutofillProperties);
    }

    /**
     * 分页插件.
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        // 防止全表更新与删除
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());

        return interceptor;
    }
}
