package com.test.springsecuritydemo.resourceuser.exception;

import com.alibaba.fastjson.JSONObject;
import com.test.springsecuritydemo.resourceuser.util.WebUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 权限不足异常处理器
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        JSONObject message = new JSONObject();
        message.put("code", HttpServletResponse.SC_FORBIDDEN);
        message.put("msg", "权限不足");
        WebUtil.sendResponse(response, message);
    }

}
