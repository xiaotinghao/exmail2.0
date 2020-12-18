package com.xs.common.constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 读取`t_result_code_msg`表数据获取常量
 *
 * @author 18871430207@163.com
 */
public class ResultCodeMsg extends ConstantsInitializer {

    /**
     * map的key与`t_result_code_msg`表字段`key`一一对应
     */
    public static Map<String, Map<String, Object>> map;

    static {
        if (map == null) {
            map = new HashMap<>();
            List<Map<String, Object>> mapList = constantsDao.listCodeMsg();
            // `t_result_code_msg`表字段`key`
            String constantsKey = "key";
            for (Map<String, Object> entryMap : mapList) {
                String key = (String) entryMap.get(constantsKey);
                entryMap.remove(constantsKey);
                map.put(key, entryMap);
            }
        }
    }

}
