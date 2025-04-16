package ltd.goods.cloud.taoduoduo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_taoduoduo_mall_tag")
public class GoodsTag {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField(value = "goods_id")
    private Long goodsId;

    @TableField(value = "tag_name")
    private String tagName;

    public GoodsTag(Long goodsId, String tagName) {
        this.goodsId = goodsId;
        this.tagName = tagName;
    }
}
