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
package com.wiflish.luban.core.dto.exception;

/**
 * 异常创建工厂类.
 *
 * @author wiflish
 * @since 2022-03-17
 */
public class ExceptionFactory {
    public static BizException bizException(ErrorCode errorCode) {
        return new BizException(errorCode);
    }

    public static InvalidParamException paramException() {
        return new InvalidParamException();
    }

    public static InvalidPermissionException permissionException() {
        return new InvalidPermissionException();
    }

    public static SysException sysException(ErrorCode errorCode) {
        return new SysException(errorCode);
    }

    public static SysException sysException(ErrorCode errorCode, Throwable e) {
        return new SysException(errorCode, e);
    }
}
