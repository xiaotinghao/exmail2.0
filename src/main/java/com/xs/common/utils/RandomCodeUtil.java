package com.xs.common.utils;

/**
 * 随机码工具类
 *
 * @author xiaotinghao
 */
public class RandomCodeUtil {

    /**
     * 随机码集合
     */
    private static final String[] RANDOM_CODE = {"0", "1", "2", "3", "4", "5", "6",
            "7", "8", "9", "q", "w", "e", "r", "t", "y", "u", "i", "o", "p",
            "a", "s", "d", "f", "g", "h", "j", "k", "l", "z", "x", "c", "v",
            "b", "n", "m"};

    /**
     * @param codeLength
     * @return
     * @Description: 产生指定长度的随机码
     * @Title: RandomCodeUtil.java
     */
    public static String randomCode(Integer codeLength) throws Exception {
        try {
            StringBuffer code = new StringBuffer();
            if (null == codeLength || 0 == codeLength) {
                codeLength = 4;
            }
            for (int i = 0; i < codeLength; i++) {
                code.append(RANDOM_CODE[(int) Math.floor(Math.random() * 36)]);
            }
            return code.toString();
        } catch (Exception e) {
            throw new RuntimeException("Random Code Error");
        }
    }

    /**
     * @return
     * @throws Exception
     * @Description: 生成长度为4的随机码
     * @Title: RandomCodeUtil.java
     * @Copyright: Copyright (c) 2014
     * @author Comsys-LZP
     * @date 2014-5-28 下午01:19:33
     * @version V2.0
     */
    public static String randomCode() throws Exception {
        return randomCode(null);
    }

}
