/*
Navicat MySQL Data Transfer

Source Server         : localhosy_3306
Source Server Version : 50639
Source Host           : localhost:3306
Source Database       : bmpvacationflow

Target Server Type    : MYSQL
Target Server Version : 50639
File Encoding         : 65001

Date: 2018-06-12 11:37:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `name` varchar(50) NOT NULL COMMENT '角色名称',
  `enname` varchar(255) DEFAULT NULL COMMENT '英文名称',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT 'N' COMMENT '删除标记 Y：删除， N：不删除',
  `role_owner` char(1) DEFAULT '0' COMMENT '角色归属：0:系统 1:项目',
  PRIMARY KEY (`id`),
  KEY `role_del_flag` (`del_flag`),
  KEY `role_enname` (`enname`),
  KEY `role_name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统角色表';

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('100', 'admin', 'admin', 'admin', '2018-06-28 10:38:16', null, null, null, '0', '0');
INSERT INTO `role` VALUES ('101', 'KPMG CDD Assistant', 'KPMG CDD Assistant', 'admin', '2018-06-12 10:42:19', null, null, 'CDD Assistant', '0', null);
INSERT INTO `role` VALUES ('102', 'KPMG CDD Executive', 'KPMG CDD Executive', 'admin', '2018-06-12 10:42:32', null, null, 'CDD Executive', '0', null);
INSERT INTO `role` VALUES ('103', 'KPMG Telephony Support', 'KPMG Telephony Support', 'admin', '2018-06-12 10:42:44', null, null, 'Telephony Support', '0', null);
INSERT INTO `role` VALUES ('104', 'KPMG Quality Control (QC)', 'KPMG Quality Control (QC)', 'admin', '2018-06-12 10:42:53', null, null, 'Quality Control (QC)', '0', null);
INSERT INTO `role` VALUES ('105', 'Bank Business Approver (BA)', 'Bank Business Approver (BA)', 'admin', '2018-06-12 10:43:14', null, null, 'Business Approver (BA)', '0', null);
INSERT INTO `role` VALUES ('106', 'Bank Financial Crime Compliance (FCC)', 'Bank Financial Crime Compliance (FCC)', 'admin', '2018-06-12 10:43:24', null, null, 'Financial Crime Compliance (FCC)', '0', null);
INSERT INTO `role` VALUES ('107', 'Bank Quality Assurance (QA)', 'Bank Quality Assurance (QA)', 'admin', '2018-06-12 10:43:33', null, null, 'Quality Assurance (QA)', '0', null);
INSERT INTO `role` VALUES ('110', 'Bank Case Manager', 'Bank Case Manager', 'admin', '2018-06-12 10:43:03', null, null, 'Case Manager', '0', null);
