-- create database luban-samples-ddd default character set utf8mb4 collate utf8mb4_unicode_ci;

-- use demo;

DROP TABLE IF EXISTS t_task;
CREATE TABLE `t_task` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id.',
  `name` varchar(50)  NULL COMMENT '任务名称.',
  `remark` varchar(250)  NULL DEFAULT '' COMMENT '任务描述.',
  `status` int  NULL DEFAULT 1 COMMENT '任务状态. 1=待开始 2=进行中 3=已完成',
  feature_bit BIGINT NULL DEFAULT 0 COMMENT '参数设置, 右边第一个bit=是否有头像;第二个bit=是否有微信；第三个bit=是否有支付宝',
  feature_json VARCHAR(5000) NULL DEFAULT NULL COMMENT 'json结构, 用户地址对象',

  `version` bigint  NULL DEFAULT 0 COMMENT '乐观锁字段.',
  `deleted` int  NULL DEFAULT '0' COMMENT '是否已删除：0=未删除；1=已删除',
  `create_id` bigint  NULL DEFAULT 0 COMMENT '记录创建者id. 0=系统创建',
  `create_time` datetime  NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_id` bigint  NULL DEFAULT 0 COMMENT '记录更新者id. 0=系统更新.',
  `update_time` datetime  NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
);
