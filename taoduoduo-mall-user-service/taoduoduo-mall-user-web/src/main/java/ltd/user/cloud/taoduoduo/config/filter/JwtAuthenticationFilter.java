package ltd.user.cloud.taoduoduo.config.filter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ltd.user.cloud.taoduoduo.entity.User;
import ltd.user.cloud.taoduoduo.utils.JwtUtil;
import ltd.user.cloud.taoduoduo.utils.UserContextUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${redis.user.token.path}")
    private String tokenPath;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (JwtUtil.validateToken(token)) {
                User user = JwtUtil.getUserDetail(token);
                if(Boolean.FALSE.equals(redisTemplate.hasKey(tokenPath + user.getUserId()))){
                    UserContextUtil.setUser(user);

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        filterChain.doFilter(request, response);

        UserContextUtil.removeUser();
    }
}
