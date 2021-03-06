package com.xs.common.service.impl;

import com.xs.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xs.common.dao.BaseDao;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * 基础业务接口实现
 *
 * @author 18871430207@163.com
 */
@Service
public class BaseServiceImpl implements BaseService {

    @Autowired
    private BaseDao baseDao;

    @Override
    public String getSql(String sqlPath, Object... args) {
        StringBuilder sb = new StringBuilder();
        URL resource = this.getClass().getClassLoader().getResource("/");
        if (resource != null) {
            StringBuilder sql = new StringBuilder();
            FileInputStream fis = null;
            InputStreamReader isr = null;
            BufferedReader br = null;
            try {
                String path = resource.getPath() + sqlPath;
                fis = new FileInputStream(path);
                isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
                br = new BufferedReader(isr);
                String temp;
                while ((temp = br.readLine()) != null) {
                    sql.append(temp);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                    if (isr != null) {
                        isr.close();
                    }
                    if (fis != null) {
                        fis.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 使用String.format需将%转换为%%，同时要保留'%s'对参数的替换
            String str = sql.toString().replaceAll("%", "%%")
                    .replaceAll("'%%s'", "'%s'");
            sb.append(String.format(str, args));
        }
        return sb.toString();
    }

}
