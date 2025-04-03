package ltd.goods.cloud.taoduoduo.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import ltd.common.cloud.taoduoduo.dto.PageResult;
import ltd.common.cloud.taoduoduo.dto.Result;
import ltd.common.cloud.taoduoduo.dto.ResultGenerator;
import ltd.common.cloud.taoduoduo.enums.ServiceResultEnum;
import ltd.goods.cloud.taoduoduo.dto.*;
import ltd.goods.cloud.taoduoduo.entity.Category;
import ltd.goods.cloud.taoduoduo.entity.Goods;
import ltd.goods.cloud.taoduoduo.service.CategoryService;
import ltd.goods.cloud.taoduoduo.service.GoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/goods/admin")
@RequiredArgsConstructor
@Api(value = "v1", tags = "后台管理系统商品模块接口")
public class GoodsAdminController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final GoodsService goodsService;

    private final CategoryService categoryService;

    @PostMapping("/add")
    @ApiOperation(value = "新增商品", notes = "新增商品")
    public Result save(@RequestBody @Valid GoodsSaveDTO goodsSaveDTO) {
        logger.info("Add new goods: {}", goodsSaveDTO);

        Goods goods = new Goods();
        BeanUtils.copyProperties(goodsSaveDTO, goods);
        String result = goodsService.save(goods);

        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改商品信息", notes = "修改商品信息")
    public Result update(@RequestBody @Valid GoodsUpdateDTO goodsUpdateDTO) {
        logger.info("Update goods: {}", goodsUpdateDTO);

        Goods goods = new Goods();
        BeanUtils.copyProperties(goodsUpdateDTO, goods);
        String result = goodsService.update(goods);

        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    @GetMapping("/detail/{id}")
    @ApiOperation(value = "获取单条商品信息", notes = "根据id查询")
    public Result detail(@PathVariable("id") Long id) {
        logger.info("Get goods detail: {}", id);

        Goods goods = goodsService.getById(id);
        if (goods == null) {
            return ResultGenerator.genFailResult(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }

        Map<String, Object> goodsDetail = new HashMap<>();
        goodsDetail.put("goods", goods);
        Category thirdCategory = categoryService.getById(goods.getGoodsCategoryId());
        if (thirdCategory != null) {
            goodsDetail.put("thirdCategory", thirdCategory);
            Category secondCategory = categoryService.getById(thirdCategory.getParentId());
            if (secondCategory != null) {
                goodsDetail.put("secondCategory", secondCategory);
                Category firstCategory = categoryService.getById(secondCategory.getParentId());
                if (firstCategory != null) {
                    goodsDetail.put("firstCategory", firstCategory);
                }
            }
        }
        return ResultGenerator.genSuccessResult(goodsDetail);
    }

    @GetMapping("/list")
    @ApiOperation(value = "商品列表", notes = "可根据名称和上架状态筛选")
    /*todo: 将param修改为body，前端请求类型可能需要改 */
    public Result list(@RequestBody @Valid GoodsPageQueryDTO goodsPageQueryDTO) {
        logger.info("Get the list of goods: {}", goodsPageQueryDTO);

        PageResult<Goods> pageResult = goodsService.pageQuery(goodsPageQueryDTO);

        return ResultGenerator.genSuccessResult(pageResult);
    }

    @GetMapping("/listByGoodsIds")
    @ApiOperation(value = "根据ids查询商品列表", notes = "根据ids查询")
    public Result listByIds(@RequestParam("goodsIds") List<Long> goodsIds) {
        List<Goods> goodsList = goodsService.getByIds(goodsIds);

        return ResultGenerator.genSuccessResult(goodsList);
    }

    @PutMapping("/updateStatus/{sellStatus}")
    @ApiOperation(value = "批量修改销售状态", notes = "批量修改销售状态")
    public Result updateStatus(@RequestBody BatchIdDTO batchIdDTO, @PathVariable("sellStatus") Integer sellStatus) {
        logger.info("Update the status of goods to {}: {}", sellStatus, batchIdDTO);

        String result = goodsService.batchUpdateSellStatus(batchIdDTO, sellStatus);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    @PutMapping("/updateStock")
    @ApiOperation(value = "批量修改库存", notes = "批量修改库存")
    public Result updateStock(@RequestBody StockNumUpdateDTO stockNumUpdateDTO) {
        logger.info("Update stock: {}", stockNumUpdateDTO);

        String result = goodsService.updateStock(stockNumUpdateDTO);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }
}
