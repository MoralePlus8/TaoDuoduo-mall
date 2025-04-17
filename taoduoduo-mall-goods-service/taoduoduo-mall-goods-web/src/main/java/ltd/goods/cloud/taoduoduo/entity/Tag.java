package ltd.goods.cloud.taoduoduo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_taoduoduo_mall_tag")
public class Tag {

    public static class TableAttributes {
        private TableAttributes() {}
        public static final String TAG_ID = "tag_id";
        public static final String TAG_NAME = "tag_name";
    }

    @TableId(value = TableAttributes.TAG_ID)
    private Long tagId;

    @TableField(value = TableAttributes.TAG_NAME)
    private String tagName;

}

