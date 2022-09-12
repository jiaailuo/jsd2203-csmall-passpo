package cn.tedu.csmall.passport.config;


import cn.tedu.csmall.passport.filter.JwtAuthorizationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * 配置类的白名单
 * <p>会按照定义的规则进行放行</p>
 * (prePostEnabled = true) 用于开启全局的方法上的授权检查
 */
@Slf4j
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthorizationFilter jwtAuthorizationFilter;


    //编码+对比(原文   密文)
    @Bean
    public PasswordEncoder passwordEncoder() {
        log.debug("创建密码编码器组件：BCryptPasswordEncoder");
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager()
            throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 在配置路径时，可以使用星号作为通配符
        // 使用 /* 只能匹配1层级路径，例如 /user 或 /brand，不可以匹配多层级，例如不可以匹配到 /user/list
        // 使用 /** 可以匹配若干层级路径

        // 白名单，不需要登录就可以访问
        String[] urls = {
                "/admin/login",
                "/doc.html",
                "/**/*.css",
                "/**/*.js",
                "/favicon.ico",
                "/v2/api-docs",
                "/swagger-resources"
        };

        //放行复杂请求的预检 本质上是在Spring Security的过滤链中添加corsFilter,以实现放行复杂的异步请求的预检
        http.cors();

        // 禁用防止跨域访问，如果无此配置，白名单路径的异步访问也会出现403错误
        // 禁用防止跨域伪造的攻击
        http.csrf().disable();

        http.authorizeRequests() // 请求需要被授权才可以访问  对请求进行认证
          //匹配某些路径  此方法并不决定这些路径应该如何被处理
//          .antMatchers(HttpMethod.OPTIONS, "/**")

          //允许此前的antMatchers()配置的路径的所有方法直接访问
//          .permitAll()

          .antMatchers(urls)  // 匹配某些路径
          .permitAll()        // 允许直接访问（不需要经过认证和授权）
          .anyRequest()       // 除了以上配置过的其它任何请求
          .authenticated();   // 已经通过认证，即已经登录过才可以访问


        // 添加处理JWT的过滤器，必须执行在处理用户名、密码的过滤器（Spring Security内置）之前
        http.addFilterBefore(jwtAuthorizationFilter,
                UsernamePasswordAuthenticationFilter.class);
    }

}
