package ltd.user.cloud.taoduoduo.controller.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ChangePasswordParam {

    @ApiModelProperty("旧密码")
    private String oldPassword;

    @ApiModelProperty("新密码")
    private String newPassword;

}
