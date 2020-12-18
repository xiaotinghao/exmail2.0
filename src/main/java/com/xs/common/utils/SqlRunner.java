package com.xs.common.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * 运行Sql脚本
 * sql脚本放在resources下
 *
 * @author 18871430207@163.com
 */
public final class SqlRunner {

    /**
     * 运行指定的sql脚本
     *
     * @param sqlPath 需要执行的sql脚本的路径
     */
    public static void run(String sqlPath) {
        try {
            // 获取数据库相关配置信息
            Properties props = Resources.getResourceAsProperties("jdbc.properties");
            // jdbc 连接信息: 注: 现在版本的JDBC不需要配置driver，因为不需要Class.forName手动加载驱动
            String url = props.getProperty("jdbc.url");
            String username = props.getProperty("jdbc.username");
            String password = props.getProperty("jdbc.password");
            // 建立连接
            Connection conn = DriverManager.getConnection(url, username, password);
            // 创建ScriptRunner，用于执行SQL脚本
            ScriptRunner runner = new ScriptRunner(conn);
            runner.setErrorLogWriter(null);
            runner.setLogWriter(null);
            // 执行SQL脚本
            runner.runScript(Resources.getResourceAsReader(sqlPath));
            // 关闭连接
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
