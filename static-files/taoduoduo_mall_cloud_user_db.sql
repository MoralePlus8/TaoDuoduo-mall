# 创建用户服务所需数据
CREATE DATABASE /*!32312 IF NOT EXISTS */`taoduoduo_mall_cloud_user_db` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `taoduoduo_mall_cloud_user_db`;

DROP TABLE IF EXISTS `tb_taoduoduo_mall_user`;

# 创建商城用户表

CREATE TABLE `tb_taoduoduo_mall_user`
(
    `user_id`      bigint(20)         NOT NULL COMMENT '用户主键id',
    `user_name`    varchar(50) UNIQUE NOT NULL DEFAULT '' COMMENT '登陆名称',
    `password_md5` varchar(128)       NOT NULL DEFAULT '' COMMENT 'MD5加密后的密码',
    `salt`         varchar(50)        NOT NULL DEFAULT '' COMMENT '用户密码盐',
    `phone_number` varchar(11)        NOT NULL DEFAULT '' COMMENT '手机号',
    `email`        varchar(50)        NOT NULL DEFAULT '' COMMENT '邮箱',
    `avatar`       varchar(200)       NOT NULL DEFAULT '' COMMENT '用户头像',
    `deleted`      tinyint(1)         NOT NULL DEFAULT '0' COMMENT '注销标识字段(0-正常 1-已注销)',
    `locked`       tinyint(1)                  DEFAULT '0' COMMENT '锁定标识字段(0-未锁定 1-已锁定)',
    `create_time`  datetime           NOT NULL COMMENT '注册时间',
    `update_time`  datetime           NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC;

CREATE TABLE `tb_taoduoduo_mall_auth`
(
    `auth_id`   bigint(20)  NOT NULL COMMENT '角色id',
    `auth_name` varchar(50) NOT NULL DEFAULT '' COMMENT '角色名称',
    `user_id`   bigint(20)  NOT NULL COMMENT '用户id',

    INDEX `idx_user_id` (`user_id`) USING BTREE,
    PRIMARY KEY (`auth_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC;