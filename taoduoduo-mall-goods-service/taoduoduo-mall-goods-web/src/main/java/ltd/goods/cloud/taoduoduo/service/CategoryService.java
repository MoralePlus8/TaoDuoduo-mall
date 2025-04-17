package ltd.goods.cloud.taoduoduo.service;


import ltd.common.cloud.taoduoduo.dto.PageResult;
import ltd.goods.cloud.taoduoduo.dto.BatchIdDTO;
import ltd.goods.cloud.taoduoduo.dto.CategoryPageQueryDTO;
import ltd.goods.cloud.taoduoduo.entity.Category;
import ltd.goods.cloud.taoduoduo.vo.CategoryVO;

import javax.validation.Valid;
import java.util.List;

public interface CategoryService {

    String save(Category category);

    void update(Category category);

    Category getCategoryById(Long id);

    PageResult<Category> pageQuery(@Valid CategoryPageQueryDTO categoryPageQueryDTO);

    void deleteBatch(BatchIdDTO batchIdDTO);

    List<CategoryVO> getCategoriesForIndex();
}
