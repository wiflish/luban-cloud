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
package com.wiflish.luban.core.dto;

import com.wiflish.luban.core.dto.exception.BaseErrorCodeConstant;
import com.wiflish.luban.core.dto.exception.ErrorCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 返回结果.
 *
 * @author wiflish
 * @since 2022-03-17
 */
@Setter
@Getter
public class Response<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String code;
    private String message;
    private T data;
    private Long total;
    private Integer pageNo;
    private Integer pageSize;

    private Response() {
    }

    public static Response of() {
        Response response = new Response();
        response.setCode(BaseErrorCodeConstant.SUCCESS_CODE.getCode());
        return response;
    }

    public static <T> Response of(T data) {
        Response<T> response = new Response<>();
        response.setCode(BaseErrorCodeConstant.SUCCESS_CODE.getCode());
        response.setData(data);

        return response;
    }

    public static <T> Response of(T data, Long total) {
        Response<T> response = new Response<>();
        response.setCode(BaseErrorCodeConstant.SUCCESS_CODE.getCode());
        response.setTotal(total);
        response.setData(data);

        return response;
    }

    public static <T> Response of(T data, Integer pageNo, Integer pageSize) {
        Response<T> response = new Response<>();
        response.setCode(BaseErrorCodeConstant.SUCCESS_CODE.getCode());
        response.setData(data);
        response.setPageNo(pageNo);
        response.setPageSize(pageSize);

        return response;
    }

    public static <T> Response of(T data, Integer pageNo, Integer pageSize, Long total) {
        Response<T> response = new Response<>();
        response.setCode(BaseErrorCodeConstant.SUCCESS_CODE.getCode());
        response.setData(data);
        response.setPageNo(pageNo);
        response.setPageSize(pageSize);
        response.setTotal(total);

        return response;
    }

    public static Response failure(ErrorCode errorCode) {
        Response response = new Response();
        response.setCode(errorCode.getCode());

        return response;
    }
}
