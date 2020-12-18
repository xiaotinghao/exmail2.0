package com.xs.common.utils.http;

import java.nio.charset.Charset;

import com.xs.common.model.Result2;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


/**
 * Http客户端请求工具类
 *
 * @author 18871430207@163.com
 */
public class HttpClientUtils {

    private static int connectTimeout = 50001;
    private static int connectRequestTimeout = 30000;
    private static int socketTimeout = 5000;

    /**
     * POST请求
     *
     * @param url      请求路径
     * @param jsonData 请求参数
     * @return http响应
     */
    public static String doPost(String url, String jsonData) {
        HttpPost httpPost = new HttpPost(url);
        if (null != jsonData) {
            StringEntity stringEntity = new StringEntity(jsonData, Charset.forName("UTF-8"));
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
        }
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(connectRequestTimeout)
                .setSocketTimeout(socketTimeout).build();
        httpPost.setConfig(requestConfig);
        return execute(httpPost);
    }

    /**
     * GET请求
     *
     * @param url 请求路径（请求参数拼接到url中）
     * @return http响应
     */
    public static String doGet(String url) {
        HttpGet httpGet = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(connectRequestTimeout)
                .setSocketTimeout(socketTimeout).build();
        httpGet.setConfig(requestConfig);
        return execute(httpGet);
    }

    /**
     * 执行Http请求
     *
     * @param uriRequest 请求体
     * @return Http响应
     */
    private static String execute(HttpUriRequest uriRequest) {
        HttpClient client = HttpClients.createDefault();
        uriRequest.addHeader("Content-type", "application/json;charset=utf-8");
        uriRequest.setHeader("Accept", "application/json");
        try {
            HttpResponse httpResponse = client.execute(uriRequest);
            String response = EntityUtils.toString(httpResponse.getEntity());
            int httpStatusCode = httpResponse.getStatusLine().getStatusCode();
            return Result2.make(httpStatusCode, response);
        } catch (Exception e) {
            e.printStackTrace();
            return Result2.error(e.getMessage());
        }
    }

}
