package ltd.order.cloud.taoduoduo.service;

import ltd.common.cloud.taoduoduo.entity.Address;

import java.util.List;

public interface AddressService {

    List<Address> getMyAddresses();

    Boolean saveUserAddress(Address address);

    Boolean updateMallUserAddress(Address address);

    Address getMallUserAddressById(Long addressId);

    Address getMyDefaultAddressByUserId();

    Boolean deleteById(Long addressId);
}
