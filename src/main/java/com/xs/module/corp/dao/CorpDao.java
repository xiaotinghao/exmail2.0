package com.xs.module.corp.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 企业相关业务Dao
 *
 * @author 18871430207@163.com
 */
@Repository
public interface CorpDao {

    /**
     * 查询企业id和客户端ip的匹配关系
     *
     * @param paramMap 查询参数
     * @return 企业id和客户端ip的匹配关系
     */
    List<Map<String, Object>> listCorpIpRelation(Map<String, Object> paramMap);

    /**
     * 保存企业id和客户端ip的匹配关系
     *
     * @param objectMap 待保存数据
     */
    void saveCorpIpRelation(Map<String, Object> objectMap);

}