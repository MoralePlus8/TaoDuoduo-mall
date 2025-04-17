package ltd.goods.cloud.taoduoduo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_taoduoduo_mall_goods_tag")
public class GoodsTag {

    public static class TableAttributes {
        private TableAttributes() {}
        public static final String ID = "id";
        public static final String GOODS_ID = "goods_id";
        public static final String TAG_NAME = "tag_name";
    }

    @TableId(value = TableAttributes.ID, type = IdType.ASSIGN_ID)
    private Long id;

    @TableField(value = TableAttributes.GOODS_ID)
    private Long goodsId;

    @TableField(value = TableAttributes.TAG_NAME)
    private String tagName;

    public GoodsTag(Long goodsId, String tagName) {
        this.goodsId = goodsId;
        this.tagName = tagName;
    }
}

