/*
Navicat MySQL Data Transfer

Source Server         : localhosy_3306
Source Server Version : 50639
Source Host           : localhost:3306
Source Database       : bmpvacationflow

Target Server Type    : MYSQL
Target Server Version : 50639
File Encoding         : 65001

Date: 2018-06-12 11:37:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for menu_role
-- ----------------------------
DROP TABLE IF EXISTS `menu_role`;
CREATE TABLE `menu_role` (
  `role_id` varchar(64) NOT NULL COMMENT '角色编号',
  `menu_id` varchar(64) NOT NULL COMMENT '菜单编号',
  PRIMARY KEY (`role_id`,`menu_id`),
  KEY `menu_id` (`menu_id`),
  CONSTRAINT `menu_role_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `menu_role_ibfk_2` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色-菜单关联表';

-- ----------------------------
-- Records of menu_role
-- ----------------------------
INSERT INTO `menu_role` VALUES ('1', '1');
INSERT INTO `menu_role` VALUES ('1', '2');
INSERT INTO `menu_role` VALUES ('66aec3a7-c5e4-43c0-9f3b-50c6e44d5695', '2');
INSERT INTO `menu_role` VALUES ('1', '3');
INSERT INTO `menu_role` VALUES ('1', '4');
