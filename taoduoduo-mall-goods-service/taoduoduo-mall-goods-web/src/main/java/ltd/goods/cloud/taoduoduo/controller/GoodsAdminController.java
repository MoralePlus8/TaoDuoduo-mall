package ltd.goods.cloud.taoduoduo.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import ltd.common.cloud.taoduoduo.dto.Result;
import ltd.common.cloud.taoduoduo.dto.ResultGenerator;
import ltd.common.cloud.taoduoduo.enums.ServiceResultEnum;
import ltd.goods.cloud.taoduoduo.dto.GoodsSaveDTO;
import ltd.goods.cloud.taoduoduo.entity.Goods;
import ltd.goods.cloud.taoduoduo.service.GoodsAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/goods/admin")
@RequiredArgsConstructor
@Api(value = "v1", tags = "后台管理系统商品模块接口")
public class GoodsAdminController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final GoodsAdminService goodsAdminService;

    @PostMapping("/add")
    @ApiOperation(value = "新增商品", notes = "新增商品")
    public Result save(@RequestBody @Valid GoodsSaveDTO goodsSaveDTO) {
        logger.info("Add new goods: {}", goodsSaveDTO);

        Goods goods = new Goods();
        BeanUtils.copyProperties(goodsSaveDTO, goods);
        String result = goodsAdminService.save(goods);

        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }
}
