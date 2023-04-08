/*
 Navicat Premium Data Transfer

 Source Server         : hos
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:3306
 Source Schema         : hospital

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 08/04/2023 15:57:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for alluser
-- ----------------------------
DROP TABLE IF EXISTS `alluser`;
CREATE TABLE `alluser`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `gender` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `age` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `role` int(0) NULL DEFAULT NULL,
  `photo` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`, `name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of alluser
-- ----------------------------
INSERT INTO `alluser` VALUES (1, 'admin', '123', '男', '28', '2077@qq.com', 1, 'http://localhost:8887/files/7e4e6b7f5763477f9dda789a5c200a88');
INSERT INTO `alluser` VALUES (2, 'admin2', '123456', '男', '35', '8680432785@qq.com', 1, 'http://localhost:8887/files/015e79bfcec94667b798ebd4b7acd270');
INSERT INTO `alluser` VALUES (3, '王洛阳', '123456', '男', '45', '973321919@hos.com', 2, 'http://localhost:8887/files/86307287ab314551be9e790b223d140e');
INSERT INTO `alluser` VALUES (26, 'admin3', '12346', '女', '20', '666432785@qq.com', 1, 'http://localhost:8887/files/cae603dda1974bc6bf347e4e2be2b703');
INSERT INTO `alluser` VALUES (34, '黄沙', '123456', '男', '56', '288885676@hos.com', 2, 'http://localhost:8887/files/fba5339bcd0b4e8e9bc3e4bf57326fbb');
INSERT INTO `alluser` VALUES (36, '罗艳', '123456', '女', '35', '589995812@hos.com', 2, 'http://localhost:8887/files/cae603dda1974bc6bf347e4e2be2b703');
INSERT INTO `alluser` VALUES (39, '李军', '123456', '男', '52', '125225@163.com', 2, 'http://localhost:8887/files/cae603dda1974bc6bf347e4e2be2b703');
INSERT INTO `alluser` VALUES (40, '李莉', '123456', '女', '25', '2022255@google.com', 2, 'http://localhost:8887/files/cae603dda1974bc6bf347e4e2be2b703');
INSERT INTO `alluser` VALUES (41, '张三', '123456', '男', '35', '30222@qq.com', 2, 'http://localhost:8887/files/cae603dda1974bc6bf347e4e2be2b703');
INSERT INTO `alluser` VALUES (42, '马婷', '123456', '女', '23', '207734192@qq.com', 3, 'http://localhost:8887/files/eeaf4dfc0bf743e182625cbaf79b0fe3');
INSERT INTO `alluser` VALUES (43, '西喜爱', '123456', '女', '44', '101234@163.cn', 2, 'http://localhost:8887/files/cae603dda1974bc6bf347e4e2be2b703');
INSERT INTO `alluser` VALUES (45, '1', '123456', '男', '24', '307725956@qq.com', 3, 'http://localhost:8887/files/cae603dda1974bc6bf347e4e2be2b703');
INSERT INTO `alluser` VALUES (48, '白衣天使', '123456', '女', '25', NULL, 3, 'http://localhost:8887/files/cae603dda1974bc6bf347e4e2be2b703');
INSERT INTO `alluser` VALUES (49, '徐煜阳', '123456', '男', '30', '160524888@qq.com', 3, 'http://localhost:8887/files/cae603dda1974bc6bf347e4e2be2b703');
INSERT INTO `alluser` VALUES (50, 'san', '01234864wyc', NULL, NULL, NULL, 3, 'http://localhost:8887/files/cae603dda1974bc6bf347e4e2be2b703');

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `commentId` int(0) NOT NULL AUTO_INCREMENT,
  `commentPersonId` int(0) NULL DEFAULT NULL,
  `commentPersonPic` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `commentTitle` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `commentDes` varchar(1024) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `commentTime` date NULL DEFAULT NULL,
  `commentNum` int(0) NULL DEFAULT NULL,
  `commentGood` int(0) NULL DEFAULT NULL,
  `commentPersonName` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `commentPic` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `commentContent` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL,
  PRIMARY KEY (`commentId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (9, 3, 'http://localhost:8887/files/f3739ef529ac481d83cbd19514793eab', '糖尿病并发症都有哪些，数量多吗？', '糖尿病并发症涵盖全身，高达100多种，是目前已知并发症最多的一种疾病！', '2022-05-21', 4, 17, '黑人', 'http://localhost:8887/files/c5af3b4dd4274d549e118ae6cdfe9032', '<p>长期血糖增高，大血管、微血管受损并危及心、脑、肾、周围神经、眼睛、足等，据世界卫生组织统计，糖尿病并发症高达100多种，是目前已知并发症最多的一种疾病。糖尿病死亡者有一半以上是心脑血管所致，10%是肾病变所致。因糖尿病截肢的患者是非糖尿病的10～20倍。临床数据显示，糖尿病发病后10年左右，将有30%～40%的患者至少会发生一种并发症，且并发症一旦产生，药物治疗很难逆转。<br/><br/><img src=\"http://localhost:8887/files/19bfa82d14604d14a265b4e2c42e0e28\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>');
INSERT INTO `comment` VALUES (11, 49, 'http://localhost:8887/files/cae603dda1974bc6bf347e4e2be2b703', '糖尿病并发病是如何产生的？', '简直多爆了！目前糖尿病是在全球发现的并发症最多的慢性病之一，大约有100多种并发症。', '2022-05-22', 2, 4, '徐煜阳', 'http://localhost:8887/files/60d5d33fd3494e488c52052bfd23eee3', '<p>患了糖尿病，血糖居高不下，那么高血糖随着全身的血液进行着循环，这就如同器官们浸泡在糖里一样。如果只是暂时泡一泡，那不会有问题。当血糖达标了，身体的组织器官就解除了被糖浸泡，那么被泡过的组织便会很快恢复。然而，如果泡的时间太长，身体的组织就会发生根本性的改变——可能就“变质坏掉”了。<br/><br/></p><p><img src=\"http://localhost:8887/files/91a4193ebc5b47be801557975cfee4db\" style=\"max-width:100%;\" contenteditable=\"false\"/><br/>这个时候，即使血糖恢复正常，组织器官的<b>应激损伤已经产生</b>，很难再恢复到原来的正常状态，也就是说<b>损伤已不可逆</b>，很容易导致<b>各种并发症的发生，也是是糖尿病患者致残致死的主要原因。</b><br/></p>');
INSERT INTO `comment` VALUES (12, 3, 'http://localhost:8887/files/f3739ef529ac481d83cbd19514793eab', '了解五种常见糖尿病并发症', '所以在生活中，糖尿病患者应该如何应对，如何提早发现糖尿病并发症呢？', '2022-05-19', 0, 6, '黑人', 'http://localhost:8887/files/6f7f2dc1306a47199e1553053acc3da8', '<p>1、脑血管病  高血糖的长期侵袭，血管加速硬化、堵塞，血液循环减弱，引发多种心脑血管疾病，是糖尿病患者的主要死亡原因之一。 糖尿病并发脑血管病比非糖尿病人高4-10倍，脑梗塞的死亡率比非糖尿病高2倍。 以脑梗塞、脑血栓等缺血性脑血管病多见，占80%以上；脑梗塞往往反复发生，临床可出现多次中风。</p><p>&nbsp;2、心血管病  包括心脏和大血管上的微血管病变、心肌病变、心脏自主神经病变，引起糖尿病患者死亡的首要病因。 糖尿病并发心肌梗塞比非糖尿病人高10倍，病死率高2～4倍。冠心病的发病年龄提前约5年。  非典型临床表现多：无痛性心肌缺血占50-65%，无痛性心肌梗塞占42-54.5%。再梗塞发生率与死亡率高，首次心肌梗塞后5年生存率仅为58%。</p><p>&nbsp;3、眼病  人体的眼睛布满微血管，而这些微血管又极其脆弱！ 高血糖损伤眼部微血管，发生视网膜缺血氧的情况，进而发生视网膜病变，诸如：视网膜脱落、视网膜出血、玻璃体出血等。这些眼疾会导致患者视力下降，严重则出现失明。   4、肾病  是糖尿病患者最重要的合并症之一。我国的发病率亦呈上升趋势，目前已成为终末期肾脏病的第二位原因，仅次于各种肾小球肾炎。 肾脏和眼睛相似，都有着丰富的毛细血管。长期高血糖会损伤肾脏微血管，增加肾小球过滤负担，出现功能下降。轻者出现蛋白尿，重者浮肿、肾衰竭。</p><p>&nbsp;5、糖尿病足  脚部处于人体最底部，血液流入脚部的时间最长。 血糖高则会导致供给到脚部血管的血氧不足，这是导致足部血管病变的主要原因。 还有一点引起糖足的原因是神经病变，主要体现在脚部疼痛感缺失，这就更容易出现磕伤、烫伤等足部损伤；从而诱发足部溃疡，严重者截肢。<br/></p>');
INSERT INTO `comment` VALUES (13, 3, 'http://localhost:8887/files/f3739ef529ac481d83cbd19514793eab', '慢性并发症糖尿病', '各位糖友想要提高生活质量，可要及时排查，谨慎提防以下常见的慢性并发症：', '2022-05-19', 0, 0, '黑人', 'http://localhost:8887/files/655345e553934438b8ef8a5998523281', '<p>糖尿病视网膜病变糖尿病可引起视网膜的微血管发生改变，导致糖友出现视力下降、东西看不清、眼疼等症状，严重者可失明。糖尿病肾病糖尿病引起的肾损伤，是糖患致死的主要原因之一。它的特点是尿液中的白蛋白排泄量增加。糖肾早期比较不容易发现，发展到后期肾功能衰竭时，需要透析甚至肾移植。所以糖友们一定要去做尿蛋白检查，以及时发现问题。</p><p>糖尿病大血管病变糖尿病和心血管疾病是一对好基友，有糖尿病的人很多都有心血管病，反之亦然。糖尿病心血管疾病以动脉粥样硬化为主，其特点和普通的心血管疾病不会差太多。神经病变最常见的并发症之一。简单来说，它是由于糖患代谢异常导致神经没有足够的营养滋养而受到损害，可影响神经系统的任何一处，小则胃肠不适，重则脑部病变。</p><p>糖尿病足当糖友存在血管病变和神经病变问题，其脚部会供血不良，在这时，一旦有外伤就会容易发生感染，所以说，糖尿病足是固定发生在糖友身上的足溃烂、感染，严重者甚至会截肢。<br/><br/></p>');
INSERT INTO `comment` VALUES (14, 34, 'http://localhost:8887/files/cae603dda1974bc6bf347e4e2be2b703', '糖尿病的危害有什么？', '糖尿病最可怕的就是它的并发症；身体“高血糖”的内环境会对血管及各个脏器造成持续的且不可逆的损害，导致各种并发症的发生。', '2022-05-13', 0, 2, 'HHH', 'http://localhost:8887/files/53de4b9a007a46bfb85602dc25a80274', '<p><br/></p><p>1，最常见—酮症酸中毒酮症酸中毒是糖尿病的急性并发症之一，是由于体内胰岛素严重不足所致。当患者胰岛素严重缺乏时，糖代谢紊乱急剧加重，这时，机体不能利用葡萄糖，只好动用脂肪供能，而脂肪燃烧不完全，因而出现继发性脂肪代谢严重紊乱：当脂肪分解加速，酮体生成增多，超过了组织所能利用的程度时，酮体在体内积聚。血酮体&gt;1.0mmol/L为高血酮，&gt;3.0mmol/L提示酸中毒；</p><p>2，最致命—高血糖高渗综合征患者有严重的高血糖，脱水，高钠，血浆渗透压升高等等，患者常常会有意识障碍或者是昏迷，血糖多高于33.3mmol/L甚至有的患者可以达到100mmol/L甚至以上，血钠通常升高大于145mmol/L，尿素氮和肌酐常常会显著升高，反应可能会有严重的脱水和肾功能不全。</p><p>3，最危险—低血糖糖尿病患者血糖值≤3.9 mmol/L即可诊断低血糖。低血糖症是一组多种病因引起的以静脉血浆葡萄糖（简称血糖）浓度过低，临床上以交感神经兴奋和脑细胞缺氧为主要特点的综合征。低血糖的症状通常表现为出汗、饥饿、心慌、颤抖、面色苍白等，严重者还可出现精神不集中、躁动、易怒甚至昏迷等。</p>');

-- ----------------------------
-- Table structure for cp
-- ----------------------------
DROP TABLE IF EXISTS `cp`;
CREATE TABLE `cp`  (
  `cpId` int(0) NOT NULL AUTO_INCREMENT,
  `doctorId` int(0) NULL DEFAULT NULL,
  `patientId` int(0) NULL DEFAULT NULL,
  `date` date NULL DEFAULT NULL,
  `paraT1` float NULL DEFAULT NULL,
  `paraM1` float NULL DEFAULT NULL,
  `paraM2` float NULL DEFAULT NULL,
  `paraM3` float NULL DEFAULT NULL,
  `paraM4` float NULL DEFAULT NULL,
  `paraM5` float NULL DEFAULT NULL,
  `paraHL` float NULL DEFAULT NULL,
  `paraHM` float NULL DEFAULT NULL,
  `caseDesc` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '病历',
  `paraRT1` float NULL DEFAULT NULL,
  `paraRM1` float NULL DEFAULT NULL,
  `paraRM2` float NULL DEFAULT NULL,
  `paraRM3` float NULL DEFAULT NULL,
  `paraRM4` float NULL DEFAULT NULL,
  `paraRM5` float NULL DEFAULT NULL,
  `paraRHL` float NULL DEFAULT NULL,
  `paraRHM` float NULL DEFAULT NULL,
  PRIMARY KEY (`cpId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cp
-- ----------------------------
INSERT INTO `cp` VALUES (2, 3, 42, '2022-05-06', 1, 2, 3, 4, 5, 6, 7, 9, '有所恶化', 9, 8, 7, 6, 5, 5, 4, 2);
INSERT INTO `cp` VALUES (3, 3, 42, '2022-05-05', 1, 2, 3, 4, 5, 6, 7, 10, '已有很大好转', 7, 8, 9, 2, 6, 5, 4, 2);
INSERT INTO `cp` VALUES (4, 3, 49, '2022-06-05', 1, 2, 5, 4, 5, 6, 5, 3, '123', 8, 9, 9, 7, 3, 8, 9, 6);
INSERT INTO `cp` VALUES (5, 3, 42, '2022-06-06', 1, 2, 5, 4, 5, 6, 5, 3, '有所好转', 8, 9, 9, 7, 3, 8, 9, 6);

-- ----------------------------
-- Table structure for dc
-- ----------------------------
DROP TABLE IF EXISTS `dc`;
CREATE TABLE `dc`  (
  `dcid` int(0) NOT NULL,
  `cpid` int(0) NOT NULL,
  `doctorid` int(0) NOT NULL,
  `caseDesc` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`dcid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dc
-- ----------------------------

-- ----------------------------
-- Table structure for doctor
-- ----------------------------
DROP TABLE IF EXISTS `doctor`;
CREATE TABLE `doctor`  (
  `doctorId` int(0) NOT NULL,
  `doctorName` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `doctorAge` int(0) NULL DEFAULT NULL,
  `doctorPosition` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `doctorDes` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `doctorPic` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`doctorId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of doctor
-- ----------------------------
INSERT INTO `doctor` VALUES (3, '王洛阳', 45, '主治医师', '常年好评', 'http://localhost:8887/files/ecd9475077514ae18c9a4c3b326e3fea');
INSERT INTO `doctor` VALUES (34, '黄沙', 56, '主治医生', '医学水平高', 'http://localhost:8887/files/bbfee008e7d041e785e35830c52e3d7d');
INSERT INTO `doctor` VALUES (36, '罗艳', 35, '主治医生', '经验丰富', 'http://localhost:8887/files/eddbc50828354a96b325f117942c1057');
INSERT INTO `doctor` VALUES (39, '李军', 52, '副院长', '临床经验丰富', 'http://localhost:8887/files/f36ea7ba1aba454892f735e9a697884e');
INSERT INTO `doctor` VALUES (40, '李莉', 25, NULL, NULL, NULL);
INSERT INTO `doctor` VALUES (41, '张三', 35, NULL, NULL, NULL);
INSERT INTO `doctor` VALUES (43, '西喜爱', 44, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `messageId` int(0) NOT NULL AUTO_INCREMENT,
  `userName` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `parentId` int(0) NULL DEFAULT NULL,
  `commentId` int(0) NULL DEFAULT NULL,
  `messageContent` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL,
  `messageTime` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `userId` int(0) NULL DEFAULT NULL,
  `photo` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`messageId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 45 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (33, '徐煜阳', NULL, 9, '请问如何预防糖尿病呢', '2022-05-19 13:52:23', 49, 'http://localhost:8887/files/cae603dda1974bc6bf347e4e2be2b703');
INSERT INTO `message` VALUES (35, '王洛阳', 33, 9, '可以常来平台与医生咨询', '2022-05-19 13:53:41', 3, 'http://localhost:8887/files/f3739ef529ac481d83cbd19514793eab');
INSERT INTO `message` VALUES (36, '黄沙', 35, 9, '我们院有很好的医疗设备', '2022-05-19 14:28:24', 34, 'http://localhost:8887/files/cae603dda1974bc6bf347e4e2be2b703');
INSERT INTO `message` VALUES (37, '王洛阳', NULL, 11, '科普的很不错', '2022-05-19 14:30:08', 3, 'http://localhost:8887/files/06f76d1b65884758a3e0c56dc082f218');
INSERT INTO `message` VALUES (38, '黄沙', 37, 11, '我也觉得是', '2022-05-19 14:30:32', 34, 'http://localhost:8887/files/fba5339bcd0b4e8e9bc3e4bf57326fbb');
INSERT INTO `message` VALUES (44, '马婷', 33, 9, '2', '2022-06-06 16:47:24', 42, 'http://localhost:8887/files/eeaf4dfc0bf743e182625cbaf79b0fe3');

-- ----------------------------
-- Table structure for patient
-- ----------------------------
DROP TABLE IF EXISTS `patient`;
CREATE TABLE `patient`  (
  `patientId` int(0) NOT NULL,
  `patientName` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `patientGender` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `patientAge` int(0) NULL DEFAULT NULL,
  `patientAddress` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `patientPhone` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `patientEmail` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`patientId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of patient
-- ----------------------------
INSERT INTO `patient` VALUES (42, '马婷', '女', 23, '四川省', '13551842486', '207734192@qq.com');
INSERT INTO `patient` VALUES (45, '王五', '男', 24, NULL, NULL, '307725956@qq.com');
INSERT INTO `patient` VALUES (48, '白衣天使', '女', 25, NULL, NULL, NULL);
INSERT INTO `patient` VALUES (49, '徐煜阳', '男', 30, NULL, NULL, '160524888@qq.com');
INSERT INTO `patient` VALUES (50, 'san', NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for problem
-- ----------------------------
DROP TABLE IF EXISTS `problem`;
CREATE TABLE `problem`  (
  `problemId` int(0) NOT NULL AUTO_INCREMENT COMMENT '题目序号',
  `problemDesc` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '问题描述',
  `optionOne` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '选项1',
  `optionTwo` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '选项2',
  `optionThree` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '选项3',
  `optionFour` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '选项4',
  `answer` int(0) NULL DEFAULT NULL COMMENT '答案号码',
  `isRight` tinyint(1) NULL DEFAULT NULL,
  `isFalse` tinyint(1) NULL DEFAULT NULL,
  PRIMARY KEY (`problemId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of problem
-- ----------------------------
INSERT INTO `problem` VALUES (1, '糖尿病患者死亡的最主要原因是', '视网膜病变', '大血管并发症', '微血管并发症', '糖尿病肾病', 2, 0, 0);
INSERT INTO `problem` VALUES (2, '世界糖尿病日是', '11月12日', '11月13日', '11月14日', '11月15日', 3, 0, 0);
INSERT INTO `problem` VALUES (3, '下列药物不属于磺脲类药物的是', '米格列奈', '优降糖 ', '美吡哒', '亚莫利', 1, 0, 0);
INSERT INTO `problem` VALUES (4, 'I型糖尿病的胰岛素分泌特点是', '胰岛素缺乏', '胰岛素缺乏与胰岛素抵抗共同存在', '胰岛素抵抗', ' 胰岛素分泌延迟', 1, 0, 0);

SET FOREIGN_KEY_CHECKS = 1;
