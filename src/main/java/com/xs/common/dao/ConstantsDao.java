package com.xs.common.dao;

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
     * 通过key获取常量值
     *
     * @param tableName  表名
     * @param columnName 字段名
     * @param key        常量查询key
     * @return 常量值
     */
    Map<String, Object> getConstantByKey(
            @Param("tableName") String tableName,
            @Param("columnName") String columnName,
            @Param("key") String key
    );

    /**
     * 获取所有返回编码&信息
     *
     * @return 所有返回编码&信息
     */
    List<Map<String, Object>> listCodeMsg();

    /**
     * 获取所有常量表
     *
     * @return 所有常量表，英文逗号分隔的字符串，例如：t_constants_base,t_constants_test
     */
    String constantsTables();

    /**
     * 获取所有常量表中的所有常量配置
     *
     * @param tables 英文逗号分隔的字符串，例如：t_constants_base,t_constants_test
     * @return 所有常量配置
     */
    List<Map<String, Object>> listConstants(@Param("tables") String tables);

    /**
     * 保存常量配置
     *
     * @param key   对应constants_key的值
     * @param value 对应constants_value的值
     */
    void save(@Param("key") String key, @Param("value") String value);

}