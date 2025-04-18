package ltd.goods.cloud.taoduoduo.dto;

import lombok.Data;

import java.util.List;

@Data
public class GoodsAddTagRequest {

    private Long goodsId;

    private List<String> tagsName;

}
