package ltd.goods.cloud.taoduoduo.dto;

import lombok.Data;

import java.util.List;

@Data
public class StockNumUpdateRequest {

    private List<StockNumRequest> stockNumRequests;

}
