package ltd.user.cloud.taoduoduo.config;

import lombok.RequiredArgsConstructor;
import ltd.user.cloud.taoduoduo.entity.User;
import ltd.user.cloud.taoduoduo.utils.JwtUtil;
import ltd.user.cloud.taoduoduo.config.filter.JsonLoginFilter;
import ltd.user.cloud.taoduoduo.config.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final UserDetailsService customUserDetailsService;

    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${redis.user.token.path}")
    private String tokenPath;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        auth.authenticationProvider(authProvider);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/**/login", "/**/register").permitAll()
                .antMatchers("/**/user/**").hasAuthority("USER")
                .antMatchers("/**/admin/**").hasAuthority("ADMIN")
                .anyRequest().authenticated();

        JsonLoginFilter jsonLoginFilter = new JsonLoginFilter();
        jsonLoginFilter.setFilterProcessesUrl("/user/login");
        jsonLoginFilter.setAuthenticationManager(authenticationManagerBean());
        jsonLoginFilter.setAuthenticationSuccessHandler((request, response, authentication) -> {

            // 登录成功：生成 JWT 并返回
            User userInfo = (User) authentication.getPrincipal();
            Map<String, Object> claim = new HashMap<>();
            claim.put("userId", userInfo.getUserId());
            claim.put("username", userInfo.getUsername());
            claim.put("authorities", userInfo.getAuthorities());

            String jwt = JwtUtil.generateToken(authentication.getName(),claim);
            redisTemplate.delete(tokenPath + userInfo.getUserId());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"token\": \"" + jwt + "\"}");
        });
        jsonLoginFilter.setAuthenticationFailureHandler((request, response, exception) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"error\": \"" + exception.getMessage() + "\"}");
        });

        http.addFilterAt(jsonLoginFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }


}
