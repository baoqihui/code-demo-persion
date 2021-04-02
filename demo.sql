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

 Date: 02/04/2021 18:31:35
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
  `type` int(5) NULL DEFAULT 2 COMMENT '类型(1,机构 2，部门)',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `parent_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ums_depa
-- ----------------------------
INSERT INTO `ums_depa` VALUES (1, 'HENA', '振而达（天津）科技发展有限公司', NULL, 1, NULL, '2020-09-17 14:51:03', '2021-04-01 18:23:26', '0');
INSERT INTO `ums_depa` VALUES (2, '001', '一厂', NULL, 1, NULL, '2020-09-17 14:52:03', '2021-04-01 18:23:26', 'HENA');
INSERT INTO `ums_depa` VALUES (3, '002', '二厂', NULL, 1, NULL, '2020-09-17 14:52:08', '2021-04-01 18:23:26', 'HENA');
INSERT INTO `ums_depa` VALUES (4, 'T1321', '产品部a', NULL, 2, NULL, '2020-09-17 14:52:43', '2021-04-01 18:46:16', '001');
INSERT INTO `ums_depa` VALUES (5, 'T1312', 'NPI部', NULL, 2, NULL, '2020-09-17 14:52:43', '2021-04-01 18:23:29', '001');
INSERT INTO `ums_depa` VALUES (6, 'T1310', '工程管理部', NULL, 2, NULL, '2020-09-17 14:52:43', '2021-04-01 18:23:29', '001');
INSERT INTO `ums_depa` VALUES (7, 'T3188', '测试工程部', NULL, 2, NULL, '2020-09-17 14:52:43', '2021-04-01 18:23:29', '001');
INSERT INTO `ums_depa` VALUES (8, 'T1423', '仓库部', NULL, 2, NULL, '2020-09-17 14:52:43', '2021-04-01 18:23:29', '001');
INSERT INTO `ums_depa` VALUES (9, 'T1210', '品质管理部', NULL, 2, NULL, '2020-09-17 14:52:43', '2021-04-01 18:23:29', '001');
INSERT INTO `ums_depa` VALUES (10, 'T3111', '生产部', NULL, 2, NULL, '2020-09-17 14:52:43', '2021-04-01 18:23:29', '001');
INSERT INTO `ums_depa` VALUES (11, 'T2411', '财务部', NULL, 2, NULL, '2020-09-17 14:52:43', '2021-04-01 18:23:29', '001');
INSERT INTO `ums_depa` VALUES (12, 'T2311', '人力资源部', NULL, 2, NULL, '2020-09-17 14:52:43', '2021-04-01 18:23:29', '001');
INSERT INTO `ums_depa` VALUES (13, 'T2211', 'IT部', NULL, 2, NULL, '2020-09-17 14:53:28', '2021-04-01 18:23:29', '001');
INSERT INTO `ums_depa` VALUES (14, 'T1111', '项目管理部', NULL, 2, NULL, '2020-09-17 14:53:28', '2021-04-01 18:23:29', '001');
INSERT INTO `ums_depa` VALUES (15, 'T1431', '采购部', NULL, 2, NULL, '2020-09-17 14:53:29', '2021-04-01 18:23:29', '001');
INSERT INTO `ums_depa` VALUES (16, 'T1411', '计划部', NULL, 2, NULL, '2020-09-17 14:53:29', '2021-04-01 18:56:13', '001');
INSERT INTO `ums_depa` VALUES (17, 'T201', '运维', NULL, 2, NULL, '2021-04-01 18:46:40', '2021-04-02 18:28:53', '002');

-- ----------------------------
-- Table structure for ums_permission
-- ----------------------------
DROP TABLE IF EXISTS `ums_permission`;
CREATE TABLE `ums_permission`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `per_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限名',
  `path` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限路径',
  `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组件路径',
  `icon` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'el-icon-s-custom' COMMENT '图标',
  `per_sort` int(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '排序',
  `per_type` int(1) NOT NULL COMMENT '权限类型 例如 0 菜单 1按钮',
  `parent_id` bigint(20) NOT NULL COMMENT '上级权限 默认 0 ',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态 0 禁用 1 启用',
  `remark` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ums_permission
-- ----------------------------
INSERT INTO `ums_permission` VALUES (1, '系统设置', 'system', 'Layout', 'el-icon-s-custom', 1, 0, 0, 1, NULL, '2021-04-01 09:12:25', '2021-04-01 19:26:29');
INSERT INTO `ums_permission` VALUES (2, '菜单管理', 'menu', 'system/menu', 'el-icon-s-custom', 0, 0, 1, 1, NULL, '2021-04-01 09:13:54', '2021-04-01 10:00:23');
INSERT INTO `ums_permission` VALUES (3, '角色管理', 'role', 'system/role', 'el-icon-s-custom', 0, 0, 1, 1, NULL, '2021-04-01 09:14:27', '2021-04-01 10:00:23');
INSERT INTO `ums_permission` VALUES (4, '查询', 'system:menu:list', NULL, 'el-icon-s-custom', 1, 1, 2, 1, NULL, '2021-04-01 09:30:22', '2021-04-01 09:30:22');
INSERT INTO `ums_permission` VALUES (5, '用户管理', 'user', 'system/user', 'el-icon-s-custom', 0, 0, 1, 1, NULL, '2021-04-01 09:36:59', '2021-04-01 09:36:59');
INSERT INTO `ums_permission` VALUES (6, '查询', 'system:user:list', NULL, 'el-icon-s-custom', 1, 1, 5, 1, NULL, '2021-04-01 10:31:33', '2021-04-02 18:26:25');
INSERT INTO `ums_permission` VALUES (7, '添加', 'system:user:add', NULL, 'el-icon-s-custom', 2, 1, 5, 1, NULL, '2021-04-01 10:33:09', '2021-04-02 18:26:29');
INSERT INTO `ums_permission` VALUES (8, '删除', 'system:user:del', NULL, 'el-icon-s-custom', 3, 1, 5, 1, NULL, '2021-04-01 10:36:00', '2021-04-02 18:26:34');
INSERT INTO `ums_permission` VALUES (12, '部门管理', 'dept', 'system/dept', 'el-icon-s-custom', 3, 0, 1, 1, NULL, '2021-04-01 16:50:03', '2021-04-01 16:50:03');
INSERT INTO `ums_permission` VALUES (20, '修改', 'system:user:amend', NULL, 'el-icon-s-custom', 1, 1, 5, 1, NULL, '2021-04-02 18:11:19', '2021-04-02 18:26:38');
INSERT INTO `ums_permission` VALUES (21, '查询', 'system:role:list', NULL, 'el-icon-s-custom', 0, 1, 3, 1, NULL, '2021-04-02 18:25:44', '2021-04-02 18:25:44');

-- ----------------------------
-- Table structure for ums_role
-- ----------------------------
DROP TABLE IF EXISTS `ums_role`;
CREATE TABLE `ums_role`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_name` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名',
  `sort` int(3) NULL DEFAULT 1 COMMENT '排序',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态 0 禁用 1 启用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ums_role
-- ----------------------------
INSERT INTO `ums_role` VALUES (1, 'SYSTEM', 1, 1, '2020-05-31 16:57:07', '2021-04-02 18:25:59');
INSERT INTO `ums_role` VALUES (2, 'USER', 1, 1, '2020-09-14 11:57:47', '2021-04-02 18:30:25');

-- ----------------------------
-- Table structure for ums_role_per
-- ----------------------------
DROP TABLE IF EXISTS `ums_role_per`;
CREATE TABLE `ums_role_per`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `per_id` int(11) NOT NULL COMMENT '权限ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 196 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户权限关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ums_role_per
-- ----------------------------
INSERT INTO `ums_role_per` VALUES (175, 1, 1, '2021-04-02 18:25:59', '2021-04-02 18:25:59');
INSERT INTO `ums_role_per` VALUES (176, 3, 1, '2021-04-02 18:25:59', '2021-04-02 18:25:59');
INSERT INTO `ums_role_per` VALUES (177, 2, 1, '2021-04-02 18:25:59', '2021-04-02 18:25:59');
INSERT INTO `ums_role_per` VALUES (178, 4, 1, '2021-04-02 18:25:59', '2021-04-02 18:25:59');
INSERT INTO `ums_role_per` VALUES (179, 21, 1, '2021-04-02 18:25:59', '2021-04-02 18:25:59');
INSERT INTO `ums_role_per` VALUES (180, 5, 1, '2021-04-02 18:25:59', '2021-04-02 18:25:59');
INSERT INTO `ums_role_per` VALUES (181, 6, 1, '2021-04-02 18:25:59', '2021-04-02 18:25:59');
INSERT INTO `ums_role_per` VALUES (182, 7, 1, '2021-04-02 18:25:59', '2021-04-02 18:25:59');
INSERT INTO `ums_role_per` VALUES (183, 8, 1, '2021-04-02 18:25:59', '2021-04-02 18:25:59');
INSERT INTO `ums_role_per` VALUES (184, 20, 1, '2021-04-02 18:25:59', '2021-04-02 18:25:59');
INSERT INTO `ums_role_per` VALUES (185, 12, 1, '2021-04-02 18:25:59', '2021-04-02 18:25:59');
INSERT INTO `ums_role_per` VALUES (186, 1, 2, '2021-04-02 18:30:25', '2021-04-02 18:30:25');
INSERT INTO `ums_role_per` VALUES (187, 5, 2, '2021-04-02 18:30:25', '2021-04-02 18:30:25');
INSERT INTO `ums_role_per` VALUES (188, 2, 2, '2021-04-02 18:30:25', '2021-04-02 18:30:25');
INSERT INTO `ums_role_per` VALUES (189, 4, 2, '2021-04-02 18:30:25', '2021-04-02 18:30:25');
INSERT INTO `ums_role_per` VALUES (190, 3, 2, '2021-04-02 18:30:25', '2021-04-02 18:30:25');
INSERT INTO `ums_role_per` VALUES (191, 21, 2, '2021-04-02 18:30:25', '2021-04-02 18:30:25');
INSERT INTO `ums_role_per` VALUES (192, 6, 2, '2021-04-02 18:30:25', '2021-04-02 18:30:25');
INSERT INTO `ums_role_per` VALUES (193, 7, 2, '2021-04-02 18:30:25', '2021-04-02 18:30:25');
INSERT INTO `ums_role_per` VALUES (194, 20, 2, '2021-04-02 18:30:25', '2021-04-02 18:30:25');
INSERT INTO `ums_role_per` VALUES (195, 12, 2, '2021-04-02 18:30:25', '2021-04-02 18:30:25');

-- ----------------------------
-- Table structure for ums_user
-- ----------------------------
DROP TABLE IF EXISTS `ums_user`;
CREATE TABLE `ums_user`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_account` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账号（编号）',
  `user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `user_pwd` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '密码',
  `is_exter` tinyint(1) NULL DEFAULT 0 COMMENT '是否外部员工',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `state` tinyint(1) NOT NULL DEFAULT 0 COMMENT '状态 0 禁用 1 启用',
  `depa_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'HENA' COMMENT '部门代码',
  `depa_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '振而达（天津）科技发展有限公司' COMMENT '部门名称',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `un`(`user_account`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ums_user
-- ----------------------------
INSERT INTO `ums_user` VALUES (1, '001', '系统管理员', 'dc5c7986daef50c1e02ab09b442ee34f', 0, '2021-04-02 17:53:50', '2021-04-02 18:01:03', 1, 'HENA', '振而达（天津）科技发展有限公司');
INSERT INTO `ums_user` VALUES (2, '002', '用户', '93dd4de5cddba2c733c65f233097f05a', 0, '2021-04-02 18:01:42', '2021-04-02 18:01:42', 1, 'HENA', '振而达（天津）科技发展有限公司');

-- ----------------------------
-- Table structure for ums_user_role
-- ----------------------------
DROP TABLE IF EXISTS `ums_user_role`;
CREATE TABLE `ums_user_role`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ums_user_role
-- ----------------------------
INSERT INTO `ums_user_role` VALUES (2, 1, 1, '2021-04-02 18:01:03', '2021-04-02 18:01:03');
INSERT INTO `ums_user_role` VALUES (3, 2, 2, '2021-04-02 18:01:42', '2021-04-02 18:01:42');

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
