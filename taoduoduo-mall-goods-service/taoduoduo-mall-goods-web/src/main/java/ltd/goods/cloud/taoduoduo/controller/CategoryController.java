package ltd.goods.cloud.taoduoduo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import ltd.common.cloud.taoduoduo.dto.PageResult;
import ltd.common.cloud.taoduoduo.dto.Result;
import ltd.common.cloud.taoduoduo.dto.ResultGenerator;
import ltd.common.cloud.taoduoduo.enums.ServiceResultEnum;
import ltd.goods.cloud.taoduoduo.dto.BatchIdDTO;
import ltd.goods.cloud.taoduoduo.dto.CategoryPageQueryDTO;
import ltd.goods.cloud.taoduoduo.dto.CategorySaveDTO;
import ltd.goods.cloud.taoduoduo.dto.CategoryUpdateDTO;
import ltd.goods.cloud.taoduoduo.entity.Category;
import ltd.goods.cloud.taoduoduo.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories/admin")
@Api(value = "v1", tags = "后台管理系统分类模块接口")
public class CategoryController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CategoryService categoryService;

    @PostMapping("/add")
    @ApiOperation(value = "新增分类", notes = "新增分类")
    public Result save(@RequestBody @Valid CategorySaveDTO categorySaveDTO) {
        logger.info("Newly added category: {}", categorySaveDTO);

        Category category = new Category();
        BeanUtils.copyProperties(categorySaveDTO, category);
        String result = categoryService.save(category);

        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改分类信息", notes = "修改分类信息")
    public Result update(@RequestBody @Valid CategoryUpdateDTO categoryUpdateDTO) {
        logger.info("Updated category: {}", categoryUpdateDTO);

        Category category = new Category();
        BeanUtils.copyProperties(categoryUpdateDTO, category);
        String result = categoryService.update(category);

        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    @GetMapping("/detail/{id}")
    @ApiOperation(value = "获取单条分类信息", notes = "根据id查询")
    public Result detail(@PathVariable("id") Long id) {
        logger.info("Get detailed category: {}", id);

        Category category = categoryService.getById(id);

        if (category == null) {
            return ResultGenerator.genFailResult(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        } else {
            return ResultGenerator.genSuccessResult(category);
        }
    }

    @GetMapping("/list")
    @ApiOperation(value = "商品分类列表", notes = "根据分类级别和上一级分类的id查询")
    /*todo: 将param修改为body，前端请求类型可能需要改 */
    public Result list(@RequestBody @Valid CategoryPageQueryDTO categoryPageQueryDTO) {
        logger.info("Get the list of categories");

        PageResult<Category> pageResult = categoryService.pageQuery(categoryPageQueryDTO);

        return ResultGenerator.genSuccessResult(pageResult);
    }

    @DeleteMapping("/batchDelete")
    @ApiOperation(value = "批量删除分类信息", notes = "批量删除分类信息")
    public Result delete(@RequestBody BatchIdDTO batchIdDTO) {
        logger.info("Delete categories: {}", batchIdDTO);

        String result = categoryService.deleteBatch(batchIdDTO);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    @GetMapping("/list4Select")
    @ApiOperation(value = "商品分类列表", notes = "用于三级分类联动效果制作")
    public Result listForSelect() {
        return null;
    }
}
