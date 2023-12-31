package com.wiflish.luban.starter.wechat.service;

import cn.hutool.core.util.StrUtil;
import com.wiflish.luban.core.dto.exception.BizException;
import com.wiflish.luban.core.dto.exception.InvalidParamException;
import com.wiflish.luban.starter.wechat.dto.WechatLoginDTO;
import com.wiflish.luban.starter.wechat.dto.WechatPhoneDTO;
import com.wiflish.luban.starter.wechat.dto.cmd.WechatQrCodeCmd;
import com.wiflish.luban.starter.wechat.invoker.WechatInvoker;
import com.wiflish.luban.starter.wechat.util.WechatCacheKeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static com.wiflish.luban.starter.wechat.constant.WechatErrorConstant.WECHAT_LOGIN_ERROR_CODE;

/**
 * @author wiflish
 * @since 2023-12-27
 */
@Slf4j
@Component
public class WechatUtil {
    /**
     * 不能超过7200秒
     */
    private static final Long accessTokenExpireIn = 7000L;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private WechatInvoker wechatInvoker;

    /**
     * 全局处理wechat的OpenAccessToken
     *
     * @param appId
     * @param appSecret
     */
    public String getOpenAccessToken(String appId, String appSecret) {
        if (StrUtil.isEmpty(appId) || StrUtil.isEmpty(appSecret)) {
            log.debug("获取access_token失败，appId: {}, appSecret: {}", appId, appSecret);
            throw new InvalidParamException();
        }
        String key = WechatCacheKeyUtil.getOpenAccessTokenKey(appId);

        //缓存里有，从缓存取。
        String accessToken = redisTemplate.opsForValue().get(key);
        if (StrUtil.isNotEmpty(accessToken)) {
            return accessToken;
        }
        accessToken = wechatInvoker.getAccessToken(appId, appSecret);
        //获取token，放到缓存。
        redisTemplate.opsForValue().set(key, accessToken, accessTokenExpireIn, TimeUnit.SECONDS);

        return accessToken;
    }

    public WechatLoginDTO code2Session(String appId, String secret, String code) {
        WechatLoginDTO loginDTO = wechatInvoker.code2Session(appId, secret, code);
        assert loginDTO != null;
        if (loginDTO.getErrcode() != 0) {
            log.error("code2Session error, errcode: {}, errmsg: {}", loginDTO.getErrcode(), loginDTO.getErrmsg());
            throw new BizException(WECHAT_LOGIN_ERROR_CODE);
        }

        return loginDTO;
    }

    /**
     * 根据code获取手机号码
     *
     * @param code 该code需要小程序端调用getPhoneNumber接口获得。不同于登录的code
     * @return
     */
    public WechatPhoneDTO getPhoneNumber(String appId, String appSecret, String code) {
        String openAccessToken = this.getOpenAccessToken(appId, appSecret);

        return wechatInvoker.getPhoneNumber(openAccessToken, code);
    }

    /**
     * 获取小程序二维码
     *
     * @param appId
     * @param appSecret
     * @param cmd
     * @return
     */
    public byte[] getUnlimitedQRCode(String appId, String appSecret, WechatQrCodeCmd cmd) {
        String openAccessToken = this.getOpenAccessToken(appId, appSecret);

        return wechatInvoker.getUnlimitedQRCode(openAccessToken, cmd);
    }
}
