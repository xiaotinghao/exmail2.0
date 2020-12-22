package com.xs.common.constants;

import com.xs.common.dao.BaseDao;
import com.xs.common.utils.ClassUtils;
import com.xs.common.utils.XsUtils;
import com.xs.common.utils.spring.SpringTool;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 读取`t_constants_base`表数据获取常量
 *
 * @author 18871430207@163.com
 */
public class ConstantsBase {

    public static String MODULE_TOKEN;
    public static Long CORP_API_CALL_MINUTE_UPPER_LIMIT;
    public static Long CORP_API_CALL_HOUR_UPPER_LIMIT;
    public static Long IP_API_CALL_MINUTE_UPPER_LIMIT;
    public static Long IP_API_CALL_HOUR_UPPER_LIMIT;
    public static Integer MAP_INITIAL_CAPACITY;
    public static String CODE_KEY;
    public static String MSG_KEY;
    public static String DATA_KEY;
    public static String LOG_MSG_PREFIX;
    public static String HANDLE_START_TIME;

    public static void init() {
        ConstantsBase obj = ClassUtils.newInstance(ConstantsBase.class);
        BaseDao baseDao = SpringTool.getBean(BaseDao.class);
        List<String> columns = baseDao.queryColumns("t_constants_base");
        Field[] fields = obj.getClass().getFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            Class<?> fieldType = field.getType();
            if (!columns.contains(fieldName)) {
                // `t_constants_base`表中无fieldName配置，请手动添加
            } else {
                String value = baseDao.getValue(fieldName);
                Object cast = XsUtils.cast(fieldType, value);
                try {
                    field.set(obj, cast);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
