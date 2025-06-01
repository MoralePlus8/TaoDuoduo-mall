package ltd.common.cloud.taoduoduo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@TableName("tb_taoduoduo_mall_order")
public class Order {

    public static class TableAttributes {
        private TableAttributes() {}
        public static final String ORDER_ID = "order_id";
        public static final String ORDER_NO = "order_no";
        public static final String USER_ID = "user_id";
        public static final String ADDRESS_ID = "address_id";
        public static final String TOTAL_PRICE = "total_price";
        public static final String PAY_STATUS = "pay_status";
        public static final String PAY_TYPE = "pay_type";
        public static final String PAY_TIME = "pay_time";
        public static final String ORDER_STATUS = "order_status";
        public static final String EXTRA_INFO = "extra_info";
        public static final String IS_DELETED = "is_deleted";
        public static final String CREATE_TIME = "create_time";
        public static final String UPDATE_TIME = "update_time";
    }

    public static class OrderStatus {
        public static final Byte FAILED = -9;
        public static final Byte ORDER_PRE_PAY = 0;
        public static final Byte ORDER_PAID = 1;
        public static final Byte ORDER_PACKAGED = 2;
        public static final Byte ORDER_EXPRESS = 3;
        public static final Byte ORDER_SUCCESS = 4;
        public static final Byte ORDER_CLOSED_BY_MALLUSER = -1;
        public static final Byte ORDER_CLOSED_BY_EXPIRED = -2;
        public static final Byte ORDER_CLOSED_BY_JUDGE = -3;
    }

    public static class PayStatus {
        public static final Byte FAILED = -1;
        public static final Byte PAY_ING = 0;
        public static final Byte PAY_SUCCESS = 1;
    }

    @TableId(value = TableAttributes.ORDER_ID, type = IdType.ASSIGN_ID)
    private Long orderId;

    @TableField(value = TableAttributes.ORDER_NO)
    private String orderNo;

    @TableField(value = TableAttributes.USER_ID)
    private Long userId;

    @TableField(value = TableAttributes.ADDRESS_ID)
    private Long addressId;

    @TableField(value = TableAttributes.TOTAL_PRICE)
    private BigDecimal totalPrice;

    @TableField(value = TableAttributes.PAY_STATUS)
    private Byte payStatus;

    @TableField(value = TableAttributes.PAY_TYPE)
    private Byte payType;

    @TableField(value = TableAttributes.PAY_TIME)
    private Date payTime;

    @TableField(value = TableAttributes.ORDER_STATUS)
    private Byte orderStatus;

    @TableField(value = TableAttributes.EXTRA_INFO)
    private String extraInfo;

    @TableField(value = TableAttributes.IS_DELETED)
    private Boolean isDeleted;

    @TableField(value = TableAttributes.CREATE_TIME, fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = TableAttributes.UPDATE_TIME, fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField(exist = false)
    private List<OrderItem> orderItems;

}
