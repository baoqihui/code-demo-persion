/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : localhost:3306
 Source Schema         : demo

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 25/03/2021 19:59:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ums_depa
-- ----------------------------
DROP TABLE IF EXISTS `ums_depa`;
CREATE TABLE `ums_depa`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '代码',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `area` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `type` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_del` tinyint(1) UNSIGNED ZEROFILL NULL DEFAULT 0,
  `parent_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ums_depa
-- ----------------------------
INSERT INTO `ums_depa` VALUES (1, 'HENA', '振而达（天津）科技发展有限公司', '', '机构', NULL, '2020-09-17 14:51:03', '2020-09-24 10:37:16', 0, '0');
INSERT INTO `ums_depa` VALUES (2, '001', '一厂', NULL, '机构', NULL, '2020-09-17 14:52:03', '2020-09-23 13:14:37', 0, 'HENA');
INSERT INTO `ums_depa` VALUES (3, '002', '二厂', NULL, '机构', NULL, '2020-09-17 14:52:08', '2020-09-25 11:15:02', 0, 'HENA');
INSERT INTO `ums_depa` VALUES (4, 'T1321', '产品部', NULL, '部门', NULL, '2020-09-17 14:52:43', '2020-09-23 13:14:43', 0, '001');
INSERT INTO `ums_depa` VALUES (5, 'T1312', 'NPI部', NULL, '部门', NULL, '2020-09-17 14:52:43', '2020-09-23 13:14:44', 0, '001');
INSERT INTO `ums_depa` VALUES (6, 'T1310', '工程管理部', NULL, '部门', NULL, '2020-09-17 14:52:43', '2020-09-23 13:14:44', 0, '001');
INSERT INTO `ums_depa` VALUES (7, 'T3188', '测试工程部', NULL, '部门', NULL, '2020-09-17 14:52:43', '2020-09-23 13:14:44', 0, '001');
INSERT INTO `ums_depa` VALUES (8, 'T1423', '仓库部', NULL, '部门', NULL, '2020-09-17 14:52:43', '2020-09-23 13:14:44', 0, '001');
INSERT INTO `ums_depa` VALUES (9, 'T1210', '品质管理部', NULL, '部门', NULL, '2020-09-17 14:52:43', '2020-09-23 13:14:44', 0, '001');
INSERT INTO `ums_depa` VALUES (10, 'T3111', '生产部', NULL, '部门', NULL, '2020-09-17 14:52:43', '2020-09-23 13:14:44', 0, '001');
INSERT INTO `ums_depa` VALUES (11, 'T2411', '财务部', NULL, '部门', NULL, '2020-09-17 14:52:43', '2020-09-23 13:14:44', 0, '001');
INSERT INTO `ums_depa` VALUES (12, 'T2311', '人力资源部', NULL, '部门', NULL, '2020-09-17 14:52:43', '2020-09-23 13:14:44', 0, '001');
INSERT INTO `ums_depa` VALUES (13, 'T2211', 'IT部', NULL, '部门', NULL, '2020-09-17 14:53:28', '2020-09-23 13:14:44', 0, '001');
INSERT INTO `ums_depa` VALUES (14, 'T1111', '项目管理部', NULL, '部门', NULL, '2020-09-17 14:53:28', '2020-09-23 13:14:44', 0, '001');
INSERT INTO `ums_depa` VALUES (15, 'T1431', '采购部', NULL, '部门', NULL, '2020-09-17 14:53:29', '2020-09-23 13:14:44', 0, '001');
INSERT INTO `ums_depa` VALUES (16, 'T1411', '计划部', NULL, '部门', NULL, '2020-09-17 14:53:29', '2020-09-23 13:14:44', 0, '001');

-- ----------------------------
-- Table structure for ums_permission
-- ----------------------------
DROP TABLE IF EXISTS `ums_permission`;
CREATE TABLE `ums_permission`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `per_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限名',
  `per_url` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限路径',
  `icon` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'el-icon-s-custom' COMMENT '图标',
  `per_sort` int(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '排序',
  `per_type` int(1) NOT NULL COMMENT '权限类型 例如 0 菜单 1按钮',
  `parent_id` bigint(20) NOT NULL COMMENT '一级权限 默认 0 ',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态 0 禁用 1 启用',
  `remark` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ums_permission
-- ----------------------------
INSERT INTO `ums_permission` VALUES (1, '基础数据', '/basic', 'el-icon-s-custom', 0, 0, 0, 1, NULL, '2021-03-25 19:13:55', '2021-03-25 19:14:16');
INSERT INTO `ums_permission` VALUES (2, '用户管理', '/basic/user', 'el-icon-s-custom', 0, 0, 1, 1, NULL, '2021-03-25 19:13:55', '2021-03-25 19:15:08');
INSERT INTO `ums_permission` VALUES (3, '用户角色', '/basic/userRole', 'el-icon-s-custom', 0, 0, 1, 1, NULL, '2021-03-25 19:13:55', '2021-03-25 19:15:08');
INSERT INTO `ums_permission` VALUES (4, '用户列表-按钮', 'user:list', NULL, 0, 1, 2, 1, NULL, '2021-03-25 19:13:55', '2021-03-25 19:15:24');
INSERT INTO `ums_permission` VALUES (5, '用户新增-按钮', 'user:add', NULL, 0, 1, 2, 1, NULL, '2021-03-25 19:13:55', '2021-03-25 19:15:24');

-- ----------------------------
-- Table structure for ums_role
-- ----------------------------
DROP TABLE IF EXISTS `ums_role`;
CREATE TABLE `ums_role`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_name` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名',
  `sort` int(3) NULL DEFAULT 1 COMMENT '排序',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态 0 禁用 1 启用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ums_role
-- ----------------------------
INSERT INTO `ums_role` VALUES (1, 'SYSTEM', 1, 1, '2020-05-31 16:57:07', '2021-02-25 18:47:26');
INSERT INTO `ums_role` VALUES (2, 'admin', 1, 1, '2021-03-25 19:18:54', '2021-03-25 19:18:54');
INSERT INTO `ums_role` VALUES (3, 'test', 1, 1, '2021-03-25 19:18:58', '2021-03-25 19:18:58');

-- ----------------------------
-- Table structure for ums_role_per
-- ----------------------------
DROP TABLE IF EXISTS `ums_role_per`;
CREATE TABLE `ums_role_per`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `per_id` int(11) NOT NULL COMMENT '权限ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户权限关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ums_role_per
-- ----------------------------
INSERT INTO `ums_role_per` VALUES (1, 1, 1, '2021-03-25 19:19:49', '2021-03-25 19:20:02');
INSERT INTO `ums_role_per` VALUES (2, 2, 1, '2021-03-25 19:19:51', '2021-03-25 19:20:03');
INSERT INTO `ums_role_per` VALUES (3, 3, 1, '2021-03-25 19:19:53', '2021-03-25 19:20:05');
INSERT INTO `ums_role_per` VALUES (4, 4, 1, '2021-03-25 19:19:55', '2021-03-25 19:20:06');
INSERT INTO `ums_role_per` VALUES (5, 5, 1, '2021-03-25 19:19:57', '2021-03-25 19:20:08');
INSERT INTO `ums_role_per` VALUES (6, 1, 2, '2021-03-25 19:20:24', '2021-03-25 19:20:26');
INSERT INTO `ums_role_per` VALUES (7, 2, 2, '2021-03-25 19:20:47', '2021-03-25 19:20:47');
INSERT INTO `ums_role_per` VALUES (8, 3, 2, '2021-03-25 19:20:52', '2021-03-25 19:20:52');
INSERT INTO `ums_role_per` VALUES (9, 4, 2, '2021-03-25 19:21:03', '2021-03-25 19:38:26');
INSERT INTO `ums_role_per` VALUES (10, 1, 3, '2021-03-25 19:21:08', '2021-03-25 19:39:11');
INSERT INTO `ums_role_per` VALUES (11, 2, 3, '2021-03-25 19:37:35', '2021-03-25 19:39:14');
INSERT INTO `ums_role_per` VALUES (12, 3, 3, '2021-03-25 19:37:49', '2021-03-25 19:39:15');

-- ----------------------------
-- Table structure for ums_user
-- ----------------------------
DROP TABLE IF EXISTS `ums_user`;
CREATE TABLE `ums_user`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_account` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账号（编号）',
  `user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `user_pwd` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '密码',
  `is_exter` tinyint(1) NULL DEFAULT 0 COMMENT '是否外部员工',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_del` tinyint(1) NOT NULL DEFAULT 0 COMMENT '状态 0 禁用 1 启用',
  `depa_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'HENA' COMMENT '部门代码',
  `depa_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '振而达（天津）科技发展有限公司' COMMENT '部门名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ums_user
-- ----------------------------
INSERT INTO `ums_user` VALUES (1, '000001', 'test', '670b14728ad9902aecba32e22fa4f6bd', 0, '2020-09-17 16:56:30', '2021-03-25 19:16:48', 0, '001', '一厂');
INSERT INTO `ums_user` VALUES (2, '000002', 'admin', '670b14728ad9902aecba32e22fa4f6bd', 0, '2020-09-18 09:21:30', '2021-03-25 19:17:04', 0, '', '');
INSERT INTO `ums_user` VALUES (3, '000003', 'sys', '670b14728ad9902aecba32e22fa4f6bd', 0, '2020-09-18 10:26:11', '2021-03-25 19:33:27', 0, '', '');

-- ----------------------------
-- Table structure for ums_user_role
-- ----------------------------
DROP TABLE IF EXISTS `ums_user_role`;
CREATE TABLE `ums_user_role`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(11) NOT NULL COMMENT '用户ID',
  `role_id` bigint(11) NOT NULL COMMENT '角色ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ums_user_role
-- ----------------------------
INSERT INTO `ums_user_role` VALUES (1, 1, 3, '2021-03-25 19:19:23', '2021-03-25 19:19:23');
INSERT INTO `ums_user_role` VALUES (2, 2, 2, '2021-03-25 19:19:30', '2021-03-25 19:19:30');
INSERT INTO `ums_user_role` VALUES (3, 3, 1, '2021-03-25 19:19:33', '2021-03-25 19:19:33');

-- ----------------------------
-- Procedure structure for pro_test_3
-- ----------------------------
DROP PROCEDURE IF EXISTS `pro_test_3`;
delimiter ;;
CREATE PROCEDURE `pro_test_3`()
begin
--  方式 1
    DECLARE cnt INT DEFAULT 0;
    select count(*) into cnt from test_tbl;
    select cnt;
 
--  方式 2
    set @cnt = (select count(*) from test_tbl);
    select @cnt;

 

--  方式 3
    select count(*) into @cnt1 from test_tbl;
    select @cnt1;

 

--  多个列的情况下似乎只能用 into 方式

    select max(status), avg(status) into @max, @avg from test_tbl;

    select @max, @avg;

end
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
