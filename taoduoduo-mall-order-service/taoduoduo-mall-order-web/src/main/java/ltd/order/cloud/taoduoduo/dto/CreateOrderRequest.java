package ltd.order.cloud.taoduoduo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest {
    @ApiModelProperty("购物车项id数组")
    private List<Long> cartItemIds;

    @ApiModelProperty("地址id")
    private Long addressId;
}
