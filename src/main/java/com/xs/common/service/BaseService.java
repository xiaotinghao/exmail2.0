package com.xs.common.service;

/**
 * 基础业务接口
 *
 * @author 18871430207@163.com
 */
public interface BaseService {

    /**
     * 获取SQL语句
     *
     * @param sqlPath SQL文件路径
     * @param args    SQL替换参数
     * @return SQL语句
     */
    String getSql(String sqlPath, Object... args);

}
