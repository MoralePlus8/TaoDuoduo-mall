package ltd.user.cloud.newbee.config.annotation;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Token2AdminUser {
    String value() default "adminUser";
}
