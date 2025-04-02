package ltd.common.cloud.taoduoduo.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MallUserToken implements Serializable {
    private Long userId;

    private String token;

}
