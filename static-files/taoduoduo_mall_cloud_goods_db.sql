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

-- 为每个三级分类生成4条商品数据
INSERT INTO `tb_taoduoduo_mall_goods`
(`goods_id`, `goods_name`, `goods_intro`, `goods_cover_img`, `goods_carousel`, `goods_detail`, `original_price`, `selling_price`, `stock_num`, `sales_volume`, `status`, `create_user`, `update_user`, `category_id`, `shop_id`)
VALUES
-- 分类ID:3 (测式3级分类)
(100001, '测试商品A001', '测试分类商品示例A', '/images/test/a001.jpg', '/images/test/a001_1.jpg', '测试商品详情描述...', 100.00, 88.00, 100, 50, 0, 1, 1, 3, 1),
(100002, '测试商品A002', '测试分类商品示例B', '/images/test/a002.jpg', '/images/test/a002_1.jpg', '测试商品详情描述...', 120.00, 99.00, 80, 40, 0, 1, 1, 3, 1),
(100003, '测试商品A003', '测试分类商品示例C', '/images/test/a003.jpg', '/images/test/a003_1.jpg', '测试商品详情描述...', 150.00, 129.00, 60, 30, 0, 1, 1, 3, 1),
(100004, '测试商品A004', '测试分类商品示例D', '/images/test/a004.jpg', '/images/test/a004_1.jpg', '测试商品详情描述...', 200.00, 179.00, 40, 20, 0, 1, 1, 3, 1),

-- 分类ID:30 (智能手机)
(100005, '旗舰智能手机X10 Pro', '6.7英寸AMOLED屏幕，旗舰处理器', '/images/phone/x10pro.jpg', '/images/phone/x10pro_1.jpg,/images/phone/x10pro_2.jpg', '旗舰智能手机详情...', 6999.00, 6499.00, 300, 850, 0, 1, 1, 30, 101),
(100006, '中端智能手机A50', '6.5英寸LCD屏幕，均衡性能', '/images/phone/a50.jpg', '/images/phone/a50_1.jpg,/images/phone/a50_2.jpg', '中端智能手机详情...', 2999.00, 2699.00, 500, 1200, 0, 1, 1, 30, 101),
(100007, '入门智能手机C20', '6.2英寸HD屏幕，长续航', '/images/phone/c20.jpg', '/images/phone/c20_1.jpg,/images/phone/c20_2.jpg', '入门智能手机详情...', 1299.00, 1099.00, 800, 2000, 0, 1, 1, 30, 101),
(100008, '折叠屏智能手机Z Fold', '7.6英寸可折叠AMOLED屏幕', '/images/phone/zfold.jpg', '/images/phone/zfold_1.jpg,/images/phone/zfold_2.jpg', '折叠屏手机详情...', 12999.00, 11999.00, 100, 150, 0, 1, 1, 30, 101),

-- 分类ID:31 (老人手机)
(100009, '大字版老人手机L1', '大按键、大字体、大音量', '/images/elder/l1.jpg', '/images/elder/l1_1.jpg,/images/elder/l1_2.jpg', '老人手机详情...', 499.00, 399.00, 600, 800, 0, 1, 1, 31, 101),
(100010, '超长待机老人手机P9', '5000mAh电池，30天待机', '/images/elder/p9.jpg', '/images/elder/p9_1.jpg,/images/elder/p9_2.jpg', '超长待机手机详情...', 599.00, 499.00, 400, 600, 0, 1, 1, 31, 101),
(100011, '4G老人手机K6', '支持4G网络，简单易用', '/images/elder/k6.jpg', '/images/elder/k6_1.jpg,/images/elder/k6_2.jpg', '4G老人手机详情...', 699.00, 599.00, 300, 400, 0, 1, 1, 31, 101),
(100012, '三防老人手机S3', '防水防摔防尘，坚固耐用', '/images/elder/s3.jpg', '/images/elder/s3_1.jpg,/images/elder/s3_2.jpg', '三防老人手机详情...', 799.00, 699.00, 200, 300, 0, 1, 1, 31, 101),

-- 分类ID:32 (对讲机)
(100013, '商用对讲机T200', '5公里通话距离，16频道', '/images/walkie/t200.jpg', '/images/walkie/t200_1.jpg,/images/walkie/t200_2.jpg', '商用对讲机详情...', 499.00, 399.00, 200, 150, 0, 1, 1, 32, 101),
(100014, '迷你对讲机M1', '轻巧便携，3公里通话', '/images/walkie/m1.jpg', '/images/walkie/m1_1.jpg,/images/walkie/m1_2.jpg', '迷你对讲机详情...', 299.00, 249.00, 300, 200, 0, 1, 1, 32, 101),
(100015, '专业对讲机P500', '10公里通话，防水防尘', '/images/walkie/p500.jpg', '/images/walkie/p500_1.jpg,/images/walkie/p500_2.jpg', '专业对讲机详情...', 899.00, 799.00, 100, 80, 0, 1, 1, 32, 101),
(100016, '酒店用对讲机H100', '简洁设计，适合酒店使用', '/images/walkie/h100.jpg', '/images/walkie/h100_1.jpg,/images/walkie/h100_2.jpg', '酒店对讲机详情...', 399.00, 349.00, 150, 120, 0, 1, 1, 32, 101),

-- 分类ID:33 (笔记本)
(100017, '轻薄笔记本Air 2023', '13.3英寸2K屏，超薄设计', '/images/laptop/air2023.jpg', '/images/laptop/air2023_1.jpg,/images/laptop/air2023_2.jpg', '轻薄笔记本详情...', 5999.00, 5499.00, 150, 420, 0, 1, 1, 33, 101),
(100018, '游戏笔记本Pro Gamer', '15.6英寸144Hz屏，RTX显卡', '/images/laptop/gamer.jpg', '/images/laptop/gamer_1.jpg,/images/laptop/gamer_2.jpg', '游戏笔记本详情...', 8999.00, 8299.00, 80, 200, 0, 1, 1, 33, 101),
(100019, '商务笔记本Elite', '14英寸防眩光屏，指纹识别', '/images/laptop/elite.jpg', '/images/laptop/elite_1.jpg,/images/laptop/elite_2.jpg', '商务笔记本详情...', 6999.00, 6499.00, 120, 300, 0, 1, 1, 33, 101),
(100020, '二合一笔记本Flex', '触控屏，360度翻转', '/images/laptop/flex.jpg', '/images/laptop/flex_1.jpg,/images/laptop/flex_2.jpg', '二合一笔记本详情...', 4999.00, 4599.00, 100, 250, 0, 1, 1, 33, 101),

-- 分类ID:34 (台式机)
(100021, '高性能台式机Tower X', 'i7处理器，16GB内存，1TB SSD', '/images/desktop/towerx.jpg', '/images/desktop/towerx_1.jpg,/images/desktop/towerx_2.jpg', '高性能台式机详情...', 6999.00, 6499.00, 50, 120, 0, 1, 1, 34, 101),
(100022, '迷你台式机Mini PC', '小巧机身，办公利器', '/images/desktop/mini.jpg', '/images/desktop/mini_1.jpg,/images/desktop/mini_2.jpg', '迷你台式机详情...', 2999.00, 2799.00, 80, 200, 0, 1, 1, 34, 101),
(100023, '游戏台式机Gamer Pro', 'RTX显卡，水冷散热', '/images/desktop/gamer.jpg', '/images/desktop/gamer_1.jpg,/images/desktop/gamer_2.jpg', '游戏台式机详情...', 9999.00, 9299.00, 30, 80, 0, 1, 1, 34, 101),
(100024, '办公台式机Office', '经济实惠，稳定耐用', '/images/desktop/office.jpg', '/images/desktop/office_1.jpg,/images/desktop/office_2.jpg', '办公台式机详情...', 3999.00, 3699.00, 100, 250, 0, 1, 1, 34, 101),

-- 分类ID:35 (平板电脑)
(100025, '旗舰平板Pro 12.9', '12.9英寸Liquid视网膜屏', '/images/tablet/pro12.jpg', '/images/tablet/pro12_1.jpg,/images/tablet/pro12_2.jpg', '旗舰平板详情...', 7999.00, 7499.00, 60, 150, 0, 1, 1, 35, 101),
(100026, '中端平板Air 10.5', '10.5英寸全面屏', '/images/tablet/air10.jpg', '/images/tablet/air10_1.jpg,/images/tablet/air10_2.jpg', '中端平板详情...', 3999.00, 3699.00, 120, 300, 0, 1, 1, 35, 101),
(100027, '入门平板Mini 8', '8英寸便携设计', '/images/tablet/mini8.jpg', '/images/tablet/mini8_1.jpg,/images/tablet/mini8_2.jpg', '入门平板详情...', 1999.00, 1799.00, 200, 500, 0, 1, 1, 35, 101),
(100028, '儿童教育平板Kids', '防蓝光，家长控制', '/images/tablet/kids.jpg', '/images/tablet/kids_1.jpg,/images/tablet/kids_2.jpg', '儿童平板详情...', 1299.00, 1099.00, 150, 400, 0, 1, 1, 35, 101),

-- 分类ID:36 (电视机)
(100029, '4K超高清电视65英寸', 'HDR10+，杜比视界', '/images/tv/uhd65.jpg', '/images/tv/uhd65_1.jpg,/images/tv/uhd65_2.jpg', '4K电视详情...', 7999.00, 6999.00, 80, 180, 0, 1, 1, 36, 102),
(100030, 'QLED量子点电视55英寸', '广色域，120Hz刷新率', '/images/tv/qled55.jpg', '/images/tv/qled55_1.jpg,/images/tv/qled55_2.jpg', 'QLED电视详情...', 5999.00, 5499.00, 100, 220, 0, 1, 1, 36, 102),
(100031, '智能电视43英寸', '安卓系统，语音控制', '/images/tv/smart43.jpg', '/images/tv/smart43_1.jpg,/images/tv/smart43_2.jpg', '智能电视详情...', 2999.00, 2699.00, 150, 350, 0, 1, 1, 36, 102),
(100032, 'OLED电视48英寸', '自发光像素，极致对比度', '/images/tv/oled48.jpg', '/images/tv/oled48_1.jpg,/images/tv/oled48_2.jpg', 'OLED电视详情...', 8999.00, 8299.00, 40, 100, 0, 1, 1, 36, 102),

-- 分类ID:37 (冰箱)
(100033, '对开门变频冰箱520L', '风冷无霜，一级能效', '/images/fridge/520l.jpg', '/images/fridge/520l_1.jpg,/images/fridge/520l_2.jpg', '对开门冰箱详情...', 5999.00, 5299.00, 60, 130, 0, 1, 1, 37, 102),
(100034, '三门冰箱280L', '中门变温室，节能静音', '/images/fridge/280l.jpg', '/images/fridge/280l_1.jpg,/images/fridge/280l_2.jpg', '三门冰箱详情...', 3999.00, 3599.00, 80, 180, 0, 1, 1, 37, 102),
(100035, '迷你冰箱120L', '适合宿舍/办公室', '/images/fridge/120l.jpg', '/images/fridge/120l_1.jpg,/images/fridge/120l_2.jpg', '迷你冰箱详情...', 1299.00, 1099.00, 120, 250, 0, 1, 1, 37, 102),
(100036, '法式多门冰箱450L', '精细分储，智能控温', '/images/fridge/450l.jpg', '/images/fridge/450l_1.jpg,/images/fridge/450l_2.jpg', '法式冰箱详情...', 6999.00, 6299.00, 50, 100, 0, 1, 1, 37, 102),

-- 分类ID:38 (洗衣机)
(100037, '滚筒洗衣机10kg', '变频电机，除菌洗', '/images/washer/10kg.jpg', '/images/washer/10kg_1.jpg,/images/washer/10kg_2.jpg', '滚筒洗衣机详情...', 3999.00, 3599.00, 70, 160, 0, 1, 1, 38, 102),
(100038, '波轮洗衣机8kg', '强力去污，节水设计', '/images/washer/8kg.jpg', '/images/washer/8kg_1.jpg,/images/washer/8kg_2.jpg', '波轮洗衣机详情...', 1999.00, 1799.00, 100, 220, 0, 1, 1, 38, 102),
(100039, '迷你洗衣机3kg', '内衣/婴儿衣物专用', '/images/washer/3kg.jpg', '/images/washer/3kg_1.jpg,/images/washer/3kg_2.jpg', '迷你洗衣机详情...', 999.00, 899.00, 120, 250, 0, 1, 1, 38, 102),
(100040, '洗烘一体机9kg', '洗涤烘干一次完成', '/images/washer/dryer9.jpg', '/images/washer/dryer9_1.jpg,/images/washer/dryer9_2.jpg', '洗烘一体机详情...', 4999.00, 4599.00, 60, 130, 0, 1, 1, 38, 102),

-- 分类ID:39 (T恤)
(100041, '纯棉男士短袖T恤', '100%纯棉，多种颜色', '/images/tshirt/men1.jpg', '/images/tshirt/men1_1.jpg,/images/tshirt/men1_2.jpg', '纯棉T恤详情...', 199.00, 129.00, 500, 1200, 0, 1, 1, 39, 103),
(100042, '潮流印花T恤', '时尚印花，宽松版型', '/images/tshirt/print1.jpg', '/images/tshirt/print1_1.jpg,/images/tshirt/print1_2.jpg', '印花T恤详情...', 159.00, 119.00, 400, 1000, 0, 1, 1, 39, 103),
(100043, '速干运动T恤', '透气排汗，运动必备', '/images/tshirt/sport1.jpg', '/images/tshirt/sport1_1.jpg,/images/tshirt/sport1_2.jpg', '运动T恤详情...', 179.00, 139.00, 350, 900, 0, 1, 1, 39, 103),
(100044, '情侣款T恤套装', '男女同款，多色可选', '/images/tshirt/couple1.jpg', '/images/tshirt/couple1_1.jpg,/images/tshirt/couple1_2.jpg', '情侣T恤详情...', 299.00, 239.00, 300, 800, 0, 1, 1, 39, 103),

-- 分类ID:40 (衬衫)
(100045, '商务男士衬衫', '免烫抗皱，经典款式', '/images/shirt/men1.jpg', '/images/shirt/men1_1.jpg,/images/shirt/men1_2.jpg', '商务衬衫详情...', 299.00, 239.00, 400, 1000, 0, 1, 1, 40, 103),
(100046, '休闲格子衬衫', '棉质面料，舒适透气', '/images/shirt/check1.jpg', '/images/shirt/check1_1.jpg,/images/shirt/check1_2.jpg', '格子衬衫详情...', 259.00, 199.00, 350, 900, 0, 1, 1, 40, 103),
(100047, '女士雪纺衬衫', '垂感顺滑，优雅大方', '/images/shirt/women1.jpg', '/images/shirt/women1_1.jpg,/images/shirt/women1_2.jpg', '女士衬衫详情...', 279.00, 219.00, 300, 800, 0, 1, 1, 40, 103),
(100048, '牛仔衬衫', '耐磨耐穿，百搭款式', '/images/shirt/denim1.jpg', '/images/shirt/denim1_1.jpg,/images/shirt/denim1_2.jpg', '牛仔衬衫详情...', 329.00, 269.00, 250, 700, 0, 1, 1, 40, 103),

-- 分类ID:41 (牛仔裤)
(100049, '男士直筒牛仔裤', '经典蓝色，舒适版型', '/images/jeans/men1.jpg', '/images/jeans/men1_1.jpg,/images/jeans/men1_2.jpg', '男士牛仔裤详情...', 399.00, 329.00, 400, 1000, 0, 1, 1, 41, 103),
(100050, '女士修身牛仔裤', '弹力面料，显瘦设计', '/images/jeans/women1.jpg', '/images/jeans/women1_1.jpg,/images/jeans/women1_2.jpg', '女士牛仔裤详情...', 359.00, 299.00, 350, 900, 0, 1, 1, 41, 103),
(100051, '破洞牛仔裤', '潮流破洞，街头风格', '/images/jeans/ripped1.jpg', '/images/jeans/ripped1_1.jpg,/images/jeans/ripped1_2.jpg', '破洞牛仔裤详情...', 429.00, 369.00, 300, 800, 0, 1, 1, 41, 103),
(100052, '黑色小脚牛仔裤', '百搭黑色，显腿长', '/images/jeans/skinny1.jpg', '/images/jeans/skinny1_1.jpg,/images/jeans/skinny1_2.jpg', '小脚牛仔裤详情...', 379.00, 319.00, 250, 700, 0, 1, 1, 41, 103),

-- 分类ID:42 (坚果炒货)
(100053, '混合坚果礼盒750g', '5种坚果混合，无添加', '/images/nut/mix1.jpg', '/images/nut/mix1_1.jpg,/images/nut/mix1_2.jpg', '混合坚果详情...', 158.00, 128.00, 400, 1500, 0, 1, 1, 42, 104),
(100054, '开心果500g', '自然开口，颗粒饱满', '/images/nut/pistachio1.jpg', '/images/nut/pistachio1_1.jpg,/images/nut/pistachio1_2.jpg', '开心果详情...', 89.00, 69.00, 500, 1200, 0, 1, 1, 42, 104),
(100055, '碧根果250g', '奶油味，易剥壳', '/images/nut/pecan1.jpg', '/images/nut/pecan1_1.jpg,/images/nut/pecan1_2.jpg', '碧根果详情...', 59.00, 49.00, 600, 1800, 0, 1, 1, 42, 104),
(100056, '腰果300g', '原味烘焙，无盐', '/images/nut/cashew1.jpg', '/images/nut/cashew1_1.jpg,/images/nut/cashew1_2.jpg', '腰果详情...', 79.00, 59.00, 450, 1300, 0, 1, 1, 42, 104),

-- 分类ID:43 (膨化食品)
(100057, '薯片大礼包500g', '多种口味组合', '/images/snack/chips1.jpg', '/images/snack/chips1_1.jpg,/images/snack/chips1_2.jpg', '薯片礼包详情...', 49.00, 39.00, 800, 2000, 0, 1, 1, 43, 104),
(100058, '虾条200g', '鲜虾风味，酥脆可口', '/images/snack/shrimp1.jpg', '/images/snack/shrimp1_1.jpg,/images/snack/shrimp1_2.jpg', '虾条详情...', 19.00, 15.00, 1000, 2500, 0, 1, 1, 43, 104),
(100059, '玉米脆片300g', '非油炸，健康零食', '/images/snack/corn1.jpg', '/images/snack/corn1_1.jpg,/images/snack/corn1_2.jpg', '玉米片详情...', 29.00, 24.00, 700, 1800, 0, 1, 1, 43, 104),
(100060, '爆米花桶装200g', '电影院同款，奶油味', '/images/snack/popcorn1.jpg', '/images/snack/popcorn1_1.jpg,/images/snack/popcorn1_2.jpg', '爆米花详情...', 39.00, 29.00, 600, 1500, 0, 1, 1, 43, 104),

-- 分类ID:44 (糖果巧克力)
(100061, '牛奶巧克力礼盒', '进口可可，丝滑口感', '/images/candy/choco1.jpg', '/images/candy/choco1_1.jpg,/images/candy/choco1_2.jpg', '巧克力详情...', 129.00, 99.00, 300, 800, 0, 1, 1, 44, 104),
(100062, '水果硬糖混合装', '多种水果口味', '/images/candy/hard1.jpg', '/images/candy/hard1_1.jpg,/images/candy/hard1_2.jpg', '水果糖详情...', 49.00, 39.00, 500, 1200, 0, 1, 1, 44, 104),
(100063, '夹心软糖200g', '果汁夹心，Q弹口感', '/images/candy/soft1.jpg', '/images/candy/soft1_1.jpg,/images/candy/soft1_2.jpg', '夹心糖详情...', 29.00, 24.00, 600, 1500, 0, 1, 1, 44, 104),
(100064, '薄荷糖盒装100g', '清新口气，便携设计', '/images/candy/mint1.jpg', '/images/candy/mint1_1.jpg,/images/candy/mint1_2.jpg', '薄荷糖详情...', 19.00, 15.00, 800, 2000, 0, 1, 1, 44, 104),

-- 分类ID:65 (手机壳/保护套)
(100065, '防摔手机壳X10', '军工级防摔，轻薄设计', '/images/case/protect1.jpg', '/images/case/protect1_1.jpg,/images/case/protect1_2.jpg', '防摔手机壳详情...', 99.00, 69.00, 500, 1200, 0, 1, 1, 65, 105),
(100066, '透明硅胶手机壳', '超薄透明，原机美感', '/images/case/clear1.jpg', '/images/case/clear1_1.jpg,/images/case/clear1_2.jpg', '透明手机壳详情...', 49.00, 39.00, 800, 2000, 0, 1, 1, 65, 105),
(100067, '个性图案手机壳', '创意设计，多种图案', '/images/case/design1.jpg', '/images/case/design1_1.jpg,/images/case/design1_2.jpg', '图案手机壳详情...', 79.00, 59.00, 400, 1000, 0, 1, 1, 65, 105),
(100068, '磁吸支架手机壳', '内置磁环，支持车载', '/images/case/magnetic1.jpg', '/images/case/magnetic1_1.jpg,/images/case/magnetic1_2.jpg', '磁吸手机壳详情...', 129.00, 99.00, 300, 800, 0, 1, 1, 65, 105),

-- 分类ID:66 (充电器/数据线)
(100069, '快充充电器套装', '30W快充，安全认证', '/images/charger/fast1.jpg', '/images/charger/fast1_1.jpg,/images/charger/fast1_2.jpg', '快充套装详情...', 99.00, 79.00, 600, 1500, 0, 1, 1, 66, 105),
(100070, '编织数据线1.2m', '耐用编织，支持快充', '/images/charger/cable1.jpg', '/images/charger/cable1_1.jpg,/images/charger/cable1_2.jpg', '数据线详情...', 49.00, 39.00, 800, 2000, 0, 1, 1, 66, 105),
(100071, '车载充电器双口', '双USB输出，智能识别', '/images/charger/car1.jpg', '/images/charger/car1_1.jpg,/images/charger/car1_2.jpg', '车载充电器详情...', 59.00, 49.00, 500, 1200, 0, 1, 1, 66, 105),
(100072, '磁吸充电线套装', '360度旋转，防断裂', '/images/charger/magnetic1.jpg', '/images/charger/magnetic1_1.jpg,/images/charger/magnetic1_2.jpg', '磁吸线详情...', 79.00, 59.00, 400, 1000, 0, 1, 1, 66, 105),

-- 分类ID:67 (移动电源)
(100073, '10000mAh快充移动电源', '双向快充，轻薄设计', '/images/powerbank/10000.jpg', '/images/powerbank/10000_1.jpg,/images/powerbank/10000_2.jpg', '移动电源详情...', 199.00, 159.00, 400, 1000, 0, 1, 1, 67, 105),
(100074, '20000mAh大容量移动电源', '双输出，可上飞机', '/images/powerbank/20000.jpg', '/images/powerbank/20000_1.jpg,/images/powerbank/20000_2.jpg', '大容量电源详情...', 299.00, 249.00, 300, 800, 0, 1, 1, 67, 105),
(100075, '迷你5000mAh移动电源', '口红大小，便携设计', '/images/powerbank/5000.jpg', '/images/powerbank/5000_1.jpg,/images/powerbank/5000_2.jpg', '迷你电源详情...', 129.00, 99.00, 500, 1200, 0, 1, 1, 67, 105),
(100076, '无线充电移动电源', '支持10W无线快充', '/images/powerbank/wireless1.jpg', '/images/powerbank/wireless1_1.jpg,/images/powerbank/wireless1_2.jpg', '无线电源详情...', 259.00, 219.00, 350, 900, 0, 1, 1, 67, 105),

-- 分类ID:68 (耳机/耳麦)
(100077, '无线蓝牙耳机', '真无线，降噪功能', '/images/earphone/bt1.jpg', '/images/earphone/bt1_1.jpg,/images/earphone/bt1_2.jpg', '蓝牙耳机详情...', 399.00, 329.00, 400, 1000, 0, 1, 1, 68, 105),
(100078, '头戴式游戏耳麦', '7.1声道，麦克风', '/images/earphone/game1.jpg', '/images/earphone/game1_1.jpg,/images/earphone/game1_2.jpg', '游戏耳麦详情...', 499.00, 429.00, 300, 800, 0, 1, 1, 68, 105),
(100079, '运动蓝牙耳机', '防汗防水，耳挂设计', '/images/earphone/sport1.jpg', '/images/earphone/sport1_1.jpg,/images/earphone/sport1_2.jpg', '运动耳机详情...', 299.00, 249.00, 350, 900, 0, 1, 1, 68, 105),
(100080, '有线入耳式耳机', '高保真音质，麦克风', '/images/earphone/wired1.jpg', '/images/earphone/wired1_1.jpg,/images/earphone/wired1_2.jpg', '有线耳机详情...', 199.00, 159.00, 500, 1200, 0, 1, 1, 68, 105),

-- 分类ID:69 (智能手表)
(100081, '运动智能手表', '心率监测，50米防水', '/images/watch/sport1.jpg', '/images/watch/sport1_1.jpg,/images/watch/sport1_2.jpg', '运动手表详情...', 799.00, 699.00, 200, 500, 0, 1, 1, 69, 106),
(100082, '儿童电话手表', 'GPS定位，视频通话', '/images/watch/kids1.jpg', '/images/watch/kids1_1.jpg,/images/watch/kids1_2.jpg', '儿童手表详情...', 599.00, 499.00, 150, 400, 0, 1, 1, 69, 106),
(100083, '商务智能手表', 'eSIM独立通话，支付', '/images/watch/business1.jpg', '/images/watch/business1_1.jpg,/images/watch/business1_2.jpg', '商务手表详情...', 1299.00, 1199.00, 100, 300, 0, 1, 1, 69, 106),
(100084, '健康监测手环', '血氧检测，睡眠分析', '/images/watch/band1.jpg', '/images/watch/band1_1.jpg,/images/watch/band1_2.jpg', '健康手环详情...', 299.00, 249.00, 300, 800, 0, 1, 1, 69, 106),

-- 分类ID:70 (智能家居)
(100085, '智能音箱', '语音助手，家居控制', '/images/smarthome/speaker1.jpg', '/images/smarthome/speaker1_1.jpg,/images/smarthome/speaker1_2.jpg', '智能音箱详情...', 399.00, 349.00, 300, 800, 0, 1, 1, 70, 106),
(100086, '智能灯泡', 'RGB调色，语音控制', '/images/smarthome/bulb1.jpg', '/images/smarthome/bulb1_1.jpg,/images/smarthome/bulb1_2.jpg', '智能灯泡详情...', 129.00, 99.00, 500, 1200, 0, 1, 1, 70, 106),
(100087, '智能插座', '远程控制，定时开关', '/images/smarthome/plug1.jpg', '/images/smarthome/plug1_1.jpg,/images/smarthome/plug1_2.jpg', '智能插座详情...', 79.00, 59.00, 600, 1500, 0, 1, 1, 70, 106),
(100088, '智能门锁', '指纹/密码/钥匙开锁', '/images/smarthome/lock1.jpg', '/images/smarthome/lock1_1.jpg,/images/smarthome/lock1_2.jpg', '智能门锁详情...', 1299.00, 1199.00, 100, 300, 0, 1, 1, 70, 106);





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