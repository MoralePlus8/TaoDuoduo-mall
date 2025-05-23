package ltd.goods.cloud.taoduoduo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@Data
public class GoodsSaveRequest {

    @ApiModelProperty("商品名称")
    @NotEmpty(message = "商品名称不能为空")
    @Length(max = 128,message = "商品名称内容过长")
    private String goodsName;

    @ApiModelProperty("商品简介")
    @NotEmpty(message = "商品简介不能为空")
    @Length(max = 200,message = "商品简介内容过长")
    private String goodsIntro;

    @ApiModelProperty("商品主图")
    @NotEmpty(message = "商品主图不能为空")
    private String goodsCoverImg;

    @ApiModelProperty("商品详情")
    @NotEmpty(message = "商品详情不能为空")
    private String goodsDetail;

    @ApiModelProperty("originalPrice")
    @NotNull(message = "originalPrice不能为空")
    @DecimalMin(value = "0.01", message = "originalPrice最低为0.01")
    @DecimalMax(value = "1000000.00", message = "originalPrice最高为1000000.00")
    private BigDecimal originalPrice;

    @ApiModelProperty("sellingPrice")
    @NotNull(message = "sellingPrice不能为空")
    @DecimalMin(value = "0.01", message = "sellingPrice最低为1")
    @DecimalMax(value = "1000000.00", message = "sellingPrice最高为1000000.00")
    private BigDecimal sellingPrice;

    @ApiModelProperty("库存")
    @NotNull(message = "库存不能为空")
    @Min(value = 1, message = "库存最低为1")
    @Max(value = 100000, message = "库存最高为100000")
    private Integer stockNum;

    @ApiModelProperty("商品标签")
    @NotEmpty(message = "商品标签不能为空")
    private List<String> tags;

    @ApiModelProperty("商品状态")
    @NotNull(message = "商品状态不能为空")
    private Boolean status;

    @ApiModelProperty("分类id")
    @NotNull(message = "分类id不能为空")
    @Min(value = 1, message = "分类id最低为1")
    private Long categoryId;

    @ApiModelProperty("店铺id")
    @NotNull(message = "店铺id不能为空")
    private Long shopId;

}
