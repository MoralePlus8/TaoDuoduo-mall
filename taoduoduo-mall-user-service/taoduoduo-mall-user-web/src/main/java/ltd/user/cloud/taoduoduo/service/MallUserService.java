package ltd.user.cloud.taoduoduo.service;

import ltd.common.cloud.taoduoduo.exception.*;
import ltd.user.cloud.taoduoduo.dto.UserUpdateRequest;
import ltd.user.cloud.taoduoduo.entity.User;

public interface MallUserService {

    String register(String username, String password, String userType) throws UserNameExistException, DataBaseErrorException;

    Boolean changePasswordById(Long userId, String originalPassword, String newPassword) throws WrongPasswordException;

    Boolean updateUserInfoById(Long userId, UserUpdateRequest mallUser);

    User getUserDetailById(Long userId) throws UserNotExistException, UserLockedException;

    Boolean logout();

}
