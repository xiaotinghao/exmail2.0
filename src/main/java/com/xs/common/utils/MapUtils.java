package com.xs.common.utils;

import com.xs.common.constants.ConstantsBase;

import java.util.HashMap;
import java.util.Map;

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
        int initialCapacity = Integer.valueOf(ConstantsBase.map.get("MAP_INITIAL_CAPACITY"));
        return new HashMap<>(initialCapacity);
    }

}
