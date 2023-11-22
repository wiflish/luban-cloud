package com.wiflish.luban.tools.invoker;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author wiflish
 * @since 2023-11-20
 */
public class CommonInvokerTest {

    @Test
    public void testGetRealAddress() {
        String realAddress = CommonInvoker.getRealAddress("47.111.117.8");
        assertEquals("浙江省 杭州市", realAddress);

        realAddress = CommonInvoker.getRealAddress("192.168.1.1");
        assertEquals("未知地址", realAddress);
    }
}