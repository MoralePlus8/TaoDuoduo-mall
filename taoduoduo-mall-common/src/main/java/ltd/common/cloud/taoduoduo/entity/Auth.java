package ltd.common.cloud.taoduoduo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_taoduoduo_mall_auth")
public class Auth {

    @TableId(value = "auth_id", type = IdType.ASSIGN_ID)
    private Long authId;

    @TableField(value = "auth_name")
    private String authName;

    @TableField(value = "user_id")
    private Long userId;

    public Auth(Long userId, String authName) {
        this.userId = userId;
        this.authName = authName;
    }

}
