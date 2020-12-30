package daisy.commons.lang3;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class工具类
 *
 * @author 18871430207@163.com
 */
public class ClassUtils extends org.apache.commons.lang3.ClassUtils {

    /**
     * 根据类名称获取对应的Class类
     *
     * @param className 类名称
     * @return 类名称对应的Class类
     */
    public static Class<?> getClass(String className) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clazz;
    }

    /**
     * 获取匹配路径下的所有类的Class类
     *
     * @param locationPattern 匹配路径
     * @return 匹配路径下的所有类的Class类
     */
    public static List<Class<?>> getClasses(String locationPattern) {
        List<Class<?>> result = new ArrayList<>();
        // 获取资源文件
        PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        // 元数据集缓存读取工厂，用于读取元数据的
        CachingMetadataReaderFactory readerFactory = new CachingMetadataReaderFactory();
        Resource[] resources = new Resource[0];
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            resources = patternResolver.getResources(locationPattern);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Resource resource : resources) {
            // 元数据集缓存工厂读取的数据
            MetadataReader metadataReader = null;
            try {
                metadataReader = readerFactory.getMetadataReader(resource);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (metadataReader != null) {
                String className = metadataReader.getClassMetadata().getClassName();
                try {
                    Class<?> clazz = loader.loadClass(className);
                    result.add(clazz);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static <T> T newInstance(Class<T> clazz) {
        Object obj = null;
        try {
            obj = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return clazz.cast(obj);
    }

}
