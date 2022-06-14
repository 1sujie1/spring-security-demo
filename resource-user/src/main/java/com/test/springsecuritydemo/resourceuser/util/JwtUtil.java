package com.test.springsecuritydemo.resourceuser.util;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * JWT工具类
 * iss (issuer)：签发人
 * exp (expiration time)：过期时间
 * sub (subject)：主题
 * aud (audience)：受众
 * nbf (Not Before)：生效时间
 * iat (Issued At)：签发时间
 * jti (JWT ID)：编号
 */
@Component
public class JwtUtil {

    //加密密钥
    private static final String JWT_SECRET = "hhhhh";

    //签名算法
    private static SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;

    //签发人
    private static String issuser = "server";

    //接受者
    private static String aud = "client";

    //主体
    private static String sub = "coinpay";

    //有效时间 ms毫秒
    private static Long validity_time = 24 * 60 * 60 * 1000L;

    private JwtUtil() {

    }

    //创建token
    public static String createToken(JSONObject message) {
        //签名算法
        Claims claims = buildClaims();
        if (null == message) {
            return Jwts.builder().setClaims(claims)
                    .signWith(SignatureAlgorithm.HS512, JWT_SECRET).compact();
        }
        for (String key : message.keySet()) {
            claims.put(key, message.getString(key));
        }
        return Jwts.builder().setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET).compact();
    }

    //解析token
    public static Claims parseToken(String token) {
        return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
    }

    /**
     * 构造基础claims
     *
     * @return
     */
    private static Claims buildClaims() {

        //签发时间
        Date noBeforeDate = new Date();
        //过期时间
        Date expiredDate = new Date(noBeforeDate.getTime() + validity_time.longValue());
        Claims claims = new DefaultClaims();
        //签发人
        claims.setIssuer(issuser);
        //主题
        claims.setSubject(sub);
        //接收者
        claims.setAudience(aud);
        //生效时间
        claims.setNotBefore(noBeforeDate);
        //签发时间
        claims.setIssuedAt(noBeforeDate);
        //过期时间
        claims.setExpiration(expiredDate);
        return claims;
    }

    public static void main(String[] args) {
        JSONObject message = new JSONObject();
        message.put("user_id", 12312);
        message.put("user_name", "sdzsfas");
        String token = createToken(message);
        System.out.println(token);
        Claims claims = parseToken(token);
        System.out.println(claims);
    }

}
