/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : website_local

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2017-03-23 16:32:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `system_log`
-- ----------------------------
DROP TABLE IF EXISTS `system_log`;
CREATE TABLE `system_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `remote_addr` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT 'IP',
  `operate_time` datetime DEFAULT NULL COMMENT '操作时间',
  `operate_type` enum('LOGIN','LOGOUT','CREATE','DELETE','UPDATE') CHARACTER SET utf8 NOT NULL DEFAULT 'LOGIN' COMMENT '动作类型，CREATE,DELETE,UPDATE,LOGIN,LOGOUT',
  `operate_business` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT '业务名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='后台用户操作记录表';

-- ----------------------------
-- Records of system_log
-- ----------------------------

-- ----------------------------
-- Table structure for `system_resource`
-- ----------------------------
DROP TABLE IF EXISTS `system_resource`;
CREATE TABLE `system_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `pid` int(11) DEFAULT NULL COMMENT '父ID',
  `name` varchar(50) DEFAULT NULL COMMENT '资源名称',
  `type` enum('MENU','BUTTON') DEFAULT NULL COMMENT '资源类型',
  `url` varchar(255) DEFAULT NULL COMMENT '资源url',
  `sort` int(4) DEFAULT '0' COMMENT '排序',
  `permission` varchar(50) DEFAULT NULL COMMENT '权限字符串,例如admin:create',
  `remark` varchar(255) DEFAULT NULL COMMENT '描述',
  `publish_status` tinyint(1) DEFAULT '1' COMMENT '是否发布',
  `icon` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='资源权限表';

-- ----------------------------
-- Records of system_resource
-- ----------------------------
INSERT INTO `system_resource` VALUES ('1', '0', '系统管理', 'MENU', '/manage/admin/sys', '0', 'admin:list', '系统管理', '1', 'fa-home');
INSERT INTO `system_resource` VALUES ('2', '1', '用户管理', 'MENU', '/manage/admin/user/toList', '0', 'user:toList', '用户管理', '1', null);
INSERT INTO `system_resource` VALUES ('3', '2', '添加用户', 'BUTTON', '/manage/admin/user/add', '0', 'user:add', '添加用户', '1', null);
INSERT INTO `system_resource` VALUES ('4', '2', '删除用户', 'BUTTON', '/manage/admin/user/delete', '0', 'user:delete', '删除用户', '1', null);
INSERT INTO `system_resource` VALUES ('5', '2', '编辑用户', 'BUTTON', '/manage/admin/user/edit', '0', 'user:edit', '编辑用户', '1', null);
INSERT INTO `system_resource` VALUES ('6', '2', '查询用户', 'BUTTON', '/manage/admin/user/ajaxList', '0', 'user:ajaxList', '查询用户', '1', null);
INSERT INTO `system_resource` VALUES ('7', '1', '角色管理', 'MENU', '/manage/admin/role/toList', '0', 'role:toList', '角色管理', '1', null);
INSERT INTO `system_resource` VALUES ('8', '7', '添加角色', 'BUTTON', '/manage/admin/role/add', '0', 'role:add', '添加角色', '1', null);
INSERT INTO `system_resource` VALUES ('9', '7', '删除角色', 'BUTTON', '/manage/admin/role/delete', '0', 'role:delete', '删除角色', '1', null);
INSERT INTO `system_resource` VALUES ('10', '7', '编辑角色', 'BUTTON', '/manage/admin/role/edit', '0', 'role:edit', '编辑角色', '1', null);
INSERT INTO `system_resource` VALUES ('11', '7', '查询角色', 'BUTTON', '/manage/admin/role/ajaxList', '0', 'role:ajaxList', '查询角色', '1', null);
INSERT INTO `system_resource` VALUES ('12', '2', '用户授权', 'BUTTON', '/manage/admin/user/setRole', '0', 'user:setRole', '用户授权', '1', null);
INSERT INTO `system_resource` VALUES ('13', '7', '角色授权', 'BUTTON', '/manage/admin/role/setResource', '0', 'role:setResource', '角色授权', '1', null);
INSERT INTO `system_resource` VALUES ('14', '1', '权限管理', 'MENU', '/manage/admin/resource/toList', '0', 'resource:toList', '权限管理', '1', null);
INSERT INTO `system_resource` VALUES ('15', '14', '添加权限', 'BUTTON', '/manage/admin/resource/add', '0', 'resource:add', '添加权限', '1', null);
INSERT INTO `system_resource` VALUES ('16', '14', '删除权限', 'BUTTON', '/manage/admin/resource/delete', '0', 'resource:delete', '删除权限', '1', null);
INSERT INTO `system_resource` VALUES ('17', '14', '编辑权限', 'BUTTON', '/manage/admin/resource/edit', '0', 'resource:edit', '编辑权限', '1', null);
INSERT INTO `system_resource` VALUES ('18', '14', '查询权限', 'BUTTON', '/manage/admin/resource/ajaxList', '0', 'resource:ajaxList', '查询权限', '1', null);
INSERT INTO `system_resource` VALUES ('19', '1', '日志管理', 'MENU', '/manage/admin/log/toList', '0', 'log:toList', '日志管理', '1', null);

-- ----------------------------
-- Table structure for `system_role`
-- ----------------------------
DROP TABLE IF EXISTS `system_role`;
CREATE TABLE `system_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_name` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `role_code` varchar(50) DEFAULT NULL COMMENT '角色值',
  `publish_status` tinyint(1) DEFAULT '1' COMMENT '是否发布',
  `remark` varchar(255) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of system_role
-- ----------------------------
INSERT INTO `system_role` VALUES ('1', '超级管理员', 'admin', '1', '超级管理员');

-- ----------------------------
-- Table structure for `system_role_resource`
-- ----------------------------
DROP TABLE IF EXISTS `system_role_resource`;
CREATE TABLE `system_role_resource` (
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `resource_id` int(11) NOT NULL COMMENT '资源ID',
  PRIMARY KEY (`role_id`,`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色资源中间表';

-- ----------------------------
-- Records of system_role_resource
-- ----------------------------
INSERT INTO `system_role_resource` VALUES ('1', '1');
INSERT INTO `system_role_resource` VALUES ('1', '2');
INSERT INTO `system_role_resource` VALUES ('1', '3');
INSERT INTO `system_role_resource` VALUES ('1', '4');
INSERT INTO `system_role_resource` VALUES ('1', '5');
INSERT INTO `system_role_resource` VALUES ('1', '6');
INSERT INTO `system_role_resource` VALUES ('1', '7');
INSERT INTO `system_role_resource` VALUES ('1', '8');
INSERT INTO `system_role_resource` VALUES ('1', '9');
INSERT INTO `system_role_resource` VALUES ('1', '10');
INSERT INTO `system_role_resource` VALUES ('1', '11');
INSERT INTO `system_role_resource` VALUES ('1', '12');
INSERT INTO `system_role_resource` VALUES ('1', '13');
INSERT INTO `system_role_resource` VALUES ('1', '14');
INSERT INTO `system_role_resource` VALUES ('1', '15');
INSERT INTO `system_role_resource` VALUES ('1', '16');
INSERT INTO `system_role_resource` VALUES ('1', '17');
INSERT INTO `system_role_resource` VALUES ('1', '18');
INSERT INTO `system_role_resource` VALUES ('1', '19');

-- ----------------------------
-- Table structure for `system_user`
-- ----------------------------
DROP TABLE IF EXISTS `system_user`;
CREATE TABLE `system_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(50) DEFAULT NULL COMMENT '登陆账号',
  `user_pwd` varchar(50) DEFAULT NULL COMMENT '密码',
  `email` varchar(255) DEFAULT NULL COMMENT '电子邮件',
  `contact` varchar(20) DEFAULT NULL COMMENT '联系方式',
  `reail_name` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `sex` int(1) DEFAULT '2' COMMENT '性别，0-女，1-男，2-未知',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `address` varchar(255) DEFAULT NULL COMMENT '住址',
  `token` varchar(100) DEFAULT NULL COMMENT '加盐',
  `head_img` varchar(255) DEFAULT NULL COMMENT '头像地址',
  `register_time` datetime DEFAULT NULL COMMENT '注册时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `login_num` int(11) DEFAULT '0' COMMENT '登录次数',
  `is_locked` tinyint(1) DEFAULT '0' COMMENT '是否锁定',
  `is_admin` tinyint(1) DEFAULT '0' COMMENT '是否管理员',
  `remote_addr` varchar(50) DEFAULT NULL COMMENT '最后登录IP',
  `publish_status` tinyint(1) DEFAULT '1' COMMENT '是否可用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`user_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of system_user
-- ----------------------------
INSERT INTO `system_user` VALUES ('1', 'admin', 'f9620b2992d9134421fd292f8834af3b', 'liudw2@163.com', '18520147639', '超级管理员', '2', '1988-10-03', '广东省广州市', '123qwe', '/static/hplus4/img/profile_small.jpg', '2016-12-23 22:33:18', '2016-12-23 22:33:44', '0', '0', '1', '127.0.0.1', '1');

-- ----------------------------
-- Table structure for `system_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `system_user_role`;
CREATE TABLE `system_user_role` (
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色中间表';

-- ----------------------------
-- Records of system_user_role
-- ----------------------------
INSERT INTO `system_user_role` VALUES ('1', '1');

-- ----------------------------
-- Table structure for `wechat_sms_record`
-- ----------------------------
DROP TABLE IF EXISTS `wechat_sms_record`;
CREATE TABLE `wechat_sms_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(50) DEFAULT NULL COMMENT '发送标题',
  `content` varchar(255) DEFAULT NULL COMMENT '发送内容',
  `phone` varchar(20) DEFAULT NULL COMMENT '送达手机号',
  `send_time` datetime DEFAULT NULL COMMENT '发送时间',
  `status` int(1) DEFAULT '1' COMMENT '发送状态',
  `result` varchar(255) DEFAULT NULL COMMENT '返回结果',
  `user_id` int(20) DEFAULT NULL COMMENT '微信用户ID',
  `nickname` varchar(50) DEFAULT NULL COMMENT '微信用户昵称',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=74 DEFAULT CHARSET=utf8 COMMENT='手机短信发送记录表';

-- ----------------------------
-- Records of wechat_sms_record
-- ----------------------------
INSERT INTO `wechat_sms_record` VALUES ('69', null, '请输入验证码735771完成手机验证(10分钟内有效)。如非本人操作请忽略。', '13286873647', '2016-07-19 17:51:37', '1', '0', '3510', '小樹~');
INSERT INTO `wechat_sms_record` VALUES ('70', null, '请输入验证码490420完成手机验证(10分钟内有效)。如非本人操作请忽略。', '13286873637', '2016-07-20 11:18:08', '1', '0', '3510', '小樹~');
INSERT INTO `wechat_sms_record` VALUES ('71', null, '请输入验证码517290完成手机验证(10分钟内有效)。如非本人操作请忽略。', '13286873647', '2016-08-29 15:07:08', '1', '0', '3510', '小樹~');
INSERT INTO `wechat_sms_record` VALUES ('72', null, '请输入验证码626923完成手机验证(10分钟内有效)。如非本人操作请忽略。', '13286873647', '2016-08-29 16:04:56', '1', '0', '3510', '小樹~');
INSERT INTO `wechat_sms_record` VALUES ('73', null, '请输入验证码960737完成手机验证(10分钟内有效)。如非本人操作请忽略。', '13711013729', '2016-09-14 15:06:22', '1', '0', '3514', '大頭蝦*');

-- ----------------------------
-- Table structure for `wechat_template`
-- ----------------------------
DROP TABLE IF EXISTS `wechat_template`;
CREATE TABLE `wechat_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `match_text` varchar(255) DEFAULT NULL COMMENT '消息头匹配',
  `access_type` varchar(100) DEFAULT NULL COMMENT '接收到的消息类型',
  `msg_type` varchar(100) DEFAULT NULL COMMENT '消息类型',
  `content` text COMMENT '消息内容',
  `media_id` varchar(255) DEFAULT NULL COMMENT '媒体ID',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `description` longtext COMMENT '描述',
  `music_url` varchar(255) DEFAULT NULL COMMENT '音乐地址',
  `hd_music_url` varchar(255) DEFAULT NULL,
  `thumb_media_id` varchar(255) DEFAULT NULL,
  `article_count` int(11) DEFAULT NULL,
  `articles` text,
  `pic_url` varchar(255) DEFAULT NULL COMMENT '图片地址',
  `url` varchar(255) DEFAULT NULL,
  `publish_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '发布状态',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `created_user_id` int(20) NOT NULL DEFAULT '1' COMMENT '创建用户ID',
  `modified_time` datetime NOT NULL COMMENT '编辑时间',
  `modified_user_id` int(20) NOT NULL DEFAULT '1' COMMENT '编辑用户ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='微信自动回复消息表';

-- ----------------------------
-- Records of wechat_template
-- ----------------------------
INSERT INTO `wechat_template` VALUES ('1', '微信订阅号首次关注回复内容', 'event', 'text', '孩子注意力不集中？做事磨蹭拖拉？总跟父母对着干？\r\n\r\n名师专家的方法和别人家的成功经验，都！试！了！可为何就是，不？适？合？\r\n\r\n每个孩子都应得到专属的个性化教育，因为每个孩子都是独一无二的，如何捕捉孩子天生的性格特质，给孩子提供更适合的教育？点击进入吉吉幼学幼升小测评，带你探知孩子真实的内心：http://t.cn/R5WKHow\r\n\r\n霸王餐报名，请猛戳：http://t.cn/Rt7Jw8U', '', '', '', '', '', '', '0', '', '', '', '0', '2016-04-11 06:21:07', '2', '2016-04-12 18:06:07', '2');
INSERT INTO `wechat_template` VALUES ('2', '微信订阅号默认回复内容', 'text', 'text', '还想了解什么，快发给我，尽快满足你，等我哦！', '', '', '', '', '', '', '0', '', '', '', '0', '2016-04-11 06:38:51', '2', '2016-04-11 06:39:24', '2');

-- ----------------------------
-- Table structure for `wechat_user`
-- ----------------------------
DROP TABLE IF EXISTS `wechat_user`;
CREATE TABLE `wechat_user` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `subscribe` int(11) DEFAULT NULL,
  `openid` varchar(255) DEFAULT NULL COMMENT '用户唯一标识',
  `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
  `sex` int(11) DEFAULT NULL COMMENT '性别',
  `city` varchar(255) DEFAULT NULL COMMENT '城市',
  `country` varchar(255) DEFAULT NULL COMMENT '国家',
  `province` varchar(255) DEFAULT NULL COMMENT '省份',
  `language` varchar(255) DEFAULT NULL COMMENT '语言',
  `headimgurl` varchar(255) DEFAULT NULL COMMENT '头像url',
  `subscribe_time` varchar(255) DEFAULT NULL,
  `unionid` varchar(255) DEFAULT NULL COMMENT '用户标识',
  `groupid` int(20) DEFAULT NULL COMMENT '分组id',
  `remark` text COMMENT '备注',
  `publish_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '发布状态',
  `created_time` date DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3528 DEFAULT CHARSET=utf8 COMMENT='微信用户表';

-- ----------------------------
-- Records of wechat_user
-- ----------------------------

-- ----------------------------
-- Procedure structure for `formDataList`
-- ----------------------------
DROP PROCEDURE IF EXISTS `formDataList`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `formDataList`(tableName varchar(50),dbFields varchar(500),whereSql varchar(1000))
BEGIN
	declare exe_sql varchar(2000);
	SET exe_sql = CONCAT("select ",dbFields," from ",tableName,whereSql);
	set @v_sql=exe_sql;   
	prepare stmt from @v_sql; 
	EXECUTE stmt;     
	deallocate prepare stmt; 
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `replaceOrgCode`
-- ----------------------------
DROP PROCEDURE IF EXISTS `replaceOrgCode`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `replaceOrgCode`()
BEGIN
DECLARE code_length int DEFAULT 0 ;
DECLARE new_code VARCHAR(500);
DECLARE org_id VARCHAR(500);
DECLARE old_code VARCHAR(500);
DECLARE code_length_index int DEFAULT 1;
DECLARE b int default 0;
DECLARE pro CURSOR for select id,org_code,LENGTH(org_code) from t_s_depart;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET b = 1;
OPEN pro;
FETCH pro into org_id,old_code,code_length;
 while b<>1 do
   set code_length_index=1;
	 set new_code='';
	 while code_length_index<code_length do
     set new_code=CONCAT(new_code,'A',SUBSTR(old_code,code_length_index,2));
     set code_length_index=code_length_index+2;
   end while;
    update t_s_depart set org_code=new_code where id=org_id;
  FETCH pro into org_id,old_code,code_length;
end while;
close pro;
end
;;
DELIMITER ;
