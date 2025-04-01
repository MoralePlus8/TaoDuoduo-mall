package ltd.user.cloud.newbee.controller.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class UpdateAdminNameParam implements Serializable {

    @NotEmpty(message = "loginName不能为空")
    private String loginUserName;

    @NotEmpty(message = "nickName不能为空")
    private String nickName;

}
