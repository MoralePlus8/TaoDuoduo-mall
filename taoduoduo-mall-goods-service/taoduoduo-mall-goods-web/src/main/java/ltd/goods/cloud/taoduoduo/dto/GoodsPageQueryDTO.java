package ltd.goods.cloud.taoduoduo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("上架状态")
    @Min(value = 0)
    @Max(value = 1)
    private Integer goodsSellStatus;
}
