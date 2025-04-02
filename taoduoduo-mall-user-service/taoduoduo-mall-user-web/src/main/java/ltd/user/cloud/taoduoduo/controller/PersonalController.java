package ltd.user.cloud.taoduoduo.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import ltd.user.cloud.taoduoduo.service.MallUserService;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;

@Api(value = "v1", tags = "管理员操作接口")
@RestController
@RequiredArgsConstructor
public class PersonalController {

    private static final Logger logger = LoggerFactory.getLogger(PersonalController.class);

    private final MallUserService mallUserService;

}
