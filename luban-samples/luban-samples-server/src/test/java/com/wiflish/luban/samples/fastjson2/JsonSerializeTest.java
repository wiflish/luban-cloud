package com.wiflish.luban.samples.fastjson2;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.wiflish.luban.samples.vo.UserAddress;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author wiflish
 * @since 2023-08-17
 */
@Slf4j
public class JsonSerializeTest {
    @Test
    public void testSerializeWriteClassName() {
        UserAddress userAddress = new UserAddress();
        userAddress.setAddress("福田区");
        userAddress.setZipcode("518000");

        String out = JSON.toJSONString(userAddress, JSONWriter.Feature.WriteClassName);
        assertNotNull(out);
        assertTrue(out.contains("com.wiflish.luban.samples.vo.UserAddress"));

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
