package cn.tedu.csmall.passport;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Date;

public class BCryptTests {

    @Test
    public void testBcrypt(){
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        String rawpassword="1234";

//        String encodePassword="$2a$10$MpVNZbyboBr/ka9DDUQP6.wBV6EGWDHteNX16Sc6Brr63jkJUz2GO";

        //对原文和密文进行对比 检查是否相同
        BCryptPasswordEncoder passwordEncoder1=new BCryptPasswordEncoder();
//        boolean matches=passwordEncoder.matches(rawpassword,encodePassword);

//        for (int i = 0; i < 10; i++) {
            String encodePassword=passwordEncoder.encode(rawpassword);

            System.out.println("原文:"+rawpassword+",密文:"+encodePassword);
//        System.out.println("匹配结构结果:"+matches);
//        }
//        System.out.println(date);
    }
}
