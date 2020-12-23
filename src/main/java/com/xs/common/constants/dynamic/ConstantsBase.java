package com.xs.common.constants.dynamic;

import com.alibaba.fastjson.JSONObject;
import com.xs.common.annotation.TableCheck;
import com.xs.common.dao.BaseDao;
import com.xs.common.utils.ClassUtils;
import com.xs.common.utils.XsUtils;
import com.xs.common.utils.spring.SpringTool;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * 基础常量类，动态获取`t_constants_base`表数据
 *
 * @author 18871430207@163.com
 */
@TableCheck(tableName = "t_constants_base")
public class ConstantsBase {

    public static Long CORP_API_CALL_MINUTE_UPPER_LIMIT;
    public static Long CORP_API_CALL_HOUR_UPPER_LIMIT;
    public static Long IP_API_CALL_MINUTE_UPPER_LIMIT;
    public static Long IP_API_CALL_HOUR_UPPER_LIMIT;
    public static Integer MAP_INITIAL_CAPACITY;
    public static String CODE_KEY;
    public static String MSG_KEY;
    public static String DATA_KEY;
    public static String LOG_MSG_PREFIX = "!!!";
    public static String HANDLE_START_TIME;

    static {
        Class<?> clazz = ConstantsBase.class;
        BaseDao baseDao = SpringTool.getBean(BaseDao.class);
        refresh(clazz, baseDao);
    }

    public static <T> void refresh(Class<T> clazz, BaseDao baseDao) {
        TableCheck tableCheckAnnotation = clazz.getAnnotation(TableCheck.class);
        if (tableCheckAnnotation != null) {
            String tableName = tableCheckAnnotation.tableName();
            List<Map<String, Object>> mapList = baseDao.list(tableName);
            JSONObject jsonObject = new JSONObject();
            for (Map<String, Object> entryMap : mapList) {
                // `t_constants_*`表字段`constants_key`
                String key = (String) entryMap.get("constants_key");
                // `t_constants_*`表字段`constants_value`
                String value = (String) entryMap.get("constants_value");
                jsonObject.put(key, value);
            }
            Object obj = ClassUtils.newInstance(clazz);
            Field[] fields = clazz.getFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                String value = jsonObject.getString(fieldName);
                Object cast = XsUtils.cast(field.getType(), value);
                try {
                    field.set(obj, cast);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
