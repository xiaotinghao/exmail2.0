package com.xs.common.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Map工具类
 *
 * @author 18871430207@163.com
 */
public class XsMapUtils {

    /**
     * pojo 转 map
     *
     * @param t 对象
     * @return 转换结果
     */
    public static <T> Map<String, Object> convert2Map(T t) {
        Map<String, Object> result = MapUtils.init();
        Method[] methods = t.getClass().getMethods();
        try {
            for (Method m : methods) {
                Class<?>[] paramCls = m.getParameterTypes();
                if (paramCls.length > 0) {
                    continue;
                }
                String methodName = m.getName();
                if (methodName.startsWith("get")) {
                    Object va = m.invoke(t);
                    String field = StringUtils.uncapitalize(methodName.substring(3));
                    result.put(field, va);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * pojo 转 map
     * map 转 pojo JSON.parseObject(JSON.toJSONString(map), Entity.class);
     *
     * @param t 对象
     * @return 转换结果
     */
    public static <T> Map<String, Object> convertToMap(T t) {
        return JSON.parseObject(JSON.toJSONString(t), Map.class);
    }

    /**
     * 对象集合 转 map集合
     *
     * @param list 对象集合
     * @return 转换结果
     */
    public static <T> List<Map<String, Object>> convert2MapList(List<T> list) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (T t : list) {
            Map<String, Object> result = convert2Map(t);
            resultList.add(result);
        }
        return resultList;
    }

}
