package ltd.user.cloud.taoduoduo.entity;

import lombok.Data;

@Data
public class AdminUser {

    private Long adminUserId;

    private String loginUserName;

    private String loginPassword;

    private String nickName;

    private Byte locked;

}
