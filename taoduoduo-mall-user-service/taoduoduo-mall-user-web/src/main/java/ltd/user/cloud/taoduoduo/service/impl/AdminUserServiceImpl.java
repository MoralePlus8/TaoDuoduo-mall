package ltd.user.cloud.taoduoduo.service.impl;

import lombok.RequiredArgsConstructor;
import ltd.user.cloud.taoduoduo.entity.User;
import ltd.user.cloud.taoduoduo.mapper.UserMapper;
import ltd.user.cloud.taoduoduo.service.AdminUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService{

    private final UserMapper userMapper;

    private final RedisTemplate<String, Object> redisTemplate;

    private final PasswordEncoder passwordEncoder;

    @Value("${redis.admin.token.path}")
    private String tokenPath;

    @Override
    public User getUserDetailById(Long loginUserId) {
        return userMapper.selectById(loginUserId);
    }

    @Override
    public Boolean lockUsers(Long[] ids, int lockStatus) {

        return ids.length > 0;

    }
}
