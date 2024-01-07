package com.wiflish.luban.starter.sms.service;

/**
 * @author wiflish
 * @since 2024-01-05
 */
public interface SmsService {
    /**
     * 发送短信
     *
     * @param phone 多个用户用逗号分隔.
     * @param signName 签名名称
     * @param templateCode 模板编码
     * @param param JSON结构字符串.
     */
    void sendSmsAsync(String phone, String signName, String templateCode, String param);
}
