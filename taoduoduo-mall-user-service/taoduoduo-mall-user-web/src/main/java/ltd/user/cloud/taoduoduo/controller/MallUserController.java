package ltd.user.cloud.taoduoduo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import ltd.common.cloud.taoduoduo.dto.Result;
import ltd.common.cloud.taoduoduo.dto.ResultGenerator;
import ltd.common.cloud.taoduoduo.enums.ServiceResultEnum;
import ltd.common.cloud.taoduoduo.util.BeanUtil;
import ltd.user.cloud.taoduoduo.controller.param.ChangePasswordParam;
import ltd.user.cloud.taoduoduo.controller.param.RegisterParam;
import ltd.user.cloud.taoduoduo.controller.param.UserUpdateParam;
import ltd.user.cloud.taoduoduo.controller.vo.MallUserVO;
import ltd.user.cloud.taoduoduo.entity.User;
import ltd.user.cloud.taoduoduo.service.MallUserService;
import ltd.user.cloud.taoduoduo.utils.UserContextUtil;
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
        return "test";
    }

    @PostMapping("/register")
    @ApiOperation(value = "用户注册", notes = "")
    public Result register(@RequestBody @Valid RegisterParam mallUserRegisterParam) {

        String registerResult;
        try {
            registerResult = mallUserService.register(mallUserRegisterParam.getUsername(), mallUserRegisterParam.getPassword());
        } catch (Exception e) {
            logger.error("register api,loginName={},error={}", mallUserRegisterParam.getUsername(), e.getStackTrace());
            return ResultGenerator.genFailResult("注册失败");
        }

        logger.info("register api,loginName={},registerResult={}", mallUserRegisterParam.getUsername(), registerResult);

        if (registerResult.equals(ServiceResultEnum.SUCCESS.getResult())) {
            return genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(registerResult);
        }

    }

    @PostMapping("/logout")
    @ApiOperation(value="登出接口", notes="清除token")
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
    public Result passwordUpdate(@RequestBody @Valid ChangePasswordParam adminPasswordParam) {
        logger.info("adminUser:{}", UserContextUtil.getUser());

        boolean updateResult;
        try{
            updateResult = mallUserService.changePassword(adminPasswordParam.getOldPassword(), adminPasswordParam.getNewPassword());
        } catch (Exception e) {
            logger.error("update password error, adminUserId={}, error={}", UserContextUtil.getUser().getUserId(), e.getMessage());
            return ResultGenerator.genFailResult("修改密码失败");
        }
        if (updateResult) {
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult("DB ERROR");
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改用户信息", notes = "")
    public Result update(@RequestBody @ApiParam("用户信息") UserUpdateParam mallUseUpdateParam) {

        try {
            Boolean updateResult = mallUserService.updateUserInfo(mallUseUpdateParam);
            logger.info("update api,loginName={},updateResult={}", mallUseUpdateParam.getUsername(), updateResult);

            if (Boolean.TRUE.equals(updateResult)) {
                return genSuccessResult();
            } else {
                return ResultGenerator.genFailResult("修改失败");
            }
        } catch (Exception e) {
            logger.error("update api,loginName={},error={}", mallUseUpdateParam.getUsername(), e.getStackTrace());
            return ResultGenerator.genFailResult("修改失败");
        }
    }

    @GetMapping("/detail")
    @ApiOperation(value = "获取用户信息", notes = "")
    public Result getUserDetail() {
        MallUserVO userVO= new MallUserVO();
        User user;
        try{
            user = mallUserService.getUserDetail();
        }catch (Exception e){
            logger.error("getUserDetail api,loginName={},error={}", UserContextUtil.getUser().getUserId(), e.getStackTrace());
            return ResultGenerator.genFailResult("获取用户信息失败");
        }
        BeanUtil.copyProperties(user, userVO);
        return ResultGenerator.genSuccessResult(userVO);
    }

    @GetMapping("/{token}")
    @ApiOperation(value = "根据token获取用户信息", notes = "")
    public Result getUserDetailByToken(@PathVariable("token") String mallUserToken) {
        User user;
        try{
            user = mallUserService.getUserDetail();
        }catch (Exception e){
            logger.error("getUserDetail api,loginName={},error={}", mallUserToken, e.getStackTrace());
            return ResultGenerator.genFailResult("获取用户信息失败");
        }
        return ResultGenerator.genSuccessResult(user);
    }
}
