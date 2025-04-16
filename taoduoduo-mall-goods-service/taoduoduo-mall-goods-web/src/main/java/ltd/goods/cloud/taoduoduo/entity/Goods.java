package ltd.goods.cloud.taoduoduo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@TableName("tb_taoduoduo_mall_goods")
public class Goods {

    @TableId(value = "goods_id", type = IdType.ASSIGN_ID)
    private Long goodsId;

    @TableField(value = "goods_name")
    private String goodsName;

    @TableField(value = "goods_intro")
    private String goodsIntro;

    @TableField(value = "goods_cover_image")
    private String goodsCoverImg;

    @TableField(value = "goods_carousel")
    private String goodsCarousel;

    @TableField(value = "goods_detail")
    private String goodsDetail;

    @TableField(value = "original_price")
    private BigDecimal originalPrice;

    @TableField(value = "selling_price")
    private BigDecimal sellingPrice;

    @TableField(value = "stock_num")
    private Integer stockNum;

    @TableField(exist = false)
    private List<String> tags;

    @TableField(value = "status")
    private Boolean status;

    @TableField(value = "create_user")
    private Long createUser;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @TableField(value = "update_user")
    private Long updateUser;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @TableField(value = "sales_volume")
    private Integer salesVolume;

    @TableField(value = "category_id")
    private Long categoryId;


}
