package com.test.springsecuritydemo.resourceuser.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/")
public class UserController {

    @RequestMapping("get")
    @PreAuthorize("hasAuthority('hello')")
    public Object getObject(Authentication authentication) {
        return authentication;
    }

}
