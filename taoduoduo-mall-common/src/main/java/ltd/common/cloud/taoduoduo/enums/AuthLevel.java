package ltd.common.cloud.taoduoduo.enums;

public enum AuthLevel {

    ADMIN(0, "管理员"),
    SHOP(1, "商户"),
    USER(2, "普通用户");

    final int level;
    final String name;

    AuthLevel(int level, String name) {
        this.level = level;
        this.name = name;
    }

}
