package ltd.common.cloud.taoduoduo.pojo;

import lombok.Data;
import ltd.common.cloud.taoduoduo.enums.AuthLevel;

import java.io.Serializable;

@Data
public class MallUserToken implements Serializable {
    private Long userId;

    private String token;

    private AuthLevel auth=AuthLevel.USER;

}
