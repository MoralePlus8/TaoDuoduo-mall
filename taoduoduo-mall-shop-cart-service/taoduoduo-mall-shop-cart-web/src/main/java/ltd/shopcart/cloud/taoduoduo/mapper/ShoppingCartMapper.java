package ltd.shopcart.cloud.taoduoduo.mapper;

import com.github.pagehelper.Page;
import ltd.shopcart.cloud.taoduoduo.entity.ShoppingCartItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ShoppingCartMapper {

    Page<ShoppingCartItem> pageQuery(Integer pageNumber, Integer pageSize);

    @Select("SELECT * " +
            "FROM tb_newbee_mall_shopping_cart_item " +
            "WHERE goods_id = #{goodsId} AND user_id = #{userId}")
    ShoppingCartItem findByIdAndUserId(Long goodsId, Long userId);
}
