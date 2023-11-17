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
package com.wiflish.luban.starter.web.interceptor.auth;

import com.wiflish.luban.core.dto.exception.BaseErrorCodeConstant;
import com.wiflish.luban.core.dto.exception.BizException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author wiflish
 * @since 2023-06-09
 */
public class AuthInterceptor implements HandlerInterceptor {
    private final AuthService authService;

    public AuthInterceptor(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        Auth authAnnotation = handlerMethod.getMethodAnnotation(Auth.class);
        if (authAnnotation == null) {
            authAnnotation = handlerMethod.getBeanType().getAnnotation(Auth.class);
            if (authAnnotation == null) {
                return true;
            }
        }

        String token = request.getHeader(authService.getTokenName());
        Object logon = authService.checkLogin(new AuthCmd(token));
        if (logon == null) {
            throw new BizException(BaseErrorCodeConstant.NO_PERMISSION);
        }

        return true;
    }
}
