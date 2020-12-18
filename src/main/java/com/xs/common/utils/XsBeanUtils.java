package com.xs.common.utils;

import org.springframework.beans.BeanUtils;

/**
 * pojo转换工具类
 *
 * @author 18871430207@163.com
 */
public class XsBeanUtils {

    public static <T> T dtoToVo(Object dtoEntity, Class<T> doClass) {
        if (dtoEntity == null) {
            return null;
        }
        if (doClass == null) {
            return null;
        }
        try {
            T newInstance = doClass.newInstance();
            BeanUtils.copyProperties(dtoEntity, newInstance);
            return newInstance;
        } catch (Exception e) {
            return null;
        }

    }

}
