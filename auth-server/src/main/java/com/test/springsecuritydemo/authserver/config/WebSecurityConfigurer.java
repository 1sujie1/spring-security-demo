package com.test.springsecuritydemo.authserver.config;

import com.test.springsecuritydemo.authserver.filter.JwtAuthenticationTokenFilter;
import com.test.springsecuritydemo.authserver.handler.exception.AccessDeniedHandlerImpl;
import com.test.springsecuritydemo.authserver.handler.exception.AuthenticationEntryPointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthenticationEntryPointImpl authenticationEntryPoint;

    @Autowired
    AccessDeniedHandlerImpl accessDeniedHandler;

    @Autowired
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    /**
     * 注入authenticationManagerBean
     * 必须同时注入自定义UserDetails实现类
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //禁用csrf
        http.csrf().disable();

        //禁用session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //允许跨域访问
        http.cors();

        //关闭表单认证
        http.formLogin().disable();

        http.logout().disable();

        /**
         * 关闭基本认证
         * Authorization参数中附带用户/密码的base64编码
         */
        http.httpBasic().disable();

        /**
         * 异常处理
         * 认证失败异常处理
         * 授权失败异常处理
         */
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);

        //配置认证路径
        http.authorizeRequests()
                //放通静态资源
                .antMatchers("/static/**").permitAll()
                //匿名访问  不登录允许访问
                .antMatchers("/user/login").anonymous()
                .anyRequest().authenticated();

        /**
         * 设置过滤器
         * jwt认证过滤器
         */
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

    }

}
