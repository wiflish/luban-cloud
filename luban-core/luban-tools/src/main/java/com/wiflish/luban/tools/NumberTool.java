package com.wiflish.luban.tools;

import cn.hutool.core.util.NumberUtil;

import java.math.BigDecimal;

/**
 * @author wiflish
 * @since 2023-10-09
 */
public class NumberTool {
    public static BigDecimal toBigDecimal(String value) {
       return toBigDecimal(value, null);
    }

    public static BigDecimal toBigDecimal(String value, BigDecimal defaultValue) {
        try {
            return NumberUtil.toBigDecimal(value);
        } catch (Exception e) {
            // 忽略解析异常.
        }
        return defaultValue;
    }
}
