package com.test.springsecuritydemo.resourceuser.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 自定登录用户信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser implements UserDetails, Serializable {

    private User user;

    //本地权限集合
    private List<String> permissions;

    //封装后的spring security权限集合
    @JSONField(serialize = false)
    private List<SimpleGrantedAuthority> authorityList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if (null != authorityList && authorityList.size() > 0) {
            return authorityList;
        }

        /**
         * SimpleGrantedAuthority
         * 封装权限信息到spring security 权限对象
         */
        authorityList = new ArrayList<>();
        for (String permission : permissions) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permission);
            authorityList.add(authority);
        }
        return authorityList;
    }

    public String getPassword() {
        return user.getPassword();
    }

    public String getUsername() {
        return user.getUserName();
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }

}
