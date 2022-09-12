package cn.tedu.csmall.passport;

import cn.tedu.csmall.passport.mapper.AdminMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

import java.util.Random;
import java.util.UUID;

public class MessageDigestTest {

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @Test
    public void testMd5() {
        String rawPassword = "123456";
        String sort = "uyidfcjhx";
        String encodedPassword = DigestUtils.md5DigestAsHex((rawPassword + sort).getBytes());
        String encode = DigestUtils.md5DigestAsHex(sort.getBytes());
        System.out.println("end:" + encode);
        System.out.println("encodedPassword:" + encodedPassword);
    }

    @Test
    public void testUUID() {
        for (int i = 0; i < 20; i++) {
            Random random;
            System.out.println(UUID.randomUUID());
        }
    }

    @Test
    public void testSqlSession() {

    }


}