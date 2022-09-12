package cn.tedu.csmall.passport;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTests {


    //密钥
    public String secretKey = "dbakjncvjzncvklsbzcjnmxslkdcz";

    @Test
    public void testGenerateJwt() {

        //准备claims
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 879);
        claims.put("name", "张三");

        //准备过期时间  :  10分钟
        Date expirationDate = new Date(System.currentTimeMillis() + 10 * 60 * 1000);


        // JWT 组件部分: header(头),Payload(载荷) 需要承载的数据含义,Signature(签名)

        //请写出java入口函数的signature
        // public static void main(String[] args)
        String jwt = Jwts.builder()
                //header:通过用于配置算法于此结果数据的类型
                //通过配置2个属性:typ(类型).alg(算法)
                .setHeaderParam("typ", "jwt")
                .setHeaderParam("alg", "HS256")
                //Payload 用于配置需要封装到jwt的数据
                .setClaims(claims)
                .setExpiration(expirationDate)
                //Signature:用于指定算法与密钥(盐)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        System.out.println(jwt);

    }

    @Test
    public void testParseJwt() {
        String jwt = "eyJ0eXAiOiJqd3QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NTgyODc3NzIsInVzZXJuYW1lIjoicm9vdCJ9.cT0CCyCeajRaS6-8j4lV5s6GX37aJYROnQUikQAw3_Y";
        Claims claims = Jwts.parser().setSigningKey("dgdaizkjfhbazkjhoibvdk").parseClaimsJws(jwt).getBody();
        Object username = claims.get("username");
        System.out.println("username=" + username);
//        Object id = claims.get("id");
//        Object name = claims.get("name");
//        System.out.println("id=" + id);
//        System.out.println("name=" + name);
    }

}
