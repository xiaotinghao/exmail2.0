package com.xs.common.utils.etl;

import com.xs.common.utils.*;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.logging.JobLogTable;
import org.pentaho.di.core.logging.TransLogTable;
import org.pentaho.di.core.variables.VariableSpace;
import org.pentaho.di.core.variables.Variables;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * ETL执行转换和作业的工具类
 *
 * @author xiaotinghao
 */
public class KettleUtils {

    private static Kettle kettle = new Kettle();

    private static DatabaseMeta databaseMeta;

    private static String name;
    private static String type;
    private static String access;
    private static String host;
    private static String sid;
    private static String port;
    private static String user;
    private static String pass;
    private static Properties properties;

    static {
        // 获取properties集合
        properties = PropertyUtils.getProperties("etl.properties");
        if (properties != null) {
            // 获取数据、赋值
            name = properties.getProperty("db.name");
            type = properties.getProperty("db.type");
            access = properties.getProperty("db.access");
            host = properties.getProperty("db.host");
            sid = properties.getProperty("db.sid");
            port = properties.getProperty("db.port");
            user = properties.getProperty("db.user");
            pass = properties.getProperty("db.pass");
        }
    }

    private static void init() {
        // 初始化
        if (!KettleEnvironment.isInitialized()) {
            try {
                KettleEnvironment.init();
            } catch (KettleException e) {
                e.printStackTrace();
            }
        }
        // 初始化etl日志表
        SqlRunner.run("sql/etl/init_log_tables.sql");
    }

    /**
     * 执行目录下的kettle文件
     *
     * @param filePath 文件路径
     * @return 执行结果
     */
    public static Kettle execute(String filePath) {
        kettle.setTimestamp(DateUtil.getCurrentDateToTimestamp());
        kettle.setFilePath(filePath);
        kettle.setStartTime(DateUtil.getCurrentDateToString());
        int errorCount = 0;
        if (StringUtils.isNotBlank(filePath)) {
            File file = new File(filePath);
            if (file.isDirectory()) {
                // 是目录，则获取目录下的文件
                List<String> fileNames = new ArrayList<>();
                FileUtils.findFileList(file, fileNames);
                for (String fileName : fileNames) {
                    errorCount += run(fileName);
                }
            } else {
                // 是文件
                errorCount += run(filePath);
            }
        }
        kettle.setEndTime(DateUtil.getCurrentDateToString());
        kettle.setErrorCount(errorCount);
        return kettle;
    }

    private static int run(String fileName) {
        // kettle转换文件后缀
        String etlTransSuffix = ".ktr";
        // kettle作业文件后缀
        String etlJobSuffix = ".kjb";
        int result = 0;
        try {
            if (fileName.endsWith(etlTransSuffix)) {
//                result = runTrans(fileName);
            } else if (fileName.endsWith(etlJobSuffix)) {
                result = runJob(fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.error(e.toString());
        }
        return result;
    }

    /**
     * 执行转换
     *
     * @param fileName 文件路径
     * @return 错误个数
     */
    private static int runTrans(String fileName) throws KettleException {
        // 初始化
        init();
        // 创建转换元数据对象
        TransMeta meta = new TransMeta(fileName);
//        setTransLogTable(meta);
        Trans trans = new Trans(meta);
        trans.prepareExecution(null);
        trans.startThreads();
        trans.waitUntilFinished();
        return trans.getErrors();
    }

    /**
     * 执行作业
     *
     * @param fileName 文件路径
     * @return 错误个数
     */
    private static int runJob(String fileName) throws KettleException {
        // 初始化
        init();
        // 创建作业对象
        JobMeta jobMeta = new JobMeta(fileName, null);
//        setJobLogTable(jobMeta);
        Job job = new Job(null, jobMeta);
        job.setVariable("DB_HOST", properties.getProperty("db.host"));
        job.setVariable("DB_SID", properties.getProperty("db.sid"));
        job.setVariable("DB_PORT", properties.getProperty("db.port"));
        job.setVariable("DB_USER", properties.getProperty("db.user"));
        job.setVariable("DB_PASS", properties.getProperty("db.pass"));
        job.setVariable("etl_host", properties.getProperty("etl.host"));
        job.setVariable("etl_sid", properties.getProperty("etl.sid"));
        job.setVariable("etl_port", properties.getProperty("etl.port"));
        job.setVariable("etl_user", properties.getProperty("etl.user"));
        job.setVariable("etl_pass", properties.getProperty("etl.pass"));
        job.setVariable("backup_host", properties.getProperty("backup.host"));
        job.setVariable("backup_sid", properties.getProperty("backup.sid"));
        job.setVariable("backup_port", properties.getProperty("backup.port"));
        job.setVariable("backup_user", properties.getProperty("backup.user"));
        job.setVariable("backup_pass", properties.getProperty("backup.pass"));
        job.start();
        job.waitUntilFinished();
        return job.getErrors();
    }

    /**
     * 设置转换日志记录表
     *
     * @param meta 转换对象
     */
    private static void setTransLogTable(TransMeta meta) {
        if (databaseMeta == null) {
            databaseMeta = new DatabaseMeta(name, type, access, host, sid, port, user, pass);
        }
        meta.addDatabase(databaseMeta);
        VariableSpace space = new Variables();
        space.setVariable("test", "");
        TransLogTable transLogTable = TransLogTable.getDefault(space, meta, null);
        transLogTable.setTableName("etl_trans_log");
        transLogTable.setConnectionName(name);
        meta.setTransLogTable(transLogTable);
    }

    /**
     * 设置作业日志记录表
     *
     * @param jobMeta 作业对象
     */
    private static void setJobLogTable(JobMeta jobMeta) {
        if (databaseMeta == null) {
            databaseMeta = new DatabaseMeta(name, type, access, host, sid, port, user, pass);
        }
        jobMeta.addDatabase(databaseMeta);
        VariableSpace space = new Variables();
        space.setVariable("test", "");
        JobLogTable jobLogTable = JobLogTable.getDefault(space, jobMeta);
        jobLogTable.setTableName("etl_job_log");
        jobLogTable.setConnectionName(name);
        jobMeta.setJobLogTable(jobLogTable);
    }

    static class Kettle {

        private String timestamp;
        private String filePath;
        private String startTime;
        private String endTime;
        private Integer errorCount;

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public Integer getErrorCount() {
            return errorCount;
        }

        public void setErrorCount(Integer errorCount) {
            this.errorCount = errorCount;
        }

    }

}
