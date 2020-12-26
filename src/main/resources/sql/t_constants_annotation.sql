/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : localhost:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 26/12/2020 11:39:00
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_constants_annotation
-- ----------------------------
DROP TABLE IF EXISTS `t_constants_annotation`;
CREATE TABLE `t_constants_annotation`  (
  `constants_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '常量key',
  `constants_value` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '常量值',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `status` int(1) NULL DEFAULT 1 COMMENT '状态，1代表启用，0代表停用'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_constants_annotation
-- ----------------------------
INSERT INTO `t_constants_annotation` VALUES ('SCAN_PATH_MISSING_TEMPLATE', '%s%s系统已使用@%s注解，但未在配置文件中发现%s.scanPath', '注解扫描路径缺失', 1);
INSERT INTO `t_constants_annotation` VALUES ('TABLE_NOT_EXISTS_TEMPLATE', '[%s]类对应的数据库表`%s`不存在', '数据库表不存在', 1);
INSERT INTO `t_constants_annotation` VALUES ('COLUMN_NOT_EXISTS_TEMPLATE', '配置的[%s]类对应的数据库表`%s`的属性映射字段`%s`不存在', '配置的属性映射字段不存在', 1);
INSERT INTO `t_constants_annotation` VALUES ('FIELD_NOT_CONFIGURED_TEMPLATE', '[%s]类的[%s]属性值未在数据库表`%s`中配置', '属性值未在数据库表中配置', 1);
INSERT INTO `t_constants_annotation` VALUES ('FIELD_UNMATCHED_TEMPLATE', '[%s]类的[%s]属性名与数据库表`%s`的字段名不匹配', '属性名与数据库表字段名不匹配', 1);
INSERT INTO `t_constants_annotation` VALUES ('FIELD_VALUE_UNMATCHED_TEMPLATE', '[%s]类的[%s]属性映射字段`%s`与数据库表`%s`的字段名不匹配', '属性的@Value注解值与数据库表字段名不匹配', 1);

SET FOREIGN_KEY_CHECKS = 1;
