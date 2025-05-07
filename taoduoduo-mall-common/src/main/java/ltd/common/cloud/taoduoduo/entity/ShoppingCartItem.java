package ltd.common.cloud.taoduoduo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tb_taoduoduo_mall_shopping_cart_item")
public class ShoppingCartItem {

    public static class TableAttributes {
        private TableAttributes() {}
        public static final String CART_ITEM_ID = "cart_item_id";
        public static final String USER_ID = "user_id";
        public static final String GOODS_ID = "goods_id";
        public static final String GOODS_COUNT = "goods_count";
        public static final String IS_DELETED = "is_deleted";
        public static final String CREATE_TIME = "create_time";
        public static final String UPDATE_TIME = "update_time";
    }

    @TableId(value = TableAttributes.CART_ITEM_ID, type = IdType.ASSIGN_ID)
    private Long cartItemId;

    @TableField(value = TableAttributes.USER_ID)
    private Long userId;

    @TableField(value = TableAttributes.GOODS_ID)
    private Long goodsId;

    @TableField(value = TableAttributes.GOODS_COUNT)
    private Integer goodsCount;

    @TableField(value = TableAttributes.IS_DELETED)
    private Boolean isDeleted;

    @TableField(value = TableAttributes.CREATE_TIME, fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = TableAttributes.UPDATE_TIME, fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
