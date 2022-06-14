package com.test.springsecuritydemo.authserver.filter;

import com.alibaba.fastjson.JSONObject;
import com.test.springsecuritydemo.authserver.entity.LoginUser;
import com.test.springsecuritydemo.authserver.util.JwtUtil;
import com.test.springsecuritydemo.authserver.util.RedisUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT认证过滤器
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = request.getHeader("token");

        if (StringUtils.isBlank(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        //解析token
        Claims claims = null;
        try {
            claims = JwtUtil.parseToken(token);
        } catch (Exception e) {
            throw new RuntimeException("认证失败");
        }

        int userId = Integer.parseInt(claims.get("user_id").toString());

        //查询redis用户数据
        Object obj = redisUtil.get(userId + "");
        if (null == obj) {
            throw new RuntimeException("用户未登录");
        }
        LoginUser user = JSONObject.parseObject(obj.toString(), LoginUser.class);

        //认证信息存入 SecurityContextHolder
//        Object principal;
//        Object credentials;
        //权限校验 FilterSecurityInterceptor
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //放行请求
        filterChain.doFilter(request, response);
    }

}
