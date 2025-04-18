package ltd.commonsecurity.cloud.taoduoduo.config;

import ltd.commonsecurity.cloud.taoduoduo.filter.JwtAuthenticationFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
@ConditionalOnClass(OncePerRequestFilter.class)
public class SecurityAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(RedisTemplate.class)
    public JwtAuthenticationFilter jwtAuthenticationFilter(
            RedisTemplate<String, Object> redisTemplate) {
        return new JwtAuthenticationFilter(redisTemplate);
    }

}
