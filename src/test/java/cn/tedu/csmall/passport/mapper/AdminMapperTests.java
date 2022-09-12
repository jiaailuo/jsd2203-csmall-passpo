package cn.tedu.csmall.passport.mapper;

import cn.tedu.csmall.passport.pojo.entity.Admin;
import cn.tedu.csmall.passport.pojo.vo.AdminListItemVO;
import cn.tedu.csmall.passport.pojo.vo.AdminLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class AdminMapperTests {

    @Autowired
    AdminMapper mapper;

    @Test
    public void testInsert() {
        Admin admin = new Admin();
        admin.setUsername("test-admin-001");
        admin.setPassword("1234");

        log.debug("插入数据之前，参数={}", admin);
        int rows = mapper.insert(admin);
        log.debug("rows = {}", rows);
        log.debug("插入数据之后，参数={}", admin);
    }

    @Test
    public void testCountByUsername() {
        String username = "test-admin-001";
        int count = mapper.countByUsername(username);
        log.debug("根据用户名【{}】统计管理员数量完成，统计结果={}", username, count);
    }

    @Test
    public void testList() {
        List<AdminListItemVO> list = mapper.list();
        log.info("查询列表完成，结果集中的数据的数量=" + list.size());
        for (AdminListItemVO item : list) {
            log.info("{}", item);
        }
    }

    @Test
    public void testGEtBynames(){
        String username="root";
        AdminLoginVO adminLoginVO=mapper.getByUsername(username);
        log.debug("{}",adminLoginVO);
    }

}
