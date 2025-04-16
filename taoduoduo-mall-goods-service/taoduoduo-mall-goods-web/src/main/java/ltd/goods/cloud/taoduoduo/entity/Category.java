package ltd.goods.cloud.taoduoduo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tb_taoduoduo_mall_category")
public class Category {

    @TableId(value = "category_id")
    private Long categoryId;

    @TableField(value = "category_level")
    private Byte categoryLevel;

    @TableId(value = "parent_id")
    private Long parentId;

    @TableField(value = "category_name")
    private String categoryName;

    @TableField(value = "category_rank")
    private Integer categoryRank;

    @TableField(value = "is_deleted")
    private Byte isDeleted;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @TableField(value = "create_user")
    private Integer createUser;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @TableField(value = "update_user")
    private Integer updateUser;

}
