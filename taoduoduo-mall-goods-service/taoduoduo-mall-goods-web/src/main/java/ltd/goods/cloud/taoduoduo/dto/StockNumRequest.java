package ltd.goods.cloud.taoduoduo.dto;

import lombok.Data;


@Data
public class StockNumRequest {

    private Long goodsId;

    private Integer goodsCount;

}