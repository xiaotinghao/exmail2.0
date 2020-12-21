package com.xs.common.utils.http;

import com.alibaba.fastjson.JSON;
import com.xs.common.utils.MapUtils;
import com.xs.common.utils.StringUtils;
import com.xs.common.utils.XsUtils;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.util.StreamUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import static com.xs.common.constants.SymbolConstants.*;
import static com.xs.common.constants.WebConstants.*;

/**
 * Http请求工具类
 *
 * @author 18871430207@163.com
 */
public class HttpUtils {

    private static ServletRequestAttributes servletRequestAttributes;

    static {
        try {
            // 获取RequestAttributes
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取当前HttpServletRequest对象
     *
     * @return HttpServletRequest
     */
    private static HttpServletRequest getCurrentRequest() {
        if (servletRequestAttributes != null) {
            // 从获取RequestAttributes中获取HttpServletRequest的信息
            return servletRequestAttributes.getRequest();
        }
        return new MockHttpServletRequest();
    }

    /**
     * 获取当前HttpServletResponse对象
     *
     * @return HttpServletResponse
     */
    private static HttpServletResponse getCurrentResponse() {
        if (servletRequestAttributes != null) {
            // 从获取RequestAttributes中获取HttpServletResponse的信息
            return servletRequestAttributes.getResponse();
        }
        return new MockHttpServletResponse();
    }

    /**
     * 获取HttpServletRequest请求参数
     */
    private static Map<String, Object> getRequestParam() {
        return getRequestParam(getCurrentRequest());
    }

    /**
     * 获取HttpServletRequest请求参数
     *
     * @param request Http请求
     */
    public static Map<String, Object> getRequestParam(HttpServletRequest request) {
        // 获取请求参数
        Map<String, Object> map = MapUtils.init();
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String key = entry.getKey();
            String[] value = entry.getValue();
            map.put(key, StringUtils.join(value, COMMA));
        }
        // 获取请求body
        byte[] bodyBytes;
        String body = null;
        try {
            bodyBytes = StreamUtils.copyToByteArray(request.getInputStream());
            body = new String(bodyBytes, request.getCharacterEncoding());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (StringUtils.isNotEmpty(body)) {
            map.putAll(XsUtils.cast(JSON.parseObject(body, Map.class)));
        }
        return map;
    }

    /**
     * 向Http中写入响应信息
     *
     * @param msg 响应信息
     */
    public static void write(String msg) {
        write(getCurrentResponse(), msg);
    }

    /**
     * 向Http中写入响应信息
     *
     * @param response Http响应
     * @param msg      响应信息
     */
    public static void write(HttpServletResponse response, String msg) {
        try {
            response.getWriter().write(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回HTTP错误信息
     *
     * @param code HTTP状态码
     */
    public static void sendError(int code) {
        try {
            getCurrentResponse().sendError(code);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回HTTP错误信息
     *
     * @param code HTTP状态码
     * @param msg  响应信息
     */
    public static void sendError(int code, String msg) {
        try {
            getCurrentResponse().sendError(code, msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取客户端真实IP地址
     *
     * @return 客户端真实IP地址
     */
    private static String getClientRealIp() {
        return getClientRealIp(getCurrentRequest());
    }

    /**
     * 获取客户端的真实IP地址
     *
     * @param request Http请求
     * @return 客户端的真实IP地址
     */
    public static String getClientRealIp(HttpServletRequest request) {
        String ipAddress;
        ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("X-Real-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (LOCAL_IP.equals(ipAddress) || POSTMAN_IP_HOST.equals(ipAddress)) {
                // 根据网卡取本机配置的IP
                try {
                    ipAddress = InetAddress.getLocalHost().getHostAddress();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (StringUtils.isNotEmpty(ipAddress) && !UNKNOWN.equalsIgnoreCase(ipAddress)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ipAddress.indexOf(",");
            if (index != -1) {
                ipAddress = ipAddress.substring(0, index);
            }
        }
        return ipAddress;
    }

    /**
     * 获取客户端的真实主机名
     *
     * @return 客户端的真实主机名
     */
    private static String getClientHostName() {
        return getClientHostName(getCurrentRequest());
    }

    /**
     * 获取客户端的真实主机名
     *
     * @param request Http请求
     * @return 客户端的真实主机名
     */
    public static String getClientHostName(HttpServletRequest request) {
        String remoteHost = request.getRemoteHost();
        if (LOCALHOST.equals(remoteHost) || POSTMAN_IP_HOST.equals(remoteHost)) {
            // 根据网卡取本机主机名
            try {
                remoteHost = InetAddress.getLocalHost().getHostName();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return remoteHost;
    }

}
