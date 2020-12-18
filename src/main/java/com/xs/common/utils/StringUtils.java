package com.xs.common.utils;

import static com.xs.common.constants.SymbolConstants.UNDERLINE;

/**
 * 字符串工具类
 *
 * @author 18871430207@163.com
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    /**
     * 下划线命名转为驼峰命名
     *
     * @param para 下划线命名的字符串
     */
    public static String underlineToHump(String para) {
        StringBuilder result = new StringBuilder();
        String[] a = para.split(UNDERLINE);
        for (String s : a) {
            if (!para.contains(UNDERLINE)) {
                result.append(s);
                continue;
            }
            if (result.length() == 0) {
                result.append(s.toLowerCase());
            } else {
                result.append(s.substring(0, 1).toUpperCase());
                result.append(s.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

    /**
     * 驼峰命名转为下划线命名
     *
     * @param para 驼峰命名的字符串
     */
    public static String humpToUnderline(String para) {
        StringBuilder sb = new StringBuilder(para);
        // 定位
        int temp = 0;
        if (!para.contains(UNDERLINE)) {
            for (int i = 0; i < para.length(); i++) {
                if (Character.isUpperCase(para.charAt(i))) {
                    sb.insert(i + temp, UNDERLINE);
                    temp += 1;
                }
            }
        }
        return sb.toString().toLowerCase();
    }

    /**
     * 驼峰命名、下划线命名 互相转换
     *
     * @param str 字符串
     */
    public static String humpUnderlineExchange(String str) {
        if (str != null) {
            if (str.contains(UNDERLINE)) {
                return underlineToHump(str);
            } else {
                return humpToUnderline(str);
            }
        }
        return null;
    }

    /**
     * 拆分字符串
     */
    public static String[] splitString(String string, int len) {
        int x = string.length() / len;
        int y = string.length() % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }
        String[] strings = new String[x + z];
        String str;
        for (int i = 0; i < x + z; i++) {
            if (i == x + z - 1 && y != 0) {
                str = string.substring(i * len, i * len + y);
            } else {
                str = string.substring(i * len, i * len + len);
            }
            strings[i] = str;
        }
        return strings;
    }


}
