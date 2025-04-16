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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    private Category selectByLevelAndName(Category category) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_level", category.getCategoryLevel());
        queryWrapper.eq("category_name", category.getCategoryName());
        queryWrapper.eq("is_deleted", 0);
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
    public String update(Category category) {
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
            return ServiceResultEnum.SUCCESS.getResult();
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
            queryWrapper.eq("category_level", categoryPageQueryDTO.getCategoryLevel());
        }
        if (categoryPageQueryDTO.getParentId() != null && categoryPageQueryDTO.getParentId() > 0) {
            queryWrapper.eq("parent_id", categoryPageQueryDTO.getParentId());
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
        queryWrapper.in("parent_id", parentIds);
        queryWrapper.eq("category_level", level);
        queryWrapper.eq("is_deleted", 0);
        queryWrapper.orderByDesc("category_rank");
        queryWrapper.last("LIMIT " + number);

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
        if (!firstLevelCategories.isEmpty()) {
            List<Long> firstLevelCategoryIds = firstLevelCategories.stream()
                    .map(Category::getCategoryId)
                    .collect(Collectors.toList());
            /* 获取二级分类所有数据 */
            List<Category> secondLevelCategories = findByLevelAndParentIdsAndNumber(
                    firstLevelCategoryIds,
                    CategoryLevelEnum.LEVEL_SECOND.getLevel(),
                    0
            );
            if (!secondLevelCategories.isEmpty()) {
                List<Long> secondLevelCategoryIds = secondLevelCategories
                        .stream()
                        .map(Category::getCategoryId)
                        .collect(Collectors.toList());
                /* 获取三级分类所有数据 */
                List<Category> thirdLevelCategories = findByLevelAndParentIdsAndNumber(
                        secondLevelCategoryIds,
                        CategoryLevelEnum.LEVEL_THIRD.getLevel(),
                        0
                );
                if (!thirdLevelCategories.isEmpty()) {
                    /* 根据 parentId 将三级分类分组 */
                    Map<Long, List<Category>> thirdLevelCategoryMap = thirdLevelCategories
                            .stream()
                            .collect(Collectors.groupingBy(Category::getParentId));
                    List<SecondLevelCategoryVO> secondLevelCategoryVOS = new ArrayList<>();

                    /* 处理二级分类 */
                    for (Category secondLevelCategory : secondLevelCategories) {
                        SecondLevelCategoryVO secondLevelCategoryVO = new SecondLevelCategoryVO();
                        BeanUtils.copyProperties(secondLevelCategory, secondLevelCategoryVO);

                        /* 如果该二级分类下有数据则放入 secondLevelCategoryVOS 中*/
                        if (thirdLevelCategoryMap.containsKey(secondLevelCategory.getCategoryId())) {
                            List<Category> tempCategories = thirdLevelCategoryMap.get(secondLevelCategory.getCategoryId());
                            List<ThirdLevelCategoryVO> thirdLevelCategoryVOS = new ArrayList<>();

                            /* 遍历集合，逐个拷贝元素 */
                            for (Category category : tempCategories) {
                                ThirdLevelCategoryVO vo = new ThirdLevelCategoryVO();
                                BeanUtils.copyProperties(category, vo);
                                thirdLevelCategoryVOS.add(vo);
                            }

                            secondLevelCategoryVO.setThirdLevelCategoryVOS(thirdLevelCategoryVOS);
                            secondLevelCategoryVOS.add(secondLevelCategoryVO);
                        }
                    }
                    /* 处理一级分类 */
                    if (!secondLevelCategoryVOS.isEmpty()) {
                        /* 根据 parentId 将二级分类分组 */
                        Map<Long, List<SecondLevelCategoryVO>> secondLevelCategoryVOMap = secondLevelCategoryVOS
                                .stream()
                                .collect(Collectors.groupingBy(SecondLevelCategoryVO::getParentId));
                        for (Category firstCategory : firstLevelCategories) {
                            CategoryVO categoryVO = new CategoryVO();
                            BeanUtils.copyProperties(firstCategory, categoryVO);

                            /* 如果该一级分类下有数据则放入 categoryVOS 中*/
                            if (secondLevelCategoryVOMap.containsKey(firstCategory.getCategoryId())) {
                                List<SecondLevelCategoryVO> tempCategories = secondLevelCategoryVOMap.get(firstCategory.getCategoryId());
                                categoryVO.setSecondLevelCategoryVOS(tempCategories);
                                categoryVOS.add(categoryVO);
                            }
                        }
                    }
                }
            }
        }

        if (categoryVOS.isEmpty()) {
            throw new DataNotExistException();
        }

        return categoryVOS;
    }

}
