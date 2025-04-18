package ltd.commonsecurity.cloud.taoduoduo.filter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ltd.common.cloud.taoduoduo.pojo.UserInfo;
import ltd.common.cloud.taoduoduo.util.JwtUtil;
import ltd.common.cloud.taoduoduo.util.UserContextUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (JwtUtil.validateToken(token)) {
                UserInfo userInfo = JwtUtil.getUserDetail(token);
                String tokenPath = "user:token:";
                if(Boolean.FALSE.equals(redisTemplate.hasKey(tokenPath + userInfo.getUserId()))){
                    UserContextUtil.setUser(userInfo);

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userInfo, null, userInfo.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        filterChain.doFilter(request, response);

        UserContextUtil.removeUser();
    }
}
