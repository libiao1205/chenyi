/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50610
 Source Host           : 192.168.1.7:3306
 Source Schema         : xiaonuo-cloud-pub

 Target Server Type    : MySQL
 Target Server Version : 50610
 File Encoding         : 65001

 Date: 21/06/2021 11:37:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cloud_sample
-- ----------------------------
DROP TABLE IF EXISTS `cloud_sample`;
CREATE TABLE `cloud_sample`  (
  `id` bigint(20) NOT NULL COMMENT '主键id',
  `sample_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '样本名称',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '微服务样本' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of cloud_sample
-- ----------------------------
INSERT INTO `cloud_sample` VALUES (1265476890672672808, '微服务样本A-测试1', '2020-12-28 23:47:41', 1265476890672672808, '2020-12-28 23:47:45', 1265476890672672808);
INSERT INTO `cloud_sample` VALUES (1275735541155614721, '微服务样本B-测试2', '2020-12-28 23:48:12', 1265476890672672808, '2020-12-28 23:48:31', 1265476890672672808);
INSERT INTO `cloud_sample` VALUES (1280709549107552257, '微服务样本C-测试3', '2020-12-28 23:48:57', 1265476890672672808, '2020-12-28 23:49:02', 1265476890672672808);

-- ----------------------------
-- Table structure for g_data_collect
-- ----------------------------
DROP TABLE IF EXISTS `g_data_collect`;
CREATE TABLE `g_data_collect`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '唯一标识',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `status` tinyint(4) NOT NULL COMMENT '状态（0正常 1停用 2删除）',
  `sort` int(11) NOT NULL COMMENT '排序',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(50) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(50) NULL DEFAULT NULL COMMENT '更新人',
  INDEX `code`(`code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of g_data_collect
-- ----------------------------
INSERT INTO `g_data_collect` VALUES (1382621475319173122, 'dataCollect_01', 'zhongwenshumu数据集', 0, 10, NULL, '2021-04-15 17:07:02', 1377879897444212737, '2021-05-29 14:20:55', 1377879897444212737);
INSERT INTO `g_data_collect` VALUES (1383016715574116353, 'dataCollect_02', '数据集02', 2, 100, NULL, '2021-04-16 19:17:35', 1377879897444212737, '2021-04-25 09:32:06', 1377879897444212737);
INSERT INTO `g_data_collect` VALUES (1387322268047699969, 'dataCollect_ChenYiWenXianHuiZong', '陈毅文献汇总数据集', 0, 100, NULL, '2021-04-28 16:26:19', 1377879897444212737, '2021-05-11 11:57:03', 1377879897444212737);
INSERT INTO `g_data_collect` VALUES (1391599362751614978, 'dataCollect_iso', 'iso数据集', 0, 100, NULL, '2021-05-10 11:41:57', 1377879897444212737, '2021-05-29 13:44:19', 1377879897444212737);
INSERT INTO `g_data_collect` VALUES (1392006542822797313, 'dataSource_AS', 'AS数据集', 0, 100, NULL, '2021-05-11 14:39:57', 1377879897444212737, '2021-06-04 18:36:55', 1377879897444212737);
INSERT INTO `g_data_collect` VALUES (1395199430272507906, 'ES_datacollect', 'ES数据集', 0, 100, NULL, '2021-05-20 10:07:20', 1377879897444212737, '2021-06-08 18:49:48', 1377879897444212737);

-- ----------------------------
-- Table structure for g_data_gather
-- ----------------------------
DROP TABLE IF EXISTS `g_data_gather`;
CREATE TABLE `g_data_gather`  (
  `id` bigint(20) NOT NULL,
  `file_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '采集文件名',
  `result_file_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '采集好的文件保存路径',
  `source_file_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '需要采集的文件',
  `status` tinyint(4) NOT NULL COMMENT '状态（0正常 1停用 2删除)',
  `sort` int(11) NOT NULL COMMENT '排序',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(50) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(50) NULL DEFAULT NULL COMMENT '更新人',
  `gather_status` tinyint(4) NULL DEFAULT NULL COMMENT '采集状态（0执行中 1执行成功 2执行失败）',
  `gather_type` tinyint(4) NULL DEFAULT NULL COMMENT '采集方式（0：手动，1：自动）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of g_data_gather
-- ----------------------------
INSERT INTO `g_data_gather` VALUES (1400750429838520322, '复旦AS数据.xlsx', 'D:\\dataSource\\1622859047263', 'http://192.168.1.27:38080/rest/chenyi/cyf/df91e514-c7fd-47a1-808b-261c5ad9c9fd', 0, 100, NULL, '2021-06-04 17:45:02', 1377879897444212737, '2021-06-07 09:28:05', -1, 2, 1);

-- ----------------------------
-- Table structure for g_data_source
-- ----------------------------
DROP TABLE IF EXISTS `g_data_source`;
CREATE TABLE `g_data_source`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数据源名称',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '唯一code',
  `data_collect_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据集code',
  `type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数据源类型',
  `connect` tinyint(4) NOT NULL COMMENT '连接成功（0：未成功，1：成功）',
  `status` tinyint(4) NOT NULL COMMENT '状态（字典 0正常 1停用 2删除）',
  `sort` int(11) NOT NULL COMMENT '排序',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(50) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(50) NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `code`(`code`) USING BTREE,
  INDEX `fk_data_source_collect_code`(`data_collect_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of g_data_source
-- ----------------------------
INSERT INTO `g_data_source` VALUES (1386584925389058049, '中文数目 ', 'dataSource_3', 'dataCollect_01', 'MARC_DATASOURCE', 0, 0, 100, NULL, '2021-04-26 15:36:22', 1377879897444212737, '2021-05-31 10:39:30', 1377879897444212737);
INSERT INTO `g_data_source` VALUES (1386585403103506433, '陈毅文献汇总', 'dataSource_4', 'dataCollect_ChenYiWenXianHuiZong', 'EXCEL_DATASOURCE', 0, 0, 100, NULL, '2021-04-26 15:38:16', 1377879897444212737, '2021-05-12 13:25:00', 1377879897444212737);
INSERT INTO `g_data_source` VALUES (1390500911645249537, 'as数据源1', 'dataSource_as3', 'dataSource_AS', 'AS_DATASOURCE', 0, 0, 100, NULL, '2021-05-07 10:57:06', 1377879897444212737, '2021-05-12 12:50:33', 1377879897444212737);
INSERT INTO `g_data_source` VALUES (1391600361604141057, 'isoMarc数据源', 'dataSource_iso', 'dataCollect_iso', 'MARC_DATASOURCE', 0, 2, 100, NULL, '2021-05-10 11:45:56', 1377879897444212737, '2021-05-31 11:07:03', 1377879897444212737);
INSERT INTO `g_data_source` VALUES (1392341510799306753, '111', 'dataSource_311', 'dataCollect_ChenYiWenXianHuiZong', 'EXCEL_DATASOURCE', 0, 2, 100, NULL, '2021-05-12 12:50:59', 1377879897444212737, '2021-05-12 12:51:10', 1377879897444212737);
INSERT INTO `g_data_source` VALUES (1392406147645419522, '内网测试AS', 'testr_as', 'dataSource_AS', 'AS_DATASOURCE', 0, 0, 100, NULL, '2021-05-12 17:07:50', 1377879897444212737, '2021-05-12 18:59:55', 1377879897444212737);
INSERT INTO `g_data_source` VALUES (1393060680797061121, 'fedora数据源', 'dataSource_fedora', NULL, 'FEDORA_DATASOURCE', 0, 2, 100, NULL, '2021-05-14 12:28:43', 1377879897444212737, '2021-05-14 13:54:35', 1377879897444212737);
INSERT INTO `g_data_source` VALUES (1395198779090034689, 'ES配置文件', 'dataSource_ES', 'ES_datacollect', 'ES_DATASOURCE', 0, 0, 100, NULL, '2021-05-20 10:04:45', 1377879897444212737, '2021-05-20 10:10:04', 1377879897444212737);
INSERT INTO `g_data_source` VALUES (1398104100926205954, 'qt2_929Marc数据', 'dataSource_qt2_929', NULL, 'MARC_DATASOURCE', 0, 0, 100, NULL, '2021-05-28 10:29:28', 1377879897444212737, '2021-05-31 10:11:12', 1377879897444212737);
INSERT INTO `g_data_source` VALUES (1398125462768136193, 'xgab9d_2616', 'dataSource_xgab9d_2616', NULL, 'MARC_DATASOURCE', 0, 0, 100, NULL, '2021-05-28 11:54:21', 1377879897444212737, '2021-05-29 14:25:07', 1377879897444212737);
INSERT INTO `g_data_source` VALUES (1399183488757350402, 'xl_2021Marc', 'dataSource_xl_2021', NULL, 'MARC_DATASOURCE', 0, 2, 100, NULL, '2021-05-31 09:58:34', 1377879897444212737, '2021-05-31 10:40:27', 1377879897444212737);
INSERT INTO `g_data_source` VALUES (1399187025851662338, 'cehsi', 'dataSource_cehsi', NULL, 'MARC_DATASOURCE', 0, 2, 100, NULL, '2021-05-31 10:12:37', 1377879897444212737, '2021-05-31 10:14:33', 1377879897444212737);
INSERT INTO `g_data_source` VALUES (1399187936774799362, 'cehsi', 'dataSource_cehsi', NULL, 'MARC_DATASOURCE', 0, 2, 100, NULL, '2021-05-31 10:16:14', 1377879897444212737, '2021-05-31 10:18:56', 1377879897444212737);
INSERT INTO `g_data_source` VALUES (1399189036156403713, 'ceshi', 'dataSource_cehsi', NULL, 'MARC_DATASOURCE', 0, 2, 100, NULL, '2021-05-31 10:20:37', 1377879897444212737, '2021-05-31 10:20:49', 1377879897444212737);
INSERT INTO `g_data_source` VALUES (1399189200447291394, 'cehsi', 'dataSource_cehsi', NULL, 'MARC_DATASOURCE', 0, 2, 100, NULL, '2021-05-31 10:21:16', 1377879897444212737, '2021-05-31 10:21:47', 1377879897444212737);
INSERT INTO `g_data_source` VALUES (1399189618980110338, 'ceshi', 'dataSource_ceshi', NULL, 'MARC_DATASOURCE', 0, 2, 100, NULL, '2021-05-31 10:22:56', 1377879897444212737, '2021-05-31 10:22:59', 1377879897444212737);
INSERT INTO `g_data_source` VALUES (1399189729852342273, 'ceshi', 'dataSource_ceshi', NULL, 'MARC_DATASOURCE', 0, 2, 100, NULL, '2021-05-31 10:23:22', 1377879897444212737, '2021-05-31 10:40:56', 1377879897444212737);
INSERT INTO `g_data_source` VALUES (1399198908499415042, 'xl_2021', 'dataSource_xl_2021', NULL, 'MARC_DATASOURCE', 0, 2, 100, NULL, '2021-05-31 10:59:50', 1377879897444212737, '2021-05-31 11:07:17', 1377879897444212737);
INSERT INTO `g_data_source` VALUES (1399201756058537986, 'xl_2021', 'dataSource_xl_2021', 'dataCollect_iso', 'MARC_DATASOURCE', 0, 0, 100, NULL, '2021-05-31 11:11:09', 1377879897444212737, NULL, NULL);

-- ----------------------------
-- Table structure for g_data_source_column
-- ----------------------------
DROP TABLE IF EXISTS `g_data_source_column`;
CREATE TABLE `g_data_source_column`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数据源列名称',
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数据源列值',
  `data_source_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数据源code',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_data_source_column`(`data_source_code`) USING BTREE,
  CONSTRAINT `fk_data_source_column` FOREIGN KEY (`data_source_code`) REFERENCES `g_data_source` (`code`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of g_data_source_column
-- ----------------------------
INSERT INTO `g_data_source_column` VALUES (1392341399994183682, 'ip', '10.55.101.128', 'dataSource_as3');
INSERT INTO `g_data_source_column` VALUES (1392341400040321025, 'port', '8089', 'dataSource_as3');
INSERT INTO `g_data_source_column` VALUES (1392341400048709634, 'username', 'simonhjsong', 'dataSource_as3');
INSERT INTO `g_data_source_column` VALUES (1392341400065486849, 'password', 'song800728$', 'dataSource_as3');
INSERT INTO `g_data_source_column` VALUES (1392350068475457537, 'excelFile', 'http://192.168.1.27:38080/rest/chenyi/cyf/b80c7395-f8bc-45f3-95ee-590ed4dc23b7', 'dataSource_4');
INSERT INTO `g_data_source_column` VALUES (1392434356478926850, 'ip', '192.168.1.7', 'testr_as');
INSERT INTO `g_data_source_column` VALUES (1392434356525064194, 'port', '8089', 'testr_as');
INSERT INTO `g_data_source_column` VALUES (1392434356525064195, 'username', 'admin', 'testr_as');
INSERT INTO `g_data_source_column` VALUES (1392434356533452801, 'password', 'admin', 'testr_as');
INSERT INTO `g_data_source_column` VALUES (1398525793096249346, 'marcFile', 'http://192.168.1.27:38080/rest/chenyi/cyf/e60b7471-0234-4939-bbd5-7c76b58b09b8', 'dataSource_xgab9d_2616');
INSERT INTO `g_data_source_column` VALUES (1399186667456774146, 'marcFile', 'http://192.168.1.27:38080/rest/chenyi/cyf/65762e82-6380-4ec5-9d05-a87741564551', 'dataSource_qt2_929');
INSERT INTO `g_data_source_column` VALUES (1399193788609019906, 'marcFile', 'http://192.168.1.27:38080/rest/chenyi/cyf/bc6e77d9-378d-4b0c-bf40-5e722f857d30', 'dataSource_3');
INSERT INTO `g_data_source_column` VALUES (1399200722749804546, 'marcFile', 'http://192.168.1.27:38080/rest/chenyi/cyf/bc072265-73b3-45d2-88b3-4d12ae2056da', 'dataSource_iso');
INSERT INTO `g_data_source_column` VALUES (1399201756066926594, 'marcFile', 'http://192.168.1.27:38080/rest/chenyi/cyf/01379c16-5ac4-4487-977d-58d06da6a677', 'dataSource_xl_2021');

-- ----------------------------
-- Table structure for g_metadata_column
-- ----------------------------
DROP TABLE IF EXISTS `g_metadata_column`;
CREATE TABLE `g_metadata_column`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `table_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联元数据表code',
  `parent_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父级',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '唯一标识',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `data_type_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数据类型，关联数据词典code',
  `marc_field` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'marc数据字段',
  `marc_children_field` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'marc数据子字段',
  `marc_indicator` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'marc字段指示符',
  `marc_fixed_length_field` tinyint(4) NULL DEFAULT NULL COMMENT 'marc数据，是否为定长字段',
  `marc_start_fixed_length` int(9) NULL DEFAULT NULL COMMENT 'marc数据，定长字段开始位置',
  `marc_end_fixed_length` int(9) NULL DEFAULT NULL COMMENT 'marc数据，定长字段结束位置',
  `excel_field_index` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字段下标',
  `as_field_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'AS数据字段名称',
  `es_field_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'es字段',
  `es_field_index` tinyint(1) NULL DEFAULT NULL COMMENT '字段是否建索引（0：否，1：是）',
  `es_field_analyzer` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'es字段分词器',
  `more_column` tinyint(4) NOT NULL COMMENT '是否允许多列（0：否，1：是）',
  `version` int(10) NOT NULL COMMENT '版本号',
  `status` tinyint(4) NOT NULL COMMENT '状态（字典 0正常 1停用 2删除）',
  `length` int(10) NOT NULL COMMENT '字段长度',
  `is_null` tinyint(4) NOT NULL COMMENT '是否非空（0：否，1：是）',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(50) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(50) NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `code`(`code`) USING BTREE,
  INDEX `fk_metadata_table_code`(`table_code`) USING BTREE,
  CONSTRAINT `fk_metadata_table_code` FOREIGN KEY (`table_code`) REFERENCES `g_metadata_table` (`code`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of g_metadata_column
-- ----------------------------
INSERT INTO `g_metadata_column` VALUES (1392714110331293697, 'm_t_ChenYiWenXianHuiZong', '', 'ChenYiWenXianHuiZong_themePerson', '主题人', 'excel_string', NULL, '', '', NULL, NULL, NULL, 'L', '', NULL, NULL, NULL, 0, 1, 0, 200, 0, 100, '2021-05-13 13:31:34', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1392714110360653825, 'm_t_ChenYiWenXianHuiZong', '', 'ChenYiWenXianHuiZong_address', '地点', 'excel_string', NULL, '', '', NULL, NULL, NULL, 'J', '', NULL, NULL, NULL, 0, 1, 0, 200, 0, 100, '2021-05-13 13:31:34', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1392714110394208258, 'm_t_ChenYiWenXianHuiZong', '', 'ChenYiWenXianHuiZong_startTime', '起始日期', 'excel_string', NULL, '', '', NULL, NULL, NULL, 'A', '', NULL, NULL, NULL, 0, 1, 0, 200, 0, 100, '2021-05-13 13:31:34', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1392714110427762690, 'm_t_ChenYiWenXianHuiZong', '', 'ChenYiWenXianHuiZong_incident', '事件', 'excel_string', NULL, '', '', NULL, NULL, NULL, 'E', '', NULL, NULL, NULL, 0, 1, 0, 200, 0, 100, '2021-05-13 13:31:34', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1392714110461317122, 'm_t_ncorporate_entities', '', 'ncorporate_entities_lock_version', 'lock_version', 'as_string', NULL, '', '', NULL, NULL, NULL, '', 'lock_version', NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-13 13:31:34', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1392714110507454465, 'm_t_ncorporate_entities', '', 'ncorporate_entities_names', 'names', 'as_json', NULL, '', '', NULL, NULL, NULL, '', 'names', NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-13 13:31:34', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1392714110557786113, 'm_t_ncorporate_entities', 'ncorporate_entities_names', 'ncorporate_entities_names_lock_version', 'names_lock_version', 'as_string', NULL, '', '', NULL, NULL, NULL, '', 'lock_version', NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-13 13:31:34', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1392714110595534850, 'm_t_ncorporate_entities', 'ncorporate_entities_names', 'ncorporate_entities_names_sort_name_auto_generate', 'names_sort_name_auto_generate', 'as_string', NULL, '', '', NULL, NULL, NULL, '', 'sort_name_auto_generate', NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-13 13:31:34', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1392714110645866497, 'm_t_ncorporate_entities', 'ncorporate_entities_names', 'ncorporate_entities_names_created_by', 'names_created_by', 'as_string', NULL, '', '', NULL, NULL, NULL, '', 'created_by', NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-13 13:31:34', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1392714110687809537, 'm_t_ncorporate_entities', 'ncorporate_entities_names', 'ncorporate_entities_names_sort_name', 'names_sort_name', 'as_string', NULL, '', '', NULL, NULL, NULL, '', 'sort_name', NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-13 13:31:34', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1392714110725558273, 'm_t_ncorporate_entities', 'ncorporate_entities_names', 'ncorporate_entities_names_primary_name', 'names_primary_name', 'as_string', NULL, '', '', NULL, NULL, NULL, '', 'primary_name', NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-13 13:31:34', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1392714110780084226, 'm_t_iso', '', 'iso_duty', '责任', 'marc_json', NULL, '', '', NULL, NULL, NULL, '', '', NULL, NULL, NULL, 1, 1, 0, 100, 0, 100, '2021-05-13 13:31:34', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1392714110805250049, 'm_t_iso', 'iso_duty', 'iso_duty_name', '责任人', 'marc_string', '702', 'a', '0', NULL, NULL, NULL, '', '', NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-13 13:31:34', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1392714110822027266, 'm_t_iso', 'iso_duty', 'iso_duty_explain', '责任说明', 'marc_string', '702', '4', '0', NULL, NULL, NULL, '', '', NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-13 13:31:34', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1392714110859776002, 'm_t_ZhongWenShuMu', '', 'ZhongWenShuMu_title', '标题', 'marc_string', '200', 'a', '', NULL, NULL, NULL, '', '', NULL, NULL, NULL, 0, 1, 0, 200, 0, 100, '2021-05-13 13:31:34', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1392714110901719041, 'm_t_ZhongWenShuMu', '', 'ZhongWenShuMu_published', '出版', 'marc_json', NULL, '', '', NULL, NULL, NULL, '', '', NULL, NULL, NULL, 0, 1, 0, 200, 0, 100, '2021-05-13 13:31:34', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1392714110922690561, 'm_t_ZhongWenShuMu', 'ZhongWenShuMu_published', 'ZhongWenShuMu_published_organized', '出版社', 'marc_string', '210', 'c', '', NULL, NULL, NULL, '', '', NULL, NULL, NULL, 0, 1, 0, 200, 0, 100, '2021-05-13 13:31:34', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1392714110964633602, 'm_t_ZhongWenShuMu', 'ZhongWenShuMu_published', 'ZhongWenShuMu_published_address', '出版地', 'marc_string', '210', 'a', '', NULL, NULL, NULL, '', '', NULL, NULL, NULL, 0, 1, 0, 200, 0, 100, '2021-05-13 13:31:34', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1392714111002382338, 'm_t_ZhongWenShuMu', 'ZhongWenShuMu_published', 'ZhongWenShuMu_published_time', '出版时间', 'marc_string', '210', 'd', '', NULL, NULL, NULL, '', '', NULL, NULL, NULL, 0, 1, 0, 200, 0, 100, '2021-05-13 13:31:34', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1392714111052713986, 'm_t_ZhongWenShuMu', '', 'ZhongWenShuMu_author', '作者', 'marc_string', '200', 'f', '', NULL, NULL, NULL, '', '', NULL, NULL, NULL, 0, 1, 0, 50, 0, 100, '2021-05-13 13:31:34', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1392714111094657026, 'm_t_ZhongWenShuMu', '', 'ZhongWenShuMu_volume', '卷期', 'marc_string', '207', 'a', '', NULL, NULL, NULL, '', '', NULL, NULL, NULL, 0, 1, 0, 200, 0, 100, '2021-05-13 13:31:34', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1392714111136600065, 'm_t_ncorporate_entities2', '', 'm_t_ncorporate_entities2_display_name', '显示名称', 'as_string', NULL, '', '', NULL, NULL, NULL, '', 'display_name', NULL, NULL, NULL, 0, 1, 2, 100, 0, 100, '2021-05-13 13:31:34', 1377879897444212737, '2021-06-04 18:34:52', 1377879897444212737);
INSERT INTO `g_metadata_column` VALUES (1392714111153377281, 'm_t_ncorporate_entities2', '', 'm_t_ncorporate_entities2_uri', '资源路径', 'as_string', NULL, '', '', NULL, NULL, NULL, '', 'uri', NULL, NULL, NULL, 0, 1, 2, 100, 0, 100, '2021-05-13 13:31:34', 1377879897444212737, '2021-06-04 18:35:32', 1377879897444212737);
INSERT INTO `g_metadata_column` VALUES (1392714111178543105, 'm_t_ncorporate_entities2', '', 'm_t_ncorporate_entities2_title', '标题', 'as_string', NULL, '', '', NULL, NULL, NULL, '', 'title', NULL, NULL, NULL, 0, 1, 2, 100, 0, 100, '2021-05-13 13:31:34', 1377879897444212737, '2021-06-04 18:35:29', 1377879897444212737);
INSERT INTO `g_metadata_column` VALUES (1394949679262228482, 'm_t_article', '', 'm_t_article_url', '全球唯一标识', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 17:34:55', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394949779002777602, 'm_t_article', '', 'm_t_article_title', '标题', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 17:35:19', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394949878265176065, 'm_t_article', '', 'm_t_article_subTitle', '副标题', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 17:35:43', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394950021244805122, 'm_t_article', '', 'm_t_article_parTitle', '并列标题', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 17:36:17', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394950174311735298, 'm_t_article', '', 'm_t_article_titleText', '标题集合', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 17:36:53', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394950298307944450, 'm_t_article', '', 'm_t_article_author1', '著者1', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 17:37:23', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394950433691688962, 'm_t_article', '', 'm_t_article_authorType1', '著作方式1', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 17:37:55', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394950551899758593, 'm_t_article', '', 'm_t_article_author2', '著者2', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 17:38:23', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394950646686834689, 'm_t_article', '', 'm_t_article_authorType2', '著作方式2', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 17:38:46', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394950715049795586, 'm_t_article', '', 'm_t_article_author3', '著者3', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 17:39:02', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394950783152709633, 'm_t_article', '', 'm_t_article_authorType3', '著作方式3', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 17:39:18', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394950956159361026, 'm_t_article', '', 'm_t_article_authorText', '作者集合', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 17:40:00', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394951093833195522, 'm_t_article', '', 'm_t_article_source', '文献来源', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 17:40:32', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394951203149340673, 'm_t_article', '', 'm_t_article_sourceText', '来源集合', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 17:40:58', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394951409609760769, 'm_t_article', '', 'm_t_article_writeDate', '写作时间', 'es_date', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 17:41:48', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394951524722434049, 'm_t_article', '', 'm_t_article_releaseDate', '发布时间', 'es_date', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 17:42:15', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394951602572910594, 'm_t_article', '', 'm_t_article_publisher', '出版商', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 17:42:34', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394951701021614081, 'm_t_article', '', 'm_t_article_publisherUrb', '出版（发行）地', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 17:42:57', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394951871234859009, 'm_t_article', '', 'm_t_article_volume', '卷', 'es_int', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 9, 0, 100, '2021-05-19 17:43:38', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394951962213507074, 'm_t_article', '', 'm_t_article_page', '页', 'es_int', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 9, 0, 100, '2021-05-19 17:43:59', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394952064864903169, 'm_t_article', '', 'm_t_article_issue', '期', 'es_int', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 9, 0, 100, '2021-05-19 17:44:24', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394952299976613890, 'm_t_article', '', 'm_t_article_year', '年', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 4, 0, 100, '2021-05-19 17:45:20', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394952430373330945, 'm_t_article', '', 'm_t_article_persons', '相关人名', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 17:45:51', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394952600968257538, 'm_t_article', '', 'm_t_article_personsText', '相关人名集合', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 17:46:32', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394952724775723010, 'm_t_article', '', 'm_t_article_events', '相关事件', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 17:47:01', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394952844179169281, 'm_t_article', '', 'm_t_article_eventsText', '相关事件集合', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 17:47:30', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394953065789415426, 'm_t_article', '', 'm_t_article_eventsId', '事件ID', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 17:48:23', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394953187793330177, 'm_t_article', '', 'm_t_article_abstract', '摘要', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 500, 0, 100, '2021-05-19 17:48:52', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394953316621377538, 'm_t_article', '', 'm_t_article_abstractText', '摘要集合', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 17:49:22', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394953419646066690, 'm_t_article', '', 'm_t_article_keywords', '关键词', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 17:49:47', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394953513099354113, 'm_t_article', '', 'm_t_article_keywordsText', '关键词集合', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 17:50:09', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394953702212132866, 'm_t_article', '', 'm_t_article_category', '资源类型', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 17:50:54', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394953844784914433, 'm_t_article', '', 'm_t_article_fileType1', '文件类型1', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 17:51:28', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394953907724640258, 'm_t_article', '', 'm_t_article_fileType2', '文件类型2', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 17:51:43', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394954001349894145, 'm_t_article', '', 'm_t_article_filePath', '资源路径', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 17:52:06', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394954102533283841, 'm_t_article', '', 'm_t_article_isDelete', '删除标识', 'es_int', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 1, 0, 100, '2021-05-19 17:52:30', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394954209081188354, 'm_t_article', '', 'm_t_article_role', '权限', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 17:52:55', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394954338634850305, 'm_t_article', '', 'm_t_article_createdDate', '创建时间', 'es_date', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 17:53:26', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394954478099652610, 'm_t_article', '', 'm_t_article_lastModifiedDate', '最后修改时间', 'es_date', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 17:53:59', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394957918175518721, 'm_t_incident', '', 'm_t_incident_pid', '上级ID', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 18:07:39', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394958064464453633, 'm_t_incident', '', 'm_t_incident_purl', '上级唯一标识', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 18:08:14', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394958211017629697, 'm_t_incident', '', 'm_t_incident_startDate', '起始时间', 'es_date', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 18:08:49', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394958279464476674, 'm_t_incident', '', 'm_t_incident_endDate', '结束时间', 'es_date', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 18:09:06', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394958389451710466, 'm_t_incident', '', 'm_t_incident_dateRemark', '时间备注', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 500, 0, 100, '2021-05-19 18:09:32', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394958504149147649, 'm_t_incident', '', 'm_t_incident_place', '时间地点', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 18:09:59', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394958625033183234, 'm_t_incident', '', 'm_t_incident_type', '事件类型', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 18:10:28', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394958668150628353, 'm_t_incident', '', 'm_t_incident_place', '事件地点', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 2, 0, 100, 0, 100, '2021-05-19 18:10:38', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394958771477307394, 'm_t_incident', '', 'm_t_incident_coordinates', '坐标', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 18:11:03', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394958897885241346, 'm_t_incident', '', 'm_t_incident_main', '主题人物/组织', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 18:11:33', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394959038906130433, 'm_t_incident', '', 'm_t_incident_mainId', '主题人物/组织ID', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 18:12:07', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394959192585428993, 'm_t_incident', '', 'm_t_incident_second', '客体人物/组织', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 18:12:43', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394959262273789953, 'm_t_incident', '', 'm_t_incident_secondId', '客体人物/组织ID', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 18:13:00', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394959379248734209, 'm_t_incident', '', 'm_t_incident_relation', '关系', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 18:13:28', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394959493971337218, 'm_t_incident', '', 'm_t_incident_personRemark', '人物备注', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 500, 0, 100, '2021-05-19 18:13:55', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394959589639217154, 'm_t_incident', '', 'm_t_incident_predicate', '谓词', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 18:14:18', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394959700972822530, 'm_t_incident', '', 'm_t_incident_work', '作品', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 18:14:44', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394959810611929089, 'm_t_incident', '', 'm_t_incident_workId', '作品ID', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 18:15:11', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394959914848772097, 'm_t_incident', '', 'm_t_incident_workRemark', '作品备注', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 500, 0, 100, '2021-05-19 18:15:35', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394960012500557826, 'm_t_incident', '', 'm_t_incident_meeting', '会议', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 18:15:59', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394960146642788353, 'm_t_incident', '', 'm_t_incident_meetingRemark', '会议备注', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 500, 0, 100, '2021-05-19 18:16:31', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394960224686202881, 'm_t_incident', '', 'm_t_incident_meetingId', '会议ID', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 18:16:49', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394960361353404417, 'm_t_incident', '', 'm_t_incident_otherRemark', '其他备注', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 500, 0, 100, '2021-05-19 18:17:22', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394960460036988930, 'm_t_incident', '', 'm_t_incident_ISBN', 'ISBN/AS-ID', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 18:17:45', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394960542941601793, 'm_t_incident', '', 'm_t_incident_page', '页码', 'es_int', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 9, 0, 100, '2021-05-19 18:18:05', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394960633450487809, 'm_t_incident', '', 'm_t_incident_title', '事件名', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 18:18:27', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394960724114563074, 'm_t_incident', '', 'm_t_incident_content', '被拆解语句', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 500, 0, 100, '2021-05-19 18:18:48', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394960826451386370, 'm_t_incident', '', 'm_t_incident_isDelete', '删除标识', 'es_int', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 1, 0, 100, '2021-05-19 18:19:13', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394960889529524226, 'm_t_incident', '', 'm_t_incident_role', '权限', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 18:19:28', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394960993921556481, 'm_t_incident', '', 'm_t_incident_createdDate', '创建时间', 'es_date', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 18:19:53', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1394961121927520258, 'm_t_incident', '', 'm_t_incident_lastModifiedDate', '最后修改时间', 'es_date', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-19 18:20:23', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395239265054019585, 'm_t_article', '', 'm_t_article_id', '主键', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-20 12:45:38', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395239413482049537, 'm_t_incident', '', 'm_t_incident_id', '主键', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, 0, 100, 0, 100, '2021-05-20 12:46:13', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395251549725868034, 'm_t_incident', '', 'm_t_incident_pid', '上级ID', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'pid', 1, NULL, 0, 3, 0, 100, 0, 100, '2021-05-20 13:34:27', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395251594760110081, 'm_t_incident', '', 'm_t_incident_personRemark', '人物备注', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'personRemark', 1, NULL, 0, 4, 0, 500, 0, 100, '2021-05-20 13:34:37', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395251661508263938, 'm_t_incident', '', 'm_t_incident_content', '被拆解语句', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'content', 1, NULL, 0, 5, 0, 500, 0, 100, '2021-05-20 13:34:53', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395251711307235330, 'm_t_incident', '', 'm_t_incident_workId', '作品ID', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'workId', 1, NULL, 0, 6, 0, 100, 0, 100, '2021-05-20 13:35:05', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395251779372400641, 'm_t_incident', '', 'm_t_incident_predicate', '谓词', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'predicate', 1, NULL, 0, 7, 0, 100, 0, 100, '2021-05-20 13:35:21', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395251817674784770, 'm_t_incident', '', 'm_t_incident_dateRemark', '时间备注', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'dateRemark', 1, NULL, 0, 8, 0, 500, 0, 100, '2021-05-20 13:35:31', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395251853993263106, 'm_t_incident', '', 'm_t_incident_role', '权限', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'role', 1, NULL, 0, 9, 0, 100, 0, 100, '2021-05-20 13:35:39', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395251885874167810, 'm_t_incident', '', 'm_t_incident_ISBN', 'ISBN/AS-ID', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'ISBN', 1, NULL, 0, 10, 0, 100, 0, 100, '2021-05-20 13:35:47', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395251916865880065, 'm_t_incident', '', 'm_t_incident_main', '主题人物/组织', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'main', 1, NULL, 0, 11, 0, 100, 0, 100, '2021-05-20 13:35:54', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395251946813210625, 'm_t_incident', '', 'm_t_incident_startDate', '起始时间', 'es_date', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'startDate', 1, NULL, 0, 12, 0, 100, 0, 100, '2021-05-20 13:36:01', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395251992212357122, 'm_t_incident', '', 'm_t_incident_meetingId', '会议ID', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'meetingId', 1, NULL, 0, 13, 0, 100, 0, 100, '2021-05-20 13:36:12', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395252087066542082, 'm_t_incident', '', 'm_t_incident_page', '页码', 'es_int', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'page', 1, NULL, 0, 14, 0, 9, 0, 100, '2021-05-20 13:36:35', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395252125167599617, 'm_t_incident', '', 'm_t_incident_work', '作品', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'work', 1, NULL, 0, 15, 0, 100, 0, 100, '2021-05-20 13:36:44', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395252181748760577, 'm_t_incident', '', 'm_t_incident_place', '事件地点', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'place', 1, NULL, 0, 16, 0, 100, 0, 100, '2021-05-20 13:36:57', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395252222001496066, 'm_t_incident', '', 'm_t_incident_createdDate', '创建时间', 'es_date', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'createdDate', 1, NULL, 0, 17, 0, 100, 0, 100, '2021-05-20 13:37:07', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395252783832711170, 'm_t_incident', '', 'm_t_incident_relation', '关系', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'relation', 1, NULL, 0, 18, 0, 100, 0, 100, '2021-05-20 13:39:21', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395252810395238401, 'm_t_incident', '', 'm_t_incident_id', '主键', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'id', 1, NULL, 0, 19, 0, 100, 0, 100, '2021-05-20 13:39:27', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395252842393583618, 'm_t_incident', '', 'm_t_incident_lastModifiedDate', '最后修改时间', 'es_date', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'lastModifiedDate', 1, NULL, 0, 20, 0, 100, 0, 100, '2021-05-20 13:39:35', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395252879991324673, 'm_t_incident', '', 'm_t_incident_secondId', '客体人物/组织ID', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'secondId', 1, NULL, 0, 21, 0, 100, 0, 100, '2021-05-20 13:39:44', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395252939198119937, 'm_t_incident', '', 'm_t_incident_meeting', '会议', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'meeting', 1, NULL, 0, 22, 0, 100, 0, 100, '2021-05-20 13:39:58', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395252973977288706, 'm_t_incident', '', 'm_t_incident_type', '事件类型', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'type', 1, NULL, 0, 23, 0, 100, 0, 100, '2021-05-20 13:40:06', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395253011176570881, 'm_t_incident', '', 'm_t_incident_otherRemark', '其他备注', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'otherRemark', 1, NULL, 0, 24, 0, 500, 0, 100, '2021-05-20 13:40:15', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395253065937403906, 'm_t_incident', '', 'm_t_incident_coordinates', '坐标', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'coordinates', 1, NULL, 0, 25, 0, 100, 0, 100, '2021-05-20 13:40:28', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395253110732570626, 'm_t_incident', '', 'm_t_incident_workRemark', '作品备注', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'workRemark', 1, NULL, 0, 26, 0, 500, 0, 100, '2021-05-20 13:40:39', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395253137043439618, 'm_t_incident', '', 'm_t_incident_purl', '上级唯一标识', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'purl', 1, NULL, 0, 27, 0, 100, 0, 100, '2021-05-20 13:40:45', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395253171361234946, 'm_t_incident', '', 'm_t_incident_endDate', '结束时间', 'es_date', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'endDate', 1, NULL, 0, 28, 0, 100, 0, 100, '2021-05-20 13:40:53', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395253245646553090, 'm_t_incident', '', 'm_t_incident_isDelete', '删除标识', 'es_int', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'isDelete', 1, NULL, 0, 29, 0, 1, 0, 100, '2021-05-20 13:41:11', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395253289904848898, 'm_t_incident', '', 'm_t_incident_second', '客体人物/组织', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'second', 1, NULL, 0, 30, 0, 100, 0, 100, '2021-05-20 13:41:22', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395253322528145410, 'm_t_incident', '', 'm_t_incident_mainId', '主题人物/组织ID', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'mainId', 1, NULL, 0, 31, 0, 100, 0, 100, '2021-05-20 13:41:29', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395253361333846017, 'm_t_incident', '', 'm_t_incident_title', '事件名', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'title', 1, NULL, 0, 32, 0, 100, 0, 100, '2021-05-20 13:41:39', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395253658487701505, 'm_t_article', '', 'm_t_article_sourceText', '来源集合', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'sourceText', 1, NULL, 0, 2, 0, 100, 0, 100, '2021-05-20 13:42:49', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395253686841196546, 'm_t_article', '', 'm_t_article_eventsText', '相关事件集合', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'eventsText', 1, NULL, 0, 3, 0, 100, 0, 100, '2021-05-20 13:42:56', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395253713777016833, 'm_t_article', '', 'm_t_article_abstract', '摘要', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'abstract', 1, NULL, 0, 4, 0, 500, 0, 100, '2021-05-20 13:43:03', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395253741631389697, 'm_t_article', '', 'm_t_article_titleText', '标题集合', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'titleText', 1, NULL, 0, 5, 0, 100, 0, 100, '2021-05-20 13:43:09', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395253766893682689, 'm_t_article', '', 'm_t_article_fileType2', '文件类型2', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'fileType2', 1, NULL, 0, 6, 0, 100, 0, 100, '2021-05-20 13:43:15', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395253797424021506, 'm_t_article', '', 'm_t_article_author2', '著者2', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'author2', 1, NULL, 0, 7, 0, 100, 0, 100, '2021-05-20 13:43:23', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395253841443241986, 'm_t_article', '', 'm_t_article_writeDate', '写作时间', 'es_date', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'writeDate', 1, NULL, 0, 8, 0, 100, 0, 100, '2021-05-20 13:43:33', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395253902617165826, 'm_t_article', '', 'm_t_article_issue', '期', 'es_int', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'issue', 1, NULL, 0, 9, 0, 9, 0, 100, '2021-05-20 13:43:48', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395253934015725570, 'm_t_article', '', 'm_t_article_authorType1', '著作方式1', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'authorType1', 1, NULL, 0, 10, 0, 100, 0, 100, '2021-05-20 13:43:55', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395253968320937985, 'm_t_article', '', 'm_t_article_lastModifiedDate', '最后修改时间', 'es_date', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'lastModifiedDate', 1, NULL, 0, 11, 0, 100, 0, 100, '2021-05-20 13:44:03', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395254184512143362, 'm_t_article', '', 'm_t_article_persons', '相关人名', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'persons', 1, NULL, 0, 12, 0, 100, 0, 100, '2021-05-20 13:44:55', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395254209799602178, 'm_t_article', '', 'm_t_article_publisherUrb', '出版（发行）地', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'publisherUrb', 1, NULL, 0, 13, 0, 100, 0, 100, '2021-05-20 13:45:01', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395254234575355906, 'm_t_article', '', 'm_t_article_category', '资源类型', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'category', 1, NULL, 0, 14, 0, 100, 0, 100, '2021-05-20 13:45:07', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395254260500348930, 'm_t_article', '', 'm_t_article_source', '文献来源', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'source', 1, NULL, 0, 15, 0, 100, 0, 100, '2021-05-20 13:45:13', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395254297427001346, 'm_t_article', '', 'm_t_article_eventsId', '事件ID', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'eventsId', 1, NULL, 0, 16, 0, 100, 0, 100, '2021-05-20 13:45:22', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395254334517231617, 'm_t_article', '', 'm_t_article_title', '标题', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'title', 1, NULL, 0, 17, 0, 100, 0, 100, '2021-05-20 13:45:31', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395254377714368514, 'm_t_article', '', 'm_t_article_fileType1', '文件类型1', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'fileType1', 1, NULL, 0, 18, 0, 100, 0, 100, '2021-05-20 13:45:41', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395254494353768449, 'm_t_article', '', 'm_t_article_author1', '著者1', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'author1', 1, NULL, 0, 19, 0, 100, 0, 100, '2021-05-20 13:46:09', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395254530470920194, 'm_t_article', '', 'm_t_article_volume', '卷', 'es_int', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'volume', 1, NULL, 0, 20, 0, 9, 0, 100, '2021-05-20 13:46:17', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395254561403912194, 'm_t_article', '', 'm_t_article_isDelete', '删除标识', 'es_int', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'isDelete', 1, NULL, 0, 21, 0, 1, 0, 100, '2021-05-20 13:46:25', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395254736692264961, 'm_t_article', '', 'm_t_article_authorText', '作者集合', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'authorText', 1, NULL, 0, 22, 0, 100, 0, 100, '2021-05-20 13:47:06', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395254775724457985, 'm_t_article', '', 'm_t_article_keywordsText', '关键词集合', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'keywordsText', 1, NULL, 0, 23, 0, 100, 0, 100, '2021-05-20 13:47:16', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395254803880820737, 'm_t_article', '', 'm_t_article_parTitle', '并列标题', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'parTitle', 1, NULL, 0, 24, 0, 100, 0, 100, '2021-05-20 13:47:22', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395254938157268993, 'm_t_article', '', 'm_t_article_authorType3', '著作方式3', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'authorType3', 1, NULL, 0, 25, 0, 100, 0, 100, '2021-05-20 13:47:54', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395254969639714818, 'm_t_article', '', 'm_t_article_publisher', '出版商', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'publisher', 1, NULL, 0, 26, 0, 100, 0, 100, '2021-05-20 13:48:02', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395255009342996481, 'm_t_article', '', 'm_t_article_role', '权限', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'role', 1, NULL, 0, 27, 0, 100, 0, 100, '2021-05-20 13:48:11', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395255038917033985, 'm_t_article', '', 'm_t_article_events', '相关事件', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'events', 1, NULL, 0, 28, 0, 100, 0, 100, '2021-05-20 13:48:19', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395255071716491266, 'm_t_article', '', 'm_t_article_subTitle', '副标题', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'subTitle', 1, NULL, 0, 29, 0, 100, 0, 100, '2021-05-20 13:48:26', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395255116327108609, 'm_t_article', '', 'm_t_article_filePath', '资源路径', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'filePath', 1, NULL, 0, 30, 0, 100, 0, 100, '2021-05-20 13:48:37', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395255165408854018, 'm_t_article', '', 'm_t_article_abstractText', '摘要集合', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'abstractText', 1, NULL, 0, 31, 0, 100, 0, 100, '2021-05-20 13:48:49', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395255224363991042, 'm_t_article', '', 'm_t_article_url', '全球唯一标识', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'url', 1, NULL, 0, 32, 0, 100, 0, 100, '2021-05-20 13:49:03', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395255249143939073, 'm_t_article', '', 'm_t_article_id', '主键', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'id', 1, NULL, 0, 33, 0, 100, 0, 100, '2021-05-20 13:49:09', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395255281830150146, 'm_t_article', '', 'm_t_article_author3', '著者3', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'author3', 1, NULL, 0, 34, 0, 100, 0, 100, '2021-05-20 13:49:16', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395255315636240385, 'm_t_article', '', 'm_t_article_year', '年', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'year', 1, NULL, 0, 35, 0, 4, 0, 100, '2021-05-20 13:49:24', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395255343859712002, 'm_t_article', '', 'm_t_article_keywords', '关键词', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'keywords', 1, NULL, 0, 36, 0, 100, 0, 100, '2021-05-20 13:49:31', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395255382803824642, 'm_t_article', '', 'm_t_article_page', '页', 'es_int', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'page', 1, NULL, 0, 37, 0, 9, 0, 100, '2021-05-20 13:49:41', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395255425854160898, 'm_t_article', '', 'm_t_article_authorType2', '著作方式2', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'authorType2', 1, NULL, 0, 38, 0, 100, 0, 100, '2021-05-20 13:49:51', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395255462692732930, 'm_t_article', '', 'm_t_article_personsText', '相关人名集合', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'personsText', 1, NULL, 0, 39, 0, 100, 0, 100, '2021-05-20 13:50:00', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395255498965073921, 'm_t_article', '', 'm_t_article_releaseDate', '发布时间', 'es_date', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'releaseDate', 1, NULL, 0, 40, 0, 100, 0, 100, '2021-05-20 13:50:08', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395255526114803713, 'm_t_article', '', 'm_t_article_createdDate', '创建时间', 'es_date', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'createdDate', 1, NULL, 0, 41, 0, 100, 0, 100, '2021-05-20 13:50:15', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395260906878337025, 'm_t_incident', '', 'm_t_incident_meetingRemark', '会议备注', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'meetingRemark', 1, NULL, 0, 33, 0, 500, 0, 100, '2021-05-20 14:11:38', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395278149469089793, 'm_t_article', '', 'm_t_article_page', '页', 'es_json', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'page', 1, NULL, 0, 42, 0, 9, 0, 100, '2021-05-20 15:20:09', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395278383817437186, 'm_t_article', 'm_t_article_page', 'm_t_article_page_pageStart', '开始页', 'es_int', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'pageStart', 1, NULL, 0, 1, 2, 9, 0, 100, '2021-05-20 15:21:04', 1377879897444212737, '2021-06-04 11:11:43', 1377879897444212737);
INSERT INTO `g_metadata_column` VALUES (1395278602382618626, 'm_t_article', 'm_t_article_page', 'm_t_article_page_pageEnd', '结束页', 'es_int', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'pageEnd', 1, NULL, 0, 1, 2, 9, 0, 100, '2021-05-20 15:21:56', 1377879897444212737, '2021-06-04 11:11:37', 1377879897444212737);
INSERT INTO `g_metadata_column` VALUES (1395288547488485377, 'm_t_article', '', 'm_t_article_title', '标题', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'title', 1, NULL, 0, 43, 0, 100, 0, 100, '2021-05-20 16:01:28', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395289450379530241, 'm_t_article', '', 'm_t_article_title', '标题', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'title', 1, NULL, 0, 44, 0, 100, 0, 100, '2021-05-20 16:05:03', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395289756068794370, 'm_t_article', '', 'm_t_article_title', '标题', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'title', 1, NULL, 0, 45, 0, 100, 0, 100, '2021-05-20 16:06:16', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395289798087331841, 'm_t_article', '', 'm_t_article_title', '标题', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'title', 1, 'ngram_analyzer', 0, 46, 0, 100, 0, 100, '2021-05-20 16:06:26', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395290592278794242, 'm_t_article', '', 'm_t_article_filePath', '资源路径', 'es_string', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'filePath', 1, '不分词', 0, 47, 0, 100, 0, 100, '2021-05-20 16:09:35', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395293794759917569, 'm_t_incident', '', 'm_t_incident_meetingRemark', '会议备注', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'meetingRemark', 1, '不分词', 0, 34, 0, 500, 0, 100, '2021-05-20 16:22:19', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395293842558205953, 'm_t_incident', '', 'm_t_incident_second', '客体人物/组织', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'second', 1, '不分词', 0, 35, 0, 100, 0, 100, '2021-05-20 16:22:30', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395293886791335937, 'm_t_incident', '', 'm_t_incident_title', '事件名', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'title', 1, '不分词', 0, 36, 0, 100, 0, 100, '2021-05-20 16:22:41', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395293913588744193, 'm_t_incident', '', 'm_t_incident_personRemark', '人物备注', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'personRemark', 1, '不分词', 0, 37, 0, 500, 0, 100, '2021-05-20 16:22:47', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395293943791927297, 'm_t_incident', '', 'm_t_incident_content', '被拆解语句', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'content', 1, '不分词', 0, 38, 0, 500, 0, 100, '2021-05-20 16:22:54', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395294016638599169, 'm_t_incident', '', 'm_t_incident_workId', '作品ID', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'workId', 1, '不分词', 0, 39, 0, 100, 0, 100, '2021-05-20 16:23:12', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395294044857876482, 'm_t_incident', '', 'm_t_incident_predicate', '谓词', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'predicate', 1, '不分词', 0, 40, 0, 100, 0, 100, '2021-05-20 16:23:18', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395294073777602561, 'm_t_incident', '', 'm_t_incident_dateRemark', '时间备注', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'dateRemark', 1, '不分词', 0, 41, 0, 500, 0, 100, '2021-05-20 16:23:25', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395294095994830849, 'm_t_incident', '', 'm_t_incident_ISBN', 'ISBN/AS-ID', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'ISBN', 1, '不分词', 0, 42, 0, 100, 0, 100, '2021-05-20 16:23:30', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395294118530826241, 'm_t_incident', '', 'm_t_incident_main', '主题人物/组织', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'main', 1, '不分词', 0, 43, 0, 100, 0, 100, '2021-05-20 16:23:36', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395294147819651074, 'm_t_incident', '', 'm_t_incident_meetingId', '会议ID', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'meetingId', 1, '不分词', 0, 44, 0, 100, 0, 100, '2021-05-20 16:23:43', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395294183550926850, 'm_t_incident', '', 'm_t_incident_role', '权限', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'role', 1, '不分词', 0, 45, 0, 100, 0, 100, '2021-05-20 16:23:51', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395294387599622146, 'm_t_incident', '', 'm_t_incident_work', '作品', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'work', 1, '不分词', 0, 46, 0, 100, 0, 100, '2021-05-20 16:24:40', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395294411003838465, 'm_t_incident', '', 'm_t_incident_place', '事件地点', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'place', 1, '不分词', 0, 47, 0, 100, 0, 100, '2021-05-20 16:24:46', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395294479664594946, 'm_t_incident', '', 'm_t_incident_id', '主键', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'id', 1, '不分词', 0, 48, 0, 100, 0, 100, '2021-05-20 16:25:02', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395294505212100610, 'm_t_incident', '', 'm_t_incident_meeting', '会议', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'meeting', 1, '不分词', 0, 49, 0, 100, 0, 100, '2021-05-20 16:25:08', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395294543594176513, 'm_t_incident', '', 'm_t_incident_relation', '关系', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'relation', 1, '不分词', 0, 50, 0, 100, 0, 100, '2021-05-20 16:25:17', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395294568931966977, 'm_t_incident', '', 'm_t_incident_otherRemark', '其他备注', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'otherRemark', 1, '不分词', 0, 51, 0, 500, 0, 100, '2021-05-20 16:25:23', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395294587776974849, 'm_t_incident', '', 'm_t_incident_secondId', '客体人物/组织ID', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'secondId', 1, '不分词', 0, 52, 0, 100, 0, 100, '2021-05-20 16:25:28', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395294633838821378, 'm_t_incident', '', 'm_t_incident_type', '事件类型', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'type', 1, '不分词', 0, 53, 0, 100, 0, 100, '2021-05-20 16:25:39', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395294653870817281, 'm_t_incident', '', 'm_t_incident_pid', '上级ID', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'pid', 1, '不分词', 0, 54, 0, 100, 0, 100, '2021-05-20 16:25:43', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395294707801178114, 'm_t_incident', '', 'm_t_incident_coordinates', '坐标', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'coordinates', 1, '不分词', 0, 55, 0, 100, 0, 100, '2021-05-20 16:25:56', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395294730165207042, 'm_t_incident', '', 'm_t_incident_workRemark', '作品备注', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'workRemark', 1, '不分词', 0, 56, 0, 500, 0, 100, '2021-05-20 16:26:02', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395294752042696705, 'm_t_incident', '', 'm_t_incident_purl', '上级唯一标识', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'purl', 1, '不分词', 0, 57, 0, 100, 0, 100, '2021-05-20 16:26:07', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395294823014514689, 'm_t_incident', '', 'm_t_incident_mainId', '主题人物/组织ID', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'mainId', 1, '不分词', 0, 58, 0, 100, 0, 100, '2021-05-20 16:26:24', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395294878270275586, 'm_t_article', '', 'm_t_article_eventsText', '相关事件集合', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'eventsText', 1, '不分词', 0, 48, 0, 100, 0, 100, '2021-05-20 16:26:37', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395294904539201538, 'm_t_article', '', 'm_t_article_year', '年', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'year', 1, '不分词', 0, 49, 0, 4, 0, 100, '2021-05-20 16:26:43', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395294932724924418, 'm_t_article', '', 'm_t_article_personsText', '相关人名集合', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'personsText', 1, '不分词', 0, 50, 0, 100, 0, 100, '2021-05-20 16:26:50', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395294953738387458, 'm_t_article', '', 'm_t_article_fileType2', '文件类型2', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'fileType2', 1, '不分词', 0, 51, 0, 100, 0, 100, '2021-05-20 16:26:55', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395294980200251394, 'm_t_article', '', 'm_t_article_abstract', '摘要', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'abstract', 1, '不分词', 0, 52, 0, 500, 0, 100, '2021-05-20 16:27:01', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395295002497171457, 'm_t_article', '', 'm_t_article_author2', '著者2', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'author2', 1, '不分词', 0, 53, 0, 100, 0, 100, '2021-05-20 16:27:07', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395295028308918274, 'm_t_article', '', 'm_t_article_sourceText', '来源集合', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'sourceText', 1, '不分词', 0, 54, 0, 100, 0, 100, '2021-05-20 16:27:13', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395295063306190850, 'm_t_article', '', 'm_t_article_authorType1', '著作方式1', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'authorType1', 1, '不分词', 0, 55, 0, 100, 0, 100, '2021-05-20 16:27:21', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395295091282198530, 'm_t_article', '', 'm_t_article_category', '资源类型', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'category', 1, '不分词', 0, 56, 0, 100, 0, 100, '2021-05-20 16:27:28', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395295150652571650, 'm_t_article', '', 'm_t_article_titleText', '标题集合', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'titleText', 1, '不分词', 0, 57, 0, 100, 0, 100, '2021-05-20 16:27:42', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395295175805812737, 'm_t_article', '', 'm_t_article_eventsId', '事件ID', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'eventsId', 1, '不分词', 0, 58, 0, 100, 0, 100, '2021-05-20 16:27:48', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395295205845417985, 'm_t_article', '', 'm_t_article_fileType1', '文件类型1', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'fileType1', 1, '不分词', 0, 59, 0, 100, 0, 100, '2021-05-20 16:27:55', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395295230382096385, 'm_t_article', '', 'm_t_article_persons', '相关人名', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'persons', 1, '不分词', 0, 60, 0, 100, 0, 100, '2021-05-20 16:28:01', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395295254914580482, 'm_t_article', '', 'm_t_article_publisherUrb', '出版（发行）地', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'publisherUrb', 1, '不分词', 0, 61, 0, 100, 0, 100, '2021-05-20 16:28:07', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395295307209162753, 'm_t_article', '', 'm_t_article_author1', '著者1', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'author1', 1, '不分词', 0, 62, 0, 100, 0, 100, '2021-05-20 16:28:19', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395295332039442433, 'm_t_article', '', 'm_t_article_source', '文献来源', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'source', 1, '不分词', 0, 63, 0, 100, 0, 100, '2021-05-20 16:28:25', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395295355816951809, 'm_t_article', '', 'm_t_article_authorText', '作者集合', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'authorText', 1, '不分词', 0, 64, 0, 100, 0, 100, '2021-05-20 16:28:31', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395295382513696769, 'm_t_article', '', 'm_t_article_authorType3', '著作方式3', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'authorType3', 1, '不分词', 0, 65, 0, 100, 0, 100, '2021-05-20 16:28:37', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395295417674547201, 'm_t_article', '', 'm_t_article_title', '标题', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'title', 1, 'ngram_analyzer', 0, 66, 0, 100, 0, 100, '2021-05-20 16:28:46', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395295443041697794, 'm_t_article', '', 'm_t_article_events', '相关事件', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'events', 1, '不分词', 0, 67, 0, 100, 0, 100, '2021-05-20 16:28:52', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395295477858615298, 'm_t_article', '', 'm_t_article_keywordsText', '关键词集合', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'keywordsText', 1, '不分词', 0, 68, 0, 100, 0, 100, '2021-05-20 16:29:00', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395295501267025922, 'm_t_article', '', 'm_t_article_filePath', '资源路径', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'filePath', 1, '不分词', 0, 69, 0, 100, 0, 100, '2021-05-20 16:29:05', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395295525145198593, 'm_t_article', '', 'm_t_article_parTitle', '并列标题', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'parTitle', 1, '不分词', 0, 70, 0, 100, 0, 100, '2021-05-20 16:29:11', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395295555981721601, 'm_t_article', '', 'm_t_article_publisher', '出版商', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'publisher', 1, '不分词', 0, 71, 0, 100, 0, 100, '2021-05-20 16:29:19', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395295574457630722, 'm_t_article', '', 'm_t_article_id', '主键', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'id', 1, '不分词', 0, 72, 0, 100, 0, 100, '2021-05-20 16:29:23', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395295605206073345, 'm_t_article', '', 'm_t_article_abstractText', '摘要集合', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'abstractText', 1, '不分词', 0, 73, 0, 100, 0, 100, '2021-05-20 16:29:30', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395295630229291010, 'm_t_article', '', 'm_t_article_role', '权限', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'role', 1, '不分词', 0, 74, 0, 100, 0, 100, '2021-05-20 16:29:36', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395295655005044737, 'm_t_article', '', 'm_t_article_keywords', '关键词', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'keywords', 1, '不分词', 0, 75, 0, 100, 0, 100, '2021-05-20 16:29:42', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395295677532651522, 'm_t_article', '', 'm_t_article_author3', '著者3', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'author3', 1, '不分词', 0, 76, 0, 100, 0, 100, '2021-05-20 16:29:48', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395295702799138817, 'm_t_article', '', 'm_t_article_authorType2', '著作方式2', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'authorType2', 1, '不分词', 0, 77, 0, 100, 0, 100, '2021-05-20 16:29:54', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395295732578697218, 'm_t_article', '', 'm_t_article_subTitle', '副标题', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'subTitle', 1, '不分词', 0, 78, 0, 100, 0, 100, '2021-05-20 16:30:01', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395295757903904769, 'm_t_article', '', 'm_t_article_url', '全球唯一标识', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'url', 1, '不分词', 0, 79, 0, 100, 0, 100, '2021-05-20 16:30:07', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395592012550946817, 'm_t_incident', '', 'm_t_incident_id', '主键', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'id', 1, '不分词', 0, 59, 0, 1, 0, 100, '2021-05-21 12:07:19', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395592169526968321, 'm_t_incident', '', 'm_t_incident_id', '主键', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'id', 1, '不分词', 0, 60, 0, 100, 0, 1, '2021-05-21 12:07:57', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395592300091457538, 'm_t_incident', '', 'm_t_incident_pid', '上级ID', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'pid', 1, '不分词', 0, 61, 0, 100, 0, 2, '2021-05-21 12:08:28', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395592324145790978, 'm_t_incident', '', 'm_t_incident_purl', '上级唯一标识', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'purl', 1, '不分词', 0, 62, 0, 100, 0, 3, '2021-05-21 12:08:34', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395592417188036609, 'm_t_incident', '', 'm_t_incident_startDate', '起始时间', 'es_date', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'startDate', 1, '不分词', 0, 63, 0, 4, 0, 100, '2021-05-21 12:08:56', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395592457755344897, 'm_t_incident', '', 'm_t_incident_endDate', '结束时间', 'es_date', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'endDate', 1, '不分词', 0, 64, 0, 100, 0, 5, '2021-05-21 12:09:05', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395592486209503234, 'm_t_incident', '', 'm_t_incident_startDate', '起始时间', 'es_date', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'startDate', 1, '不分词', 0, 65, 0, 100, 0, 4, '2021-05-21 12:09:12', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395592537942048770, 'm_t_incident', '', 'm_t_incident_dateRemark', '时间备注', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'dateRemark', 1, '不分词', 0, 66, 0, 500, 0, 6, '2021-05-21 12:09:25', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395592655789408257, 'm_t_incident', '', 'm_t_incident_place', '事件地点', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'place', 1, '不分词', 0, 67, 0, 100, 0, 7, '2021-05-21 12:09:53', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395592688240738306, 'm_t_incident', '', 'm_t_incident_type', '事件类型', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'type', 1, '不分词', 0, 68, 0, 100, 0, 8, '2021-05-21 12:10:00', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395592747359453186, 'm_t_incident', '', 'm_t_incident_coordinates', '坐标', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'coordinates', 1, '不分词', 0, 69, 0, 100, 0, 9, '2021-05-21 12:10:14', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395592821158232066, 'm_t_incident', '', 'm_t_incident_main', '主题人物/组织', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'main', 1, '不分词', 0, 70, 0, 100, 0, 10, '2021-05-21 12:10:32', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395592851323666433, 'm_t_incident', '', 'm_t_incident_mainId', '主题人物/组织ID', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'mainId', 1, '不分词', 0, 71, 0, 100, 0, 11, '2021-05-21 12:10:39', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395592935171997698, 'm_t_incident', '', 'm_t_incident_second', '客体人物/组织', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'second', 1, '不分词', 0, 72, 0, 100, 0, 12, '2021-05-21 12:10:59', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395592968806121474, 'm_t_incident', '', 'm_t_incident_secondId', '客体人物/组织ID', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'secondId', 1, '不分词', 0, 73, 0, 100, 0, 13, '2021-05-21 12:11:07', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395593058237071361, 'm_t_incident', '', 'm_t_incident_relation', '关系', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'relation', 1, '不分词', 0, 74, 0, 100, 0, 14, '2021-05-21 12:11:29', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395593224230846466, 'm_t_incident', '', 'm_t_incident_personRemark', '人物备注', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'personRemark', 1, '不分词', 0, 75, 0, 500, 0, 15, '2021-05-21 12:12:08', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395593337426722818, 'm_t_incident', '', 'm_t_incident_predicate', '谓词', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'predicate', 1, '不分词', 0, 76, 0, 100, 0, 16, '2021-05-21 12:12:35', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395593388999884801, 'm_t_incident', '', 'm_t_incident_work', '作品', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'work', 1, '不分词', 0, 77, 0, 100, 0, 17, '2021-05-21 12:12:47', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395593423774859266, 'm_t_incident', '', 'm_t_incident_workId', '作品ID', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'workId', 1, '不分词', 0, 78, 0, 100, 0, 18, '2021-05-21 12:12:56', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395593452212240386, 'm_t_incident', '', 'm_t_incident_workRemark', '作品备注', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'workRemark', 1, '不分词', 0, 79, 0, 500, 0, 19, '2021-05-21 12:13:03', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395593603890855938, 'm_t_incident', '', 'm_t_incident_meeting', '会议', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'meeting', 1, '不分词', 0, 80, 0, 100, 0, 20, '2021-05-21 12:13:39', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395593630851842050, 'm_t_incident', '', 'm_t_incident_meetingRemark', '会议备注', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'meetingRemark', 1, '不分词', 0, 81, 0, 500, 0, 21, '2021-05-21 12:13:45', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395593660765618178, 'm_t_incident', '', 'm_t_incident_meetingId', '会议ID', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'meetingId', 1, '不分词', 0, 82, 0, 100, 0, 22, '2021-05-21 12:13:52', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395593712812736514, 'm_t_incident', '', 'm_t_incident_otherRemark', '其他备注', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'otherRemark', 1, '不分词', 0, 83, 0, 500, 0, 23, '2021-05-21 12:14:05', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395593792856834050, 'm_t_incident', '', 'm_t_incident_ISBN', 'ISBN/AS-ID', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'ISBN', 1, '不分词', 0, 84, 0, 100, 0, 23, '2021-05-21 12:14:24', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395593811022364673, 'm_t_incident', '', 'm_t_incident_ISBN', 'ISBN/AS-ID', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'ISBN', 1, '不分词', 0, 85, 0, 100, 0, 24, '2021-05-21 12:14:28', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395593888239501313, 'm_t_incident', '', 'm_t_incident_page', '页码', 'es_int', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'page', 1, '不分词', 0, 86, 0, 9, 0, 25, '2021-05-21 12:14:46', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395593951770624002, 'm_t_incident', '', 'm_t_incident_title', '事件名', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'title', 1, '不分词', 0, 87, 0, 100, 0, 26, '2021-05-21 12:15:02', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395594024474689537, 'm_t_incident', '', 'm_t_incident_content', '被拆解语句', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'content', 1, '不分词', 0, 88, 0, 500, 0, 27, '2021-05-21 12:15:19', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395594064622567426, 'm_t_incident', '', 'm_t_incident_isDelete', '删除标识', 'es_int', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'isDelete', 1, '不分词', 0, 89, 0, 1, 0, 28, '2021-05-21 12:15:29', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395594117722456065, 'm_t_incident', '', 'm_t_incident_role', '权限', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'role', 1, '不分词', 0, 90, 0, 100, 0, 29, '2021-05-21 12:15:41', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395594171128528897, 'm_t_incident', '', 'm_t_incident_createdDate', '创建时间', 'es_date', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'createdDate', 1, '不分词', 0, 91, 0, 100, 0, 30, '2021-05-21 12:15:54', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395594210592735233, 'm_t_incident', '', 'm_t_incident_lastModifiedDate', '最后修改时间', 'es_date', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'lastModifiedDate', 1, '不分词', 0, 92, 0, 100, 0, 31, '2021-05-21 12:16:03', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395594539342282754, 'm_t_article', '', 'm_t_article_id', '主键', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'id', 1, '不分词', 0, 80, 0, 100, 0, 1, '2021-05-21 12:17:22', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395594600725921794, 'm_t_article', '', 'm_t_article_url', '全球唯一标识', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'url', 1, '不分词', 0, 81, 0, 100, 0, 2, '2021-05-21 12:17:36', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395594666056400897, 'm_t_article', '', 'm_t_article_title', '标题', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'title', 1, 'ngram_analyzer', 0, 82, 0, 100, 0, 3, '2021-05-21 12:17:52', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395594699459837954, 'm_t_article', '', 'm_t_article_subTitle', '副标题', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'subTitle', 1, '不分词', 0, 83, 0, 100, 0, 4, '2021-05-21 12:18:00', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395594738416533506, 'm_t_article', '', 'm_t_article_parTitle', '并列标题', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'parTitle', 1, '不分词', 0, 84, 0, 100, 0, 5, '2021-05-21 12:18:09', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395594753943846913, 'm_t_article', '', 'm_t_article_titleText', '标题集合', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'titleText', 1, '不分词', 0, 85, 0, 100, 0, 6, '2021-05-21 12:18:13', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395594940368076801, 'm_t_article', '', 'm_t_article_author1', '著者1', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'author1', 1, '不分词', 0, 86, 0, 100, 0, 7, '2021-05-21 12:18:57', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395594962388172802, 'm_t_article', '', 'm_t_article_authorType1', '著作方式1', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'authorType1', 1, '不分词', 0, 87, 0, 100, 0, 8, '2021-05-21 12:19:03', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395595001776881666, 'm_t_article', '', 'm_t_article_author2', '著者2', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'author2', 1, '不分词', 0, 88, 0, 100, 0, 9, '2021-05-21 12:19:12', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395595027114672130, 'm_t_article', '', 'm_t_article_authorType2', '著作方式2', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'authorType2', 1, '不分词', 0, 89, 0, 100, 0, 10, '2021-05-21 12:19:18', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395595060304199681, 'm_t_article', '', 'm_t_article_author3', '著者3', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'author3', 1, '不分词', 0, 90, 0, 100, 0, 11, '2021-05-21 12:19:26', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395595080508162050, 'm_t_article', '', 'm_t_article_authorType3', '著作方式3', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'authorType3', 1, '不分词', 0, 91, 0, 100, 0, 12, '2021-05-21 12:19:31', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395595145071083522, 'm_t_article', '', 'm_t_article_authorText', '作者集合', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'authorText', 1, '不分词', 0, 92, 0, 100, 0, 13, '2021-05-21 12:19:46', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395595228323823617, 'm_t_article', '', 'm_t_article_source', '文献来源', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'source', 1, '不分词', 0, 93, 0, 100, 0, 14, '2021-05-21 12:20:06', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395595270732431361, 'm_t_article', '', 'm_t_article_sourceText', '来源集合', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'sourceText', 1, '不分词', 0, 94, 0, 100, 0, 15, '2021-05-21 12:20:16', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395595393625538561, 'm_t_article', '', 'm_t_article_writeDate', '写作时间', 'es_date', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'writeDate', 1, '不分词', 0, 95, 0, 100, 0, 16, '2021-05-21 12:20:45', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395595414467035137, 'm_t_article', '', 'm_t_article_releaseDate', '发布时间', 'es_date', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'releaseDate', 1, '不分词', 0, 96, 0, 100, 0, 17, '2021-05-21 12:20:50', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395595492778885121, 'm_t_article', '', 'm_t_article_publisher', '出版商', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'publisher', 1, '不分词', 0, 97, 0, 100, 0, 18, '2021-05-21 12:21:09', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395595581710712833, 'm_t_article', '', 'm_t_article_publisherUrb', '出版（发行）地', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'publisherUrb', 1, '不分词', 0, 98, 0, 100, 0, 19, '2021-05-21 12:21:30', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395595640623906818, 'm_t_article', '', 'm_t_article_page', '页', 'es_json', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'page', 1, '不分词', 0, 99, 0, 9, 0, 20, '2021-05-21 12:21:44', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395595676548120577, 'm_t_article', '', 'm_t_article_volume', '卷', 'es_int', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'volume', 1, '不分词', 0, 100, 0, 9, 0, 21, '2021-05-21 12:21:53', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395595714506571777, 'm_t_article', '', 'm_t_article_issue', '期', 'es_int', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'issue', 1, '不分词', 0, 101, 0, 9, 0, 22, '2021-05-21 12:22:02', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395595757850509313, 'm_t_article', '', 'm_t_article_year', '年', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'year', 1, '不分词', 0, 102, 0, 4, 0, 23, '2021-05-21 12:22:12', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395595837403873282, 'm_t_article', '', 'm_t_article_persons', '相关人名', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'persons', 1, '不分词', 0, 103, 0, 100, 0, 24, '2021-05-21 12:22:31', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395595879330136066, 'm_t_article', '', 'm_t_article_personsText', '相关人名集合', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'personsText', 1, '不分词', 0, 104, 0, 100, 0, 25, '2021-05-21 12:22:41', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395595913907978242, 'm_t_article', '', 'm_t_article_events', '相关事件', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'events', 1, '不分词', 0, 105, 0, 100, 0, 26, '2021-05-21 12:22:49', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395595949651836929, 'm_t_article', '', 'm_t_article_eventsText', '相关事件集合', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'eventsText', 1, '不分词', 0, 106, 0, 100, 0, 27, '2021-05-21 12:22:58', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395596051623755777, 'm_t_article', '', 'm_t_article_eventsId', '事件ID', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'eventsId', 1, '不分词', 0, 107, 0, 100, 0, 28, '2021-05-21 12:23:22', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395596129818165250, 'm_t_article', '', 'm_t_article_abstract', '摘要', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'abstract', 1, '不分词', 0, 108, 0, 500, 0, 29, '2021-05-21 12:23:41', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395596155743158274, 'm_t_article', '', 'm_t_article_abstractText', '摘要集合', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'abstractText', 1, '不分词', 0, 109, 0, 100, 0, 30, '2021-05-21 12:23:47', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395596220821979137, 'm_t_article', '', 'm_t_article_keywords', '关键词', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'keywords', 1, '不分词', 0, 110, 0, 100, 0, 31, '2021-05-21 12:24:03', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395596245270577154, 'm_t_article', '', 'm_t_article_keywordsText', '关键词集合', 'es_keyword', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'keywordsText', 1, '不分词', 0, 111, 0, 100, 0, 32, '2021-05-21 12:24:08', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395596305458839553, 'm_t_article', '', 'm_t_article_category', '资源类型', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'category', 1, '不分词', 0, 112, 0, 100, 0, 33, '2021-05-21 12:24:23', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395596344675581954, 'm_t_article', '', 'm_t_article_fileType1', '文件类型1', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'fileType1', 1, '不分词', 0, 113, 0, 100, 0, 34, '2021-05-21 12:24:32', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395596364988596226, 'm_t_article', '', 'm_t_article_fileType2', '文件类型2', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'fileType2', 1, '不分词', 0, 114, 0, 100, 0, 35, '2021-05-21 12:24:37', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395596444630040578, 'm_t_article', '', 'm_t_article_filePath', '资源路径', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'filePath', 1, '不分词', 0, 115, 0, 100, 0, 36, '2021-05-21 12:24:56', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395596494328348674, 'm_t_article', '', 'm_t_article_isDelete', '删除标识', 'es_int', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'isDelete', 1, '不分词', 0, 116, 0, 1, 0, 37, '2021-05-21 12:25:08', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395596585411854338, 'm_t_article', '', 'm_t_article_role', '权限', 'es_text', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'role', 1, '不分词', 0, 117, 0, 100, 0, 38, '2021-05-21 12:25:30', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395596625048027137, 'm_t_article', '', 'm_t_article_createdDate', '创建时间', 'es_date', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'createdDate', 1, '不分词', 0, 118, 0, 100, 0, 39, '2021-05-21 12:25:39', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1395596649957998593, 'm_t_article', '', 'm_t_article_lastModifiedDate', '最后修改时间', 'es_date', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'lastModifiedDate', 1, '不分词', 0, 119, 0, 100, 0, 40, '2021-05-21 12:25:45', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1397744422140592130, 'm_t_iso', 'iso_duty', 'iso_duty_name', '责任人', 'marc_string', '702', 'a', '#0', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 0, 2, 0, 100, 0, 100, '2021-05-27 10:40:14', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1397744448505987073, 'm_t_iso', 'iso_duty', 'iso_duty_explain', '责任说明', 'marc_string', '702', '4', '0#', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 0, 3, 0, 100, 0, 100, '2021-05-27 10:40:20', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1397744786302648321, 'm_t_iso', 'iso_duty', 'iso_duty_all', '无指示符', 'marc_string', '702', '4', '##', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 0, 1, 0, 100, 0, 100, '2021-05-27 10:41:41', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1397750127350280194, 'm_t_iso', 'iso_duty', 'iso_duty_explain', '责任说明', 'marc_string', '702', '4', '#0', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 0, 4, 0, 100, 0, 100, '2021-05-27 11:02:54', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1397774699160244225, 'm_t_iso', '', 'iso_page', '页码', 'marc_string', '215', 'a', '##', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, 0, 1, 0, 100, 0, 100, '2021-05-27 12:40:32', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1397794549920206850, 'm_t_iso', '', 'iso_date', '日期', 'marc_string', '100', 'a', NULL, 1, 1, 8, NULL, NULL, NULL, 0, NULL, 0, 1, 0, 10, 0, 100, '2021-05-27 13:59:25', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1397794684624474114, 'm_t_iso', '', 'iso_date', '日期', 'marc_string', '100', 'a', NULL, 1, 1, 8, NULL, NULL, NULL, 0, NULL, 0, 5, 0, 10, 0, 100, '2021-05-27 13:59:57', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1397794774873313282, 'm_t_iso', '', 'iso_date', '日期', 'marc_string', '100', 'a', NULL, 0, NULL, NULL, NULL, NULL, NULL, 0, NULL, 0, 6, 0, 10, 0, 100, '2021-05-27 14:00:19', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1397794940300857345, 'm_t_iso', '', 'iso_date', '日期', 'marc_string', '100', 'a', NULL, 1, 1, 8, NULL, NULL, NULL, 0, NULL, 0, 7, 0, 10, 0, 100, '2021-05-27 14:00:58', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1397795327661608961, 'm_t_iso', '', 'iso_date', '日期', 'marc_string', '100', 'a', NULL, 0, NULL, NULL, NULL, NULL, NULL, 0, NULL, 0, 8, 0, 10, 0, 100, '2021-05-27 14:02:31', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1397795397660348417, 'm_t_iso', '', 'iso_date', '日期', 'marc_string', '100', 'a', NULL, 1, 1, 8, NULL, NULL, NULL, 0, NULL, 0, 9, 0, 10, 0, 100, '2021-05-27 14:02:47', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1397798537432440834, 'm_t_iso', '', 'iso_duty', '责任', 'marc_json', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, 0, NULL, 1, 10, 0, 100, 0, 1, '2021-05-27 14:15:16', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1397798564997406722, 'm_t_iso', '', 'iso_page', '页码', 'marc_string', '215', 'a', '##', 0, NULL, NULL, NULL, NULL, NULL, 0, NULL, 0, 11, 0, 100, 0, 2, '2021-05-27 14:15:22', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1397798584182153217, 'm_t_iso', '', 'iso_date', '日期', 'marc_string', '100', 'a', NULL, 1, 1, 8, NULL, NULL, NULL, 0, NULL, 0, 12, 0, 10, 0, 3, '2021-05-27 14:15:27', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1397798614712492033, 'm_t_iso', '', 'iso_date', '日期', 'marc_string', '100', 'a', NULL, 1, 1, 8, NULL, NULL, NULL, 0, NULL, 0, 13, 0, 10, 0, 3, '2021-05-27 14:15:34', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1397800858157621250, 'm_t_iso', '', 'iso_date', '日期', 'marc_string', '100', 'a', NULL, 1, NULL, NULL, NULL, NULL, NULL, 0, NULL, 0, 14, 0, 10, 0, 3, '2021-05-27 14:24:29', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1397801326166450177, 'm_t_iso', '', 'iso_date', '日期', 'marc_string', '100', 'a', NULL, 1, 0, 0, NULL, NULL, NULL, 0, NULL, 0, 15, 0, 10, 0, 3, '2021-05-27 14:26:21', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1397801737732530178, 'm_t_iso', '', 'iso_date', '日期', 'marc_string', '100', 'a', NULL, 1, 0, 0, NULL, NULL, NULL, 0, NULL, 0, 16, 0, 10, 0, 3, '2021-05-27 14:27:59', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1397802135629373442, 'm_t_iso', '', 'iso_date', '日期', 'marc_string', '100', 'a', NULL, 1, 0, 0, NULL, NULL, NULL, 0, NULL, 0, 17, 0, 10, 0, 3, '2021-05-27 14:29:34', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1397802420330340353, 'm_t_iso', '', 'iso_date', '日期', 'marc_string', '100', 'a', NULL, 1, 0, 0, NULL, NULL, NULL, 0, NULL, 0, 18, 0, 10, 0, 3, '2021-05-27 14:30:42', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1397802586508664833, 'm_t_iso', '', 'iso_date', '日期', 'marc_string', '100', 'a', NULL, 1, 0, 1, NULL, NULL, NULL, 0, NULL, 0, 19, 0, 10, 0, 3, '2021-05-27 14:31:21', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1397804025675067393, 'm_t_iso', '', 'iso_date', '日期', 'marc_string', '100', 'a', NULL, 1, 0, 8, NULL, NULL, NULL, 0, NULL, 0, 20, 0, 10, 0, 3, '2021-05-27 14:37:04', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1398113516241104898, 'm_t_iso', 'iso_duty', 'iso_duty_name', '责任人', 'marc_string', '702', 'a', '#0', 0, NULL, NULL, NULL, NULL, NULL, 0, NULL, 0, 21, 0, 1, 0, 100, '2021-05-28 11:06:53', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1398113594687172609, 'm_t_iso', 'iso_duty', 'iso_duty_name', '责任人', 'marc_string', '702', 'a', '#0', 0, NULL, NULL, NULL, NULL, NULL, 0, NULL, 0, 22, 0, 100, 0, 1, '2021-05-28 11:07:11', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1398113627646013442, 'm_t_iso', 'iso_duty', 'iso_duty_all', '无指示符', 'marc_string', '702', '4', '##', 0, NULL, NULL, NULL, NULL, NULL, 0, NULL, 0, 23, 0, 100, 0, 2, '2021-05-28 11:07:19', 1377879897444212737, '2021-05-28 18:56:14', 1377879897444212737);
INSERT INTO `g_metadata_column` VALUES (1398113650970537985, 'm_t_iso', 'iso_duty', 'iso_duty_explain', '责任说明', 'marc_string', '702', '4', '#0', 0, NULL, NULL, NULL, NULL, NULL, 0, NULL, 0, 24, 0, 100, 0, 3, '2021-05-28 11:07:25', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1398157871169478657, 'm_t_iso', 'iso_duty', 'iso_duty_name', '责任人', 'marc_string', '1', 'a', '#0', 0, NULL, NULL, NULL, NULL, NULL, 0, NULL, 0, 25, 0, 100, 0, 1, '2021-05-28 14:03:08', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1398158030267817985, 'm_t_iso', 'iso_duty', 'iso_duty_name', '责任人', 'marc_string', '001', 'a', '#0', 0, NULL, NULL, NULL, NULL, NULL, 0, NULL, 0, 26, 0, 100, 0, 1, '2021-05-28 14:03:46', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1398164176143691778, 'm_t_iso', 'iso_duty', 'iso_duty_name', '责任人', 'marc_string', '702', 'a', '#0', 0, NULL, NULL, NULL, NULL, NULL, 0, NULL, 0, 27, 0, 100, 0, 1, '2021-05-28 14:28:11', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1398164648543956993, 'm_t_iso', '', 'iso_duty_field_all', '责任_所有字段', 'marc_string', '702', NULL, '#0', 0, NULL, NULL, NULL, NULL, NULL, 0, NULL, 0, 1, 0, 100, 0, 100, '2021-05-28 14:30:04', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1398180689298944001, 'm_t_iso', '', 'iso_page', '页码', 'marc_string', '215', 'a', '##', 0, NULL, NULL, NULL, NULL, NULL, 0, NULL, 0, 28, 0, 100, 0, 4, '2021-05-28 15:33:48', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1398180881129631746, 'm_t_iso', '', 'iso_duty_field_all', '责任_所有字段', 'marc_string', '702', NULL, '#0', 0, NULL, NULL, NULL, NULL, NULL, 0, NULL, 0, 29, 0, 100, 0, 2, '2021-05-28 15:34:34', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1398191501996511233, 'm_t_iso', '', 'iso_nullArray', '空数组', 'marc_json', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, 0, NULL, 0, 1, 0, 100, 0, 100, '2021-05-28 16:16:46', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1398191613766324226, 'm_t_iso', '', 'iso_nullJson', '空json', 'marc_json', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, 0, NULL, 0, 1, 0, 100, 0, 100, '2021-05-28 16:17:13', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1398191817181679617, 'm_t_iso', 'iso_nullJson', 'iso_nullJson_01', '测试空json', 'marc_string', '001', NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, 0, NULL, 0, 1, 0, 100, 0, 100, '2021-05-28 16:18:01', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1398192123583975425, 'm_t_iso', 'iso_nullJson', 'iso_nullJson_01', '测试空json', 'marc_string', '002', NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, 0, NULL, 0, 30, 0, 100, 0, 100, '2021-05-28 16:19:14', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1398192291687485442, 'm_t_iso', 'iso_nullArray', 'iso_nullArray_02', '测试空数组', 'marc_json', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, 0, NULL, 0, 1, 0, 100, 0, 100, '2021-05-28 16:19:54', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1398192586266038274, 'm_t_iso', 'iso_nullJson', 'iso_nullJson_01', '测试空json', 'marc_json', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, 0, NULL, 0, 31, 0, 100, 0, 100, '2021-05-28 16:21:04', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1398192767208312834, 'm_t_iso', 'iso_nullJson_01', 'iso_nullJson_01_01', '测试空json01_01', 'marc_string', '002', '', NULL, 0, NULL, NULL, NULL, NULL, NULL, 0, NULL, 0, 1, 0, 100, 0, 100, '2021-05-28 16:21:48', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1398198057802743809, 'm_t_iso', 'iso_nullJson_01', 'iso_nullJson_01_01', '测试空json01_01', 'marc_string', '001', '', NULL, 0, NULL, NULL, NULL, NULL, NULL, 0, NULL, 0, 32, 0, 100, 0, 100, '2021-05-28 16:42:49', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1398198239688736770, 'm_t_iso', 'iso_nullArray', 'iso_nullArray_02', '测试空数组', 'marc_string', '001', NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, 0, NULL, 0, 33, 0, 100, 0, 100, '2021-05-28 16:43:32', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1398198702500823042, 'm_t_iso', 'iso_nullJson_01', 'iso_nullJson_01_01', '测试空json01_01', 'marc_string', '002', '', NULL, 0, NULL, NULL, NULL, NULL, NULL, 0, NULL, 0, 34, 0, 100, 0, 100, '2021-05-28 16:45:23', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1398198761929916417, 'm_t_iso', 'iso_nullArray', 'iso_nullArray_02', '测试空数组', 'marc_json', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, 0, NULL, 0, 35, 0, 100, 0, 100, '2021-05-28 16:45:37', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1398523627044417537, 'm_t_ChenYiWenXianHuiZong', '', 'ChenYiWenXianHuiZong_address', '地点', 'excel_string', NULL, NULL, NULL, 0, NULL, NULL, 'J', NULL, NULL, 0, NULL, 0, 5, 0, 200, 0, 100, '2021-05-29 14:16:31', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1398874932455747585, 'm_t_article', '', 'm_t_article_title', '标题', 'es_keyword', NULL, NULL, NULL, 0, NULL, NULL, NULL, 'title', 'title', 1, 'ngram_analyzer', 0, 120, 0, 100, 0, 3, '2021-05-30 13:32:28', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1398875102891290626, 'm_t_article', '', 'm_t_article_subTitle', '副标题', 'es_keyword', NULL, NULL, NULL, 0, NULL, NULL, NULL, 'display_string', 'subTitle', 1, '不分词', 0, 121, 0, 100, 0, 4, '2021-05-30 13:33:09', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1398875746448519170, 'm_t_article', '', 'm_t_article_category', '资源类型', 'es_text', NULL, NULL, NULL, 0, NULL, NULL, NULL, 'title', 'category', 1, '不分词', 0, 122, 0, 100, 0, 33, '2021-05-30 13:35:42', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1400651533908492289, 'm_t_article', '', 'm_t_article_page', '页', 'es_text', NULL, NULL, NULL, 0, NULL, NULL, NULL, '11', 'page', 1, '不分词', 0, 123, 0, 9, 0, 20, '2021-06-04 11:12:03', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1400762460222259202, 'm_t_ncorporate_entities2', '', 'm_t_ncorporate_entities2_titleInfo', '标题', 'as_string', NULL, NULL, NULL, 0, NULL, NULL, NULL, 'title', NULL, 0, NULL, 0, 1, 0, 50, 0, 100, '2021-06-04 18:32:50', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1400762584247828481, 'm_t_ncorporate_entities2', '', 'm_t_ncorporate_entities2_children', '下级', 'as_json', NULL, NULL, NULL, 0, NULL, NULL, NULL, 'children', NULL, 0, NULL, 0, 1, 0, 50, 0, 100, '2021-06-04 18:33:20', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1400762705949753346, 'm_t_ncorporate_entities2', '', 'm_t_ncorporate_entities2_titleInfo', '标题', 'as_string', NULL, NULL, NULL, 0, NULL, NULL, NULL, 'title', NULL, 0, NULL, 0, 7, 0, 50, 0, 1, '2021-06-04 18:33:49', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1400762727076462593, 'm_t_ncorporate_entities2', '', 'm_t_ncorporate_entities2_children', '下级', 'as_json', NULL, NULL, NULL, 0, NULL, NULL, NULL, 'children', NULL, 0, NULL, 0, 8, 0, 50, 0, 2, '2021-06-04 18:33:54', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1400763268514971650, 'm_t_ncorporate_entities2', 'm_t_ncorporate_entities2_children', 'm_t_ncorporate_entities2_children_title', '名称', 'as_string', NULL, NULL, NULL, 0, NULL, NULL, NULL, 'title', NULL, 0, NULL, 0, 1, 0, 50, 0, 1, '2021-06-04 18:36:03', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402212157868232705, 'm_t_article', '', 'm_t_article_pageCount', '总页数', 'es_keyword', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'pageCount', 0, '不分词', 0, 1, 0, 50, 0, 100, '2021-06-08 18:33:25', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402212308573769730, 'm_t_article', '', 'm_t_article_srcDatabase', '来源数据库', 'es_keyword', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'srcDatabase', 0, '不分词', 0, 1, 0, 50, 0, 100, '2021-06-08 18:34:01', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402212554724888578, 'm_t_article', '', 'm_t_article_city', '城市', 'es_text', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'city', 0, '不分词', 0, 1, 0, 50, 0, 100, '2021-06-08 18:35:00', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402212647670665218, 'm_t_article', '', 'm_t_article_journalName', '刊名', 'es_text', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'journalName', 0, '不分词', 0, 1, 0, 50, 0, 100, '2021-06-08 18:35:22', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402212763861274625, 'm_t_article', '', 'm_t_article_callNumber', '索书号', 'es_keyword', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'callNumber', 0, '不分词', 0, 1, 0, 50, 0, 100, '2021-06-08 18:35:49', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402212878546128897, 'm_t_article', '', 'm_t_article_organization', '机构', 'es_text', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'organization', 0, '不分词', 0, 1, 0, 50, 0, 100, '2021-06-08 18:36:17', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402212974029459458, 'm_t_article', '', 'm_t_article_conferenceName', '会议名称', 'es_text', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'conferenceName', 0, '不分词', 0, 1, 0, 50, 0, 100, '2021-06-08 18:36:40', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402213111388721153, 'm_t_article', '', 'm_t_article_conferenceAddress', '会议地点', 'es_text', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'conferenceAddress', 0, '不分词', 0, 1, 0, 50, 0, 100, '2021-06-08 18:37:12', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402213209589960706, 'm_t_article', '', 'm_t_article_seriesName', '丛书名', 'es_text', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'seriesName', 0, '不分词', 0, 1, 0, 50, 0, 100, '2021-06-08 18:37:36', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402213335091924994, 'm_t_article', '', 'm_t_article_edition', '版次', 'es_keyword', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'edition', 0, '不分词', 0, 1, 0, 50, 0, 100, '2021-06-08 18:38:06', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402213434123636738, 'm_t_article', '', 'm_t_article_catalogueDate', '编目日期', 'es_keyword', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'catalogueDate', 0, '不分词', 0, 1, 0, 50, 0, 100, '2021-06-08 18:38:29', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402213554344972289, 'm_t_article', '', 'm_t_article_barCode', '条码', 'es_keyword', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'barCode', 0, '不分词', 0, 1, 0, 50, 0, 100, '2021-06-08 18:38:58', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402213666249003009, 'm_t_article', '', 'm_t_article_price', '价格', 'es_keyword', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'price', 0, '不分词', 0, 1, 0, 50, 0, 100, '2021-06-08 18:39:25', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402213784813588482, 'm_t_article', '', 'm_t_article_manufacturingCompany', '制作公司', 'es_text', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'manufacturingCompany', 0, '不分词', 0, 1, 0, 50, 0, 100, '2021-06-08 18:39:53', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402213889897680898, 'm_t_article', '', 'm_t_article_publishingPlatform', '发布平台', 'es_text', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'publishingPlatform', 0, '不分词', 0, 1, 0, 50, 0, 100, '2021-06-08 18:40:18', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402213928967622657, 'm_t_article', '', 'm_t_article_title', '标题', 'es_text', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'title', 1, 'ngram_analyzer', 0, 124, 0, 100, 0, 3, '2021-06-08 18:40:27', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402214061696372737, 'm_t_article', '', 'm_t_article_pageCount', '总页数', 'es_keyword', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'pageCount', 0, '不分词', 0, 125, 0, 50, 0, 41, '2021-06-08 18:40:59', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402214296451567617, 'm_t_article', '', 'm_t_article_srcDatabase', '来源数据库', 'es_keyword', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'srcDatabase', 0, '不分词', 0, 126, 0, 50, 0, 42, '2021-06-08 18:41:55', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402214331100712962, 'm_t_article', '', 'm_t_article_city', '城市', 'es_text', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'city', 0, '不分词', 0, 127, 0, 50, 0, 43, '2021-06-08 18:42:03', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402214382606766081, 'm_t_article', '', 'm_t_article_journalName', '刊名', 'es_text', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'journalName', 0, '不分词', 0, 128, 0, 50, 0, 44, '2021-06-08 18:42:15', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402214567206473729, 'm_t_article', '', 'm_t_article_callNumber', '索书号', 'es_keyword', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'callNumber', 0, '不分词', 0, 129, 0, 50, 0, 45, '2021-06-08 18:42:59', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402214604766466049, 'm_t_article', '', 'm_t_article_organization', '机构', 'es_text', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'organization', 0, '不分词', 0, 130, 0, 50, 0, 46, '2021-06-08 18:43:08', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402214652044660738, 'm_t_article', '', 'm_t_article_conferenceName', '会议名称', 'es_text', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'conferenceName', 0, '不分词', 0, 131, 0, 50, 0, 47, '2021-06-08 18:43:20', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402214695514427393, 'm_t_article', '', 'm_t_article_seriesName', '丛书名', 'es_text', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'seriesName', 0, '不分词', 0, 132, 0, 50, 0, 48, '2021-06-08 18:43:30', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402214743958638594, 'm_t_article', '', 'm_t_article_edition', '版次', 'es_keyword', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'edition', 0, '不分词', 0, 133, 0, 50, 0, 49, '2021-06-08 18:43:42', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402214789416505346, 'm_t_article', '', 'm_t_article_catalogueDate', '编目日期', 'es_keyword', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'catalogueDate', 0, '不分词', 0, 134, 0, 50, 0, 50, '2021-06-08 18:43:52', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402214823063212033, 'm_t_article', '', 'm_t_article_barCode', '条码', 'es_keyword', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'barCode', 0, '不分词', 0, 135, 0, 50, 0, 51, '2021-06-08 18:44:00', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402214850795950082, 'm_t_article', '', 'm_t_article_price', '价格', 'es_keyword', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'price', 0, '不分词', 0, 136, 0, 50, 0, 52, '2021-06-08 18:44:07', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402214881003327490, 'm_t_article', '', 'm_t_article_manufacturingCompany', '制作公司', 'es_text', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'manufacturingCompany', 0, '不分词', 0, 137, 0, 50, 0, 53, '2021-06-08 18:44:14', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402214920991821826, 'm_t_article', '', 'm_t_article_publishingPlatform', '发布平台', 'es_text', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'publishingPlatform', 0, '不分词', 0, 138, 0, 50, 0, 54, '2021-06-08 18:44:24', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402215042203013122, 'm_t_article', '', 'm_t_article_conferenceAddress', '会议地点', 'es_text', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'conferenceAddress', 0, '不分词', 0, 139, 0, 55, 0, 100, '2021-06-08 18:44:53', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402215072951455746, 'm_t_article', '', 'm_t_article_conferenceAddress', '会议地点', 'es_text', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'conferenceAddress', 0, '不分词', 0, 140, 0, 50, 0, 55, '2021-06-08 18:45:00', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402215194082955265, 'm_t_article', '', 'm_t_article_subTitle', '副标题', 'es_text', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'subTitle', 1, '不分词', 0, 141, 0, 100, 0, 4, '2021-06-08 18:45:29', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402215216254046210, 'm_t_article', '', 'm_t_article_parTitle', '并列标题', 'es_text', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'parTitle', 1, '不分词', 0, 142, 0, 100, 0, 5, '2021-06-08 18:45:34', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402215238777458689, 'm_t_article', '', 'm_t_article_titleText', '标题集合', 'es_text', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'titleText', 1, '不分词', 0, 143, 0, 100, 0, 6, '2021-06-08 18:45:39', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402215291206258689, 'm_t_article', '', 'm_t_article_writeDate', '写作时间', 'es_text', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'writeDate', 1, '不分词', 0, 144, 0, 100, 0, 16, '2021-06-08 18:45:52', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402215317517127681, 'm_t_article', '', 'm_t_article_releaseDate', '发布时间', 'es_text', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'releaseDate', 1, '不分词', 0, 145, 0, 100, 0, 17, '2021-06-08 18:45:58', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402215394394525698, 'm_t_article', '', 'm_t_article_abstract', '摘要', 'es_text', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'abstract', 1, '不分词', 0, 146, 0, 500, 0, 29, '2021-06-08 18:46:17', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402215460182183938, 'm_t_article', '', 'm_t_article_isDelete', '删除标识', 'es_text', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'isDelete', 1, '不分词', 0, 147, 0, 1, 0, 37, '2021-06-08 18:46:32', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402215551504764930, 'm_t_incident', '', 'm_t_incident_startDate', '起始时间', 'es_text', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'startDate', 1, '不分词', 0, 93, 0, 100, 0, 4, '2021-06-08 18:46:54', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402215574573436929, 'm_t_incident', '', 'm_t_incident_endDate', '结束时间', 'es_text', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'endDate', 1, '不分词', 0, 94, 0, 100, 0, 5, '2021-06-08 18:47:00', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402215624863141890, 'm_t_incident', '', 'm_t_incident_isDelete', '删除标识', 'es_text', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'isDelete', 1, '不分词', 0, 95, 0, 1, 0, 28, '2021-06-08 18:47:12', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402215644735754241, 'm_t_incident', '', 'm_t_incident_page', '页码', 'es_text', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'page', 1, '不分词', 0, 96, 0, 9, 0, 25, '2021-06-08 18:47:16', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402215671122120706, 'm_t_incident', '', 'm_t_incident_createdDate', '创建时间', 'es_text', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'createdDate', 1, '不分词', 0, 97, 0, 100, 0, 30, '2021-06-08 18:47:23', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402215702847836161, 'm_t_incident', '', 'm_t_incident_lastModifiedDate', '最后修改时间', 'es_text', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'lastModifiedDate', 1, '不分词', 0, 98, 0, 100, 0, 31, '2021-06-08 18:47:30', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402216139042869249, 'm_t_article', '', 'm_t_article_volume', '卷', 'es_text', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'volume', 1, '不分词', 0, 148, 0, 9, 0, 21, '2021-06-08 18:49:14', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402216168138756098, 'm_t_article', '', 'm_t_article_issue', '期', 'es_text', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'issue', 1, '不分词', 0, 149, 0, 9, 0, 22, '2021-06-08 18:49:21', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402216206847987714, 'm_t_article', '', 'm_t_article_createdDate', '创建时间', 'es_text', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'createdDate', 1, '不分词', 0, 150, 0, 100, 0, 39, '2021-06-08 18:49:30', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_column` VALUES (1402216227945336833, 'm_t_article', '', 'm_t_article_lastModifiedDate', '最后修改时间', 'es_text', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, 'lastModifiedDate', 1, '不分词', 0, 151, 0, 100, 0, 40, '2021-06-08 18:49:35', 1377879897444212737, NULL, NULL);

-- ----------------------------
-- Table structure for g_metadata_constraint
-- ----------------------------
DROP TABLE IF EXISTS `g_metadata_constraint`;
CREATE TABLE `g_metadata_constraint`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `metadata_column_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '元数据字段code',
  `dict_data_id` bigint(20) NOT NULL COMMENT '数据词典id',
  `root_dict_data_id` bigint(20) NOT NULL COMMENT '数据词典根节点id（在页面下拉框中显示）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of g_metadata_constraint
-- ----------------------------
INSERT INTO `g_metadata_constraint` VALUES (1396647371675881473, 'ChenYiWenXianHuiZong_address', 1395614346838065154, 1395613872445505538);
INSERT INTO `g_metadata_constraint` VALUES (1396647371675881474, 'ChenYiWenXianHuiZong_address', 1395614432250871810, 1395613872445505538);
INSERT INTO `g_metadata_constraint` VALUES (1396647371684270081, 'ChenYiWenXianHuiZong_address', 1395614165837070337, 1395613872445505538);
INSERT INTO `g_metadata_constraint` VALUES (1396647371684270082, 'ChenYiWenXianHuiZong_address', 1395614346838065154, 1395613872445505538);
INSERT INTO `g_metadata_constraint` VALUES (1396647371684270083, 'ChenYiWenXianHuiZong_address', 1395614432250871810, 1395613872445505538);
INSERT INTO `g_metadata_constraint` VALUES (1396647371692658690, 'ChenYiWenXianHuiZong_address', 1395614165837070337, 1395613872445505538);
INSERT INTO `g_metadata_constraint` VALUES (1396647371692658691, 'ChenYiWenXianHuiZong_address', 1395617134758379522, 1395613872445505538);
INSERT INTO `g_metadata_constraint` VALUES (1396647371692658692, 'ChenYiWenXianHuiZong_address', 1395617679954984961, 1395613872445505538);
INSERT INTO `g_metadata_constraint` VALUES (1396729237099565058, 'ChenYiWenXianHuiZong_themePerson', 1395667560849547265, 1395667123966648321);
INSERT INTO `g_metadata_constraint` VALUES (1396729305034706945, 'ChenYiWenXianHuiZong_startTime', 1395614346838065154, 1395613872445505538);
INSERT INTO `g_metadata_constraint` VALUES (1396729305043095553, 'ChenYiWenXianHuiZong_startTime', 1395617134758379522, 1395613872445505538);
INSERT INTO `g_metadata_constraint` VALUES (1396729305051484162, 'ChenYiWenXianHuiZong_startTime', 1395617368272060417, 1395613872445505538);
INSERT INTO `g_metadata_constraint` VALUES (1396729305051484163, 'ChenYiWenXianHuiZong_startTime', 1395617679954984961, 1395613872445505538);
INSERT INTO `g_metadata_constraint` VALUES (1396729305059872769, 'ChenYiWenXianHuiZong_startTime', 1395617845739044865, 1395613872445505538);
INSERT INTO `g_metadata_constraint` VALUES (1396729305059872770, 'ChenYiWenXianHuiZong_startTime', 1395617972151173121, 1395613872445505538);
INSERT INTO `g_metadata_constraint` VALUES (1396729305059872771, 'ChenYiWenXianHuiZong_startTime', 1395617442590932993, 1395613872445505538);
INSERT INTO `g_metadata_constraint` VALUES (1396729305068261377, 'ChenYiWenXianHuiZong_startTime', 1395614432250871810, 1395613872445505538);
INSERT INTO `g_metadata_constraint` VALUES (1396729305068261378, 'ChenYiWenXianHuiZong_startTime', 1395614165837070337, 1395613872445505538);
INSERT INTO `g_metadata_constraint` VALUES (1399661902102605825, 'iso_duty_name', 1395614346838065154, 1395613872445505538);
INSERT INTO `g_metadata_constraint` VALUES (1399661902110994433, 'iso_duty_name', 1395617134758379522, 1395613872445505538);
INSERT INTO `g_metadata_constraint` VALUES (1399661902119383042, 'iso_duty_name', 1395617368272060417, 1395613872445505538);
INSERT INTO `g_metadata_constraint` VALUES (1399661902127771649, 'iso_duty_name', 1395617679954984961, 1395613872445505538);
INSERT INTO `g_metadata_constraint` VALUES (1399661902136160258, 'iso_duty_name', 1395617845739044865, 1395613872445505538);
INSERT INTO `g_metadata_constraint` VALUES (1399661902136160259, 'iso_duty_name', 1395617972151173121, 1395613872445505538);
INSERT INTO `g_metadata_constraint` VALUES (1399661902144548865, 'iso_duty_name', 1395617442590932993, 1395613872445505538);
INSERT INTO `g_metadata_constraint` VALUES (1399661902144548866, 'iso_duty_name', 1395614432250871810, 1395613872445505538);
INSERT INTO `g_metadata_constraint` VALUES (1399661902152937474, 'iso_duty_name', 1395614165837070337, 1395613872445505538);
INSERT INTO `g_metadata_constraint` VALUES (1401056187239104513, 'ncorporate_entities_names', 1395614346838065154, 1395613872445505538);
INSERT INTO `g_metadata_constraint` VALUES (1401056187247493121, 'ncorporate_entities_names', 1395617134758379522, 1395613872445505538);
INSERT INTO `g_metadata_constraint` VALUES (1401056187255881729, 'ncorporate_entities_names', 1395617368272060417, 1395613872445505538);
INSERT INTO `g_metadata_constraint` VALUES (1401056187255881730, 'ncorporate_entities_names', 1395617679954984961, 1395613872445505538);
INSERT INTO `g_metadata_constraint` VALUES (1401056187264270337, 'ncorporate_entities_names', 1395617845739044865, 1395613872445505538);
INSERT INTO `g_metadata_constraint` VALUES (1401056187272658945, 'ncorporate_entities_names', 1395617972151173121, 1395613872445505538);
INSERT INTO `g_metadata_constraint` VALUES (1401056187272658946, 'ncorporate_entities_names', 1395617442590932993, 1395613872445505538);
INSERT INTO `g_metadata_constraint` VALUES (1401056187281047554, 'ncorporate_entities_names', 1395614432250871810, 1395613872445505538);
INSERT INTO `g_metadata_constraint` VALUES (1401056187281047555, 'ncorporate_entities_names', 1395614165837070337, 1395613872445505538);
INSERT INTO `g_metadata_constraint` VALUES (1401753193284202497, 'm_t_ncorporate_entities2_titleInfo', 1395667560849547265, 1395667123966648321);

-- ----------------------------
-- Table structure for g_metadata_table
-- ----------------------------
DROP TABLE IF EXISTS `g_metadata_table`;
CREATE TABLE `g_metadata_table`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `type_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联元数据类型code',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '唯一标识',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `version` int(10) NOT NULL COMMENT '版本号',
  `status` tinyint(4) NOT NULL COMMENT '状态（字典 0正常 1停用 2删除）',
  `sort` int(11) NOT NULL COMMENT '排序',
  `data_source_table_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'excel类型：数据sheet页名称，as类型：path路径',
  `excel_start_row` int(10) NULL DEFAULT NULL COMMENT 'excel类型数据起始行',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(50) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(50) NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `code`(`code`) USING BTREE,
  INDEX `fk_type_code`(`type_code`) USING BTREE,
  CONSTRAINT `fk_type_code` FOREIGN KEY (`type_code`) REFERENCES `sys_dict_data` (`code`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of g_metadata_table
-- ----------------------------
INSERT INTO `g_metadata_table` VALUES (1386928144257675266, 'MARC_MATADATA', 'm_t_ZhongWenShuMu', 'MARC_ZhongWenShuMu', 4, 0, 6, NULL, NULL, '地磁及其应用', '2021-04-27 14:20:12', 1377879897444212737, '2021-05-30 13:29:03', 1377879897444212737);
INSERT INTO `g_metadata_table` VALUES (1387277777974632449, 'EXCEL_MATADATA', 'm_t_ChenYiWenXianHuiZong', '陈毅文献汇总', 5, 0, 1, '大事记-事件拆解', 2, '20210331陈毅文献汇总', '2021-04-28 13:29:31', 1377879897444212737, '2021-05-30 13:28:04', 1377879897444212737);
INSERT INTO `g_metadata_table` VALUES (1391599568234762242, 'MARC_MATADATA', 'm_t_iso', 'isoMarc数据', 35, 0, 3, NULL, NULL, NULL, '2021-05-10 11:42:46', 1377879897444212737, '2021-05-30 13:28:32', 1377879897444212737);
INSERT INTO `g_metadata_table` VALUES (1392005857062481921, 'AS_MATADATA', 'm_t_ncorporate_entities', 'AS元数据表', 8, 0, 2, '/repositories/2/archival_objects/21495/children', NULL, NULL, '2021-05-11 14:37:13', 1377879897444212737, '2021-06-04 18:13:46', 1377879897444212737);
INSERT INTO `g_metadata_table` VALUES (1392298071743377410, 'AS_MATADATA', 'm_t_ncorporate_entities2', 'AS元数据表2', 8, 0, 7, '/repositories/2/resources/26/tree', NULL, NULL, '2021-05-12 09:58:23', 1377879897444212737, '2021-06-04 18:37:30', 1377879897444212737);
INSERT INTO `g_metadata_table` VALUES (1394948626038939650, 'ES_MATADATA', 'm_t_article', '文章数据', 151, 0, 5, NULL, NULL, NULL, '2021-05-19 17:30:44', 1377879897444212737, '2021-06-08 18:49:35', 1377879897444212737);
INSERT INTO `g_metadata_table` VALUES (1394957691880235010, 'ES_MATADATA', 'm_t_incident', '陈毅事件', 98, 0, 4, NULL, NULL, NULL, '2021-05-19 18:06:45', 1377879897444212737, '2021-06-08 18:47:30', 1377879897444212737);

-- ----------------------------
-- Table structure for g_metadata_table_data_collect
-- ----------------------------
DROP TABLE IF EXISTS `g_metadata_table_data_collect`;
CREATE TABLE `g_metadata_table_data_collect`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `collect_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联数据集code',
  `table_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联元数据表code',
  `version` int(11) NOT NULL COMMENT '版本号',
  `status` tinyint(4) NOT NULL COMMENT '状态（0正常 1停用 2删除）',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(50) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(50) NULL DEFAULT NULL COMMENT '更新人',
  INDEX `fk_collect_code`(`collect_code`) USING BTREE,
  INDEX `fk_metadata_table_code1`(`table_code`) USING BTREE,
  CONSTRAINT `fk_collect_code` FOREIGN KEY (`collect_code`) REFERENCES `g_data_collect` (`code`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_metadata_table_code1` FOREIGN KEY (`table_code`) REFERENCES `g_metadata_table` (`code`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of g_metadata_table_data_collect
-- ----------------------------
INSERT INTO `g_metadata_table_data_collect` VALUES (1391965548538535938, 'dataCollect_ChenYiWenXianHuiZong', 'm_t_ChenYiWenXianHuiZong', 4, 0, '2021-05-11 11:57:03', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_table_data_collect` VALUES (1398515526278918145, 'dataCollect_iso', 'm_t_iso', 35, 0, '2021-05-29 13:44:19', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_table_data_collect` VALUES (1398524736114864130, 'dataCollect_01', 'm_t_ZhongWenShuMu', 4, 0, '2021-05-29 14:20:55', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_table_data_collect` VALUES (1400763488808206338, 'dataSource_AS', 'm_t_ncorporate_entities2', 8, 0, '2021-06-04 18:36:55', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_table_data_collect` VALUES (1402216279438807041, 'ES_datacollect', 'm_t_incident', 98, 0, '2021-06-08 18:49:48', 1377879897444212737, NULL, NULL);
INSERT INTO `g_metadata_table_data_collect` VALUES (1402216279510110210, 'ES_datacollect', 'm_t_article', 151, 0, '2021-06-08 18:49:48', 1377879897444212737, NULL, NULL);

-- ----------------------------
-- Table structure for sys_app
-- ----------------------------
DROP TABLE IF EXISTS `sys_app`;
CREATE TABLE `sys_app`  (
  `id` bigint(20) NOT NULL COMMENT '主键id',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '应用名称',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编码',
  `active` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否默认激活（Y-是，N-否）',
  `status` tinyint(4) NOT NULL COMMENT '状态（字典 0正常 1停用 2删除）',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统应用表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_app
-- ----------------------------
INSERT INTO `sys_app` VALUES (1265476890672672821, '系统应用', 'system', 'Y', 0, '2020-03-25 19:07:00', 1265476890672672808, '2020-08-15 15:23:05', 1280709549107552257);
INSERT INTO `sys_app` VALUES (1265476890672672822, '业务应用', 'business', 'N', 2, '2020-03-26 08:40:33', 1265476890672672808, '2020-09-23 22:00:01', 1265476890672672808);
INSERT INTO `sys_app` VALUES (1265476890672672823, '在线办公', 'office', 'N', 2, '2020-04-02 15:48:43', 1265476890672672808, '2020-12-01 19:22:50', 1265476890672672808);
INSERT INTO `sys_app` VALUES (1290262474351808514, '高级体验', 'experience', 'N', 2, '2020-08-03 20:25:20', 1265476890672672808, '2020-12-01 19:22:53', 1265476890672672808);

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编码',
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '值',
  `sys_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '是否是系统参数（Y-是，N-否）',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `status` tinyint(4) NOT NULL COMMENT '状态（字典 0正常 1停用 2删除）',
  `group_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'DEFAULT' COMMENT '常量所属分类的编码，来自于“常量的分类”字典',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统参数配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (1265117443880853504, '验证码开关', 'XIAONUO_KAPTCHA_OPEN', 'N', 'Y', '登录验证码开关 N关闭 Y打开', 0, 'DEFAULT', '2020-04-14 23:30:14', 1265476890672672808, '2021-04-07 14:08:18', 1265476890672672808);
INSERT INTO `sys_config` VALUES (1265117443880853506, 'jwt密钥', 'XIAONUO_JWT_SECRET', 'xiaonuo', 'Y', '（重要）jwt密钥，默认为空，自行设置', 0, 'DEFAULT', '2020-05-26 06:35:19', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_config` VALUES (1265117443880853507, '默认密码', 'XIAONUO_DEFAULT_PASSWORD', '123456', 'Y', '默认密码', 0, 'DEFAULT', '2020-05-26 06:37:56', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_config` VALUES (1265117443880853508, 'token过期时间', 'XIAONUO_TOKEN_EXPIRE', '86400', 'Y', 'token过期时间（单位：秒）', 0, 'DEFAULT', '2020-05-27 11:54:49', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_config` VALUES (1265117443880853509, 'session会话过期时间', 'XIAONUO_SESSION_EXPIRE', '7200', 'Y', 'session会话过期时间（单位：秒）', 0, 'DEFAULT', '2020-05-27 11:54:49', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_config` VALUES (1265117443880853519, '阿里云短信keyId', 'XIAONUO_ALIYUN_SMS_ACCESSKEY_ID', '你的keyId', 'Y', '阿里云短信keyId', 0, 'ALIYUN_SMS', '2020-06-07 16:27:11', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_config` VALUES (1269547042242371585, '阿里云短信secret', 'XIAONUO_ALIYUN_SMS_ACCESSKEY_SECRET', '你的secret', 'Y', '阿里云短信secret', 0, 'ALIYUN_SMS', '2020-06-07 16:29:37', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_config` VALUES (1269547130041737217, '阿里云短信签名', 'XIAONUO_ALIYUN_SMS_SIGN_NAME', 'XiaoNuo快速开发平台', 'Y', '阿里云短信签名', 0, 'ALIYUN_SMS', '2020-06-07 16:29:58', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_config` VALUES (1269547279530926081, '阿里云短信-登录模板号', 'XIAONUO_ALIYUN_SMS_LOGIN_TEMPLATE_CODE', 'SMS_1877123456', 'Y', '阿里云短信-登录模板号', 0, 'ALIYUN_SMS', '2020-06-07 16:30:33', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_config` VALUES (1269547410879750145, '阿里云短信默认失效时间', 'XIAONUO_ALIYUN_SMS_INVALIDATE_MINUTES', '5', 'Y', '阿里云短信默认失效时间（单位：分钟）', 0, 'ALIYUN_SMS', '2020-06-07 16:31:04', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_config` VALUES (1269575927357071361, '腾讯云短信secretId', 'XIAONUO_TENCENT_SMS_SECRET_ID', '你的secretId', 'Y', '腾讯云短信secretId', 0, 'TENCENT_SMS', '2020-06-07 18:24:23', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_config` VALUES (1269575991693500418, '腾讯云短信secretKey', 'XIAONUO_TENCENT_SMS_SECRET_KEY', '你的secretkey', 'Y', '腾讯云短信secretKey', 0, 'TENCENT_SMS', '2020-06-07 18:24:39', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_config` VALUES (1269576044084551682, '腾讯云短信sdkAppId', 'XIAONUO_TENCENT_SMS_SDK_APP_ID', '1400375123', 'Y', '腾讯云短信sdkAppId', 0, 'TENCENT_SMS', '2020-06-07 18:24:51', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_config` VALUES (1269576089294954497, '腾讯云短信签名', 'XIAONUO_TENCENT_SMS_SIGN', 'XiaoNuo快速开发平台', 'Y', '腾讯云短信签名', 0, 'TENCENT_SMS', '2020-06-07 18:25:02', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_config` VALUES (1270378172860403713, '邮箱host', 'XIAONUO_EMAIL_HOST', 'smtp.126.com', 'Y', '邮箱host', 0, 'EMAIL', '2020-06-09 23:32:14', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_config` VALUES (1270378295543795714, '邮箱用户名', 'XIAONUO_EMAIL_USERNAME', 'test@126.com', 'Y', '邮箱用户名', 0, 'EMAIL', '2020-06-09 23:32:43', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_config` VALUES (1270378340510928897, '邮箱密码', 'XIAONUO_EMAIL_PASSWORD', '你的邮箱密码', 'Y', '邮箱密码', 0, 'EMAIL', '2020-06-09 23:32:54', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_config` VALUES (1270378527358783489, '邮箱端口', 'XIAONUO_EMAIL_PORT', '465', 'Y', '邮箱端口', 0, 'EMAIL', '2020-06-09 23:33:38', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_config` VALUES (1270378790035460097, '邮箱是否开启ssl', 'XIAONUO_EMAIL_SSL', 'true', 'Y', '邮箱是否开启ssl', 0, 'EMAIL', '2020-06-09 23:34:41', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_config` VALUES (1270380786649972737, '邮箱发件人', 'XIAONUO_EMAIL_FROM', 'test@126.com', 'Y', '邮箱发件人', 0, 'EMAIL', '2020-06-09 23:42:37', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_config` VALUES (1270380786649972738, 'win本地上传文件路径', 'XIAONUO_FILE_UPLOAD_PATH_FOR_WINDOWS', 'C:\\XNXX\\PROJECT\\XiaoNuo\\img', 'Y', 'win本地上传文件路径', 0, 'FILE_PATH', '2020-06-09 23:42:37', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_config` VALUES (1270380786649972739, 'linux/mac本地上传文件路径', 'XIAONUO_FILE_UPLOAD_PATH_FOR_LINUX', 'C:\\XNXX\\PROJECT\\XiaoNuo\\img', 'Y', 'linux/mac本地上传文件路径', 0, 'FILE_PATH', '2020-06-09 23:42:37', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_config` VALUES (1270380786649982740, 'XiaoNuo演示环境', 'XIAONUO_DEMO_ENV_FLAG', 'false', 'Y', 'XiaoNuo演示环境的开关，true-打开，false-关闭，如果演示环境开启，则只能读数据不能写数据', 0, 'DEFAULT', '2020-06-09 23:42:37', 1265476890672672808, '2020-09-03 14:38:17', 1265476890672672808);
INSERT INTO `sys_config` VALUES (1270380786649982741, 'XiaoNuo放开XSS过滤的接口', 'XIAONUO_UN_XSS_FILTER_URL', '/demo/xssfilter,/demo/unxss', 'Y', '多个url可以用英文逗号隔开', 0, 'DEFAULT', '2020-06-09 23:42:37', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_config` VALUES (1270380786649982742, '单用户登陆的开关', 'XIAONUO_ENABLE_SINGLE_LOGIN', 'false', 'Y', '单用户登陆的开关，true-打开，false-关闭，如果一个人登录两次，就会将上一次登陆挤下去', 0, 'DEFAULT', '2020-06-09 23:42:37', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_config` VALUES (1280694183769792514, 'druid监控登录账号', 'XIAONUO_DRUID_USERNAME', '', 'Y', 'druid监控登录账号', 0, 'DEFAULT', '2020-07-08 10:44:22', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_config` VALUES (1280694281648070658, 'druid监控界面登录密码', 'XIAONUO_DRUID_PASSWORD', '', 'Y', 'druid监控登录密码', 0, 'DEFAULT', '2020-07-08 10:44:46', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_config` VALUES (1280694281648070659, '阿里云定位api接口地址', 'XIAONUO_IP_GEO_API', 'http://api01.aliyun.venuscn.com/ip?ip=%s', 'Y', '阿里云定位api接口地址', 0, 'DEFAULT', '2020-07-20 10:44:46', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_config` VALUES (1280694281648070660, '阿里云定位appCode', 'XIAONUO_IP_GEO_APP_CODE', '461535aabeae4f34861884d392f5d452', 'Y', '阿里云定位appCode', 0, 'DEFAULT', '2020-07-20 10:44:46', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_config` VALUES (1288309751255412737, 'Oauth用户登录的开关', 'XIAONUO_ENABLE_OAUTH_LOGIN', 'true', 'Y', 'Oauth用户登录的开关', 0, 'OAUTH', '2020-07-29 11:05:55', 1265476890672672808, '2021-05-21 12:58:28', 1265476890672672808);
INSERT INTO `sys_config` VALUES (1288310043346743297, 'Oauth码云登录ClientId', 'XIAONUO_OAUTH_GITEE_CLIENT_ID', '你的clientId', 'Y', 'Oauth码云登录ClientId', 0, 'OAUTH', '2020-07-29 11:07:05', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_config` VALUES (1288310157876408321, 'Oauth码云登录ClientSecret', 'XIAONUO_OAUTH_GITEE_CLIENT_SECRET', '你的clientSecret', 'Y', 'Oauth码云登录ClientSecret', 0, 'OAUTH', '2020-07-29 11:07:32', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_config` VALUES (1288310280056483841, 'Oauth码云登录回调地址', 'XIAONUO_OAUTH_GITEE_REDIRECT_URI', 'http://localhost:9005/oauth/callback/gitee', 'Y', 'Oauth码云登录回调地址', 0, 'OAUTH', '2020-07-29 11:08:01', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_config` VALUES (1288358228593221633, '前端项目地址', 'XIAONUO_WEB_URL', 'http://localhost:9000', 'Y', '前端项目地址', 0, 'DEFAULT', '2020-07-29 14:18:33', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_config` VALUES (1288358228593221634, '支付宝支付跳转地址', 'XIAONUO_ALIPAY_RETURN_URL', 'http://localhost:9005/pay/index', 'Y', '支付宝支付跳转地址', 0, 'DEFAULT', '2020-07-29 14:18:33', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_config` VALUES (1288358228593221635, '是否开启多租户', 'XIAONUO_TENANT_OPEN', 'false', 'Y', '是否开启多租户', 0, 'DEFAULT', '2020-09-03 17:45:58', 1265476890672672808, '2020-09-23 22:23:38', 1265476890672672808);
INSERT INTO `sys_config` VALUES (1400394439032655873, '登录验证码开关', 'XIAONUO_CAPTCHA_OPEN', 'false', 'Y', NULL, 0, 'OAUTH', '2021-06-03 18:10:27', 1377879897444212737, NULL, NULL);

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父级',
  `type_id` bigint(20) NOT NULL COMMENT '字典类型id',
  `value` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '值',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编码',
  `sort` int(11) NOT NULL COMMENT '排序',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `status` tinyint(4) NOT NULL COMMENT '状态（字典 0正常 1停用 2删除）',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `code`(`code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统字典值表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (1265216536659087357, NULL, 1265216211667636234, '男', '1', 100, '男性', 0, '2020-04-01 10:23:29', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265216536659087358, NULL, 1265216211667636234, '女', '2', 100, '女性', 0, '2020-04-01 10:23:49', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265216536659087359, NULL, 1265216211667636234, '未知', '3', 100, '未知性别', 0, '2020-04-01 10:24:01', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265216536659087361, NULL, 1265216211667636235, '默认常量', 'DEFAULT', 100, '默认常量，都以XIAONUO_开头的', 0, '2020-04-14 23:25:45', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265216536659087363, NULL, 1265216211667636235, '阿里云短信', 'ALIYUN_SMS', 100, '阿里云短信配置', 0, '2020-04-14 23:25:45', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265216536659087364, NULL, 1265216211667636235, '腾讯云短信', 'TENCENT_SMS', 100, '腾讯云短信', 0, '2020-04-14 23:25:45', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265216536659087365, NULL, 1265216211667636235, '邮件配置', 'EMAIL', 100, '邮箱配置', 0, '2020-04-14 23:25:45', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265216536659087366, NULL, 1265216211667636235, '文件上传路径', 'FILE_PATH', 100, '文件上传路径', 0, '2020-04-14 23:25:45', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265216536659087367, NULL, 1265216211667636235, 'Oauth配置', 'OAUTH', 100, 'Oauth配置', 0, '2020-04-14 23:25:45', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265216617500102656, NULL, 1265216211667636226, '正常', '0', 100, '正常', 0, '2020-05-26 17:41:44', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265216617500102657, NULL, 1265216211667636226, '停用', '1', 100, '停用', 0, '2020-05-26 17:42:03', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265216938389524482, NULL, 1265216211667636226, '删除', '2', 100, '删除', 0, '2020-05-26 17:43:19', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265217669028892673, NULL, 1265217074079453185, '否', 'N', 100, '否', 0, '2020-05-26 17:46:14', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265217706584690689, NULL, 1265217074079453185, '是', 'Y', 100, '是', 0, '2020-05-26 17:46:23', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265220776437731330, NULL, 1265217846770913282, '登录', '1', 100, '登录', 0, '2020-05-26 17:58:34', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265220806070489090, NULL, 1265217846770913282, '登出', '2', 100, '登出', 0, '2020-05-26 17:58:41', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265221129564573697, NULL, 1265221049302372354, '目录', '0', 100, '目录', 0, '2020-05-26 17:59:59', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265221163119005697, NULL, 1265221049302372354, '菜单', '1', 100, '菜单', 0, '2020-05-26 18:00:07', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265221188091891713, NULL, 1265221049302372354, '按钮', '2', 100, '按钮', 0, '2020-05-26 18:00:13', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265466389204967426, NULL, 1265466149622128641, '未发送', '0', 100, '未发送', 0, '2020-05-27 10:14:33', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265466432670539778, NULL, 1265466149622128641, '发送成功', '1', 100, '发送成功', 0, '2020-05-27 10:14:43', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265466486097584130, NULL, 1265466149622128641, '发送失败', '2', 100, '发送失败', 0, '2020-05-27 10:14:56', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265466530477514754, NULL, 1265466149622128641, '失效', '3', 100, '失效', 0, '2020-05-27 10:15:07', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265466835009150978, NULL, 1265466752209395713, '无', '0', 100, '无', 0, '2020-05-27 10:16:19', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265466874758569986, NULL, 1265466752209395713, '组件', '1', 100, '组件', 0, '2020-05-27 10:16:29', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265466925476093953, NULL, 1265466752209395713, '内链', '2', 100, '内链', 0, '2020-05-27 10:16:41', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265466962209808385, NULL, 1265466752209395713, '外链', '3', 100, '外链', 0, '2020-05-27 10:16:50', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265467428423475202, NULL, 1265467337566461954, '系统权重', '1', 100, '系统权重', 0, '2020-05-27 10:18:41', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265467503090475009, NULL, 1265467337566461954, '业务权重', '2', 100, '业务权重', 0, '2020-05-27 10:18:59', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265467709110493186, NULL, 1265467629167058946, '事假', '1', 100, '事假', 0, '2020-05-27 10:19:48', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265467745013735426, NULL, 1265467629167058946, '病假', '2', 100, '病假', 0, '2020-05-27 10:19:56', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265467785253888001, NULL, 1265467629167058946, '婚假', '3', 100, '婚假', 0, '2020-05-27 10:20:06', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265467823426248706, NULL, 1265467629167058946, '丧假', '4', 100, '丧假', 0, '2020-05-27 10:20:15', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265467855781109762, NULL, 1265467629167058946, '产假', '5', 100, '产假', 0, '2020-05-27 10:20:23', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265467895782187010, NULL, 1265467629167058946, '其他', '6', 100, '其他', 0, '2020-05-27 10:20:32', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265468138431062018, NULL, 1265468028632571905, '全部数据', '1', 100, '全部数据', 0, '2020-05-27 10:21:30', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265468194928336897, NULL, 1265468028632571905, '本部门及以下数据', '2', 100, '本部门及以下数据', 0, '2020-05-27 10:21:44', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265468241992622082, NULL, 1265468028632571905, '本部门数据', '3', 100, '本部门数据', 0, '2020-05-27 10:21:55', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265468273634451457, NULL, 1265468028632571905, '仅本人数据', '4', 100, '仅本人数据', 0, '2020-05-27 10:22:02', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265468302046666753, NULL, 1265468028632571905, '自定义数据', '5', 100, '自定义数据', 0, '2020-05-27 10:22:09', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265468508100239362, NULL, 1265468437904367618, 'app', '1', 100, 'app', 0, '2020-05-27 10:22:58', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265468543433056258, NULL, 1265468437904367618, 'pc', '2', 100, 'pc', 0, '2020-05-27 10:23:07', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265468576874242050, NULL, 1265468437904367618, '其他', '3', 100, '其他', 0, '2020-05-27 10:23:15', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265468839764828161, NULL, 1265468761230680066, 'Integer', '1', 100, 'Integer', 0, '2020-05-27 10:24:17', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265468871641538562, NULL, 1265468761230680066, 'String', '2', 100, 'String', 0, '2020-05-27 10:24:25', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265468898896125954, NULL, 1265468761230680066, 'Long', '3', 100, 'Long', 0, '2020-05-27 10:24:31', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265468922275176450, NULL, 1265468761230680066, 'Double', '4', 100, 'Double', 0, '2020-05-27 10:24:37', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265468946648276993, NULL, 1265468761230680066, 'Boolean', '5', 100, 'Boolean', 0, '2020-05-27 10:24:43', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265468970450952193, NULL, 1265468761230680066, 'Date', '6', 100, 'Date', 0, '2020-05-27 10:24:48', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265468970450952194, NULL, 1265468761230680066, 'List', '7', 100, 'List', 0, '2020-05-27 10:24:48', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265469305756196865, NULL, 1265469198583341057, '流程脚本', '1', 100, '流程脚本', 0, '2020-05-27 10:26:08', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265469330859106306, NULL, 1265469198583341057, '系统脚本', '2', 100, '系统脚本', 0, '2020-05-27 10:26:14', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265469526330449922, NULL, 1265469441454514178, 'groovy', '1', 100, 'groovy', 0, '2020-05-27 10:27:01', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265469753078718464, NULL, 1265469702042427393, '启动', '1', 100, '启动', 0, '2020-05-27 10:30:05', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265469753078718465, NULL, 1265469702042427393, '全局', '2', 100, '全局', 0, '2020-05-27 10:27:55', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265469779460890626, NULL, 1265469702042427393, '节点', '3', 100, '节点', 0, '2020-05-27 10:28:01', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265470046877130753, NULL, 1265469962873610241, '流程启动', 'PROCESS_STARTED', 100, '流程启动', 0, '2020-05-27 10:29:05', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265470074517594113, NULL, 1265469962873610241, '流程完成', 'PROCESS_COMPLETED', 100, '流程完成', 0, '2020-05-27 10:29:12', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265470103152107521, NULL, 1265469962873610241, '流程取消', 'PROCESS_CANCELLED', 100, '流程取消', 0, '2020-05-27 10:29:19', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265470125725851649, NULL, 1265469962873610241, '活动开始', 'ACTIVITY_STARTED', 100, '活动开始', 0, '2020-05-27 10:29:24', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265470153416646657, NULL, 1265469962873610241, '活动完成', 'ACTIVITY_COMPLETED', 100, '活动完成', 0, '2020-05-27 10:29:31', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265470179165478913, NULL, 1265469962873610241, '活动取消', 'ACTIVITY_CANCELLED', 100, '活动取消', 0, '2020-05-27 10:29:37', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265470207363784705, NULL, 1265469962873610241, '任务分配', 'TASK_ASSIGNED', 100, '任务分配', 0, '2020-05-27 10:29:43', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265470236853936130, NULL, 1265469962873610241, '任务创建', 'TASK_CREATED', 100, '任务创建', 0, '2020-05-27 10:29:50', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265470266780295170, NULL, 1265469962873610241, '任务完成', 'TASK_COMPLETED', 100, '任务完成', 0, '2020-05-27 10:29:58', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265470296446607361, NULL, 1265469962873610241, '连接线', 'SEQUENCEFLOW_TAKEN', 100, '连接线', 0, '2020-05-27 10:30:05', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265470296446607362, NULL, 1265469962873610242, '全局', '1', 100, '全局', 0, '2020-05-27 10:30:05', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265470296446607363, NULL, 1265469962873610242, '节点', '2', 100, '节点', 0, '2020-05-27 10:30:05', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265470526197997569, NULL, 1265470456631271426, '草稿', '0', 100, '草稿', 0, '2020-05-27 10:30:59', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265470552823439361, NULL, 1265470456631271426, '审核中', '1', 100, '审核中', 0, '2020-05-27 10:31:06', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265470575615287297, NULL, 1265470456631271426, '已退回', '2', 100, '已退回', 0, '2020-05-27 10:31:11', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1265470607588466690, NULL, 1265470456631271426, '已完成', '3', 100, '已完成', 0, '2020-05-27 10:31:19', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1275617233011335170, NULL, 1275617093517172738, '其它', '0', 100, '其它', 0, '2020-06-24 10:30:23', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1275617295355469826, NULL, 1275617093517172738, '增加', '1', 100, '增加', 0, '2020-06-24 10:30:38', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1275617348610547714, NULL, 1275617093517172738, '删除', '2', 100, '删除', 0, '2020-06-24 10:30:50', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1275617395515449346, NULL, 1275617093517172738, '编辑', '3', 100, '编辑', 0, '2020-06-24 10:31:02', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1275617433612312577, NULL, 1275617093517172738, '更新', '4', 100, '更新', 0, '2020-06-24 10:31:11', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1275617472707420161, NULL, 1275617093517172738, '查询', '5', 100, '查询', 0, '2020-06-24 10:31:20', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1275617502973517826, NULL, 1275617093517172738, '详情', '6', 100, '详情', 0, '2020-06-24 10:31:27', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1275617536959963137, NULL, 1275617093517172738, '树', '7', 100, '树', 0, '2020-06-24 10:31:35', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1275617619524837377, NULL, 1275617093517172738, '导入', '8', 100, '导入', 0, '2020-06-24 10:31:55', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1275617651816783873, NULL, 1275617093517172738, '导出', '9', 100, '导出', 0, '2020-06-24 10:32:03', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1275617683475390465, NULL, 1275617093517172738, '授权', '10', 100, '授权', 0, '2020-06-24 10:32:10', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1275617709928865793, NULL, 1275617093517172738, '强退', '11', 100, '强退', 0, '2020-06-24 10:32:17', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1275617739091861505, NULL, 1275617093517172738, '清空', '12', 100, '清空', 0, '2020-06-24 10:32:23', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1275617788601425921, NULL, 1275617093517172738, '修改状态', '13', 100, '修改状态', 0, '2020-06-24 10:32:35', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1277774590944317441, NULL, 1277774529430654977, '阿里云', '1', 100, '阿里云', 0, '2020-06-30 09:22:57', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1277774666055913474, NULL, 1277774529430654977, '腾讯云', '2', 100, '腾讯云', 0, '2020-06-30 09:23:15', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1277774695168577538, NULL, 1277774529430654977, 'minio', '3', 100, 'minio', 0, '2020-06-30 09:23:22', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1277774726835572737, NULL, 1277774529430654977, '本地', '4', 100, '本地', 0, '2020-06-30 09:23:29', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1278607123583868929, NULL, 1278606951432855553, '运行', '1', 100, '运行', 0, '2020-07-02 16:31:08', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1278607162943217666, NULL, 1278606951432855553, '停止', '2', 100, '停止', 0, '2020-07-02 16:31:18', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1278939265862004738, NULL, 1278911800547147777, '通知', '1', 100, '通知', 0, '2020-07-03 14:30:57', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1278939319922388994, NULL, 1278911800547147777, '公告', '2', 100, '公告', 0, '2020-07-03 14:31:10', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1278939399001796609, NULL, 1278911952657776642, '草稿', '0', 100, '草稿', 0, '2020-07-03 14:31:29', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1278939432686252034, NULL, 1278911952657776642, '发布', '1', 100, '发布', 0, '2020-07-03 14:31:37', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1278939458804183041, NULL, 1278911952657776642, '撤回', '2', 100, '撤回', 0, '2020-07-03 14:31:43', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1278939485878415362, NULL, 1278911952657776642, '删除', '3', 100, '删除', 0, '2020-07-03 14:31:50', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1278992343223025665, NULL, 1278992276965605377, '委托中', '0', 90, '委托中', 0, '2020-07-03 18:01:52', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1278992370066571266, NULL, 1278992276965605377, '委托结束', '1', 100, '委托结束', 0, '2020-07-03 18:01:58', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1278992396788482050, NULL, 1278992276965605377, '未委托', '2', 100, '未委托', 0, '2020-07-03 18:02:05', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1291390260160299009, NULL, 1291390159941599233, '是', 'true', 100, '是', 2, '2020-08-06 23:06:46', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1291390315437031426, NULL, 1291390159941599233, '否', 'false', 100, '否', 2, '2020-08-06 23:06:59', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1291391148769738754, NULL, 1291391077990858754, '是', 'true', 100, '是', 0, '2020-08-06 23:10:17', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1291391205719998465, NULL, 1291391077990858754, '否', 'false', 100, '否', 0, '2020-08-06 23:10:31', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1291393684314587138, NULL, 1291393441594408961, '是', 'true', 100, '已结束', 0, '2020-08-06 23:20:22', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1291393766048989186, NULL, 1291393441594408961, '否', 'false', 100, '未结束', 0, '2020-08-06 23:20:41', 1265476890672672808, '2021-04-12 16:45:12', 1377879897444212737);
INSERT INTO `sys_dict_data` VALUES (1300767954291433474, NULL, 1300767512828354562, 'Mysql', 'com.mysql.cj.jdbc.Driver', 100, 'Mysql数据库驱动', 0, '2020-09-01 20:10:22', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1300768214854180866, NULL, 1300767512828354562, 'Oracle', 'oracle.jdbc.OracleDriver', 100, 'Oracle数据库驱动', 0, '2020-09-01 20:11:24', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1300768392747196417, NULL, 1300767512828354562, 'Sqlserver', 'com.microsoft.jdbc.sqlserver.SQLServerDriver', 100, 'Sqlserver数据库', 0, '2020-09-01 20:12:07', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1300768392747196418, NULL, 1300767512828354563, '未支付', '0', 100, '未支付', 0, '2020-09-01 20:12:07', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1300768392747196419, NULL, 1300767512828354563, '已支付', '1', 100, '已支付', 0, '2020-09-01 20:12:07', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1300768392747196420, NULL, 1300767512828354563, '已退款', '2', 100, '已退款', 0, '2020-09-01 20:12:07', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1300768392747196421, NULL, 1300767512828354563, '已关闭', '3', 100, '已关闭', 0, '2020-09-01 20:12:07', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1300768392747196422, NULL, 1300767512828354563, '已关闭有退款', '4', 100, '已关闭有退款', 0, '2020-09-01 20:12:07', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1381525557664595970, NULL, 1291393441594408961, '测试1', 'ceshi1', 10, '测试子级字11', 2, '2021-04-12 16:32:15', 1377879897444212737, '2021-04-15 11:14:42', 1377879897444212737);
INSERT INTO `sys_dict_data` VALUES (1381532093782736897, NULL, 1291393441594408961, '测试2', 'ceshi2', 100, '测试数据词典', 2, '2021-04-12 16:58:13', 1377879897444212737, '2021-04-15 11:23:32', 1377879897444212737);
INSERT INTO `sys_dict_data` VALUES (1381533697076412417, NULL, 1381533149530996737, 'AS元数据', 'AS_MATADATA', 100, NULL, 0, '2021-04-12 17:04:36', 1377879897444212737, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1381533780593393665, NULL, 1381533149530996737, 'MARC元数据', 'MARC_MATADATA', 100, NULL, 0, '2021-04-12 17:04:56', 1377879897444212737, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1381534153630597122, NULL, 1381533149530996737, 'Excel元数据', 'EXCEL_MATADATA', 100, NULL, 0, '2021-04-12 17:06:25', 1377879897444212737, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1381534578236768258, NULL, 1381534434753822721, '字符串', 'string', 100, '文本框', 0, '2021-04-12 17:08:06', 1377879897444212737, '2021-04-12 17:10:39', 1377879897444212737);
INSERT INTO `sys_dict_data` VALUES (1381534807782637569, NULL, 1381534434753822721, '大文本', 'string_max', 100, '多行文本框', 0, '2021-04-12 17:09:01', 1377879897444212737, '2021-04-12 17:10:32', 1377879897444212737);
INSERT INTO `sys_dict_data` VALUES (1381535350110339074, NULL, 1381534434753822721, '日期', 'date', 100, '时间框', 0, '2021-04-12 17:11:10', 1377879897444212737, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1381535451797045250, NULL, 1381534434753822721, 'json', 'json', 100, 'json传', 0, '2021-04-12 17:11:34', 1377879897444212737, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1381536803021438977, 0, 1291393441594408961, '三级', 'sanjijiedian_1', 10, '三级节点', 2, '2021-04-12 17:16:56', 1377879897444212737, '2021-05-21 13:16:14', 1377879897444212737);
INSERT INTO `sys_dict_data` VALUES (1381574725540032513, NULL, 1381533149530996737, '字符串', 'marc_dataType_string', 100, '文本框', 2, '2021-04-12 19:47:38', 1377879897444212737, '2021-04-14 18:15:59', 1377879897444212737);
INSERT INTO `sys_dict_data` VALUES (1381860238988079106, NULL, 1381533149530996737, '抽取模式1', '123132123123', 100, NULL, 2, '2021-04-13 14:42:09', 1377879897444212737, '2021-04-14 18:17:37', 1377879897444212737);
INSERT INTO `sys_dict_data` VALUES (1381861579600883713, NULL, 1381533149530996737, 'mode-1-name', 'mode-1-name', 100, NULL, 2, '2021-04-13 14:47:29', 1377879897444212737, '2021-04-14 18:17:34', 1377879897444212737);
INSERT INTO `sys_dict_data` VALUES (1381861629274025986, NULL, 1381533149530996737, 'mode-1-sex', 'mode-1-sex', 100, NULL, 2, '2021-04-13 14:47:41', 1377879897444212737, '2021-04-14 18:17:26', 1377879897444212737);
INSERT INTO `sys_dict_data` VALUES (1382274021292236802, 1381536803021438977, 1291393441594408961, '测试', 'ceshi', 100, NULL, 0, '2021-04-14 18:06:23', 1377879897444212737, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1382276973079814145, 1381533780593393665, 1381533149530996737, '字符串', 'marc_string', 100, NULL, 0, '2021-04-14 18:18:07', 1377879897444212737, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1382277062003253250, 1381533697076412417, 1381533149530996737, 'string', 'as_string', 100, NULL, 0, '2021-04-14 18:18:28', 1377879897444212737, '2021-06-07 11:30:26', 1377879897444212737);
INSERT INTO `sys_dict_data` VALUES (1382277132916350977, 1381533697076412417, 1381533149530996737, 'json', 'as_json', 100, NULL, 0, '2021-04-14 18:18:45', 1377879897444212737, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1382277199261851649, 1381533780593393665, 1381533149530996737, 'json', 'marc_json', 100, NULL, 0, '2021-04-14 18:19:00', 1377879897444212737, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1383031865878298625, 1382274021292236802, 1291393441594408961, '四级节点', 'node_4', 100, NULL, 0, '2021-04-16 20:17:47', 1377879897444212737, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1383302309793546241, 1291393766048989186, 1291393441594408961, '二级节点', 'node_2', 100, NULL, 2, '2021-04-17 14:12:26', 1377879897444212737, '2021-05-21 13:16:19', 1377879897444212737);
INSERT INTO `sys_dict_data` VALUES (1383302445315702785, 1383302309793546241, 1291393441594408961, '三级节点', 'node_3', 100, NULL, 0, '2021-04-17 14:12:58', 1377879897444212737, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1383302532947296258, 1383302445315702785, 1291393441594408961, '四级节点', 'node_04', 100, NULL, 0, '2021-04-17 14:13:19', 1377879897444212737, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1383699336696193025, 1291393684314587138, 1291393441594408961, '二级节点否', 'node_02', 100, NULL, 2, '2021-04-18 16:30:04', 1377879897444212737, '2021-05-21 13:16:25', 1377879897444212737);
INSERT INTO `sys_dict_data` VALUES (1383699399522672641, 1383699336696193025, 1291393441594408961, '三级节点否', 'node_003', 100, NULL, 0, '2021-04-18 16:30:19', 1377879897444212737, '2021-04-18 19:47:12', 1377879897444212737);
INSERT INTO `sys_dict_data` VALUES (1383699517223231490, 1383699399522672641, 1291393441594408961, '四级节点', 'node_004', 100, NULL, 0, '2021-04-18 16:30:47', 1377879897444212737, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1387278133840355330, 1381534153630597122, 1381533149530996737, '字符串', 'excel_string', 100, '字符串', 0, '2021-04-28 13:30:56', 1377879897444212737, '2021-04-28 13:31:15', 1377879897444212737);
INSERT INTO `sys_dict_data` VALUES (1394948025687236609, 0, 1381533149530996737, 'ES元数据', 'ES_MATADATA', 100, NULL, 0, '2021-05-19 17:28:21', 1377879897444212737, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1394949253024473089, 1394948025687236609, 1381533149530996737, 'text', 'es_text', 100, NULL, 0, '2021-05-19 17:33:13', 1377879897444212737, '2021-05-20 16:12:36', 1377879897444212737);
INSERT INTO `sys_dict_data` VALUES (1394949359853395970, 1394948025687236609, 1381533149530996737, 'int', 'es_int', 100, NULL, 0, '2021-05-19 17:33:39', 1377879897444212737, '2021-05-20 16:12:57', 1377879897444212737);
INSERT INTO `sys_dict_data` VALUES (1394949421266395138, 1394948025687236609, 1381533149530996737, 'date', 'es_date', 100, NULL, 0, '2021-05-19 17:33:54', 1377879897444212737, '2021-05-20 16:12:42', 1377879897444212737);
INSERT INTO `sys_dict_data` VALUES (1394949515323662338, 0, 1381533149530996737, '长整型', 'es_long', 100, NULL, 2, '2021-05-19 17:34:16', 1377879897444212737, '2021-05-19 17:54:18', 1377879897444212737);
INSERT INTO `sys_dict_data` VALUES (1394954668831432706, 1394948025687236609, 1381533149530996737, 'long', 'es_long', 100, NULL, 0, '2021-05-19 17:54:45', 1377879897444212737, '2021-05-20 16:12:50', 1377879897444212737);
INSERT INTO `sys_dict_data` VALUES (1395278069492101121, 1394948025687236609, 1381533149530996737, 'json', 'es_json', 100, NULL, 0, '2021-05-20 15:19:49', 1377879897444212737, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1395291578590351362, 1394948025687236609, 1381533149530996737, 'keyword', 'es_keyword', 100, NULL, 0, '2021-05-20 16:13:30', 1377879897444212737, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1395613872445505538, 0, 1395609304143806466, '地址约束', 'constraint_address', 1, NULL, 0, '2021-05-21 13:34:11', 1377879897444212737, '2021-05-21 17:04:30', 1377879897444212737);
INSERT INTO `sys_dict_data` VALUES (1395614165837070337, 1395613872445505538, 1395609304143806466, '日本', 'constraint_address_Japan', 3, NULL, 0, '2021-05-21 13:35:21', 1377879897444212737, '2021-05-21 13:46:19', 1377879897444212737);
INSERT INTO `sys_dict_data` VALUES (1395614346838065154, 1395613872445505538, 1395609304143806466, '中国', 'constraint_address_China', 1, NULL, 0, '2021-05-21 13:36:04', 1377879897444212737, '2021-05-21 13:45:44', 1377879897444212737);
INSERT INTO `sys_dict_data` VALUES (1395614432250871810, 1395613872445505538, 1395609304143806466, '美国', 'constraint_address_America', 2, NULL, 0, '2021-05-21 13:36:25', 1377879897444212737, '2021-05-21 13:45:39', 1377879897444212737);
INSERT INTO `sys_dict_data` VALUES (1395617134758379522, 1395614346838065154, 1395609304143806466, '北京', 'constraint_address_China_beijing', 1, NULL, 0, '2021-05-21 13:47:09', 1377879897444212737, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1395617368272060417, 1395614346838065154, 1395609304143806466, '上海', 'constraint_address_China_shanghai', 2, NULL, 0, '2021-05-21 13:48:05', 1377879897444212737, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1395617442590932993, 1395614346838065154, 1395609304143806466, '深圳', 'constraint_address_China_shenzhen', 3, NULL, 0, '2021-05-21 13:48:22', 1377879897444212737, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1395617679954984961, 1395617368272060417, 1395609304143806466, '杨浦区', 'constraint_address_China_shanghai_yangpu', 1, NULL, 0, '2021-05-21 13:49:19', 1377879897444212737, '2021-05-21 17:02:20', 1377879897444212737);
INSERT INTO `sys_dict_data` VALUES (1395617845739044865, 1395617368272060417, 1395609304143806466, '浦东新区', 'constraint_address_China_shanghai_pudong', 2, NULL, 0, '2021-05-21 13:49:58', 1377879897444212737, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1395617972151173121, 1395617368272060417, 1395609304143806466, '松江区', 'constraint_address_China_shanghai_songjiang', 3, NULL, 0, '2021-05-21 13:50:29', 1377879897444212737, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1395667123966648321, 0, 1395609304143806466, '文献类型约束', 'constraint_literatureType', 2, NULL, 0, '2021-05-21 17:05:47', 1377879897444212737, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1395667560849547265, 1395667123966648321, 1395609304143806466, '陈毅专题数据库', 'constraint_literatureType_chenyi', 1, NULL, 0, '2021-05-21 17:07:31', 1377879897444212737, '2021-05-21 17:07:48', 1377879897444212737);
INSERT INTO `sys_dict_data` VALUES (1400391670750068738, 0, 1400391461047451650, '实物资源', 'shiwuziyuan', 1, NULL, 0, '2021-06-03 17:59:27', 1377879897444212737, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1400395400895590402, 0, 1400391461047451650, '书画作品', 'shuhuazuopin', 2, NULL, 0, '2021-06-03 18:14:16', 1377879897444212737, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1400395474656620545, 0, 1400391461047451650, '毅公藏书', 'yigongcangshu', 3, NULL, 0, '2021-06-03 18:14:34', 1377879897444212737, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1400395574510415873, 0, 1400391461047451650, '媒体报道', 'meitibaodao', 4, NULL, 0, '2021-06-03 18:14:58', 1377879897444212737, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1400395638251253762, 0, 1400391461047451650, '研究文献', 'yanjiuwenxian', 5, NULL, 0, '2021-06-03 18:15:13', 1377879897444212737, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1400395747370266626, 0, 1400391461047451650, '毅公著述', 'yigongzhushu', 6, NULL, 0, '2021-06-03 18:15:39', 1377879897444212737, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1400395823891148801, 0, 1400391461047451650, '影音资料', 'yingyinziliao', 7, NULL, 0, '2021-06-03 18:15:57', 1377879897444212737, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1400395929008795650, 0, 1400391461047451650, '毅公手迹', 'yigongshouji', 8, NULL, 0, '2021-06-03 18:16:22', 1377879897444212737, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1400395986302988289, 0, 1400391461047451650, '历史照片', 'lishizhaopian', 9, NULL, 0, '2021-06-03 18:16:36', 1377879897444212737, NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (1400396101994475521, 1400391670750068738, 1400391461047451650, 'cc', '测试', 100, NULL, 2, '2021-06-03 18:17:03', 1377879897444212737, '2021-06-03 18:17:18', 1377879897444212737);

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编码',
  `sort` int(11) NOT NULL COMMENT '排序',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `status` tinyint(4) NOT NULL COMMENT '状态（字典 0正常 1停用 2删除）',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统字典类型表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1265216211667636226, '通用状态', 'common_status', 100, '通用状态', 0, '2020-05-26 17:40:26', 1265476890672672808, '2020-06-08 11:31:47', 1265476890672672808);
INSERT INTO `sys_dict_type` VALUES (1265216211667636234, '性别', 'sex', 100, '性别字典', 0, '2020-04-01 10:12:30', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1265216211667636235, '常量的分类', 'consts_type', 100, '常量的分类，用于区别一组配置', 0, '2020-04-14 23:24:13', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1265217074079453185, '是否', 'yes_or_no', 100, '是否', 0, '2020-05-26 17:43:52', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1265217846770913282, '访问类型', 'vis_type', 100, '访问类型', 0, '2020-05-26 17:46:56', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1265221049302372354, '菜单类型', 'menu_type', 100, '菜单类型', 0, '2020-05-26 17:59:39', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1265466149622128641, '发送类型', 'send_type', 100, '发送类型', 0, '2020-05-27 10:13:36', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1265466752209395713, '打开方式', 'open_type', 100, '打开方式', 0, '2020-05-27 10:16:00', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1265467337566461954, '菜单权重', 'menu_weight', 100, '菜单权重', 0, '2020-05-27 10:18:19', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1265467629167058946, '请假类型', 'leave_type', 100, '请假类型', 0, '2020-05-27 10:19:29', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1265468028632571905, '数据范围类型', 'data_scope_type', 100, '数据范围类型', 0, '2020-05-27 10:21:04', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1265468437904367618, '短信发送来源', 'sms_send_source', 100, '短信发送来源', 0, '2020-05-27 10:22:42', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1265468761230680066, '字段类型', 'filed_type', 100, '字段类型', 0, '2020-05-27 10:23:59', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1265469198583341057, '脚本类型', 'script_type', 100, '脚本类型', 0, '2020-05-27 10:25:43', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1265469441454514178, '脚本语言类型', 'script_language_type', 100, '脚本语言类型', 0, '2020-05-27 10:26:41', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1265469702042427393, '表单类型', 'form_type', 100, '表单类型', 0, '2020-05-27 10:27:43', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1265469962873610241, '事件类型', 'event_type', 100, '事件类型', 0, '2020-05-27 10:28:45', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1265469962873610242, '事件节点类型', 'event_node_type', 100, '事件节点类型', 0, '2020-05-27 10:28:45', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1265470456631271426, '流程状态', 'process_status', 100, '流程状态', 0, '2020-05-27 10:30:43', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1275617093517172738, '操作类型', 'op_type', 100, '操作类型', 0, '2020-06-24 10:29:50', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1277774529430654977, '文件存储位置', 'file_storage_location', 100, '文件存储位置', 0, '2020-06-30 09:22:42', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1278606951432855553, '运行状态', 'run_status', 100, '定时任务运行状态', 0, '2020-07-02 16:30:27', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1278911800547147777, '通知公告类型', 'notice_type', 100, '通知公告类型', 0, '2020-07-03 12:41:49', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1278911952657776642, '通知公告状态', 'notice_status', 100, '通知公告状态', 0, '2020-07-03 12:42:25', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1278992276965605377, '委托状态', 'delegate_status', 100, '委托状态', 0, '2020-07-03 18:01:36', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1291390159941599233, '是否boolean', 'yes_true_false', 100, '是否boolean', 2, '2020-08-06 23:06:22', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1291391077990858754, '流程是否挂起', 'suspended_status', 100, '流程是否挂起', 0, '2020-08-06 23:10:01', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1291393441594408961, '是否结束', 'ended_status', 100, '是否结束', 0, '2020-08-06 23:19:24', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1300767512828354562, 'JDBC驱动类型', 'jdbc_driver', 100, 'JDBC驱动类型', 0, '2020-09-01 20:08:37', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1300767512828354563, '支付宝交易状态', 'alipay_trade_status', 100, '支付宝交易状态', 0, '2020-09-23 10:36:53', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (1381533149530996737, '元数据类型', 'metadata_type', 100, '元数据类型', 0, '2021-04-12 17:02:25', 1377879897444212737, '2021-04-12 17:03:00', 1377879897444212737);
INSERT INTO `sys_dict_type` VALUES (1381534434753822721, '数据类型', 'data_type', 100, '数据类型', 0, '2021-04-12 17:07:32', 1377879897444212737, '2021-04-12 17:07:52', 1377879897444212737);
INSERT INTO `sys_dict_type` VALUES (1395609304143806466, '元数据字段约束', 'metadata_field_constraint', 1, NULL, 0, '2021-05-21 13:16:02', 1377879897444212737, '2021-05-21 13:18:40', 1377879897444212737);
INSERT INTO `sys_dict_type` VALUES (1400391461047451650, '陈毅数据类型', 'ChenYiType', 100, '前端页面展示所用', 0, '2021-06-03 17:58:37', 1377879897444212737, NULL, NULL);

-- ----------------------------
-- Table structure for sys_emp
-- ----------------------------
DROP TABLE IF EXISTS `sys_emp`;
CREATE TABLE `sys_emp`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `job_num` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工号',
  `org_id` bigint(20) NOT NULL COMMENT '所属机构id',
  `org_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '所属机构名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '员工表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_emp
-- ----------------------------
INSERT INTO `sys_emp` VALUES (1280700700074041345, '110', 1265476890672672771, '研发部');
INSERT INTO `sys_emp` VALUES (1377879897444212737, NULL, 1377879536427884546, '复旦大学图书馆');

-- ----------------------------
-- Table structure for sys_emp_ext_org_pos
-- ----------------------------
DROP TABLE IF EXISTS `sys_emp_ext_org_pos`;
CREATE TABLE `sys_emp_ext_org_pos`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `emp_id` bigint(20) NOT NULL COMMENT '员工id',
  `org_id` bigint(20) NOT NULL COMMENT '机构id',
  `pos_id` bigint(20) NOT NULL COMMENT '岗位id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '员工附属机构岗位表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_emp_ext_org_pos
-- ----------------------------

-- ----------------------------
-- Table structure for sys_emp_pos
-- ----------------------------
DROP TABLE IF EXISTS `sys_emp_pos`;
CREATE TABLE `sys_emp_pos`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `emp_id` bigint(20) NOT NULL COMMENT '员工id',
  `pos_id` bigint(20) NOT NULL COMMENT '职位id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '员工职位关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_emp_pos
-- ----------------------------
INSERT INTO `sys_emp_pos` VALUES (1280710828479324161, 1280700700074041345, 1265476890672672790);
INSERT INTO `sys_emp_pos` VALUES (1379714022168858626, 1377879897444212737, 1265476890672672790);

-- ----------------------------
-- Table structure for sys_file_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_file_info`;
CREATE TABLE `sys_file_info`  (
  `id` bigint(20) NOT NULL COMMENT '主键id',
  `file_location` tinyint(4) NOT NULL COMMENT '文件存储位置（1:阿里云，2:腾讯云，3:minio，4:本地）',
  `file_bucket` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件仓库',
  `file_origin_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件名称（上传时候的文件名）',
  `file_suffix` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件后缀',
  `file_size_kb` bigint(20) NULL DEFAULT NULL COMMENT '文件大小kb',
  `file_size_info` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件大小信息，计算后的',
  `file_object_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '存储到bucket的名称（文件唯一标识id）',
  `file_path` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '存储路径',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '修改用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '文件信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_file_info
-- ----------------------------
INSERT INTO `sys_file_info` VALUES (1323533270338170882, 4, 'defaultBucket', 'xiaonuo_logo.png', 'png', 30, '30.23 kB', '1323533270338170882.png', NULL, '2020-11-03 15:51:36', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_file_info` VALUES (1323547434033016833, 4, 'defaultBucket', 'xiaonuo_logo_b.png', 'png', 30, '30.03 kB', '1323547434033016833.png', NULL, '2020-11-03 16:47:53', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_file_info` VALUES (1323586730685218817, 4, 'defaultBucket', 'xiaonuo_logo_b.png', 'png', 30, '30.4 kB', '1323586730685218817.png', NULL, '2020-11-03 19:24:02', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_file_info` VALUES (1324609505700892673, 4, 'defaultBucket', 'xiaonuo_logo_pt_wz.png', 'png', 29, '29.23 kB', '1324609505700892673.png', NULL, '2020-11-06 15:08:11', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_file_info` VALUES (1333734209900740609, 4, 'defaultBucket', 'await_logo.jpg', 'jpg', 6, '6.4 kB', '1333734209900740609.jpg', NULL, '2020-12-01 19:26:30', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_file_info` VALUES (1377894713034563585, 4, 'defaultBucket', 'logo (2).png', 'png', 63, '62.53 kB', '1377894713034563585.png', NULL, '2021-04-02 16:04:34', 1265476890672672808, NULL, NULL);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `pid` bigint(20) NOT NULL COMMENT '父id',
  `pids` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '父ids',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编码',
  `type` tinyint(4) NOT NULL DEFAULT 1 COMMENT '菜单类型（字典 0目录 1菜单 2按钮）',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `router` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组件地址',
  `permission` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `application` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '应用分类（应用编码）',
  `open_type` tinyint(4) NOT NULL COMMENT '打开方式（字典 0无 1组件 2内链 3外链）',
  `visible` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '是否可见（Y-是，N-否）',
  `link` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '链接地址',
  `redirect` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '重定向地址',
  `weight` tinyint(4) NULL DEFAULT NULL COMMENT '权重（字典 1系统权重 2业务权重）',
  `sort` int(11) NOT NULL COMMENT '排序',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '状态（字典 0正常 1停用 2删除）',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统菜单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1264622039642255311, 0, '[0],', '主控面板', 'system_index', 0, 'home', '/', 'RouteView', NULL, 'system', 0, 'Y', NULL, '/analysis', 1, 1, NULL, 0, '2020-05-25 02:19:24', 1265476890672672808, '2021-04-06 10:00:14', 1265476890672672808);
INSERT INTO `sys_menu` VALUES (1264622039642255321, 1264622039642255311, '[0],[1264622039642255311],', '分析页', 'system_index_dashboard', 1, NULL, 'analysis', 'system/dashboard/Analysis', NULL, 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-05-25 02:21:55', 1265476890672672808, '2021-04-06 10:00:49', 1265476890672672808);
INSERT INTO `sys_menu` VALUES (1264622039642255331, 1264622039642255311, '[0],[1264622039642255311],', '工作台', 'system_index_workplace', 1, NULL, 'workplace', 'system/dashboard/Workplace', NULL, 'system', 0, 'Y', NULL, NULL, 1, 2, NULL, 0, '2020-05-25 02:23:48', 1265476890672672808, '2021-04-06 10:09:21', 1265476890672672808);
INSERT INTO `sys_menu` VALUES (1264622039642255341, 0, '[0],', '组织架构', 'sys_mgr', 0, 'team', '/sys', 'PageView', NULL, 'system', 0, 'Y', NULL, NULL, 1, 2, NULL, 0, '2020-03-27 15:58:16', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255351, 1264622039642255341, '[0],[1264622039642255341],', '用户管理', 'sys_user_mgr', 1, NULL, '/mgr_user', 'system/user/index', NULL, 'system', 1, 'Y', NULL, NULL, 1, 3, NULL, 0, '2020-03-27 16:10:21', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255361, 1264622039642255351, '[0],[1264622039642255341],[1264622039642255351],', '用户查询', 'sys_user_mgr_page', 2, NULL, NULL, NULL, 'sysUser:page', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-27 16:36:49', 1265476890672672808, '2021-04-08 13:49:27', 1377879897444212737);
INSERT INTO `sys_menu` VALUES (1264622039642255371, 1264622039642255351, '[0],[1264622039642255341],[1264622039642255351],', '用户编辑', 'sys_user_mgr_edit', 2, NULL, NULL, NULL, 'sysUser:edit', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-02 12:20:23', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255381, 1264622039642255351, '[0],[1264622039642255341],[1264622039642255351],', '用户增加', 'sys_user_mgr_add', 2, NULL, NULL, NULL, 'sysUser:add', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-27 16:37:35', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255391, 1264622039642255351, '[0],[1264622039642255341],[1264622039642255351],', '用户删除', 'sys_user_mgr_delete', 2, NULL, NULL, NULL, 'sysUser:delete', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-27 16:37:58', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255401, 1264622039642255351, '[0],[1264622039642255341],[1264622039642255351],', '用户详情', 'sys_user_mgr_detail', 2, NULL, NULL, NULL, 'sysUser:detail', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-27 16:38:25', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255411, 1264622039642255351, '[0],[1264622039642255341],[1264622039642255351],', '用户导出', 'sys_user_mgr_export', 2, NULL, NULL, NULL, 'sysUser:export', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-02 12:21:59', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255421, 1264622039642255351, '[0],[1264622039642255341],[1264622039642255351],', '用户选择器', 'sys_user_mgr_selector', 2, NULL, NULL, NULL, 'sysUser:selector', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-03 13:30:14', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255431, 1264622039642255351, '[0],[1264622039642255341],[1264622039642255351],', '用户授权角色', 'sys_user_mgr_grant_role', 2, NULL, NULL, NULL, 'sysUser:grantRole', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-04-01 09:22:01', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255441, 1264622039642255351, '[0],[1264622039642255341],[1264622039642255351],', '用户拥有角色', 'sys_user_mgr_own_role', 2, NULL, NULL, NULL, 'sysUser:ownRole', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-05-29 14:27:22', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255451, 1264622039642255351, '[0],[1264622039642255341],[1264622039642255351],', '用户授权数据', 'sys_user_mgr_grant_data', 2, NULL, NULL, NULL, 'sysUser:grantData', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-04-01 09:22:13', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255461, 1264622039642255351, '[0],[1264622039642255341],[1264622039642255351],', '用户拥有数据', 'sys_user_mgr_own_data', 2, NULL, NULL, NULL, 'sysUser:ownData', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-05-29 14:27:41', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255471, 1264622039642255351, '[0],[1264622039642255341],[1264622039642255351],', '用户更新信息', 'sys_user_mgr_update_info', 2, NULL, NULL, NULL, 'sysUser:updateInfo', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-04-01 16:19:32', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255481, 1264622039642255351, '[0],[1264622039642255341],[1264622039642255351],', '用户修改密码', 'sys_user_mgr_update_pwd', 2, NULL, NULL, NULL, 'sysUser:updatePwd', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-04-01 16:20:25', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255491, 1264622039642255351, '[0],[1264622039642255341],[1264622039642255351],', '用户修改状态', 'sys_user_mgr_change_status', 2, NULL, NULL, NULL, 'sysUser:changeStatus', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-06-23 11:13:14', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255501, 1264622039642255351, '[0],[1264622039642255341],[1264622039642255351],', '用户修改头像', 'sys_user_mgr_update_avatar', 2, NULL, NULL, NULL, 'sysUser:updateAvatar', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-02 12:21:42', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255511, 1264622039642255351, '[0],[1264622039642255341],[1264622039642255351],', '用户重置密码', 'sys_user_mgr_reset_pwd', 2, NULL, NULL, NULL, 'sysUser:resetPwd', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-05-29 15:01:51', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255521, 1264622039642255341, '[0],[1264622039642255341],', '机构管理', 'sys_org_mgr', 1, NULL, '/org', 'system/org/index', NULL, 'system', 1, 'Y', NULL, NULL, 1, 4, NULL, 0, '2020-03-27 17:15:39', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255531, 1264622039642255521, '[0],[1264622039642255341],[1264622039642255521]', '机构查询', 'sys_org_mgr_page', 2, NULL, NULL, NULL, 'sysOrg:page', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-27 17:17:37', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255541, 1264622039642255521, '[0],[1264622039642255341],[1264622039642255521]', '机构列表', 'sys_org_mgr_list', 2, NULL, NULL, NULL, 'sysOrg:list', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-02 11:54:26', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255551, 1264622039642255521, '[0],[1264622039642255341],[1264622039642255521]', '机构增加', 'sys_org_mgr_add', 2, NULL, NULL, NULL, 'sysOrg:add', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-27 17:19:53', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255561, 1264622039642255521, '[0],[1264622039642255341],[1264622039642255521]', '机构编辑', 'sys_org_mgr_edit', 2, NULL, NULL, NULL, 'sysOrg:edit', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-02 11:54:37', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255571, 1264622039642255521, '[0],[1264622039642255341],[1264622039642255521]', '机构删除', 'sys_org_mgr_delete', 2, NULL, NULL, NULL, 'sysOrg:delete', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-27 17:20:48', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255581, 1264622039642255521, '[0],[1264622039642255341],[1264622039642255521]', '机构详情', 'sys_org_mgr_detail', 2, NULL, NULL, NULL, 'sysOrg:detail', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-27 17:21:15', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255591, 1264622039642255521, '[0],[1264622039642255341],[1264622039642255521]', '机构树', 'sys_org_mgr_tree', 2, NULL, NULL, NULL, 'sysOrg:tree', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-27 17:21:58', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255601, 1264622039642255341, '[0],[1264622039642255341],', '职位管理', 'sys_pos_mgr', 1, NULL, '/pos', 'system/pos/index', NULL, 'system', 1, 'Y', NULL, NULL, 1, 5, NULL, 0, '2020-03-27 18:38:31', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255611, 1264622039642255601, '[0],[1264622039642255341],[1264622039642255601],', '职位查询', 'sys_pos_mgr_page', 2, NULL, NULL, NULL, 'sysPos:page', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-27 18:41:48', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255621, 1264622039642255601, '[0],[1264622039642255341],[1264622039642255601],', '职位列表', 'sys_pos_mgr_list', 2, NULL, NULL, NULL, 'sysPos:list', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-02 11:55:57', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255631, 1264622039642255601, '[0],[1264622039642255341],[1264622039642255601],', '职位增加', 'sys_pos_mgr_add', 2, NULL, NULL, NULL, 'sysPos:add', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-27 18:42:20', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255641, 1264622039642255601, '[0],[1264622039642255341],[1264622039642255601],', '职位编辑', 'sys_pos_mgr_edit', 2, NULL, NULL, NULL, 'sysPos:edit', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-02 11:56:08', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255651, 1264622039642255601, '[0],[1264622039642255341],[1264622039642255601],', '职位删除', 'sys_pos_mgr_delete', 2, NULL, NULL, NULL, 'sysPos:delete', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-27 18:42:39', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255661, 1264622039642255601, '[0],[1264622039642255341],[1264622039642255601],', '职位详情', 'sys_pos_mgr_detail', 2, NULL, NULL, NULL, 'sysPos:detail', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-27 18:43:00', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255671, 0, '[0],', '权限管理', 'auth_manager', 0, 'safety-certificate', '/auth', 'PageView', NULL, 'system', 0, 'Y', NULL, NULL, 1, 3, NULL, 0, '2020-07-15 15:51:57', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255681, 1264622039642255671, '[0],[1264622039642255671],', '应用管理', 'sys_app_mgr', 1, NULL, '/app', 'system/app/index', NULL, 'system', 1, 'Y', NULL, NULL, 1, 6, NULL, 0, '2020-03-27 16:40:21', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255691, 1264622039642255681, '[0],[1264622039642255671],[1264622039642255681],', '应用查询', 'sys_app_mgr_page', 2, NULL, NULL, NULL, 'sysApp:page', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-27 16:41:58', 1265476890672672808, '2021-04-08 13:47:52', 1265476890672672808);
INSERT INTO `sys_menu` VALUES (1264622039642255701, 1264622039642255681, '[0],[1264622039642255671],[1264622039642255681],', '应用列表', 'sys_app_mgr_list', 2, NULL, NULL, NULL, 'sysApp:list', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-02 10:04:59', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255711, 1264622039642255681, '[0],[1264622039642255671],[1264622039642255681],', '应用增加', 'sys_app_mgr_add', 2, NULL, NULL, NULL, 'sysApp:add', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-27 16:44:10', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255721, 1264622039642255681, '[0],[1264622039642255671],[1264622039642255681],', '应用编辑', 'sys_app_mgr_edit', 2, NULL, NULL, NULL, 'sysApp:edit', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-02 10:04:34', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255731, 1264622039642255681, '[0],[1264622039642255671],[1264622039642255681],', '应用删除', 'sys_app_mgr_delete', 2, NULL, NULL, NULL, 'sysApp:delete', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-27 17:14:29', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255741, 1264622039642255681, '[0],[1264622039642255671],[1264622039642255681],', '应用详情', 'sys_app_mgr_detail', 2, NULL, NULL, NULL, 'sysApp:detail', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-27 17:14:56', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255751, 1264622039642255681, '[0],[1264622039642255671],[1264622039642255681],', '设为默认应用', 'sys_app_mgr_set_as_default', 2, NULL, NULL, NULL, 'sysApp:setAsDefault', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-27 17:14:56', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255761, 1264622039642255671, '[0],[1264622039642255671],', '菜单管理', 'sys_menu_mgr', 1, NULL, '/menu', 'system/menu/index', NULL, 'system', 1, 'Y', NULL, NULL, 1, 7, NULL, 0, '2020-03-27 18:44:35', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255771, 1264622039642255761, '[0],[1264622039642255671],[1264622039642255761],', '菜单列表', 'sys_menu_mgr_list', 2, NULL, NULL, NULL, 'sysMenu:list', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-27 18:45:20', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255781, 1264622039642255761, '[0],[1264622039642255671],[1264622039642255761],', '菜单增加', 'sys_menu_mgr_add', 2, NULL, NULL, NULL, 'sysMenu:add', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-27 18:45:37', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255791, 1264622039642255761, '[0],[1264622039642255671],[1264622039642255761],', '菜单编辑', 'sys_menu_mgr_edit', 2, NULL, NULL, NULL, 'sysMenu:edit', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-02 11:52:00', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255801, 1264622039642255761, '[0],[1264622039642255671],[1264622039642255761],', '菜单删除', 'sys_menu_mgr_delete', 2, NULL, NULL, NULL, 'sysMenu:delete', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-27 18:46:01', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255811, 1264622039642255761, '[0],[1264622039642255671],[1264622039642255761],', '菜单详情', 'sys_menu_mgr_detail', 2, NULL, NULL, NULL, 'sysMenu:detail', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-27 18:46:22', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255821, 1264622039642255761, '[0],[1264622039642255671],[1264622039642255761],', '菜单授权树', 'sys_menu_mgr_grant_tree', 2, NULL, NULL, NULL, 'sysMenu:treeForGrant', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-06-03 09:50:31', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255831, 1264622039642255761, '[0],[1264622039642255671],[1264622039642255761],', '菜单树', 'sys_menu_mgr_tree', 2, NULL, NULL, NULL, 'sysMenu:tree', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-27 18:47:50', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255841, 1264622039642255761, '[0],[1264622039642255671],[1264622039642255761],', '菜单切换', 'sys_menu_mgr_change', 2, NULL, NULL, NULL, 'sysMenu:change', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-06-03 09:51:43', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255851, 1264622039642255671, '[0],[1264622039642255671],', '角色管理', 'sys_role_mgr', 1, NULL, '/role', 'system/role/index', NULL, 'system', 1, 'Y', NULL, NULL, 1, 8, NULL, 0, '2020-03-28 16:01:09', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255861, 1264622039642255851, '[0],[1264622039642255671],[1264622039642255851],', '角色查询', 'sys_role_mgr_page', 2, NULL, NULL, NULL, 'sysRole:page', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-28 16:02:09', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255871, 1264622039642255851, '[0],[1264622039642255671],[1264622039642255851],', '角色增加', 'sys_role_mgr_add', 2, NULL, NULL, NULL, 'sysRole:add', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-28 16:02:27', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255881, 1264622039642255851, '[0],[1264622039642255671],[1264622039642255851],', '角色编辑', 'sys_role_mgr_edit', 2, NULL, NULL, NULL, 'sysRole:edit', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-02 11:57:27', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255891, 1264622039642255851, '[0],[1264622039642255671],[1264622039642255851],', '角色删除', 'sys_role_mgr_delete', 2, NULL, NULL, NULL, 'sysRole:delete', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-28 16:02:46', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255901, 1264622039642255851, '[0],[1264622039642255671],[1264622039642255851],', '角色详情', 'sys_role_mgr_detail', 2, NULL, NULL, NULL, 'sysRole:detail', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-28 16:03:01', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255911, 1264622039642255851, '[0],[1264622039642255671],[1264622039642255851],', '角色下拉', 'sys_role_mgr_drop_down', 2, NULL, NULL, NULL, 'sysRole:dropDown', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-05-29 15:45:39', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255921, 1264622039642255851, '[0],[1264622039642255671],[1264622039642255851],', '角色授权菜单', 'sys_role_mgr_grant_menu', 2, NULL, NULL, NULL, 'sysRole:grantMenu', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-04-01 09:16:27', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255931, 1264622039642255851, '[0],[1264622039642255671],[1264622039642255851],', '角色拥有菜单', 'sys_role_mgr_own_menu', 2, NULL, NULL, NULL, 'sysRole:ownMenu', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-05-29 14:21:54', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255941, 1264622039642255851, '[0],[1264622039642255671],[1264622039642255851],', '角色授权数据', 'sys_role_mgr_grant_data', 2, NULL, NULL, NULL, 'sysRole:grantData', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-04-01 09:16:56', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255951, 1264622039642255851, '[0],[1264622039642255671],[1264622039642255851],', '角色拥有数据', 'sys_role_mgr_own_data', 2, NULL, NULL, NULL, 'sysRole:ownData', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-05-29 14:23:08', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255961, 0, '[0],', '开发管理', 'system_tools', 0, 'euro', '/tools', 'PageView', NULL, 'system', 1, 'Y', NULL, NULL, 1, 4, NULL, 0, '2020-05-25 02:10:55', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255971, 1264622039642255961, '[0],[1264622039642255961],', '系统配置', 'system_tools_config', 1, NULL, '/config', 'system/config/index', NULL, 'system', 1, 'Y', NULL, NULL, 1, 9, NULL, 0, '2020-05-25 02:12:56', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255981, 1264622039642255971, '[0],[1264622039642255961],[1264622039642255971],', '配置查询', 'system_tools_config_page', 2, NULL, NULL, NULL, 'sysConfig:page', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-05-27 17:02:22', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642255991, 1264622039642255971, '[0],[1264622039642255961],[1264622039642255971],', '配置列表', 'system_tools_config_list', 2, NULL, NULL, NULL, 'sysConfig:list', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-05-27 17:02:42', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256001, 1264622039642255971, '[0],[1264622039642255961],[1264622039642255971],', '配置增加', 'system_tools_config_add', 2, NULL, NULL, NULL, 'sysConfig:add', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-05-27 17:03:31', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256011, 1264622039642255971, '[0],[1264622039642255961],[1264622039642255971],', '配置编辑', 'system_tools_config_edit', 2, NULL, NULL, NULL, 'sysConfig:edit', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-05-27 17:03:55', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256021, 1264622039642255971, '[0],[1264622039642255961],[1264622039642255971],', '配置删除', 'system_tools_config_delete', 2, NULL, NULL, NULL, 'sysConfig:delete', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-05-27 17:03:44', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256031, 1264622039642255971, '[0],[1264622039642255961],[1264622039642255971],', '配置详情', 'system_tools_config_detail', 2, NULL, NULL, NULL, 'sysConfig:detail', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-05-27 17:02:59', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256041, 1264622039642255961, '[0],[1264622039642255961],', '邮件发送', 'sys_email_mgr', 1, NULL, '/email', 'system/email/index', NULL, 'system', 1, 'Y', NULL, NULL, 1, 10, NULL, 0, '2020-07-02 11:44:21', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256051, 1264622039642256041, '[0],[1264622039642255961],[1264622039642256041],', '发送文本邮件', 'sys_email_mgr_send_email', 2, NULL, NULL, NULL, 'email:sendEmail', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-02 11:45:39', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256061, 1264622039642256041, '[0],[1264622039642255961],[1264622039642256041],', '发送html邮件', 'sys_email_mgr_send_email_html', 2, NULL, NULL, NULL, 'email:sendEmailHtml', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-02 11:45:57', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256071, 1264622039642255961, '[0],[1264622039642255961],', '短信管理', 'sys_sms_mgr', 1, NULL, '/sms', 'system/sms/index', NULL, 'system', 1, 'Y', NULL, NULL, 1, 11, NULL, 0, '2020-07-02 12:00:12', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256081, 1264622039642256071, '[0],[1264622039642255961],[1264622039642256071],', '短信发送查询', 'sys_sms_mgr_page', 2, NULL, NULL, NULL, 'sms:page', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-02 12:16:56', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256091, 1264622039642256071, '[0],[1264622039642255961],[1264622039642256071],', '发送验证码短信', 'sys_sms_mgr_send_login_message', 2, NULL, NULL, NULL, 'sms:sendLoginMessage', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-02 12:02:31', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256101, 1264622039642256071, '[0],[1264622039642255961],[1264622039642256071],', '验证短信验证码', 'sys_sms_mgr_validate_message', 2, NULL, NULL, NULL, 'sms:validateMessage', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-02 12:02:50', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256111, 1264622039642255961, '[0],[1264622039642255961],', '字典管理', 'sys_dict_mgr', 1, NULL, '/dict', 'system/dict/index', NULL, 'system', 1, 'Y', NULL, NULL, 1, 12, NULL, 0, '2020-04-01 11:17:26', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256121, 1264622039642256111, '[0],[1264622039642255961],[1264622039642256111],', '字典类型查询', 'sys_dict_mgr_dict_type_page', 2, NULL, NULL, NULL, 'sysDictType:page', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-04-01 11:20:22', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256131, 1264622039642256111, '[0],[1264622039642255961],[1264622039642256111],', '字典类型列表', 'sys_dict_mgr_dict_type_list', 2, NULL, NULL, NULL, 'sysDictType:list', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-05-29 15:12:35', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256141, 1264622039642256111, '[0],[1264622039642255961],[1264622039642256111],', '字典类型增加', 'sys_dict_mgr_dict_type_add', 2, NULL, NULL, NULL, 'sysDictType:add', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-04-01 11:19:58', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256151, 1264622039642256111, '[0],[1264622039642255961],[1264622039642256111],', '字典类型删除', 'sys_dict_mgr_dict_type_delete', 2, NULL, NULL, NULL, 'sysDictType:delete', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-04-01 11:21:30', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256161, 1264622039642256111, '[0],[1264622039642255961],[1264622039642256111],', '字典类型编辑', 'sys_dict_mgr_dict_type_edit', 2, NULL, NULL, NULL, 'sysDictType:edit', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-04-01 11:21:42', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256171, 1264622039642256111, '[0],[1264622039642255961],[1264622039642256111],', '字典类型详情', 'sys_dict_mgr_dict_type_detail', 2, NULL, NULL, NULL, 'sysDictType:detail', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-04-01 11:22:06', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256181, 1264622039642256111, '[0],[1264622039642255961],[1264622039642256111],', '字典类型下拉', 'sys_dict_mgr_dict_type_drop_down', 2, NULL, NULL, NULL, 'sysDictType:dropDown', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-04-01 11:22:23', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256191, 1264622039642256111, '[0],[1264622039642255961],[1264622039642256111],', '字典类型修改状态', 'sys_dict_mgr_dict_type_change_status', 2, NULL, NULL, NULL, 'sysDictType:changeStatus', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-06-23 11:15:50', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256201, 1264622039642256111, '[0],[1264622039642255961],[1264622039642256111],', '字典值查询', 'sys_dict_mgr_dict_page', 2, NULL, NULL, NULL, 'sysDictData:page', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-04-01 11:23:11', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256211, 1264622039642256111, '[0],[1264622039642255961],[1264622039642256111],', '字典值列表', 'sys_dict_mgr_dict_list', 2, NULL, NULL, NULL, 'sysDictData:list', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-04-01 11:24:58', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256221, 1264622039642256111, '[0],[1264622039642255961],[1264622039642256111],', '字典值增加', 'sys_dict_mgr_dict_add', 2, NULL, NULL, NULL, 'sysDictData:add', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-04-01 11:22:51', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256231, 1264622039642256111, '[0],[1264622039642255961],[1264622039642256111],', '字典值删除', 'sys_dict_mgr_dict_delete', 2, NULL, NULL, NULL, 'sysDictData:delete', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-04-01 11:23:26', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256241, 1264622039642256111, '[0],[1264622039642255961],[1264622039642256111],', '字典值编辑', 'sys_dict_mgr_dict_edit', 2, NULL, NULL, NULL, 'sysDictData:edit', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-04-01 11:24:21', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256251, 1264622039642256111, '[0],[1264622039642255961],[1264622039642256111],', '字典值详情', 'sys_dict_mgr_dict_detail', 2, NULL, NULL, NULL, 'sysDictData:detail', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-04-01 11:24:42', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256261, 1264622039642256111, '[0],[1264622039642255961],[1264622039642256111],', '字典值修改状态', 'sys_dict_mgr_dict_change_status', 2, NULL, NULL, NULL, 'sysDictData:changeStatus', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-06-23 11:17:53', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256271, 1264622039642255961, '[0],[1264622039642255961],', '接口文档', 'sys_swagger_mgr', 1, NULL, '/swagger', 'Iframe', NULL, 'system', 2, 'Y', 'http://localhost:9005/doc.html', NULL, 1, 13, NULL, 0, '2020-07-02 12:16:56', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256281, 0, '[0],', '日志管理', 'sys_log_mgr', 0, 'read', '/log', 'PageView', NULL, 'system', 1, 'Y', NULL, NULL, 1, 5, NULL, 0, '2020-04-01 09:25:01', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256291, 1264622039642256281, '[0],[1264622039642256281],', '访问日志', 'sys_log_mgr_vis_log', 1, NULL, '/vislog', 'system/log/vislog/index', NULL, 'system', 0, 'Y', NULL, NULL, 1, 14, NULL, 0, '2020-04-01 09:26:40', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256301, 1264622039642256291, '[0],[1264622039642256281],[1264622039642256291],', '访问日志查询', 'sys_log_mgr_vis_log_page', 2, NULL, NULL, NULL, 'sysVisLog:page', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-02 09:55:51', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256311, 1264622039642256291, '[0],[1264622039642256281],[1264622039642256291],', '访问日志清空', 'sys_log_mgr_vis_log_delete', 2, NULL, NULL, NULL, 'sysVisLog:delete', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-02 09:56:57', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256321, 1264622039642256281, '[0],[1264622039642256281],', '操作日志', 'sys_log_mgr_op_log', 1, NULL, '/oplog', 'system/log/oplog/index', NULL, 'system', 0, 'Y', NULL, NULL, 1, 15, NULL, 0, '2020-04-01 09:26:59', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256331, 1264622039642256321, '[0],[1264622039642256281],[1264622039642256321],', '操作日志查询', 'sys_log_mgr_op_log_page', 2, NULL, NULL, NULL, 'sysOpLog:page', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-02 09:57:39', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256341, 1264622039642256321, '[0],[1264622039642256281],[1264622039642256321],', '操作日志清空', 'sys_log_mgr_op_log_delete', 2, NULL, NULL, NULL, 'sysOpLog:delete', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-02 09:58:13', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256351, 0, '[0],', '系统监控', 'sys_monitor_mgr', 0, 'deployment-unit', '/monitor', 'PageView', NULL, 'system', 1, 'Y', NULL, NULL, 1, 6, NULL, 0, '2020-06-05 16:00:50', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256361, 1264622039642256351, '[0],[1264622039642256351],', '服务监控', 'sys_monitor_mgr_machine_monitor', 1, NULL, '/machine', 'system/machine/index', NULL, 'system', 1, 'Y', NULL, NULL, 1, 16, NULL, 0, '2020-06-05 16:02:38', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256371, 1264622039642256361, '[0],[1264622039642256351],[1264622039642256361],', '服务监控查询', 'sys_monitor_mgr_machine_monitor_query', 2, NULL, NULL, NULL, 'sysMachine:query', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-06-05 16:05:33', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256381, 1264622039642256351, '[0],[1264622039642256351],', '在线用户', 'sys_monitor_mgr_online_user', 1, NULL, '/onlineUser', 'system/onlineUser/index', NULL, 'system', 1, 'Y', NULL, NULL, 1, 17, NULL, 0, '2020-06-05 16:01:55', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256391, 1264622039642256381, '[0],[1264622039642256351],[1264622039642256381],', '在线用户列表', 'sys_monitor_mgr_online_user_list', 2, NULL, NULL, NULL, 'sysOnlineUser:list', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-06-05 16:03:46', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256401, 1264622039642256381, '[0],[1264622039642256351],[1264622039642256381],', '在线用户强退', 'sys_monitor_mgr_online_user_force_exist', 2, NULL, NULL, NULL, 'sysOnlineUser:forceExist', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-06-05 16:04:16', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256411, 1264622039642256351, '[0],[1264622039642256351],', '数据监控', 'sys_monitor_mgr_druid', 1, NULL, '/druid', 'Iframe', NULL, 'system', 2, 'Y', 'http://localhost:9005/druid/login.html', NULL, 1, 18, NULL, 0, '2020-06-28 16:15:07', 1265476890672672808, '2020-09-13 09:39:10', 1265476890672672808);
INSERT INTO `sys_menu` VALUES (1264622039642256421, 0, '[0],', '通知公告', 'sys_notice', 0, 'sound', '/notice', 'PageView', NULL, 'system', 1, 'Y', NULL, NULL, 1, 7, NULL, 0, '2020-06-29 15:41:53', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256431, 1264622039642256421, '[0],[1264622039642256421],', '公告管理', 'sys_notice_mgr', 1, NULL, '/notice', 'system/notice/index', NULL, 'system', 1, 'Y', NULL, NULL, 1, 19, NULL, 0, '2020-06-29 15:44:24', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256441, 1264622039642256431, '[0],[1264622039642256421],[1264622039642256431],', '公告查询', 'sys_notice_mgr_page', 2, NULL, NULL, NULL, 'sysNotice:page', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-06-29 15:45:30', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256451, 1264622039642256431, '[0],[1264622039642256421],[1264622039642256431],', '公告增加', 'sys_notice_mgr_add', 2, NULL, NULL, NULL, 'sysNotice:add', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-06-29 15:45:57', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256461, 1264622039642256431, '[0],[1264622039642256421],[1264622039642256431],', '公告编辑', 'sys_notice_mgr_edit', 2, NULL, NULL, NULL, 'sysNotice:edit', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-06-29 15:46:22', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256471, 1264622039642256431, '[0],[1264622039642256421],[1264622039642256431],', '公告删除', 'sys_notice_mgr_delete', 2, NULL, NULL, NULL, 'sysNotice:delete', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-06-29 15:46:11', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256481, 1264622039642256431, '[0],[1264622039642256421],[1264622039642256431],', '公告查看', 'sys_notice_mgr_detail', 2, NULL, NULL, NULL, 'sysNotice:detail', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-06-29 15:46:33', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256491, 1264622039642256431, '[0],[1264622039642256421],[1264622039642256431],', '公告修改状态', 'sys_notice_mgr_changeStatus', 2, NULL, NULL, NULL, 'sysNotice:changeStatus', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-06-29 15:46:50', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256501, 1264622039642256421, '[0],[1264622039642256421],', '已收公告', 'sys_notice_mgr_received', 1, NULL, '/noticeReceived', 'system/noticeReceived/index', NULL, 'system', 1, 'Y', NULL, NULL, 1, 20, NULL, 0, '2020-06-29 16:32:53', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256511, 1264622039642256501, '[0],[1264622039642256421],[1264622039642256501],', '已收公告查询', 'sys_notice_mgr_received_page', 2, NULL, NULL, NULL, 'sysNotice:received', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-06-29 16:33:43', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256521, 0, '[0],', '文件管理', 'sys_file_mgr', 0, 'file', '/file', 'PageView', NULL, 'system', 1, 'Y', NULL, NULL, 1, 8, NULL, 0, '2020-06-24 17:31:10', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256531, 1264622039642256521, '[0],[1264622039642256521],', '系统文件', 'sys_file_mgr_sys_file', 1, NULL, '/file', 'system/file/index', NULL, 'system', 1, 'Y', NULL, NULL, 1, 21, NULL, 0, '2020-06-24 17:32:57', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256541, 1264622039642256531, '[0],[1264622039642256521],[1264622039642256531],', '文件查询', 'sys_file_mgr_sys_file_page', 2, NULL, NULL, NULL, 'sysFileInfo:page', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-06-24 17:35:38', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256551, 1264622039642256531, '[0],[1264622039642256521],[1264622039642256531],', '文件列表', 'sys_file_mgr_sys_file_list', 2, NULL, NULL, NULL, 'sysFileInfo:list', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-06-24 17:35:49', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256561, 1264622039642256531, '[0],[1264622039642256521],[1264622039642256531],', '文件删除', 'sys_file_mgr_sys_file_delete', 2, NULL, NULL, NULL, 'sysFileInfo:delete', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-06-24 17:36:11', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256571, 1264622039642256531, '[0],[1264622039642256521],[1264622039642256531],', '文件详情', 'sys_file_mgr_sys_file_detail', 2, NULL, NULL, NULL, 'sysFileInfo:detail', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-06-24 17:36:01', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256581, 1264622039642256531, '[0],[1264622039642256521],[1264622039642256531],', '文件上传', 'sys_file_mgr_sys_file_upload', 2, NULL, NULL, NULL, 'sysFileInfo:upload', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-06-24 17:34:29', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256591, 1264622039642256531, '[0],[1264622039642256521],[1264622039642256531],', '文件下载', 'sys_file_mgr_sys_file_download', 2, NULL, NULL, NULL, 'sysFileInfo:download', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-06-24 17:34:55', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256601, 1264622039642256531, '[0],[1264622039642256521],[1264622039642256531],', '图片预览', 'sys_file_mgr_sys_file_preview', 2, NULL, NULL, NULL, 'sysFileInfo:preview', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-06-24 17:35:19', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256611, 0, '[0],', '定时任务', 'sys_timers', 0, 'dashboard', '/timers', 'PageView', NULL, 'system', 1, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-01 17:17:20', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256621, 1264622039642256611, '[0],[1264622039642256611],', '任务管理', 'sys_timers_mgr', 1, NULL, '/timers', 'system/timers/index', NULL, 'system', 1, 'Y', NULL, NULL, 1, 22, NULL, 0, '2020-07-01 17:18:53', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256631, 1264622039642256621, '[0],[1264622039642256611],[1264622039642256621],', '定时任务查询', 'sys_timers_mgr_page', 2, NULL, NULL, NULL, 'sysTimers:page', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-01 17:19:43', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256641, 1264622039642256621, '[0],[1264622039642256611],[1264622039642256621],', '定时任务列表', 'sys_timers_mgr_list', 2, NULL, NULL, NULL, 'sysTimers:list', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-01 17:19:56', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256651, 1264622039642256621, '[0],[1264622039642256611],[1264622039642256621],', '定时任务详情', 'sys_timers_mgr_detail', 2, NULL, NULL, NULL, 'sysTimers:detail', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-01 17:20:10', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256661, 1264622039642256621, '[0],[1264622039642256611],[1264622039642256621],', '定时任务增加', 'sys_timers_mgr_add', 2, NULL, NULL, NULL, 'sysTimers:add', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-01 17:20:23', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256671, 1264622039642256621, '[0],[1264622039642256611],[1264622039642256621],', '定时任务删除', 'sys_timers_mgr_delete', 2, NULL, NULL, NULL, 'sysTimers:delete', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-01 17:20:33', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256681, 1264622039642256621, '[0],[1264622039642256611],[1264622039642256621],', '定时任务编辑', 'sys_timers_mgr_edit', 2, NULL, NULL, NULL, 'sysTimers:edit', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-01 17:20:43', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256691, 1264622039642256621, '[0],[1264622039642256611],[1264622039642256621],', '定时任务可执行列表', 'sys_timers_mgr_get_action_classes', 2, NULL, NULL, NULL, 'sysTimers:getActionClasses', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-01 17:22:16', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256701, 1264622039642256621, '[0],[1264622039642256611],[1264622039642256621],', '定时任务启动', 'sys_timers_mgr_start', 2, NULL, NULL, NULL, 'sysTimers:start', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-01 17:22:32', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1264622039642256711, 1264622039642256621, '[0],[1264622039642256611],[1264622039642256621],', '定时任务关闭', 'sys_timers_mgr_stop', 2, NULL, NULL, NULL, 'sysTimers:stop', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-01 17:22:43', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1380011988209373186, 0, '[0],', '元数据管理', 'main_metadata', 0, 'switcher', '/metadata', 'PageView', '', 'system', 1, 'Y', NULL, '', 2, 100, NULL, 0, '2021-04-08 12:17:52', 1265476890672672808, '2021-04-22 14:54:23', 1377879897444212737);
INSERT INTO `sys_menu` VALUES (1380017817176248322, 1380011988209373186, '[0],[1380011988209373186],', '元数据类型', 'system_metadataType', 1, NULL, '/metadataType', 'system/metadata/metadataType', '', 'system', 1, 'Y', NULL, '', 2, 100, NULL, 2, '2021-04-08 12:41:02', 1265476890672672808, '2021-04-08 13:32:18', 1265476890672672808);
INSERT INTO `sys_menu` VALUES (1380018324817055746, 1380011988209373186, '[0],[1380011988209373186],', '元数据列表', 'main_metadataList', 1, NULL, '/metadata', 'main/metadata/index', '', 'system', 1, 'Y', NULL, '', 2, 100, NULL, 0, '2021-04-08 12:43:03', 1265476890672672808, '2021-04-22 14:50:55', 1377879897444212737);
INSERT INTO `sys_menu` VALUES (1380036868661317633, 1264622039642255351, '[0],[1264622039642255341],[1264622039642255351],', 'test', 'test', 2, '', 'sysNotice:add', '', 'sysNotice:add', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-04-08 13:56:44', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1380331425961611266, 0, '[0],', '数据源管理', 'main_dataSource', 0, 'database', '/dataSource', 'PageView', '', 'system', 1, 'Y', NULL, '', 2, 100, '数据源管理', 0, '2021-04-09 09:27:12', 1265476890672672808, '2021-04-22 14:54:35', 1377879897444212737);
INSERT INTO `sys_menu` VALUES (1380332174741344257, 1380331425961611266, '[0],[1380331425961611266],', '数据源列表', 'main_dataSourceList', 1, NULL, '/dataSource', 'main/dataSource/index', '', 'system', 1, 'Y', NULL, '', 2, 100, NULL, 0, '2021-04-09 09:30:10', 1265476890672672808, '2021-04-22 14:54:57', 1377879897444212737);
INSERT INTO `sys_menu` VALUES (1381515284497088514, 1264622039642256111, '[0],[1264622039642255961],[1264622039642256111],', '查询子级字典值', 'sys_dict_mgr_dict_children_list', 2, '', '', '', 'sysDictData:children:list', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-04-12 15:51:26', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1381787504008015873, 1380018324817055746, '[0],[1380011988209373186],[1380018324817055746],', '元数据类型列表', 'system_metadataList_type', 2, '', '', '', 'sysDictData:findByCode', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-04-13 09:53:08', 1377879897444212737, '2021-04-13 09:54:05', 1377879897444212737);
INSERT INTO `sys_menu` VALUES (1381882594334797826, 1380331425961611266, '[0],[1380331425961611266],', '数据集列表', 'data_collect', 1, NULL, '/data', 'PageView', '', 'system', 1, 'Y', NULL, '', 2, 100, NULL, 2, '2021-04-13 16:10:59', 1377879897444212737, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1381883524719505410, 0, '[0],', '数据集管理', 'system_dataCollect', 0, 'profile', '/system/dataCollect', 'PageView', '', 'system', 0, 'Y', NULL, '', 2, 100, NULL, 2, '2021-04-13 16:14:41', 1377879897444212737, '2021-04-13 16:22:19', 1377879897444212737);
INSERT INTO `sys_menu` VALUES (1381883901347033090, 1380011988209373186, '[0],[1380011988209373186],', '数据集列表', 'main_dataCollectList', 1, NULL, '/metadata/dataCollect', 'main/metadata/dataCollect/index', '', 'system', 1, 'Y', NULL, '', 2, 100, NULL, 0, '2021-04-13 16:16:11', 1377879897444212737, '2021-04-22 14:50:48', 1377879897444212737);
INSERT INTO `sys_menu` VALUES (1381906786073636866, 1380011988209373186, '[0],[1380011988209373186],', '元数据字段', 'main_metadataColumn', 1, NULL, '/metadata/column', 'main/metadata/column/index', '', 'system', 1, 'N', NULL, '', 2, 100, NULL, 0, '2021-04-13 17:47:07', 1377879897444212737, '2021-04-23 17:55:24', 1377879897444212737);
INSERT INTO `sys_menu` VALUES (1382226257229340673, 1380018324817055746, '[0],[1380011988209373186],[1380018324817055746],', '查询元数据表', 'main_metadataList_pageTable', 2, '', '', '', 'metadata:metadataTable:page', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-04-14 14:56:35', 1265476890672672808, '2021-05-26 11:08:22', 1377879897444212737);
INSERT INTO `sys_menu` VALUES (1382228717184442370, 1380018324817055746, '[0],[1380011988209373186],[1380018324817055746],', '新增元数据表', 'main_metadataList_addTable', 2, '', '', '', 'metadata:metadataTable:add', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-04-14 15:06:21', 1265476890672672808, '2021-05-26 11:08:32', 1377879897444212737);
INSERT INTO `sys_menu` VALUES (1382250332676907009, 1380018324817055746, '[0],[1380011988209373186],[1380018324817055746],', '更新元数据表', 'main_metadataList_editTable', 2, '', '', '', 'metadata:metadataTable:edit', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-04-14 16:32:15', 1265476890672672808, '2021-04-22 14:51:27', 1377879897444212737);
INSERT INTO `sys_menu` VALUES (1382250525069631489, 1380018324817055746, '[0],[1380011988209373186],[1380018324817055746],', '删除元数据表', 'main_metadataList_deleteTable', 2, '', '', '', 'metadata:metadataTable:delete', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-04-14 16:33:01', 1265476890672672808, '2021-04-22 14:51:20', 1377879897444212737);
INSERT INTO `sys_menu` VALUES (1382289871906754562, 1381906786073636866, '[0],[1380011988209373186],[1381906786073636866],', '查询元数据字段', 'main_metadataColumn_page', 2, '', '', '', 'metadata:metadataColumn:page', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-04-14 19:09:22', 1265476890672672808, '2021-05-26 11:09:49', 1377879897444212737);
INSERT INTO `sys_menu` VALUES (1382290149993304066, 1381906786073636866, '[0],[1380011988209373186],[1381906786073636866],', '新增元数据字段', 'main_metadataColumn_add', 2, '', '', '', 'metadata:metadataColumn:add', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-04-14 19:10:28', 1265476890672672808, '2021-05-26 11:09:52', 1377879897444212737);
INSERT INTO `sys_menu` VALUES (1382290386606575618, 1381906786073636866, '[0],[1380011988209373186],[1381906786073636866],', '更新元数据字段', 'main_metadataColumn_edit', 2, '', '', '', 'metadata:metadataColumn:edit', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-04-14 19:11:25', 1265476890672672808, '2021-05-26 11:09:40', 1377879897444212737);
INSERT INTO `sys_menu` VALUES (1382290546879320065, 1381906786073636866, '[0],[1380011988209373186],[1381906786073636866],', '删除元数据字段', 'main_metadataColumn_delete', 2, '', '', '', 'metadata:metadataColumn:delete', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-04-14 19:12:03', 1265476890672672808, '2021-05-26 11:09:26', 1377879897444212737);
INSERT INTO `sys_menu` VALUES (1382521411372376065, 1381906786073636866, '[0],[1380011988209373186],[1381906786073636866],', '查询子级元数据', 'main_metadataColumn_childrenList', 2, '', '', '', 'metadata:metadataColumn:children:list', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-04-15 10:29:25', 1265476890672672808, '2021-05-26 11:09:32', 1377879897444212737);
INSERT INTO `sys_menu` VALUES (1382572364020641793, 1381906786073636866, '[0],[1380011988209373186],[1381906786073636866],', '检索元数据字段', 'main_metadataColumn_searchPage', 2, '', '', '', 'metadata:metadataColumn:searchPage', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-04-15 13:51:53', 1265476890672672808, '2021-05-26 11:09:35', 1377879897444212737);
INSERT INTO `sys_menu` VALUES (1382616615366545410, 1381883901347033090, '[0],[1380011988209373186],[1381883901347033090],', '数据集查询分页', 'main_dataCollectList_page', 2, '', '', '', 'dataCollect:page', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-04-15 16:47:44', 1265476890672672808, '2021-05-26 11:09:09', 1377879897444212737);
INSERT INTO `sys_menu` VALUES (1382616736468684802, 1381883901347033090, '[0],[1380011988209373186],[1381883901347033090],', '数据集新增', 'main_dataCollectList_add', 2, '', '', '', 'dataCollect:add', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-04-15 16:48:12', 1265476890672672808, '2021-05-26 11:09:12', 1377879897444212737);
INSERT INTO `sys_menu` VALUES (1382616857759567873, 1381883901347033090, '[0],[1380011988209373186],[1381883901347033090],', '数据集更新', 'main_dataCollectList_edit', 2, '', '', '', 'dataCollect:edit', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-04-15 16:48:41', 1265476890672672808, '2021-05-26 11:09:01', 1377879897444212737);
INSERT INTO `sys_menu` VALUES (1382617024927748098, 1381883901347033090, '[0],[1380011988209373186],[1381883901347033090],', '数据集删除', 'main_dataCollectList_delete', 2, '', '', '', 'dataCollect:delete', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-04-15 16:49:21', 1265476890672672808, '2021-04-26 11:12:41', 1377879897444212737);
INSERT INTO `sys_menu` VALUES (1382645666693046274, 1381883901347033090, '[0],[1380011988209373186],[1381883901347033090],', '数据集下属元数据表查询', 'main_dataCollectList_findByCodeMetadataTable', 2, '', '', '', 'dataCollect:findByCodeMetadataTable', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-04-15 18:43:10', 1265476890672672808, '2021-05-27 10:28:05', 1265476890672672808);
INSERT INTO `sys_menu` VALUES (1382646318777294850, 1380018324817055746, '[0],[1380011988209373186],[1380018324817055746],', '查询所有元数据表', 'main_metadataList_listTable', 2, '', '', '', 'metadata:metadataTable:list', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-04-15 18:45:45', 1265476890672672808, '2021-05-26 11:08:36', 1377879897444212737);
INSERT INTO `sys_menu` VALUES (1383029023851462657, 1264622039642256111, '[0],[1264622039642255961],[1264622039642256111],', '检索字典值', 'sys_dict_mgr_dict_search', 2, '', '', '', 'sysDictData:search', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-04-16 20:06:29', 1377879897444212737, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1383995035686100994, 1264622039642255851, '[0],[1264622039642255671],[1264622039642255851],', '角色拥有资源', 'sys_role_mgr_metadataTable_findColumnTree', 2, '', '', '', 'metadata:metadataTable:findColumnTree', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-04-19 12:05:05', 1265476890672672808, '2021-04-19 14:59:34', 1265476890672672808);
INSERT INTO `sys_menu` VALUES (1384039222561239041, 1264622039642255851, '[0],[1264622039642255671],[1264622039642255851],', '角色授权资源', 'sys_role_mgr_grant_metadata', 2, '', '', '', 'sysRole:grantMetadataColumn', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-04-19 15:00:40', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1384443675130994690, 1380018324817055746, '[0],[1380011988209373186],[1380018324817055746],', '导出excel', 'main_metadataList_exportExcel', 2, '', '', '', 'metadata:metadataTable:exportExcel', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-04-20 17:47:49', 1265476890672672808, '2021-05-26 11:08:28', 1377879897444212737);
INSERT INTO `sys_menu` VALUES (1384717362652639234, 1380018324817055746, '[0],[1380011988209373186],[1380018324817055746],', '下载导入模板', 'main_metadataList_downloadTemplate', 2, '', '', '', 'metadata:metadataTable:downloadTemplate', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-04-21 11:55:21', 1265476890672672808, '2021-05-26 11:08:39', 1377879897444212737);
INSERT INTO `sys_menu` VALUES (1384718359668011009, 1380018324817055746, '[0],[1380011988209373186],[1380018324817055746],', '导入excel', 'main_metadataList_importExcel', 2, '', '', '', 'metadata:metadataTable:importExcel', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-04-21 11:59:18', 1265476890672672808, '2021-05-26 11:08:18', 1377879897444212737);
INSERT INTO `sys_menu` VALUES (1385127823449530370, 0, '[0],', '数据采集管理', 'main_dataGather', 0, 'tool', '/dataGather', 'PageView', '', 'system', 1, 'Y', NULL, '', 2, 100, NULL, 0, '2021-04-22 15:06:22', 1377879897444212737, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1385128136558518274, 1385127823449530370, '[0],[1385127823449530370],', '数据采集', 'main_dataGatherList', 1, NULL, '/dataGather', 'main/dataGather/index', '', 'system', 1, 'Y', NULL, '', 2, 100, NULL, 0, '2021-04-22 15:07:37', 1377879897444212737, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1385145701343236098, 1385128136558518274, '[0],[1385127823449530370],[1385128136558518274],', '执行采取项', 'main_dataGatherList_exec', 2, '', '', '', 'dataGather:exec', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-04-22 16:17:25', 1377879897444212737, '2021-05-26 11:10:44', 1377879897444212737);
INSERT INTO `sys_menu` VALUES (1385196145327280130, 1385128136558518274, '[0],[1385127823449530370],[1385128136558518274],', '数据采集项添加', 'main_dataGatherList_add', 2, '', '', '', 'dataGather:add', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-04-22 19:37:51', 1377879897444212737, '2021-04-22 19:38:12', 1377879897444212737);
INSERT INTO `sys_menu` VALUES (1385196397581111297, 1385128136558518274, '[0],[1385127823449530370],[1385128136558518274],', '数据采集项状态更新', 'main_dataGatherList_updateGatherStatus', 2, '', '', '', 'dataGather:updateGatherStatus', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-04-22 19:38:52', 1377879897444212737, '2021-05-26 11:10:51', 1377879897444212737);
INSERT INTO `sys_menu` VALUES (1385196533380091905, 1385128136558518274, '[0],[1385127823449530370],[1385128136558518274],', '数据采集项删除', 'main_dataGatherList_delete', 2, '', '', '', 'dataGather:delete', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-04-22 19:39:24', 1377879897444212737, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1385196679186681857, 1385128136558518274, '[0],[1385127823449530370],[1385128136558518274],', '数据采集项分页查询', 'main_dataGatherList_page', 2, '', '', '', 'dataGather:page', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-04-22 19:39:59', 1377879897444212737, '2021-05-26 11:10:53', 1377879897444212737);
INSERT INTO `sys_menu` VALUES (1385418868288688129, 1385128136558518274, '[0],[1385127823449530370],[1385128136558518274],', '立即执行数据采集', 'main_dataGatherList_immediatelyExec', 2, '', '', '', 'dataGather:immediatelyExec', 'system', 0, 'Y', '', '', 2, 100, NULL, 2, '2021-04-23 10:22:53', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1385479705971953665, 1385128136558518274, '[0],[1385127823449530370],[1385128136558518274],', '下载采集内容', 'main_dataGatherList_downFolder', 2, '', '', '', 'dataGather:downFolder', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-04-23 14:24:38', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1386212893333704705, 1380332174741344257, '[0],[1380331425961611266],[1380332174741344257],', '数据源分页查询', 'main_dataSourceList_page', 2, '', '', '', 'dataSource:page', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-04-25 14:58:03', 1377879897444212737, '2021-04-25 17:13:15', 1265476890672672808);
INSERT INTO `sys_menu` VALUES (1386213055313530882, 1380332174741344257, '[0],[1380331425961611266],[1380332174741344257],', '数据源添加', 'main_dataSourceList_add', 2, '', '', '', 'dataSource:add', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-04-25 14:58:42', 1377879897444212737, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1386213279079649281, 1380332174741344257, '[0],[1380331425961611266],[1380332174741344257],', '数据源更新', 'main_dataSourceList_edit', 2, '', '', '', 'dataSource:edit', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-04-25 14:59:35', 1377879897444212737, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1386213470209888258, 1380332174741344257, '[0],[1380331425961611266],[1380332174741344257],', '数据源删除', 'main_dataSourceList_delete', 2, '', '', '', 'dataSource:delete', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-04-25 15:00:21', 1377879897444212737, '2021-04-25 17:13:05', 1265476890672672808);
INSERT INTO `sys_menu` VALUES (1386240388162482177, 1380332174741344257, '[0],[1380331425961611266],[1380332174741344257],', '数据源字段查询', 'main_dataSourceList_findColumnByCode', 2, '', '', '', 'dataSource:findColumnByCode', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-04-25 16:47:18', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1386508887090851841, 1380332174741344257, '[0],[1380331425961611266],[1380332174741344257],', '数据源下属数据集查询', 'main_dataSourceList_findCollectByCode', 2, '', '', '', 'dataSource:findCollectByCode', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-04-26 10:34:13', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1386518281488740354, 1381883901347033090, '[0],[1380011988209373186],[1381883901347033090],', '数据集查询不分页', 'main_dataCollectList_list', 2, '', '', '', 'dataCollect:list', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-04-26 11:11:33', 1377879897444212737, '2021-05-26 11:09:05', 1377879897444212737);
INSERT INTO `sys_menu` VALUES (1386581828772098050, 1380332174741344257, '[0],[1380331425961611266],[1380332174741344257],', '数据源上传文件', 'main_dataSourceList_addFile', 2, '', '', '', 'dataSource:addFile', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-04-26 15:24:04', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1386974045684686849, 1380332174741344257, '[0],[1380331425961611266],[1380332174741344257],', '数据源执行抽取任务', 'main_dataSourceList_execExtract', 2, '', '', '', 'dataSource:execExtract', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-04-27 17:22:36', 1377879897444212737, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1387595655663878145, 1380332174741344257, '[0],[1380331425961611266],[1380332174741344257],', '数据源获取文件', 'main_dataSourceList_getFile', 2, '', '', '', 'dataSource:getFile', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-04-29 10:32:39', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1390488580609593345, 1380332174741344257, '[0],[1380331425961611266],[1380332174741344257],', '数据源测试连接', 'main_dataSourceList_testConnect', 2, '', '', '', 'dataSource:testConnect', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-05-07 10:08:06', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1394211296968114177, 1385128136558518274, '[0],[1385127823449530370],[1385128136558518274],', '获取采集日志', 'main_dataGatherList_getGatherLog', 2, '', '', '', 'dataGather:getGatherLog', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-05-17 16:40:51', 1377879897444212737, '2021-05-26 11:10:57', 1377879897444212737);
INSERT INTO `sys_menu` VALUES (1395651227902574594, 1264622039642256111, '[0],[1264622039642255961],[1264622039642256111],', '字典值树状', 'sys_dict_mgr_dict_treeByCode', 2, '', '', '', 'sysDictData:treeByCode', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-05-21 16:02:37', 1377879897444212737, '2021-05-21 18:21:17', 1265476890672672808);
INSERT INTO `sys_menu` VALUES (1395686366678392834, 1264622039642256111, '[0],[1264622039642255961],[1264622039642256111],', '所有一级字典值', 'sys_dict_mgr_dict_listByParentIdIsNull', 2, '', '', '', 'sysDictData:listByParentIdIsNull', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-05-21 18:22:15', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_menu` VALUES (1395706117337223169, 1381906786073636866, '[0],[1380011988209373186],[1381906786073636866],', '元数据字段约束', 'main_metadataColumn_fieldConstraint', 2, '', '', '', 'metadata:metadataColumn:fieldConstraint', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-05-21 19:40:44', 1265476890672672808, '2021-05-26 11:09:28', 1377879897444212737);
INSERT INTO `sys_menu` VALUES (1395716838712233986, 1381906786073636866, '[0],[1380011988209373186],[1381906786073636866],', '元数据字段已约束列表', 'main_metadataColumn_fieldConstraintList', 2, '', '', '', 'metadata:metadataColumn:fieldConstraintList', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-05-21 20:23:20', 1265476890672672808, '2021-05-26 11:09:43', 1377879897444212737);
INSERT INTO `sys_menu` VALUES (1398905923907936257, 1380018324817055746, '[0],[1380011988209373186],[1380018324817055746],', 'as数据存入es', 'main_metadataList_asDataSaveES', 2, '', '', '', 'metadata:metadataTable:asDataSaveES', 'system', 0, 'Y', '', '', 2, 100, NULL, 0, '2021-05-30 15:35:37', 1377879897444212737, NULL, NULL);

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `title` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT '内容',
  `type` tinyint(4) NOT NULL COMMENT '类型（字典 1通知 2公告）',
  `public_user_id` bigint(20) NOT NULL COMMENT '发布人id',
  `public_user_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '发布人姓名',
  `public_org_id` bigint(20) NULL DEFAULT NULL COMMENT '发布机构id',
  `public_org_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发布机构名称',
  `public_time` datetime(0) NULL DEFAULT NULL COMMENT '发布时间',
  `cancel_time` datetime(0) NULL DEFAULT NULL COMMENT '撤回时间',
  `status` tinyint(4) NOT NULL COMMENT '状态（字典 0草稿 1发布 2撤回 3删除）',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '通知表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
INSERT INTO `sys_notice` VALUES (1304960081456066561, 'qqqq', 'qqqqqq<p></p>', 1, 1265476890672672808, '超级管理员', NULL, NULL, '2020-09-13 09:48:23', '2020-09-13 09:52:26', 3, '2020-09-13 09:48:23', 1265476890672672808, '2020-09-13 09:52:27', 1280700700074041345);
INSERT INTO `sys_notice` VALUES (1304960124862918657, '123123123', '<p>23123123123123</p>', 2, 1265476890672672808, '超级管理员', NULL, NULL, '2020-09-13 09:48:33', '2020-09-13 09:52:28', 3, '2020-09-13 09:48:33', 1265476890672672808, '2020-09-13 09:52:40', 1280700700074041345);
INSERT INTO `sys_notice` VALUES (1304961721068220417, '南方的冬天', '<p><img src=\"https://timgsa.baidu.com/timg?image&amp;quality=80&amp;size=b9999_10000&amp;sec=1599972071688&amp;di=9d692807717018e9f36dc209b8f2a290&amp;imgtype=0&amp;src=http%3A%2F%2Fimg1.imgtn.bdimg.com%2Fit%2Fu%3D3178869736%2C1430240761%26fm%3D214%26gp%3D0.jpg\">&nbsp;&nbsp;<br></p>', 1, 1280700700074041345, '老俞', 1265476890672672771, '研发部', '2020-09-13 09:54:54', NULL, 1, '2020-09-13 09:54:54', 1280700700074041345, NULL, NULL);
INSERT INTO `sys_notice` VALUES (1304964964817104898, '南方的冬天', '<div style=\"text-align: center;\"><img src=\"https://timgsa.baidu.com/timg?image&amp;quality=80&amp;size=b9999_10000&amp;sec=1599972936240&amp;di=0c65610a70a7f8de26e84f51da77604f&amp;imgtype=0&amp;src=http%3A%2F%2Fimg1.imgtn.bdimg.com%2Fit%2Fu%3D3178869736%2C1430240761%26fm%3D214%26gp%3D0.jpg\">&nbsp;&nbsp;<br></div><p></p>', 1, 1265476890672672808, '超级管理员', NULL, NULL, '2020-09-13 10:07:47', NULL, 1, '2020-09-13 10:07:47', 1265476890672672808, NULL, NULL);

-- ----------------------------
-- Table structure for sys_notice_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice_user`;
CREATE TABLE `sys_notice_user`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `notice_id` bigint(20) NOT NULL COMMENT '通知公告id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `status` tinyint(4) NOT NULL COMMENT '状态（字典 0未读 1已读）',
  `read_time` datetime(0) NULL DEFAULT NULL COMMENT '阅读时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户数据范围表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_notice_user
-- ----------------------------
INSERT INTO `sys_notice_user` VALUES (1304960081539952642, 1304960081456066561, 1280700700074041345, 0, NULL);
INSERT INTO `sys_notice_user` VALUES (1304960124934221825, 1304960124862918657, 1280700700074041345, 1, '2020-09-13 09:49:02');
INSERT INTO `sys_notice_user` VALUES (1304961721131134977, 1304961721068220417, 1280700700074041345, 1, '2020-09-13 09:54:56');
INSERT INTO `sys_notice_user` VALUES (1304964964875825153, 1304964964817104898, 1280700700074041345, 0, NULL);

-- ----------------------------
-- Table structure for sys_oauth_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_oauth_user`;
CREATE TABLE `sys_oauth_user`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `uuid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '第三方平台的用户唯一id',
  `access_token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户授权的token',
  `nick_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `blog` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户网址',
  `company` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所在公司',
  `location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '位置',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `gender` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `source` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户来源',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户备注（各平台中的用户个人介绍）',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '更新用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '第三方认证用户信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_oauth_user
-- ----------------------------

-- ----------------------------
-- Table structure for sys_op_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_op_log`;
CREATE TABLE `sys_op_log`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `op_type` tinyint(4) NULL DEFAULT NULL COMMENT '操作类型',
  `success` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否执行成功（Y-是，N-否）',
  `message` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT '具体消息',
  `ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ip',
  `location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `browser` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '浏览器',
  `os` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作系统',
  `url` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求地址',
  `class_name` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类名称',
  `method_name` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '方法名称',
  `req_method` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方式（GET POST PUT DELETE)',
  `param` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '请求参数',
  `result` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '返回结果',
  `op_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  `account` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作账号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统操作日志表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_op_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_org
-- ----------------------------
DROP TABLE IF EXISTS `sys_org`;
CREATE TABLE `sys_org`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `pid` bigint(20) NOT NULL COMMENT '父id',
  `pids` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '父ids',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编码',
  `sort` int(11) NOT NULL COMMENT '排序',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '状态（字典 0正常 1停用 2删除）',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统组织机构表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_org
-- ----------------------------
INSERT INTO `sys_org` VALUES (1265476890651701250, 0, '[0],', '华夏集团', 'hxjt', 100, '华夏集团总公司', 2, '2020-03-26 16:50:53', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_org` VALUES (1265476890672672769, 1265476890651701250, '[0],[1265476890651701250],', '华夏集团北京分公司', 'hxjt_bj', 100, '华夏集团北京分公司', 2, '2020-03-26 16:55:42', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_org` VALUES (1265476890672672770, 1265476890651701250, '[0],[1265476890651701250],', '华夏集团成都分公司', 'hxjt_cd', 100, '华夏集团成都分公司', 2, '2020-03-26 16:56:02', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_org` VALUES (1265476890672672771, 1265476890672672769, '[0],[1265476890651701250],[1265476890672672769],', '研发部', 'hxjt_bj_yfb', 100, '华夏集团北京分公司研发部', 2, '2020-03-26 16:56:36', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_org` VALUES (1265476890672672772, 1265476890672672769, '[0],[1265476890651701250],[1265476890672672769],', '企划部', 'hxjt_bj_qhb', 100, '华夏集团北京分公司企划部', 2, '2020-03-26 16:57:06', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_org` VALUES (1265476890672672773, 1265476890672672770, '[0],[1265476890651701250],[1265476890672672770],', '市场部', 'hxjt_cd_scb', 100, '华夏集团成都分公司市场部', 2, '2020-03-26 16:57:35', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_org` VALUES (1265476890672672774, 1265476890672672770, '[0],[1265476890651701250],[1265476890672672770],', '财务部', 'hxjt_cd_cwb', 100, '华夏集团成都分公司财务部', 2, '2020-03-26 16:58:01', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_org` VALUES (1265476890672672775, 1265476890672672773, '[0],[1265476890651701250],[1265476890672672770],[1265476890672672773],', '市场部二部', 'hxjt_cd_scb_2b', 100, '华夏集团成都分公司市场部二部', 2, '2020-04-06 15:36:50', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_org` VALUES (1377879440705478658, 0, '[0],', '复旦大学', 'fddx', 100, NULL, 0, '2021-04-02 15:03:53', 1265476890672672808, NULL, NULL);
INSERT INTO `sys_org` VALUES (1377879536427884546, 1377879440705478658, '[0],[1377879440705478658],', '复旦大学图书馆', 'fddxtsg', 101, NULL, 0, '2021-04-02 15:04:16', 1265476890672672808, NULL, NULL);

-- ----------------------------
-- Table structure for sys_pos
-- ----------------------------
DROP TABLE IF EXISTS `sys_pos`;
CREATE TABLE `sys_pos`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编码',
  `sort` int(11) NOT NULL COMMENT '排序',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '状态（字典 0正常 1停用 2删除）',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `CODE_UNI`(`code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统职位表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_pos
-- ----------------------------
INSERT INTO `sys_pos` VALUES (1265476890672672787, '总经理', 'zjl', 100, '总经理职位', 2, '2020-03-26 19:28:54', 1265476890672672808, '2021-04-02 15:03:32', 1265476890672672808);
INSERT INTO `sys_pos` VALUES (1265476890672672788, '副总经理', 'fzjl', 100, '副总经理职位', 2, '2020-03-26 19:29:57', 1265476890672672808, '2021-04-02 15:03:34', 1265476890672672808);
INSERT INTO `sys_pos` VALUES (1265476890672672789, '部门经理', 'bmjl', 100, '部门经理职位', 2, '2020-03-26 19:31:49', 1265476890672672808, '2021-04-02 15:03:35', 1265476890672672808);
INSERT INTO `sys_pos` VALUES (1265476890672672790, '工作人员', 'gzry', 100, '工作人员职位', 0, '2020-05-27 11:32:00', 1265476890672672808, '2020-06-01 10:51:35', 1265476890672672808);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(20) NOT NULL COMMENT '主键id',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编码',
  `sort` int(11) NOT NULL COMMENT '序号',
  `data_scope_type` tinyint(4) NOT NULL DEFAULT 1 COMMENT '数据范围类型（字典 1全部数据 2本部门及以下数据 3本部门数据 4仅本人数据 5自定义数据）',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '状态（字典 0正常 1停用 2删除）',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `code`(`code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1265476890672672817, '组织架构管理员', 'ent_manager_role', 100, 1, '组织架构管理员', 0, '2020-04-02 19:27:26', 1265476890672672808, '2020-09-12 15:54:07', 1265476890672672808);
INSERT INTO `sys_role` VALUES (1265476890672672818, '权限管理员', 'auth_role', 101, 5, '权限管理员', 0, '2020-04-02 19:28:40', 1265476890672672808, '2020-07-16 10:52:21', 1265476890672672808);
INSERT INTO `sys_role` VALUES (1265476890672672819, '公告发布员', 'notice_produce_role', 102, 5, '公告发布员', 0, '2020-05-29 15:48:11', 1265476890672672808, '2020-08-08 19:28:34', 1265476890672672808);
INSERT INTO `sys_role` VALUES (1380014845910323201, '开发人员', 'exploit', 100, 1, '开发人员权限', 0, '2021-04-08 12:29:13', 1265476890672672808, '2021-04-08 12:34:34', 1265476890672672808);

-- ----------------------------
-- Table structure for sys_role_data_scope
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_data_scope`;
CREATE TABLE `sys_role_data_scope`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `org_id` bigint(20) NOT NULL COMMENT '机构id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统角色数据范围表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_data_scope
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统角色菜单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1304366872187256834, 1265476890672672818, 1264622039642255671);
INSERT INTO `sys_role_menu` VALUES (1304366872602492929, 1265476890672672818, 1264622039642255681);
INSERT INTO `sys_role_menu` VALUES (1304366873026117634, 1265476890672672818, 1264622039642255761);
INSERT INTO `sys_role_menu` VALUES (1304366873449742337, 1265476890672672818, 1264622039642255851);
INSERT INTO `sys_role_menu` VALUES (1304366873864978433, 1265476890672672818, 1264622039642255691);
INSERT INTO `sys_role_menu` VALUES (1304366874284408834, 1265476890672672818, 1264622039642255701);
INSERT INTO `sys_role_menu` VALUES (1304366874703839233, 1265476890672672818, 1264622039642255711);
INSERT INTO `sys_role_menu` VALUES (1304366875119075330, 1265476890672672818, 1264622039642255721);
INSERT INTO `sys_role_menu` VALUES (1304366875538505730, 1265476890672672818, 1264622039642255731);
INSERT INTO `sys_role_menu` VALUES (1304366875962130433, 1265476890672672818, 1264622039642255741);
INSERT INTO `sys_role_menu` VALUES (1304366876377366529, 1265476890672672818, 1264622039642255751);
INSERT INTO `sys_role_menu` VALUES (1304366876800991233, 1265476890672672818, 1264622039642255771);
INSERT INTO `sys_role_menu` VALUES (1304366877216227330, 1265476890672672818, 1264622039642255781);
INSERT INTO `sys_role_menu` VALUES (1304366877639852033, 1265476890672672818, 1264622039642255791);
INSERT INTO `sys_role_menu` VALUES (1304366878067671041, 1265476890672672818, 1264622039642255801);
INSERT INTO `sys_role_menu` VALUES (1304366878487101441, 1265476890672672818, 1264622039642255811);
INSERT INTO `sys_role_menu` VALUES (1304366878898143233, 1265476890672672818, 1264622039642255821);
INSERT INTO `sys_role_menu` VALUES (1304366879325962242, 1265476890672672818, 1264622039642255831);
INSERT INTO `sys_role_menu` VALUES (1304366879745392641, 1265476890672672818, 1264622039642255841);
INSERT INTO `sys_role_menu` VALUES (1304366880160628738, 1265476890672672818, 1264622039642255881);
INSERT INTO `sys_role_menu` VALUES (1304366880580059138, 1265476890672672818, 1264622039642255891);
INSERT INTO `sys_role_menu` VALUES (1304366880999489537, 1265476890672672818, 1264622039642255901);
INSERT INTO `sys_role_menu` VALUES (1304366881423114242, 1265476890672672818, 1264622039642255911);
INSERT INTO `sys_role_menu` VALUES (1304366881838350338, 1265476890672672818, 1264622039642255921);
INSERT INTO `sys_role_menu` VALUES (1304366882261975042, 1265476890672672818, 1264622039642255931);
INSERT INTO `sys_role_menu` VALUES (1304366882685599745, 1265476890672672818, 1264622039642255941);
INSERT INTO `sys_role_menu` VALUES (1304366883100835842, 1265476890672672818, 1264622039642255951);
INSERT INTO `sys_role_menu` VALUES (1304366883520266242, 1265476890672672818, 1264622039642255861);
INSERT INTO `sys_role_menu` VALUES (1304366883939696642, 1265476890672672818, 1264622039642255871);
INSERT INTO `sys_role_menu` VALUES (1304366884363321346, 1265476890672672818, 1264622039642257021);
INSERT INTO `sys_role_menu` VALUES (1304366884782751746, 1265476890672672818, 1264622039642257031);
INSERT INTO `sys_role_menu` VALUES (1304366885197987842, 1265476890672672818, 1264622039642256831);
INSERT INTO `sys_role_menu` VALUES (1304366885617418242, 1265476890672672818, 1264622039642257261);
INSERT INTO `sys_role_menu` VALUES (1304366886045237250, 1265476890672672818, 1264622039642257271);
INSERT INTO `sys_role_menu` VALUES (1304366886473056258, 1265476890672672818, 1264622039642257301);
INSERT INTO `sys_role_menu` VALUES (1304366886884098050, 1265476890672672818, 1264622039642257321);
INSERT INTO `sys_role_menu` VALUES (1304366887307722754, 1265476890672672818, 1264622039642257331);
INSERT INTO `sys_role_menu` VALUES (1304366887722958850, 1265476890672672818, 1264622039642257471);
INSERT INTO `sys_role_menu` VALUES (1304366888142389250, 1265476890672672818, 1264622039642257481);
INSERT INTO `sys_role_menu` VALUES (1304366888566013954, 1265476890672672818, 1264622039642257341);
INSERT INTO `sys_role_menu` VALUES (1304366888981250049, 1265476890672672818, 1264622039642257411);
INSERT INTO `sys_role_menu` VALUES (1304366889404874754, 1265476890672672818, 1264622039642257421);
INSERT INTO `sys_role_menu` VALUES (1304366889820110850, 1265476890672672818, 1264622039642257431);
INSERT INTO `sys_role_menu` VALUES (1304366890235346946, 1265476890672672818, 1264622039642257441);
INSERT INTO `sys_role_menu` VALUES (1304366890663165954, 1265476890672672818, 1264622039642257451);
INSERT INTO `sys_role_menu` VALUES (1304366891082596354, 1265476890672672818, 1264622039642257461);
INSERT INTO `sys_role_menu` VALUES (1304366891506221057, 1265476890672672818, 1264622039642257351);
INSERT INTO `sys_role_menu` VALUES (1304366891925651458, 1265476890672672818, 1264622039642257361);
INSERT INTO `sys_role_menu` VALUES (1304366892345081858, 1265476890672672818, 1264622039642257371);
INSERT INTO `sys_role_menu` VALUES (1304366892764512258, 1265476890672672818, 1264622039642257381);
INSERT INTO `sys_role_menu` VALUES (1304366893183942658, 1265476890672672818, 1264622039642257391);
INSERT INTO `sys_role_menu` VALUES (1304366893607567361, 1265476890672672818, 1264622039642257401);
INSERT INTO `sys_role_menu` VALUES (1304366894031192065, 1265476890672672818, 1264622039642257491);
INSERT INTO `sys_role_menu` VALUES (1304366894446428162, 1265476890672672818, 1264622039642257501);
INSERT INTO `sys_role_menu` VALUES (1304366894865858562, 1265476890672672818, 1264622039642257511);
INSERT INTO `sys_role_menu` VALUES (1304366895285288961, 1265476890672672818, 1264622039642255591);
INSERT INTO `sys_role_menu` VALUES (1304366895708913665, 1265476890672672818, 1264622039642257061);
INSERT INTO `sys_role_menu` VALUES (1304366896132538369, 1265476890672672818, 1264622039642257462);
INSERT INTO `sys_role_menu` VALUES (1304366896556163074, 1265476890672672818, 1264622039642256912);
INSERT INTO `sys_role_menu` VALUES (1304366896979787777, 1265476890672672818, 1264622039642255421);
INSERT INTO `sys_role_menu` VALUES (1304366897399218178, 1265476890672672818, 1264622039642257022);
INSERT INTO `sys_role_menu` VALUES (1304366897827037185, 1265476890672672818, 1264622039642256821);
INSERT INTO `sys_role_menu` VALUES (1304366898242273282, 1265476890672672818, 1264622039642257311);
INSERT INTO `sys_role_menu` VALUES (1304366898670092290, 1265476890672672818, 1264622039642257312);
INSERT INTO `sys_role_menu` VALUES (1304366899089522690, 1265476890672672818, 1264622039642257313);
INSERT INTO `sys_role_menu` VALUES (1304366899508953089, 1265476890672672818, 1264622039642257314);
INSERT INTO `sys_role_menu` VALUES (1304366899932577793, 1265476890672672818, 1264622039642257522);
INSERT INTO `sys_role_menu` VALUES (1304366900352008193, 1265476890672672818, 1264622039642257523);
INSERT INTO `sys_role_menu` VALUES (1304366900771438594, 1265476890672672818, 1264622039642257524);
INSERT INTO `sys_role_menu` VALUES (1304366901190868994, 1265476890672672818, 1264622039642257525);
INSERT INTO `sys_role_menu` VALUES (1304366901610299394, 1265476890672672818, 1264622039642257531);
INSERT INTO `sys_role_menu` VALUES (1304366902033924097, 1265476890672672818, 1264622039642257532);
INSERT INTO `sys_role_menu` VALUES (1307864773769273346, 1265476890672672819, 1264622039642256431);
INSERT INTO `sys_role_menu` VALUES (1307864774197092353, 1265476890672672819, 1264622039642256421);
INSERT INTO `sys_role_menu` VALUES (1307864774624911362, 1265476890672672819, 1264622039642256441);
INSERT INTO `sys_role_menu` VALUES (1307864775048536065, 1265476890672672819, 1264622039642256451);
INSERT INTO `sys_role_menu` VALUES (1307864775467966465, 1265476890672672819, 1264622039642256461);
INSERT INTO `sys_role_menu` VALUES (1307864775887396866, 1265476890672672819, 1264622039642256471);
INSERT INTO `sys_role_menu` VALUES (1307864776311021570, 1265476890672672819, 1264622039642256481);
INSERT INTO `sys_role_menu` VALUES (1307864776730451969, 1265476890672672819, 1264622039642256491);
INSERT INTO `sys_role_menu` VALUES (1307864777154076673, 1265476890672672819, 1264622039642256501);
INSERT INTO `sys_role_menu` VALUES (1307864777573507074, 1265476890672672819, 1264622039642256511);
INSERT INTO `sys_role_menu` VALUES (1307864778005520386, 1265476890672672819, 1264622039642255421);
INSERT INTO `sys_role_menu` VALUES (1307864778424950785, 1265476890672672819, 1264622039642257321);
INSERT INTO `sys_role_menu` VALUES (1307864778840186881, 1265476890672672819, 1264622039642257331);
INSERT INTO `sys_role_menu` VALUES (1307864779263811585, 1265476890672672819, 1264622039642257021);
INSERT INTO `sys_role_menu` VALUES (1307864779683241986, 1265476890672672819, 1264622039642257011);
INSERT INTO `sys_role_menu` VALUES (1307864780106866689, 1265476890672672819, 1264622039642256831);
INSERT INTO `sys_role_menu` VALUES (1307864780530491393, 1265476890672672819, 1264622039642257061);
INSERT INTO `sys_role_menu` VALUES (1307864780945727489, 1265476890672672819, 1264622039642257501);
INSERT INTO `sys_role_menu` VALUES (1307864781369352193, 1265476890672672819, 1264622039642257491);
INSERT INTO `sys_role_menu` VALUES (1307864781792976897, 1265476890672672819, 1264622039642257511);
INSERT INTO `sys_role_menu` VALUES (1307864782216601602, 1265476890672672819, 1264622039642257271);
INSERT INTO `sys_role_menu` VALUES (1307864782631837697, 1265476890672672819, 1264622039642257261);
INSERT INTO `sys_role_menu` VALUES (1307864783063851009, 1265476890672672819, 1264622039642257301);
INSERT INTO `sys_role_menu` VALUES (1307864783483281410, 1265476890672672819, 1264622039642257471);
INSERT INTO `sys_role_menu` VALUES (1307864783902711809, 1265476890672672819, 1264622039642257341);
INSERT INTO `sys_role_menu` VALUES (1307864784322142210, 1265476890672672819, 1264622039642257481);
INSERT INTO `sys_role_menu` VALUES (1307864784745766913, 1265476890672672819, 1264622039642257411);
INSERT INTO `sys_role_menu` VALUES (1307864785169391618, 1265476890672672819, 1264622039642257431);
INSERT INTO `sys_role_menu` VALUES (1307864785588822018, 1265476890672672819, 1264622039642257421);
INSERT INTO `sys_role_menu` VALUES (1307864786012446722, 1265476890672672819, 1264622039642257441);
INSERT INTO `sys_role_menu` VALUES (1307864786436071426, 1265476890672672819, 1264622039642257451);
INSERT INTO `sys_role_menu` VALUES (1307864786859696130, 1265476890672672819, 1264622039642257461);
INSERT INTO `sys_role_menu` VALUES (1307864787274932225, 1265476890672672819, 1264622039642257351);
INSERT INTO `sys_role_menu` VALUES (1307864787702751233, 1265476890672672819, 1264622039642257361);
INSERT INTO `sys_role_menu` VALUES (1307864788113793026, 1265476890672672819, 1264622039642257371);
INSERT INTO `sys_role_menu` VALUES (1307864788541612034, 1265476890672672819, 1264622039642257381);
INSERT INTO `sys_role_menu` VALUES (1307864788961042433, 1265476890672672819, 1264622039642257391);
INSERT INTO `sys_role_menu` VALUES (1307864789384667138, 1265476890672672819, 1264622039642257401);
INSERT INTO `sys_role_menu` VALUES (1307864789808291841, 1265476890672672819, 1264622039642257462);
INSERT INTO `sys_role_menu` VALUES (1307864790227722241, 1265476890672672819, 1264622039642257031);
INSERT INTO `sys_role_menu` VALUES (1307864790659735554, 1265476890672672819, 1264622039642256912);
INSERT INTO `sys_role_menu` VALUES (1307864791079165953, 1265476890672672819, 1264622039642257022);
INSERT INTO `sys_role_menu` VALUES (1307864791494402050, 1265476890672672819, 1264622039642257311);
INSERT INTO `sys_role_menu` VALUES (1307864791913832450, 1265476890672672819, 1264622039642257312);
INSERT INTO `sys_role_menu` VALUES (1307864792345845762, 1265476890672672819, 1264622039642257313);
INSERT INTO `sys_role_menu` VALUES (1307864792769470465, 1265476890672672819, 1264622039642257314);
INSERT INTO `sys_role_menu` VALUES (1307864793193095169, 1265476890672672819, 1264622039642257522);
INSERT INTO `sys_role_menu` VALUES (1307864793612525570, 1265476890672672819, 1264622039642257523);
INSERT INTO `sys_role_menu` VALUES (1307864794027761665, 1265476890672672819, 1264622039642257524);
INSERT INTO `sys_role_menu` VALUES (1307864794459774978, 1265476890672672819, 1264622039642257525);
INSERT INTO `sys_role_menu` VALUES (1307864794875011073, 1265476890672672819, 1264622039642257532);
INSERT INTO `sys_role_menu` VALUES (1307864795307024385, 1265476890672672819, 1264622039642257531);
INSERT INTO `sys_role_menu` VALUES (1307864795722260482, 1265476890672672819, 1264622039642256821);
INSERT INTO `sys_role_menu` VALUES (1380014262977564674, 1265476890672672817, 1264622039642255311);
INSERT INTO `sys_role_menu` VALUES (1380014262981758977, 1265476890672672817, 1264622039642255331);
INSERT INTO `sys_role_menu` VALUES (1380014262985953282, 1265476890672672817, 1264622039642255321);
INSERT INTO `sys_role_menu` VALUES (1380014262990147585, 1265476890672672817, 1264622039642255341);
INSERT INTO `sys_role_menu` VALUES (1380014262994341890, 1265476890672672817, 1264622039642255351);
INSERT INTO `sys_role_menu` VALUES (1380014262998536194, 1265476890672672817, 1264622039642255361);
INSERT INTO `sys_role_menu` VALUES (1380014262998536195, 1265476890672672817, 1264622039642255371);
INSERT INTO `sys_role_menu` VALUES (1380014263002730497, 1265476890672672817, 1264622039642255381);
INSERT INTO `sys_role_menu` VALUES (1380014263002730498, 1265476890672672817, 1264622039642255391);
INSERT INTO `sys_role_menu` VALUES (1380014263006924801, 1265476890672672817, 1264622039642255401);
INSERT INTO `sys_role_menu` VALUES (1380014263011119106, 1265476890672672817, 1264622039642255411);
INSERT INTO `sys_role_menu` VALUES (1380014263011119107, 1265476890672672817, 1264622039642255421);
INSERT INTO `sys_role_menu` VALUES (1380014263015313409, 1265476890672672817, 1264622039642255431);
INSERT INTO `sys_role_menu` VALUES (1380014263015313410, 1265476890672672817, 1264622039642255441);
INSERT INTO `sys_role_menu` VALUES (1380014263019507714, 1265476890672672817, 1264622039642255451);
INSERT INTO `sys_role_menu` VALUES (1380014263019507715, 1265476890672672817, 1264622039642255461);
INSERT INTO `sys_role_menu` VALUES (1380014263023702017, 1265476890672672817, 1264622039642255471);
INSERT INTO `sys_role_menu` VALUES (1380014263027896321, 1265476890672672817, 1264622039642255481);
INSERT INTO `sys_role_menu` VALUES (1380014263027896322, 1265476890672672817, 1264622039642255491);
INSERT INTO `sys_role_menu` VALUES (1380014263032090626, 1265476890672672817, 1264622039642255501);
INSERT INTO `sys_role_menu` VALUES (1380014263032090627, 1265476890672672817, 1264622039642255511);
INSERT INTO `sys_role_menu` VALUES (1380014263036284930, 1265476890672672817, 1264622039642255521);
INSERT INTO `sys_role_menu` VALUES (1380014263036284931, 1265476890672672817, 1264622039642255531);
INSERT INTO `sys_role_menu` VALUES (1380014263040479234, 1265476890672672817, 1264622039642255541);
INSERT INTO `sys_role_menu` VALUES (1380014263040479235, 1265476890672672817, 1264622039642255551);
INSERT INTO `sys_role_menu` VALUES (1380014263044673538, 1265476890672672817, 1264622039642255561);
INSERT INTO `sys_role_menu` VALUES (1380014263048867841, 1265476890672672817, 1264622039642255571);
INSERT INTO `sys_role_menu` VALUES (1380014263048867842, 1265476890672672817, 1264622039642255581);
INSERT INTO `sys_role_menu` VALUES (1380014263053062145, 1265476890672672817, 1264622039642255591);
INSERT INTO `sys_role_menu` VALUES (1380014263053062146, 1265476890672672817, 1264622039642255601);
INSERT INTO `sys_role_menu` VALUES (1380014263057256450, 1265476890672672817, 1264622039642255621);
INSERT INTO `sys_role_menu` VALUES (1380014263057256451, 1265476890672672817, 1264622039642255631);
INSERT INTO `sys_role_menu` VALUES (1380014263061450753, 1265476890672672817, 1264622039642255641);
INSERT INTO `sys_role_menu` VALUES (1380014263061450754, 1265476890672672817, 1264622039642255651);
INSERT INTO `sys_role_menu` VALUES (1380014263065645057, 1265476890672672817, 1264622039642255661);
INSERT INTO `sys_role_menu` VALUES (1380014263065645058, 1265476890672672817, 1264622039642255611);
INSERT INTO `sys_role_menu` VALUES (1380014263069839362, 1265476890672672817, 1264622039642257321);
INSERT INTO `sys_role_menu` VALUES (1380014263069839363, 1265476890672672817, 1264622039642257331);
INSERT INTO `sys_role_menu` VALUES (1380014263074033665, 1265476890672672817, 1264622039642257471);
INSERT INTO `sys_role_menu` VALUES (1380014263078227970, 1265476890672672817, 1264622039642257481);
INSERT INTO `sys_role_menu` VALUES (1380014263078227971, 1265476890672672817, 1264622039642257341);
INSERT INTO `sys_role_menu` VALUES (1380014263082422274, 1265476890672672817, 1264622039642257411);
INSERT INTO `sys_role_menu` VALUES (1380014263082422275, 1265476890672672817, 1264622039642257421);
INSERT INTO `sys_role_menu` VALUES (1380014263086616577, 1265476890672672817, 1264622039642257431);
INSERT INTO `sys_role_menu` VALUES (1380014263090810881, 1265476890672672817, 1264622039642257441);
INSERT INTO `sys_role_menu` VALUES (1380014263090810882, 1265476890672672817, 1264622039642257451);
INSERT INTO `sys_role_menu` VALUES (1380014263095005185, 1265476890672672817, 1264622039642257461);
INSERT INTO `sys_role_menu` VALUES (1380014263095005186, 1265476890672672817, 1264622039642257351);
INSERT INTO `sys_role_menu` VALUES (1380014263099199490, 1265476890672672817, 1264622039642257361);
INSERT INTO `sys_role_menu` VALUES (1380014263099199491, 1265476890672672817, 1264622039642257371);
INSERT INTO `sys_role_menu` VALUES (1380014263103393793, 1265476890672672817, 1264622039642257381);
INSERT INTO `sys_role_menu` VALUES (1380014263103393794, 1265476890672672817, 1264622039642257391);
INSERT INTO `sys_role_menu` VALUES (1380014263107588097, 1265476890672672817, 1264622039642257401);
INSERT INTO `sys_role_menu` VALUES (1380014263111782402, 1265476890672672817, 1264622039642257491);
INSERT INTO `sys_role_menu` VALUES (1380014263111782403, 1265476890672672817, 1264622039642257501);
INSERT INTO `sys_role_menu` VALUES (1380014263115976706, 1265476890672672817, 1264622039642257511);
INSERT INTO `sys_role_menu` VALUES (1380014263115976707, 1265476890672672817, 1264622039642256831);
INSERT INTO `sys_role_menu` VALUES (1380014263120171009, 1265476890672672817, 1264622039642257031);
INSERT INTO `sys_role_menu` VALUES (1380014263120171010, 1265476890672672817, 1264622039642257021);
INSERT INTO `sys_role_menu` VALUES (1380014263124365314, 1265476890672672817, 1264622039642257061);
INSERT INTO `sys_role_menu` VALUES (1380014263124365315, 1265476890672672817, 1264622039642257261);
INSERT INTO `sys_role_menu` VALUES (1380014263128559617, 1265476890672672817, 1264622039642257301);
INSERT INTO `sys_role_menu` VALUES (1380014263128559618, 1265476890672672817, 1264622039642257271);
INSERT INTO `sys_role_menu` VALUES (1380014263132753921, 1265476890672672817, 1264622039642257462);
INSERT INTO `sys_role_menu` VALUES (1380014263136948225, 1265476890672672817, 1264622039642256912);
INSERT INTO `sys_role_menu` VALUES (1380014263136948226, 1265476890672672817, 1264622039642255911);
INSERT INTO `sys_role_menu` VALUES (1380014263141142529, 1265476890672672817, 1264622039642257522);
INSERT INTO `sys_role_menu` VALUES (1380014263141142530, 1265476890672672817, 1264622039642257523);
INSERT INTO `sys_role_menu` VALUES (1380014263145336833, 1265476890672672817, 1264622039642257524);
INSERT INTO `sys_role_menu` VALUES (1380014263145336834, 1265476890672672817, 1264622039642257525);
INSERT INTO `sys_role_menu` VALUES (1380014263149531137, 1265476890672672817, 1264622039642257532);
INSERT INTO `sys_role_menu` VALUES (1380014263149531138, 1265476890672672817, 1264622039642257531);
INSERT INTO `sys_role_menu` VALUES (1380014263153725441, 1265476890672672817, 1264622039642257311);
INSERT INTO `sys_role_menu` VALUES (1380014263153725442, 1265476890672672817, 1264622039642257312);
INSERT INTO `sys_role_menu` VALUES (1380014263157919745, 1265476890672672817, 1264622039642257313);
INSERT INTO `sys_role_menu` VALUES (1380014263157919746, 1265476890672672817, 1264622039642257314);
INSERT INTO `sys_role_menu` VALUES (1380014263162114050, 1265476890672672817, 1264622039642256821);
INSERT INTO `sys_role_menu` VALUES (1380014263162114051, 1265476890672672817, 1264622039642257022);
INSERT INTO `sys_role_menu` VALUES (1399604381669163010, 1380014845910323201, 1264622039642255341);
INSERT INTO `sys_role_menu` VALUES (1399604381673357314, 1380014845910323201, 1264622039642255351);
INSERT INTO `sys_role_menu` VALUES (1399604381681745921, 1380014845910323201, 1264622039642255521);
INSERT INTO `sys_role_menu` VALUES (1399604381681745922, 1380014845910323201, 1264622039642255601);
INSERT INTO `sys_role_menu` VALUES (1399604381690134530, 1380014845910323201, 1264622039642255851);
INSERT INTO `sys_role_menu` VALUES (1399604381698523138, 1380014845910323201, 1264622039642255761);
INSERT INTO `sys_role_menu` VALUES (1399604381698523139, 1380014845910323201, 1264622039642255681);
INSERT INTO `sys_role_menu` VALUES (1399604381706911746, 1380014845910323201, 1264622039642255671);
INSERT INTO `sys_role_menu` VALUES (1399604381706911747, 1380014845910323201, 1264622039642255691);
INSERT INTO `sys_role_menu` VALUES (1399604381715300353, 1380014845910323201, 1264622039642255701);
INSERT INTO `sys_role_menu` VALUES (1399604381715300354, 1380014845910323201, 1264622039642255711);
INSERT INTO `sys_role_menu` VALUES (1399604381715300355, 1380014845910323201, 1264622039642255721);
INSERT INTO `sys_role_menu` VALUES (1399604381723688962, 1380014845910323201, 1264622039642255731);
INSERT INTO `sys_role_menu` VALUES (1399604381723688963, 1380014845910323201, 1264622039642255741);
INSERT INTO `sys_role_menu` VALUES (1399604381723688964, 1380014845910323201, 1264622039642255751);
INSERT INTO `sys_role_menu` VALUES (1399604381732077569, 1380014845910323201, 1264622039642255771);
INSERT INTO `sys_role_menu` VALUES (1399604381732077570, 1380014845910323201, 1264622039642255791);
INSERT INTO `sys_role_menu` VALUES (1399604381732077571, 1380014845910323201, 1264622039642255781);
INSERT INTO `sys_role_menu` VALUES (1399604381740466178, 1380014845910323201, 1264622039642255801);
INSERT INTO `sys_role_menu` VALUES (1399604381740466179, 1380014845910323201, 1264622039642255811);
INSERT INTO `sys_role_menu` VALUES (1399604381748854785, 1380014845910323201, 1264622039642255821);
INSERT INTO `sys_role_menu` VALUES (1399604381748854786, 1380014845910323201, 1264622039642255831);
INSERT INTO `sys_role_menu` VALUES (1399604381748854787, 1380014845910323201, 1264622039642255841);
INSERT INTO `sys_role_menu` VALUES (1399604381757243393, 1380014845910323201, 1264622039642255881);
INSERT INTO `sys_role_menu` VALUES (1399604381765632002, 1380014845910323201, 1264622039642255891);
INSERT INTO `sys_role_menu` VALUES (1399604381765632003, 1380014845910323201, 1264622039642255901);
INSERT INTO `sys_role_menu` VALUES (1399604381774020609, 1380014845910323201, 1264622039642255911);
INSERT INTO `sys_role_menu` VALUES (1399604381774020610, 1380014845910323201, 1264622039642255921);
INSERT INTO `sys_role_menu` VALUES (1399604381782409217, 1380014845910323201, 1264622039642255931);
INSERT INTO `sys_role_menu` VALUES (1399604381782409218, 1380014845910323201, 1264622039642255941);
INSERT INTO `sys_role_menu` VALUES (1399604381782409219, 1380014845910323201, 1264622039642255951);
INSERT INTO `sys_role_menu` VALUES (1399604381790797825, 1380014845910323201, 1264622039642255861);
INSERT INTO `sys_role_menu` VALUES (1399604381794992130, 1380014845910323201, 1264622039642255871);
INSERT INTO `sys_role_menu` VALUES (1399604381794992131, 1380014845910323201, 1264622039642255361);
INSERT INTO `sys_role_menu` VALUES (1399604381794992132, 1380014845910323201, 1264622039642255371);
INSERT INTO `sys_role_menu` VALUES (1399604381803380738, 1380014845910323201, 1264622039642255381);
INSERT INTO `sys_role_menu` VALUES (1399604381803380739, 1380014845910323201, 1264622039642255391);
INSERT INTO `sys_role_menu` VALUES (1399604381811769346, 1380014845910323201, 1264622039642255401);
INSERT INTO `sys_role_menu` VALUES (1399604381811769347, 1380014845910323201, 1264622039642255411);
INSERT INTO `sys_role_menu` VALUES (1399604381820157953, 1380014845910323201, 1264622039642255421);
INSERT INTO `sys_role_menu` VALUES (1399604381820157954, 1380014845910323201, 1264622039642255511);
INSERT INTO `sys_role_menu` VALUES (1399604381820157955, 1380014845910323201, 1264622039642255501);
INSERT INTO `sys_role_menu` VALUES (1399604381828546562, 1380014845910323201, 1264622039642255491);
INSERT INTO `sys_role_menu` VALUES (1399604381836935170, 1380014845910323201, 1264622039642255481);
INSERT INTO `sys_role_menu` VALUES (1399604381841129473, 1380014845910323201, 1264622039642255471);
INSERT INTO `sys_role_menu` VALUES (1399604381841129474, 1380014845910323201, 1264622039642255461);
INSERT INTO `sys_role_menu` VALUES (1399604381849518082, 1380014845910323201, 1264622039642255451);
INSERT INTO `sys_role_menu` VALUES (1399604381849518083, 1380014845910323201, 1264622039642255441);
INSERT INTO `sys_role_menu` VALUES (1399604381849518084, 1380014845910323201, 1264622039642255431);
INSERT INTO `sys_role_menu` VALUES (1399604381857906690, 1380014845910323201, 1264622039642255531);
INSERT INTO `sys_role_menu` VALUES (1399604381857906691, 1380014845910323201, 1264622039642255621);
INSERT INTO `sys_role_menu` VALUES (1399604381857906692, 1380014845910323201, 1264622039642255631);
INSERT INTO `sys_role_menu` VALUES (1399604381866295297, 1380014845910323201, 1264622039642255641);
INSERT INTO `sys_role_menu` VALUES (1399604381866295298, 1380014845910323201, 1264622039642255651);
INSERT INTO `sys_role_menu` VALUES (1399604381874683905, 1380014845910323201, 1264622039642255661);
INSERT INTO `sys_role_menu` VALUES (1399604381874683906, 1380014845910323201, 1264622039642255611);
INSERT INTO `sys_role_menu` VALUES (1399604381874683907, 1380014845910323201, 1380331425961611266);
INSERT INTO `sys_role_menu` VALUES (1399604381883072513, 1380014845910323201, 1380332174741344257);
INSERT INTO `sys_role_menu` VALUES (1399604381883072514, 1380014845910323201, 1264622039642255961);
INSERT INTO `sys_role_menu` VALUES (1399604381883072515, 1380014845910323201, 1264622039642256111);
INSERT INTO `sys_role_menu` VALUES (1399604381891461121, 1380014845910323201, 1264622039642256131);
INSERT INTO `sys_role_menu` VALUES (1399604381891461122, 1380014845910323201, 1264622039642256141);
INSERT INTO `sys_role_menu` VALUES (1399604381899849730, 1380014845910323201, 1264622039642256151);
INSERT INTO `sys_role_menu` VALUES (1399604381899849731, 1380014845910323201, 1264622039642256161);
INSERT INTO `sys_role_menu` VALUES (1399604381908238338, 1380014845910323201, 1264622039642256171);
INSERT INTO `sys_role_menu` VALUES (1399604381908238339, 1380014845910323201, 1264622039642256181);
INSERT INTO `sys_role_menu` VALUES (1399604381916626945, 1380014845910323201, 1264622039642256191);
INSERT INTO `sys_role_menu` VALUES (1399604381916626946, 1380014845910323201, 1264622039642256201);
INSERT INTO `sys_role_menu` VALUES (1399604381916626947, 1380014845910323201, 1264622039642256211);
INSERT INTO `sys_role_menu` VALUES (1399604381925015554, 1380014845910323201, 1264622039642256221);
INSERT INTO `sys_role_menu` VALUES (1399604381925015555, 1380014845910323201, 1264622039642256231);
INSERT INTO `sys_role_menu` VALUES (1399604381933404161, 1380014845910323201, 1264622039642256241);
INSERT INTO `sys_role_menu` VALUES (1399604381933404162, 1380014845910323201, 1264622039642256251);
INSERT INTO `sys_role_menu` VALUES (1399604381933404163, 1380014845910323201, 1264622039642256261);
INSERT INTO `sys_role_menu` VALUES (1399604381941792770, 1380014845910323201, 1264622039642256121);
INSERT INTO `sys_role_menu` VALUES (1399604381941792771, 1380014845910323201, 1381515284497088514);
INSERT INTO `sys_role_menu` VALUES (1399604381941792772, 1380014845910323201, 1383029023851462657);
INSERT INTO `sys_role_menu` VALUES (1399604381950181378, 1380014845910323201, 1383995035686100994);
INSERT INTO `sys_role_menu` VALUES (1399604381950181379, 1380014845910323201, 1384039222561239041);
INSERT INTO `sys_role_menu` VALUES (1399604381950181380, 1380014845910323201, 1385127823449530370);
INSERT INTO `sys_role_menu` VALUES (1399604381958569985, 1380014845910323201, 1385128136558518274);
INSERT INTO `sys_role_menu` VALUES (1399604381958569986, 1380014845910323201, 1385145701343236098);
INSERT INTO `sys_role_menu` VALUES (1399604381958569987, 1380014845910323201, 1385196533380091905);
INSERT INTO `sys_role_menu` VALUES (1399604381966958593, 1380014845910323201, 1385196679186681857);
INSERT INTO `sys_role_menu` VALUES (1399604381966958594, 1380014845910323201, 1385196397581111297);
INSERT INTO `sys_role_menu` VALUES (1399604381975347201, 1380014845910323201, 1385196145327280130);
INSERT INTO `sys_role_menu` VALUES (1399604381975347202, 1380014845910323201, 1385479705971953665);
INSERT INTO `sys_role_menu` VALUES (1399604381975347203, 1380014845910323201, 1386212893333704705);
INSERT INTO `sys_role_menu` VALUES (1399604381983735809, 1380014845910323201, 1386213279079649281);
INSERT INTO `sys_role_menu` VALUES (1399604381983735810, 1380014845910323201, 1386213470209888258);
INSERT INTO `sys_role_menu` VALUES (1399604381992124418, 1380014845910323201, 1386213055313530882);
INSERT INTO `sys_role_menu` VALUES (1399604381992124419, 1380014845910323201, 1386240388162482177);
INSERT INTO `sys_role_menu` VALUES (1399604381992124420, 1380014845910323201, 1386581828772098050);
INSERT INTO `sys_role_menu` VALUES (1399604382000513026, 1380014845910323201, 1386974045684686849);
INSERT INTO `sys_role_menu` VALUES (1399604382000513027, 1380014845910323201, 1387595655663878145);
INSERT INTO `sys_role_menu` VALUES (1399604382000513028, 1380014845910323201, 1390488580609593345);
INSERT INTO `sys_role_menu` VALUES (1399604382008901634, 1380014845910323201, 1264622039642255541);
INSERT INTO `sys_role_menu` VALUES (1399604382008901635, 1380014845910323201, 1264622039642255551);
INSERT INTO `sys_role_menu` VALUES (1399604382017290242, 1380014845910323201, 1264622039642255561);
INSERT INTO `sys_role_menu` VALUES (1399604382017290243, 1380014845910323201, 1264622039642255571);
INSERT INTO `sys_role_menu` VALUES (1399604382017290244, 1380014845910323201, 1264622039642255581);
INSERT INTO `sys_role_menu` VALUES (1399604382025678849, 1380014845910323201, 1264622039642255591);
INSERT INTO `sys_role_menu` VALUES (1399604382025678850, 1380014845910323201, 1394211296968114177);
INSERT INTO `sys_role_menu` VALUES (1399604382025678851, 1380014845910323201, 1264622039642256611);
INSERT INTO `sys_role_menu` VALUES (1399604382034067457, 1380014845910323201, 1264622039642256621);
INSERT INTO `sys_role_menu` VALUES (1399604382034067458, 1380014845910323201, 1264622039642256641);
INSERT INTO `sys_role_menu` VALUES (1399604382042456066, 1380014845910323201, 1264622039642256651);
INSERT INTO `sys_role_menu` VALUES (1399604382050844673, 1380014845910323201, 1264622039642256661);
INSERT INTO `sys_role_menu` VALUES (1399604382050844674, 1380014845910323201, 1264622039642256671);
INSERT INTO `sys_role_menu` VALUES (1399604382059233282, 1380014845910323201, 1264622039642256681);
INSERT INTO `sys_role_menu` VALUES (1399604382059233283, 1380014845910323201, 1264622039642256691);
INSERT INTO `sys_role_menu` VALUES (1399604382059233284, 1380014845910323201, 1264622039642256701);
INSERT INTO `sys_role_menu` VALUES (1399604382067621889, 1380014845910323201, 1264622039642256711);
INSERT INTO `sys_role_menu` VALUES (1399604382067621890, 1380014845910323201, 1264622039642256631);
INSERT INTO `sys_role_menu` VALUES (1399604382076010498, 1380014845910323201, 1395651227902574594);
INSERT INTO `sys_role_menu` VALUES (1399604382076010499, 1380014845910323201, 1395686366678392834);
INSERT INTO `sys_role_menu` VALUES (1399604382076010500, 1380014845910323201, 1381906786073636866);
INSERT INTO `sys_role_menu` VALUES (1399604382084399106, 1380014845910323201, 1381883901347033090);
INSERT INTO `sys_role_menu` VALUES (1399604382084399107, 1380014845910323201, 1380018324817055746);
INSERT INTO `sys_role_menu` VALUES (1399604382092787713, 1380014845910323201, 1380011988209373186);
INSERT INTO `sys_role_menu` VALUES (1399604382092787714, 1380014845910323201, 1384718359668011009);
INSERT INTO `sys_role_menu` VALUES (1399604382092787715, 1380014845910323201, 1382226257229340673);
INSERT INTO `sys_role_menu` VALUES (1399604382101176321, 1380014845910323201, 1382250525069631489);
INSERT INTO `sys_role_menu` VALUES (1399604382101176322, 1380014845910323201, 1382250332676907009);
INSERT INTO `sys_role_menu` VALUES (1399604382101176323, 1380014845910323201, 1381787504008015873);
INSERT INTO `sys_role_menu` VALUES (1399604382109564930, 1380014845910323201, 1384443675130994690);
INSERT INTO `sys_role_menu` VALUES (1399604382117953538, 1380014845910323201, 1382228717184442370);
INSERT INTO `sys_role_menu` VALUES (1399604382117953539, 1380014845910323201, 1382646318777294850);
INSERT INTO `sys_role_menu` VALUES (1399604382122147842, 1380014845910323201, 1384717362652639234);
INSERT INTO `sys_role_menu` VALUES (1399604382122147843, 1380014845910323201, 1382616857759567873);
INSERT INTO `sys_role_menu` VALUES (1399604382130536450, 1380014845910323201, 1386518281488740354);
INSERT INTO `sys_role_menu` VALUES (1399604382130536451, 1380014845910323201, 1382617024927748098);
INSERT INTO `sys_role_menu` VALUES (1399604382134730753, 1380014845910323201, 1382616615366545410);
INSERT INTO `sys_role_menu` VALUES (1399604382134730754, 1380014845910323201, 1382616736468684802);
INSERT INTO `sys_role_menu` VALUES (1399604382143119362, 1380014845910323201, 1382645666693046274);
INSERT INTO `sys_role_menu` VALUES (1399604382143119363, 1380014845910323201, 1382290546879320065);
INSERT INTO `sys_role_menu` VALUES (1399604382143119364, 1380014845910323201, 1395706117337223169);
INSERT INTO `sys_role_menu` VALUES (1399604382151507970, 1380014845910323201, 1382521411372376065);
INSERT INTO `sys_role_menu` VALUES (1399604382151507971, 1380014845910323201, 1382572364020641793);
INSERT INTO `sys_role_menu` VALUES (1399604382159896578, 1380014845910323201, 1382290386606575618);
INSERT INTO `sys_role_menu` VALUES (1399604382159896579, 1380014845910323201, 1395716838712233986);
INSERT INTO `sys_role_menu` VALUES (1399604382159896580, 1380014845910323201, 1382289871906754562);
INSERT INTO `sys_role_menu` VALUES (1399604382168285185, 1380014845910323201, 1382290149993304066);
INSERT INTO `sys_role_menu` VALUES (1399604382168285186, 1380014845910323201, 1386508887090851841);
INSERT INTO `sys_role_menu` VALUES (1399604382168285187, 1380014845910323201, 1264622039642255971);
INSERT INTO `sys_role_menu` VALUES (1399604382176673794, 1380014845910323201, 1264622039642255981);
INSERT INTO `sys_role_menu` VALUES (1399604382176673795, 1380014845910323201, 1264622039642255991);
INSERT INTO `sys_role_menu` VALUES (1399604382185062401, 1380014845910323201, 1264622039642256001);
INSERT INTO `sys_role_menu` VALUES (1399604382189256705, 1380014845910323201, 1264622039642256011);
INSERT INTO `sys_role_menu` VALUES (1399604382189256706, 1380014845910323201, 1264622039642256021);
INSERT INTO `sys_role_menu` VALUES (1399604382197645314, 1380014845910323201, 1264622039642256031);
INSERT INTO `sys_role_menu` VALUES (1399604382197645315, 1380014845910323201, 1398905923907936257);

-- ----------------------------
-- Table structure for sys_role_metadata
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_metadata`;
CREATE TABLE `sys_role_metadata`  (
  `id` bigint(20) NOT NULL,
  `role_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色code',
  `metadata_column_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '元数据字段code',
  `permission` int(10) NOT NULL COMMENT '权限',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_role_code`(`role_code`) USING BTREE,
  INDEX `fk_metadata_code`(`metadata_column_code`) USING BTREE,
  CONSTRAINT `fk_metadata_code` FOREIGN KEY (`metadata_column_code`) REFERENCES `g_metadata_column` (`code`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_role_code` FOREIGN KEY (`role_code`) REFERENCES `sys_role` (`code`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role_metadata
-- ----------------------------
INSERT INTO `sys_role_metadata` VALUES (1397432504183730178, 'notice_produce_role', 'm_t_article_id', 1);
INSERT INTO `sys_role_metadata` VALUES (1397432504183730179, 'notice_produce_role', 'm_t_article_url', 1);
INSERT INTO `sys_role_metadata` VALUES (1397432504183730180, 'notice_produce_role', 'm_t_article_title', 1);
INSERT INTO `sys_role_metadata` VALUES (1397432504250839041, 'notice_produce_role', 'm_t_article_subTitle', 1);
INSERT INTO `sys_role_metadata` VALUES (1397432504250839042, 'notice_produce_role', 'm_t_article_parTitle', 1);
INSERT INTO `sys_role_metadata` VALUES (1397432504317947905, 'notice_produce_role', 'm_t_article_titleText', 1);
INSERT INTO `sys_role_metadata` VALUES (1397432504317947906, 'notice_produce_role', 'm_t_article_author1', 1);
INSERT INTO `sys_role_metadata` VALUES (1397432504317947907, 'notice_produce_role', 'm_t_article_authorType1', 1);
INSERT INTO `sys_role_metadata` VALUES (1397432504317947908, 'notice_produce_role', 'm_t_article_author2', 1);
INSERT INTO `sys_role_metadata` VALUES (1397432504317947909, 'notice_produce_role', 'm_t_article_authorType2', 1);
INSERT INTO `sys_role_metadata` VALUES (1397432504317947910, 'notice_produce_role', 'm_t_article_author3', 1);
INSERT INTO `sys_role_metadata` VALUES (1397432504317947911, 'notice_produce_role', 'm_t_article_authorType3', 1);
INSERT INTO `sys_role_metadata` VALUES (1397432504317947912, 'notice_produce_role', 'm_t_article_authorText', 1);
INSERT INTO `sys_role_metadata` VALUES (1397432504380862465, 'notice_produce_role', 'm_t_article_source', 1);
INSERT INTO `sys_role_metadata` VALUES (1397432504380862466, 'notice_produce_role', 'm_t_article_sourceText', 1);
INSERT INTO `sys_role_metadata` VALUES (1397432504380862467, 'notice_produce_role', 'm_t_article_writeDate', 1);
INSERT INTO `sys_role_metadata` VALUES (1397432504380862468, 'notice_produce_role', 'm_t_article_releaseDate', 1);
INSERT INTO `sys_role_metadata` VALUES (1397432504380862469, 'notice_produce_role', 'm_t_article_publisher', 1);
INSERT INTO `sys_role_metadata` VALUES (1397432504380862470, 'notice_produce_role', 'm_t_article_publisherUrb', 1);
INSERT INTO `sys_role_metadata` VALUES (1397432504380862471, 'notice_produce_role', 'm_t_article_page', 1);
INSERT INTO `sys_role_metadata` VALUES (1397432504380862472, 'notice_produce_role', 'm_t_article_page_pageEnd', 1);
INSERT INTO `sys_role_metadata` VALUES (1397432504380862473, 'notice_produce_role', 'm_t_article_page_pageStart', 1);
INSERT INTO `sys_role_metadata` VALUES (1397432504443777025, 'notice_produce_role', 'm_t_article_volume', 1);
INSERT INTO `sys_role_metadata` VALUES (1397432504443777026, 'notice_produce_role', 'm_t_article_issue', 1);
INSERT INTO `sys_role_metadata` VALUES (1397432504443777027, 'notice_produce_role', 'm_t_article_year', 1);
INSERT INTO `sys_role_metadata` VALUES (1397432504443777028, 'notice_produce_role', 'm_t_article_persons', 1);
INSERT INTO `sys_role_metadata` VALUES (1397432504443777029, 'notice_produce_role', 'm_t_article_personsText', 1);
INSERT INTO `sys_role_metadata` VALUES (1397432504443777030, 'notice_produce_role', 'm_t_article_events', 1);
INSERT INTO `sys_role_metadata` VALUES (1397432504443777031, 'notice_produce_role', 'm_t_article_eventsText', 1);
INSERT INTO `sys_role_metadata` VALUES (1397432504443777032, 'notice_produce_role', 'm_t_article_eventsId', 1);
INSERT INTO `sys_role_metadata` VALUES (1397432504506691585, 'notice_produce_role', 'm_t_article_abstract', 1);
INSERT INTO `sys_role_metadata` VALUES (1397432504506691586, 'notice_produce_role', 'm_t_article_abstractText', 1);
INSERT INTO `sys_role_metadata` VALUES (1397432504506691587, 'notice_produce_role', 'm_t_article_keywords', 1);
INSERT INTO `sys_role_metadata` VALUES (1397432504506691588, 'notice_produce_role', 'm_t_article_keywordsText', 1);
INSERT INTO `sys_role_metadata` VALUES (1397432504506691589, 'notice_produce_role', 'm_t_article_category', 1);
INSERT INTO `sys_role_metadata` VALUES (1397432504506691590, 'notice_produce_role', 'm_t_article_fileType1', 1);
INSERT INTO `sys_role_metadata` VALUES (1397432504506691591, 'notice_produce_role', 'm_t_article_fileType2', 1);
INSERT INTO `sys_role_metadata` VALUES (1397432504506691592, 'notice_produce_role', 'm_t_article_filePath', 1);
INSERT INTO `sys_role_metadata` VALUES (1397432504573800450, 'notice_produce_role', 'm_t_article_isDelete', 1);
INSERT INTO `sys_role_metadata` VALUES (1397432504573800451, 'notice_produce_role', 'm_t_article_role', 1);
INSERT INTO `sys_role_metadata` VALUES (1397432504573800452, 'notice_produce_role', 'm_t_article_createdDate', 1);
INSERT INTO `sys_role_metadata` VALUES (1397432504573800453, 'notice_produce_role', 'm_t_article_lastModifiedDate', 1);
INSERT INTO `sys_role_metadata` VALUES (1397741438925058049, 'exploit', 'ChenYiWenXianHuiZong_address', 131);
INSERT INTO `sys_role_metadata` VALUES (1397741438933446658, 'exploit', 'ChenYiWenXianHuiZong_themePerson', 131);
INSERT INTO `sys_role_metadata` VALUES (1397741438933446659, 'exploit', 'ChenYiWenXianHuiZong_startTime', 131);
INSERT INTO `sys_role_metadata` VALUES (1397741438941835266, 'exploit', 'ChenYiWenXianHuiZong_incident', 131);

-- ----------------------------
-- Table structure for sys_sms
-- ----------------------------
DROP TABLE IF EXISTS `sys_sms`;
CREATE TABLE `sys_sms`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `phone_numbers` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '手机号',
  `validate_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '短信验证码',
  `template_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '短信模板ID',
  `biz_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '回执id，可根据该id查询具体的发送状态',
  `status` tinyint(4) NOT NULL COMMENT '发送状态（字典 0 未发送，1 发送成功，2 发送失败，3 失效）',
  `source` tinyint(4) NOT NULL COMMENT '来源（字典 1 app， 2 pc， 3 其他）',
  `invalid_time` datetime(0) NULL DEFAULT NULL COMMENT '失效时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '短信信息发送表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_sms
-- ----------------------------

-- ----------------------------
-- Table structure for sys_timers
-- ----------------------------
DROP TABLE IF EXISTS `sys_timers`;
CREATE TABLE `sys_timers`  (
  `id` bigint(20) NOT NULL COMMENT '定时器id',
  `timer_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '任务名称',
  `action_class` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行任务的class的类名（实现了TimerTaskRunner接口的类的全称）',
  `cron` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '定时任务表达式',
  `job_status` tinyint(4) NULL DEFAULT 0 COMMENT '状态（字典 1运行  2停止）',
  `remark` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注信息',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '定时任务' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_timers
-- ----------------------------
INSERT INTO `sys_timers` VALUES (1288760324837851137, '定时同步缓存常量', 'com.cn.sundeinfo.sys.modular.timer.tasks.RefreshConstantsTaskRunner', '0 0/1 * * * ?', 1, '定时同步sys_config表的数据到缓存常量中', '2020-07-30 16:56:20', 1265476890672672808, '2020-07-30 16:58:52', 1265476890672672808);
INSERT INTO `sys_timers` VALUES (1304971718170832898, '定时打印一句话', 'com.cn.sundeinfo.sys.modular.timer.tasks.SystemOutTaskRunner', '0 0 * * * ? *', 2, '定时打印一句话', '2020-09-13 10:34:37', 1265476890672672808, '2020-09-23 20:37:48', 1265476890672672808);
INSERT INTO `sys_timers` VALUES (1393136727563517953, '数据采集任务', 'com.cn.sundeinfo.main.modular.dataGather.tasks.DataGatherTaskRunner', '0 00 01 * * ?', 1, '每天凌晨一点执行采集任务，已经采集过的将跳过', '2021-05-14 17:30:54', 1265476890672672808, '2021-06-04 17:58:18', 1377879897444212737);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `account` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账号',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `nick_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '姓名',
  `avatar` bigint(20) NULL DEFAULT NULL COMMENT '头像',
  `birthday` date NULL DEFAULT NULL COMMENT '生日',
  `sex` tinyint(4) NOT NULL COMMENT '性别(字典 1男 2女 3未知)',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机',
  `tel` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
  `last_login_ip` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后登陆IP',
  `last_login_time` datetime(0) NULL DEFAULT NULL COMMENT '最后登陆时间',
  `admin_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '管理员类型（0超级管理员 1非管理员）',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '状态（字典 0正常 1冻结 2删除）',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1265476890672672808, 'superAdmin', '$2a$09$PiCiFNspSlTBE9CakVs8ZOqx0xa03X9wOm01gMasHch4929TpEWCC', '超级管理员', '超级管理员', 1377894713034563585, '2020-03-18', 1, 'superAdmin@qq.com', '15228937093', '1234567890', '192.168.1.7', '2021-05-30 15:36:20', 1, 0, '2020-05-29 16:39:28', -1, '2021-05-30 15:36:20', -1);
INSERT INTO `sys_user` VALUES (1275735541155614721, 'yubaoshan', '$2a$09$PiCiFNspSlTBE9CakVs8ZOqx0xa03X9wOm01gMasHch4929TpEWCC', 'Await', '俞宝山', 1307866860842360834, '1992-10-03', 1, 'await183@qq.com', '18200001102', '', '127.0.0.1', '2020-09-23 10:15:10', 2, 2, '2020-06-24 18:20:30', 1265476890672672808, '2021-04-02 15:03:12', 1265476890672672808);
INSERT INTO `sys_user` VALUES (1280709549107552257, 'xuyuxiang', '$2a$09$PiCiFNspSlTBE9CakVs8ZOqx0xa03X9wOm01gMasHch4929TpEWCC', '就是那个锅', '徐玉祥', 1307863777357832194, '2020-07-01', 1, NULL, '18200001100', NULL, '127.0.0.1', '2020-09-23 10:16:54', 2, 2, '2020-07-08 11:45:26', 1265476890672672808, '2021-04-02 15:03:14', 1265476890672672808);
INSERT INTO `sys_user` VALUES (1377879897444212737, 'testuser', '$2a$10$T9SNvcNUdqlfNhWbArA1Xu0cFR22JQUsaJ9NRbEpBLixCgxiWWPOO', NULL, '测试', NULL, NULL, 1, NULL, '15737927787', NULL, '192.168.1.7', '2021-06-08 18:32:17', 2, 0, '2021-04-02 15:05:42', 1265476890672672808, '2021-06-08 18:32:17', -1);

-- ----------------------------
-- Table structure for sys_user_data_scope
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_data_scope`;
CREATE TABLE `sys_user_data_scope`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `org_id` bigint(20) NOT NULL COMMENT '机构id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户数据范围表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_data_scope
-- ----------------------------
INSERT INTO `sys_user_data_scope` VALUES (1380016975794671617, 1377879897444212737, 1377879440705478658);
INSERT INTO `sys_user_data_scope` VALUES (1380016975824031745, 1377879897444212737, 1377879536427884546);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1283596920384860162, 1280700700074041345, 1265476890672672819);
INSERT INTO `sys_user_role` VALUES (1380017042236641282, 1377879897444212737, 1380014845910323201);
INSERT INTO `sys_user_role` VALUES (1380017042261807106, 1377879897444212737, 1265476890672672818);

-- ----------------------------
-- Table structure for sys_vis_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_vis_log`;
CREATE TABLE `sys_vis_log`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `success` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否执行成功（Y-是，N-否）',
  `message` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '具体消息',
  `ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ip',
  `location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `browser` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '浏览器',
  `os` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作系统',
  `vis_type` tinyint(4) NOT NULL COMMENT '操作类型（字典 1登入 2登出）',
  `vis_time` datetime(0) NULL DEFAULT NULL COMMENT '访问时间',
  `account` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '访问账号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统访问日志表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_vis_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
