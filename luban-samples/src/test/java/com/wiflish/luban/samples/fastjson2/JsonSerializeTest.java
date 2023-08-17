package com.wiflish.luban.samples.fastjson2;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.wiflish.luban.samples.vo.UserAddress;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author wiflish
 * @since 2023-08-17
 */
@Slf4j
public class JsonSerializeTest {
    @Test
    public void testSerialize() {
        UserAddress userAddress = new UserAddress();
        userAddress.setAddress("福田区");
        userAddress.setZipcode("518000");

        String out = JSON.toJSONString(userAddress, JSONWriter.Feature.WriteClassName);

        log.info("序列化: {}", out);
        UserAddress parse = (UserAddress) JSON.parse(out, JSONReader.Feature.SupportAutoType);
        log.info("反序列化: add={}, code={}", parse.getAddress(), parse.getZipcode());

        userAddress.setAddress("福田区");
        userAddress.setZipcode("518003");
        out = JSON.toJSONString(userAddress);

        log.info("序列化: {}", out);
        parse = (UserAddress) JSON.parse(out, JSONReader.Feature.SupportAutoType);
        log.info("反序列化: add={}, code={}", parse.getAddress(), parse.getZipcode());

    }
}
