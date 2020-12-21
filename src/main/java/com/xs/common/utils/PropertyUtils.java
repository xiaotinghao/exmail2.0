package com.xs.common.utils;

import org.apache.ibatis.io.Resources;

import java.io.IOException;
import java.util.Properties;

/**
 * 配置读取工具类
 *
 * @author 18871430207@163.com
 */
public class PropertyUtils {

    /**
     * 使用静态代码块读取资源文件
     * 配置文件放在resources下
     *
     * @param fileName 配置文件名
     */
    public static Properties getProperties(String fileName) {
        Properties properties = null;
        try {
            // 获取相关配置信息
            properties = Resources.getResourceAsProperties(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    /**
     * 获取resources根目录
     *
     * @return resources根目录
     */
    public static String getResourcesPath() {
        return PropertyUtils.class.getClassLoader().getResource("").getPath();
    }

}
