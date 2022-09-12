package cn.tedu.csmall.passport.service.impl;

import cn.tedu.csmall.passport.ex.ServiceException;
import cn.tedu.csmall.passport.mapper.AdminMapper;
import cn.tedu.csmall.passport.mapper.AdminROleMapper;
import cn.tedu.csmall.passport.pojo.dto.AdminAddNewDTO;
import cn.tedu.csmall.passport.pojo.entity.Admin;
import cn.tedu.csmall.passport.pojo.entity.AdminRole;
import cn.tedu.csmall.passport.pojo.vo.AdminListItemVO;
import cn.tedu.csmall.passport.pojo.vo.AdminLoginDTO;
import cn.tedu.csmall.passport.security.AdminDetails;
import cn.tedu.csmall.passport.service.IAdminService;
import cn.tedu.csmall.passport.util.JwtUtils;
import cn.tedu.csmall.passport.web.ServiceCode;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 品牌业务实现
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
@Service
public class AdminServiceImpl implements IAdminService {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminROleMapper adminROleMapper;


    public AdminServiceImpl() {
        log.debug("创建业务逻辑对象：AdminServiceImpl");
    }

    @Override
    public void addNew(AdminAddNewDTO adminAddNewDTO) {
        log.debug("开始处理添加管理员的业务，参数：{}", adminAddNewDTO);

        // 检查此用户名有没有被占用
        String username = adminAddNewDTO.getUsername();
        int count = adminMapper.countByUsername(username);
        if (count > 0) {
            String message = "添加管理员失败，用户名【" + username + "】已经被占用！";
            log.error(message);
            throw new ServiceException(ServiceCode.ERR_CONFLICT, message);
        }

        // 创建实体对象
        Admin admin = new Admin();

        // 将当前方法参数的值复制到实体类型的对象中
        BeanUtils.copyProperties(adminAddNewDTO, admin);
        // 补全属性值（不由客户端提交的属性的值，应该在插入之前补全）
        admin.setLoginCount(0);
        admin.setLastLoginIp(null);
        admin.setGmtLastLogin(null);

        // TODO 将原密码从Admin对象中取出，加密后再存入到Admin对象中
        //进行密码加密器
        String rawPassword = admin.getPassword();
        String encodePassword = passwordEncoder.encode(rawPassword);
        admin.setPassword(encodePassword);


        // 将品牌数据写入到数据库中
        log.debug("即将向表中写入数据：{}", admin);
        int rows = adminMapper.insert(admin);
        if (rows != 1) {
            String message = "添加管理员失败，服务器忙，请稍后再次尝试！【错误码:1】";
            log.error(message);
            throw new ServiceException(ServiceCode.ERR_INSERT, message);
        }

        //插入管理员与角色的关联数据,使得以上添加的管理员是否被分配了角色
        AdminRole adminRole=new AdminRole();
        adminRole.setAdminId(admin.getId());
        adminRole.setRoleId(2l);//锁定为2号角色
        log.debug("即将向管理员与角色的关联表中写入数据:{}",adminRole);
        int row=adminROleMapper.insert(adminRole);
        if (row != 1) {
            String message = "添加管理员失败，服务器忙，请稍后再次尝试！【错误码:2】";
            log.error(message);
            throw new ServiceException(ServiceCode.ERR_INSERT, message);
        }


    }

    @Override
    public List<AdminListItemVO> list() {
        log.debug("开始处理查询管理员列表的业务");
        return adminMapper.list();
    }

    @Override
    public String login(AdminLoginDTO adminLoginDTO) {
        log.debug("开始处理管理员登陆的业务,参数{}", adminLoginDTO);
        // TODO 需要实现登陆

        //调用authenticationManager执行Spring Security的认证
        /**Authentication :认证信息  包含用户名和密码*/
        Authentication authentication = new UsernamePasswordAuthenticationToken(adminLoginDTO.getUsername(), adminLoginDTO.getPassword());
        /**Authentication :认证信息  包含登陆成功的用户信息  类似于凭证*/
        Authentication loginResult = authenticationManager.authenticate(authentication);
        //以上调用的authenticate()方法是会抛出异常的方法,如果还能执行到此处,则表示用户名与密码是匹配的

        log.debug("登陆成功!认证方法返回: {}>>>{}", loginResult.getClass().getName(), loginResult);

  // 从认证结果中获取Principal，本质上是User类型，且
  // 是UserDetailsService中loadUserByUsername()返回的结果
        //Principal  当事人  登陆的是谁
        log.debug("尝试获取Principal:{}>>>{}", loginResult.getPrincipal().getClass().getName(), loginResult.getPrincipal());

        //因为AdminDetails里面声明了id所以将User进行更改
        AdminDetails adminDetails = (AdminDetails) loginResult.getPrincipal();

        //货物登陆的用户id
        Long id = adminDetails.getId();
        log.debug("成功登陆的用户id:{}", id);

        //获取到登陆成功的用户
        String username = adminDetails.getUsername();
        log.debug("登陆成功的用户名:{}", username);


        Collection<GrantedAuthority> authorities = adminDetails.getAuthorities();
        log.debug("登陆成功的权限:{}", authorities);
        String authoritiesString = JSON.toJSONString(authorities);

        log.debug("将权限转换为JSON:{}", authoritiesString);
        //应该在此处生成jwt数据,想jwt中存入:id(暂无)  username ,权限(暂无)
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", adminDetails.getUsername());
        claims.put("authorities", authoritiesString);
        claims.put("id",adminDetails.getId());

        String jwt = JwtUtils.generate(claims);

        return jwt;
    }
}
