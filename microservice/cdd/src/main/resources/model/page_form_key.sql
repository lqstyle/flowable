/*
Navicat MySQL Data Transfer

Source Server         : 10.119.169.2_3306
Source Server Version : 50721
Source Host           : 10.119.169.2:3306
Source Database       : cdd_2_0_dev_dmn

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-06-27 10:48:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for page_form_key
-- ----------------------------
DROP TABLE IF EXISTS `page_form_key`;
CREATE TABLE `page_form_key` (
  `id` int(8) NOT NULL,
  `page_id` int(8) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `roll_back` varchar(255) DEFAULT NULL,
  `current_option` varchar(255) DEFAULT NULL,
  `post_url` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of page_form_key
-- ----------------------------
INSERT INTO `page_form_key` VALUES ('1', '100101', 'Create Case', '', null, 'Create', null);
INSERT INTO `page_form_key` VALUES ('2', '100102', 'Capture All Files for the Case', null, null, 'Submit', null);
INSERT INTO `page_form_key` VALUES ('3', '100103', 'Separate File Package w/DST', null, 'Back', 'Submit', null);
INSERT INTO `page_form_key` VALUES ('4', '100104', 'Automated Document Ingestion', 'Auto Ingestion', null, null, null);
INSERT INTO `page_form_key` VALUES ('5', '100105', 'Manual Document Ingestion & Corrections w/DIT', null, 'Back', 'Submit', null);
INSERT INTO `page_form_key` VALUES ('6', '100106', 'Case Created & Run Policy Rules Engine', 'Run Rules Engine', null, null, null);
INSERT INTO `page_form_key` VALUES ('7', '100107', 'Validate Killer Characteristics', null, 'Back', 'Submit', null);
INSERT INTO `page_form_key` VALUES ('8', '100108', 'Bank Review & Approval /Exit', null, 'Exit', 'Approved', null);
INSERT INTO `page_form_key` VALUES ('9', '100109', 'Validate CDS Code', null, 'Back', 'Submit', null);
INSERT INTO `page_form_key` VALUES ('10', '100110', 'Validate Contact Information', null, 'Back', 'Submit', null);
INSERT INTO `page_form_key` VALUES ('11', '100111', 'Perform Case Review and Doc Linking', null, 'Back', 'Submit', null);
INSERT INTO `page_form_key` VALUES ('12', '100112', 'Outbound Letters and Letter Cycle', null, null, 'Send', null);
INSERT INTO `page_form_key` VALUES ('13', '100113', 'Complete Questionaire on KYC Portal', null, null, 'Submit', null);
INSERT INTO `page_form_key` VALUES ('14', '100114', 'Capture Date in KYC Portal', null, null, 'Submit', null);
INSERT INTO `page_form_key` VALUES ('15', '100115', 'Contact Customer', null, 'Back', 'Contact', null);
INSERT INTO `page_form_key` VALUES ('16', '100116', 'Conduct CDD Interview', null, 'Back', 'Conduct Interview', null);
INSERT INTO `page_form_key` VALUES ('17', '100117', 'Identification & Verification(ID&V))', null, 'Back', 'Submit', null);
INSERT INTO `page_form_key` VALUES ('18', '100118', 'Retrieve Screening / Negative News', null, 'Back', 'Pass', null);
INSERT INTO `page_form_key` VALUES ('19', '100119', 'Review KYC', null, 'Badk', 'Pass', null);
INSERT INTO `page_form_key` VALUES ('20', '100120', 'Review EDD', null, 'Back', 'Pass', null);
INSERT INTO `page_form_key` VALUES ('21', '100121', 'Obtain FInal Risk Rating', null, 'Badk', 'Pass', null);
INSERT INTO `page_form_key` VALUES ('22', '100122', 'Complete Overall Case Summary', null, 'Back', 'Completed', null);
INSERT INTO `page_form_key` VALUES ('23', '100123', 'Quality Control before Sending Case to Bank', null, 'Back', 'Pass', null);
INSERT INTO `page_form_key` VALUES ('24', '100124', 'Bank Review & Approval', null, 'Back', 'Approved', null);
INSERT INTO `page_form_key` VALUES ('25', '100125', 'Quality Assurance by Bank', null, 'Back', 'Pass', null);
INSERT INTO `page_form_key` VALUES ('26', '100126', 'Close Case& Transfer File to Bank System', null, 'Back', 'Closed', null);

