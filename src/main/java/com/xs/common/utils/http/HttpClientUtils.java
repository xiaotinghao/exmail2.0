package com.xs.common.utils.http;

import java.nio.charset.Charset;

import com.alibaba.fastjson.JSON;
import com.xs.common.entity.Result;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * Http客户端请求工具类
 *
 * @author 18871430207@163.com
 */
public class HttpClientUtils {

    private static int connectTimeout = 5000;
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
        Result result = new Result();
        HttpClient client = HttpClients.createDefault();
        uriRequest.addHeader("Content-type", "application/json;charset=utf-8");
        uriRequest.setHeader("Accept", "application/json");
        try {
            HttpResponse httpResponse = client.execute(uriRequest);
            String response = EntityUtils.toString(httpResponse.getEntity());
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = Result.success(response);
            } else {
                result = Result.fail(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSONObject.toJSONString(result);
    }

    public static void main(String[] args) {
        String targetURL = "http://services.cytsbiz.com/BaokuAirWebAPI/api/RuiAnAirOrder";
        String requestStr = "{\"departBeginTime\":\"" + "2020-11-10" + "\"," +
                "\"departEndTime\":\"" + "2020-11-15" + "\"}";
		String res = HttpClientUtils.doPost(targetURL, requestStr);
        JSONObject jsonObject = JSON.parseObject(res);
        String code = jsonObject.getString("code");
        if ("1".equals(code)) {
            System.out.println(jsonObject.get("data"));
        } else {
            System.out.println(jsonObject.get("msg"));
        }

        targetURL += "?departBeginTime=" + "2020-11-10" +
                "&departEndTime=" + "2020-11-15";
		String res2 = HttpClientUtils.doGet(targetURL);
        JSONObject jsonObject2 = JSON.parseObject(res2);
        String code2 = jsonObject2.getString("code");
        if ("1".equals(code2)) {
            System.out.println(jsonObject2.get("data"));
        } else {
            System.out.println(jsonObject2.get("msg"));
        }
    }

}
