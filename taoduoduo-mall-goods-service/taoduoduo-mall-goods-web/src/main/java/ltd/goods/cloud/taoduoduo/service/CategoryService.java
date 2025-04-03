package ltd.goods.cloud.taoduoduo.service;


import ltd.common.cloud.taoduoduo.dto.PageResult;
import ltd.goods.cloud.taoduoduo.dto.BatchIdDTO;
import ltd.goods.cloud.taoduoduo.dto.CategoryPageQueryDTO;
import ltd.goods.cloud.taoduoduo.entity.Category;

import javax.validation.Valid;

public interface CategoryService {

    String save(Category category);

    String update(Category category);

    Category getById(Long id);

    PageResult<Category> pageQuery(@Valid CategoryPageQueryDTO categoryPageQueryDTO);

    String deleteBatch(BatchIdDTO batchIdDTO);
}
