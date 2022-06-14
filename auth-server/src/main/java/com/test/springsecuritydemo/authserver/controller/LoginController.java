package com.test.springsecuritydemo.authserver.controller;

import com.test.springsecuritydemo.authserver.entity.BaseResponse;
import com.test.springsecuritydemo.authserver.entity.SysUser;
import com.test.springsecuritydemo.authserver.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/")
public class LoginController {

    @Autowired
    LoginService loginService;

    //登录
    @PostMapping("login")
    public BaseResponse login(@RequestBody SysUser user) {
        return loginService.login(user);
    }

    //登出
    @PostMapping("logout")
    public BaseResponse logout() {
        System.out.println(1);
        return loginService.logout();
    }

}
