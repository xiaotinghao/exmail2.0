package com.xs.common.constants;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

/**
 * Web端常用常量类
 *
 * @author 18871430207@163.com
 */
public class WebConstants {

    /**
     * POST请求
     */
    public static final String POST = HttpPost.METHOD_NAME;

    /**
     * GET请求
     */
    public static final String GET = HttpGet.METHOD_NAME;

    /**
     * 未知
     */
    public static final String UNKNOWN = "unknown";

    /**
     * 本机IP
     */
    public static final String LOCAL_IP = "127.0.0.1";

    /**
     * 本机主机名
     */
    public static final String LOCALHOST = "localhost";

    /**
     * 公共网关接口(Common Gateway Interface)
     */
    public static final String CGI = "cgi";

    /**
     * 应用程序接口(Application Program Interface)
     */
    public static final String API = "api";

}
