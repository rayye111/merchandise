/*
 Navicat Premium Data Transfer

 Source Server         : main
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : goods-simulation

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 19/11/2023 16:20:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for edges
-- ----------------------------
DROP TABLE IF EXISTS `edges`;
CREATE TABLE `edges`  (
  `edge_id` int NOT NULL AUTO_INCREMENT,
  `source_node_id` int NULL DEFAULT NULL,
  `dest_node_id` int NULL DEFAULT NULL,
  `weight` double NULL DEFAULT NULL,
  PRIMARY KEY (`edge_id`) USING BTREE,
  INDEX `source_node_id`(`source_node_id` ASC) USING BTREE,
  INDEX `dest_node_id`(`dest_node_id` ASC) USING BTREE,
  CONSTRAINT `edges_ibfk_1` FOREIGN KEY (`source_node_id`) REFERENCES `nodes` (`node_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `edges_ibfk_2` FOREIGN KEY (`dest_node_id`) REFERENCES `nodes` (`node_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of edges
-- ----------------------------

-- ----------------------------
-- Table structure for nodes
-- ----------------------------
DROP TABLE IF EXISTS `nodes`;
CREATE TABLE `nodes`  (
  `node_id` int NOT NULL AUTO_INCREMENT,
  `node_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `x_coord` int NULL DEFAULT NULL,
  `y_coord` int NULL DEFAULT NULL,
  PRIMARY KEY (`node_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of nodes
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
