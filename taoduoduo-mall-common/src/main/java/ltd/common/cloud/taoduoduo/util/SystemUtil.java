package ltd.common.cloud.taoduoduo.util;

import java.math.BigInteger;
import java.security.MessageDigest;

public class SystemUtil {

    public static String genToken(String src) {
        if (null == src || src.isEmpty()) {
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(src.getBytes());
            String result = new BigInteger(1, md.digest()).toString(16);
            if (result.length() == 31) {
                result = result + "-";
            }
            System.out.println(result);
            return result;
        } catch (Exception e) {
            return null;
        }
    }

}
