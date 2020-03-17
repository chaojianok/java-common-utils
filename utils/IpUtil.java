package com.chaojianok.java.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取客户端IP地址 . <br>
 */
public final class IpUtil {

    /**
     * 私有构造函数
     */
    private IpUtil() {
        super();
    }

    /**
     * 获取客户端ip地址
     *
     * @param request
     * @return String
     */
    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        String unknownStr = "unknown";
        if (StringUtils.isEmpty(ip) || StringUtils.equalsIgnoreCase(unknownStr, ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || ip.length() == 0 || StringUtils.equalsIgnoreCase(unknownStr, ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || StringUtils.equalsIgnoreCase(unknownStr, ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isEmpty(ip) || StringUtils.equalsIgnoreCase(unknownStr, ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isEmpty(ip) || StringUtils.equalsIgnoreCase(unknownStr, ip)) {
            ip = request.getRemoteAddr();
        }

        // 多个路由时取第一个非unknown的ip
        final String[] arr = ip.split(",");
        for (final String str : arr) {
            if (!StringUtils.equalsIgnoreCase(ip, unknownStr)) {
                ip = str;
                break;
            }
        }
        return ip;
    }

}
