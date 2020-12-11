package com.xs.common.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 基础Dao
 *
 * @author xiaotinghao
 */
@Repository
public interface BaseDao {

    /**
     * 查询数据
     *
     * @param param 查询参数
     * @return 查询结果
     */
    List<Map<String, Object>> query(Map<String, Object> param);

    /**
     * 保存至数据库
     *
     * @param param 待保存的数据
     * @return 保存结果
     */
    int insert(Map<String, Object> param);

    /**
     * 查询数据表的所有字段
     *
     * @param tableName 表名
     * @return 查询结果
     */
    List<String> queryColumns(@Param("tableName") String tableName);

    /**
     * 查询数据
     *
     * @param sql SQL语句
     * @return 查询结果
     */
    List<Map<String, Object>> createSqlQuery(@Param("str") String sql);

    /**
     * 使用pwd作为密码，加密str
     *
     * @param str 待加密字符串
     * @param pwd 密码
     * @return
     */
    String encode(@Param("str") String str, @Param("pwd") String pwd);

    /**
     * 使用pwd作为密码，解密加密字符串str
     *
     * @param str 待解密字符串
     * @param pwd 密码
     * @return
     */
    String decode(@Param("str") String str, @Param("pwd") String pwd);

}