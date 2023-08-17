DROP TABLE IF EXISTS t_user;

CREATE TABLE t_user
(
	id BIGINT NOT NULL COMMENT '主键ID',
	name VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
	age INT NULL DEFAULT NULL COMMENT '年龄',
	email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
	settings BIGINT NULL DEFAULT 0 COMMENT '参数设置, 右边第一个bit=是否有头像;第二个bit=是否有微信；第三个bit=是否有支付宝',
	other_info VARCHAR(5000) NULL DEFAULT NULL COMMENT 'json结构, 用户地址对象',
	PRIMARY KEY (id)
);