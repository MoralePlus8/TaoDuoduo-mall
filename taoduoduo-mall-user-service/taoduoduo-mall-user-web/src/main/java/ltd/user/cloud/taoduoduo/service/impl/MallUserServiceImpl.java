package ltd.user.cloud.taoduoduo.service.impl;

import lombok.RequiredArgsConstructor;
import ltd.common.cloud.taoduoduo.dto.PageQueryUtil;
import ltd.common.cloud.taoduoduo.dto.PageResult;
import ltd.common.cloud.taoduoduo.enums.ServiceResultEnum;
import ltd.common.cloud.taoduoduo.pojo.MallUserToken;
import ltd.common.cloud.taoduoduo.util.MD5Util;
import ltd.user.cloud.taoduoduo.controller.param.MallUserUpdateParam;
import ltd.user.cloud.taoduoduo.dao.MallUserMapper;
import ltd.user.cloud.taoduoduo.entity.MallUser;
import ltd.user.cloud.taoduoduo.service.MallUserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MallUserServiceImpl implements MallUserService {

    private final MallUserMapper mallUserMapper;

    private final RedisTemplate<Object, Object> redisTemplate;


    @Override
    public String register(String loginName, String password) {
        if(mallUserMapper.selectByLoginName(loginName) != null) {
            return ServiceResultEnum.SAME_LOGIN_NAME_EXIST.getResult();
        }

        MallUser registerUser = new MallUser();
        registerUser.setLoginName(loginName);
        registerUser.setNickName(loginName);
        registerUser.setIntroduceSign("");
        String passwordMD5 = MD5Util.MD5Encode(password, "UTF-8");
        registerUser.setPasswordMd5(passwordMD5);
        if(mallUserMapper.insertSelective(registerUser) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String login(String loginName, String passwordMD5) {
        MallUser user=mallUserMapper.selectByLoginNameAndPasswd(loginName,passwordMD5);
        if(user!=null) {
            if(user.getLockedFlag()==1) {
                return ServiceResultEnum.LOGIN_USER_LOCKED_ERROR.getResult();
            }

            String token = getNewToken(System.currentTimeMillis() + "", user.getUserId());
            MallUserToken mallUserToken = new MallUserToken();
            mallUserToken.setUserId(user.getUserId());
            mallUserToken.setToken(token);
            ValueOperations<Object, Object> setToken = redisTemplate.opsForValue();
            setToken.set(token, mallUserToken, (long)7 * 24 * 60 * 60, TimeUnit.SECONDS);

            return token;
        }

        return ServiceResultEnum.LOGIN_ERROR.getResult();
    }

    @Override
    public Boolean updateUserInfo(MallUserUpdateParam mallUser, Long userId) {
        MallUser user = mallUserMapper.selectByPrimaryKey(userId);
        if(user==null){
            NewBeeMallException.fail(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }

        user.setNickName(mallUser.getNickName());
        if(!MD5Util.MD5Encode("", "UTF-8").equals(mallUser.getPasswordMd5())) {
            user.setPasswordMd5(mallUser.getPasswordMd5());
        }
        user.setIntroduceSign(mallUser.getIntroduceSign());
        return mallUserMapper.updateByPrimaryKeySelective(user) > 0;
    }

    @Override
    public MallUser getUserDetailByToken(String token) {
        ValueOperations<Object, Object> valueOperations = redisTemplate.opsForValue();
        MallUserToken mallUserToken = (MallUserToken) valueOperations.get(token);
        if(mallUserToken != null) {
            MallUser mallUser = mallUserMapper.selectByPrimaryKey(mallUserToken.getUserId());
            if(mallUser == null){
                NewBeeMallException.fail(ServiceResultEnum.DATA_NOT_EXIST.getResult());
            }
            if(mallUser.getLockedFlag().intValue() == 1) {
                NewBeeMallException.fail(ServiceResultEnum.LOGIN_USER_LOCKED_ERROR.getResult());
            }
            return mallUser;
        }
        NewBeeMallException.fail(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        return null;
    }

    @Override
    public Boolean logout(String token) {
        redisTemplate.delete(token);
        return true;
    }

    @Override
    public Boolean lockUsers(Long[] ids, int lockStatus) {
        if(ids.length < 1){
            return false;
        }
        return mallUserMapper.lockUserBatch(ids, lockStatus) > 0;
    }

    @Override
    public PageResult<?> getNewBeeMallUsersPage(PageQueryUtil pageUtil) {
        List<MallUser> mallUsers=mallUserMapper.findMallUserList(pageUtil);
        int total=mallUserMapper.getTotalMallUsers(pageUtil);
        return new PageResult<>(mallUsers,total,pageUtil.getLimit(),pageUtil.getPage());
    }

}
