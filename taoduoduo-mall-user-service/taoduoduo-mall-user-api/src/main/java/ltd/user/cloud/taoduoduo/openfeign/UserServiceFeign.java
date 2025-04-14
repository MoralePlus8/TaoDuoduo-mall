package ltd.user.cloud.taoduoduo.openfeign;

import ltd.common.cloud.taoduoduo.dto.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "taoduoduo-mall-user-service", path = "/")
public interface UserServiceFeign {

    @GetMapping(value = "/admin/profile")
    Result getMallUserByToken(@RequestParam("userId") Long userId);

}
