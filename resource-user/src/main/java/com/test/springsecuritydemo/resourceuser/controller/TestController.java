package com.test.springsecuritydemo.resourceuser.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class TestController {

    @RequestMapping("test1")
    @PreAuthorize("hasAuthority('api:test1')")
    public String test1() {
        return "test1";
    }

    @RequestMapping("test2")
    @PreAuthorize("hasAuthority('api:test2')")
    public String test2() {
        return "test2";
    }

    @RequestMapping("test3")
    @PreAuthorize("hasAuthority('api:test3')")
    public String test3() {
        return "test3";
    }

}
