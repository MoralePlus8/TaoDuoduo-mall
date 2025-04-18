package ltd.user.cloud.taoduoduo.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class AdminUpdateRequest implements Serializable {

    @NotEmpty(message = "loginName不能为空")
    private String adminName;

}
