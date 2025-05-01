package ltd.user.cloud.taoduoduo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import ltd.common.cloud.taoduoduo.enums.ServiceResultEnum;
import ltd.common.cloud.taoduoduo.exception.*;
import ltd.common.cloud.taoduoduo.entity.Auth;
import ltd.user.cloud.taoduoduo.mapper.AuthMapper;
import ltd.user.cloud.taoduoduo.mapper.UserMapper;
import ltd.common.cloud.taoduoduo.entity.User;
import ltd.user.cloud.taoduoduo.dto.UserUpdateRequest;
import ltd.user.cloud.taoduoduo.service.MallUserService;
import ltd.common.cloud.taoduoduo.util.UserContextUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MallUserServiceImpl implements MallUserService {

    private final UserMapper userMapper;

    private final AuthMapper authMapper;

    private final RedisTemplate<String, Object> redisTemplate;

    private final PasswordEncoder passwordEncoder;

    @Value("${redis.path.user.token}")
    private String tokenPath;

    @Override
    public String register(String username, String password, String userType) throws UserNameExistException, DataBaseErrorException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", username);
        if(userMapper.selectOne(queryWrapper) != null) {
            throw new UserNameExistException();
        }
        User registerUser = new User();
        registerUser.setUsername(username);
        registerUser.setPassword(passwordEncoder.encode(password));
        if(userMapper.insert(registerUser) > 0) {
            setAuth(registerUser.getUserId(), userType);
            return ServiceResultEnum.SUCCESS.getResult();
        }
        throw new DataBaseErrorException();
    }

    public void setAuth(Long userId, String userType){
        if(userType.equals("ADMIN")){
            authMapper.insert(new Auth(userId, "ADMIN"));
            authMapper.insert(new Auth(userId, "USER"));
        }
        else if(userType.equals("USER")){
            authMapper.insert(new Auth(userId, "USER"));
        }
    }

    @Override
    public Boolean changePasswordById(Long userId, String originalPassword, String newPassword) throws WrongPasswordException {
        User user = userMapper.selectById(userId);
        if(user != null && passwordEncoder.matches(originalPassword, user.getPassword())){
            user.setPassword(passwordEncoder.encode(newPassword));
            return userMapper.updateById(user) > 0;
        }
        throw new WrongPasswordException();
    }

    @Override
    public Boolean updateUserInfoById(Long userId, UserUpdateRequest mallUser) {
        User user = new User();
        user.setUserId(userId);
        BeanUtils.copyProperties(mallUser, user);
        return userMapper.updateById(user) > 0;
    }

    @Override
    public User getUserDetailById(Long userId) throws UserNotExistException, UserLockedException {

        User user = userMapper.selectById(userId);
        if(user == null){
            throw new UserNotExistException();
        }
        if(Boolean.TRUE.equals(user.getLocked())) {
            throw new UserLockedException();
        }
        return user;

    }

    @Override
    public Boolean logout() {
        redisTemplate.opsForValue().set(tokenPath + UserContextUtil.getUserId(), UserContextUtil.getUserName());
        return true;
    }

}
