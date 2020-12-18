package com.xs.common.utils;

import java.io.*;

/**
 * 输入输出流工具类
 *
 * @author 18871430207@163.com
 */
public class StreamUtils extends org.apache.commons.io.FileUtils {

    /**
     * 关闭流
     *
     * @param is  输入流
     * @param oss 输出流
     */
    public static void close(InputStream is, OutputStream... oss) {
        close(is);
        for (OutputStream os : oss) {
            close(os);
        }
    }

    /**
     * 关闭流
     *
     * @param os  输出流
     * @param iss 输入流
     */
    public static void close(OutputStream os, InputStream... iss) {
        close(os);
        for (InputStream is : iss) {
            close(is);
        }
    }

    /**
     * 关闭输出流
     *
     * @param os 输出流
     */
    public static void close(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭输入流
     *
     * @param is 输入流
     */
    public static void close(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
