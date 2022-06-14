package com.test.springsecuritydemo.authserver.util;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 请求响应工具类
 */
public class WebUtil {

    public static void sendResponse(HttpServletResponse response, JSONObject message) {
        try {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            PrintWriter writer = response.getWriter();
            writer.print(message);
            writer.flush();
        } catch (Exception e) {

        }
    }

}
