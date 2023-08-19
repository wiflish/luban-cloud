DROP DATABASE IF EXISTS luban_samples;

CREATE DATABASE luban_samples DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE luban_samples;

DROP TABLE IF EXISTS t_user;

CREATE TABLE t_user
(
	id BIGINT AUTO_INCREMENT NOT NULL COMMENT '主键ID',
	name VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
	age INT NULL DEFAULT NULL COMMENT '年龄',
	email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
	feature_bit BIGINT NULL DEFAULT 0 COMMENT '参数设置, 右边第一个bit=是否有头像;第二个bit=是否有微信；第三个bit=是否有支付宝',
	feature_json VARCHAR(5000) NULL DEFAULT NULL COMMENT 'json结构, 用户地址对象',

    `version`          bigint       NOT NULL DEFAULT 0 COMMENT '乐观锁字段.',
    `deleted`          tinyint unsigned NOT NULL DEFAULT '0' COMMENT '是否已删除：0=未删除；1=已删除',
    `create_id`        bigint       NOT NULL DEFAULT 0 COMMENT '记录创建者id. 0=系统创建',
    `create_time`      datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_id`        bigint       NOT NULL DEFAULT 0 COMMENT '记录更新者id. 0=系统更新.',
    `update_time`      datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (id)
);