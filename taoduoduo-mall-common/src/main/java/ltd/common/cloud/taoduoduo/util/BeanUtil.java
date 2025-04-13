package ltd.common.cloud.taoduoduo.util;

public class BeanUtil {

    private BeanUtil(){}

    public static void copyProperties(Object source, Object target, String... ignoreProperties) {
        if (source == null) {
            return;
        }
        org.springframework.beans.BeanUtils.copyProperties(source, target, ignoreProperties);
    }
}
