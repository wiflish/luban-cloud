-- create database luban-samples-ddd default character set utf8mb4 collate utf8mb4_unicode_ci;

-- use demo;

CREATE TABLE `t_task` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id.',
  `name` varchar(50) NOT NULL COMMENT '任务名称.',
  `remark` varchar(250) NOT NULL DEFAULT '' COMMENT '任务描述.',
  `status` int NOT NULL DEFAULT 1 COMMENT '任务状态. 1=待开始 2=进行中 3=已完成',

  `version` bigint NOT NULL DEFAULT 0 COMMENT '乐观锁字段.',
  `deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '是否已删除：0=未删除；1=已删除',
  `create_id` bigint NOT NULL DEFAULT 0 COMMENT '记录创建者id. 0=系统创建',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_id` bigint NOT NULL DEFAULT 0 COMMENT '记录更新者id. 0=系统更新.',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务.';