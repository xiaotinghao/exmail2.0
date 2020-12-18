package com.xs.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Properties;

/**
 * 配置工具类
 *
 * @author 18871430207@163.com
 */
public class ConfigProperties extends PropertyPlaceholderConfigurer {

    private static Properties prop;

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        prop = props;
    }

    public static String getConfigProperties(String key, String defultValue) {
        return prop.getProperty(key, defultValue);
    }

    public static String getConfigProperties(String key) {
        return prop.getProperty(key);
    }

}
