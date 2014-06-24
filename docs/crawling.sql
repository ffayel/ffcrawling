/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50166
Source Host           : localhost:3306
Source Database       : crawling

Target Server Type    : MYSQL
Target Server Version : 50166
File Encoding         : 65001

Date: 2014-06-24 23:55:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `image`
-- ----------------------------
DROP TABLE IF EXISTS `image`;
CREATE TABLE `image` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(255) DEFAULT NULL,
  `dateload` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `job`
-- ----------------------------
DROP TABLE IF EXISTS `job`;
CREATE TABLE `job` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `datecreate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `lnk_img_site`
-- ----------------------------
DROP TABLE IF EXISTS `lnk_img_site`;
CREATE TABLE `lnk_img_site` (
  `id_img` int(11) NOT NULL,
  `id_site` int(11) NOT NULL,
  PRIMARY KEY (`id_img`,`id_site`),
  KEY `fk_site` (`id_site`),
  CONSTRAINT `fk_image` FOREIGN KEY (`id_img`) REFERENCES `image` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_site` FOREIGN KEY (`id_site`) REFERENCES `site` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `lnk_site_job`
-- ----------------------------
DROP TABLE IF EXISTS `lnk_site_job`;
CREATE TABLE `lnk_site_job` (
  `id_job` int(11) NOT NULL,
  `id_site` int(11) NOT NULL,
  PRIMARY KEY (`id_job`,`id_site`),
  KEY `fk_site1` (`id_site`),
  CONSTRAINT `fk_job` FOREIGN KEY (`id_job`) REFERENCES `job` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_site1` FOREIGN KEY (`id_site`) REFERENCES `site` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `site`
-- ----------------------------
DROP TABLE IF EXISTS `site`;
CREATE TABLE `site` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
