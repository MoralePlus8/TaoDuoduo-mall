package ltd.user.cloud.newbee.service;

import ltd.common.cloud.newbee.dto.PageQueryUtil;
import ltd.common.cloud.newbee.dto.PageResult;
import ltd.common.cloud.newbee.util.NumberUtil;
import ltd.common.cloud.newbee.util.SystemUtil;
import ltd.user.cloud.newbee.controller.param.MallUserUpdateParam;
import ltd.user.cloud.newbee.entity.MallUser;

public interface NewBeeMallUserService {

    String register(String loginName, String password);

    String login(String loginName, String passwordMD5);

    Boolean updateUserInfo(MallUserUpdateParam mallUser, Long userId);

    MallUser getUserDetailByToken(String token);

    Boolean logout(String token);

    Boolean lockUsers(Long[] ids, int lockStatus);

    PageResult<?> getNewBeeMallUsersPage(PageQueryUtil pageUtil);

    default String getNewToken(String timeStr, Long userId) {
        String src = timeStr + userId + NumberUtil.genRandomNum(4);
        return SystemUtil.genToken(src);
    }

}
