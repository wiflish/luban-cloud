package com.wiflish.luban.starter.store.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.wiflish.luban.core.dto.constant.BaseConstant;
import com.wiflish.luban.starter.store.cmd.StoreUploadCmd;
import com.wiflish.luban.starter.store.config.LubanStoreProperties;
import com.wiflish.luban.starter.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

/**
 * 七牛云上传.
 *
 * @author xiezhengrong
 * @since 2023-06-05
 */
@Slf4j
@RequiredArgsConstructor
public class QiniuServiceImpl implements StoreService {
    private static final String defaultFilePathTmp = "%s/$(year)$(mon)";
    private static final String defaultFileName = "/$(day)$(hour)$(min)$(sec)$(etag)$(ext)";
    private static final String defaultFileNameTmp = defaultFilePathTmp + defaultFileName;

    private static final String imageReturnBody = "{\"key\": \"$(key)\", \"hash\": \"$(etag)\", \"w\": \"$(imageInfo.width)\", \"h\": \"$(imageInfo.height)\"}";
    private static final String fileReturnBody = "{\"key\": \"$(key)\", \"hash\": \"$(etag)\", \"fname\": $(fname)}";

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
        } else {
            putPolicy.put("returnBody", fileReturnBody);
        }

        return auth.uploadToken(bucket, null, lubanStoreProperties.getExpireSeconds(), putPolicy);
    }

    @Override
    public String uploadFile(StoreUploadCmd storeUploadCmd, byte[] fileData) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.huanan());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本

        UploadManager uploadManager = new UploadManager(cfg);
        String upToken = getUploadToken(storeUploadCmd);

        try {
            Response response = uploadManager.put(fileData, null, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
            return putRet.key;
        } catch (QiniuException ex) {
            if (ex.response != null) {
                log.warn("文件上传失败，原因：{}", ex.response);
            }
        }
        return null;
    }

    private String getEnvDir() {
        return (BaseConstant.PROD_ENV.equals(activeEnv) ? "p" : "t");
    }
}