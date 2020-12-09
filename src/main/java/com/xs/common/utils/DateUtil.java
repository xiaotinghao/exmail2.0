package com.xs.common.utils;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.time.DateUtils;

/**
 * 时间工具类
 *
 * @author xiaotinghao
 */
public class DateUtil {

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
     * 获取当前日期对象
     *
     * @return 日期对象
     */
    public static Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }

    /**
     * 获取指定年月日的日期对象
     *
     * @param year  年
     * @param month 月
     * @param day   日
     * @return 指定日期对象
     */
    public static Date getDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day);
        return cal.getTime();
    }

    /**
     * 获取指定年月日时分秒日期
     *
     * @param year   年
     * @param month  月
     * @param day    日
     * @param hour   十
     * @param minute 分
     * @param second 秒
     * @return 指定日期对象
     */
    public static Date getDate(int year, int month, int day, int hour, int minute, int second) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DATE, day);
        cal.set(Calendar.HOUR, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);
        return cal.getTime();
    }

    /**
     * 获取指定日期的年份
     *
     * @param date 日期对象
     * @return 年
     */
    public static int getDateYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取指定日期的月份
     *
     * @param date 日期对象
     * @return 月
     */
    public static int getDateMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }

    /**
     * 获取指定日期的多少号
     *
     * @param date 日期对象
     * @return 日
     */
    public static int getDateDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DATE);
    }

    /**
     * 获取指定日期的小时数
     *
     * @param date 日期对象
     * @return 时
     */
    public static int getDateHour(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.HOUR);
    }

    /**
     * 获取指定日期的分钟数
     *
     * @param date 日期对象
     * @return 分
     */
    public static int getDateMinute(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MINUTE);
    }

    /**
     * 获取指定日期的秒数
     *
     * @param date 日期对象
     * @return 秒
     */
    public static int getDateSecond(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.SECOND);
    }

    /**
     * 获取当前时间的毫秒值，至1970-1-1 00:00:00 时起的毫秒数
     *
     * @return 毫秒值
     */
    public static Long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前日期字符串（默认格式）
     *
     * @return 日期字符串
     */
    public static String getCurrentDateStr() {
        Date date = new Date();
        return formatDateToString(date, DATETIME_FORMAT);
    }

    /**
     * 获取当前日期字符串（默认格式）
     *
     * @return 日期字符串
     */
    public static String getCurrentDateToString() {
        Date date = new Date();
        return formatDateToString(date, DEFAULT_FORMAT);
    }

    /**
     * 获取当前日期字符串（精确到毫秒）
     *
     * @return 日期字符串
     */
    public static String getCurrentDateWithMsec() {
        return formatDateToString(new Date(), DATE_FORMAT_MSEC);
    }

    /**
     * 获取当前日期时间戳格式字符串
     *
     * @return 日期时间戳
     */
    public static String getCurrentDateToTimestamp() {
        Date date = new Date();
        return formatDateToString(date, DATE_FOMATE_YYYYMMDDHHMMSS);
    }


    /**
     * 比较两个日期的大小,只精确到天
     *
     * @param first  第一个日期
     * @param second 第二个日期
     * @return 如果第一个日期大于第二个日期，返回1 如果第一个日期小于第二个日期，返回-1 如果两个日期相等，返回0
     * @throws FileNotFoundException
     */
    public static int compareTwoDate(Date firstDate, Date secondDate) throws FileNotFoundException {
        if (firstDate == null) {
            throw new FileNotFoundException("The first date is null");
        }
        if (secondDate == null) {
            throw new FileNotFoundException("The second date is null");
        }
        Calendar first = Calendar.getInstance();
        first.setTime(firstDate);
        Calendar second = Calendar.getInstance();
        second.setTime(secondDate);
        if (first.get(Calendar.YEAR) < second.get(Calendar.YEAR)) {
            return -1;
        } else if (first.get(Calendar.YEAR) > second.get(Calendar.YEAR)) {
            return 1;
        } else {
            if (first.get(Calendar.MONTH) < second.get(Calendar.MONTH)) {
                return -1;
            } else if (first.get(Calendar.MONTH) > second.get(Calendar.MONTH)) {
                return 1;
            } else {
                if (first.get(Calendar.DATE) < second.get(Calendar.DATE)) {
                    return -1;
                } else if (first.get(Calendar.DATE) > second.get(Calendar.DATE)) {
                    return 1;
                }
                return 0;
            }
        }
    }

    /**
     * 获得两个日期相隔的天数,时分秒
     *
     * @param first  第一个日期
     * @param second 第二个日期
     * @return 相隔的天数时分秒，如果第一个日期大于第二个日期，返回负整数
     */
    public static String getIntervalDays(Date first, Date second) {
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try {
            long time1 = first.getTime();
            long time2 = second.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return day + "天" + hour + "小时" + min + "分" + sec + "秒";
    }

    /**
     * 获得两个日期相隔的天数,时分秒
     *
     * @param first  第一个整数日期 无时分秒
     * @param second 第二个整数日期 无时分秒
     * @return 相隔的天数时分秒，如果第一个日期大于第二个日期，返回负整数
     */
    public static int getDistanceDays(Date first, Date second) {
        int day = 0;
        try {
            long time1 = first.getTime();
            long time2 = second.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = (int) (diff / (24 * 60 * 60 * 1000));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return day;
    }

    /**
     * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
     *
     * @param dateDate 传入当前日期毫秒值
     * @return 返回日期字符串
     */
    public static String formatLongToString(Long dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_FORMAT);
        String dateString = formatter.format(dateDate);
        return dateString;
    }


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

    /**
     * 将日期字符串格式化为指定的日期
     *
     * @param dateString 日期字符串
     * @param format     格式化规则
     * @return 返回日期
     */
    public static Date formatStringToDate(String dateString, String format) {
        Date date = null;
        String dateFormat = format;
        try {
            // 如果需要格式的日期字符串为空
            if (dateString == null || "".equals(dateString)) {
                return date;
            }
            // 如果没有定义格式化规则，默认为yyyy-MM-dd HH:mm:ss
            if (dateFormat == null || "".equals(dateFormat)) {
                dateFormat = DEFAULT_FORMAT;
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
            date = simpleDateFormat.parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 将日期格式化为时间戳格式的字符串
     *
     * @param date   日期
     * @param format 格式化规则
     * @return 返回日期字符串
     */
    public static String formatTimeStampToString(Date date) {
        String dateString = null;
        try {
            // 如果需要格式的日期为空
            if (date == null) {
                return dateString;
            }
            // 如果没有定义格式化规则，默认为YYYYMMDDHHMMSS
            String format = DATE_FOMATE_YYYYMMDDHHMMSS;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            dateString = simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateString;
    }

    /**
     * 将时间戳转换为日期 对象
     *
     * @param timeStamp 日期
     * @param format    格式化规则
     * @return 返回日期字符串
     */
    public static Date formatTimeStampToDate(String timeStamp) {
        Date date = null;
        try {
            // 如果需要格式的日期字符串为空
            if (timeStamp == null || "".equals(timeStamp)) {
                return date;
            }
            // 如果没有定义格式化规则，默认为yyyy-MM-dd
            String format = DATE_FOMATE_YYYYMMDDHHMMSS;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            date = simpleDateFormat.parse(timeStamp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 设置一个月最大或最小日
     *
     * @param date
     * @param max
     * @return
     */
    public static Date setDay(Date date, boolean max) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (max) {
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        } else {
            cal.set(Calendar.DAY_OF_MONTH, 1);
        }
        return cal.getTime();
    }

    /**
     * 获取当前年最大时间或最小时间
     *
     * @return
     */
    public static Date getYear(Date date, boolean max) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        if (!max) {
            cal.set(Calendar.MONTH, 0);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
        } else {
            cal.set(Calendar.MONTH, 11);
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));
            cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));
            cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));
            cal.set(Calendar.MILLISECOND, cal.getActualMaximum(Calendar.MILLISECOND));
        }
        return cal.getTime();
    }


    /**
     * 获取上一年的今天
     *
     * @return
     */
    public static Date getLastYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, -1);
        return cal.getTime();
    }

    /**
     * 获取指定月份的第一天
     *
     * @param date
     * @return
     */
    public static Date getMonthStartDate(Date date) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.DATE, 1);
        return now.getTime();
    }

    /**
     * 获取指定月份的最后一天
     *
     * @param date
     * @return
     */
    public static Date getMonthEndDate(Date date) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.DAY_OF_MONTH, now.getActualMaximum(Calendar.DAY_OF_MONTH));
        return now.getTime();
    }

    /**
     * 获取指定年份的第一天
     *
     * @param date
     * @return
     */
    public static Date getYearStartDate(Date date) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.MONTH, 1);
        now.set(Calendar.DAY_OF_YEAR, 1);
        return now.getTime();
    }

    /**
     * 获取指定年份的最后一天
     *
     * @param date
     * @return
     */
    public static Date getYearEndDate(Date date) {
        Calendar now = Calendar.getInstance();
        now.setTime(getYearStartDate(date));
        now.add(Calendar.YEAR, 1);
        now.add(Calendar.DATE, -1);
        return now.getTime();
    }

    /**
     * /**
     * 获取本季度第一天
     *
     * @param date
     * @return
     */
    public static Date getSeasonStartDate(Date date) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        int currentMonth = now.get(Calendar.MONTH) + 1;
        Date time = null;
        if (currentMonth >= 1 && currentMonth <= 3) {
            now.set(Calendar.MONTH, 0);
        } else if (currentMonth >= 4 && currentMonth <= 6) {
            now.set(Calendar.MONTH, 3);
        } else if (currentMonth >= 7 && currentMonth <= 9) {
            now.set(Calendar.MONTH, 6);
        } else if (currentMonth >= 10 && currentMonth <= 12) {
            now.set(Calendar.MONTH, 9);
        }
        now.set(Calendar.DATE, 1);
        time = now.getTime();
        return time;
    }

    /**
     * 获取本季度最后一天
     *
     * @param date
     * @return
     */
    public static Date getSeasonEndDate(Date date) {
        Calendar now = Calendar.getInstance();
        now.setTime(getSeasonStartDate(date));
        now.add(Calendar.MONTH, 3);
        now.add(Calendar.DATE, -1);
        return now.getTime();
    }

    /**
     * 获取dete后num天的日期（去掉时分秒）
     *
     * @param date 日期对象
     * @param num  往后的天数
     * @return 日期对象
     */
    public static Date getAfterDay(Date date, int num) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, num);

        return truncateDay(cal.getTime());
    }

    /**
     * 把date 截止到日期为止,去掉时分秒
     *
     * @param date 日期对象
     * @return 日期对象（去掉时分秒）
     */
    public static Date truncateDay(Date date) {
        return DateUtils.truncate(date, Calendar.DAY_OF_MONTH);
    }

    /***
     * 获取前一天日期字符串
     * @return
     */
    public static String getYesterday() {
        //获取定时任务执行前一天日期
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date time = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(time);
        return date;
    }
}
