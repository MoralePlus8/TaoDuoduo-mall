package ltd.user.cloud.taoduoduo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import ltd.common.cloud.taoduoduo.enums.ServiceResultEnum;
import ltd.common.cloud.taoduoduo.exception.*;
import ltd.common.cloud.taoduoduo.pojo.MallUserToken;
import ltd.user.cloud.taoduoduo.mapper.UserMapper;
import ltd.user.cloud.taoduoduo.entity.User;
import ltd.user.cloud.taoduoduo.controller.param.UserUpdateParam;
import ltd.user.cloud.taoduoduo.service.MallUserService;
import ltd.user.cloud.taoduoduo.utils.UserContextUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MallUserServiceImpl implements MallUserService {

    private final UserMapper userMapper;

    private final RedisTemplate<String, Object> redisTemplate;

    private final PasswordEncoder passwordEncoder;

    @Value("${redis.user.token.path}")
    private String tokenPath;

    @Override
    public String register(String username, String password) throws UserNameExistException, DataBaseErrorException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", username);
        if(userMapper.selectOne(queryWrapper) != null) {
            throw new UserNameExistException();
        }
        User registerUser = new User();
        registerUser.setUsername(username);
        registerUser.setPassword(passwordEncoder.encode(password));
        if(userMapper.insert(registerUser) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        throw new DataBaseErrorException();
    }

    @Override
    public Boolean changePassword(String originalPassword, String newPassword) throws WrongPasswordException {
        User admin = userMapper.selectById(UserContextUtil.getUser().getUserId());
        if(admin != null && passwordEncoder.matches(originalPassword, admin.getPassword())){
            admin.setPassword(newPassword);
            return userMapper.updateById(admin) > 0;
        }
        throw new WrongPasswordException();
    }

    @Override
    public Boolean updateUserInfo(UserUpdateParam mallUser) throws UserNotExistException {
        User user = userMapper.selectById(UserContextUtil.getUser().getUserId());
        if(user==null){
            throw new UserNotExistException();
        }

        BeanUtils.copyProperties(mallUser, user);
        return userMapper.updateById(user) > 0;
    }

    @Override
    public User getUserDetail() throws TokenNotExistException, UserLockedException {
        MallUserToken mallUserToken = (MallUserToken) redisTemplate.opsForValue().get(tokenPath + UserContextUtil.getUser().getUserId());
        if(mallUserToken != null) {
            User user = userMapper.selectById(mallUserToken.getUserId());
            if(user == null){
                throw new TokenNotExistException();
            }
            if(Boolean.TRUE.equals(user.getLocked())) {
                throw new UserLockedException();
            }
            return user;
        }
        throw new TokenNotExistException();
    }

    @Override
    public Boolean logout() {
        redisTemplate.delete(tokenPath+UserContextUtil.getUser().getUserId());
        return true;
    }

}
