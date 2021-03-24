/*
Navicat MySQL Data Transfer

Source Server         : localhost:3306
Source Server Version : 50722
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2021-03-24 17:15:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `ad_user`
-- ----------------------------
DROP TABLE IF EXISTS `ad_user`;
CREATE TABLE `ad_user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ACCOUNT` varchar(255) DEFAULT NULL,
  `PWD` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `SEX` tinyint(1) DEFAULT '0',
  `ROLE_ID` int(11) DEFAULT NULL,
  `STATUS` tinyint(2) DEFAULT NULL,
  `CRT_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of ad_user
-- ----------------------------
INSERT INTO `ad_user` VALUES ('1', '测试', '123test', '测试', '0', null, '1', '2021-03-24 16:50:24');

-- ----------------------------
-- Table structure for `ps_sig_img`
-- ----------------------------
DROP TABLE IF EXISTS `ps_sig_img`;
CREATE TABLE `ps_sig_img` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ACCOUNT` varchar(255) DEFAULT NULL,
  `SIG1` longtext,
  `SIG2` longtext,
  `SIG3` longtext,
  `SEAL` longtext,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

 
-- ----------------------------
-- Table structure for `ps_user`
-- ----------------------------
DROP TABLE IF EXISTS `ps_user`;
CREATE TABLE `ps_user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ACCOUNT` varchar(255) DEFAULT NULL,
  `PWD` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `SEX` tinyint(1) DEFAULT '0',
  `CODE` varchar(255) DEFAULT NULL,
  `CODE_TYPE` tinyint(2) DEFAULT NULL,
  `CRT_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of ps_user
-- ----------------------------
INSERT INTO `ps_user` VALUES ('1', '测试', '123test', '测试', '0', '110101202101011425', '1', '2021-03-24 16:50:24');
