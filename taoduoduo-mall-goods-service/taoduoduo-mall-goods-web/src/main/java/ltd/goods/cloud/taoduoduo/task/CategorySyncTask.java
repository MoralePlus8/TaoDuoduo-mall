package ltd.goods.cloud.taoduoduo.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import ltd.common.cloud.taoduoduo.entity.Category;
import ltd.goods.cloud.taoduoduo.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CategorySyncTask {

    private final CategoryMapper categoryMapper;

    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${redis.path.category.level1}")
    private String level1Path;

    @Value("${redis.path.category.level2}")
    private String level2Path;

    @Value("${redis.path.category.level3}")
    private String level3Path;

    @Scheduled(cron = "0 0 2 * * ?")
    public void syncCategoryToRedis() {

        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusHours(1);

        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.between(Category.TableAttributes.UPDATE_TIME, startTime, endTime);
        queryWrapper.eq(Category.TableAttributes.IS_DELETED, 0);

        List<Category> updatedCategories = categoryMapper.selectList(queryWrapper);

        for (Category category : updatedCategories) {
            String redisKey;
            if (category.getCategoryLevel() == 1) {
                redisKey = level1Path + category.getCategoryId();
            } else if (category.getCategoryLevel() == 2) {
                redisKey = level2Path + category.getCategoryId();
            } else {
                redisKey = level3Path + category.getCategoryId();
            }
            redisTemplate.opsForValue().set(redisKey, category);
        }


    }


}
