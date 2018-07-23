/*
Navicat MySQL Data Transfer

Source Server         : 10.119.169.2_3306
Source Server Version : 50721
Source Host           : 10.119.169.2:3306
Source Database       : cdd_2_0_dev_dmn

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-07-07 09:45:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for document
-- ----------------------------
DROP TABLE IF EXISTS `document`;
CREATE TABLE `document` (
  `id` int(32) DEFAULT NULL,
  `case_id` int(32) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `type` varchar(32) DEFAULT NULL,
  `upload_url` varchar(255) DEFAULT NULL,
  `user_id` int(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
