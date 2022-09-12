package cn.tedu.csmall.passport.service;

import cn.tedu.csmall.passport.pojo.dto.AdminAddNewDTO;
import cn.tedu.csmall.passport.pojo.vo.AdminListItemVO;
import cn.tedu.csmall.passport.pojo.vo.AdminLoginDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 管理员业务接口
 */
public interface IAdminService {

    /**
     * 添加管理员
     *
     * @param adminAddNewDTO 管理员数据
     */
    @Transactional
    void addNew(AdminAddNewDTO adminAddNewDTO);

    /**
     * 查询管理员列表
     *
     * @return 管理员列表的集合
     */
    List<AdminListItemVO> list();

    /**
     * 管理员登录
     *
     * @param adminLoginDTO 封装了管理员登录时提交的数据的对象
     * @return 成功登录的用户的JWT数据
     */
    String login(AdminLoginDTO adminLoginDTO);

}
