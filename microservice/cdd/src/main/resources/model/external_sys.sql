/*
Navicat MySQL Data Transfer

Source Server         : 10.119.169.2_3306
Source Server Version : 50721
Source Host           : 10.119.169.2:3306
Source Database       : cdd_2_0_dev_dmn

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-07-09 10:08:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for external_sys
-- ----------------------------
DROP TABLE IF EXISTS `external_sys`;
CREATE TABLE `external_sys` (
  `id` int(8) NOT NULL,
  `case_id` varchar(32) DEFAULT NULL,
  `name` varchar(32) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `commit_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
