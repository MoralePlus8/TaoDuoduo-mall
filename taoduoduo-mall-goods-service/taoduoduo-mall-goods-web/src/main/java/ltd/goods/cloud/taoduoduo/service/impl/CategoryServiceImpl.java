package ltd.goods.cloud.taoduoduo.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.page.PageMethod;
import lombok.RequiredArgsConstructor;
import ltd.common.cloud.taoduoduo.dto.PageResult;
import ltd.common.cloud.taoduoduo.enums.CategoryLevelEnum;
import ltd.common.cloud.taoduoduo.enums.ServiceResultEnum;
import ltd.common.cloud.taoduoduo.exception.DataBaseErrorException;
import ltd.common.cloud.taoduoduo.exception.DataNotExistException;
import ltd.common.cloud.taoduoduo.exception.ParamErrorException;
import ltd.common.cloud.taoduoduo.exception.SameCategoryExistException;
import ltd.goods.cloud.taoduoduo.dto.BatchIdDTO;
import ltd.goods.cloud.taoduoduo.dto.CategoryPageQueryDTO;
import ltd.goods.cloud.taoduoduo.entity.Category;
import ltd.goods.cloud.taoduoduo.mapper.CategoryMapper;
import ltd.goods.cloud.taoduoduo.service.CategoryService;
import ltd.goods.cloud.taoduoduo.vo.CategoryVO;
import ltd.goods.cloud.taoduoduo.vo.SecondLevelCategoryVO;
import ltd.goods.cloud.taoduoduo.vo.ThirdLevelCategoryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    private Category selectByLevelAndName(Category category) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Category.TableAttributes.CATEGORY_NAME, category.getCategoryName());
        queryWrapper.eq(Category.TableAttributes.CATEGORY_LEVEL, category.getCategoryLevel());
        queryWrapper.eq(Category.TableAttributes.IS_DELETED, 0);
        return categoryMapper.selectOne(queryWrapper);
    }

    @Override
    public String save(Category category) {

        Category existingCategory = selectByLevelAndName(category);
        if (existingCategory != null) {
            throw new SameCategoryExistException();
        }

        if (categoryMapper.insert(category) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }

        throw new DataBaseErrorException();
    }

    @Override
    public void update(Category category) {
        Category existingCategory = categoryMapper.selectById(category.getCategoryId());
        if (existingCategory == null) {
            throw new DataNotExistException();
        }

        /* 当前存在同名但id不同的分类 */
        Category duplicateCategory = selectByLevelAndName(category);
        if (duplicateCategory != null && !duplicateCategory.getCategoryId().equals(category.getCategoryId())) {
            throw new SameCategoryExistException();
        }

        category.setUpdateTime(new Date());
        if (categoryMapper.updateById(category) > 0) {
            return;
        }

        throw new DataBaseErrorException();
    }

    @Override
    public Category getCategoryById(Long id) {
        Category category = categoryMapper.selectById(id);
        if (category == null) {
            throw new DataNotExistException();
        }

        return category;
    }

    @Override
    public PageResult<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageMethod.startPage(categoryPageQueryDTO.getPageNumber(), categoryPageQueryDTO.getPageSize());
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        if (categoryPageQueryDTO.getCategoryLevel() != null && categoryPageQueryDTO.getCategoryLevel() > 0) {
            queryWrapper.eq(Category.TableAttributes.CATEGORY_LEVEL, categoryPageQueryDTO.getCategoryLevel());
        }
        if (categoryPageQueryDTO.getParentId() != null && categoryPageQueryDTO.getParentId() > 0) {
            queryWrapper.eq(Category.TableAttributes.PARENT_ID, categoryPageQueryDTO.getParentId());
        }

        List<Category> page = categoryMapper.selectList(queryWrapper);
        return new PageResult<>(page, page.size(), categoryPageQueryDTO.getPageSize(), categoryPageQueryDTO.getPageNumber());
    }

    @Override
    public void deleteBatch(BatchIdDTO batchIdDTO) {
        if (batchIdDTO == null || batchIdDTO.getIds().isEmpty()) {
            throw new ParamErrorException();
        }

        categoryMapper.deleteBatchIds(batchIdDTO.getIds());
        throw new DataBaseErrorException();
    }

    private List<Category> findByLevelAndParentIdsAndNumber(List<Long> parentIds, Integer level, Integer number) {

        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(Category.TableAttributes.PARENT_ID, parentIds);
        queryWrapper.eq(Category.TableAttributes.CATEGORY_LEVEL, level);
        queryWrapper.eq(Category.TableAttributes.IS_DELETED, 0);
        queryWrapper.orderByDesc(Category.TableAttributes.CATEGORY_RANK);
        if(number != null) queryWrapper.last("LIMIT " + number);

        return categoryMapper.selectList(queryWrapper);
    }

    @Override
    public List<CategoryVO> getCategoriesForIndex() {
        List<CategoryVO> categoryVOS = new ArrayList<>();

        /* 获取一级分类 10 条数据 */
        List<Category> firstLevelCategories = findByLevelAndParentIdsAndNumber(
                Collections.singletonList(0L),
                CategoryLevelEnum.LEVEL_FIRST.getLevel(),
                10
        );

        for(Category firstLevelCategory : firstLevelCategories) {
            CategoryVO categoryVO = new CategoryVO();
            BeanUtils.copyProperties(firstLevelCategory, categoryVO);
            categoryVOS.add(categoryVO);

            /* 获取当前一级分类下的所有二级分类数据 */
            List<Category> secondLevelCategories = findByLevelAndParentIdsAndNumber(
                    Collections.singletonList(firstLevelCategory.getCategoryId()),
                    CategoryLevelEnum.LEVEL_SECOND.getLevel(),
                    null
            );

            List<SecondLevelCategoryVO> secondLevelCategoryVOS = new ArrayList<>();
            for (Category secondLevelCategory : secondLevelCategories) {
                SecondLevelCategoryVO secondLevelCategoryVO = new SecondLevelCategoryVO();
                BeanUtils.copyProperties(secondLevelCategory, secondLevelCategoryVO);
                secondLevelCategoryVOS.add(secondLevelCategoryVO);

                /* 获取当前二级分类下的所有三级分类数据 */
                List<Category> thirdLevelCategories = findByLevelAndParentIdsAndNumber(
                        Collections.singletonList(secondLevelCategory.getCategoryId()),
                        CategoryLevelEnum.LEVEL_THIRD.getLevel(),
                        null
                );

                List<ThirdLevelCategoryVO> thirdLevelCategoryVOS = new ArrayList<>();
                for (Category thirdLevelCategory : thirdLevelCategories) {
                    ThirdLevelCategoryVO thirdLevelCategoryVO = new ThirdLevelCategoryVO();
                    BeanUtils.copyProperties(thirdLevelCategory, thirdLevelCategoryVO);
                    thirdLevelCategoryVOS.add(thirdLevelCategoryVO);
                }
                secondLevelCategoryVO.setThirdLevelCategoryVOS(thirdLevelCategoryVOS);
            }
            categoryVO.setSecondLevelCategoryVOS(secondLevelCategoryVOS);
        }

        return categoryVOS;
    }

}
