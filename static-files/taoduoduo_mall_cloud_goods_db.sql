# 创建商品服务所需数据
CREATE DATABASE /*!32312 IF NOT EXISTS */`taoduoduo_mall_cloud_goods_db` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `taoduoduo_mall_cloud_goods_db`;

# 创建商品分类表

DROP TABLE IF EXISTS `tb_taoduoduo_mall_category`;
CREATE TABLE `tb_taoduoduo_mall_category`
(
    `category_id`    bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '分类id',
    `category_level` tinyint(4)          NOT NULL DEFAULT '0' COMMENT '分类级别(1-一级分类 2-二级分类 3-三级分类)',
    `parent_id`      bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '父分类id',
    `category_name`  varchar(50)         NOT NULL DEFAULT '' COMMENT '分类名称',
    `category_rank`  int(11)             NOT NULL DEFAULT '0' COMMENT '排序值(字段越大越靠前)',
    `is_deleted`     tinyint             NOT NULL DEFAULT '0' COMMENT '删除标识字段(0-未删除 1-已删除)',
    `create_time`    timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_user`    bigint(11) unsigned NOT NULL DEFAULT '0' COMMENT '创建者id',
    `update_time`    timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `update_user`    bigint(11) unsigned          DEFAULT '0' COMMENT '修改者id',
    INDEX `idx_parent_id` (`parent_id`) USING BTREE,
    PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC;


INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (1, 1, 0, '测试1级分类', 1, 0, '2025-04-17 04:28:32', 0, '2025-04-17 04:28:32', 0);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (2, 2, 1, '测试2级分类', 2, 0, '2025-04-17 04:28:32', 0, '2025-04-17 04:28:32', 0);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (3, 3, 2, '测式3级分类', 3, 0, '2025-04-17 04:28:32', 0, '2025-04-17 04:28:32', 0);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (4, 1, 0, '电子产品', 100, 0, '2025-04-17 05:51:50', 1, '2025-04-17 05:51:50', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (5, 1, 0, '家用电器', 95, 0, '2025-04-17 05:51:50', 1, '2025-04-17 05:51:50', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (6, 1, 0, '服装服饰', 90, 0, '2025-04-17 05:51:50', 1, '2025-04-17 05:51:50', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (7, 1, 0, '食品饮料', 85, 0, '2025-04-17 05:51:50', 1, '2025-04-17 05:51:50', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (8, 1, 0, '家居用品', 80, 0, '2025-04-17 05:51:50', 1, '2025-04-17 05:51:50', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (9, 1, 0, '运动户外', 75, 0, '2025-04-17 05:51:50', 1, '2025-04-17 05:51:50', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (10, 1, 0, '美妆个护', 70, 0, '2025-04-17 05:51:50', 1, '2025-04-17 05:51:50', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (11, 1, 0, '图书音像', 65, 0, '2025-04-17 05:51:50', 1, '2025-04-17 05:51:50', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (12, 1, 0, '母婴用品', 60, 0, '2025-04-17 05:51:50', 1, '2025-04-17 05:51:50', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (13, 1, 0, '汽车用品', 55, 0, '2025-04-17 05:51:50', 1, '2025-04-17 05:51:50', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (14, 2, 4, '手机通讯', 50, 0, '2025-04-17 05:51:53', 1, '2025-04-17 05:51:53', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (15, 2, 4, '电脑办公', 45, 0, '2025-04-17 05:51:53', 1, '2025-04-17 05:51:53', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (16, 2, 4, '数码配件', 40, 0, '2025-04-17 05:51:53', 1, '2025-04-17 05:51:53', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (17, 2, 4, '智能设备', 35, 0, '2025-04-17 05:51:53', 1, '2025-04-17 05:51:53', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (18, 2, 5, '大家电', 50, 0, '2025-04-17 05:51:53', 1, '2025-04-17 05:51:53', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (19, 2, 5, '厨房电器', 45, 0, '2025-04-17 05:51:53', 1, '2025-04-17 05:51:53', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (20, 2, 5, '生活电器', 40, 0, '2025-04-17 05:51:53', 1, '2025-04-17 05:51:53', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (21, 2, 5, '个人护理', 35, 0, '2025-04-17 05:51:53', 1, '2025-04-17 05:51:53', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (22, 2, 6, '男装', 50, 0, '2025-04-17 05:51:53', 1, '2025-04-17 05:51:53', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (23, 2, 6, '女装', 45, 0, '2025-04-17 05:51:53', 1, '2025-04-17 05:51:53', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (24, 2, 6, '童装', 40, 0, '2025-04-17 05:51:53', 1, '2025-04-17 05:51:53', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (25, 2, 6, '鞋靴箱包', 35, 0, '2025-04-17 05:51:53', 1, '2025-04-17 05:51:53', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (26, 2, 7, '休闲零食', 50, 0, '2025-04-17 05:51:53', 1, '2025-04-17 05:51:53', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (27, 2, 7, '酒水饮料', 45, 0, '2025-04-17 05:51:53', 1, '2025-04-17 05:51:53', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (28, 2, 7, '生鲜果蔬', 40, 0, '2025-04-17 05:51:53', 1, '2025-04-17 05:51:53', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (29, 2, 7, '粮油调味', 35, 0, '2025-04-17 05:51:53', 1, '2025-04-17 05:51:53', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (30, 3, 14, '智能手机', 30, 0, '2025-04-17 05:51:54', 1, '2025-04-17 05:51:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (31, 3, 14, '老人手机', 25, 0, '2025-04-17 05:51:54', 1, '2025-04-17 05:51:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (32, 3, 14, '对讲机', 20, 0, '2025-04-17 05:51:54', 1, '2025-04-17 05:51:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (33, 3, 15, '笔记本', 30, 0, '2025-04-17 05:51:54', 1, '2025-04-17 05:51:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (34, 3, 15, '台式机', 25, 0, '2025-04-17 05:51:54', 1, '2025-04-17 05:51:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (35, 3, 15, '平板电脑', 20, 0, '2025-04-17 05:51:54', 1, '2025-04-17 05:51:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (36, 3, 18, '电视机', 30, 0, '2025-04-17 05:51:54', 1, '2025-04-17 05:51:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (37, 3, 18, '冰箱', 25, 0, '2025-04-17 05:51:54', 1, '2025-04-17 05:51:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (38, 3, 18, '洗衣机', 20, 0, '2025-04-17 05:51:54', 1, '2025-04-17 05:51:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (39, 3, 22, 'T恤', 30, 0, '2025-04-17 05:51:54', 1, '2025-04-17 05:51:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (40, 3, 22, '衬衫', 25, 0, '2025-04-17 05:51:54', 1, '2025-04-17 05:51:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (41, 3, 22, '牛仔裤', 20, 0, '2025-04-17 05:51:54', 1, '2025-04-17 05:51:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (42, 3, 26, '坚果炒货', 30, 0, '2025-04-17 05:51:54', 1, '2025-04-17 05:51:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (43, 3, 26, '膨化食品', 25, 0, '2025-04-17 05:51:54', 1, '2025-04-17 05:51:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (44, 3, 26, '糖果巧克力', 20, 0, '2025-04-17 05:51:54', 1, '2025-04-17 05:51:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (45, 2, 9, '健身器材', 50, 0, '2025-04-17 05:54:53', 1, '2025-04-17 05:54:53', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (46, 2, 9, '户外装备', 45, 0, '2025-04-17 05:54:53', 1, '2025-04-17 05:54:53', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (47, 2, 9, '运动服饰', 40, 0, '2025-04-17 05:54:53', 1, '2025-04-17 05:54:53', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (48, 2, 9, '球类运动', 35, 0, '2025-04-17 05:54:53', 1, '2025-04-17 05:54:53', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (49, 2, 10, '护肤', 50, 0, '2025-04-17 05:54:53', 1, '2025-04-17 05:54:53', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (50, 2, 10, '彩妆', 45, 0, '2025-04-17 05:54:53', 1, '2025-04-17 05:54:53', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (51, 2, 10, '香水', 40, 0, '2025-04-17 05:54:53', 1, '2025-04-17 05:54:53', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (52, 2, 10, '个人护理', 35, 0, '2025-04-17 05:54:53', 1, '2025-04-17 05:54:53', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (53, 2, 11, '图书', 50, 0, '2025-04-17 05:54:53', 1, '2025-04-17 05:54:53', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (54, 2, 11, '音像', 45, 0, '2025-04-17 05:54:53', 1, '2025-04-17 05:54:53', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (55, 2, 11, '电子书', 40, 0, '2025-04-17 05:54:53', 1, '2025-04-17 05:54:53', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (56, 2, 11, '文具', 35, 0, '2025-04-17 05:54:53', 1, '2025-04-17 05:54:53', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (57, 2, 12, '奶粉辅食', 50, 0, '2025-04-17 05:54:53', 1, '2025-04-17 05:54:53', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (58, 2, 12, '尿裤湿巾', 45, 0, '2025-04-17 05:54:53', 1, '2025-04-17 05:54:53', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (59, 2, 12, '婴童服饰', 40, 0, '2025-04-17 05:54:53', 1, '2025-04-17 05:54:53', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (60, 2, 12, '玩具', 35, 0, '2025-04-17 05:54:53', 1, '2025-04-17 05:54:53', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (61, 2, 13, '汽车配件', 50, 0, '2025-04-17 05:54:53', 1, '2025-04-17 05:54:53', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (62, 2, 13, '汽车美容', 45, 0, '2025-04-17 05:54:53', 1, '2025-04-17 05:54:53', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (63, 2, 13, '车载电器', 40, 0, '2025-04-17 05:54:53', 1, '2025-04-17 05:54:53', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (64, 2, 13, '汽车装饰', 35, 0, '2025-04-17 05:54:53', 1, '2025-04-17 05:54:53', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (65, 3, 16, '手机壳/保护套', 30, 0, '2025-04-17 05:54:54', 1, '2025-04-17 05:54:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (66, 3, 16, '充电器/数据线', 25, 0, '2025-04-17 05:54:54', 1, '2025-04-17 05:54:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (67, 3, 16, '移动电源', 20, 0, '2025-04-17 05:54:54', 1, '2025-04-17 05:54:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (68, 3, 16, '耳机/耳麦', 15, 0, '2025-04-17 05:54:54', 1, '2025-04-17 05:54:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (69, 3, 17, '智能手表', 30, 0, '2025-04-17 05:54:54', 1, '2025-04-17 05:54:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (70, 3, 17, '智能家居', 25, 0, '2025-04-17 05:54:54', 1, '2025-04-17 05:54:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (71, 3, 17, '智能音箱', 20, 0, '2025-04-17 05:54:54', 1, '2025-04-17 05:54:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (72, 3, 17, '无人机', 15, 0, '2025-04-17 05:54:54', 1, '2025-04-17 05:54:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (73, 3, 19, '电饭煲', 30, 0, '2025-04-17 05:54:54', 1, '2025-04-17 05:54:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (74, 3, 19, '微波炉', 25, 0, '2025-04-17 05:54:54', 1, '2025-04-17 05:54:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (75, 3, 19, '榨汁机', 20, 0, '2025-04-17 05:54:54', 1, '2025-04-17 05:54:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (76, 3, 19, '空气炸锅', 15, 0, '2025-04-17 05:54:54', 1, '2025-04-17 05:54:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (77, 3, 20, '电风扇', 30, 0, '2025-04-17 05:54:54', 1, '2025-04-17 05:54:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (78, 3, 20, '空气净化器', 25, 0, '2025-04-17 05:54:54', 1, '2025-04-17 05:54:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (79, 3, 20, '吸尘器', 20, 0, '2025-04-17 05:54:54', 1, '2025-04-17 05:54:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (80, 3, 20, '加湿器', 15, 0, '2025-04-17 05:54:54', 1, '2025-04-17 05:54:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (81, 3, 23, '连衣裙', 30, 0, '2025-04-17 05:54:54', 1, '2025-04-17 05:54:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (82, 3, 23, '半身裙', 25, 0, '2025-04-17 05:54:54', 1, '2025-04-17 05:54:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (83, 3, 23, '针织衫', 20, 0, '2025-04-17 05:54:54', 1, '2025-04-17 05:54:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (84, 3, 23, '外套', 15, 0, '2025-04-17 05:54:54', 1, '2025-04-17 05:54:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (85, 3, 45, '跑步机', 30, 0, '2025-04-17 05:54:54', 1, '2025-04-17 05:54:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (86, 3, 45, '哑铃', 25, 0, '2025-04-17 05:54:54', 1, '2025-04-17 05:54:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (87, 3, 45, '瑜伽垫', 20, 0, '2025-04-17 05:54:54', 1, '2025-04-17 05:54:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (88, 3, 45, '健身车', 15, 0, '2025-04-17 05:54:54', 1, '2025-04-17 05:54:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (89, 3, 49, '洁面', 30, 0, '2025-04-17 05:54:54', 1, '2025-04-17 05:54:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (90, 3, 49, '爽肤水', 25, 0, '2025-04-17 05:54:54', 1, '2025-04-17 05:54:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (91, 3, 49, '面霜', 20, 0, '2025-04-17 05:54:54', 1, '2025-04-17 05:54:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (92, 3, 49, '面膜', 15, 0, '2025-04-17 05:54:54', 1, '2025-04-17 05:54:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (93, 3, 57, '婴儿奶粉', 30, 0, '2025-04-17 05:54:54', 1, '2025-04-17 05:54:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (94, 3, 57, '米粉', 25, 0, '2025-04-17 05:54:54', 1, '2025-04-17 05:54:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (95, 3, 57, '果泥', 20, 0, '2025-04-17 05:54:54', 1, '2025-04-17 05:54:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (96, 3, 57, '饼干', 15, 0, '2025-04-17 05:54:54', 1, '2025-04-17 05:54:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (97, 3, 61, '轮胎', 30, 0, '2025-04-17 05:54:54', 1, '2025-04-17 05:54:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (98, 3, 61, '机油', 25, 0, '2025-04-17 05:54:54', 1, '2025-04-17 05:54:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (99, 3, 61, '刹车片', 20, 0, '2025-04-17 05:54:54', 1, '2025-04-17 05:54:54', 1);
INSERT INTO taoduoduo_mall_cloud_goods_db.tb_taoduoduo_mall_category (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user) VALUES (100, 3, 61, '滤清器', 15, 0, '2025-04-17 05:54:54', 1, '2025-04-17 05:54:54', 1);




DROP TABLE IF EXISTS `tb_taoduoduo_mall_tag`;
CREATE TABLE `tb_taoduoduo_mall_tag`
(
    `tag_name` varchar(50) NOT NULL DEFAULT '' COMMENT '标签名称',
    PRIMARY KEY (`tag_name`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC;


# 创建商品表
DROP TABLE IF EXISTS `tb_taoduoduo_mall_goods`;
CREATE TABLE `tb_taoduoduo_mall_goods`
(
    `goods_id`        bigint(20) unsigned NOT NULL COMMENT '商品表主键id',
    `goods_name`      varchar(200)        NOT NULL DEFAULT '' COMMENT '商品名',
    `goods_intro`     varchar(200)        NOT NULL DEFAULT '' COMMENT '商品简介',
    `goods_cover_img` varchar(200)        NOT NULL DEFAULT '/admin/dist/img/no-img.png' COMMENT '商品主图',
    `goods_carousel`  varchar(500)        NOT NULL DEFAULT '/admin/dist/img/no-img.png' COMMENT '商品轮播图',
    `goods_detail`    text                NOT NULL COMMENT '商品详情',
    `original_price`  decimal             NOT NULL DEFAULT '1' COMMENT '商品价格',
    `selling_price`   decimal             NOT NULL DEFAULT '1' COMMENT '商品实际售价',
    `stock_num`       int(11) unsigned    NOT NULL DEFAULT '0' COMMENT '商品库存数量',
    `sales_volume`    int(11) unsigned    NOT NULL DEFAULT '0' COMMENT '商品销量',
    `status`          tinyint             NOT NULL DEFAULT '0' COMMENT '商品上架状态 1-下架 0-上架',
    `create_user`     bigint(11) unsigned NOT NULL DEFAULT '0' COMMENT '添加者主键id',
    `create_time`     datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '商品添加时间',
    `update_user`     bigint(11) unsigned NOT NULL DEFAULT '0' COMMENT '修改者主键id',
    `update_time`     datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '商品修改时间',
    `category_id`     bigint(20) unsigned NOT NULL COMMENT '分类id',
    `shop_id`         bigint(20) unsigned NOT NULL COMMENT '所属商店id',
    INDEX `idx_update_time` (`update_time`) USING BTREE,
    PRIMARY KEY (`goods_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC;


DROP TABLE IF EXISTS `tb_taoduoduo_mall_goods_tag`;
CREATE TABLE `tb_taoduoduo_mall_goods_tag`
(
    `id`       bigint(20) unsigned NOT NULL COMMENT '商品标签表主键id',
    `goods_id` bigint(20) unsigned NOT NULL COMMENT '商品表主键id',
    `tag_name` varchar(50)         NOT NULL DEFAULT '' COMMENT '标签名称',
    INDEX `idx_goods_id` (`goods_id`) USING BTREE,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC;


-- for AT mode you must to init this sql for you business database. the seata server not need it.
CREATE TABLE IF NOT EXISTS `undo_log`
(
    `branch_id`     BIGINT       NOT NULL COMMENT 'branch transaction id',
    `xid`           VARCHAR(128) NOT NULL COMMENT 'global transaction id',
    `context`       VARCHAR(128) NOT NULL COMMENT 'undo_log context,such as serialization',
    `rollback_info` LONGBLOB     NOT NULL COMMENT 'rollback info',
    `log_status`    INT(11)      NOT NULL COMMENT '0:normal status,1:defense status',
    `log_created`   DATETIME(6)  NOT NULL COMMENT 'create datetime',
    `log_modified`  DATETIME(6)  NOT NULL COMMENT 'modify datetime',
    UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='AT transaction mode undo table';