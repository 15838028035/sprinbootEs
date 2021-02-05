CREATE DATABASE /*!32312 IF NOT EXISTS*/`UPM` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `UPM`;


SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for UPM_USER
-- ----------------------------
DROP TABLE IF EXISTS `UPM_USER`;
CREATE TABLE `UPM_USER` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_no` varchar(255) DEFAULT NULL COMMENT '登陆账号',
  `user_pass` varchar(255) DEFAULT NULL COMMENT '登陆密码',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_user_name` varchar(255) DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user_id` bigint(20) DEFAULT NULL COMMENT '更新人',
  `update_user_name` varchar(255) DEFAULT NULL COMMENT '更新人姓名',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='系统用户表';

-- ----------------------------
-- Records of UPM_USER
-- ----------------------------
INSERT INTO `UPM_USER` VALUES ('1', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '0', 'admin', '2019-03-31 04:07:57', '1', 'admin', '2019-03-31 04:07:57');
SET FOREIGN_KEY_CHECKS=1;


-- ----------------------------
-- Table structure for UPM_ROLE
-- ----------------------------
DROP TABLE IF EXISTS `UPM_ROLE`;
CREATE TABLE `UPM_ROLE` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_code` varchar(255) DEFAULT NULL COMMENT '角色编码',
  `app_id` varchar(255) DEFAULT NULL COMMENT '应用ID',
  `role_name` varchar(255) DEFAULT NULL COMMENT '角色名称',
  `role_desc` varchar(255) DEFAULT NULL COMMENT '角色描述',
  `lock_Status` varchar(255) DEFAULT NULL COMMENT '加锁状态,1:加锁，0:未加锁',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_user_name` varchar(255) DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user_id` bigint(20) DEFAULT NULL COMMENT '更新人',
  `update_user_name` varchar(255) DEFAULT NULL COMMENT '更新人姓名',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='角色信息表';

-- ----------------------------
-- Table structure for upm_Permission
-- ----------------------------
DROP TABLE IF EXISTS `upm_Permission`;
CREATE TABLE `upm_Permission` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `app_id` varchar(255) DEFAULT NULL COMMENT '应用ID',
  `parent_Id` bigint(10)  NULL  COMMENT '父ID',
  `per_name` varchar(255) DEFAULT NULL COMMENT '权限名称',
  `per_type` varchar(255) DEFAULT NULL COMMENT '权限类型',
  `url` varchar(255) DEFAULT NULL COMMENT 'URL',
  `code` varchar(255) DEFAULT NULL COMMENT '编码',
  `key_code` varchar(255) DEFAULT NULL COMMENT '编码key',
  `state` varchar(255) DEFAULT NULL COMMENT '1:正常，0：加锁',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `sort_no` bigint(10)  NULL  COMMENT '排序编号',
  
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_user_name` varchar(255) DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user_id` bigint(20) DEFAULT NULL COMMENT '更新人',
  `update_user_name` varchar(255) DEFAULT NULL COMMENT '更新人姓名',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='权限信息表';

-- 2019-04-11 刘杰
DROP TABLE IF EXISTS `upm_role_and_permission_rel`;
CREATE TABLE `upm_role_and_permission_rel` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` bigint NULL COMMENT '角色ID',
  `permission_id` bigint(10)  NULL  COMMENT '权限ID',
  
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_user_name` varchar(255) DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user_id` bigint(20) DEFAULT NULL COMMENT '更新人',
  `update_user_name` varchar(255) DEFAULT NULL COMMENT '更新人姓名',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='角色权限关联表';


-- 2019-04-30 刘杰
DROP TABLE IF EXISTS `upm_user_and_upm_role`;
CREATE TABLE `upm_user_and_upm_role` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint NULL COMMENT '用户ID',
  `role_id` bigint(10)  NULL  COMMENT '角色ID',
  
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_user_name` varchar(255) DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user_id` bigint(20) DEFAULT NULL COMMENT '更新人',
  `update_user_name` varchar(255) DEFAULT NULL COMMENT '更新人姓名',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='用户角色关联表';

