package ltd.goods.cloud.taoduoduo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Goods {

    private Long goodsId;

    private String goodsName;

    private String goodsIntro;

    private Long goodsCategoryId;

    private String goodsCoverImg;

    private String goodsCarousel;

    private BigDecimal originalPrice;

    private BigDecimal sellingPrice;

    private Integer stockNum;

    private String tag;

    private Byte goodsSellStatus;

    private String goodsDetailContent;

    private Integer createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private Integer updateUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}
