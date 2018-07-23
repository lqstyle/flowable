/*
Navicat MySQL Data Transfer

Source Server         : localhosy_3306
Source Server Version : 50639
Source Host           : localhost:3306
Source Database       : bmpvacationflow

Target Server Type    : MYSQL
Target Server Version : 50639
File Encoding         : 65001

Date: 2018-06-12 11:37:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` varchar(64) NOT NULL COMMENT '用户编号',
  `role_id` varchar(64) NOT NULL COMMENT '角色编号',
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户-角色关联表';

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1');
INSERT INTO `user_role` VALUES ('6586be8c-57c6-4441-b83f-7025d528dcd6', '66aec3a7-c5e4-43c0-9f3b-50c6e44d5695');
INSERT INTO `user_role` VALUES ('e5da423e-78f3-413d-8e74-dd55457fa0f3', '66aec3a7-c5e4-43c0-9f3b-50c6e44d5695');
