package ltd.order.cloud.taoduoduo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import ltd.common.cloud.taoduoduo.entity.Address;
import ltd.common.cloud.taoduoduo.exception.AddressNotExistException;
import ltd.common.cloud.taoduoduo.util.UserContextUtil;
import ltd.order.cloud.taoduoduo.mapper.AddressMapper;
import ltd.order.cloud.taoduoduo.service.AddressService;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressMapper addressMapper;

    @Override
    public List<Address> getMyAddresses() {
        return addressMapper.selectList(new QueryWrapper<Address>().eq(Address.TableAttributes.USER_ID, UserContextUtil.getUserId()));
    }

    @Override
    public Boolean saveUserAddress(Address address) {
        if(address.getDefaultFlag()){
            QueryWrapper<Address> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(Address.TableAttributes.DEFAULT_FLAG, 1);
            Address defaultAddress = addressMapper.selectOne(queryWrapper);
            if(defaultAddress != null) {
                defaultAddress.setDefaultFlag(false);
                defaultAddress.setUpdateTime(new Date());
                addressMapper.updateById(defaultAddress);
            }
        }
        return addressMapper.insert(address) > 0;
    }

    @Override
    public Boolean updateMallUserAddress(Address address) {
        Address tempAddress = getMallUserAddressById(address.getAddressId());
        if (address.getDefaultFlag()) {
            QueryWrapper<Address> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(Address.TableAttributes.DEFAULT_FLAG, 1);
            Address defaultAddress = addressMapper.selectOne(queryWrapper);
            if (defaultAddress != null && !defaultAddress.getAddressId().equals(address.getAddressId())) {
                defaultAddress.setDefaultFlag(false);
                defaultAddress.setUpdateTime(new Date());
                addressMapper.updateById(defaultAddress);
            }
        }
        tempAddress.setUpdateTime(new Date());
        return addressMapper.updateById(tempAddress) > 0;
    }

    @Override
    public Address getMallUserAddressById(Long addressId) {
        Address address = addressMapper.selectById(addressId);
        if (address == null) {
            throw new AddressNotExistException();
        }
        return address;
    }

    @Override
    public Address getMyDefaultAddressByUserId() {
        QueryWrapper<Address> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Address.TableAttributes.USER_ID, UserContextUtil.getUserId());
        queryWrapper.eq(Address.TableAttributes.DEFAULT_FLAG, true);
        return addressMapper.selectOne(queryWrapper);
    }

    @Override
    public Boolean deleteById(Long addressId) {
        return addressMapper.deleteById(addressId) > 0;
    }
}
