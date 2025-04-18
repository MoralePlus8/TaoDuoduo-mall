package ltd.goods.cloud.taoduoduo.entity.doc;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.util.List;

@Data
@Document(indexName = "goods_index")
public class GoodsDoc {

    public static class IndexAttributes {
        private IndexAttributes() {}
        public static final String GOODS_ID = "goods_id";
        public static final String GOODS_NAME = "goods_name";
        public static final String GOODS_INTRO = "goods_intro";
        public static final String CATEGORY_LEVEL1_ID = "category_level1_id";
        public static final String CATEGORY_LEVEL2_ID = "category_level2_id";
        public static final String CATEGORY_LEVEL3_ID = "category_level3_id";
        public static final String CATEGORY_ID_LIST = "category_id_list";
        public static final String PRICE = "selling_price";
        public static final String SALES_VOLUME = "sales_volume";
        public static final String TAGS = "tags";
        public static final String STATUS = "status";
        public static final String COVER_IMG = "cover_img";
    }

    @Id
    @Field(name = IndexAttributes.GOODS_ID, type = FieldType.Keyword)
    private Long goodsId;

    @Field(name = IndexAttributes.GOODS_NAME, type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String goodsName;

    @Field(name = IndexAttributes.GOODS_INTRO, type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String goodsIntro;

    @Field(name = IndexAttributes.CATEGORY_LEVEL1_ID, type = FieldType.Long, index = false)
    private Long categoryLevel1Id;

    @Field(name = IndexAttributes.CATEGORY_LEVEL2_ID, type = FieldType.Long, index = false)
    private Long categoryLevel2Id;

    @Field(name = IndexAttributes.CATEGORY_LEVEL3_ID, type = FieldType.Long, index = false)
    private Long categoryLevel3Id;

    @Field(name = IndexAttributes.CATEGORY_ID_LIST, type = FieldType.Long)
    private List<Long> categoryIdList;

    @Field(name = IndexAttributes.PRICE, type = FieldType.Double)
    private BigDecimal sellingPrice;

    @Field(name = IndexAttributes.SALES_VOLUME, type = FieldType.Integer)
    private Integer salesVolume;

    @Field(name = IndexAttributes.TAGS, type = FieldType.Keyword)
    private List<String> tags;

    @Field(name = IndexAttributes.STATUS, type = FieldType.Boolean)
    private Boolean status;

    @Field(name = IndexAttributes.COVER_IMG, type = FieldType.Keyword, index = false)
    private String coverImg;

}
