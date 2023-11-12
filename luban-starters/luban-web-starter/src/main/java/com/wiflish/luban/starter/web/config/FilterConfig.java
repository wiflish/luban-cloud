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
package com.wiflish.luban.starter.web.config;

import com.wiflish.luban.starter.web.filter.RequestLoggingAndProfilingFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "server.api", name="request.log-enabled", havingValue = "true")
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<RequestLoggingAndProfilingFilter> logAndProfileFilterRegistration() {
        FilterRegistrationBean<RequestLoggingAndProfilingFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(loggingFilter());
        registration.addUrlPatterns("/*"); // 设置需要过滤的URL模式
        registration.setName("LoggingAndProfilingFilter");
        registration.setOrder(1); // 设置Filter执行顺序
        return registration;
    }

    @Bean
    public RequestLoggingAndProfilingFilter loggingFilter() {
        RequestLoggingAndProfilingFilter filter = new RequestLoggingAndProfilingFilter();
        filter.setIncludeClientInfo(true);
        filter.setIncludeHeader(true);
        filter.setIncludePayload(true);
        filter.setIncludeProfile(true);
        filter.setIncludeQueryString(true);
        filter.setMaxPayloadLength(1000);

        return filter;
    }
}
