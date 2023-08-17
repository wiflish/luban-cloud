package com.wiflish.luban.core.dto.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wiflish
 * @since 2023-08-15
 */
public class ErrorCode {
    @Getter
    private String prefix = "error.";

    @Getter
    @Setter
    private String code;

    private ErrorCode() {
    }

    public static ErrorCode of(String code) {
        ErrorCode errorCode = new ErrorCode();
        errorCode.setCode(code);

        return errorCode;
    }

    public String getKey() {
        return prefix + code;
    }
}
