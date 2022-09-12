package cn.tedu.csmall.passport.security;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginPrincipal implements Serializable {

    /**当前登录的用户id*/
    private Long id;

    /**当前登陆的用户名*/
    private String username;


}
