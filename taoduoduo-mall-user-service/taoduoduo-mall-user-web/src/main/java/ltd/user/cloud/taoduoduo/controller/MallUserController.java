package ltd.user.cloud.taoduoduo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import ltd.common.cloud.taoduoduo.dto.Result;
import ltd.common.cloud.taoduoduo.dto.ResultGenerator;
import ltd.common.cloud.taoduoduo.enums.ServiceResultEnum;
import ltd.user.cloud.taoduoduo.dto.ChangePasswordRequest;
import ltd.user.cloud.taoduoduo.dto.RegisterRequest;
import ltd.user.cloud.taoduoduo.dto.UserUpdateRequest;
import ltd.user.cloud.taoduoduo.entity.User;
import ltd.user.cloud.taoduoduo.service.MallUserService;
import ltd.common.cloud.taoduoduo.util.UserContextUtil;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;

import javax.validation.Valid;

import static ltd.common.cloud.taoduoduo.dto.ResultGenerator.genSuccessResult;

@Api(value = "v1", tags = "用户操作接口")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class MallUserController {

    private static final Logger logger = LoggerFactory.getLogger(MallUserController.class);

    private final MallUserService mallUserService;

    @PostMapping("/test")
    public String test() {
        return UserContextUtil.getUser().toString();
    }

    @PostMapping("/register")
    @ApiOperation(value = "用户注册")
    public Result register(@RequestBody @Valid RegisterRequest registerRequest) {

        String registerResult;
        try {
            registerResult = mallUserService.register(registerRequest.getUsername(), registerRequest.getPassword(), registerRequest.getUserType());
        } catch (Exception e) {
            logger.error("register api,loginName={},error={}", registerRequest.getUsername(), e.getStackTrace());
            return ResultGenerator.genFailResult("注册失败");
        }

        logger.info("register api,loginName={},registerResult={}", registerRequest.getUsername(), registerResult);

        if (registerResult.equals(ServiceResultEnum.SUCCESS.getResult())) {
            return genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(registerResult);
        }

    }

    @PostMapping("/logout")
    @ApiOperation(value="登出接口", notes="在黑名单中添加token")
    public Result logout() {
        Boolean logoutResult = mallUserService.logout();
        logger.info("logout api,loginOutUser={},logoutResult={}", UserContextUtil.getUser().getUserId(), logoutResult);

        if(Boolean.TRUE.equals(logoutResult)){
            return genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("登出失败");
        }
    }

    @PutMapping("/password")
    @ApiOperation(value = "修改密码接口")
    public Result passwordUpdate(@RequestBody @Valid ChangePasswordRequest passwordParam) {
        logger.info("adminUser:{}", UserContextUtil.getUser());

        boolean updateResult;
        try{
            updateResult = mallUserService.changePasswordById(UserContextUtil.getUserId(), passwordParam.getOldPassword(), passwordParam.getNewPassword());
        } catch (Exception e) {
            logger.error("update password error, adminUserId={}, error={}", UserContextUtil.getUser().getUserId(), e.getMessage());
            return ResultGenerator.genFailResult("修改密码失败");
        }
        if (updateResult) {
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult("DB ERROR");
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改用户信息")
    public Result update(@RequestBody @ApiParam("用户信息") UserUpdateRequest updateParam) {

        try {
            Boolean updateResult = mallUserService.updateUserInfoById(UserContextUtil.getUserId(), updateParam);
            logger.info("update api,loginName={},updateResult={}", updateParam.getUsername(), updateResult);

            if (Boolean.TRUE.equals(updateResult)) {
                return genSuccessResult();
            } else {
                return ResultGenerator.genFailResult("修改失败");
            }
        } catch (Exception e) {
            logger.error("update api,loginName={},error={}", updateParam.getUsername(), e.getStackTrace());
            return ResultGenerator.genFailResult("修改失败");
        }
    }

    @GetMapping("/detail")
    @ApiOperation(value = "获取当前登录用户信息")
    public Result getUserDetail() {
        User user;
        try{
            user = mallUserService.getUserDetailById(UserContextUtil.getUserId());
        }catch (Exception e){
            logger.error("getUserDetail api,loginName={},error={}", UserContextUtil.getUserId(), e.getStackTrace());
            return ResultGenerator.genFailResult("获取用户信息失败");
        }
        return ResultGenerator.genSuccessResult(user);
    }

}
