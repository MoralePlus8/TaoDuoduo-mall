package ltd.user.cloud.taoduoduo.service;

import ltd.common.cloud.taoduoduo.exception.LoginException;
import ltd.common.cloud.taoduoduo.exception.TokenNotExistException;
import ltd.common.cloud.taoduoduo.exception.UserNotExistException;
import ltd.common.cloud.taoduoduo.exception.WrongPasswordException;
import ltd.common.cloud.taoduoduo.util.NumberUtil;
import ltd.common.cloud.taoduoduo.util.SystemUtil;
import ltd.user.cloud.taoduoduo.entity.AdminUser;

public interface AdminUserService {

    String login(String userName, String password) throws LoginException;

    AdminUser getUserDetailById(Long loginUserId);

    AdminUser getUserDetailByToken(String token) throws TokenNotExistException;

    Boolean updatePassword(Long loginUserId, String originalPassword, String newPassword) throws WrongPasswordException;

    Boolean updateName(Long loginUserId, String loginUserName, String nickName) throws UserNotExistException;

    Boolean logout(String token);

    default String getNewToken(String timeStr, Long userId) {
        String src = timeStr + userId + NumberUtil.genRandomNum(6);
        return SystemUtil.genToken(src);
    }
}
