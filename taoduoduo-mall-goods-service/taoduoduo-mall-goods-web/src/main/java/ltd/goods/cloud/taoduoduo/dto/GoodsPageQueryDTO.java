package ltd.goods.cloud.taoduoduo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class GoodsPageQueryDTO {

    @ApiModelProperty("页码")
    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码最低为1")
    private Integer pageNumber;

    @ApiModelProperty("每页条数")
    @NotNull(message = "每页条数不能为空")
    @Min(value = 10, message = "每页条数最低为10")
    private Integer pageSize;

    @ApiModelProperty("搜索词")
    @NotNull(message = "搜索词不能为空")
    String keyword;

    @ApiModelProperty("分类ID")
    List<Integer> categoryIds;

    @ApiModelProperty("最低价格")
    Double minPrice;

    @ApiModelProperty("最高价格")
    Double maxPrice;

    @ApiModelProperty("排序类型")
    String sortType;

    @ApiModelProperty("排序方式")
    String priceOrder;

    @ApiModelProperty("商品标签")
    List<String> tags;

    @ApiModelProperty("商品状态")
    Boolean status;

}
