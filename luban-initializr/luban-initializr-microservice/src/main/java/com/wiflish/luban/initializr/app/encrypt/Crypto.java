/*-
 * ************
 * luban-cloud
 * ------------
 * Copyright (C) 2023 - 2023 the original author or authors.
 * ------------
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ************
 */
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
