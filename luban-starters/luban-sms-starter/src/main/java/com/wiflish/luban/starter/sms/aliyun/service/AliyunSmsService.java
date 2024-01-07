// This file is auto-generated, don't edit it. Thanks.
package com.wiflish.luban.starter.sms.aliyun.service;

import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsRequest;
import com.wiflish.luban.starter.sms.config.LubanSmsProperties;
import com.wiflish.luban.starter.sms.service.SmsService;
import darabonba.core.client.ClientOverrideConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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

            client.sendSms(sendSmsRequest);

            client.close();
        } catch (Exception e) {
            log.error("send sms error, phone: {}, signName: {}, templateCode: {}, param: {}",
                    phone, signName, templateCode, param, e);
        }
    }
}

