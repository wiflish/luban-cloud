/*-
 * #%L
 * %%
 * Copyright (C) 2023 wiflish
 * %%
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
 * #L%
 */
package com.wiflish.luban.core.web.interceptor.auth;

/**
 * @author wiflish
 * @since 2023-06-09
 */
public interface AuthService {
    default String getTokenName() {
        return "token";
    }

    /**
     * 登录认证.
     *
     * @param cmd 认证参数
     * @return 返回当前登录用户；null表示没有登录。
     */
    Object checkLogin(AuthCmd cmd);
}
