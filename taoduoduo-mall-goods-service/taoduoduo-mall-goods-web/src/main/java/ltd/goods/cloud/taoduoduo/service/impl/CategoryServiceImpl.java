package ltd.goods.cloud.taoduoduo.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.page.PageMethod;
import lombok.RequiredArgsConstructor;
import ltd.common.cloud.taoduoduo.dto.PageResult;
import ltd.common.cloud.taoduoduo.dto.ResultGenerator;
import ltd.common.cloud.taoduoduo.enums.ServiceResultEnum;
import ltd.goods.cloud.taoduoduo.dto.BatchIdDTO;
import ltd.goods.cloud.taoduoduo.dto.CategoryPageQueryDTO;
import ltd.goods.cloud.taoduoduo.entity.Category;
import ltd.goods.cloud.taoduoduo.mapper.CategoryMapper;
import ltd.goods.cloud.taoduoduo.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    @Override
    public String save(Category category) {
        Category existingCategory = categoryMapper.findByLevelAndName(category.getCategoryLevel(), category.getCategoryName());
        if (existingCategory != null) {
            return ServiceResultEnum.SAME_CATEGORY_EXIST.getResult();
        }

        if (categoryMapper.insert(category) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }

        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String update(Category category) {
        Category existingCategory = categoryMapper.findById(category.getCategoryId());
        if (existingCategory == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }

        /* 当前存在同名但id不同的分类 */
        Category duplicateCategory = categoryMapper.findByLevelAndName(category.getCategoryLevel(), category.getCategoryName());
        if (duplicateCategory != null && !duplicateCategory.getCategoryId().equals(category.getCategoryId())) {
            return ServiceResultEnum.SAME_CATEGORY_EXIST.getResult();
        }

        category.setUpdateTime(new Date());
        if (categoryMapper.update(category) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }

        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public Category getById(Long id) {
        return categoryMapper.findById(id);
    }

    @Override
    public PageResult<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageMethod.startPage(categoryPageQueryDTO.getPageNumber(), categoryPageQueryDTO.getPageSize());
        Page<Category> page = categoryMapper.pageQuery(categoryPageQueryDTO);

        return new PageResult<>(page.getResult(), page.getTotal(), page.getPageSize(), page.getPageNum());
    }

    @Override
    public String deleteBatch(BatchIdDTO batchIdDTO) {
        if (batchIdDTO == null || batchIdDTO.getIds().length == 0) {
            return ServiceResultEnum.PARAM_ERROR.getResult();
        }

        if (categoryMapper.deleteBatch(batchIdDTO.getIds()) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }

        return ServiceResultEnum.DELETE_FAILED_ERROR.getResult();
    }

}
