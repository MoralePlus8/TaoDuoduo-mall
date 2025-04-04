package ltd.user.cloud.taoduoduo.service;

import ltd.common.cloud.taoduoduo.dto.PageQueryUtil;
import ltd.common.cloud.taoduoduo.dto.PageResult;
import ltd.common.cloud.taoduoduo.exception.*;
import ltd.common.cloud.taoduoduo.util.NumberUtil;
import ltd.common.cloud.taoduoduo.util.SystemUtil;
import ltd.user.cloud.taoduoduo.controller.param.MallUserUpdateParam;
import ltd.user.cloud.taoduoduo.entity.MallUser;

public interface MallUserService {

    String register(String loginName, String password) throws UserNameExistException, DataBaseErrorException;

    String login(String loginName, String passwordMD5) throws UserLockedException, LoginException;

    Boolean updateUserInfo(MallUserUpdateParam mallUser, Long userId) throws UserNotExistException;

    MallUser getUserDetailByToken(String token) throws TokenNotExistException, UserLockedException;

    Boolean logout(String token);

    Boolean lockUsers(Long[] ids, int lockStatus);

    PageResult<?> getNewBeeMallUsersPage(PageQueryUtil pageUtil);

    default String getNewToken(String timeStr, Long userId) {
        String src = timeStr + userId + NumberUtil.genRandomNum(4);
        return SystemUtil.genToken(src);
    }

}
