/*
 Navicat MySQL Data Transfer

 Source Server         : cdd
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : 10.119.169.2:3306
 Source Schema         : cdd2.0_dev

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 14/06/2018 11:02:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for operation_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `operation_dictionary`;
CREATE TABLE `operation_dictionary`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '字典id',
  `status` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '按钮名称',
  `role` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色',
  `title` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '弹出框标题',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 63 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '状态字典表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
