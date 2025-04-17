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

    public static class TableAttributes {
        private TableAttributes(){}
        public static final String CATEGORY_ID = "category_id";
        public static final String CATEGORY_LEVEL = "category_level";
        public static final String PARENT_ID = "parent_id";
        public static final String CATEGORY_NAME = "category_name";
        public static final String CATEGORY_RANK = "category_rank";
        public static final String IS_DELETED = "is_deleted";
        public static final String CREATE_TIME = "create_time";
        public static final String CREATE_USER = "create_user";
        public static final String UPDATE_TIME = "update_time";
        public static final String UPDATE_USER = "update_user";
    }

    @TableId(value = TableAttributes.CATEGORY_ID)
    private Long categoryId;

    @TableField(value = TableAttributes.CATEGORY_LEVEL)
    private Byte categoryLevel;

    @TableField(value = TableAttributes.PARENT_ID)
    private Long parentId;

    @TableField(value = TableAttributes.CATEGORY_NAME)
    private String categoryName;

    @TableField(value = TableAttributes.CATEGORY_RANK)
    private Integer categoryRank;

    @TableField(value = TableAttributes.IS_DELETED)
    private Byte isDeleted;

    @TableField(value = TableAttributes.CREATE_TIME, fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @TableField(value = TableAttributes.CREATE_USER)
    private Integer createUser;

    @TableField(value = TableAttributes.UPDATE_TIME, fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @TableField(value = TableAttributes.UPDATE_USER)
    private Integer updateUser;


}

