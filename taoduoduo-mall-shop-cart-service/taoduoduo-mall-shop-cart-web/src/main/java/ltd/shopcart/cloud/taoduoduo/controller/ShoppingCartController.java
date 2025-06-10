package ltd.shopcart.cloud.taoduoduo.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import ltd.common.cloud.taoduoduo.dto.PageResult;
import ltd.common.cloud.taoduoduo.dto.Result;
import ltd.common.cloud.taoduoduo.dto.ResultGenerator;
import ltd.common.cloud.taoduoduo.util.UserContextUtil;
import ltd.shopcart.cloud.taoduoduo.dto.CartItemRequest;
import ltd.common.cloud.taoduoduo.entity.ShoppingCartItem;
import ltd.shopcart.cloud.taoduoduo.service.ShoppingCartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shopcart")
@Api(value = "v1", tags = "淘多多商城购物车相关接口")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    private static final Logger logger = LoggerFactory.getLogger(ShoppingCartController.class);

    @GetMapping("/page")
    @ApiOperation(value = "购物车列表(每页默认5条)", notes = "传参为页码")
    public Result pageList(@RequestParam Integer pageNumber) {
        PageResult<ShoppingCartItem> pageResult = shoppingCartService.pageQuery(pageNumber);

        return ResultGenerator.genSuccessResult(pageResult);
    }

    @PostMapping("/add")
    @ApiOperation(value = "添加商品到购物车接口", notes = "传参为商品id、数量")
    public Result save(@RequestBody CartItemRequest cartItemRequest) {
        logger.info("User {} add new shoppingCartItem: {}", UserContextUtil.getUserId(), cartItemRequest);

        shoppingCartService.save(cartItemRequest);

        return ResultGenerator.genSuccessResult();
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改购物项数据", notes = "传参为购物项id、数量")
    public Result update(@RequestBody CartItemRequest cartItemRequest) {
        logger.info("User {} update shoppingCartItem: {}", UserContextUtil.getUserId(), cartItemRequest);

        shoppingCartService.update(cartItemRequest);

        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/delete/{cartItemId}")
    @ApiOperation(value = "删除购物项", notes = "传参为购物项id")
    public Result delete(@PathVariable Long cartItemId) {
        logger.info("User {} delete shoppingCartItem: {}", UserContextUtil.getUserId(), cartItemId);

        shoppingCartService.deleteById(cartItemId);

        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/batchDelete")
    @ApiOperation(value = "批量删除购物项")
    public Result batchDelete(@RequestBody List<Long> cartItemIds) {
        logger.info("User {} batch delete shoppingCartItem: {}", UserContextUtil.getUserId(), cartItemIds);

        shoppingCartService.deleteByIds(cartItemIds);

        return ResultGenerator.genSuccessResult();
    }


    @GetMapping("/settle")
    @ApiOperation(value = "根据购物项id数组查询购物项明细", notes = "确认订单页面使用")
    public Result toSettle(@RequestBody List<Long> cartItemIds) {
        logger.info("User {} to settle shoppingCartItem: {}", UserContextUtil.getUserId(), cartItemIds);

        List<ShoppingCartItem> shoppingCartItems = shoppingCartService.getCartItemsForSettle(cartItemIds);

        return ResultGenerator.genSuccessResult(shoppingCartItems);
    }

    @GetMapping("/list")
    @ApiOperation(value = "根据购物项id数组查询购物车列表")
    public Result getCartItemsByIds(@RequestBody List<Long> cartItemIds) {
        logger.info("User {} get shoppingCartItem: {}", UserContextUtil.getUserId(), cartItemIds);

        List<ShoppingCartItem> shoppingCartItems = shoppingCartService.getCartItemsByIds(cartItemIds);

        return ResultGenerator.genSuccessResult(shoppingCartItems);
    }

    @GetMapping("/mycart")
    @ApiOperation(value = "获取当前用户的购物车列表")
    public Result getMyCartItems() {
        logger.info("User {} get my shoppingCartItem", UserContextUtil.getUserId());

        List<ShoppingCartItem> shoppingCartItems = shoppingCartService.getMyCartItems();

        return ResultGenerator.genSuccessResult(shoppingCartItems);
    }

}
