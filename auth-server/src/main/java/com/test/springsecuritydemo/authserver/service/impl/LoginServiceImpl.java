package com.test.springsecuritydemo.authserver.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.test.springsecuritydemo.authserver.entity.BaseResponse;
import com.test.springsecuritydemo.authserver.entity.LoginUser;
import com.test.springsecuritydemo.authserver.entity.SysUser;
import com.test.springsecuritydemo.authserver.service.LoginService;
import com.test.springsecuritydemo.authserver.util.JwtUtil;
import com.test.springsecuritydemo.authserver.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RedisUtil redisUtil;

    public BaseResponse login(SysUser user) {
        //AuthenticationManager  认证用户
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        /**
         * 认证用户
         * 封装权限
         * UserDetailsServiceImpl   loadUserByUsername
         */
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        if (null == authentication) {
            throw new RuntimeException("认证失败");
        }
        //生成jwt
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        int userId = loginUser.getUser().getId();

        JSONObject message = new JSONObject();
        message.put("user_id", loginUser.getUser().getId());
        message.put("user_name", loginUser.getUsername());

        String token = JwtUtil.createToken(message);
        //用户信息存入redis
        redisUtil.set(userId + "", JSON.toJSONString(loginUser));
        return BaseResponse.success(200, "success", token);
    }

    @Override
    public BaseResponse logout() {
        //获取SecurityContextHolder中的用户
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder
                .getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authenticationToken.getPrincipal();

        int userId = loginUser.getUser().getId();

        /**
         * 删除redis信息
         * 不用清理 SecurityContextHolder
         * 新请求不在当前线程中
         * 新请求带旧token失效
         */
        redisUtil.del(userId + "");

        return BaseResponse.success(200, "success", new JSONObject());
    }

}
