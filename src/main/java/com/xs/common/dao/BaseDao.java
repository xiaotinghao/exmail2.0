package com.xs.common.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 基础Dao
 *
 * @author 18871430207@163.com
 */
@Repository
public interface BaseDao {

    /**
     * 使用pwd作为密码，加密str
     *
     * @param str 待加密字符串
     * @param pwd 密码
     * @return 加密字符串
     */
    String encode(@Param("str") String str, @Param("pwd") String pwd);

    /**
     * 使用pwd作为密码，解密加密字符串str
     *
     * @param str 待解密字符串
     * @param pwd 密码
     * @return 解密字符串
     */
    String decode(@Param("str") String str, @Param("pwd") String pwd);

    /**
     * 查询数据表的所有字段
     *
     * @param tableName 表名
     * @return 查询结果
     */
    List<String> listColumns(@Param("tableName") String tableName);

    /**
     * 查询数据表某字段的全部值
     *
     * @param tableName  表名
     * @param columnName 字段名
     * @return 查询结果
     */
    List<String> listColumnValues(@Param("tableName") String tableName, @Param("columnName") String columnName);

    /**
     * 通过关键字查询表数据
     *
     * @param tableName  表名
     * @param columnName 字段名
     * @param key        查询关键字
     * @return 常量值
     */
    Map<String, Object> getByKey(@Param("tableName") String tableName, @Param("columnName") String columnName, @Param("key") String key);

    /**
     * 查询数据表是否存在
     *
     * @param tableName 表名
     * @return 表名称
     */
    String checkTable(@Param("tableName") String tableName);

    /**
     * 查询数据表的字段是否存在
     *
     * @param tableName  表名
     * @param columnName 字段名
     * @return 字段名称
     */
    String checkColumn(@Param("tableName") String tableName, @Param("columnName") String columnName);

}