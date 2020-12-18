package com.xs.common.utils.http;

import com.xs.common.model.Result2;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Http连接工具类
 *
 * @author 18871430207@163.com
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
        return connect(targetURL, HttpPost.METHOD_NAME, jsonData);
    }

    /**
     * GET请求
     *
     * @param targetURL 请求路径（请求参数拼接到url中）
     * @return http响应
     */
    public static String get(String targetURL) {
        return connect(targetURL, HttpGet.METHOD_NAME, null);
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
        return Result2.success(output.toString());
    }

    /**
     * 抓取网站html信息
     *
     * @param netUrl 网站地址
     * @return 网站html信息
     */
    public static String getOuterNetIp(String netUrl) {
        String result = "";
        URLConnection connection;
        BufferedReader in = null;
        try {
            URL url = new URL(netUrl);
            connection = url.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "KeepAlive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);
            connection.connect();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.getMessage();
            }
        }
        return result;
    }

    public static void main(String[] args) {
//        String targetURL = "http://services.cytsbiz.com/BaokuAirWebAPI/api/RuiAnAirOrder";
//        String requestStr = "{\"departBeginTime\":\"" + "2020-11-10" + "\"," +
//                "\"departEndTime\":\"" + "2020-11-15" + "\"}";
//        String res = HttpConnectionUtils.post(targetURL, requestStr);
//        JSONObject jsonObject = JSON.parseObject(res);
//        String code = jsonObject.getString("code");
//        if ("1".equals(code)) {
//            System.out.println(jsonObject.get("data"));
//        } else {
//            System.out.println(jsonObject.get("msg"));
//        }
//
//        targetURL += "?departBeginTime=" + "2020-11-10" +
//                "&departEndTime=" + "2020-11-15";
//        String res2 = HttpConnectionUtils.get(targetURL);
//        JSONObject jsonObject2 = JSON.parseObject(res2);
//        String code2 = jsonObject2.getString("code");
//        if ("1".equals(code2)) {
//            System.out.println(jsonObject2.get("data"));
//        } else {
//            System.out.println(jsonObject2.get("msg"));
//        }

        String outerNetIp = getOuterNetIp("http://www.baidu.com");
        System.out.println(outerNetIp);

    }

}
