package com.xs.common.dao;

import com.xs.common.entity.CodeMsg;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 常量获取数据服务层
 *
 * @author xiaotinghao
 */
@Repository
public interface ConstantsDao {

    /**
     * 根据常量key获取常量
     *
     * @param key 常量key
     * @return 常量
     */
    String getBaseConstants(@Param("key") String key);

    /**
     * 根据常量key获取常量
     *
     * @param key    常量key
     * @param module 模块名称
     * @return 常量
     */
    String getModuleConstants(@Param("key") String key, @Param("module") String module);

    /**
     * 根据常量key获取常量
     *
     * @param key 常量key
     * @return 常量列表
     */
    List<String> listBaseConstants(@Param("key") String key);

    /**
     * 根据常量key获取常量
     *
     * @param key    常量key
     * @param module 模块名称
     * @return 常量列表
     */
    List<String> listModuleConstants(@Param("key") String key, @Param("module") String module);

    /**
     * 根据常量key获取常量
     *
     * @param key 常量key
     * @return 常量
     */
    CodeMsg getCodeMsg(@Param("key") String key);

}