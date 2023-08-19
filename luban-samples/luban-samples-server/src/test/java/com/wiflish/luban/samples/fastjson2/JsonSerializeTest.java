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
package com.wiflish.luban.samples.fastjson2;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.wiflish.luban.samples.mybatis.domain.vo.UserAddress;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.*;
@Slf4j
public class JsonSerializeTest {
    @Test
    public void testSerializeWriteClassName() {
        UserAddress userAddress = new UserAddress();
        userAddress.setAddress("福田区");
        userAddress.setZipcode("518000");

        String out = JSON.toJSONString(userAddress, JSONWriter.Feature.WriteClassName);
        assertNotNull(out);
        assertTrue(out.contains(UserAddress.class.getName()));

        UserAddress userAddress1 = (UserAddress) JSON.parse(out, JSONReader.Feature.SupportAutoType);

        assertNotNull(userAddress1);
        assertEquals("福田区", userAddress1.getAddress());
        assertEquals("518000", userAddress1.getZipcode());
    }

    @Test(expected = ClassCastException.class)
    public void testSerializeNoClassName() {
        UserAddress userAddress = new UserAddress();
        userAddress.setAddress("福田区");
        userAddress.setZipcode("518000");

        String out = JSON.toJSONString(userAddress);
        assertNotNull(out);
        assertTrue(out.contains("\"address\":\"福田区\""));
        assertFalse(out.contains("com.wiflish.luban.samples.vo.UserAddress"));

        UserAddress userAddress1 = (UserAddress) JSON.parse(out, JSONReader.Feature.SupportAutoType);
        assertNotNull(userAddress1);
        assertEquals("福田区", userAddress1.getAddress());
        assertEquals("518000", userAddress1.getZipcode());
    }
}
