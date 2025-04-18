package ltd.goods.cloud.taoduoduo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import ltd.common.cloud.taoduoduo.dto.PageResult;
import ltd.common.cloud.taoduoduo.dto.Result;
import ltd.common.cloud.taoduoduo.dto.ResultGenerator;
import ltd.goods.cloud.taoduoduo.dto.CategoryPageQueryRequest;
import ltd.goods.cloud.taoduoduo.dto.CategorySaveRequest;
import ltd.goods.cloud.taoduoduo.dto.CategoryUpdateRequest;
import ltd.goods.cloud.taoduoduo.entity.Category;
import ltd.goods.cloud.taoduoduo.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/goods/category/admin")
@Api(value = "v1", tags = "后台管理系统分类模块接口")
public class CategoryAdminController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CategoryService categoryService;

    @PostMapping("/add")
    @ApiOperation(value = "新增分类", notes = "新增分类")
    public Result save(@RequestBody @Valid CategorySaveRequest categorySaveRequest) {
        logger.info("Add new category: {}", categorySaveRequest);

        Category category = new Category();
        BeanUtils.copyProperties(categorySaveRequest, category);
        categoryService.save(category);

        return ResultGenerator.genSuccessResult();
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改分类信息", notes = "修改分类信息")
    public Result update(@RequestBody @Valid CategoryUpdateRequest categoryUpdateRequest) {
        logger.info("Update category: {}", categoryUpdateRequest);

        Category category = new Category();
        BeanUtils.copyProperties(categoryUpdateRequest, category);
        categoryService.update(category);

        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/detail/{id}")
    @ApiOperation(value = "获取单条分类信息", notes = "根据id查询")
    public Result detail(@PathVariable("id") Long id) {
        logger.info("Get detailed category: {}", id);

        Category category = categoryService.getCategoryById(id);

        return ResultGenerator.genSuccessResult(category);
    }

    @GetMapping("/list")
    @ApiOperation(value = "商品分类列表", notes = "根据分类级别和上一级分类的id查询")

    public Result list(@RequestBody @Valid CategoryPageQueryRequest categoryPageQueryRequest) {
        logger.info("Get the list of categories: {}", categoryPageQueryRequest);

        PageResult<Category> pageResult = categoryService.pageQuery(categoryPageQueryRequest);

        return ResultGenerator.genSuccessResult(pageResult);
    }

    @DeleteMapping("/batchDelete")
    @ApiOperation(value = "批量删除分类信息", notes = "批量删除分类信息")
    public Result delete(@RequestBody List<Long> categoryIds) {
        logger.info("Delete categories: {}", categoryIds);

        categoryService.deleteBatch(categoryIds);

        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/list4Select")
    @ApiOperation(value = "商品分类列表", notes = "用于三级分类联动效果制作")
    public Result listForSelect() {
        return null;
    }
}
