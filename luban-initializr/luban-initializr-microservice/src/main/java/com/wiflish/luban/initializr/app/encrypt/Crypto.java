package com.wiflish.luban.initializr.app.encrypt;

import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SM4;

/**
 * @author wiflish
 * @since 2022-11-04
 */
public class Crypto {
    public static void main(String[] args) {
        SM4 sm4 = SmUtil.sm4();
        String phone = "1234567890";
        String encryptHex = sm4.encryptHex(phone);

        System.out.println(encryptHex);

        System.out.println(sm4.encryptHex("123"));
    }
}
