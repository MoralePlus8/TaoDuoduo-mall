package ltd.goods.cloud.taoduoduo.mapper.repository;

import ltd.goods.cloud.taoduoduo.entity.doc.GoodsDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsRepository extends ElasticsearchRepository<GoodsDoc, Long> {

}
