package ltd.goods.cloud.taoduoduo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_taoduoduo_mall_tag")
public class Tag {

    @TableId(value = "tag_id")
    private Long tagId;

    @TableField(value = "tag_name")
    private String tagName;

}
