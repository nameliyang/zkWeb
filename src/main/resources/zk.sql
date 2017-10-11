/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50718
Source Host           : localhost:3306
Source Database       : conseb

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2017-10-11 17:13:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for zk
-- ----------------------------
DROP TABLE IF EXISTS `zk`;
CREATE TABLE `zk` (
  `ID` varchar(255) NOT NULL,
  `DES` varchar(255) DEFAULT NULL,
  `CONNECTSTR` varchar(255) DEFAULT NULL,
  `SESSIONTIMEOUT` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of zk
-- ----------------------------
