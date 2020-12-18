package com.xs.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author 18871430207@163.com
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    /**
     * 默认日期格式
     */
    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * 不带时分秒日期格式
     */
    public static final String DATETIME_FORMAT = "yyyy-MM-dd";
    /**
     * 时间戳日期格式
     */
    public static final String DATE_FOMATE_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    /**
     * 日期格式，精确到毫秒
     */
    public static final String DATE_FORMAT_MSEC = "yyyy-MM-dd HH:mm:ss.sss";

    public static SimpleDateFormat DATE_SDF = new SimpleDateFormat(DATETIME_FORMAT);
    public static SimpleDateFormat DEFAULT_SDF = new SimpleDateFormat(DEFAULT_FORMAT);

    /**
     * 将日期格式化为指定格式的字符串
     *
     * @param date   日期
     * @param format 格式化规则
     * @return 返回日期字符串
     */
    public static String formatDateToString(Date date, String format) {
        String dateFormat = format;
        String dateString = null;
        try {
            // 如果需要格式的日期为空
            if (date == null) {
                return dateString;
            }
            // 如果没有定义格式化规则，默认为"yyyy-MM-dd HH:mm:ss"
            if (dateFormat == null || "".equals(dateFormat)) {
                dateFormat = DEFAULT_FORMAT;
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
            dateString = simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateString;
    }


}
