package com.xs.common.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xs.common.utils.MapUtils;

import java.util.Map;

import static com.xs.module.constants.ConstantsBase.CODE_KEY;
import static com.xs.module.constants.ConstantsBase.DATA_KEY;
import static com.xs.module.constants.ConstantsBase.MSG_KEY;
import static com.xs.module.constants.ResultCodeMsg.ERROR_CODE_MESSAGE;
import static com.xs.module.constants.ResultCodeMsg.SUCCESS_CODE_MESSAGE;

/**
 * 返回数据封装实体
 *
 * @author 18871430207@163.com
 */
public class Result {

    public static String make(Integer code, String msg) {
        Map<String, Object> errorMap = MapUtils.init();
        errorMap.put(CODE_KEY, code);
        errorMap.put(MSG_KEY, msg);
        return JSON.toJSONString(errorMap);
    }

    public static String make(Integer code, String msg, Object data) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(CODE_KEY, code);
        jsonObject.put(MSG_KEY, msg);
        jsonObject.put(DATA_KEY, data);
        return jsonObject.toString();
    }

    public static String success() {
        return JSON.toJSONString(SUCCESS_CODE_MESSAGE);
    }

    public static String success(Object data) {
        JSONObject jsonObject = (JSONObject) JSON.toJSON(SUCCESS_CODE_MESSAGE);
        jsonObject.put(DATA_KEY, data);
        return jsonObject.toString();
    }

    public static String error() {
        return JSON.toJSONString(ERROR_CODE_MESSAGE);
    }

    public static String error(String msg) {
        JSONObject jsonObject = (JSONObject) JSON.toJSON(ERROR_CODE_MESSAGE);
        jsonObject.put(MSG_KEY, msg);
        return jsonObject.toString();
    }

}
