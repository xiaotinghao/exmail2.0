package com.xs.common.dao;

import com.xs.common.entity.CodeMsg;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 常量获取数据服务层
 *
 * @author 18871430207@163.com
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
     * 获取所有返回编码&信息
     *
     * @return 所有返回编码&信息
     */
    List<Map<String, Object>> listCodeMsg();

    /**
     * 获取所有常量表
     *
     * @return 所有常量表，英文逗号分隔的字符串，例如：t_constants_base,t_constants_token
     */
    String constantsTables();

    /**
     * 获取所有常量表中的所有常量配置
     *
     * @param tables 英文逗号分隔的字符串，例如：t_constants_base,t_constants_token
     * @return 所有常量配置
     */
    List<Map<String, Object>> listConstants(@Param("tables") String tables);


}