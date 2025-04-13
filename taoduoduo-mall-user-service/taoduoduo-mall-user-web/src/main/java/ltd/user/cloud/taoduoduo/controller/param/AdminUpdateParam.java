package ltd.user.cloud.taoduoduo.controller.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class AdminUpdateParam implements Serializable {

    @NotEmpty(message = "loginName不能为空")
    private String adminName;

}
