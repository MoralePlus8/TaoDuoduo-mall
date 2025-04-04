package ltd.goods.cloud.taoduoduo.service.impl;


import com.github.pagehelper.Page;
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

    @Override
    public String save(Category category) {
        Category existingCategory = categoryMapper.findByLevelAndName(category.getCategoryLevel(), category.getCategoryName());
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
        Category existingCategory = categoryMapper.findById(category.getCategoryId());
        if (existingCategory == null) {
            throw new DataNotExistException();
        }

        /* 当前存在同名但id不同的分类 */
        Category duplicateCategory = categoryMapper.findByLevelAndName(category.getCategoryLevel(), category.getCategoryName());
        if (duplicateCategory != null && !duplicateCategory.getCategoryId().equals(category.getCategoryId())) {
            throw new SameCategoryExistException();
        }

        category.setUpdateTime(new Date());
        if (categoryMapper.update(category) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }

        throw new DataBaseErrorException();
    }

    @Override
    public Category getCategoryById(Long id) {
        Category category = categoryMapper.findById(id);
        if (category == null) {
            throw new DataNotExistException();
        }

        return category;
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
            throw new ParamErrorException();
        }

        if (categoryMapper.deleteBatch(batchIdDTO.getIds()) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }

        throw new DataBaseErrorException();
    }

    @Override
    public List<CategoryVO> getCategoriesForIndex() {
        List<CategoryVO> categoryVOS = new ArrayList<>();

        /* 获取一级分类 10 条数据 */
        List<Category> firstLevelCategories = categoryMapper.findByLevelAndParentIdsAndNumber(
                Collections.singletonList(0L),
                CategoryLevelEnum.LEVEL_FIRST.getLevel(),
                10
        );
        if (!firstLevelCategories.isEmpty()) {
            List<Long> firstLevelCategoryIds = firstLevelCategories.stream()
                    .map(Category::getCategoryId)
                    .collect(Collectors.toList());
            /* 获取二级分类所有数据 */
            List<Category> secondLevelCategories = categoryMapper.findByLevelAndParentIdsAndNumber(
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
                List<Category> thirdLevelCategories = categoryMapper.findByLevelAndParentIdsAndNumber(
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
