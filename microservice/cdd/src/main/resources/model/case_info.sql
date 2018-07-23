/*
Navicat MySQL Data Transfer

Source Server         : 10.119.169.2_3306
Source Server Version : 50721
Source Host           : 10.119.169.2:3306
Source Database       : cdd_2_0_dev_dmn

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-07-07 09:44:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for case_info
-- ----------------------------
DROP TABLE IF EXISTS `case_info`;
CREATE TABLE `case_info` (
  `ID` varchar(32) COLLATE utf8_bin NOT NULL,
  `TASK_ID` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `CASE_KEY` varchar(64) COLLATE utf8_bin NOT NULL,
  `CREATER` varchar(32) CHARACTER SET utf8 DEFAULT NULL,
  `STATUS` varchar(32) CHARACTER SET utf8 DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `CLAIMED_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT;
