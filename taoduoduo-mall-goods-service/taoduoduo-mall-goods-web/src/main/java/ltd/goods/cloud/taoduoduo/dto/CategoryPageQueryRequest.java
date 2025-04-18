package ltd.goods.cloud.taoduoduo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class CategoryPageQueryRequest {

    @ApiModelProperty("页码")
    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码最低为1")
    private Integer pageNumber;

    @ApiModelProperty("每页条数")
    @NotNull(message = "每页条数不能为空")
    @Min(value = 10, message = "每页条数最低为10")
    private Integer pageSize;

    @ApiModelProperty("分类级别")
    @NotNull(message = "分类级别不能为空")
    @Min(value = 0, message = "分类级别最低为0")
    @Max(value = 3, message = "分类级别最高为3")
    private Integer categoryLevel;

    @ApiModelProperty("上一级分类的id")
    @NotNull(message = "上一级分类的id不能为空")
    @Min(value = 0, message = "上一级分类的id最低为0")
    private Long parentId;

}
