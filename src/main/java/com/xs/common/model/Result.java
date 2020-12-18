package com.xs.common.model;

import com.alibaba.fastjson.JSON;
import com.xs.common.constants.ConstantsConfig;
import com.xs.common.constants.ResultCodeMsg;
import com.xs.common.utils.MapUtils;

import java.util.Map;

/**
 * 返回数据封装实体
 *
 * @author 18871430207@163.com
 */
public class Result {

    private static String code_key = ConstantsConfig.get("CODE_KEY", "code");
    private static String msg_key = ConstantsConfig.get("MSG_KEY", "msg");
    private static String data_key = ConstantsConfig.get("DATA_KEY", "data");

    /**
     * 通过key查询其在`t_result_code_msg`表中的配置值
     *
     * @param key 关键字
     * @return 配置值, Json字符串
     */
    public static String get(String key) {
        return JSON.toJSONString(ResultCodeMsg.get(key));
    }

    public static String make(Integer code, String msg) {
        Map<String, Object> errorMap = MapUtils.init();
        errorMap.put(code_key, code);
        errorMap.put(msg_key, msg);
        return JSON.toJSONString(errorMap);
    }

    public static String make(Integer code, String msg, Object data) {
        Map<String, Object> errorMap = MapUtils.init();
        errorMap.put(code_key, code);
        errorMap.put(msg_key, msg);
        errorMap.put(data_key, data);
        return JSON.toJSONString(errorMap);
    }

    public static String success() {
        return JSON.toJSONString(ResultCodeMsg.get("SUCCESS_CODE_MESSAGE"));
    }

    public static String success(Object data) {
        Map<String, Object> successMap = ResultCodeMsg.get("SUCCESS_CODE_MESSAGE");
        successMap.put(data_key, data);
        return JSON.toJSONString(successMap);
    }

    public static String error() {
        return JSON.toJSONString(ResultCodeMsg.get("ERROR_CODE_MESSAGE"));
    }

    public static String error(String msg) {
        Map<String, Object> errorMap = ResultCodeMsg.get("ERROR_CODE_MESSAGE");
        errorMap.put(msg_key, msg);
        return JSON.toJSONString(errorMap);
    }

}
