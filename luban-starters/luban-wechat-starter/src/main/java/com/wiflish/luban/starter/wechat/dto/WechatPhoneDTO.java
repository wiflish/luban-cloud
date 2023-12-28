package com.wiflish.luban.starter.wechat.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wiflish
 * @since 2023-12-27
 */
@Getter
@Setter
public class WechatPhoneDTO extends WechatRespDTO {
    /**
     * 用户绑定的手机号（国外手机号会有区号）
     */
    private String phoneNumber;
    /**
     * 没有区号的手机号
     */
    private String purePhoneNumber;
    /**
     * 没有区号的手机号
     */
    private int countryCode;
}
