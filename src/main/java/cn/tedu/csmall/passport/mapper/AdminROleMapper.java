package cn.tedu.csmall.passport.mapper;

import cn.tedu.csmall.passport.pojo.entity.AdminRole;
import org.springframework.stereotype.Repository;

/**添加*/
@Repository
public interface AdminROleMapper {

    int insert(AdminRole adminRole);
}
