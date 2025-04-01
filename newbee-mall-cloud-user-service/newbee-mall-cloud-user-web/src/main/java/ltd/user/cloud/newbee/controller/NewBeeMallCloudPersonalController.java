package ltd.user.cloud.newbee.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import ltd.user.cloud.newbee.service.NewBeeMallUserService;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;

@Api(value = "v1", tags = "管理员操作接口")
@RestController
@RequiredArgsConstructor
public class NewBeeMallCloudPersonalController {

    private static final Logger logger = LoggerFactory.getLogger(NewBeeMallCloudPersonalController.class);

    private final NewBeeMallUserService newBeeMallUserService;

}
