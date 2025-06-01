package ltd.order.cloud.taoduoduo.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import ltd.common.cloud.taoduoduo.dto.Result;
import ltd.common.cloud.taoduoduo.dto.ResultGenerator;
import ltd.common.cloud.taoduoduo.entity.Address;
import ltd.common.cloud.taoduoduo.util.UserContextUtil;
import ltd.order.cloud.taoduoduo.dto.CreateOrderRequest;
import ltd.order.cloud.taoduoduo.service.AddressService;
import ltd.order.cloud.taoduoduo.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class UserOrderController {

    private final OrderService orderService;

    private final AddressService addressService;

    @PostMapping("/create")
    @ApiOperation(value = "生成订单接口", notes = "传参为地址id和待结算的购物项id数组")
    public Result saveOrder(@ApiParam(value = "订单信息") @RequestBody CreateOrderRequest createOrderRequest) {
        if(createOrderRequest == null || createOrderRequest.getAddressId() == null || createOrderRequest.getCartItemIds() == null) {
            return ResultGenerator.genFailResult("参数错误");
        }
        if(createOrderRequest.getCartItemIds().isEmpty()){
            return ResultGenerator.genFailResult("购物车项不能为空");
        }
        Address address = addressService.getMallUserAddressById(createOrderRequest.getAddressId());
        if(!Objects.equals(address.getUserId(), UserContextUtil.getUserId())) {
            return ResultGenerator.genFailResult("地址不属于当前用户");
        }
        String orderNo = orderService.saveOrder(address.getAddressId(), createOrderRequest.getCartItemIds());
        return ResultGenerator.genSuccessResult(orderNo);
    }

    @GetMapping("/{orderNo}")
    @ApiOperation(value = "根据订单号获取订单信息", notes = "传参为订单号")
    public Result getOrderByOrderNo(@ApiParam(value = "订单号") @PathVariable String orderNo) {
        if(orderNo == null || orderNo.isEmpty()) {
            return ResultGenerator.genFailResult("订单号不能为空");
        }
        return ResultGenerator.genSuccessResult(orderService.getOrderDetailByOrderNo(orderNo));
    }

    @GetMapping("")
    @ApiOperation(value = "获取用户订单列表", notes = "传参为页码")
    public Result getUserOrders(@ApiParam(value = "页码") @RequestParam(required = false) Integer page,
                                @ApiParam(value = "查询的订单状态") @RequestParam(required = false) Integer status) {
        if (page == null || page < 1) {
            page = 1;
        }
        return ResultGenerator.genSuccessResult(orderService.getMyOrders(page, status));
    }

    @PutMapping("/{orderNo}/cancel")
    @ApiOperation(value = "取消订单", notes = "传参为订单号")
    public Result cancelOrder(@ApiParam(value = "订单号") @PathVariable String orderNo) {
        if (orderNo == null || orderNo.isEmpty()) {
            return ResultGenerator.genFailResult("订单号不能为空");
        }
        orderService.cancelOrder(orderNo);
        return ResultGenerator.genSuccessResult("取消订单失败");
    }

    @PutMapping("/{orderNo}/finish")
    @ApiOperation(value = "确认收货接口", notes = "传参为订单号")
    public Result finishOrder(@ApiParam(value = "订单号") @PathVariable String orderNo) {
        if (orderNo == null || orderNo.isEmpty()) {
            return ResultGenerator.genFailResult("订单号不能为空");
        }
        orderService.finishOrder(orderNo);
        return ResultGenerator.genSuccessResult("确认收货成功");
    }
    
    @GetMapping("/paySuccess")
    @ApiOperation(value = "支付成功回调接口", notes = "传参为订单号和支付方式")
    public Result paySuccess(@ApiParam(value = "订单号")  @RequestParam String orderNo,
                             @ApiParam(value = "支付类型") @RequestParam Integer payType) {
        if (orderNo == null || orderNo.isEmpty() || payType == null) {
            return ResultGenerator.genFailResult("参数错误");
        }
        orderService.paySuccess(orderNo, payType);
        return ResultGenerator.genSuccessResult("支付成功");
    }
}
