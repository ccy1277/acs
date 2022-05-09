package com.ccy1277.acs.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * token 工具
 * created by ccy on 2022/5/9
 */

public class JwtTokenUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    /**
     * 根据payload生成token
     */
    private String setToken(Map<String, Object> claims){
        return Jwts.builder()
                // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setClaims(claims)
                // 过期时间
                .setExpiration(setExpiration())
                // 设置签名使用的签名算法和签名使用的秘钥
                .signWith(SignatureAlgorithm.HS512, setSecret())
                // 开始压缩成 header.payload.signature
                .compact();
    }

    /**
     * 根据用户信息生成token
     */
    public String setToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return setToken(claims);
    }

    private SecretKey setSecret() {
        byte[] noKey = secret.getBytes();
        // 根据给定的字节数组使用AES加密算法构造一个密钥
        return new SecretKeySpec(noKey, 0, noKey.length, "AES");
    }

    /**
     * 生成token的过期时间
     */
    private Date setExpiration(){
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * 获取token过期时间
     */
    private Date getTokenExpiration(String token){
        return getClaimsFromToken(token).getExpiration();
    }

    /**
     * 从token中获得Payload
     */
    private Claims getClaimsFromToken(String token){
        Claims claims = null;
        try{
            claims = Jwts.parser()
                    .setSigningKey(setSecret())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e){
            LOGGER.info("获取Payload失败:{}", token);
        }
        return claims;
    }

    /**
     * 从token中获取登录用户名
     */
    public String getUNameFromToken(String token){
        String username = null;
        try{
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e){
            LOGGER.info("获取登录用户名失败:{}", token);
        }
        return username;
    }

    /**
     * 验证token是否有效
     */
    public boolean validateToken(String token, UserDetails userDetails){
        String username = getUNameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 判断token是否过期
     */
    private boolean isTokenExpired(String token){
        return getTokenExpiration(token).before(new Date());
    }

    /**
     * 刷新token(token没有过期的时候)
     */
    public String refreshToken(String oldToken){
        if(oldToken == null || oldToken.length() < 1){
            return null;
        }
        // 去掉token的开头 'Bearer'
        String token = oldToken.substring(tokenHead.length());
        if(token.length() < 1){
            return null;
        }

        Claims claims = getClaimsFromToken(token);
        if(claims == null){
            return null;
        }
        // 如果token过期 刷新失败
        if(isTokenExpired(token)){
            return null;
        }
        // 如果token在30分钟之内刚刷新过，返回原token
        if(isRefreshed(token, 30*60*60)){
            return token;
        }else{
            claims.put(CLAIM_KEY_CREATED, new Date());
            return setToken(claims);
        }
    }

    /**
     * 判断token在指定时间内是否刷新过
     * @param time 以毫秒为单位
     */
    private boolean isRefreshed(String token, int time){
        Claims claims = getClaimsFromToken(token);
        Date created = claims.get(CLAIM_KEY_CREATED, Date.class);
        Date now = new Date(System.currentTimeMillis() - time);
        return now.before(created);
    }
}
