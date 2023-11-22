package com.wiflish.luban.tools.invoker;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用外部调用
 *
 * @author wiflish
 * @since 2023-11-20
 */
@Slf4j
public class CommonInvoker {
    public static final String DEFAULT_ADDRESS = "未知地址";
    public static final String IP_ADDRESS_URL = "https://whois.pconline.com.cn/ipJson.jsp";

    /**
     * 根据ip地址查询对应的省市.
     *
     * @param ip
     * @return
     */
    public static String getRealAddress(String ip) {
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("ip", ip);
            paramMap.put("json", true);
            String response = HttpUtil.get(IP_ADDRESS_URL, paramMap, 2000);
            if (StrUtil.isEmpty(response)) {
                log.warn("根据IP获取不到地址: {}", ip);
                return DEFAULT_ADDRESS;
            }

            JSONObject jsonObject = JSON.parseObject(response);
            String pro = jsonObject.getString("pro");
            String city = jsonObject.getString("city");

            if (StrUtil.isEmpty(pro) && StrUtil.isEmpty(city)) {
                return DEFAULT_ADDRESS;
            }
            return pro + " " + city;
        } catch (Exception e) {
            log.error("根据IP获取地址异常 {}", ip, e);
        }
        return DEFAULT_ADDRESS;
    }
}
