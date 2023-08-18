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
package com.wiflish.luban.core.dto.exception;

import java.io.Serial;

/**
 * 异常抽象类.
 *
 * @author wiflish
 * @since 2022-03-17
 */
public abstract class BaseException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    private final ErrorCode errCode;

    public BaseException(ErrorCode errorCode) {
        super(errorCode.getKey());
        this.errCode = errorCode;
    }

    public BaseException(ErrorCode errorCode, Throwable e) {
        super(errorCode.getKey(), e);
        this.errCode = errorCode;
    }

    public ErrorCode getErrCode() {
        return errCode;
    }
}
