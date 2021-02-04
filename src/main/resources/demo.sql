/*
Navicat MySQL Data Transfer

Source Server         : kid626
Source Server Version : 50640
Source Host           : localhost:3306
Source Database       : tree_demo

Target Server Type    : MYSQL
Target Server Version : 50640
File Encoding         : 65001

Date: 2021-01-29 16:29:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for demo_tree
-- ----------------------------
DROP TABLE IF EXISTS `demo_tree`;
CREATE TABLE `demo_tree` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `p_id` bigint(20) DEFAULT NULL COMMENT '父节点主键',
  `code` varchar(32) DEFAULT NULL COMMENT '唯一编码',
  `name` varchar(32) DEFAULT NULL COMMENT '名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_delete` char(1) DEFAULT NULL COMMENT '是否删除Y/N',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
