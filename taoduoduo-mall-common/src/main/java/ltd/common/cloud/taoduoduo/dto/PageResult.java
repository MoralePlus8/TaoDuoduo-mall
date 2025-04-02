package ltd.common.cloud.taoduoduo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageResult<T> implements Serializable {

    @ApiModelProperty("总记录数")
    private long totalCount;

    @ApiModelProperty("每页记录数")
    private int pageSize;

    @ApiModelProperty("总页数")
    private long totalPage;

    @ApiModelProperty("当前页数")
    private int currPage;

    @ApiModelProperty("列表数据")
    private List<T> list;

    public PageResult(List<T> list, long totalCount, int pageSize, int currPage) {
        this.list = list;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.currPage = currPage;
        this.totalPage = (long) Math.ceil((double) totalCount / pageSize);
    }

}
