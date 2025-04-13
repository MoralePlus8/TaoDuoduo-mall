package ltd.user.cloud.taoduoduo.service;

import ltd.common.cloud.taoduoduo.exception.*;
import ltd.user.cloud.taoduoduo.controller.param.UserUpdateParam;
import ltd.user.cloud.taoduoduo.entity.User;

public interface MallUserService {

    String register(String username, String password) throws UserNameExistException, DataBaseErrorException;

    Boolean changePassword(String originalPassword, String newPassword) throws WrongPasswordException;

    Boolean updateUserInfo(UserUpdateParam mallUser) throws UserNotExistException;

    User getUserDetail() throws TokenNotExistException, UserLockedException;

    Boolean logout();

}
