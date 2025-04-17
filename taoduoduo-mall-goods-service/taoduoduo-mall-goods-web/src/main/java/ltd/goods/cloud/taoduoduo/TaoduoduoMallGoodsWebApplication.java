package ltd.goods.cloud.taoduoduo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TaoduoduoMallGoodsWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaoduoduoMallGoodsWebApplication.class, args);
    }

}
