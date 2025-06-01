package ltd.order.cloud.taoduoduo;

import ltd.goods.cloud.taoduoduo.openfeign.GoodsServiceFeign;
import ltd.shopcart.cloud.taoduoduo.openfeign.ShopCartServiceFeign;
import ltd.user.cloud.taoduoduo.openfeign.UserServiceFeign;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackageClasses ={UserServiceFeign.class, GoodsServiceFeign.class, ShopCartServiceFeign.class})
public class TaoduoduoMallOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaoduoduoMallOrderApplication.class, args);
    }

}
