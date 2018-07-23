/*
Navicat MySQL Data Transfer

Source Server         : localhosy_3306
Source Server Version : 50639
Source Host           : localhost:3306
Source Database       : bmpvacationflow

Target Server Type    : MYSQL
Target Server Version : 50639
File Encoding         : 65001

Date: 2018-06-12 11:37:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `login_name` varchar(50) NOT NULL COMMENT '登录名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `employee_no` varchar(10) DEFAULT NULL COMMENT '工号',
  `department` varchar(50) DEFAULT NULL COMMENT '部门',
  `name` varchar(50) NOT NULL COMMENT '姓名',
  `email` varchar(30) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) DEFAULT NULL COMMENT '电话',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机',
  `user_type` char(1) DEFAULT NULL COMMENT '用户类型0：超级管理员 1：普通用户',
  `grade` varchar(30) DEFAULT NULL COMMENT '用户职级',
  `user_level` varchar(10) DEFAULT NULL COMMENT '用户职级：a-p',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_login_name` (`login_name`) USING BTREE,
  KEY `user_update_date` (`update_date`),
  KEY `user_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '1', '32121', null, '', 'admin.liang@kpmg.com', '', null, null, null, null, '', '2018-06-04 13:41:38', '', '2018-06-10 13:41:45', null, '0');
INSERT INTO `user` VALUES ('6586be8c-57c6-4441-b83f-7025d528dcd6', 'lucas', '1', null, null, '1', '1', '1', '1', null, null, null, 'admin', '2018-06-12 11:13:40', null, null, null, '0');
INSERT INTO `user` VALUES ('e5da423e-78f3-413d-8e74-dd55457fa0f3', 'lucas1', '1', null, null, '1', '1', '1', '1', null, null, null, 'admin', '2018-06-12 11:35:51', null, null, null, '0');
