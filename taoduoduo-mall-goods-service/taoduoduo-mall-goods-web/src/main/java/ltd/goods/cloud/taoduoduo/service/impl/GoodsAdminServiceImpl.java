package ltd.goods.cloud.taoduoduo.service.impl;

import lombok.RequiredArgsConstructor;
import ltd.common.cloud.taoduoduo.enums.CategoryLevelEnum;
import ltd.common.cloud.taoduoduo.enums.ServiceResultEnum;
import ltd.goods.cloud.taoduoduo.entity.Category;
import ltd.goods.cloud.taoduoduo.entity.Goods;
import ltd.goods.cloud.taoduoduo.mapper.CategoryAdminMapper;
import ltd.goods.cloud.taoduoduo.mapper.GoodsAdminMapper;
import ltd.goods.cloud.taoduoduo.service.GoodsAdminService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoodsAdminServiceImpl implements GoodsAdminService {

    private final GoodsAdminMapper goodsAdminMapper;

    private final CategoryAdminMapper categoryAdminMapper;

    @Override
    public String save(Goods goods) {
        Category category = categoryAdminMapper.findById(goods.getGoodsCategoryId());

        /* 分类不存在或者不是第三级分类 */
        if (category == null || category.getCategoryLevel().intValue() != CategoryLevelEnum.LEVEL_THREE.getLevel()) {
            return ServiceResultEnum.GOODS_CATEGORY_ERROR.getResult();
        }

        /* 同级分类下已有同名商品 */
        if (goodsAdminMapper.findByNameAndCategoryId(goods.getGoodsName(), goods.getGoodsCategoryId()) != null) {
            return ServiceResultEnum.SAME_GOODS_EXIST.getResult();
        }

        if (goodsAdminMapper.save() > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }
}
