package cn.tedu.csmall.passport.service;

import cn.tedu.csmall.passport.ex.ServiceException;
import cn.tedu.csmall.passport.pojo.dto.AdminAddNewDTO;
import cn.tedu.csmall.passport.pojo.vo.AdminListItemVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class AdminServiceTests {

    @Autowired
    IAdminService service;

    @Test
    public void testAddNew() {
        AdminAddNewDTO admin = new AdminAddNewDTO();
        admin.setUsername("test-admin-001");
        admin.setPassword("1234");

        try {
            log.debug("添加管理员：{}", admin);
            service.addNew(admin);
            log.debug("添加成功！");
        } catch (ServiceException e) {
            log.debug("添加管理员失败，业务状态码：{}", e.getServiceCode());
            log.debug("添加管理员失败的原因：{}", e.getMessage());
        }
    }

    @Test
    public void testList() {
        List<AdminListItemVO> list = service.list();
        log.info("查询列表完成，结果集中的数据的数量=" + list.size());
        for (AdminListItemVO item : list) {
            log.info("{}", item);
        }
    }

}
