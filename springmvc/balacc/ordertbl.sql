/*
Navicat MySQL Data Transfer

Source Server         : 6.238
Source Server Version : 50628
Source Host           : 192.168.6.238:3306
Source Database       : rightsman

Target Server Type    : MYSQL
Target Server Version : 50628
File Encoding         : 65001

Date: 2018-08-17 18:35:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `ordertbl`
-- ----------------------------
DROP TABLE IF EXISTS `ordertbl`;
CREATE TABLE `ordertbl` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `time` time DEFAULT NULL,
  `timestp` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `dtime` datetime DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ordertbl
-- ----------------------------
INSERT INTO `ordertbl` VALUES ('1', 't1', '00:00:00', null, null, '2018-08-17');
INSERT INTO `ordertbl` VALUES ('2', '时间', '00:00:00', null, null, '2018-08-17');
INSERT INTO `ordertbl` VALUES ('3', '时间', '14:00:09', null, null, '2018-08-17');
INSERT INTO `ordertbl` VALUES ('4', '时间', '14:14:19', '2018-07-17 14:12:20', null, '2018-08-17');
INSERT INTO `ordertbl` VALUES ('5', '时间', '14:14:19', '2018-07-17 14:12:20', null, '2018-07-17');
INSERT INTO `ordertbl` VALUES ('6', '时间', '14:14:19', '2018-07-16 14:12:20', null, '2018-07-17');
INSERT INTO `ordertbl` VALUES ('7', '时间', '14:14:19', '2018-07-16 14:12:20', null, '2018-07-17');
INSERT INTO `ordertbl` VALUES ('8', '时间', '14:14:19', '2018-07-16 14:12:20', null, '2018-06-17');
INSERT INTO `ordertbl` VALUES ('9', '时间', '14:14:19', '2018-07-16 14:12:20', null, '2018-09-17');
