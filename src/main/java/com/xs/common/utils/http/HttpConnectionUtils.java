package com.xs.common.utils.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xs.common.entity.Result;
import org.apache.http.HttpStatus;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.xs.common.constants.WebConstants.GET;
import static com.xs.common.constants.WebConstants.POST;

/**
 * Http连接工具类
 *
 * @author xiaotinghao
 */
public class HttpConnectionUtils {

    private static int connectTimeout = 5000;
    private static int readTimeout = 30000;

    /**
     * POST请求
     *
     * @param targetURL 请求路径
     * @param jsonData  请求参数
     * @return http响应
     */
    public static String post(String targetURL, String jsonData) {
        return connect(targetURL, POST, jsonData);
    }

    /**
     * GET请求
     *
     * @param targetURL 请求路径（请求参数拼接到url中）
     * @return http响应
     */
    public static String get(String targetURL) {
        return connect(targetURL, GET, null);
    }

    private static String connect(String targetURL, String requestMethod, String jsonData) {
        StringBuilder output = new StringBuilder();
        try {
            URL targetUrl = new URL(targetURL);
            HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod(requestMethod);
            httpConnection.setRequestProperty("Content-Type", "application/json");
            httpConnection.setConnectTimeout(connectTimeout);
            httpConnection.setReadTimeout(readTimeout);
            OutputStream outputStream = httpConnection.getOutputStream();
            // 参数
            if (null != jsonData) {
                outputStream.write(jsonData.getBytes());
                outputStream.flush();
            }
            InputStream is;
            if (httpConnection.getResponseCode() == HttpStatus.SC_OK) {
                is = httpConnection.getInputStream();
            } else {
                is = httpConnection.getErrorStream();
            }
            int count = 0;
            while (count == 0) {
                count = is.available();
            }
            byte[] bytes = new byte[count];
            int size;
            while ((size = is.read(bytes)) != -1) {
                String str = new String(bytes, 0, size, "UTF-8");
                output.append(str);
            }
            is.close();
            httpConnection.disconnect();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSONObject.toJSONString(Result.success(output.toString()));
    }

    public static void main(String[] args) {
        String targetURL = "http://services.cytsbiz.com/BaokuAirWebAPI/api/RuiAnAirOrder";
        String requestStr = "{\"departBeginTime\":\"" + "2020-11-10" + "\"," +
                "\"departEndTime\":\"" + "2020-11-15" + "\"}";
        String res = HttpConnectionUtils.post(targetURL, requestStr);
        JSONObject jsonObject = JSON.parseObject(res);
        String code = jsonObject.getString("code");
        if ("1".equals(code)) {
            System.out.println(jsonObject.get("data"));
        } else {
            System.out.println(jsonObject.get("msg"));
        }

        targetURL += "?departBeginTime=" + "2020-11-10" +
                "&departEndTime=" + "2020-11-15";
        String res2 = HttpConnectionUtils.get(targetURL);
        JSONObject jsonObject2 = JSON.parseObject(res2);
        String code2 = jsonObject2.getString("code");
        if ("1".equals(code2)) {
            System.out.println(jsonObject2.get("data"));
        } else {
            System.out.println(jsonObject2.get("msg"));
        }
    }

}
