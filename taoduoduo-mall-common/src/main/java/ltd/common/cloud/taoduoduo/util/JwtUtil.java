package ltd.common.cloud.taoduoduo.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import ltd.common.cloud.taoduoduo.pojo.UserInfo;

import java.util.Date;
import java.util.Map;

public class JwtUtil {

    private JwtUtil(){}

    private static final String RAW_SECRET = "taoduoduo";

    private static final String SECRET = MD5Util.md5Encode(RAW_SECRET, "UTF-8");

    private static final long EXPIRATION = (long) 7 * 24 * 60 * 60 * 1000; // 1 天

    public static String generateToken(String username, Map<String, Object> payload) {
        return Jwts.builder()
                .setSubject(username)
                .setClaims(payload)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public static UserInfo getUserDetail(String token) {
        Map<String, Object> payload = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(payload, UserInfo.class);
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
