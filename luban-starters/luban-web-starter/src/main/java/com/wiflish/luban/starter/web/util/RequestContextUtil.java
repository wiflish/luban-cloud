package com.wiflish.luban.starter.web.util;

import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author wiflish
 * @since 2023-11-19
 */
public class RequestContextUtil {
    public static HttpServletRequest getHttpServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        return requestAttributes == null ? null : ((ServletRequestAttributes) requestAttributes).getRequest();
    }

    public static HttpServletResponse getHttpServletResponse() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        return requestAttributes == null ? null : ((ServletRequestAttributes) requestAttributes).getResponse();
    }

    public static Map<String, String> getParaMap(HttpServletRequest request) {
        Enumeration<String> paramEnumeration = request.getParameterNames();

        Map<String, String> params = new HashMap<>();
        while (paramEnumeration.hasMoreElements()) {
            String parameter = paramEnumeration.nextElement();
            String value = request.getParameter(parameter);
            if (StrUtil.isNotBlank(value)) {
                params.put(parameter, value);
            }
        }

        return params;
    }

    public static String getDomain(HttpServletRequest request) {
        String domain = request.getHeader(HttpHeaders.ORIGIN);
        if (StrUtil.isBlank(domain)) {
            domain = request.getHeader(HttpHeaders.REFERER);
        }
        return StrUtil.removeSuffix(domain, "/");
    }

    public static String getClientIP() {
        HttpServletRequest request = getHttpServletRequest();
        if (request == null) {
            return "UNKNOWN";
        }
        String ip = request.getHeader("X-Forwarded-For");
        if (StrUtil.isEmpty(ip) || "UNKNOWN".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (StrUtil.isEmpty(ip) || "UNKNOWN".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StrUtil.isEmpty(ip) || "UNKNOWN".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StrUtil.isEmpty(ip) || "UNKNOWN".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }
}