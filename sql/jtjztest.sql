/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80033
 Source Host           : localhost:3306
 Source Schema         : jtjztest

 Target Server Type    : MySQL
 Target Server Version : 80033
 File Encoding         : 65001

 Date: 04/09/2024 19:14:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `adminId` int(0) NOT NULL AUTO_INCREMENT COMMENT 'ID号',
  `adminName` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '姓名',
  `sex` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '性别',
  `tel` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '电话号码',
  `email` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '电子邮箱',
  `pwd` varchar(16) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '密码',
  `cardId` varchar(18) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '身份证号',
  `career` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '职业',
  `role` varchar(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '角色(0管理员，1家庭成员)',
  PRIMARY KEY (`adminId`) USING BTREE,
  INDEX `sex`(`sex`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9528 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '管理员信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (9527, '爸爸', '男', '13658377857', '1253838283@qq.com', '123456', '313111111111111111', '教师', '0');

-- ----------------------------
-- Table structure for income
-- ----------------------------
DROP TABLE IF EXISTS `income`;
CREATE TABLE `income`  (
  `incomeId` int(0) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `incomeName` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '姓名',
  `money` decimal(10, 2) NULL DEFAULT NULL COMMENT '金额',
  `incomeway` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '收款方式',
  `label` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '收入来源',
  `notes` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '备注',
  `datetime` timestamp(0) NULL DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`incomeId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '收入表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of income
-- ----------------------------
INSERT INTO `income` VALUES (1, '妈妈', 5500.00, '支付宝', '转账', '8月份工资', '2024-08-21 12:00:00');
INSERT INTO `income` VALUES (2, '爸爸', 8000.00, '银联', '工资', '8月份工资', '2024-08-27 12:00:00');
INSERT INTO `income` VALUES (3, '张大宝', 800.00, '支付宝', '奖学金', '大一下学期奖学金', '2024-08-31 12:00:00');
INSERT INTO `income` VALUES (4, '妈妈', 5500.00, '银联', '转账', '9月份工资', '2024-09-21 12:00:00');
INSERT INTO `income` VALUES (5, '爸爸', 8000.00, '银联', '工资', '9月份工资', '2024-09-27 12:00:00');
INSERT INTO `income` VALUES (6, '张大宝', 1000.00, '支付宝', '奖金', '竞赛一等奖奖金', '2024-07-01 12:00:00');
INSERT INTO `income` VALUES (7, '妈妈', 5500.00, '银联', '工资', '10月份工资', '2024-10-21 12:00:00');
INSERT INTO `income` VALUES (8, '爸爸', 8000.00, '银联', '工资', '10月份工资', '2024-10-27 12:00:00');
INSERT INTO `income` VALUES (9, '妈妈', 5500.00, '银联', '工资', '11月份工资', '2024-11-21 12:00:00');
INSERT INTO `income` VALUES (10, '爸爸', 8000.00, '银联', '工资', '11月份工资', '2024-11-27 12:00:00');
INSERT INTO `income` VALUES (11, '妈妈', 5500.00, '微信', '工资', '年终奖', '2024-12-21 12:00:00');
INSERT INTO `income` VALUES (12, '爸爸', 8000.00, '银联', '工资', '12月份工资', '2024-12-27 12:00:00');
INSERT INTO `income` VALUES (14, '爸爸', 5000.00, '银联', '工资', '年终奖', '2024-12-31 12:00:00');
INSERT INTO `income` VALUES (15, '翠花', 1000.00, '现金', '红包', '过年爷爷给的红包', '2025-01-29 12:00:00');
INSERT INTO `income` VALUES (16, '张大宝', 1000.00, '现金', '红包', '过年爷爷给的红包', '2024-02-01 12:00:00');
INSERT INTO `income` VALUES (17, '翠花', 2000.00, '现金', '红包', '过年小姨给的红包', '2025-01-29 12:00:00');
INSERT INTO `income` VALUES (18, '张大宝', 2000.00, '支付宝', '红包', '过年小姨给的红包', '2024-08-29 12:00:00');
INSERT INTO `income` VALUES (19, '张大宝', 2300.00, '微信', '工资', '兼职工资', '2024-09-01 16:29:07');
INSERT INTO `income` VALUES (20, '张大宝', 250.00, '微信', '零花', '爸爸给的零花钱', '2024-09-04 16:31:46');

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member`  (
  `memberId` int(0) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `memberName` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '姓名',
  `tel` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '电话号码',
  `email` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '电子邮箱',
  `pwd` varchar(16) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '密码',
  `cardId` varchar(18) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '身份证号',
  `sex` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '性别',
  `career` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '职业',
  `role` varchar(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '2' COMMENT '角色(0管理员，1家庭成员)',
  PRIMARY KEY (`memberId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20155010 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '家庭成员信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of member
-- ----------------------------
INSERT INTO `member` VALUES (20154084, '妈妈', '15922837483', '15922837483@163.com', '123456', '283848222222222222', '女', '医生', '1');
INSERT INTO `member` VALUES (20155003, '张大宝', '15583829425', '15583829425@163.com', '123456', '324234222222222222', '男', '高中生', '1');
INSERT INTO `member` VALUES (20155007, '翠花', '18734538457', '18734538457@163.com', '123456', '242342222222222222', '女', '小学生', '1');

-- ----------------------------
-- Table structure for pay
-- ----------------------------
DROP TABLE IF EXISTS `pay`;
CREATE TABLE `pay`  (
  `payId` int(0) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `payName` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '姓名',
  `money` decimal(10, 2) NULL DEFAULT NULL COMMENT '金额',
  `payway` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '支付方式',
  `label1` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '一级目录',
  `label2` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '二级目录',
  `notes` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '备注',
  `datetime` timestamp(0) NULL DEFAULT NULL COMMENT '时间',
  `toperson` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '消费对象',
  `place` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '消费地点',
  PRIMARY KEY (`payId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 40 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '支出表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pay
-- ----------------------------
INSERT INTO `pay` VALUES (1, '妈妈', 139.00, '微信', '购物', '服饰用品类', '上衣', '2024-08-21 12:00:00', '张大宝', '万达广场');
INSERT INTO `pay` VALUES (2, '张大宝', 1306.00, '支付宝', '购物', '数码类', '蓝牙耳机', '2024-08-27 12:00:00', '张大宝', '万达广场');
INSERT INTO `pay` VALUES (3, '翠花', 6.00, '现金', '购物', '食品类', '冰激凌', '2024-08-27 12:00:00', '翠花', '万达广场');
INSERT INTO `pay` VALUES (4, '爸爸', 2000.00, '微信', '学习', '学费类', '学费', '2024-08-31 12:00:00', '翠花', '家');
INSERT INTO `pay` VALUES (5, '爸爸', 6000.00, '银联', '学习', '学费类', '学费', '2024-08-31 12:10:00', '张大宝', '家');
INSERT INTO `pay` VALUES (6, '张大宝', 300.00, '支付宝', '学习', '学费类', '买书', '2024-09-01 12:00:00', '张大宝', '杭电');
INSERT INTO `pay` VALUES (7, '张大宝', 19.00, '微信', '购物', '食品类', '零食', '2024-09-05 12:00:00', '张大宝', '老婆大人');
INSERT INTO `pay` VALUES (8, '妈妈', 333.00, '银联', '学习', '用品类', '文具', '2024-09-13 12:00:00', '翠花', '晨光文具店');
INSERT INTO `pay` VALUES (9, '爸爸', 198.00, '支付宝', '购物', '家居用品类', '平底锅', '2024-09-22 12:00:00', '全家', '万达广场');
INSERT INTO `pay` VALUES (10, '妈妈', 788.00, '微信', '购物', '食品类', '水果', '2024-09-23 12:00:00', '翠花', '菜市场');
INSERT INTO `pay` VALUES (11, '张大宝', 1500.00, '支付宝', '购物', '数码类', '游戏机', '2024-09-25 12:00:00', '张大宝', '线上淘宝');
INSERT INTO `pay` VALUES (12, '爸爸', 120.00, '支付宝', '餐饮', '快餐类', '午餐', '2024-09-28 12:00:00', '全家', '肯德基');
INSERT INTO `pay` VALUES (13, '妈妈', 45.00, '支付宝', '购物', '服饰类', '袜子', '2024-09-30 12:00:00', '翠花', '淘宝');
INSERT INTO `pay` VALUES (14, '妈妈', 350.00, '微信', '购物', '数码类', '键盘', '2024-10-02 12:00:00', '张大宝', '淘宝');
INSERT INTO `pay` VALUES (15, '爸爸', 800.00, '支付宝', '学习', '学费类', '英语课程', '2024-10-05 12:00:00', '翠花', '新东方');
INSERT INTO `pay` VALUES (16, '张大宝', 220.00, '支付宝', '购物', '食品类', '蛋糕', '2024-06-01 12:00:00', '全家', '面包店');
INSERT INTO `pay` VALUES (17, '妈妈', 15.00, '现金', '餐饮', '快餐类', '早餐', '2024-10-15 12:00:00', '全家', '街边小吃店');
INSERT INTO `pay` VALUES (18, '张大宝', 430.00, '微信', '购物', '家居用品类', '台灯', '2024-07-01 12:00:00', '张大宝', '宜家');
INSERT INTO `pay` VALUES (19, '妈妈', 2000.00, '现金', '送礼', '份子钱类', '小姨结婚份子钱', '2024-11-21 12:00:00', '礼尚往来', '婚礼酒店');
INSERT INTO `pay` VALUES (20, '爸爸', 1888.00, '支付宝', '购物', '家居用品类', '朋友乔迁礼物茶具一套', '2024-12-13 12:00:00', '礼尚往来', '老家');

SET FOREIGN_KEY_CHECKS = 1;
