package com.xs.common.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static com.xs.common.constants.SymbolConstants.COMMA;
import static com.xs.common.constants.SymbolConstants.LINE_BREAK;

/**
 * 列表工具类
 *
 * @author 18871430207@163.com
 */
public class CollectionUtils extends org.springframework.util.CollectionUtils {

    public static <T> String toString(Collection<T> collection) {
        return toString(collection, COMMA);
    }

    public static <T> String toString(Collection<T> collection, String separator) {
        StringBuilder sb = new StringBuilder();
        Iterator iterator = collection.iterator();
        while (iterator.hasNext()) {
            Object next = iterator.next();
            sb.append(separator).append(String.valueOf(next));
        }
        if (LINE_BREAK.equals(separator)) {
            return sb.toString();
        }
        return sb.length() > 0 ? sb.substring(separator.length()) : sb.toString();
    }

    public static <T> void reverse(List<T> list) {
        Collections.reverse(list);
    }

}
