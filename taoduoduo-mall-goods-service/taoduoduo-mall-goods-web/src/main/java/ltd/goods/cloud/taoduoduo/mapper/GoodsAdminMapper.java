package ltd.goods.cloud.taoduoduo.mapper;

import ltd.goods.cloud.taoduoduo.entity.Goods;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface GoodsAdminMapper {

    @Select("SELECT * " +
            "FROM tb_newbee_mall_goods_info " +
            "WHERE goods_name = #{goodsName} AND goods_category_id = #{goodsCategoryId}")
    Goods findByNameAndCategoryId(String goodsName, Long goodsCategoryId);

    @Insert("INSERT INTO tb_newbee_mall_goods_info (goods_id, goods_name, goods_intro, goods_cover_img, goods_carousel, original_price, " +
            "selling_price, stock_num, tag, goods_sell_status, create_user, create_time, update_user, update_time, goods_detail_content) " +
            "VALUES (#{goodsId}, #{goodsName}, #{goodsIntro}, #{goodsCoverImg}, #{goodsCarousel}, #{originalPrice}, #{sellingPrice}, " +
            "#{stockNum}, #{tag}, #{goodsSellStatus}, #{createUser}, #{createTime}, #{updateUser}, #{updateTime}, #{goodsDetailContent})")
    int save();
}
