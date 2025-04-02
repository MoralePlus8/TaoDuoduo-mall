package ltd.goods.cloud.taoduoduo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import ltd.common.cloud.taoduoduo.dto.Result;
import ltd.goods.cloud.taoduoduo.entity.Category;
import ltd.goods.cloud.taoduoduo.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories/admin")
@Api(value = "v1", tags = "后台管理系统分类模块接口")
public class CategoryController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CategoryService categoryService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "新增分类", notes = "新增分类")
    public Result save() {
        logger.info("");
        Category category = new Category();
        return null;
    }
}
