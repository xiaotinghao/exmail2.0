package com.xs.common.utils;

import java.util.HashMap;
import java.util.Map;

import static com.xs.module.constants.ConstantsBase.MAP_INITIAL_CAPACITY;

/**
 * Map工具类
 *
 * @author 18871430207@163.com
 */
public class MapUtils {

    /**
     * 初始化一个Map对象
     *
     * @param <K> key的泛型
     * @param <V> value的泛型
     * @return Map对象
     */
    public static <K, V> Map<K, V> init() {
        return new HashMap<>(MAP_INITIAL_CAPACITY);
    }

}
