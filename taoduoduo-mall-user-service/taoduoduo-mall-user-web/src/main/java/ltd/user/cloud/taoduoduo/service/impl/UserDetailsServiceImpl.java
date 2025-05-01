package ltd.user.cloud.taoduoduo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import ltd.common.cloud.taoduoduo.entity.Auth;
import ltd.common.cloud.taoduoduo.pojo.SecurityAuthority;
import ltd.user.cloud.taoduoduo.mapper.AuthMapper;
import ltd.user.cloud.taoduoduo.mapper.UserMapper;
import ltd.common.cloud.taoduoduo.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserMapper userMapper;

    private final AuthMapper authMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", username);
        User userDetails = userMapper.selectOne(queryWrapper);
        if(userDetails == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        QueryWrapper<Auth> authQueryWrapper = new QueryWrapper<>();
        authQueryWrapper.eq("user_id", userDetails.getUserId());
        userDetails.setAuthorities(authMapper.selectList(authQueryWrapper)
                .stream().map(Auth::getAuthName)
                .map(SecurityAuthority::new)
                .collect(Collectors.toList()));

        return userDetails;
    }
}
