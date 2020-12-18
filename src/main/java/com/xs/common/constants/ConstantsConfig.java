package com.xs.common.constants;

import com.xs.common.utils.MapUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 读取`t_constants_*`表数据获取常量
 *
 * @author 18871430207@163.com
 */
public class ConstantsConfig extends ConstantsInitializer {

    /**
     * map的key与`t_constants_*`表字段`constants_key`一一对应
     */
    private static Map<String, String> map;

    static {
        if (map == null) {
            map = MapUtils.init();
            String tables = constantsDao.constantsTables();
            List<Map<String, Object>> mapList = new ArrayList<>();
            try {
                mapList.addAll(constantsDao.listConstants(tables));
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (Map<String, Object> entryMap : mapList) {
                // `t_constants_*`表字段`constants_key`
                String key = (String) entryMap.get("constants_key");
                // `t_constants_*`表字段`constants_value`
                String value = (String) entryMap.get("constants_value");
                map.put(key, value);
            }
        }
    }

    /**
     * 通过key查询其在`t_constants_*`表中的配置值
     *
     * @param key 关键字
     * @return 配置值
     */
    public static String get(String key) {
        return map.get(key);
    }

}
