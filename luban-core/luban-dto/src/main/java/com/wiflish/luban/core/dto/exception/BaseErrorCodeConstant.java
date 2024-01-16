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
package com.wiflish.luban.core.dto.exception;

/**
 * 1000以下为系统错误码.
 *
 * @author wiflish
 * @since 2023-03-22
 */
public abstract class BaseErrorCodeConstant {
    public static final ErrorCode SUCCESS_CODE = ErrorCode.of("0");
    public static final ErrorCode INVALID_PARAM_CODE = ErrorCode.of("400");
    public static final ErrorCode UNAUTHORIZED = ErrorCode.of("401");
    public static final ErrorCode NO_PERMISSION = ErrorCode.of("403");
    public static final ErrorCode RECORD_NOT_FOUND = ErrorCode.of("404");
    public static final ErrorCode SERVER_ERROR_CODE = ErrorCode.of("500");
    public static final ErrorCode USER_NOT_LOGIN = ErrorCode.of("600");
    public static final ErrorCode USER_ADMIN_NOT_ALLOWED = ErrorCode.of("601");

    public static final ErrorCode USER_LOGIN_VERIFY_CODE = ErrorCode.of("690");
    public static final ErrorCode SUBMIT_DUPLICATION_ERROR_CODE = ErrorCode.of("700");
}
