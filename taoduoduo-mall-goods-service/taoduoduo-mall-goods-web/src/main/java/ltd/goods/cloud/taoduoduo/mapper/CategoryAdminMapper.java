package ltd.goods.cloud.taoduoduo.mapper;

import com.github.pagehelper.Page;
import ltd.goods.cloud.taoduoduo.dto.CategoryPageQueryDTO;
import ltd.goods.cloud.taoduoduo.entity.Category;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CategoryAdminMapper {

    @Select("SELECT * " +
            "FROM tb_newbee_mall_goods_category " +
            "WHERE category_name = #{categoryName} AND category_level = #{categoryLevel} AND is_deleted = 0 LIMIT 1")
    Category findByLevelAndName(Byte categoryLevel, String categoryName);

    @Select("SELECT * " +
            "FROM tb_newbee_mall_goods_category " +
            "WHERE category_id = {categoryId} AND is_deleted = 0")
    Category findById(Long categoryId);

    @Insert("INSERT INTO tb_newbee_mall_goods_category(category_level, parent_id, category_name, category_rank) " +
            "VALUES (#{categoryLevel}, #{parentId}, #{categoryName}, #{categoryRank})")
    int insert(Category category);

    int update(Category category);

    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    int deleteBatch(Long[] ids);
}
