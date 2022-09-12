package cn.tedu.csmall.passport.security;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;


@Setter
@Getter
@EqualsAndHashCode
@ToString(callSuper = true)
public class AdminDetails extends User {

    //识别用户名的唯一标尺
    private Long id;

    /**
     <p>Collection<? extends GrantedAuthority> authorities 权限</p>
     <br>
     boolean accountNonExpired,     账号有没有过期  <br>  <br>
     <p>boolean credentialsNonExpired, 凭证有没有过期</p>  <br>
     boolean accountNonLocked,      凭证有没有锁定
     */
    public AdminDetails(String username, String password, boolean enabled,
                        Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled,true,
                true, true, authorities);
    }
}
