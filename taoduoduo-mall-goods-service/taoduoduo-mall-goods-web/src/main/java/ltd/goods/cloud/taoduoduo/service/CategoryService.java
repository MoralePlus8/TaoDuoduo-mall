package ltd.goods.cloud.taoduoduo.service;


import ltd.common.cloud.taoduoduo.dto.PageResult;
import ltd.goods.cloud.taoduoduo.dto.CategoryPageQueryRequest;
import ltd.common.cloud.taoduoduo.entity.Category;
import ltd.goods.cloud.taoduoduo.vo.CategoryVO;

import javax.validation.Valid;
import java.util.List;

public interface CategoryService {

    String save(Category category);

    void update(Category category);

    Category getCategoryById(Long id);

    PageResult<Category> pageQuery(@Valid CategoryPageQueryRequest categoryPageQueryRequest);

    void deleteBatch(List<Long> categoryIds);

    List<CategoryVO> getCategoriesForIndex();
}
