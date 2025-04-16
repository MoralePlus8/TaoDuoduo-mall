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

    @Id
    @Field(name = "goods_id", type = FieldType.Keyword)
    private Long goodsId;

    @Field(name = "goods_name", type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String goodsName;

    @Field(name = "goods_intro", type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String goodsIntro;

    @Field(name = "category_level1_id", type = FieldType.Long)
    private Integer categoryLevel1Id;

    @Field(name = "category_level2_id", type = FieldType.Long)
    private Integer categoryLevel2Id;

    @Field(name = "category_level3_id", type = FieldType.Long)
    private Integer categoryLevel3Id;

    @Field(name = "category_id_list", type = FieldType.Long)
    private List<Integer> categoryIdList;

    @Field(name = "price", type = FieldType.Double, index = false)
    private BigDecimal price;

    @Field(name = "sales_volume", type = FieldType.Integer, index = false)
    private Integer salesVolume;

    @Field(name = "tags", type = FieldType.Keyword)
    private List<String> tags;

    @Field(name = "status", type = FieldType.Boolean, index = false)
    private Boolean status;

    @Field(name = "cover_img", type = FieldType.Keyword, index = false)
    private String coverImg;

}
