package ltd.common.cloud.taoduoduo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@TableName("tb_taoduoduo_mall_goods")
public class Goods {

    public static class TableAttributes {
        private TableAttributes() {}
        public static final String GOODS_ID = "goods_id";
        public static final String GOODS_NAME = "goods_name";
        public static final String GOODS_INTRO = "goods_intro";
        public static final String GOODS_COVER_IMAGE = "goods_cover_img";
        public static final String GOODS_CAROUSEL = "goods_carousel";
        public static final String GOODS_DETAIL = "goods_detail";
        public static final String ORIGINAL_PRICE = "original_price";
        public static final String SELLING_PRICE = "selling_price";
        public static final String STOCK_NUM = "stock_num";
        public static final String STATUS = "status";
        public static final String CREATE_USER = "create_user";
        public static final String CREATE_TIME = "create_time";
        public static final String UPDATE_USER = "update_user";
        public static final String UPDATE_TIME = "update_time";
        public static final String SALES_VOLUME = "sales_volume";
        public static final String CATEGORY_ID = "category_id";
        public static final String SHOP_ID = "shop_id";
    }

    @TableId(value = TableAttributes.GOODS_ID, type = IdType.ASSIGN_ID)
    private Long goodsId;

    @TableField(value = TableAttributes.GOODS_NAME)
    private String goodsName;

    @TableField(value = TableAttributes.GOODS_INTRO)
    private String goodsIntro;

    @TableField(value = TableAttributes.GOODS_COVER_IMAGE)
    private String goodsCoverImg;

    @TableField(value = TableAttributes.GOODS_CAROUSEL)
    private String goodsCarousel;

    @TableField(value = TableAttributes.GOODS_DETAIL)
    private String goodsDetail;

    @TableField(value = TableAttributes.ORIGINAL_PRICE)
    private BigDecimal originalPrice;

    @TableField(value = TableAttributes.SELLING_PRICE)
    private BigDecimal sellingPrice;

    @TableField(value = TableAttributes.STOCK_NUM)
    private Integer stockNum;

    @TableField(exist = false)
    private List<String> tags;

    @TableField(value = TableAttributes.STATUS)
    private Boolean status;

    @TableField(value = TableAttributes.CATEGORY_ID)
    private Long categoryId;

    @TableField(value = TableAttributes.CREATE_USER)
    private Long createUser;

    @TableField(value = TableAttributes.CREATE_TIME, fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @TableField(value = TableAttributes.UPDATE_USER)
    private Long updateUser;

    @TableField(value = TableAttributes.UPDATE_TIME, fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @TableField(value = TableAttributes.SALES_VOLUME)
    private Integer salesVolume;

    @TableField(value = TableAttributes.SHOP_ID)
    private Long shopId;

}
