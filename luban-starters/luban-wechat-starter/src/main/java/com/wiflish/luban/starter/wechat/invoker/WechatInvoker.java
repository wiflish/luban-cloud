package com.wiflish.luban.starter.wechat.invoker;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wiflish.luban.core.dto.exception.BizException;
import com.wiflish.luban.starter.wechat.dto.WechatLoginDTO;
import com.wiflish.luban.starter.wechat.dto.WechatPhoneDTO;
import com.wiflish.luban.starter.wechat.dto.cmd.WechatQrCodeCmd;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static com.wiflish.luban.starter.wechat.constant.WechatErrorConstant.WECHAT_PHONE_ERROR_CODE;

/**
 * @author xiezhengrong
 * @since 2023-12-27
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class WechatInvoker {
    private static final String baseUrl = "https://api.weixin.qq.com";
    private static final String tokenApiTpl = baseUrl + "/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
    private static final String code2SessionApiTpl = baseUrl + "/sns/jscode2session?grant_type=authorization_code&appid=%s&secret=%s&js_code=%s";

    private static final String phoneNumberApiTpl = baseUrl + "/wxa/business/getuserphonenumber?access_token=%s";
    private static final String unlimitedQRCodeApiTpl = baseUrl + "/wxa/getwxacodeunlimit?access_token=%s";

    private final RestTemplate restTemplate;

    public String getAccessToken(String appId, String secret) {
        String url = String.format(tokenApiTpl, appId, secret);
        String tokenResponse = restTemplate.getForObject(url, String.class);
        JSONObject tokenObj = JSONObject.parseObject(tokenResponse);
        log.debug("code2Session: {}", tokenResponse);
        assert tokenObj != null;
        return tokenObj.getString("access_token");
    }

    public WechatLoginDTO code2Session(String appId, String secret, String code) {
        String url = String.format(code2SessionApiTpl, appId, secret, code);
        String sessionResponse = restTemplate.getForObject(url, String.class);
        log.debug("code2Session: {}", sessionResponse);
        return JSONObject.parseObject(sessionResponse, WechatLoginDTO.class);
    }

    public WechatPhoneDTO getPhoneNumber(String accessToken, String code) {
        String url = String.format(phoneNumberApiTpl, accessToken);

        Map<String, String> postObj = Map.of("code", code);

        String phoneResponse = restTemplate.postForObject(url, postObj, String.class);
        log.debug("getPhoneNumber: {}", phoneResponse);
        JSONObject jsonObject = JSONObject.parseObject(phoneResponse);
        assert jsonObject != null;

        Integer errcode = jsonObject.getInteger("errcode");
        if (errcode == null || errcode != 0) {
            throw new BizException(WECHAT_PHONE_ERROR_CODE);
        }
        String phoneInfo = jsonObject.getString("phone_info");

        return JSON.parseObject(phoneInfo, WechatPhoneDTO.class);
    }

    public byte[] getUnlimitedQRCode(String accessToken, WechatQrCodeCmd cmd) {
        if (cmd == null || cmd.getScene() == null) {
            return null;
        }
        String url = String.format(unlimitedQRCodeApiTpl, accessToken);

        try {
            return restTemplate.postForObject(url, JSON.toJSONString(cmd), byte[].class);
        } catch (Exception ex) {
            log.error("get unlimited qrcode error", ex);
        }
        return null;
    }
}
