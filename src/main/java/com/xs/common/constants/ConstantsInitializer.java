package com.xs.common.constants;

import com.xs.common.dao.ConstantsDao;
import com.xs.common.utils.spring.SpringTool;

/**
 * ConstantsDao初始化
 *
 * @author 18871430207@163.com
 */
class ConstantsInitializer {

    static ConstantsDao constantsDao;

    static {
        if (constantsDao == null) {
            constantsDao = SpringTool.getBean(ConstantsDao.class);
        }
    }

}
