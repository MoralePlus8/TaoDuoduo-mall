package ltd.gateway.cloud.taoduoduo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TaoduoduoMallGatewayAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaoduoduoMallGatewayAdminApplication.class, args);
    }

}
