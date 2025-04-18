package ltd.goods.cloud.taoduoduo.dto;

import lombok.Data;

import java.util.List;

@Data
public class UpdateStockNumDTO {

    private List<StockNumDTO> stockNumRequests;

}
