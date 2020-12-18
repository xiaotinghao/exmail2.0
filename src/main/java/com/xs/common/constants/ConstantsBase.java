package com.xs.common.constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 读取`t_constants_*`表数据获取常量
 *
 * @author 18871430207@163.com
 */
public class ConstantsBase extends ConstantsInitializer {

    /**
     * map的key与`t_constants_*`表字段`constants_key`一一对应
     */
    public static Map<String, String> map;

    static {
        if (map == null) {
            map = new HashMap<>();
            String tables = constantsDao.constantsTables();
            List<Map<String, Object>> mapList = constantsDao.listConstants(tables);
            for (Map<String, Object> entryMap : mapList) {
                // `t_constants_*`表字段`constants_key`
                String key = (String) entryMap.get("constants_key");
                // `t_constants_*`表字段`constants_value`
                String value = (String) entryMap.get("constants_value");
                map.put(key, value);
            }
        }
    }

}
