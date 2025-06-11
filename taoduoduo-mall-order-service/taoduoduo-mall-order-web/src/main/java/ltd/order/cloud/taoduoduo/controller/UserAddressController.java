package ltd.order.cloud.taoduoduo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import ltd.common.cloud.taoduoduo.dto.Result;
import ltd.common.cloud.taoduoduo.dto.ResultGenerator;
import ltd.common.cloud.taoduoduo.entity.Address;
import ltd.common.cloud.taoduoduo.util.UserContextUtil;
import ltd.order.cloud.taoduoduo.dto.AddressRequest;
import ltd.order.cloud.taoduoduo.service.AddressService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "v1", tags = "淘多多商城个人地址相关接口")
@RequestMapping("/order/address")
@RequiredArgsConstructor
public class UserAddressController {

    private final AddressService addressService;

    @GetMapping("")
    @ApiOperation(value = "我的收货地址列表", notes = "无传参")
    public Result getMyAddresses() {
        return ResultGenerator.genSuccessResult(addressService.getMyAddresses());
    }

    @PostMapping("/add")
    @ApiOperation(value = "添加地址", notes = "")
    public Result addAddress(@ApiParam(value = "地址信息") @RequestBody AddressRequest addressRequest) {
        Address address = new Address();
        BeanUtils.copyProperties(addressRequest, address);
        address.setUserId(UserContextUtil.getUserId());
        if(addressService.saveUserAddress(address)){
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("添加失败");
        }
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改地址", notes = "")
    public Result updateAddress(@ApiParam(value = "地址信息") @RequestBody AddressRequest addressRequest) {
        Address address = new Address();
        BeanUtils.copyProperties(addressRequest, address);
        if(addressService.updateMallUserAddress(address)){
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("修改失败");
        }
    }

    @GetMapping("/{addressId}")
    @ApiOperation(value = "根据地址ID获取地址信息", notes = "传参为地址ID")
    public Result getUserAddressById(@ApiParam(value = "地址ID") @PathVariable Long addressId) {
        Address address = addressService.getMallUserAddressById(addressId);
        return ResultGenerator.genSuccessResult(address);
    }

    @GetMapping("/default")
    @ApiOperation(value = "获取默认地址", notes = "无传参")
    public Result getDefaultAddress() {
        Address defaultAddress = addressService.getMyDefaultAddressByUserId();
        if (defaultAddress != null) {
            return ResultGenerator.genSuccessResult(defaultAddress);
        } else {
            return ResultGenerator.genFailResult("没有默认地址");
        }
    }

    @DeleteMapping("/{addressId}")
    @ApiOperation(value = "删除地址", notes = "传参为地址ID")
    public Result deleteAddress(@ApiParam(value = "地址ID") @PathVariable Long addressId) {
        if(addressService.deleteById(addressId)){
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("删除失败");
        }
    }

}
