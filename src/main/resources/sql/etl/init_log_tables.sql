-- ----------------------------
-- Database structure for etl
-- ----------------------------
CREATE DATABASE IF NOT EXISTS `etl` CHARSET 'utf8';

USE `etl`;

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for etl_channel_log
-- ----------------------------
CREATE TABLE IF NOT EXISTS `etl_channel_log`  (
  `ID_BATCH` int(11) NULL DEFAULT NULL COMMENT '批次ID',
  `CHANNEL_ID` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日志通道ID',
  `LOG_DATE` datetime(0) NULL DEFAULT NULL COMMENT '日志打印日期',
  `LOGGING_OBJECT_TYPE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对象类型',
  `OBJECT_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对象名称',
  `OBJECT_COPY` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对象（普通strep）副本',
  `REPOSITORY_DIRECTORY` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '库对象的路径',
  `FILENAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件名称',
  `OBJECT_ID` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '库对象ID',
  `OBJECT_REVISION` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '库对象修改',
  `PARENT_CHANNEL_ID` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父对象日志通道ID',
  `ROOT_CHANNEL_ID` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '记录该信息的对象的通道ID'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;


SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for etl_job_entry_log
-- ----------------------------
CREATE TABLE IF NOT EXISTS `etl_job_entry_log`  (
  `ID_BATCH` int(11) NULL DEFAULT NULL COMMENT '作业的批次Id',
  `CHANNEL_ID` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日志通道Id',
  `LOG_DATE` datetime(0) NULL DEFAULT NULL COMMENT '日志打印日期',
  `TRANSNAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '转换的名称',
  `STEPNAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '步骤名称',
  `LINES_READ` bigint(20) NULL DEFAULT NULL COMMENT '由(转换)作业条目读取的行数',
  `LINES_WRITTEN` bigint(20) NULL DEFAULT NULL COMMENT '由(转换)作业条目编写的行数',
  `LINES_UPDATED` bigint(20) NULL DEFAULT NULL COMMENT '由(转换)作业条目执行的update语句的数量',
  `LINES_INPUT` bigint(20) NULL DEFAULT NULL COMMENT '从输入(文件、数据库、网络、…)中读取的行数',
  `LINES_OUTPUT` bigint(20) NULL DEFAULT NULL COMMENT '作业条目中写入到输出的行数(文件、数据库、网络、…)',
  `LINES_REJECTED` bigint(20) NULL DEFAULT NULL COMMENT '由(转换)作业条目的错误处理所拒绝的行数',
  `ERRORS` bigint(20) NULL DEFAULT NULL COMMENT '错误',
  `RESULT` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结果是否准确',
  `NR_RESULT_ROWS` bigint(20) NULL DEFAULT NULL COMMENT '执行后的结果行数。',
  `NR_RESULT_FILES` bigint(20) NULL DEFAULT NULL COMMENT '执行后的结果文件的数量',
  `LOG_FIELD` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '包含这个特定作业条目的错误日志的日志字段',
  `COPY_NR` int(11) NULL DEFAULT NULL COMMENT '	复制 Nr',
  INDEX `IDX_job_entry_log_1`(`ID_BATCH`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;


SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for etl_job_log
-- ----------------------------
CREATE TABLE IF NOT EXISTS `etl_job_log`  (
  `ID_JOB` int(11) NULL DEFAULT NULL COMMENT '批次Id,它是一个惟一的数字，每一次作业的数量都增加了一个',
  `CHANNEL_ID` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日志通道ID(GUID)，可以与日志关系信息相匹配',
  `JOBNAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '作业的名称',
  `STATUS` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '作业的状态:开始(start),结束(end),暂停(stopped),运行(running)',
  `LINES_READ` bigint(20) NULL DEFAULT NULL COMMENT '最后一个作业条目(转换)读取的行数',
  `LINES_WRITTEN` bigint(20) NULL DEFAULT NULL COMMENT '最后一个作业条目(转换)所写的行数',
  `LINES_UPDATED` bigint(20) NULL DEFAULT NULL COMMENT '最后一个作业条目(转换)执行的update语句的数量',
  `LINES_INPUT` bigint(20) NULL DEFAULT NULL COMMENT '通过上一个作业条目(转换)从磁盘或网络读取的行数。这是来自文件、数据库等的输入',
  `LINES_OUTPUT` bigint(20) NULL DEFAULT NULL COMMENT '由上一个作业条目(转换)写入到磁盘或网络的行数。这是对文件、数据库等的输入',
  `LINES_REJECTED` bigint(20) NULL DEFAULT NULL COMMENT '最后一个作业条目(转换)错误处理的行数',
  `ERRORS` bigint(20) NULL DEFAULT NULL COMMENT '出现的错误数量',
  `STARTDATE` datetime(0) NULL DEFAULT NULL COMMENT '增量(CDC)数据处理的日期范围的开始。这是最后一次这项工作正确地完成的“日期范围”',
  `ENDDATE` datetime(0) NULL DEFAULT NULL COMMENT '增量(CDC)数据处理的日期范围的结束',
  `LOGDATE` datetime(0) NULL DEFAULT NULL COMMENT '日志记录的更新时间。如果这个作业“结束(end)”了，那就是作业的结束',
  `DEPDATE` datetime(0) NULL DEFAULT NULL COMMENT '依赖日期:在作业设置中依赖项规则计算的最大日期',
  `REPLAYDATE` datetime(0) NULL DEFAULT NULL COMMENT '重放日期是作业开始时间的日期。',
  `LOG_FIELD` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '将包含作业运行的完整文本日志的字段。通常这是一个CLOB或(long)文本类型的字段',
  `EXECUTING_SERVER` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行该作业的服务器',
  `EXECUTING_USER` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行该作业的用户。如果可用或操作系统用户，则是存储库用户',
  `START_JOB_ENTRY` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '这个作业开始时的工作条目的名称',
  `CLIENT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行作业的客户端: Spoon, pan, kitchen, carte.',
  INDEX `IDX_job_log_1`(`ID_JOB`) USING BTREE,
  INDEX `IDX_job_log_2`(`ERRORS`, `STATUS`, `JOBNAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;


SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for etl_trans_log
-- ----------------------------
CREATE TABLE IF NOT EXISTS `etl_trans_log`  (
  `ID_BATCH` int(11) NULL DEFAULT NULL COMMENT '批次Id,它是一个惟一的数字，每一次转换的数量都增加了一个',
  `CHANNEL_ID` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日志通道ID(GUID)，可以与日志关系信息相匹配',
  `TRANSNAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '转换的名称',
  `STATUS` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '转换的状态:开始(start),结束(end),暂停(stopped)',
  `LINES_READ` bigint(20) NULL DEFAULT NULL COMMENT '指定步骤读取的行数',
  `LINES_WRITTEN` bigint(20) NULL DEFAULT NULL COMMENT '指定步骤所写的行数',
  `LINES_UPDATED` bigint(20) NULL DEFAULT NULL COMMENT '指定步骤执行的update语句的数量',
  `LINES_INPUT` bigint(20) NULL DEFAULT NULL COMMENT '通过指定的步骤从磁盘或网络读取的行数。这是来自文件、数据库等的输入',
  `LINES_OUTPUT` bigint(20) NULL DEFAULT NULL COMMENT '通过指定的步骤写入到磁盘或网络的行数。这是对文件、数据库等的输入',
  `LINES_REJECTED` bigint(20) NULL DEFAULT NULL COMMENT '被指定的步骤拒绝错误处理的行数',
  `ERRORS` bigint(20) NULL DEFAULT NULL COMMENT '出现的错误数量',
  `STARTDATE` datetime(0) NULL DEFAULT NULL COMMENT '增量(CDC)数据处理的日期范围的开始。这是最后一次这种转换正确运行的“日期范围”',
  `ENDDATE` datetime(0) NULL DEFAULT NULL COMMENT '增量(CDC)数据处理的日期范围的结束',
  `LOGDATE` datetime(0) NULL DEFAULT NULL COMMENT '日志记录的更新时间。如果转换的状态为“结束(end)”，那么它就是转换的结束',
  `DEPDATE` datetime(0) NULL DEFAULT NULL COMMENT '依赖日期:在转换设置中由依赖规则计算的最大日期',
  `REPLAYDATE` datetime(0) NULL DEFAULT NULL COMMENT '重放日期是转换的开始时间的日期',
  `LOG_FIELD` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '将包含运行的完整文本日志的字段。通常这是一个CLOB或(long)文本类型的字段',
  `EXECUTING_SERVER` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行此转换的服务器',
  `EXECUTING_USER` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行此转换的用户。如果可用或操作系统用户，则是存储库用户',
  `CLIENT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行转换的客户端: Spoon, pan, kitchen, carte.',
  INDEX `IDX_trans_log_1`(`ID_BATCH`) USING BTREE,
  INDEX `IDX_trans_log_2`(`ERRORS`, `STATUS`, `TRANSNAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;


SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for etl_trans_metrics_log
-- ----------------------------
CREATE TABLE IF NOT EXISTS `etl_trans_metrics_log`  (
  `ID_BATCH` int(11) NULL DEFAULT NULL COMMENT '批次ID',
  `CHANNEL_ID` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日志通道ID',
  `LOG_DATE` datetime(0) NULL DEFAULT NULL COMMENT '日志打印日期',
  `METRICS_DATE` datetime(0) NULL DEFAULT NULL COMMENT '这是度量度量的日期和时间。',
  `METRICS_CODE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '这是度量的代码(关键字)',
  `METRICS_DESCRIPTION` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '这是度量的描述',
  `METRICS_SUBJECT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '被测的对象(可多选)',
  `METRICS_TYPE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '度量的类型:开始、停止、求和、计数、最大值、……',
  `METRICS_VALUE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '在度量中测量的值'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;


SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for etl_trans_running_log
-- ----------------------------
CREATE TABLE IF NOT EXISTS `etl_trans_running_log`  (
  `ID_BATCH` int(11) NULL DEFAULT NULL COMMENT '批次ID',
  `SEQ_NR` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '一个简单的序列号，以保持快照记录的分离(1.N)',
  `LOG_DATE` datetime(0) NULL DEFAULT NULL COMMENT '快照日期和时间',
  `TRANSNAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行性能快照的转换的名称',
  `STEPNAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性能快照的步骤',
  `STEP_COPY` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '步骤拷贝数:0..(copies-1)',
  `LINES_READ` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '从之前的步骤中读取的行数',
  `LINES_WRITTEN` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '在间隔期间写入以下步骤的行数',
  `LINES_UPDATED` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '在间隔期间执行的update语句的数量',
  `LINES_INPUT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '在间隔期间从输入(文件、数据库、网络、…)读取的行数',
  `LINES_OUTPUT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '在间隔期间写入输出的行数(文件、数据库、网络、…)',
  `LINES_REJECTED` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '在间隔期间的步骤错误处理所拒绝的行数',
  `ERRORS` bigint(20) NULL DEFAULT NULL COMMENT '错误',
  `INPUT_BUFFER_ROWS` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '在快照时，步骤的大小输入缓冲区的大小',
  `OUTPUT_BUFFER_ROWS` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '快照时的输出缓冲区的大小'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;


SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for etl_trans_step_log
-- ----------------------------
CREATE TABLE IF NOT EXISTS `etl_trans_step_log`  (
  `ID_BATCH` int(11) NULL DEFAULT NULL COMMENT '批次ID',
  `CHANNEL_ID` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日志通道ID',
  `LOG_DATE` datetime(0) NULL DEFAULT NULL COMMENT '日志打印日期',
  `TRANSNAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '转换的名称',
  `STEPNAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '步骤名称',
  `STEP_COPY` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拷贝step数',
  `LINES_READ` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '从前面的步骤读取的行数',
  `LINES_WRITTEN` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '按照以下步骤编写的行数',
  `LINES_UPDATED` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行的update语句的数量',
  `LINES_INPUT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '从输入(文件、数据库、网络、…)中读取的行数',
  `LINES_OUTPUT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '通过步骤写入输出的行数(文件、数据库、网络、…)',
  `LINES_REJECTED` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处理错误步骤所拒绝的行数',
  `ERRORS` bigint(20) NULL DEFAULT NULL COMMENT '这个步骤中遇到的错误数量',
  `LOG_FIELD` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '日志字段用来存储由该步骤生成的日志行'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
