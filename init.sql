CREATE TABLE `tiny_id_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `biz_type` varchar(63) NOT NULL DEFAULT '' COMMENT '业务类型，唯一',
  `begin_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '开始id，仅记录初始值，无其他含义。初始化时begin_id和max_id应相同',
  `max_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '当前最大id',
  `step` int(11) DEFAULT '0' COMMENT '步长',
  `delta` int(11) NOT NULL DEFAULT '1' COMMENT '每次id增量',
  `remainder` int(11) NOT NULL DEFAULT '0' COMMENT '余数',
  `create_time` timestamp NOT NULL DEFAULT '2010-01-01 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '2010-01-01 00:00:00' COMMENT '更新时间',
  `version` bigint(20) NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_biz_type` (`biz_type`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT 'id信息表';

INSERT INTO `tiny_id_info` (`id`, `biz_type`, `begin_id`, `max_id`, `step`, `delta`, `remainder`, `create_time`, `update_time`, `version`)
VALUES
	(1, 'global_sequence', 100000000000, 100000000000, 1000, 1, 0, '2020-11-20 10:00:58', '2020-11-20 10:00:58', 1);

INSERT INTO `tiny_id_info` (`id`, `biz_type`, `begin_id`, `max_id`, `step`, `delta`, `remainder`, `create_time`, `update_time`, `version`)
VALUES
	(2, 'user_sequence', 100000000000, 100000000000, 1000, 1, 0, '2020-11-20 10:00:58', '2020-11-20 10:00:58', 1);

INSERT INTO `tiny_id_info` (`id`, `biz_type`, `begin_id`, `max_id`, `step`, `delta`, `remainder`, `create_time`, `update_time`, `version`)
VALUES
	(3, 'study_sequence', 100000000000, 100000000000, 1000, 1, 0, '2020-11-20 10:00:58', '2020-11-20 10:00:58', 1);


