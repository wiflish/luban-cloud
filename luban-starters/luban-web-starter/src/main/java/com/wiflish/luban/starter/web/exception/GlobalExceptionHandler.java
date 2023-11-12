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
package com.wiflish.luban.starter.web.exception;

import cn.hutool.core.util.StrUtil;
import com.wiflish.luban.core.dto.Response;
import com.wiflish.luban.core.dto.exception.BizException;
import com.wiflish.luban.core.dto.exception.InvalidParamException;
import com.wiflish.luban.core.dto.exception.InvalidPermissionException;
import com.wiflish.luban.core.dto.exception.SysException;
import com.wiflish.luban.starter.i18n.MessageSourceResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.MessageFormat;

import static com.wiflish.luban.core.dto.exception.BaseErrorCodeConstant.*;

/**
 * 全局异常处理.
 *
 * @author wiflish
 * @since 2022-12-16
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    private MessageSourceResolver messageSourceResolver;

    @ExceptionHandler(InvalidParamException.class)
    public ResponseEntity<?> handleInvalidParamException(InvalidParamException ex) {
        log.error("参数异常: {}", ex.getMessage());
        Response response = Response.failure(INVALID_PARAM_CODE);
        response.setMessage(messageSourceResolver.getMessage(INVALID_PARAM_CODE.getKey()));
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> handleBindException(BindException ex) {
        log.error("参数异常: {}", ex.getMessage());
        StringBuilder message = new StringBuilder();

        int idx = 0;
        for (FieldError fieldError : ex.getFieldErrors()) {
            String errorMessage = fieldError.getDefaultMessage();
            if (StrUtil.isEmpty(errorMessage)) {
                continue;
            }
            if (idx++ > 0) {
                message.append(", ");
            }

            message.append(MessageFormat.format(errorMessage, fieldError.getField()));
        }

        //FIXME 需要将Validation的相关消息转换为国际化消息.
        Response response = Response.failure(INVALID_PARAM_CODE);
        response.setMessage(messageSourceResolver.getMessage(INVALID_PARAM_CODE.getKey()));
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidPermissionException.class)
    public ResponseEntity<?> handleInvalidPermissionException(InvalidPermissionException ex) {
        log.error("权限异常: {}", ex.getMessage());
        Response response = Response.failure(INVALID_PERMISSION_CODE);
        response.setMessage(messageSourceResolver.getMessage(INVALID_PERMISSION_CODE.getKey()));
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BizException.class)
    public ResponseEntity<?> handleBizException(BizException ex) {
        log.error("业务异常: {}", ex.getErrCode());
        Response response = Response.failure(ex.getErrCode());
        response.setMessage(messageSourceResolver.getMessage(ex.getErrCode().getKey()));
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SysException.class)
    public ResponseEntity<?> handleSysException(SysException ex) {
        log.error("系统繁忙: ", ex);
        Response response = Response.failure(ex.getErrCode());
        response.setMessage(messageSourceResolver.getMessage(ex.getErrCode().getKey()));
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<?> handleGeneralException(Throwable ex) {
        log.error("服务器繁忙: ", ex);
        Response response = Response.failure(SERVER_ERROR_CODE);
        response.setMessage(messageSourceResolver.getMessage(SERVER_ERROR_CODE.getKey()));
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
