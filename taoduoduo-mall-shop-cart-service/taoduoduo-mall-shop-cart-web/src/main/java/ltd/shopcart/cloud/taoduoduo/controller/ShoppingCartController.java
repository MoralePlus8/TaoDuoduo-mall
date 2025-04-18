package ltd.shopcart.cloud.taoduoduo.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import ltd.common.cloud.taoduoduo.dto.PageResult;
import ltd.common.cloud.taoduoduo.dto.Result;
import ltd.common.cloud.taoduoduo.dto.ResultGenerator;
import ltd.common.cloud.taoduoduo.util.UserContextUtil;
import ltd.shopcart.cloud.taoduoduo.dto.CartItemRequest;
import ltd.shopcart.cloud.taoduoduo.entity.ShoppingCartItem;
import ltd.shopcart.cloud.taoduoduo.service.ShoppingCartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shopcart")
@Api(value = "v1", tags = "淘多多商城购物车相关接口")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    private static final Logger logger = LoggerFactory.getLogger(ShoppingCartController.class);

    @GetMapping("/page")
    @ApiOperation(value = "购物车列表(每页默认5条)", notes = "传参为页码")
    public Result pageList(Integer pageNumber) {
        PageResult<ShoppingCartItem> pageResult = shoppingCartService.pageQuery(pageNumber);

        return ResultGenerator.genSuccessResult(pageResult);
    }

    @PostMapping("/shop-cart")
    @ApiOperation(value = "添加商品到购物车接口", notes = "传参为商品id、数量")
    public Result save(@RequestBody CartItemRequest cartItemRequest) {
        logger.info("User {} add new shoppingCartItem: {}", UserContextUtil.getUserId(), cartItemRequest);

        shoppingCartService.save(cartItemRequest,UserContextUtil.getUserId());

        return ResultGenerator.genSuccessResult();
    }
}
