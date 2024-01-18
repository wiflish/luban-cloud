package com.wiflish.luban.starter.sms.aliyun.service;

import com.alibaba.fastjson2.JSON;
import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsResponse;
import com.wiflish.luban.starter.sms.config.LubanSmsProperties;
import com.wiflish.luban.starter.sms.service.SmsService;
import darabonba.core.client.ClientOverrideConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
public class AliyunSmsService implements SmsService {
    private final LubanSmsProperties.Aliyun aliyun;

    @Override
    public void sendSmsAsync(String phone, String signName, String templateCode, String param) {
        try {
            StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                    .accessKeyId(aliyun.getAccessKey())
                    .accessKeySecret(aliyun.getSecretKey())
                    .build());

            // Configure the Client
            AsyncClient client = AsyncClient.builder()
                    .region("cn-shenzhen")
                    .credentialsProvider(provider)
                    .overrideConfiguration(
                            ClientOverrideConfiguration.create()
                                    .setEndpointOverride("dysmsapi.aliyuncs.com")
                    )
                    .build();

            // Parameter settings for API request
            SendSmsRequest sendSmsRequest = SendSmsRequest.builder()
                    .phoneNumbers(phone)
                    .signName(signName)
                    .templateCode(templateCode)
                    .templateParam(param)
                    .build();

            CompletableFuture<SendSmsResponse> response = client.sendSms(sendSmsRequest);
            SendSmsResponse resp = response.get();
            log.info("发送短信返回结果：{}", JSON.toJSONString(resp));

            client.close();
        } catch (Exception e) {
            log.error("send sms error, phone: {}, signName: {}, templateCode: {}, param: {}",
                    phone, signName, templateCode, param, e);
        }
    }
}

