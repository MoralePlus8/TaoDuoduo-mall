package ltd.goods.cloud.taoduoduo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ThirdLevelCategoryVO {

    @ApiModelProperty("当前三级分类id")
    private Long categoryId;

    @ApiModelProperty("父级分类id")
    private Long parentId;

    @ApiModelProperty("当前分类级别")
    private Byte categoryLevel;

    @ApiModelProperty("当前三级分类名称")
    private String categoryName;

}
