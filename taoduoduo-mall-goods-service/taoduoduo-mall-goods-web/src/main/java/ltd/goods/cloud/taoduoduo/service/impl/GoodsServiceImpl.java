package ltd.goods.cloud.taoduoduo.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.page.PageMethod;
import lombok.RequiredArgsConstructor;
import ltd.common.cloud.taoduoduo.dto.PageResult;
import ltd.common.cloud.taoduoduo.enums.CategoryLevelEnum;
import ltd.common.cloud.taoduoduo.enums.ServiceResultEnum;
import ltd.goods.cloud.taoduoduo.dto.BatchIdDTO;
import ltd.goods.cloud.taoduoduo.dto.GoodsPageQueryDTO;
import ltd.goods.cloud.taoduoduo.dto.StockNumUpdateDTO;
import ltd.goods.cloud.taoduoduo.entity.Category;
import ltd.goods.cloud.taoduoduo.entity.Goods;
import ltd.goods.cloud.taoduoduo.mapper.CategoryMapper;
import ltd.goods.cloud.taoduoduo.mapper.GoodsMapper;
import ltd.goods.cloud.taoduoduo.service.GoodsService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoodsServiceImpl implements GoodsService {

    private final GoodsMapper goodsMapper;

    private final CategoryMapper categoryMapper;

    @Override
    public String save(Goods goods) {
        Category category = categoryMapper.findById(goods.getGoodsCategoryId());
        /* 分类不存在或者不是第三级分类 */
        if (category == null || category.getCategoryLevel().intValue() != CategoryLevelEnum.LEVEL_THREE.getLevel()) {
            return ServiceResultEnum.GOODS_CATEGORY_ERROR.getResult();
        }

        /* 同级分类下已有同名商品 */
        if (goodsMapper.findByNameAndCategoryId(goods.getGoodsName(), goods.getGoodsCategoryId()) != null) {
            return ServiceResultEnum.SAME_GOODS_EXIST.getResult();
        }

        if (goodsMapper.save(goods) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String update(Goods goods) {
        Category category = categoryMapper.findById(goods.getGoodsCategoryId());
        /* 分类不存在或者不是第三级分类 */
        if (category == null || category.getCategoryLevel().intValue() != CategoryLevelEnum.LEVEL_THREE.getLevel()) {
            return ServiceResultEnum.GOODS_CATEGORY_ERROR.getResult();
        }

        Goods existingGoods = goodsMapper.findById(goods.getGoodsId());
        if (existingGoods == null) {
            return ServiceResultEnum.GOODS_NOT_EXIST.getResult();
        }

        Goods duplicateGoods = goodsMapper.findByNameAndCategoryId(goods.getGoodsName(), goods.getGoodsCategoryId());
        if (duplicateGoods != null && !duplicateGoods.getGoodsId().equals(goods.getGoodsId())) {
            return ServiceResultEnum.SAME_GOODS_EXIST.getResult();
        }

        goods.setUpdateTime(new Date());
        if (goodsMapper.update(goods) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public Goods getById(Long id) {
        return goodsMapper.findById(id);
    }

    @Override
    public PageResult<Goods> pageQuery(GoodsPageQueryDTO goodsPageQueryDTO) {
        PageMethod.startPage(goodsPageQueryDTO.getPageNumber(), goodsPageQueryDTO.getPageSize());
        Page<Goods> page = goodsMapper.pageQuery(goodsPageQueryDTO);

        return new PageResult<>(page.getResult(), page.getTotal(), page.getPageSize(), page.getPageNum());
    }

    @Override
    public String batchUpdateSellStatus(BatchIdDTO batchIdDTO, Integer sellStatus) {
        if (batchIdDTO == null || batchIdDTO.getIds().length == 0) {
            return ServiceResultEnum.PARAM_ERROR.getResult();
        }

        if (sellStatus != 0 && sellStatus != 1) {
            return ServiceResultEnum.PARAM_ERROR.getResult();
        }

        if (goodsMapper.batchUpdateSellStatus(batchIdDTO.getIds(), sellStatus) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String updateStock(StockNumUpdateDTO stockNumUpdateDTO) {
        if (stockNumUpdateDTO == null || stockNumUpdateDTO.getStockNumDTOS() == null || stockNumUpdateDTO.getStockNumDTOS().isEmpty()) {
            return ServiceResultEnum.PARAM_ERROR.getResult();
        }

        if (goodsMapper.updateStockNum(stockNumUpdateDTO.getStockNumDTOS()) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public List<Goods> getByIds(List<Long> goodsIds) {
        return goodsMapper.findByIds(goodsIds);
    }
}
