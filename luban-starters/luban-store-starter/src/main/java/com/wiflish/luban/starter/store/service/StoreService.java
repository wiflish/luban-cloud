package com.wiflish.luban.starter.store.service;

import com.wiflish.luban.starter.store.cmd.StoreUploadCmd;

/**
 * @author wiflish
 * @since 2023-11-30
 */
public interface StoreService {
    String getUploadToken(StoreUploadCmd storeUploadCmd);
}
