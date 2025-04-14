package ltd.user.cloud.taoduoduo.service;

import ltd.common.cloud.taoduoduo.exception.UserNotExistException;

public interface AdminUserService  {

    Boolean lockUsers(Long[] ids, Boolean lockStatus) throws UserNotExistException;

}
