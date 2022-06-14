package com.test.springsecuritydemo.authserver.controller;

import io.jsonwebtoken.Jwts;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/user/")
public class UserController {

    @PreAuthorize("hasAuthority('test')")
    @GetMapping("get")
    public Object getObject(Authentication authentication) {
        return authentication.getPrincipal();
    }

    @RequestMapping("getJwtUser")
    public Object getJwtUser(HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization");
            token = token.replace("Bearer ", "");
            Object o = Jwts.parser().setSigningKey("hhhhh".getBytes("UTF-8"))
                    .parseClaimsJws(token).getBody();
            return o;
        } catch (Exception e) {

        }
        return null;
    }

}
