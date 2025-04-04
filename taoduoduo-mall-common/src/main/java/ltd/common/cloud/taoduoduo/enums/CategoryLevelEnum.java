package ltd.common.cloud.taoduoduo.enums;


import lombok.Getter;

@Getter
public enum CategoryLevelEnum {

    DEFAULT(0, "ERROR"),
    LEVEL_FIRST(1, "一级分类"),
    LEVEL_SECOND(2, "二级分类"),
    LEVEL_THIRD(3, "三级分类");

    private final int level;

    private final String name;

    CategoryLevelEnum(int level, String name) {
        this.level = level;
        this.name = name;
    }

}
