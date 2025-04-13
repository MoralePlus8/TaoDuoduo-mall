package ltd.user.cloud.taoduoduo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import ltd.common.cloud.taoduoduo.dto.Result;
import ltd.common.cloud.taoduoduo.dto.ResultGenerator;
import ltd.common.cloud.taoduoduo.pojo.AdminUserToken;
import ltd.user.cloud.taoduoduo.entity.User;
import ltd.user.cloud.taoduoduo.service.AdminUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@Api(value = "v1", tags = "管理员操作接口")
@RestController
@RequestMapping("/users/admin")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserService adminUserService;

    private static final Logger logger = LoggerFactory.getLogger(AdminUserController.class);

    @PostMapping("/profile")
    @ApiOperation(value = "获取管理员信息接口")
    public Result profile(@RequestBody AdminUserToken adminUser) {
        logger.info("adminUser:{}", adminUser);
        User adminEntity = adminUserService.getUserDetailById(adminUser.getAdminUserId());
        if (adminEntity != null) {
            adminEntity.setPassword("******");
            Result result = ResultGenerator.genSuccessResult();
            result.setData(adminEntity);
            return result;
        }
        return ResultGenerator.genFailResult("获取管理员信息失败");
    }

}
