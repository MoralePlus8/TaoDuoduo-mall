package ltd.user.cloud.taoduoduo.dao;

import ltd.user.cloud.taoduoduo.entity.AdminUser;

public interface AdminUserMapper {

    int insert(AdminUser record);

    int insertSelective(AdminUser record);

    AdminUser login(String userName, String password);

    AdminUser selectByPrimaryKey(Long adminUserId);

    int updateByPrimaryKeySelective(AdminUser record);

    int updateByPrimaryKey(AdminUser record);

}
