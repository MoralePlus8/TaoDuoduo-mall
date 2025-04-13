package ltd.common.cloud.taoduoduo.util;

public class NumberUtil {

    private NumberUtil() {}

    public static int genRandomNum(int length) {
        int num = 1;
        double random = Math.random();
        if (random < 0.1) {
            random = random + 0.1;
        }
        for (int i = 0; i < length; i++) {
            num = num * 10;
        }
        return (int) (random * num);
    }

    public static String genOrderNo() {
        StringBuilder buffer = new StringBuilder(String.valueOf(System.currentTimeMillis()));
        int num = genRandomNum(4);
        buffer.append(num);
        return buffer.toString();
    }

}
