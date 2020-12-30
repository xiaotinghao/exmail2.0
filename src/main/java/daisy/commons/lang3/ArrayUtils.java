package daisy.commons.lang3;

import com.xs.common.utils.XsUtils;

import java.util.*;

import static com.xs.common.constants.SymbolConstants.COMMA;

/**
 * 数组工具类
 *
 * @author 18871430207@163.com
 */
public class ArrayUtils extends org.apache.commons.lang3.ArrayUtils {

    /**
     * 合并数组
     */
    public static <T> Object[] join(T[]... arrays) {
        List<T> list = new ArrayList<>();
        for (T[] array : arrays) {
            Collections.addAll(list, array);
        }
        return list.toArray();
    }

    public static <T> List<T> toList(T[] array) {
        return Arrays.asList(array);
    }

    public static <T> List<T> toList(String str, Class<T> clazz) {
        return toList(str, COMMA, clazz);
    }

    public static <T> List<T> toList(String str, String regex, Class<T> clazz) {
        if (clazz == null) {
            return null;
        }
        if (!ClassUtils.isPrimitiveWrapper(clazz)) {
            return null;
        }
        List<T> resultList = new ArrayList<>();
        for (String item : str.split(regex)) {
            Object cast = XsUtils.cast(clazz, item);
            resultList.add(XsUtils.cast(cast));
        }
        return resultList;
    }

    /**
     * 拆分数组
     */
    public static byte[][] split(byte[] data, int len) {
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

}
