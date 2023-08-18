/*-
 * ************
 * luban-cloud
 * ************
 * Copyright (C) 2023 - 2023 the original author or authors.
 * ************
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
package com.wiflish.luban.samples.featuretype;

import com.wiflish.luban.core.infra.po.FeatureBit;
import com.wiflish.luban.core.infra.po.FeatureJson;
import com.wiflish.luban.samples.LubanSamplesApplication;
import com.wiflish.luban.samples.featuretype.infra.dao.UserDao;
import com.wiflish.luban.samples.featuretype.infra.po.UserPO;
import com.wiflish.luban.samples.featuretype.domain.vo.UserAddress;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LubanSamplesApplication.class)
public class FeatureTypeHandlerTest {
    @Autowired
    private UserDao userDao;
    @Test
    public void testFeatureTypeHandler() {
        UserPO userPO = userDao.selectById(1);

        assertNotNull(userPO);
        assertEquals("张三1", userPO.getName());
        assertEquals(0, userPO.getFeatureBit().getValue());

        userPO = userDao.selectById(2);
        assertNotNull(userPO);
        assertEquals(10, userPO.getAge().intValue());
        assertTrue(userPO.getFeatureBit().getBits(1));

        UserAddress userAddress = userPO.getFeatureJson().getObject();
        assertTrue(userPO.getFeatureBit().getBits(1));
        assertEquals("福田区2", userAddress.getAddress());
        assertEquals("518002", userAddress.getZipcode());

        userPO = userDao.selectById(3);

        userAddress = userPO.getFeatureJson().getObject();

        assertNotNull(userPO);
        assertEquals("张三3", userPO.getName());
        assertEquals("zhangsan@qq.com", userPO.getEmail());
        assertEquals(10, userPO.getAge().intValue());
        assertFalse(userPO.getFeatureBit().getBits(1));
        assertTrue(userPO.getFeatureBit().getBits(2));
        assertEquals("福田区3", userAddress.getAddress());
        assertEquals("518003", userAddress.getZipcode());

        userPO = userDao.selectById(6);

        assertNotNull(userPO);
        assertEquals("张三6", userPO.getName());
        assertEquals("zhangsan@qq.com", userPO.getEmail());
        assertEquals(10, userPO.getAge().intValue());
        assertTrue(userPO.getFeatureBit().getBits(1));
        assertTrue(userPO.getFeatureBit().getBits(2));
        assertTrue(userPO.getFeatureBit().getBits(3));
        assertTrue(userPO.getFeatureBit().getBits(4));


        //insert
        UserPO userPO2 = new UserPO();

        userPO2.setName("Just for test");
        userPO2.setEmail("lisi@qq.com");
        userPO2.setFeatureBit(new FeatureBit(5L));

        UserAddress userAddress02 = new UserAddress();
        userAddress02.setAddress("福田区 for test");
        userAddress02.setZipcode("528000");

        userPO2.setFeatureJson(new FeatureJson(userAddress02));

        userDao.insert(userPO2);

        UserPO userPO1 = userDao.selectById(userPO2.getId());
        assertNotNull(userPO1);
        assertNull(userPO1.getAge());
        assertEquals("Just for test", userPO1.getName());
        assertEquals("lisi@qq.com", userPO1.getEmail());
        assertTrue(userPO1.getFeatureBit().getBits(1));
        assertFalse(userPO1.getFeatureBit().getBits(2));
        assertTrue(userPO1.getFeatureBit().getBits(4));
        assertFalse(userPO1.getFeatureBit().getBits(8));

        UserAddress userAddressTest = userPO1.getFeatureJson().getObject();
        assertNotNull(userPO1);
        assertEquals("福田区 for test", userAddressTest.getAddress());
        assertEquals("528000", userAddressTest.getZipcode());
    }

    @Test(expected = NullPointerException.class)
    public void testFeatureJsonTypeHandler() {
        UserPO userPO = userDao.selectById(6);
        UserAddress userAddress = userPO.getFeatureJson().getObject();
        assertNotNull(userPO);
        assertEquals("福田区3", userAddress.getAddress());
        assertEquals("518003", userAddress.getZipcode());
    }
}
