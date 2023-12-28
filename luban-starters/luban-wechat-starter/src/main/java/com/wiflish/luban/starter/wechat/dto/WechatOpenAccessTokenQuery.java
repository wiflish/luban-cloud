package com.wiflish.luban.starter.wechat.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author xiezhengrong
 * @since 2023-12-27
 */
@Data
public class WechatOpenAccessTokenQuery {
    /**
     * 开放平台的应用id，比如：公众号appId
     */
    @NotNull
    private String appId;

    /**
     * appId对应的secret
     */
    @NotNull
    private String appSecret;
}
