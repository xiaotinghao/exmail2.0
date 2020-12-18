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