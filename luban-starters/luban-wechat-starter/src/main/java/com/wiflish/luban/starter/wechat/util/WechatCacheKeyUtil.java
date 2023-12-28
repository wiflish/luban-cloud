package com.wiflish.luban.starter.wechat.util;

/**
 * @author xiezhengrong
 * @since 2023-12-27
 */
public class WechatCacheKeyUtil {
    private static final String wechatOpenAccessTokenKey = "wechat_token_%s";

    /**
     * 获取微信token的key.
     *
     * @return 返回key.
     */
    public static String getOpenAccessTokenKey(String appId) {
        assert appId != null;
        return String.format(wechatOpenAccessTokenKey, appId);
    }
}
