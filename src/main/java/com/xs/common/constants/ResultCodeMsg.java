package com.xs.common.constants;

import com.alibaba.fastjson.JSONObject;
import com.xs.common.utils.MapUtils;
import com.xs.common.utils.XsUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 读取`t_result_code_msg`表数据获取常量
 *
 * @author 18871430207@163.com
 */
public class ResultCodeMsg extends ConstantsInitializer {

    /**
     * key与`t_result_code_msg`表字段`key`一一对应
     * value 类型为Map<String,Object>
     */
    private static JSONObject jsonObject;

    static {
        if (jsonObject == null) {
            jsonObject = new JSONObject();
            List<Map<String, Object>> mapList = new ArrayList<>();
            try {
                mapList.addAll(constantsDao.listCodeMsg());
            } catch (Exception e) {
                e.printStackTrace();
            }
            // `t_result_code_msg`表字段`key`
            String constantsKey = "key";
            for (Map<String, Object> entryMap : mapList) {
                String key = (String) entryMap.get(constantsKey);
                entryMap.remove(constantsKey);
                jsonObject.put(key, entryMap);
            }
        }
    }

    /**
     * 通过key查询其在`t_result_code_msg`表中的配置值
     *
     * @param key 关键字
     * @return 配置值
     */
    public static Map<String, Object> get(String key) {
        Map<String, Object> objectMap = XsUtils.cast(jsonObject.get(key));
        return objectMap == null ? MapUtils.init() : objectMap;
    }

}
