package com.xs.common.service;

import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 基础业务接口
 *
 * @author xiaotinghao
 */
public interface BaseService {

    /**
     * 查询数据表的所有字段
     *
     * @param tableName 表名
     * @return 查询结果
     */
    List<String> queryColumns(@Param("tableName") String tableName);

    /**
     * 获取SQL语句
     *
     * @param sqlPath SQL文件路径
     * @param args    SQL替换参数
     * @return SQL语句
     */
    String getSql(String sqlPath, Object... args);

    /**
     * 将Http请求中的参数转换为Map
     *
     * @param request Http请求
     * @return 转换结果Map
     */
    Map<String, Object> requestParamToMap(HttpServletRequest request);

}
