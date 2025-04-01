package ltd.user.cloud.newbee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("ltd.user.cloud.newbee.dao")
public class NewbeeMallCloudUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewbeeMallCloudUserServiceApplication.class, args);
    }

}
