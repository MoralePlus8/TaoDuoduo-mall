package ltd.user.cloud.taoduoduo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class RegisterRequest implements Serializable {

    @ApiModelProperty("用户名")
    @NotEmpty(message = "用户名不能为空")
    private String username;

    @ApiModelProperty("用户密码")
    @NotEmpty(message = "密码不能为空")
    private String password;

    @ApiModelProperty("用户类别")
    @NotEmpty(message = "用户类别不能为空")
    private String userType;

}
