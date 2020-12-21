package com.xs.common.utils;

import java.util.Collection;

import static com.xs.common.constants.SymbolConstants.COMMA;

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

    public static String[] toArray(Collection<Object> o) {
        return o.toArray(new String[o.size()]);
    }

    public static String toString(Object[] arr) {
        return toString(arr, COMMA);
    }

    public static String toString(Object[] arr, String separator) {
        StringBuilder result = new StringBuilder();
        for (Object o : arr) {
            result.append(separator).append(o.toString());
        }
        return result.length() > 0 ? result.substring(separator.length()) : result.toString();
    }

}
