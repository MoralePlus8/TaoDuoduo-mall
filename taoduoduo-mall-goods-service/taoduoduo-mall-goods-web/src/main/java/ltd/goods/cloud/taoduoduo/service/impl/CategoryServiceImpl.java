package ltd.goods.cloud.taoduoduo.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.page.PageMethod;
import lombok.RequiredArgsConstructor;
import ltd.common.cloud.taoduoduo.dto.PageResult;
import ltd.common.cloud.taoduoduo.enums.ServiceResultEnum;
import ltd.common.cloud.taoduoduo.exception.DataBaseErrorException;
import ltd.common.cloud.taoduoduo.exception.DataNotExistException;
import ltd.common.cloud.taoduoduo.exception.ParamErrorException;
import ltd.common.cloud.taoduoduo.exception.SameCategoryExistException;
import ltd.goods.cloud.taoduoduo.dto.CategoryPageQueryDTO;
import ltd.goods.cloud.taoduoduo.entity.Category;
import ltd.goods.cloud.taoduoduo.mapper.CategoryMapper;
import ltd.goods.cloud.taoduoduo.service.CategoryService;
import ltd.goods.cloud.taoduoduo.vo.CategoryVO;
import ltd.goods.cloud.taoduoduo.vo.SecondLevelCategoryVO;
import ltd.goods.cloud.taoduoduo.vo.ThirdLevelCategoryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final RedisTemplate<String, Object> redisTemplate;

    private final CategoryMapper categoryMapper;

    @Value("${redis.path.category.level1}")
    private String level1Path;

    @Value("${redis.path.category.level2}")
    private String level2Path;

    @Value("${redis.path.category.level3}")
    private String level3Path;

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
    public void deleteBatch(List<Long> categoryIds) {
        if (categoryIds == null || categoryIds.isEmpty()) {
            throw new ParamErrorException();
        }
        categoryMapper.deleteBatchIds(categoryIds);
    }

    @Override
    public List<CategoryVO> getCategoriesForIndex() {

        Set<String> level1Keys = redisTemplate.keys(level1Path + "*");
        Set<String> level2Keys = redisTemplate.keys(level2Path + "*");
        Set<String> level3Keys = redisTemplate.keys(level3Path + "*");
        if(level1Keys == null || level2Keys == null || level3Keys == null) {
            throw new DataNotExistException();
        }

        List<Object> level1List = redisTemplate.opsForValue().multiGet(level1Keys);
        List<Object> level2List = redisTemplate.opsForValue().multiGet(level2Keys);
        List<Object> level3List = redisTemplate.opsForValue().multiGet(level3Keys);
        if(level1List == null || level2List == null || level3List == null) {
            throw new DataNotExistException();
        }

        List<CategoryVO> categoryVOS = level1List.stream().map(obj->{
            CategoryVO categoryVO = new CategoryVO();
            BeanUtils.copyProperties(obj, categoryVO);
            return categoryVO;
        }).collect(Collectors.toList());

        List<SecondLevelCategoryVO> secondLevelCategoryVOS = level2List.stream().map(obj->{
            SecondLevelCategoryVO secondLevelCategoryVO = new SecondLevelCategoryVO();
            BeanUtils.copyProperties(obj, secondLevelCategoryVO);
            return secondLevelCategoryVO;
        }).collect(Collectors.toList());

        List<ThirdLevelCategoryVO> thirdLevelCategoryVOS = level3List.stream().map(obj->{
            ThirdLevelCategoryVO thirdLevelCategoryVO = new ThirdLevelCategoryVO();
            BeanUtils.copyProperties(obj, thirdLevelCategoryVO);
            return thirdLevelCategoryVO;
        }).collect(Collectors.toList());

        Map<Long, List<ThirdLevelCategoryVO>> thirdLevelMap = thirdLevelCategoryVOS.stream()
                .collect(Collectors.groupingBy(ThirdLevelCategoryVO::getParentId));
        for(SecondLevelCategoryVO secondLevelCategoryVO:secondLevelCategoryVOS){
            List<ThirdLevelCategoryVO> thirdLevelCategories = thirdLevelMap.get(secondLevelCategoryVO.getCategoryId());
            if(thirdLevelCategories != null){
                secondLevelCategoryVO.setThirdLevelCategoryVOS(thirdLevelCategories);
            }
        }

        Map<Long, List<SecondLevelCategoryVO>> secondLevelMap = secondLevelCategoryVOS.stream()
                .collect(Collectors.groupingBy(SecondLevelCategoryVO::getParentId));
        for(CategoryVO categoryVO:categoryVOS){
            List<SecondLevelCategoryVO> secondLevelCategories = secondLevelMap.get(categoryVO.getCategoryId());
            if(secondLevelCategories != null){
                categoryVO.setSecondLevelCategoryVOS(secondLevelCategories);
            }
        }

        return categoryVOS;
    }

}
