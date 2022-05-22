/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50735
 Source Host           : localhost:3306
 Source Schema         : acs

 Target Server Type    : MySQL
 Target Server Version : 50735
 File Encoding         : 65001

 Date: 22/05/2022 22:30:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父级ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `level` int(11) NULL DEFAULT NULL COMMENT '菜单级数',
  `sort` int(11) NULL DEFAULT NULL COMMENT '菜单排序',
  `icon` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单前端图标',
  `hidden` int(11) NULL DEFAULT NULL COMMENT '控制前端是否显示 1:显示 0: 隐藏',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, 0, '2022-05-12 20:43:21', '权限管理', 1, 0, NULL, 1);
INSERT INTO `menu` VALUES (2, 1, '2022-05-12 20:43:21', '用户管理', 2, 0, NULL, 0);
INSERT INTO `menu` VALUES (3, 1, '2022-05-12 20:43:21', '角色管理', 2, 0, NULL, 0);
INSERT INTO `menu` VALUES (4, 1, '2022-05-12 20:43:21', '资源管理', 2, 0, NULL, 0);
INSERT INTO `menu` VALUES (5, 1, '2022-05-12 20:43:21', '菜单管理', 2, 0, NULL, 0);
INSERT INTO `menu` VALUES (6, 0, '2022-05-12 20:43:21', '日常管理', 1, 0, NULL, 1);
INSERT INTO `menu` VALUES (7, 0, '2022-05-12 20:43:21', '教学管理', 1, 0, NULL, 1);
INSERT INTO `menu` VALUES (8, 0, '2022-05-12 20:43:21', '团务管理', 1, 0, NULL, 1);
INSERT INTO `menu` VALUES (9, 0, '2022-05-12 20:43:21', '文体管理', 1, 0, NULL, 1);

-- ----------------------------
-- Table structure for resource
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `category_id` bigint(20) NULL DEFAULT NULL COMMENT '分类ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源名称',
  `url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源URL',
  `description` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of resource
-- ----------------------------
INSERT INTO `resource` VALUES (1, 1, '2022-05-12 20:20:02', '请假管理', '/asl/**', NULL);
INSERT INTO `resource` VALUES (2, 1, '2022-05-12 20:20:02', '班会管理', '/meet/**', NULL);
INSERT INTO `resource` VALUES (3, 1, '2022-05-12 20:20:02', '通知管理', '/information/school/**', NULL);
INSERT INTO `resource` VALUES (4, 2, '2022-05-12 20:20:02', '教学通知管理', '/information/edu/**', NULL);
INSERT INTO `resource` VALUES (5, 2, '2022-05-12 20:20:02', '作业管理', '/work/**', NULL);
INSERT INTO `resource` VALUES (6, 2, '2022-05-12 20:20:02', '教学考勤管理', '/attendance/**', NULL);
INSERT INTO `resource` VALUES (7, 3, '2022-05-12 20:20:02', '团日活动管理', '/activity/cyl/**', NULL);
INSERT INTO `resource` VALUES (8, 3, '2022-05-12 20:20:02', '团费管理', '/fee/cyl/**', NULL);
INSERT INTO `resource` VALUES (9, 4, '2022-05-12 20:20:02', '文艺活动管理', '/activity/art/**', NULL);
INSERT INTO `resource` VALUES (10, 4, '2022-05-12 20:20:02', '体育活动管理', '/activity/pe/**', NULL);
INSERT INTO `resource` VALUES (11, 5, '2022-05-12 20:32:12', '用户管理', '/user/**', NULL);
INSERT INTO `resource` VALUES (12, 5, '2022-05-12 20:32:12', '角色管理', '/role/**', NULL);
INSERT INTO `resource` VALUES (13, 5, '2022-05-12 20:32:12', '资源管理', '/resource/**', '资源管理');
INSERT INTO `resource` VALUES (15, NULL, '2022-05-21 09:19:51', '摸鱼二号', '/moyu/2/**', '再不摸就晚了');
INSERT INTO `resource` VALUES (17, NULL, '2022-05-21 10:06:21', '摸鱼一号', '/moyu/1/**', '再不摸就晚了');
INSERT INTO `resource` VALUES (18, NULL, '2022-05-21 10:38:21', '菜单管理', '/menu/**', '菜单管理');

-- ----------------------------
-- Table structure for resource_category
-- ----------------------------
DROP TABLE IF EXISTS `resource_category`;
CREATE TABLE `resource_category`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类名称',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `description` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of resource_category
-- ----------------------------
INSERT INTO `resource_category` VALUES (1, '2022-05-12 18:36:01', '日常管理', 0, '班级日常管理权限');
INSERT INTO `resource_category` VALUES (2, '2022-05-12 18:36:01', '权限管理', 0, '权限管理');
INSERT INTO `resource_category` VALUES (3, '2022-05-12 18:36:01', '教学管理', 0, '班级日常管理权限');
INSERT INTO `resource_category` VALUES (4, '2022-05-12 18:36:01', '团务管理', 0, '班级团组织管理权限');
INSERT INTO `resource_category` VALUES (5, '2022-05-12 18:36:01', '文体管理', 0, '班级艺术体育类权限');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名',
  `description` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '角色描述',
  `create_time` datetime NULL DEFAULT NULL COMMENT '注册时间',
  `status` int(11) NULL DEFAULT 1 COMMENT '角色状态 1: 激活 0: 禁用',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '超级管理员', '拥有最高权限', '2022-05-11 22:38:29', 1);
INSERT INTO `role` VALUES (2, '班主任', '拥有查看和管理班委的权限', '2022-05-11 22:38:29', 1);
INSERT INTO `role` VALUES (3, '班长', '拥有查看和管理班级的权限', '2022-05-11 22:43:18', 1);
INSERT INTO `role` VALUES (4, '学习委员', '拥有查看和管理教学的权限', '2022-05-11 22:43:18', 1);
INSERT INTO `role` VALUES (5, '团支书', '拥有查看和管理团组织的权限', '2022-05-11 23:02:13', 1);
INSERT INTO `role` VALUES (6, '文体委员', '拥有查看和管理课外活动的权限', '2022-05-11 23:02:13', 1);
INSERT INTO `role` VALUES (8, '劳动委员', '劳动管理111', '2022-05-16 22:06:51', 1);
INSERT INTO `role` VALUES (9, '摸鱼委员', '摸鱼管理', '2022-05-17 18:42:04', 1);

-- ----------------------------
-- Table structure for role_menu_relation
-- ----------------------------
DROP TABLE IF EXISTS `role_menu_relation`;
CREATE TABLE `role_menu_relation`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `menu_id` bigint(20) NULL DEFAULT NULL,
  `role_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_menu_relation
-- ----------------------------
INSERT INTO `role_menu_relation` VALUES (1, 1, 1);
INSERT INTO `role_menu_relation` VALUES (2, 2, 1);
INSERT INTO `role_menu_relation` VALUES (3, 3, 1);
INSERT INTO `role_menu_relation` VALUES (4, 4, 1);
INSERT INTO `role_menu_relation` VALUES (5, 5, 1);
INSERT INTO `role_menu_relation` VALUES (6, 6, 1);
INSERT INTO `role_menu_relation` VALUES (7, 7, 1);
INSERT INTO `role_menu_relation` VALUES (8, 8, 1);
INSERT INTO `role_menu_relation` VALUES (9, 9, 1);
INSERT INTO `role_menu_relation` VALUES (18, 8, 5);
INSERT INTO `role_menu_relation` VALUES (19, 7, 4);
INSERT INTO `role_menu_relation` VALUES (21, 1, 9);
INSERT INTO `role_menu_relation` VALUES (22, 2, 9);
INSERT INTO `role_menu_relation` VALUES (23, 3, 9);

-- ----------------------------
-- Table structure for role_resource_relation
-- ----------------------------
DROP TABLE IF EXISTS `role_resource_relation`;
CREATE TABLE `role_resource_relation`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `resource_id` bigint(20) NULL DEFAULT NULL,
  `role_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 57 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_resource_relation
-- ----------------------------
INSERT INTO `role_resource_relation` VALUES (31, 9, 6);
INSERT INTO `role_resource_relation` VALUES (32, 10, 6);
INSERT INTO `role_resource_relation` VALUES (37, 4, 4);
INSERT INTO `role_resource_relation` VALUES (38, 5, 4);
INSERT INTO `role_resource_relation` VALUES (39, 6, 4);
INSERT INTO `role_resource_relation` VALUES (43, 1, 1);
INSERT INTO `role_resource_relation` VALUES (44, 2, 1);
INSERT INTO `role_resource_relation` VALUES (45, 3, 1);
INSERT INTO `role_resource_relation` VALUES (46, 4, 1);
INSERT INTO `role_resource_relation` VALUES (47, 5, 1);
INSERT INTO `role_resource_relation` VALUES (48, 6, 1);
INSERT INTO `role_resource_relation` VALUES (49, 7, 1);
INSERT INTO `role_resource_relation` VALUES (50, 8, 1);
INSERT INTO `role_resource_relation` VALUES (51, 9, 1);
INSERT INTO `role_resource_relation` VALUES (52, 10, 1);
INSERT INTO `role_resource_relation` VALUES (53, 11, 1);
INSERT INTO `role_resource_relation` VALUES (54, 12, 1);
INSERT INTO `role_resource_relation` VALUES (55, 13, 1);
INSERT INTO `role_resource_relation` VALUES (56, 18, 1);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户密码',
  `nickname` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `profile` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `create_time` datetime NULL DEFAULT NULL COMMENT '注册时间',
  `login_time` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `status` int(11) NULL DEFAULT 1 COMMENT '账号状态 1: 激活 0: 禁用',
  `description` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  UNIQUE INDEX `username_2`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'ccy1277', '$2a$10$xzrFNi/gCWoNfxebxPcEl.No2JQ4srrTTVfRvBnMlxCtBo2GY6dwq', '夜小辰', '', 'ccy1277@139.com', '2022-05-11 17:01:42', NULL, 1, '最高权限拥有者');
INSERT INTO `user` VALUES (2, 'yige', '$2a$10$t120c4rolaM7VWzD86wg4.tQLdyQpgRQSnFERm3q3K.aceYEWtgt6', '一哥', NULL, 'yige@qq.com', '2022-05-13 21:16:59', NULL, 1, '');
INSERT INTO `user` VALUES (4, 'test', '$2a$10$MMsURhldZT2WMRhhWPYnn.2krHYMwGOWONk8QI8htcGMO0NTWpAP.', '专注', 'string', '134@123.com', '2022-05-19 20:08:54', NULL, 1, NULL);

-- ----------------------------
-- Table structure for user_role_relation
-- ----------------------------
DROP TABLE IF EXISTS `user_role_relation`;
CREATE TABLE `user_role_relation`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL,
  `role_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role_relation
-- ----------------------------
INSERT INTO `user_role_relation` VALUES (3, 4, 4);
INSERT INTO `user_role_relation` VALUES (4, 4, 5);
INSERT INTO `user_role_relation` VALUES (7, 1, 1);
INSERT INTO `user_role_relation` VALUES (8, 1, 2);

SET FOREIGN_KEY_CHECKS = 1;
