package com.test.springsecuritydemo.authserver.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sun.xml.bind.v2.model.core.ID;
import com.test.springsecuritydemo.authserver.entity.*;
import com.test.springsecuritydemo.authserver.entity.dto.MenuDto;
import com.test.springsecuritydemo.authserver.mapper.SysRoleMapper;
import com.test.springsecuritydemo.authserver.mapper.SysRoleMenuMapper;
import com.test.springsecuritydemo.authserver.mapper.SysUserMapper;
import com.test.springsecuritydemo.authserver.mapper.SysUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    SysUserMapper userMapper;

    @Autowired
    SysUserRoleMapper userRoleMapper;

    @Autowired
    SysRoleMenuMapper roleMenuMapper;

    //自定义认证方法
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<SysUser> users = userMapper.selectList(new EntityWrapper<SysUser>()
                .eq("user_name", username));
        SysUser user = null;
        if (null != users && users.size() > 0) {
            user = users.get(0);
        }
//        String password = user.getPassword();
//        String encodePassword = passwordEncoder.encode(password);
//        user.setPassword(encodePassword);
        //查询用户角色
        List<SysUserRole> roles = userRoleMapper.selectList(new EntityWrapper<SysUserRole>()
                .eq("user_id", user.getId()));
        if (roles == null || roles.size() == 0) {
            throw new RuntimeException("认证失败");
        }
        List<Integer> ids = new ArrayList<>();
        for (SysUserRole userRole : roles) {
            ids.add(userRole.getRoleId());
        }
        //获取权限信息
        List<MenuDto> menuDtos = roleMenuMapper.getRoleMenuList(ids);
        if (menuDtos == null || menuDtos.size() == 0) {
            throw new RuntimeException("认证失败");
        }

//        List<String> list = new ArrayList<>(Arrays.asList("test", "test1", "hello"));
        List<String> list = new ArrayList<>();

        for (MenuDto menuDto : menuDtos) {
            list.add(menuDto.getPerms());
        }

        return new LoginUser(user, list, null);
    }

}
