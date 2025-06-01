package ltd.common.cloud.taoduoduo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tb_taoduoduo_mall_address")
public class Address {

    public static class TableAttributes {
        private TableAttributes() {}
        public static final String ADDRESS_ID = "address_id";
        public static final String USER_ID = "user_id";
        public static final String USER_NAME = "user_name";
        public static final String USER_PHONE = "user_phone";
        public static final String DEFAULT_FLAG = "default_flag";
        public static final String PROVINCE = "province";
        public static final String CITY = "city";
        public static final String REGION = "region";
        public static final String DETAIL_ADDRESS = "detail_address";
        public static final String IS_DELETED = "is_deleted";
        public static final String CREATE_TIME = "create_time";
        public static final String UPDATE_TIME = "update_time";
    }

    @TableId(value = TableAttributes.ADDRESS_ID, type = IdType.ASSIGN_ID)
    private Long addressId;

    @TableField(value = TableAttributes.USER_ID)
    private Long userId;

    @TableField(value = TableAttributes.USER_NAME)
    private String userName;

    @TableField(value = TableAttributes.USER_PHONE)
    private String userPhone;

    @TableField(value = TableAttributes.DEFAULT_FLAG)
    private Boolean defaultFlag;

    private String province;

    private String city;

    private String region;

    @TableField(value = TableAttributes.DETAIL_ADDRESS)
    private String detailAddress;

    @TableField(value = TableAttributes.IS_DELETED)
    private Byte isDeleted;

    @TableField(value = TableAttributes.CREATE_TIME, fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = TableAttributes.UPDATE_TIME, fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
