package ltd.shopcart.cloud.taoduoduo.annotation;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TokenToMallUser {

    String value() default "user";

}
