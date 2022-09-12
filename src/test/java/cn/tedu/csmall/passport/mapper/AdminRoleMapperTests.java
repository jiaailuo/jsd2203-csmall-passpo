package cn.tedu.csmall.passport.mapper;


import cn.tedu.csmall.passport.pojo.entity.AdminRole;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class AdminRoleMapperTests {
    @Autowired
    AdminROleMapper mApper;

    @Test
    public void testInsert(){
        AdminRole adminRole=new AdminRole();
        adminRole.setAdminId(10l);
        adminRole.setRoleId(20l);

        log.debug("插入数据之前,参数{}",adminRole);
        int rows=mApper.insert(adminRole);
        log.debug("rows={}",rows);
    }
}
