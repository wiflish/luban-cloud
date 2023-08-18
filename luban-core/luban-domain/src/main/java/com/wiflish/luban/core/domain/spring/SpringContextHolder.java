/*-
 * ************
 * luban-cloud
 * ************
 * Copyright (C) 2023 - 2023 the original author or authors.
 * ************
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
package com.wiflish.luban.core.domain.spring;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * SpringContextHolder
 *
 * @author wiflish
 * @since 2022-03-19
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> targetClz) {
        T beanInstance = null;
        try {
            beanInstance = applicationContext.getBean(targetClz);
        } catch (Exception ignored) {
        }

        if (beanInstance == null) {
            String simpleName = targetClz.getSimpleName();
            simpleName = StrUtil.lowerFirst(simpleName);
            beanInstance = (T) applicationContext.getBean(simpleName);
        }

        return beanInstance;
    }

    public static Object getBean(String clz) {
        return SpringContextHolder.applicationContext.getBean(clz);
    }

    public static <T> T getBean(String name, Class<T> requiredType) {
        return SpringContextHolder.applicationContext.getBean(name, requiredType);
    }
}
