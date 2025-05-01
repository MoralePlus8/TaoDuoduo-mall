package ltd.goods.cloud.taoduoduo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import ltd.common.cloud.taoduoduo.dto.PageResult;
import ltd.common.cloud.taoduoduo.dto.Result;
import ltd.common.cloud.taoduoduo.dto.ResultGenerator;
import ltd.goods.cloud.taoduoduo.dto.GoodsPageQueryRequest;
import ltd.common.cloud.taoduoduo.entity.Goods;
import ltd.goods.cloud.taoduoduo.doc.GoodsDoc;
import ltd.goods.cloud.taoduoduo.service.GoodsService;
import ltd.goods.cloud.taoduoduo.vo.GoodsDetailVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/goods")
@RequiredArgsConstructor
@Api(value = "v1", tags = "淘多多商品相关接口")
public class GoodsController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final GoodsService goodsService;


    @GetMapping("/detail/{goodsId}")
    @ApiOperation(value = "商品详情", notes = "根据id查询")
    public Result detail(@PathVariable Long goodsId) {
        logger.info("Get goods detail: {}", goodsId);

        Goods goods = goodsService.getGoodsOnSale(goodsId);
        GoodsDetailVO goodsDetailVO = new GoodsDetailVO();
        BeanUtils.copyProperties(goods, goodsDetailVO);
        goodsDetailVO.setGoodsCarouselList(goods.getGoodsCarousel().split(","));
        
        return ResultGenerator.genSuccessResult(goodsDetailVO);
    }

    @GetMapping("/search")
    @ApiOperation(value = "商品列表", notes = "可根据名称和上架状态筛选")
    public Result list(@RequestBody @Valid GoodsPageQueryRequest goodsPageQueryRequest) {
        logger.info("Get the list of goods: {}", goodsPageQueryRequest);

        PageResult<GoodsDoc> pageResult = goodsService.pageQuery(goodsPageQueryRequest);

        return ResultGenerator.genSuccessResult(pageResult);
    }
}
