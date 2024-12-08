package pig.chat.springboot.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    public static String getToken(String id) {
        // 使用HS256签名算法和密钥secret来创建JwtBuilder
        JwtBuilder builder = Jwts.builder()
                .setId(id) // 设置JWT的ID
                .setSubject(id) // 设置主题，可以是用户的角色、类型等
                .setIssuedAt(new Date()) // 设置签发时间
                .signWith(SignatureAlgorithm.HS256, "secret") // 使用HS256算法和密钥secret进行签名
                .setExpiration(new Date(System.currentTimeMillis() + 7200000));  // 设置过期时间两小时
        // 构建并返回JWT
        return builder.compact();
    }

    public static Claims parseToken(String token) {
        // 解析JWT并验证签名
        Claims claims = Jwts.parser()
                .setSigningKey("secret") // 设置签名时使用的密钥
                .parseClaimsJws(token) // 解析token
                .getBody(); // 获取claims

        return claims;
    }
}
