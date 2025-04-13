package ltd.user.cloud.taoduoduo.config;

import com.alibaba.cloud.sentinel.SentinelProperties;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.SentinelWebInterceptor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class UserServiceWebMvcConfigurer extends WebMvcConfigurationSupport {

    private static final Logger log = LoggerFactory.getLogger(UserServiceWebMvcConfigurer.class);

    private final SentinelProperties sentinelProperties;

    private final Optional<SentinelWebInterceptor> sentinelWebInterceptorOptional;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.
                addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
                .resourceChain(false);
    }

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        if (sentinelWebInterceptorOptional.isPresent()) {
            SentinelProperties.Filter filterConfig = sentinelProperties.getFilter();
            registry.addInterceptor(sentinelWebInterceptorOptional.get()).order(filterConfig.getOrder()).addPathPatterns(filterConfig.getUrlPatterns());
            log.info("[Sentinel Starter] register SentinelWebInterceptor with urlPatterns: {}.", filterConfig.getUrlPatterns());
        }
    }

}
