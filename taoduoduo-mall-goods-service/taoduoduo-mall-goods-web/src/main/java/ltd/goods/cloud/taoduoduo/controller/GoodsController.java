package ltd.goods.cloud.taoduoduo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import ltd.common.cloud.taoduoduo.dto.Result;
import ltd.common.cloud.taoduoduo.dto.ResultGenerator;
import ltd.common.cloud.taoduoduo.enums.ServiceResultEnum;
import ltd.goods.cloud.taoduoduo.entity.Goods;
import ltd.goods.cloud.taoduoduo.service.GoodsService;
import ltd.goods.cloud.taoduoduo.vo.GoodsDetailVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/goods/mall")
@RequiredArgsConstructor
@Api(value = "v1", tags = "淘多多商品相关接口")
public class GoodsController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final GoodsService goodsService;

    public Result search() {
        return null;
    }

    @GetMapping("/deatil/{goodsId}")
    @ApiOperation(value = "商品详情", notes = "根据id查询")
    public Result detail(@PathVariable Long goodsId) {
        logger.info("Get goods detail: {}", goodsId);

        Goods goods = goodsService.getGoodsOnSale(goodsId);
        GoodsDetailVO goodsDetailVO = new GoodsDetailVO();
        BeanUtils.copyProperties(goods, goodsDetailVO);
        goodsDetailVO.setGoodsCarouselList(goods.getGoodsCarousel().split(","));
        
        return ResultGenerator.genSuccessResult(goodsDetailVO);
    }
}
