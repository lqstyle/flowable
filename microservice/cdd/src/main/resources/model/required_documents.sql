/*
Navicat MySQL Data Transfer

Source Server         : 10.119.169.2_3306
Source Server Version : 50721
Source Host           : 10.119.169.2:3306
Source Database       : cdd_2_0_dev_dmn

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-06-27 13:44:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for required_documents
-- ----------------------------
DROP TABLE IF EXISTS `required_documents`;
CREATE TABLE `required_documents` (
  `id` int(8) NOT NULL,
  `document_id` int(8) NOT NULL,
  `document_name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of required_documents
-- ----------------------------
INSERT INTO `required_documents` VALUES ('1', '101', 'Form 8938');
INSERT INTO `required_documents` VALUES ('2', '102', 'Form 114');
INSERT INTO `required_documents` VALUES ('3', '103', 'Business Registration Certificate');
INSERT INTO `required_documents` VALUES ('4', '104', 'UBO ID');
INSERT INTO `required_documents` VALUES ('5', '105', 'UBO Proof of Address');
INSERT INTO `required_documents` VALUES ('6', '106', 'UBO Nationality');
INSERT INTO `required_documents` VALUES ('7', '107', 'Certificate of Registration');
INSERT INTO `required_documents` VALUES ('8', '108', 'Society Form 1');
INSERT INTO `required_documents` VALUES ('9', '109', 'Constitution');
INSERT INTO `required_documents` VALUES ('10', '110', 'AS ID');
INSERT INTO `required_documents` VALUES ('11', '111', 'AS Proof of Address');
INSERT INTO `required_documents` VALUES ('12', '112', 'AS Nationality');
