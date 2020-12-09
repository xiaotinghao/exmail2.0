package com.xs.common.utils.etl;

import com.xs.common.utils.PropertyUtils;

/**
 * Kettle工具测试类
 *
 * @author xiaotinghao
 */
public class KettleUtilsTest {

    public static void main(String[] args) {

        String path = PropertyUtils.getResourcesPath() + "etl";
        KettleUtils.Kettle kettle = KettleUtils.execute(path);

        System.out.println(kettle);

    }

}
