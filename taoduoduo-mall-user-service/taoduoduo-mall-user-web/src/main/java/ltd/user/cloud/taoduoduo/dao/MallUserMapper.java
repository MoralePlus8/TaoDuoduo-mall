package ltd.user.cloud.taoduoduo.dao;

import ltd.common.cloud.taoduoduo.dto.PageQueryUtil;
import ltd.user.cloud.taoduoduo.entity.MallUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MallUserMapper {

    int deleteByPrimaryKey(Long userId);

    int insert(MallUser record);

    int insertSelective(MallUser record);

    MallUser selectByPrimaryKey(Long userId);

    MallUser selectByLoginName(String loginName);

    MallUser selectByLoginNameAndPasswd(@Param("loginName") String loginName, @Param("password") String password);

    int updateByPrimaryKeySelective(MallUser record);

    int updateByPrimaryKey(MallUser record);

    List<MallUser> findMallUserList(PageQueryUtil pageUtil);

    int getTotalMallUsers(PageQueryUtil pageUtil);

    int lockUserBatch(@Param("ids") Long[] ids, @Param("lockStatus") int lockStatus);

}
