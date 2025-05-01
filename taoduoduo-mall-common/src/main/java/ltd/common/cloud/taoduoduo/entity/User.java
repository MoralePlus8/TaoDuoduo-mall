package ltd.common.cloud.taoduoduo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ltd.common.cloud.taoduoduo.pojo.SecurityAuthority;
import ltd.common.cloud.taoduoduo.pojo.UserInfo;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("tb_taoduoduo_mall_user")
public class User extends UserInfo {

    @TableId(value = "user_id", type = IdType.ASSIGN_ID)
    private Long userId;

    @TableField(value = "user_name")
    private String username;

    @TableField(exist = false)
    private Date exp;

    @TableField(value = "password_md5")
    private String password;

    @TableField(exist = false)
    private List<SecurityAuthority> authorities;

    @TableField(value = "salt")
    private String salt;

    @TableField(value = "phone_number")
    private String phoneNumber;

    @TableField(value = "email")
    private String email;

    @TableField(value = "avatar")
    private String avatar;

    @TableField(value = "deleted")
    private Boolean deleted;

    @TableField(value = "locked")
    private Boolean locked;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createdTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updatedTime;


    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

}
