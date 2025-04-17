package ltd.goods.cloud.taoduoduo.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import ltd.common.cloud.taoduoduo.dto.Result;
import ltd.common.cloud.taoduoduo.dto.ResultGenerator;
import ltd.goods.cloud.taoduoduo.service.CategoryService;
import ltd.goods.cloud.taoduoduo.vo.CategoryVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category/")
@RequiredArgsConstructor
@Api(value = "v1", tags = "淘多多商城分类页面接口")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/listAll")
    public Result getCategories() {
        List<CategoryVO> categories = categoryService.getCategoriesForIndex();

        return ResultGenerator.genSuccessResult(categories);
    }
}
