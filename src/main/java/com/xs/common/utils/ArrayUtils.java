package com.xs.common.utils;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

/**
 * 数组工具类
 *
 * @author 18871430207@163.com
 */
public class ArrayUtils extends org.apache.commons.lang3.ArrayUtils {

    /**
     * 拆分数组
     */
    public static byte[][] splitArray(byte[] data, int len) {
        int x = data.length / len;
        int y = data.length % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }
        byte[][] arrays = new byte[x + z][];
        byte[] arr;
        for (int i = 0; i < x + z; i++) {
            arr = new byte[len];
            if (i == x + z - 1 && y != 0) {
                System.arraycopy(data, i * len, arr, 0, y);
            } else {
                System.arraycopy(data, i * len, arr, 0, len);
            }
            arrays[i] = arr;
        }
        return arrays;
    }

    public String[] toArray (Collection<Object> o) {
        return o.toArray(new String[o.size()]);
    }

}
