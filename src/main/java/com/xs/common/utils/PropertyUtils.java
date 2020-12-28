package com.xs.common.utils;

import org.apache.ibatis.io.Resources;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 配置读取工具类
 *
 * @author 18871430207@163.com
 */
public class PropertyUtils {

    private static Properties properties = new Properties();

    /**
     * 使用静态代码块读取资源文件
     * 配置文件放在resources下
     *
     * @param fileName 配置文件名
     */
    public static Properties getProperties(String fileName) {
        Properties properties = new Properties();
        try {
            // 获取相关配置信息
            InputStream is = Resources.getResourceAsStream(fileName);
            properties.load(new InputStreamReader(is, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    /**
     * 读取资源文件
     * 配置文件放在resources下
     */
    public static Properties list() {
        if (properties.isEmpty()) {
            // 刷新资源文件
            refresh();
        }
        return properties;
    }

    /**
     * 刷新资源文件
     * 配置文件放在resources下
     */
    public static void refresh() {
        Properties newProperties = new Properties();
        // 获取资源文件
        PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        try {
            String locationPattern1 = "classpath:*.yml";
            String locationPattern2 = "classpath:*.properties";
            Resource[] resources1 = patternResolver.getResources(locationPattern1);
            Resource[] resources2 = patternResolver.getResources(locationPattern2);
            Object[] objects = ArrayUtils.join(resources1, resources2);
            if (objects != null && objects.length > 0) {
                for (Object obj : objects) {
                    Resource resource = XsUtils.cast(obj);
                    InputStream inputStream = resource.getInputStream();
                    newProperties.load(new InputStreamReader(inputStream, "UTF-8"));
                }
            }
            properties = newProperties;
        } catch (IOException e) {
            e.printStackTrace();
        }
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
