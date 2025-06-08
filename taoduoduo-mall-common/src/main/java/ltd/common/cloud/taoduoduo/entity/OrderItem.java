package ltd.common.cloud.taoduoduo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("tb_taoduoduo_mall_order_item")
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    public static class TableAttributes {
        private TableAttributes() {}
        public static final String ORDER_ITEM_ID = "order_item_id";
        public static final String ORDER_ID = "order_id";
        public static final String GOODS_ID = "goods_id";
        public static final String GOODS_NAME = "goods_name";
        public static final String GOODS_COVER_IMG = "goods_cover_img";
        public static final String SELLING_PRICE = "selling_price";
        public static final String GOODS_COUNT = "goods_count";
        public static final String CREATE_TIME = "create_time";
    }

    @TableId(value = TableAttributes.ORDER_ITEM_ID)
    private Long orderItemId;

    @TableField(value = TableAttributes.ORDER_ID)
    private Long orderId;

    @TableField(value = TableAttributes.GOODS_ID)
    private Long goodsId;

    @TableField(value = TableAttributes.GOODS_NAME)
    private String goodsName;

    @TableField(value = TableAttributes.GOODS_COVER_IMG)
    private String goodsCoverImg;

    @TableField(value = TableAttributes.SELLING_PRICE)
    private BigDecimal sellingPrice;

    @TableField(value = TableAttributes.GOODS_COUNT)
    private Integer goodsCount;

    @TableField(value = TableAttributes.CREATE_TIME, fill = FieldFill.INSERT)
    private Date createTime;

}
