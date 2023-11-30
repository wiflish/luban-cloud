package com.wiflish.luban.starter.store.service.impl;

import cn.hutool.core.util.StrUtil;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.wiflish.luban.core.dto.constant.BaseConstant;
import com.wiflish.luban.starter.store.cmd.StoreUploadCmd;
import com.wiflish.luban.starter.store.config.LubanStoreProperties;
import com.wiflish.luban.starter.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

/**
 * 七牛云上传.
 *
 * @author xiezhengrong
 * @since 2023-06-05
 */
@RequiredArgsConstructor
public class QiniuServiceImpl implements StoreService {
    private static final String defaultFilePathTmp = "%s/$(year)$(mon)";
    private  static final String defaultFileName = "/$(day)$(hour)$(min)$(sec)$(etag)$(ext)";
    private  static final String defaultFileNameTmp = defaultFilePathTmp + defaultFileName;

    private static final String imageReturnBody = "{\"key\": \"$(key)\", \"hash\": \"$(etag)\", \"w\": \"$(imageInfo.width)\", \"h\": \"$(imageInfo.height)\"}";

    @Value("${spring.profiles.active:test}")
    private String activeEnv;

    private final LubanStoreProperties lubanStoreProperties;

    @Override
    public String getUploadToken(StoreUploadCmd storeUploadCmd) {
        String bucket = storeUploadCmd.getName();
        if (StrUtil.isEmpty(bucket)) {
            bucket = lubanStoreProperties.getQiniu().getBucket();
        }
        Auth auth = Auth.create(lubanStoreProperties.getQiniu().getAccessKey(), lubanStoreProperties.getQiniu().getSecretKey());
        StringMap putPolicy = new StringMap();
        String saveKey = String.format(defaultFileNameTmp, getEnvDir());

        //指定上传目录.
        String path = storeUploadCmd.getPath();
        if (StrUtil.isNotEmpty(path)) {
            saveKey = path + defaultFileName;
        }

        if (saveKey.startsWith("/")) {
            saveKey = saveKey.substring(1);
        }
        putPolicy.put("saveKey", saveKey);
        if (storeUploadCmd.getType() != null && storeUploadCmd.getType() == 1) {
            putPolicy.put("returnBody", imageReturnBody);
        }

        return auth.uploadToken(bucket, null, lubanStoreProperties.getExpireSeconds(), putPolicy);
    }

    private String getEnvDir() {
        return (BaseConstant.PROD_ENV.equals(activeEnv) ? "p" : "t");
    }
}