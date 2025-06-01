package ltd.order.cloud.taoduoduo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import ltd.common.cloud.taoduoduo.dto.Result;
import ltd.common.cloud.taoduoduo.dto.ResultGenerator;
import ltd.order.cloud.taoduoduo.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Api(value = "v1", tags = "后台管理系统订单相关接口")
@RequestMapping("/order/admin")
@RequiredArgsConstructor
public class AdminOrderController {

    private final OrderService orderService;

    @GetMapping("/list")
    @ApiOperation(value = "订单列表", notes = "根据订单号和订单状态筛选")
    public Result list(@ApiParam(value = "页码") @RequestParam(required = false) Integer pageNum,
                       @ApiParam(value = "页大小") @RequestParam(required = false) Integer pageSize,
                       @ApiParam(value = "订单号") @RequestParam(required = false) String orderNo,
                       @ApiParam(value = "订单状态") @RequestParam(required = false) Integer orderStatus) {
        if(pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if(pageSize == null || pageSize < 1) {
            pageSize = 5;
        }

        return ResultGenerator.genSuccessResult(orderService.getOrdersPage(pageNum, pageSize, orderNo, orderStatus));
    }

    @GetMapping("/detail/{orderId}")
    @ApiOperation(value = "订单详情", notes = "传参")
    public Result orderDetail(@ApiParam(value = "订单ID") @PathVariable Long orderId) {
        if(orderId == null || orderId <= 0) {
            return ResultGenerator.genFailResult("订单ID不能为空或无效");
        }
        return ResultGenerator.genSuccessResult(orderService.getOrderDetailByOrderId(orderId));
    }

    @PutMapping("/checkDone")
    @ApiOperation(value = "修改订单状态为配货成功", notes = "批量修改")
    public Result checkDone(@ApiParam(value = "订单ID列表") @RequestBody List<Long> orderIds) {
        if(orderIds == null || orderIds.isEmpty()) {
            return ResultGenerator.genFailResult("订单ID不能为空");
        }
        orderService.checkDone(orderIds);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping("/checkOut")
    @ApiOperation(value = "修改订单状态为已出库", notes = "批量修改")
    public Result checkOut(@ApiParam(value = "订单ID列表") @RequestBody List<Long> orderIds) {
        if(orderIds == null || orderIds.isEmpty()) {
            return ResultGenerator.genFailResult("订单ID不能为空");
        }
        orderService.checkOut(orderIds);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping("/close")
    @ApiOperation(value = "修改订单状态为商家关闭", notes = "批量修改")
    public Result close(@ApiParam(value = "订单ID列表") @RequestBody List<Long> orderIds) {
        if(orderIds == null || orderIds.isEmpty()) {
            return ResultGenerator.genFailResult("订单ID不能为空");
        }
        orderService.closeOrder(orderIds);
        return ResultGenerator.genSuccessResult();
    }
}
