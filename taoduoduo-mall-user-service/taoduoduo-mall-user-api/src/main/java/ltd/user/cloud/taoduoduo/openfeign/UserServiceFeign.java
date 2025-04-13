package ltd.user.cloud.taoduoduo.openfeign;

import ltd.common.cloud.taoduoduo.dto.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "taoduoduo-mall-user-service", path = "/users")
public interface UserServiceFeign {

    @GetMapping(value = "/admin/{token}")
    Result getAdminUserByToken(@PathVariable(value = "token") String token);

    @GetMapping(value = "/mall/{token}")
    Result getMallUserByToken(@PathVariable(value = "token") String token);

}
