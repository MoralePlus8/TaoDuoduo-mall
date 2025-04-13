package ltd.common.cloud.taoduoduo.pojo;

import lombok.Data;
import ltd.common.cloud.taoduoduo.enums.AuthLevel;

import java.io.Serializable;

@Data
public class AdminUserToken implements Serializable {
    private Long adminUserId;

    private String token;

    private AuthLevel auth=AuthLevel.ADMIN;
}
