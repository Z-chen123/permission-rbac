/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : localhost:3306
 Source Schema         : test1

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 26/02/2020 21:38:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_acl
-- ----------------------------
DROP TABLE IF EXISTS `sys_acl`;
CREATE TABLE `sys_acl`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '权限码',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '权限名称',
  `acl_module_id` int(11) NOT NULL DEFAULT 0 COMMENT '权限所在的权限模块id',
  `url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '请求的url，可以填写正则表达式',
  `type` int(11) NOT NULL DEFAULT 3 COMMENT '类型，1：菜单，2：按钮，3：其他',
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '状态，1：正常，2：冻结',
  `seq` int(11) NOT NULL DEFAULT 0 COMMENT '权限在当前模块下的顺序，由小到大',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  `operator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '操作人',
  `operate_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最近一次的更新时间',
  `operate_ip` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '最近一次的更新ip地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_acl
-- ----------------------------
INSERT INTO `sys_acl` VALUES (1, '20200224155245_82', '进入产品管理界面', 1, '/sys/product/product.page', 1, 1, 1, '', 'Admin', '2020-02-24 15:52:46', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl` VALUES (2, '20200224160441_74', '查询产品列表', 1, '/sys/product/page.json', 2, 1, 2, '', 'Admin', '2020-02-24 16:08:41', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl` VALUES (3, '20200224160919_3', '产品上架', 1, '/sys/product/online.json', 2, 1, 3, '', 'Admin', '2020-02-24 16:09:25', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl` VALUES (4, '20200224161014_87', '产品下架', 1, '/sys/product/offline', 2, 1, 4, '', 'Admin', '2020-02-24 16:10:14', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl` VALUES (5, '20200225141314_51', '进入订单页', 2, '/sys/order/order.page', 1, 1, 1, '', 'Admin', '2020-02-25 14:13:15', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl` VALUES (6, '20200225141416_90', '查询订单列表', 2, 'sys/order/list.json', 2, 1, 2, '', 'Admin', '2020-02-25 14:14:17', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl` VALUES (7, '20200225141801_8', '进入权限管理页', 7, '/sys/aclModule/acl.page', 1, 1, 1, '', 'Admin', '2020-02-25 14:18:02', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl` VALUES (8, '20200225141906_67', '进入角色管理页', 8, '/sys/role/role.page', 1, 1, 1, '', 'Admin', '2020-02-25 14:19:07', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl` VALUES (9, '20200225142008_28', '进入用户管理页', 9, '/sys/dept/dept.page', 1, 1, 1, '', 'Admin', '2020-02-25 14:20:09', '0:0:0:0:0:0:0:1');

-- ----------------------------
-- Table structure for sys_acl_module
-- ----------------------------
DROP TABLE IF EXISTS `sys_acl_module`;
CREATE TABLE `sys_acl_module`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限模块id',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '权限模块名称',
  `parent_id` int(11) NOT NULL DEFAULT 0 COMMENT '上级权限模块id',
  `level` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '权限模块层级',
  `seq` int(11) NOT NULL DEFAULT 0 COMMENT '权限模块在当前层级的顺序，由小到大',
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '状态，1：正常，0：冻结，2：删除',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  `operator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '操作人',
  `operate_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次操作时间',
  `operate_ip` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '最后一次操作ip',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_acl_module
-- ----------------------------
INSERT INTO `sys_acl_module` VALUES (1, '产品管理', 0, '0', 1, 1, '', 'Admin', '2020-02-24 12:23:16', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl_module` VALUES (2, '订单管理', 0, '0', 1, 1, '订单管理', 'Admin', '2020-02-24 12:23:36', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl_module` VALUES (3, '公告管理', 0, '0', 1, 1, '公告管理', 'Admin', '2020-02-24 12:23:52', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl_module` VALUES (4, '产品下架管理', 1, '0.1', 3, 1, '', 'Admin', '2020-02-24 12:38:24', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl_module` VALUES (5, '产品销售管理', 1, '0.1', 1, 1, '', 'Admin', '2020-02-24 12:24:38', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl_module` VALUES (6, '权限管理', 0, '0', 4, 1, '', 'Admin', '2020-02-25 14:15:03', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl_module` VALUES (7, '权限管理', 6, '0.6', 1, 1, '3123', 'Admin', '2020-02-26 16:18:10', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl_module` VALUES (8, '角色管理', 6, '0.6', 2, 1, '', 'Admin', '2020-02-25 14:16:12', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl_module` VALUES (9, '用户管理', 6, '0.6', 3, 1, '', 'Admin', '2020-02-25 14:16:30', '0:0:0:0:0:0:0:1');

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '部门名称',
  `parent_id` int(11) NOT NULL DEFAULT 0 COMMENT '上级部门id',
  `level` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '部门层级',
  `seq` int(11) NOT NULL DEFAULT 0 COMMENT '部门在当前层级的顺序，由小到大',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  `operator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '操作人',
  `operate_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次操作时间',
  `operate_ip` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '最后一次操作ip',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1, '技术部', 0, '0', 1, '技术部', 'System', '2020-02-21 14:37:02', '127.0.0.1');
INSERT INTO `sys_dept` VALUES (2, '人事部', 0, '0', 1, '人事部', 'System', '2020-02-21 14:40:22', '127.0.0.1');
INSERT INTO `sys_dept` VALUES (3, '财务部', 0, '0', 1, '财务部', 'System', '2020-02-21 14:44:04', '127.0.0.1');
INSERT INTO `sys_dept` VALUES (4, '后端开发', 1, '0.1', 1, '后端开发', 'System-update', '2020-02-22 16:36:33', '127.0.0.1');
INSERT INTO `sys_dept` VALUES (5, '前端开发', 1, '0.1', 2, '前端开发人员', 'System-update', '2020-02-22 16:36:27', '127.0.0.1');
INSERT INTO `sys_dept` VALUES (6, 'ui设计', 1, '0.1', 3, 'UI开发', 'System-update', '2020-02-22 16:36:38', '127.0.0.1');
INSERT INTO `sys_dept` VALUES (7, '运营部', 0, '0', 5, '', 'Admin', '2020-02-26 16:17:01', '0:0:0:0:0:0:0:1');

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` int(11) NOT NULL DEFAULT 0 COMMENT '权限更新的类型，1:部门，2：用户，3：权限模块，4：权限，5：角色，6：角色用户关系，7：角色权限关系',
  `target_id` int(11) NOT NULL COMMENT '基于type后指定对象的id，例如:用户，角色，权限等表的主键',
  `old_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '旧值',
  `new_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '新值',
  `operator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '操作人',
  `operate_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最近一次更新操作时间',
  `operate_ip` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '最近一次更新操作ip地址',
  `status` int(11) NOT NULL DEFAULT 0 COMMENT '当前状态是否复原过，0：没有，1：复原过',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES (1, 1, 7, '{\"id\":7,\"name\":\"运营部\",\"parentId\":0,\"level\":\"0\",\"seq\":4,\"operator\":\"Admin\",\"operateTime\":1582705015248,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', '', 'Admin', '2020-02-26 16:16:56', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (2, 1, 7, '{\"id\":7,\"name\":\"运营部\",\"parentId\":0,\"level\":\"0\",\"seq\":5,\"operator\":\"Admin\",\"operateTime\":1582705021432,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', '{\"id\":7,\"name\":\"运营部\",\"parentId\":0,\"level\":\"0\",\"seq\":4,\"operator\":\"Admin\",\"operateTime\":1582705015000,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2020-02-26 16:17:02', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (3, 2, 2, '{\"id\":2,\"username\":\"chenguangxing\",\"telephone\":\"13965124794\",\"mail\":\"chenguangxing@qq.com\",\"deptId\":1,\"status\":1,\"remark\":\"所有者哈哈1123424\",\"operator\":\"Admin\",\"operateTime\":1582705033135,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', '{\"id\":2,\"username\":\"chenguangxing\",\"telephone\":\"13965124794\",\"mail\":\"chenguangxing@qq.com\",\"password\":\"E10ADC3949BA59ABBE56E057F20F883E\",\"deptId\":1,\"status\":1,\"remark\":\"所有者哈哈11\",\"operator\":\"chenguangxing\",\"operateTime\":1582435005000,\"operateIp\":\"127.0.0.1\"}', 'Admin', '2020-02-26 16:17:13', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (4, 5, 5, '{\"id\":5,\"name\":\"仓库管理员\",\"type\":1,\"status\":1,\"remark\":\"112\",\"operator\":\"Admin\",\"operateTime\":1582705071744,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', '', 'Admin', '2020-02-26 16:17:52', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (5, 5, 5, '{\"id\":5,\"name\":\"仓库管理员\",\"type\":1,\"status\":1,\"remark\":\"112323\",\"operator\":\"Admin\",\"operateTime\":1582705079486,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', '{\"id\":5,\"name\":\"仓库管理员\",\"type\":1,\"status\":1,\"remark\":\"112\",\"operator\":\"Admin\",\"operateTime\":1582705072000,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2020-02-26 16:18:00', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (6, 3, 7, '{\"id\":7,\"name\":\"权限管理\",\"parentId\":6,\"level\":\"0.6\",\"seq\":1,\"status\":1,\"remark\":\"3123\",\"operator\":\"Admin\",\"operateTime\":1582705089648,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', '{\"id\":7,\"name\":\"权限管理\",\"parentId\":6,\"level\":\"0.6\",\"seq\":1,\"status\":1,\"operator\":\"Admin\",\"operateTime\":1582611351000,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2020-02-26 16:18:10', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (7, 2, 5, '{\"id\":5,\"username\":\"chenguangcan1\",\"telephone\":\"15632421233\",\"mail\":\"647622965@qq.com\",\"deptId\":1,\"status\":1,\"remark\":\"测试2\",\"operator\":\"Admin\",\"operateTime\":1582715865543,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', '{\"id\":5,\"username\":\"chenguangcan\",\"telephone\":\"15632421233\",\"mail\":\"647622965@qq.com\",\"password\":\"71299D589895FF6B064CEAE7C7F202FD\",\"deptId\":1,\"status\":1,\"remark\":\"测试2\",\"operator\":\"Admin\",\"operateTime\":1582448803000,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2020-02-26 19:17:46', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (8, 2, 5, '{\"id\":5,\"username\":\"chenguangcan1\",\"telephone\":\"15632421233\",\"mail\":\"647622965@qq.com\",\"deptId\":1,\"status\":1,\"remark\":\"测试2\",\"operator\":\"Admin\",\"operateTime\":1582715901495,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', '{\"id\":5,\"username\":\"chenguangcan1\",\"telephone\":\"15632421233\",\"mail\":\"647622965@qq.com\",\"password\":\"71299D589895FF6B064CEAE7C7F202FD\",\"deptId\":1,\"status\":1,\"remark\":\"测试2\",\"operator\":\"Admin\",\"operateTime\":1582715866000,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2020-02-26 19:18:22', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (9, 2, 5, '{\"id\":5,\"username\":\"chenguangcan1\",\"telephone\":\"15632421233\",\"mail\":\"647622965@qq.com\",\"deptId\":1,\"status\":1,\"remark\":\"测试2\",\"operator\":\"Admin\",\"operateTime\":1582715911662,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', '{\"id\":5,\"username\":\"chenguangcan1\",\"telephone\":\"15632421233\",\"mail\":\"647622965@qq.com\",\"password\":\"71299D589895FF6B064CEAE7C7F202FD\",\"deptId\":1,\"status\":1,\"remark\":\"测试2\",\"operator\":\"Admin\",\"operateTime\":1582715901000,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2020-02-26 19:18:32', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (10, 2, 5, '{\"id\":5,\"username\":\"chenguangcan1\",\"telephone\":\"15632421233\",\"mail\":\"647622965@qq.com\",\"deptId\":1,\"status\":1,\"remark\":\"测试2\",\"operator\":\"Admin\",\"operateTime\":1582716050493,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', '{\"id\":5,\"username\":\"chenguangcan1\",\"telephone\":\"15632421233\",\"mail\":\"647622965@qq.com\",\"password\":\"71299D589895FF6B064CEAE7C7F202FD\",\"deptId\":1,\"status\":1,\"remark\":\"测试2\",\"operator\":\"Admin\",\"operateTime\":1582715912000,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2020-02-26 19:20:51', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (11, 2, 5, '{\"id\":5,\"username\":\"chenguangcan1\",\"telephone\":\"15632421233\",\"mail\":\"647622965@qq.com\",\"deptId\":1,\"status\":1,\"remark\":\"测试2\",\"operator\":\"Admin\",\"operateTime\":1582716196760,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', '{\"id\":5,\"username\":\"chenguangcan1\",\"telephone\":\"15632421233\",\"mail\":\"647622965@qq.com\",\"password\":\"71299D589895FF6B064CEAE7C7F202FD\",\"deptId\":1,\"status\":1,\"remark\":\"测试2\",\"operator\":\"Admin\",\"operateTime\":1582716050000,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2020-02-26 19:23:17', '0:0:0:0:0:0:0:1', 1);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '角色名称',
  `type` int(11) NOT NULL DEFAULT 1 COMMENT '角色类型，1：管理员角色，2：其他',
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '状态，1：可用，2：冻结',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  `operator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '操作人',
  `operate_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最近一次更新操作时间',
  `operate_ip` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '最近一次更新操作ip地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '产品管理员', 1, 1, '111', 'Admin', '2020-02-24 17:48:14', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_role` VALUES (2, '订单管理员', 1, 1, '', 'Admin', '2020-02-24 17:44:00', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_role` VALUES (3, '公告管理员', 1, 1, '', 'Admin', '2020-02-24 17:44:11', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_role` VALUES (4, '权限管理员', 1, 1, '', 'Admin', '2020-02-25 14:21:27', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_role` VALUES (5, '仓库管理员', 1, 1, '112323', 'Admin', '2020-02-26 16:17:59', '0:0:0:0:0:0:0:1');

-- ----------------------------
-- Table structure for sys_role_acl
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_acl`;
CREATE TABLE `sys_role_acl`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `acl_id` int(11) NOT NULL COMMENT '权限id',
  `operator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '操作人',
  `operate_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最近一次更新操作时间',
  `operate_ip` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '最近一次更新操作的ip地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_acl
-- ----------------------------
INSERT INTO `sys_role_acl` VALUES (10, 4, 2, 'Admin', '2020-02-26 12:54:32', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_role_acl` VALUES (11, 4, 6, 'Admin', '2020-02-26 12:54:32', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_role_acl` VALUES (12, 4, 7, 'Admin', '2020-02-26 12:54:32', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_role_acl` VALUES (13, 4, 8, 'Admin', '2020-02-26 12:54:32', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_role_acl` VALUES (14, 4, 9, 'Admin', '2020-02-26 12:54:32', '0:0:0:0:0:0:0:1');

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `operator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '操作人',
  `operate_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最近一次更新操作时间',
  `operate_ip` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '最近一次更新操作的ip地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 59 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------
INSERT INTO `sys_role_user` VALUES (55, 4, 2, 'Admin', '2020-02-26 12:36:31', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_role_user` VALUES (56, 4, 1, 'Admin', '2020-02-26 12:36:31', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_role_user` VALUES (57, 4, 3, 'Admin', '2020-02-26 12:36:31', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_role_user` VALUES (58, 4, 5, 'Admin', '2020-02-26 12:36:31', '0:0:0:0:0:0:0:1');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户名称',
  `telephone` varchar(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户手机号',
  `mail` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户邮箱',
  `password` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '加密后密码',
  `dept_id` int(11) NOT NULL DEFAULT 0 COMMENT '用户所在部门',
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '状态， 1：正常，0:冻结，2：删除',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  `operator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '操作人',
  `operate_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  `operate_ip` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '最后一次更新的ip地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'Admin', '18988886666', 'admin@qq.com', 'E10ADC3949BA59ABBE56E057F20F883E', 1, 1, '系统管理员', 'System', '2020-02-22 17:48:42', '127.0.0.1');
INSERT INTO `sys_user` VALUES (2, 'chenguangxing', '13965124794', 'chenguangxing@qq.com', 'E10ADC3949BA59ABBE56E057F20F883E', 1, 1, '所有者哈哈1123424', 'Admin', '2020-02-26 16:17:13', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_user` VALUES (3, 'ceshi', '13242421312', '2605410440@qq.com', 'E25BDFCA67BB81D6E0A6C72F1F13B9D1', 1, 1, '测试用户', 'Admin', '2020-02-23 16:38:41', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_user` VALUES (5, 'chenguangcan1', '15632421233', '647622965@qq.com', '71299D589895FF6B064CEAE7C7F202FD', 1, 1, '测试2', 'Admin', '2020-02-26 19:23:17', '0:0:0:0:0:0:0:1');

SET FOREIGN_KEY_CHECKS = 1;
