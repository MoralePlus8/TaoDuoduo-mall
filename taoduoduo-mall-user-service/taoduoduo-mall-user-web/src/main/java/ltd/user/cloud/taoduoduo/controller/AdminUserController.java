package ltd.user.cloud.taoduoduo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import ltd.common.cloud.taoduoduo.dto.Result;
import ltd.common.cloud.taoduoduo.dto.ResultGenerator;
import ltd.common.cloud.taoduoduo.entity.User;
import ltd.user.cloud.taoduoduo.service.AdminUserService;
import ltd.user.cloud.taoduoduo.service.MallUserService;
import ltd.common.cloud.taoduoduo.util.UserContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@Api(value = "v1", tags = "管理员操作接口")
@RestController
@RequestMapping("/user/admin")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserService adminUserService;

    private final MallUserService mallUserService;

    private static final Logger logger = LoggerFactory.getLogger(AdminUserController.class);

    @GetMapping("/profile")
    @ApiOperation(value = "获取任意用户信息接口")
    public Result profile(@RequestParam Long userId) {
        logger.info("adminUser:{}", UserContextUtil.getUser());
        try {

            User user = mallUserService.getUserDetailById(userId);
            user.setPassword("******");
            Result result = ResultGenerator.genSuccessResult();
            result.setData(user);
            return result;

        }catch (Exception e){
            logger.error("获取管理员信息失败, userId={}, error={}", userId, e.getMessage());
            return ResultGenerator.genFailResult("获取管理员信息失败");
        }
    }

    @PutMapping("/lock")
    @ApiOperation(value = "锁定用户接口")
    public Result lock(@RequestParam Long[] ids, @RequestParam Boolean lockStatus) {
        try {
            if (Boolean.TRUE.equals(adminUserService.lockUsers(ids, lockStatus))) {
                return ResultGenerator.genSuccessResult();
            } else {
                return ResultGenerator.genFailResult("锁定用户失败");
            }
        } catch (Exception e) {
            logger.error("锁定用户失败, ids={}, lockStatus={}, error={}", ids, lockStatus, e.getMessage());
            return ResultGenerator.genFailResult("锁定用户失败");
        }
    }


}
