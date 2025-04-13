package ltd.user.cloud.taoduoduo.service;

import ltd.user.cloud.taoduoduo.entity.User;

public interface AdminUserService  {

    User getUserDetailById(Long loginUserId);

    Boolean lockUsers(Long[] ids, int lockStatus);

}
