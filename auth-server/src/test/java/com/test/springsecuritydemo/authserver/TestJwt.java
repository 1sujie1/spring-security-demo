package com.test.springsecuritydemo.authserver;

import com.test.springsecuritydemo.authserver.entity.dto.MenuDto;
import com.test.springsecuritydemo.authserver.mapper.SysRoleMapper;
import com.test.springsecuritydemo.authserver.mapper.SysRoleMenuMapper;
import com.test.springsecuritydemo.authserver.mapper.SysUserRoleMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class TestJwt {

    @Autowired
    SysRoleMenuMapper roleMenuMapper;

    @Test
    public void test() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJOT05FX1BST1ZJREVEIiwic2NvcGUiOlsiYWxsIl0sImF0aSI6IjRmYzM5OTgxLTNhMmQtNGRhMi04ZjE1LTNjYzBmMmRkODZkZCIsImV4cCI6MTY1NzM2MTE4NSwiYXV0aG9yaXRpZXMiOlsiYWRtaW4iXSwianRpIjoiMThiMzZlYTYtZDFiNy00YjEyLWE0YjYtYWFjOWZkMzQwNjc0IiwiY2xpZW50X2lkIjoiY2xpZW50IiwiZW5oYW5jZSI6ImVuaGFuY2UgaW5mbyJ9.JmrcnR4slcj-XLIE_v4iO32Ige2wU6-SDAFxRDOY-ME";
        Claims claims = Jwts.parser().setSigningKey("hhhhh").parseClaimsJwt(token).getBody();
        System.out.println(claims);
    }

    @Test
    public void test1() {
        List<MenuDto> list = roleMenuMapper.getRoleMenuList(Arrays.asList(1, 2));
        System.out.println(list.size());
    }

    public static void main(String[] args) {
        try {
            String aa = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJOT05FX1BST1ZJREVEIiwic2NvcGUiOlsiYWxsIl0sImF0aSI6IjRmYzM5OTgxLTNhMmQtNGRhMi04ZjE1LTNjYzBmMmRkODZkZCIsImV4cCI6MTY1NzM2MTE4NSwiYXV0aG9yaXRpZXMiOlsiYWRtaW4iXSwianRpIjoiMThiMzZlYTYtZDFiNy00YjEyLWE0YjYtYWFjOWZkMzQwNjc0IiwiY2xpZW50X2lkIjoiY2xpZW50IiwiZW5oYW5jZSI6ImVuaGFuY2UgaW5mbyJ9.JmrcnR4slcj-XLIE_v4iO32Ige2wU6-SDAFxRDOY-ME";
            aa = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.F2e__nydU4nuFPQZxyOjjqhH4eX75gew2UrOvmrko4Y";
            String secret = "hhhhh";
            Claims claims = Jwts.parser().setSigningKey(secret.getBytes("UTF-8")).parseClaimsJws(aa).getBody();
            System.out.println(claims);
        } catch (Exception e) {

        }
    }

}
