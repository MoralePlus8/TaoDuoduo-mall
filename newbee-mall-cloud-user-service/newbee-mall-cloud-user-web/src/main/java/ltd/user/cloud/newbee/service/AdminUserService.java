package ltd.user.cloud.newbee.service;

import ltd.common.cloud.newbee.util.NumberUtil;
import ltd.common.cloud.newbee.util.SystemUtil;
import ltd.user.cloud.newbee.entity.AdminUser;

public interface AdminUserService {

    String login(String userName, String password);

    AdminUser getUserDetailById(Long loginUserId);

    AdminUser getUserDetailByToken(String token);

    Boolean updatePassword(Long loginUserId, String originalPassword, String newPassword);

    Boolean updateName(Long loginUserId, String loginUserName, String nickName);

    Boolean logout(String token);

    default String getNewToken(String timeStr, Long userId) {
        String src = timeStr + userId + NumberUtil.genRandomNum(6);
        return SystemUtil.genToken(src);
    }
}
