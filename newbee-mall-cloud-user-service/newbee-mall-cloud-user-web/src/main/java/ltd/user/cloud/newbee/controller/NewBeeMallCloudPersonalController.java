package ltd.user.cloud.newbee.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import ltd.common.cloud.newbee.dto.Result;
import ltd.common.cloud.newbee.dto.ResultGenerator;
import ltd.common.cloud.newbee.enums.ServiceResultEnum;
import ltd.common.cloud.newbee.pojo.MallUserToken;
import ltd.common.cloud.newbee.util.NumberUtil;
import ltd.user.cloud.newbee.config.annotation.Token2MallUser;
import ltd.user.cloud.newbee.controller.param.MallUserLoginParam;
import ltd.user.cloud.newbee.service.NewBeeMallUserService;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;

import javax.validation.Valid;

@Api(value = "v1", tags = "管理员操作接口")
@RestController
@RequiredArgsConstructor
public class NewBeeMallCloudPersonalController {

    private static final Logger logger = LoggerFactory.getLogger(NewBeeMallCloudPersonalController.class);

    private final NewBeeMallUserService newBeeMallUserService;

    @PostMapping("/login")
    @ApiOperation(value = "登录接口", notes = "返回token")
    public Result login(@RequestBody @Valid MallUserLoginParam mallUserLoginParam) {
        if(!NumberUtil.isPhone(mallUserLoginParam.getLoginName())) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_NAME_IS_NOT_PHONE.getResult());
        }
        String loginResult = newBeeMallUserService.login(mallUserLoginParam.getLoginName(), mallUserLoginParam.getPasswordMd5());
        logger.info("login api,loginName={},loginResult={}", mallUserLoginParam.getLoginName(), loginResult);

        if(loginResult != null && loginResult.length() == 32) {
            Result result = ResultGenerator.genSuccessResult();
            result.setData(loginResult);
            return result;
        }

        return ResultGenerator.genFailResult(loginResult);
    }

    @PostMapping("/logout")
    @ApiOperation(value="登出接口", notes="清除token")
    public Result logout(@Token2MallUser MallUserToken mallUserToken) {
        Boolean logoutResult = newBeeMallUserService.logout(mallUserToken.getToken());
        logger.info("logout api,loginOutUser={},logoutResult={}", mallUserToken.getUserId(), logoutResult);

        if(Boolean.TRUE.equals(logoutResult)){
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("登出失败");
        }
    }



}
