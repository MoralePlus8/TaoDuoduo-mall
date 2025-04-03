package ltd.goods.cloud.taoduoduo.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.page.PageMethod;
import lombok.RequiredArgsConstructor;
import ltd.common.cloud.taoduoduo.dto.PageResult;
import ltd.common.cloud.taoduoduo.enums.ServiceResultEnum;
import ltd.goods.cloud.taoduoduo.dto.BatchIdDTO;
import ltd.goods.cloud.taoduoduo.dto.CategoryPageQueryDTO;
import ltd.goods.cloud.taoduoduo.entity.Category;
import ltd.goods.cloud.taoduoduo.mapper.CategoryAdminMapper;
import ltd.goods.cloud.taoduoduo.service.CategoryAdminService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class CategoryAdminServiceImpl implements CategoryAdminService {

    private final CategoryAdminMapper categoryAdminMapper;

    @Override
    public String save(Category category) {
        Category existingCategory = categoryAdminMapper.findByLevelAndName(category.getCategoryLevel(), category.getCategoryName());
        if (existingCategory != null) {
            return ServiceResultEnum.SAME_CATEGORY_EXIST.getResult();
        }

        if (categoryAdminMapper.insert(category) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }

        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String update(Category category) {
        Category existingCategory = categoryAdminMapper.findById(category.getCategoryId());
        if (existingCategory == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }

        /* 当前存在同名但id不同的分类 */
        Category duplicateCategory = categoryAdminMapper.findByLevelAndName(category.getCategoryLevel(), category.getCategoryName());
        if (duplicateCategory != null && !duplicateCategory.getCategoryId().equals(category.getCategoryId())) {
            return ServiceResultEnum.SAME_CATEGORY_EXIST.getResult();
        }

        category.setUpdateTime(new Date());
        if (categoryAdminMapper.update(category) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }

        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public Category getById(Long id) {
        return categoryAdminMapper.findById(id);
    }

    @Override
    public PageResult<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageMethod.startPage(categoryPageQueryDTO.getPageNumber(), categoryPageQueryDTO.getPageSize());
        Page<Category> page = categoryAdminMapper.pageQuery(categoryPageQueryDTO);

        return new PageResult<>(page.getResult(), page.getTotal(), page.getPageSize(), page.getPageNum());
    }

    @Override
    public String deleteBatch(BatchIdDTO batchIdDTO) {
        if (batchIdDTO == null || batchIdDTO.getIds().length == 0) {
            return ServiceResultEnum.PARAM_ERROR.getResult();
        }

        if (categoryAdminMapper.deleteBatch(batchIdDTO.getIds()) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }

        return ServiceResultEnum.DELETE_FAILED_ERROR.getResult();
    }

}
