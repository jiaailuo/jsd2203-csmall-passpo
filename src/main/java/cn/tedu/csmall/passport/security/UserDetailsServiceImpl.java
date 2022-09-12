package cn.tedu.csmall.passport.security;

import cn.tedu.csmall.passport.mapper.AdminMapper;
import cn.tedu.csmall.passport.pojo.vo.AdminLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
  当点击登陆时自动运行次代码<br>
  重写其中的UserDeatils loadUserByUsernmae(String s)方法,
  <p>Spring Security在执行认证时会自动调用此方法 ,此方法返回的结果</p>
  必须至少包括:密码,权限和其他必要的信息(根据API觉得).
*/
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AdminMapper adminMapper;


    @Override
    public AdminDetails loadUserByUsername(String s)
            throws UsernameNotFoundException {
        log.debug("Spring Security自动根据用户名{}查询用户详情", s);
        //执行查询
        AdminLoginVO admin = adminMapper.getByUsername(s);
        List<String> permissions=admin.getPermissions();
        List<SimpleGrantedAuthority>authorities =new ArrayList<>();
        for (String permission : permissions) {
            authorities.add(new SimpleGrantedAuthority(permission));
        }

        if (admin != null) {

            log.debug("查询到匹配的管理员信息:{}", admin);

            //执行数据库查询
            AdminDetails adminDetails=new AdminDetails(
                    admin.getUsername(),
                    admin.getPassword(),
                    admin.getEnable()==1,
                    authorities

            );
            adminDetails.setId(admin.getId());
            log.debug("即将向Spring Security返回AdminDetails：{}", adminDetails);
            return adminDetails;
//            UserDetails userDetails = User.builder()
//                    .username(admin.getUsername())
//                    .password(admin.getPassword())
//                    .disabled(admin.getEnable() == 0)        //账号是否禁用
//                    .accountLocked(false)                  //账户是否锁定
//                    .accountExpired(false)                 //账户是否过期
//                    .credentialsExpired(false)             //认证是否过期
//                    .authorities(admin.getPermissions().toArray(new String[]{}))  //[必须]次账号的权限信息
//                    .build();
//            log.debug("即将向Spring Security返回UserDetails:{}",
//                    userDetails);
//            return userDetails;
        }
        log.debug("没有查询到匹配的管理员信息");
        return null;
    }
}
