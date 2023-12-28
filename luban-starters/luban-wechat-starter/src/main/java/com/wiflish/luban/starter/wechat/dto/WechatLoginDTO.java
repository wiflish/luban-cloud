package com.wiflish.luban.starter.wechat.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wiflish
 * @since 2023-12-27
 */
@Getter
@Setter
public class WechatLoginDTO extends WechatRespDTO {
    private String openid;
    @JSONField(name = "session_key")
    private String sessionKey;
    private String unionid;
}
