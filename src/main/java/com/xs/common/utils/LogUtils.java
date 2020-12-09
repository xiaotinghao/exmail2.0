package com.xs.common.utils;

import java.io.*;

import static com.xs.common.constants.SymbolConstants.*;

/**
 * 日志工具类
 *
 * @author xiaotinghao
 */
public class LogUtils extends com.mysql.jdbc.log.LogUtils {

    private static String namespace = "/home/xth/";

    public static void info(String msg) {
        log(msg, Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    public static void error(String msg) {
        log(msg, Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    private static void log(String msg, String prefix) {
        defaultTag();
        String eLog = prefix + ".log";
        String dateStr = DateUtil.getCurrentDateStr();
        String logFileName = namespace + dateStr + UNDERLINE + eLog;
        File file = new File(namespace);
        if (!file.exists()) {
            if (file.mkdirs()) {
                generate(new File(logFileName), msg);
            }
        } else {
            generate(new File(logFileName), msg);
        }
    }

    private static void generate(File file, String msg) {
        String dateStr = DateUtil.getCurrentDateToString();
        String text = SQUARE_BRACKETS_L + dateStr + SQUARE_BRACKETS_R
                + defaultTag() + "\n" + msg;
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    // 向文件写入日志
                    FileUtils.appendText(file.getPath(), text);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // 向文件写入日志
            FileUtils.appendText(file.getPath(), text);
        }
    }

    /**
     * 获取方法调用者的类名、方法名、代码行数
     *
     * @return 方法调用者的类名、方法名、代码行数
     */
    private static String defaultTag() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement log = stackTrace[1];
        String tag = null;
        for (int i = 1; i < stackTrace.length; i++) {
            StackTraceElement e = stackTrace[i];
            if (!e.getClassName().equals(log.getClassName())) {
                tag = e.getClassName() + DOT + e.getMethodName()
                        + SQUARE_BRACKETS_L + e.getLineNumber() + SQUARE_BRACKETS_R;
                break;
            }
        }
        if (tag == null) {
            tag = log.getClassName() + DOT + log.getMethodName()
                    + SQUARE_BRACKETS_L + log.getLineNumber() + SQUARE_BRACKETS_R;
        }
        return tag;
    }

}
