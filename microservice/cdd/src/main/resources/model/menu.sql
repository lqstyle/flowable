/*
Navicat MySQL Data Transfer

Source Server         : 10.119.169.2_3306
Source Server Version : 50721
Source Host           : 10.119.169.2:3306
Source Database       : cdd2.0_dev

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-06-13 09:13:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `parent_id` varchar(64) DEFAULT NULL COMMENT '父级编号',
  `parent_ids` varchar(2000) DEFAULT NULL COMMENT '所有父级编号',
  `name` varchar(100) DEFAULT NULL COMMENT '名称',
  `sort` decimal(10,0) DEFAULT NULL COMMENT '排序',
  `href` varchar(2000) NOT NULL DEFAULT '' COMMENT '链接',
  `icon` varchar(100) DEFAULT '' COMMENT '图标',
  `permission` varchar(200) DEFAULT '' COMMENT '权限标识',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记 0:否;1:是',
  `menu_type` char(1) DEFAULT '0' COMMENT '0:菜单；1:按钮',
  `menu_owner` char(1) DEFAULT '0' COMMENT '菜单归属 0:系统 1:项目',
  PRIMARY KEY (`id`),
  KEY `menu_parent_id` (`parent_id`),
  KEY `menu_del_flag` (`del_flag`),
  KEY `menu_menuType` (`menu_type`) USING BTREE,
  KEY `menu_parent_ids` (`parent_ids`(255)) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统菜单表';

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', '1', null, 'UserManage', '1', '/userManagement', '', '', 'admin', '2018-06-09 10:37:51', '', '0000-00-00 00:00:00', 'UserManage', '0', '0', '0');
INSERT INTO `menu` VALUES ('2', '1', null, 'CaseManage', '2', '/taskManagement', '', '', 'admin', '2018-06-28 17:09:32', null, null, 'CaseManage', '0', '0', '0');
INSERT INTO `menu` VALUES ('3', '1', null, 'RoleManage', '3', '/roleManagement', '', '', 'admin', '2018-06-09 22:00:12', null, null, 'RoleManage', '0', '0', '0');
INSERT INTO `menu` VALUES ('4', '1', null, '资源管理', null, '/menu', '', '', 'admin', '2018-06-09 22:59:33', null, null, '资源管理页面', '0', '0', '0');
INSERT INTO `menu` VALUES ('5', '1', null, '历史查看', null, '/history', '', '', 'admin', '2018-06-12 15:15:48', null, null, '历史查询页面', '0', '0', '0');
