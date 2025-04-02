package ltd.user.cloud.taoduoduo.service.impl;

import lombok.RequiredArgsConstructor;
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
    public String login(String userName, String password) {
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
        return "登录失败";
    }

    @Override
    public AdminUser getUserDetailById(Long loginUserId) {
        return adminUserMapper.selectByPrimaryKey(loginUserId);
    }

    @Override
    public AdminUser getUserDetailByToken(String token) {
        ValueOperations<Object, Object> valueOperations = redisTemplate.opsForValue();
        AdminUserToken adminUserToken = (AdminUserToken) valueOperations.get(token);
        if(adminUserToken != null) {
            return adminUserMapper.selectByPrimaryKey(adminUserToken.getAdminUserId());
        }
        return null;
    }

    @Override
    public Boolean updatePassword(Long loginUserId, String originalPassword, String newPassword) {
        AdminUser adminUser = adminUserMapper.selectByPrimaryKey(loginUserId);
        if(adminUser != null && originalPassword.equals(adminUser.getLoginPassword())){
            adminUser.setLoginPassword(newPassword);
            return adminUserMapper.updateByPrimaryKey(adminUser) > 0;
        }
        return false;
    }

    @Override
    public Boolean updateName(Long loginUserId, String loginUserName, String nickName) {
        AdminUser adminUser = adminUserMapper.selectByPrimaryKey(loginUserId);
        if(adminUser != null){
            adminUser.setLoginUserName(loginUserName);
            adminUser.setNickName(nickName);
            return adminUserMapper.updateByPrimaryKey(adminUser) > 0;
        }
        return false;
    }

    @Override
    public Boolean logout(String token) {
        redisTemplate.delete(token);
        return true;
    }
}
