package ltd.user.cloud.taoduoduo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("ltd.user.cloud.taoduoduo.dao")
public class TaoduoduoMallUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaoduoduoMallUserServiceApplication.class, args);
    }

}
