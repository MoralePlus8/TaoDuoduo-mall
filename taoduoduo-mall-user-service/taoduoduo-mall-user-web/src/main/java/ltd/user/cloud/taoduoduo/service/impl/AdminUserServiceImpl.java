package ltd.user.cloud.taoduoduo.service.impl;

import lombok.RequiredArgsConstructor;
import ltd.common.cloud.taoduoduo.exception.UserNotExistException;
import ltd.common.cloud.taoduoduo.entity.User;
import ltd.user.cloud.taoduoduo.mapper.UserMapper;
import ltd.user.cloud.taoduoduo.service.AdminUserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService{

    private final UserMapper userMapper;

    @Override
    public Boolean lockUsers(Long[] ids, Boolean lockStatus) throws UserNotExistException {
        for (Long id : ids) {
            User user = userMapper.selectById(id);
            if (user != null) {
                user.setLocked(lockStatus);
                userMapper.updateById(user);
            }
            else throw new UserNotExistException();
        }
        return ids.length > 0;

    }

}
