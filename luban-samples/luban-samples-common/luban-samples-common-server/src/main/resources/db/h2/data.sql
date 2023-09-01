DELETE FROM t_user;
INSERT INTO t_user (name, age, email, feature_bit, feature_json) VALUES
('张三1', 10, 'zhangsan@qq.com', 0, '{"@type":"com.wiflish.luban.samples.common.mybatis.domain.vo.UserAddress", "address": "福田区1", "zipcode": "518001", "sex": 1}'),
('张三2', 10, 'zhangsan@qq.com', 1, '{"@type":"com.wiflish.luban.samples.common.mybatis.domain.vo.UserAddress", "address": "福田区2", "zipcode": "518002"}'),
('张三3', 10, 'zhangsan@qq.com', 2, '{"@type":"com.wiflish.luban.samples.common.mybatis.domain.vo.UserAddress", "address": "福田区3", "zipcode": "518003"}'),
('张三4', 10, 'zhangsan@qq.com', 4, '{"@type":"com.wiflish.luban.samples.common.mybatis.domain.vo.UserAddress", "address": "福田区4", "zipcode": "518004"}'),
('张三5', 10, 'zhangsan@qq.com', 7, '{"@type":"com.wiflish.luban.samples.common.mybatis.domain.vo.UserAddress", "address": "福田区5", zipcode": "518005"}'),
('张三6', 10, 'zhangsan@qq.com', 15, '{"@type":"com.wiflish.luban.samples.common.mybatis.domain.vo.UserAddress", "address": "福田区6", "zipcode": "518006"}1');