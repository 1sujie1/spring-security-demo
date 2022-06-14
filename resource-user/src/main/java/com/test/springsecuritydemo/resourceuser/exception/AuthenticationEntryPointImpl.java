package com.test.springsecuritydemo.resourceuser.exception;

import com.alibaba.fastjson.JSONObject;
import com.test.springsecuritydemo.resourceuser.util.WebUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证异常处理器
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        JSONObject message = new JSONObject();
        message.put("code", HttpServletResponse.SC_UNAUTHORIZED);
        message.put("msg", "认证失败");
        WebUtil.sendResponse(response, message);
    }

}
