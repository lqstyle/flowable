/*
Navicat MySQL Data Transfer

Source Server         : 10.119.169.2_3306
Source Server Version : 50721
Source Host           : 10.119.169.2:3306
Source Database       : cdd_2_0_dev_dmn

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-07-07 10:49:40
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for client
-- ----------------------------
DROP TABLE IF EXISTS `client`;
CREATE TABLE `client` (
  `id_` int(8) NOT NULL,
  `case_id_` int(32) DEFAULT NULL,
  `name_` varchar(255) DEFAULT NULL,
  `industry_` varchar(255) DEFAULT NULL,
  `phone_` varchar(32) DEFAULT NULL,
  `email_` varchar(32) DEFAULT NULL,
  `faxNo_` varchar(32) DEFAULT NULL,
  `country_` varchar(32) DEFAULT NULL,
  `province_` varchar(32) DEFAULT NULL,
  `address_` varchar(255) DEFAULT NULL,
  `entity_type_` varchar(32) DEFAULT NULL,
  `result_state_` varchar(32) DEFAULT NULL,
  `required_document_list_` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of client
-- ----------------------------
