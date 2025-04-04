package ltd.user.cloud.taoduoduo.service.impl;

import lombok.RequiredArgsConstructor;
import ltd.common.cloud.taoduoduo.exception.LoginException;
import ltd.common.cloud.taoduoduo.exception.TokenNotExistException;
import ltd.common.cloud.taoduoduo.exception.UserNotExistException;
import ltd.common.cloud.taoduoduo.exception.WrongPasswordException;
import ltd.common.cloud.taoduoduo.pojo.AdminUserToken;
import ltd.user.cloud.taoduoduo.dao.AdminUserMapper;
import ltd.user.cloud.taoduoduo.entity.AdminUser;
import ltd.user.cloud.taoduoduo.service.AdminUserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService{

    private AdminUserMapper adminUserMapper;

    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public String login(String userName, String password) throws LoginException {
        AdminUser loginAdminUser = adminUserMapper.login(userName, password);
        if(loginAdminUser != null) {
            String token = getNewToken(System.currentTimeMillis() + "", loginAdminUser.getAdminUserId());
            AdminUserToken adminUserToken = new AdminUserToken();
            adminUserToken.setAdminUserId(loginAdminUser.getAdminUserId());
            adminUserToken.setToken(token);
            ValueOperations<Object, Object> setToken = redisTemplate.opsForValue();
            setToken.set(token, adminUserToken, (long)2 * 24 * 60 * 60, TimeUnit.SECONDS);
            return token;
        }
        throw new LoginException();
    }

    @Override
    public AdminUser getUserDetailById(Long loginUserId) {
        return adminUserMapper.selectByPrimaryKey(loginUserId);
    }

    @Override
    public AdminUser getUserDetailByToken(String token) throws TokenNotExistException {
        ValueOperations<Object, Object> valueOperations = redisTemplate.opsForValue();
        AdminUserToken adminUserToken = (AdminUserToken) valueOperations.get(token);
        if(adminUserToken != null) {
            return adminUserMapper.selectByPrimaryKey(adminUserToken.getAdminUserId());
        }
        throw new TokenNotExistException();
    }

    @Override
    public Boolean updatePassword(Long loginUserId, String originalPassword, String newPassword) throws WrongPasswordException {
        AdminUser adminUser = adminUserMapper.selectByPrimaryKey(loginUserId);
        if(adminUser != null && originalPassword.equals(adminUser.getLoginPassword())){
            adminUser.setLoginPassword(newPassword);
            return adminUserMapper.updateByPrimaryKey(adminUser) > 0;
        }
        throw new WrongPasswordException();
    }

    @Override
    public Boolean updateName(Long loginUserId, String loginUserName, String nickName) throws UserNotExistException {
        AdminUser adminUser = adminUserMapper.selectByPrimaryKey(loginUserId);
        if(adminUser != null){
            adminUser.setLoginUserName(loginUserName);
            adminUser.setNickName(nickName);
            return adminUserMapper.updateByPrimaryKey(adminUser) > 0;
        }
        throw new UserNotExistException();
    }

    @Override
    public Boolean logout(String token) {
        redisTemplate.delete(token);
        return true;
    }
}
