package ltd.user.cloud.taoduoduo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

@Data
public class UserUpdateRequest implements Serializable {

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("电话号码")
    private String phoneNumber;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("头像")
    private String avatar;


}
