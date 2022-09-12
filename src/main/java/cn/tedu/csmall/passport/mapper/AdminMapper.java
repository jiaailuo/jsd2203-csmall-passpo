package cn.tedu.csmall.passport.mapper;

import cn.tedu.csmall.passport.pojo.entity.Admin;
import cn.tedu.csmall.passport.pojo.vo.AdminListItemVO;
import cn.tedu.csmall.passport.pojo.vo.AdminLoginVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 管理员Mapper接口
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Repository
public interface AdminMapper {

    /**
     * 插入管理员数据
     *
     * @param admin 管理员数据
     * @return 受影响的行数，成功插入数据时，将返回1
     */
    int insert(Admin admin);

    /**
     * 根据管理员用户名统计此用户名对应的管理员数据的数量
     *
     * @param username 管理员用户名
     * @return 此名称对应的管理员数据的数量
     */
    int countByUsername(String username);

    /**
     * 查询管理员列表
     *
     * @return 管理员列表的集合
     */
    List<AdminListItemVO> list();

    /**根据管理员的用户名查询管理员的登陆信息*/
    AdminLoginVO getByUsername(String username);

}
