package com.wiflish.luban.starter.wechat.constant;

import com.wiflish.luban.core.dto.exception.ErrorCode;

/**
 * @author wiflish
 * @since 2023-12-27
 */
public class WechatErrorConstant {
    public static final ErrorCode WECHAT_LOGIN_ERROR_CODE = ErrorCode.of("WECHAT-ERROR-00001");
    public static final ErrorCode WECHAT_PHONE_ERROR_CODE = ErrorCode.of("WECHAT-ERROR-00002");
}
