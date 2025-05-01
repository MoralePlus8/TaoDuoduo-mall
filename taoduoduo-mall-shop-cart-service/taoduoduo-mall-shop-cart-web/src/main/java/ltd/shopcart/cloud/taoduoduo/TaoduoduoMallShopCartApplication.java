package ltd.shopcart.cloud.taoduoduo;

import ltd.goods.cloud.taoduoduo.openfeign.GoodsServiceFeign;
import ltd.user.cloud.taoduoduo.openfeign.UserServiceFeign;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackageClasses ={UserServiceFeign.class, GoodsServiceFeign.class})
public class TaoduoduoMallShopCartApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaoduoduoMallShopCartApplication.class, args);
    }

}
